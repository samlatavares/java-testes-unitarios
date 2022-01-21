package br.ce.servicos;

import static br.ce.builders.LocacaoBuilder.umLocacao;
import static br.ce.builders.UsuarioBuilder.getUsuarioBuilder;
import static br.ce.servicos.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.servicos.matchers.MatchersProprios.ehDataComDiferencaDias;
import static br.ce.servicos.matchers.MatchersProprios.ehHoje;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.ce.daos.LocacaoDAO;
import br.ce.entidades.Filme;
import br.ce.entidades.Locacao;
import br.ce.entidades.Usuario;
import br.ce.exceptions.FilmeSemEstoqueException;
import br.ce.exceptions.LocadoraException;
import br.ce.utils.DataUtils;
import buildermaster.BuilderMaster;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	private SPCService spc;
	private LocacaoDAO dao;
	private EmailService email;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void before() {
		service = new LocacaoService();
		
		dao = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
		
		spc = Mockito.mock(SPCService.class);
		service.setSPCService(spc);
		
		email = Mockito.mock(EmailService.class);
		service.setEmailService(email);
	}
	
	@After
	public void after() {
		
	}
	
	@BeforeClass
	public static void beforeClass() {
	}
	
	@AfterClass
	public static void afterClass() {
		
	}
	
	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//assert
		error.checkThat(locacao.getValor(), is(equalTo(10.0)));
		error.checkThat(locacao.getValor(), not(12.0));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		
		Date dataAtual = new Date();
		if(DataUtils.verificarDiaSemana(dataAtual, Calendar.SATURDAY)) {
			error.checkThat(locacao.getDataRetorno(), ehDataComDiferencaDias(2));
		} else {
			error.checkThat(locacao.getDataRetorno(), ehDataComDiferencaDias(1));
		}
	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 0, 10.0));
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
	}
	
	@Test 
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		//arrange
		LocacaoService service = new LocacaoService();
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		//act
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuário Vazio!"));
		}
	}
	
	@Test 
	public void naoDeveAlugarFilmeSemFilme() throws LocadoraException, FilmeSemEstoqueException {
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme Vazio!");
		
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		
		//act
		service.alugarFilme(usuario, null);
		
	}
	
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Date dataAtual = new Date();
		Assume.assumeTrue(DataUtils.verificarDiaSemana(dataAtual, Calendar.SATURDAY));
				
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//assert		
		assertThat(locacao.getDataRetorno(), caiNumaSegunda());
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws FilmeSemEstoqueException {
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		
		Mockito.when(spc.possuiNegativacao(usuario)).thenReturn(true);
		
		//act
		try {
			service.alugarFilme(usuario, Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0)));
			//assert
			Assert.fail(); //A exceção é esperada. Caso ela não ocorra, é necessário que o teste falhe.
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuário Negativado!"));
		}
		
		//assert
		Mockito.verify(spc).possuiNegativacao(usuario);
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		//arrange
		Usuario usuarioAtrasado = getUsuarioBuilder().agora();
		Usuario usuarioEmDia = getUsuarioBuilder().comNome("Usuário em dia").agora();
		Usuario usuarioAtrasadoTb = getUsuarioBuilder().comNome("Outro atrasado").agora();
		List<Locacao> locacoes = Arrays.asList(umLocacao().atrasada().comUsuario(usuarioAtrasado).agora(),
												umLocacao().comUsuario(usuarioEmDia).agora(),
												umLocacao().atrasada().comUsuario(usuarioAtrasadoTb).agora());
		when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
			
		//act
		service.notificarAtrasos();
		
		//assert
		Mockito.verify(email).notificarAtraso(usuarioAtrasado);
		Mockito.verify(email, Mockito.never()).notificarAtraso(usuarioEmDia);
		Mockito.verify(email).notificarAtraso(usuarioAtrasadoTb);
		Mockito.verifyNoMoreInteractions(email);
	}
	
	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Locacao.class);
	}

}

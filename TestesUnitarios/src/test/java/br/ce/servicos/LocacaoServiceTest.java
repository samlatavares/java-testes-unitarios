package br.ce.servicos;

import static br.ce.builders.LocacaoBuilder.umLocacao;
import static br.ce.builders.UsuarioBuilder.getUsuarioBuilder;
import static br.ce.servicos.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.servicos.matchers.MatchersProprios.ehHoje;
import static br.ce.servicos.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.ce.builders.LocacaoBuilder;
import br.ce.daos.LocacaoDAO;
import br.ce.entidades.Filme;
import br.ce.entidades.Locacao;
import br.ce.entidades.Usuario;
import br.ce.exceptions.FilmeSemEstoqueException;
import br.ce.exceptions.LocadoraException;
import br.ce.runners.ParallelRunner;
import br.ce.utils.DataUtils;

//@RunWith(ParallelRunner.class)
public class LocacaoServiceTest {
	
	@InjectMocks @Spy
	private LocacaoService service;
	
	@Mock
	private SPCService spc;
	
	@Mock
	private LocacaoDAO dao;
	
	@Mock
	private EmailService email;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		
//		service = new LocacaoService();		
//		dao = Mockito.mock(LocacaoDAO.class);
//		service.setLocacaoDAO(dao);
//		
//		spc = Mockito.mock(SPCService.class);
//		service.setSPCService(spc);
//		
//		email = Mockito.mock(EmailService.class);
//		service.setEmailService(email);
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
		

		Mockito.doReturn(DataUtils.obterData(28, 1, 2022)).when(service).obterData();
	
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//assert
		error.checkThat(locacao.getValor(), is(equalTo(10.0)));
		error.checkThat(locacao.getValor(), not(12.0));
//		error.checkThat(locacao.getDataLocacao(), ehHoje());
		
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 1, 2022)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 1, 2022)), is(true));
		
//		Date dataAtual = new Date();
//		if(DataUtils.verificarDiaSemana(dataAtual, Calendar.SATURDAY)) {
//			error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(2));
//		} else {
//			error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
//		}
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
			Assert.assertThat(e.getMessage(), is("Usu?rio Vazio!"));
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
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
//		Date dataAtual = new Date();
//		Assume.assumeTrue(DataUtils.verificarDiaSemana(dataAtual, Calendar.SATURDAY));
				
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		Mockito.doReturn(DataUtils.obterData(29, 1, 2022)).when(service).obterData();
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//assert		
		assertThat(locacao.getDataRetorno(), caiNumaSegunda());

	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		
		Mockito.when(spc.possuiNegativacao(usuario)).thenReturn(true);
		Mockito.when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true); //op??o mais gen?rica - matcher
		
		//act
		try {
			service.alugarFilme(usuario, Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0)));
			//assert
			Assert.fail(); //A exce??o ? esperada. Caso ela n?o ocorra, ? necess?rio que o teste falhe.
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usu?rio Negativado!"));
		}
		
		//assert
		Mockito.verify(spc).possuiNegativacao(usuario);
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		//arrange
		Usuario usuarioAtrasado = getUsuarioBuilder().agora();
		Usuario usuarioEmDia = getUsuarioBuilder().comNome("Usu?rio em dia").agora();
		Usuario usuarioAtrasadoTb = getUsuarioBuilder().comNome("Outro atrasado").agora();
		List<Locacao> locacoes = Arrays.asList(umLocacao().atrasada().comUsuario(usuarioAtrasado).agora(),
												umLocacao().comUsuario(usuarioEmDia).agora(),
												umLocacao().atrasada().comUsuario(usuarioAtrasadoTb).agora(),
												umLocacao().atrasada().comUsuario(usuarioAtrasadoTb).agora());
		when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
			
		//act
		service.notificarAtrasos();
		
		//assert
		Mockito.verify(email, Mockito.times(3)).notificarAtraso(Mockito.any(Usuario.class)); //matcher
		Mockito.verify(email).notificarAtraso(usuarioAtrasado);
		Mockito.verify(email, Mockito.never()).notificarAtraso(usuarioEmDia);
		Mockito.verify(email, Mockito.times(2)).notificarAtraso(usuarioAtrasadoTb);
		Mockito.verifyNoMoreInteractions(email);
	}
	
	@Test
	public void deveTratarErroNoSPC() throws Exception {
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha catastr?fica"));
		
		//assert
		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com o SPC, tente novamente!");
		
		//act
		service.alugarFilme(usuario, filmes);
		
	}
	
	@Test
	public void deveProrrogarUmaLocacao() {
		//arrange
		Locacao locacao = LocacaoBuilder.umLocacao().agora();
		
		//act
		service.prorrogarLocacao(locacao, 3);
		
		//assert
		ArgumentCaptor<Locacao> argumentCaptor = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argumentCaptor.capture());
		Locacao locacaoRetornada = argumentCaptor.getValue();
		
		error.checkThat(locacaoRetornada.getValor(), is(12.0));
		error.checkThat(locacaoRetornada.getDataLocacao(), ehHoje());
		error.checkThat(locacaoRetornada.getDataRetorno(), ehHojeComDiferencaDias(3));
		
	}
	
	@Test
	public void deveCalcularValorLocacao() throws Exception {
		//arrange
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		//act
		Class<LocacaoService> clazz = LocacaoService.class;
		Method metodo = clazz.getDeclaredMethod("calcularValorLocacao", List.class);
		metodo.setAccessible(true);
		Double valor = (Double) metodo.invoke(service, filmes);
		
		//assert
		Assert.assertThat(valor, is(10.0));
		
	}
	
//	public static void main(String[] args) {
//	new BuilderMaster().gerarCodigoClasse(Locacao.class);
//}
}

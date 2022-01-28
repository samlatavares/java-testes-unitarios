package br.ce.servicos;

import static br.ce.builders.UsuarioBuilder.getUsuarioBuilder;
import static br.ce.servicos.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.servicos.matchers.MatchersProprios.ehHoje;
import static br.ce.servicos.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.ce.daos.LocacaoDAO;
import br.ce.entidades.Filme;
import br.ce.entidades.Locacao;
import br.ce.entidades.Usuario;
import br.ce.utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class/*, DataUtils.class*/})
@PowerMockIgnore({"jdk.internal.reflect.*"})
public class LocacaoServiceTest_PowerMock {
	
	@InjectMocks
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
		service = PowerMockito.spy(service);
		
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
		
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 1, 2022));
		
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.DAY_OF_MONTH, 28);
//		calendar.set(Calendar.MONTH, Calendar.JANUARY);
//		calendar.set(Calendar.YEAR, 2022);
//		
//		PowerMockito.mockStatic(Calendar.class);
//		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//assert
		error.checkThat(locacao.getValor(), is(equalTo(10.0)));
		error.checkThat(locacao.getValor(), not(12.0));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 1, 2022)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 1, 2022)), is(true));
		
		Date dataAtual = new Date();
		if(DataUtils.verificarDiaSemana(dataAtual, Calendar.SATURDAY)) {
			error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(2));
		} else {
			error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
		}
	}	
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
//		Date dataAtual = new Date();
//		Assume.assumeTrue(DataUtils.verificarDiaSemana(dataAtual, Calendar.SATURDAY));
				
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 1, 2022));
		
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.DAY_OF_MONTH, 29);
//		calendar.set(Calendar.MONTH, Calendar.JANUARY);
//		calendar.set(Calendar.YEAR, 2022);
//		
//		PowerMockito.mockStatic(Calendar.class);
//		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//assert		
		assertThat(locacao.getDataRetorno(), caiNumaSegunda());
		PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();
		
		PowerMockito.verifyStatic(Mockito.times(2));
		Calendar.getInstance();
	}
	
	
	@Test
	public void deveAlugarFilmeSemCalcularOValor() throws Exception {
		//arrange
		Usuario usuario = getUsuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes); // Mockando o retorno do método
			
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//assert
		Assert.assertThat(locacao.getValor(), is(1.0));
		PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes); //Verificando se o método foi chamado
	}
	
	@Test
	public void deveCalcularValorLocacao() throws Exception {
		//arrange
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		//act
		Double valor = (Double)Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);
		
		//assert
		Assert.assertThat(valor, is(10.0));
		
	}

}

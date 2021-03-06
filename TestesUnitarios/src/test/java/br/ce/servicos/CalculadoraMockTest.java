package br.ce.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;



public class CalculadoraMockTest {
	
	@Mock
	private Calculadora calculadoraMock;
	
	@Spy
	private Calculadora calculadoraSpy;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void devoMostrarDiferencaEntreMockESpy() {
		Mockito.when(calculadoraMock.somar(1, 2)).thenCallRealMethod();
		Mockito.when(calculadoraSpy.somar(1, 2)).thenReturn(8);
		
		System.out.println("Mock: " + calculadoraMock.somar(1, 2));
		System.out.println("Spy: " + calculadoraSpy.somar(1, 3));
	}
	
	@Test
	public void teste() { //exemplo de utilização de matchers e do mockito em uma classe concreta
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);		
		Mockito.when(calc.somar(argumentCaptor.capture(), argumentCaptor.capture())).thenReturn(5);
				
		Assert.assertEquals(5, calc.somar(1, 100000));
		
		System.out.println(argumentCaptor.getAllValues());
	}
}

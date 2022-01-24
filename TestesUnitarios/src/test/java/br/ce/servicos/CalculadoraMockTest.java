package br.ce.servicos;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;



public class CalculadoraMockTest {

	@Test
	public void teste() { //exemplo de utilização de matchers e do mockito em uma classe concreta
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);		
		Mockito.when(calc.somar(argumentCaptor.capture(), argumentCaptor.capture())).thenReturn(5);
				
		Assert.assertEquals(5, calc.somar(1, 100000));
		
		System.out.println(argumentCaptor.getAllValues());
	}
}

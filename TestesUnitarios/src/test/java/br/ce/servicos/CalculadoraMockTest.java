package br.ce.servicos;

import org.junit.Test;
import org.mockito.Mockito;

public class CalculadoraMockTest {

	@Test
	public void teste() { //exemplo de utilização de matchers e do mockito em uma classe concreta
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		Mockito.when(calc.somar(Mockito.eq(1),Mockito.anyInt())).thenReturn(5);
		
		System.out.println(calc.somar(1, 8));
	}
}

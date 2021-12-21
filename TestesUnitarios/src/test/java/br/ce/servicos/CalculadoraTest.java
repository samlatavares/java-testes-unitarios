package br.ce.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.exceptions.NaoPodeDividirPorZeroException;



public class CalculadoraTest {

	private Calculadora calculadora;
	
	@Before
	public void before() {
		calculadora = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		//arrange
		int a = 5;
		int b = 5;
		
		//act
		int soma = calculadora.somar(a, b);
		
		//assert
		Assert.assertEquals(10, soma);		
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		//arrange
		int a = 5;
		int b = 5;
		
		//act
		int subtracao = calculadora.subtrair(a, b);
		
		//assert
		Assert.assertEquals(0, subtracao);		
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		//arrange
		int a = 5;
		int b = 5;
		
		//act
		int divisao = calculadora.dividir(a, b);
		
		//assert
		Assert.assertEquals(1, divisao);		
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		//arrange
		int a = 5;
		int b = 0;
		
		//act
		int divisao = calculadora.dividir(a, b);
		
		//assert
		Assert.assertEquals(1, divisao);		
	}
}

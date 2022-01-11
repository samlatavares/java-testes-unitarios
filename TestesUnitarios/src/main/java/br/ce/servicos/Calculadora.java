package br.ce.servicos;

import br.ce.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int somar(int a, int b) {
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int dividir(int a, int b) throws NaoPodeDividirPorZeroException {
		
		if(b == 0) {
			throw new NaoPodeDividirPorZeroException();
		}
		
		return a/b;
	}
	
	public int dividir(String a, String b) throws NaoPodeDividirPorZeroException {
		int primeiro = Integer.valueOf(a);
		int segundo = Integer.valueOf(b);
		
		if(segundo == 0) {
			throw new NaoPodeDividirPorZeroException();
		}
		
		return primeiro/segundo;
	}
}

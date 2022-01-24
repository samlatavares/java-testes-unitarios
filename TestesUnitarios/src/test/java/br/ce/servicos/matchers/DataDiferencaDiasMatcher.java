package br.ce.servicos.matchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

	private Integer quantidadeDias;
	
	public DataDiferencaDiasMatcher(Integer quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
	}
	
	public void describeTo(Description description) {
		Date dataEsperada = DataUtils.obterDataComDiferencaDias(quantidadeDias);
		DateFormat format = new SimpleDateFormat("dd/MM/YYYY");
		description.appendText(format.format(dataEsperada));
		
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(quantidadeDias));
	}

}

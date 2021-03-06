package br.com.casadocodigo.loja.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class Pagamento implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotNull
	private BigDecimal value;

	public Pagamento(BigDecimal value) {
		this.value = value;
	}
	
	public BigDecimal getValue() {
		return value;
	}

}

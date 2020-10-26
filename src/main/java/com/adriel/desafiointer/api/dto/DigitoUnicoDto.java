package com.adriel.desafiointer.api.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class DigitoUnicoDto {

	private Long id;
	private String parametroN;
	private int parametroK;
	private String valorCalculado;
	private Long usuarioId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "parametroN não pode ser vazio.")
	@Length(min = 1, max = 250, message = "Nome deve conter entre 1 e 250 caracteres.")
	public String getParametroN() {
		return parametroN;
	}
	
	public void setParametroN(String parametroN) {
		this.parametroN = parametroN;
	}
	
	public int getParametroK() {
		return parametroK;
	}
	
	public void setParametroK(int parametroK) {
		this.parametroK = parametroK;
	}
	
	public String getValorCalculado() {
		return valorCalculado;
	}
	
	public void setValorCalculado(String valorCalculado) {
		this.valorCalculado = valorCalculado;
	}
	
	public Long getusuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
}

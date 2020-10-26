package com.adriel.desafiointer.api.dto;

public class ChaveDto {
	
	private Long id;
	private String chave;
	private Long usuarioId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getChave() {
		return chave;
	}
	
	public void setChave(String nome) {
		this.chave = nome;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
}

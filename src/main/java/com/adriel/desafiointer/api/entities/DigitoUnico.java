package com.adriel.desafiointer.api.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "digitounico")
public class DigitoUnico implements Serializable {


	private static final long serialVersionUID = -922487901798985748L;
	private Long id;
	private String valorCalculado;
	private Usuario usuario;
	private String parametroN;
	private int  parametrok;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "valorcalculado", nullable = false)
	public String getValorCalculado() {
		return valorCalculado;
	}

	public void setValorCalculado(String valorCalculado) {
		this.valorCalculado = valorCalculado;
	}
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Column(name = "parametron", nullable = false)
	public String getParametroN() {
		return parametroN;
	}

	public void setParametroN(String parametroN) {
		this.parametroN = parametroN;
	}
	
	@Column(name = "parametrok", nullable = false)
	public int getParametroK() {
		return parametrok;
	}

	public void setParametroK(int paramentrok) {
		this.parametrok = paramentrok;
	}
	

}

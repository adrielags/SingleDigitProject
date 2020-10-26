package com.adriel.desafiointer.api.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	

	private static final long serialVersionUID = 7743732093874653593L;
	private Long id;
	private String nome;
	private String email;
	private List<DigitoUnico> digitosUnicosCalculados;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<DigitoUnico> getDigitosUnicosCalculados() {
		return digitosUnicosCalculados;
	}

	public void setDigitosUnicosCalculados(List<DigitoUnico> digitosUnicosCalculados) {
		this.digitosUnicosCalculados = digitosUnicosCalculados;
	}

}

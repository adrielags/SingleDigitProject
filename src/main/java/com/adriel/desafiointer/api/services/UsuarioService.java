package com.adriel.desafiointer.api.services;

import java.util.Optional;

import com.adriel.desafiointer.api.entities.Usuario;

public interface UsuarioService {
	
	
	/**
	 * Persiste um usuário na base de dados.
	 * 
	 * @param usuario
	 * @return Usuario
	 */
	Usuario persistir(Usuario usuario);
	
	/**
	 * Busca e retorna um usuário dado um CPF.
	 * 
	 * @param nome
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorNome(String nome);
	
	/**
	 * Busca e retorna um usuário dado um email.
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorEmail(String email);
	
	/**
	 * Busca e retorna um usuário por ID.
	 * 
	 * @param id
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorId(Long id);
	
	/**
	 * Remove um usuário da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

}

package com.adriel.desafiointer.api.services;

import com.adriel.desafiointer.api.entities.Chave;

public interface ChaveService {
	
	/**
	 * Retorna uma chave de um usuário.
	 * 
	 * @param usuarioId
	 * @param pageRequest
	 * @return Page<DigitoUnico>
	 */
	Chave buscarPorUsuarioId(Long usuarioId);
	
	/**
	 * Persiste uma chave pública na base de dados.
	 * 
	 * @param usuarioId
	 * @param pageRequest
	 * @return Page<DigitoUnico>
	 */
	Chave persistir(Chave chave);

}

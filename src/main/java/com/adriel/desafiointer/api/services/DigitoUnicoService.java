package com.adriel.desafiointer.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.adriel.desafiointer.api.entities.DigitoUnico;


public interface DigitoUnicoService {
	/**
	 * Retorna uma lista paginada de digitos únicos de um determinado funcionário.
	 * 
	 * @param digitoUnicoId
	 * @param pageRequest
	 * @return Page<DigitoUnico>
	 */
	Page<DigitoUnico> buscarPorUsuarioId(Long usuarioId, PageRequest pageRequest);
	
	/**
	 * Retorna um lançamento por ID.
	 * 
	 * @param id
	 * @return Optional<Lancamento>
	 */
	Optional<DigitoUnico> buscarPorId(Long id);
	
	/**
	 * Persiste um lançamento na base de dados.
	 * 
	 * @param digitoUnico
	 * @return DigitoUnico
	 */
	DigitoUnico persistir(DigitoUnico digitoUnico);
	
	/**
	 * Remove um digitoUnico da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

}

package com.adriel.desafiointer.api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adriel.desafiointer.api.entities.Chave;
import com.adriel.desafiointer.api.repositories.ChaveRepository;
import com.adriel.desafiointer.api.services.ChaveService;

@Service
public class ChaveServiceImpl implements ChaveService {
	
	private static final Logger log = LoggerFactory.getLogger(ChaveServiceImpl.class);

	@Autowired
	private ChaveRepository chaveRepository;
	
	
	public Chave buscarPorUsuarioId(Long usuarioId) {
		log.info("Buscando digitos unicos para o usuario ID {}", usuarioId);
		return this.chaveRepository.findByUsuarioId(usuarioId);
	}
	
	public Chave persistir(Chave chave) {
		log.info("Persistindo o chave: {}", chave);
		return this.chaveRepository.save(chave);
	}
	
}

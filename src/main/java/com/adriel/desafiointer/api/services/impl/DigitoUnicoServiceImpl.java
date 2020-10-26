package com.adriel.desafiointer.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.adriel.desafiointer.api.entities.DigitoUnico;
import com.adriel.desafiointer.api.repositories.DigitoUnicoRepository;
import com.adriel.desafiointer.api.services.DigitoUnicoService;


@Service
public class DigitoUnicoServiceImpl implements DigitoUnicoService {
	
	private static final Logger log = LoggerFactory.getLogger(DigitoUnicoServiceImpl.class);

	@Autowired
	private DigitoUnicoRepository digitoUnicoRepository;

	public Page<DigitoUnico> buscarPorUsuarioId(Long usuarioId, PageRequest pageRequest) {
		log.info("Buscando digitos unicos para o usuario ID {}", usuarioId);
		return this.digitoUnicoRepository.findByUsuarioId(usuarioId, pageRequest);
	}
	
	public Optional<DigitoUnico> buscarPorId(Long id) {
		log.info("Buscando um lançamento pelo ID {}", id);
		return this.digitoUnicoRepository.findById(id);
	}
	
	public DigitoUnico persistir(DigitoUnico digitoUnico) {
		log.info("Persistindo o dígito único: {}", digitoUnico);
		return this.digitoUnicoRepository.save(digitoUnico);
	}
	
	public void remover(Long id) {
		log.info("Removendo o digito único de ID {}", id);
		this.digitoUnicoRepository.deleteById(id); 
	}

}

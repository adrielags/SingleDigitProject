package com.adriel.desafiointer.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adriel.desafiointer.api.entities.Usuario;
import com.adriel.desafiointer.api.repositories.UsuarioRepository;
import com.adriel.desafiointer.api.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario persistir(Usuario usuario) {
		log.info("Persistindo usuário: {}", usuario);
		return this.usuarioRepository.save(usuario);
	}
	
	public Optional<Usuario> buscarPorNome(String nome) {
		log.info("Buscando usuário pelo nome {}", nome);
		return Optional.ofNullable(this.usuarioRepository.findByNome(nome));
	}
	
	public Optional<Usuario> buscarPorEmail(String email) {
		log.info("Buscando funcionário pelo email {}", email);
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}
	
	public Optional<Usuario> buscarPorId(Long id) {
		log.info("Buscando funcionário pelo IDl {}", id);
		return this.usuarioRepository.findById(id);
	}
	
	public void remover(Long id) {
		log.info("Removendo o lançamento ID {}", id);
		this.usuarioRepository.deleteById(id);
	}
	
}

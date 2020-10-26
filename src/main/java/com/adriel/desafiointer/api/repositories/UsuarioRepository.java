package com.adriel.desafiointer.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adriel.desafiointer.api.entities.Usuario;

@Transactional(readOnly = true)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
		Usuario findByEmail(String email);
		
		Usuario findByNome(String nome);
		
		Usuario findByNomeOrEmail(String nome, String email);
}

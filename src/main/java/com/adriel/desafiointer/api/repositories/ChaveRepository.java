package com.adriel.desafiointer.api.repositories;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.adriel.desafiointer.api.entities.Chave;

@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "ChaveRepository.findByUsuarioId", 
				query = "SELECT usr FROM Usuario usr WHERE usr.usuario.id = :usuarioId") })
public interface ChaveRepository extends JpaRepository<Chave, Long>  {
	
	Chave findByUsuarioId(@Param("usuarioId") Long usuarioId);
	
}

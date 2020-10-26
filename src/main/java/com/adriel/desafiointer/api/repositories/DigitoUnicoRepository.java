package com.adriel.desafiointer.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adriel.desafiointer.api.entities.DigitoUnico;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "DigitoUnicoRepository.findByUsuarioId", 
				query = "SELECT usr FROM Usuario usr WHERE usr.usuario.id = :usuarioId") })
public interface DigitoUnicoRepository extends JpaRepository<DigitoUnico, Long> {
	
	List<DigitoUnico> findByUsuarioId(@Param("usuarioId") Long usuarioId);
	
	Page<DigitoUnico> findByUsuarioId(@Param("usuarioId") Long funcionarioId, Pageable pageable);

}

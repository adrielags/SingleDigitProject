package com.adriel.desafiointer.api.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.adriel.desafiointer.api.entities.DigitoUnico;
import com.adriel.desafiointer.api.entities.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DigitoUnicoRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DigitoUnicoRepository digitoUnicoRepository;
	
	private Long usuarioId;
	
	@Before
	public void setUp() throws Exception {
		 Usuario usuario = this.usuarioRepository.save(obterDadosUsuario());
		 this.usuarioId = usuario.getId();
		 this.digitoUnicoRepository.save(obterDadosDigitoUnico(usuario));
		 this.digitoUnicoRepository.save(obterDadosDigitoUnicoAlternativo(usuario));
	}
	@After
	public void tearDown() throws Exception {
		this.usuarioRepository.deleteAll();
	}
	
	@Test
	public void testBuscarLancamentosPorFuncionarioId() {
		List<DigitoUnico> digits = this.digitoUnicoRepository.findByUsuarioId(usuarioId);
		
		assertEquals(2, digits.size());
	}
	
	@Test
	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
		PageRequest page = PageRequest.of(0, 10);
		Page<DigitoUnico> digits = this.digitoUnicoRepository.findByUsuarioId(usuarioId, page);
		
		assertEquals(2, digits.getTotalElements());
	}

	private Usuario obterDadosUsuario() {
		Usuario usuario = new Usuario();
		usuario.setEmail("teste@teste.com.br");
		usuario.setNome("nometeste");
		return usuario;
	}
	
	
	private DigitoUnico obterDadosDigitoUnico(Usuario usuario) throws NoSuchAlgorithmException {
		DigitoUnico digit = new DigitoUnico();
		digit.setParametroN("1");
		digit.setParametroK(2);
		digit.setValorCalculado("2");
		
		digit.setUsuario(usuario);

		return digit;
	}
	
	private DigitoUnico obterDadosDigitoUnicoAlternativo(Usuario usuario) throws NoSuchAlgorithmException {
		DigitoUnico digit = new DigitoUnico();
		digit.setParametroN("1");
		digit.setParametroK(2);
		digit.setValorCalculado("2");
		
		digit.setUsuario(usuario);

		return digit;
	}

}

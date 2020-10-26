package com.adriel.desafiointer.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.adriel.desafiointer.api.entities.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	private static final String nome = "nometeste";
	private static final String email = "teste@teste.com.br";
	
	@Before
	public void setUp() throws Exception{
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		this.usuarioRepository.save(usuario);
	}
	
	@After
	public final void tearDown() {
			this.usuarioRepository.deleteAll();	
	}
	
	@Test
	public void testBuscarPorNome() {
		Usuario usuario = this.usuarioRepository.findByNome(nome);
		assertEquals(nome,usuario.getNome());		
	}
	
	@Test
	public void testBuscarPorEmail() {
		Usuario usuario = this.usuarioRepository.findByEmail(email);
		assertEquals(email,usuario.getEmail());		
	}
	
	@Test
	public void testBuscarPorNomeEEmail() {
		Usuario usuario = this.usuarioRepository.findByNomeOrEmail(nome,email);
		assertNotNull(usuario);
	}
	
	@Test
	public void buscaUsuarioPorNomeComEmailInvalido() {
		
		Usuario usuario = this.usuarioRepository.findByNomeOrEmail(nome, "dfsdsdasd");
		assertNotNull(usuario);
	}
	
	@Test
	public void buscaUsuarioPorNomeECpfComNomeInvalido() {
		
		Usuario usuario = this.usuarioRepository.findByNomeOrEmail("sdasdasda",email);
		assertNotNull(usuario);
	}

}

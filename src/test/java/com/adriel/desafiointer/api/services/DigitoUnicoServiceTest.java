package com.adriel.desafiointer.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.adriel.desafiointer.api.entities.DigitoUnico;
import com.adriel.desafiointer.api.repositories.DigitoUnicoRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DigitoUnicoServiceTest {
	
	@MockBean
	private DigitoUnicoRepository digitoUnicoRepository;

	@Autowired
	private DigitoUnicoService digitoUnicoService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.digitoUnicoRepository.findByUsuarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
		.willReturn(new PageImpl<DigitoUnico>(new ArrayList<DigitoUnico>()));
		BDDMockito.given(this.digitoUnicoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new DigitoUnico()));
		BDDMockito.given(this.digitoUnicoRepository.save(Mockito.any(DigitoUnico.class))).willReturn(new DigitoUnico());
	}

	@Test
	public void testBuscarDigitoUnicoPorUsuarioId() {
		Page<DigitoUnico> digit = this.digitoUnicoService.buscarPorUsuarioId(1L, PageRequest.of(0, 10));

		assertNotNull(digit);
	}

	@Test
	public void testBuscarDigitoUnicoPorId() {
		Optional<DigitoUnico> digit = this.digitoUnicoService.buscarPorId(1L);

		assertTrue(digit.isPresent());
	}

	@Test
	public void testPersistirDigitoUnico() {
		DigitoUnico digit = this.digitoUnicoService.persistir(new DigitoUnico());

		assertNotNull(digit);
	}

}

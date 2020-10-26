package com.adriel.desafiointer.api.controllers;

import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adriel.desafiointer.api.dto.ChaveDto;
import com.adriel.desafiointer.api.entities.Chave;
import com.adriel.desafiointer.api.entities.Usuario;
import com.adriel.desafiointer.api.response.Response;
import com.adriel.desafiointer.api.security.Criptografia;
import com.adriel.desafiointer.api.services.ChaveService;
import com.adriel.desafiointer.api.services.UsuarioService;

@RestController
@RequestMapping("/api/chaves")
@CrossOrigin(origins = "*") 
public class ChaveController {
	
	private static final Logger log = LoggerFactory.getLogger(DigitoUnicoController.class);
	
	@Autowired
	private ChaveService chaveService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private Optional<Usuario> usuarioAssociado;
	
	/**
	 * Adiciona chave pública
	 * 
	 * @param chave
	 * @param result
	 * @return ResponseEntity<Response<DigitoUnicoDto>>
	 * @throws ParseException 
	 */
	@PostMapping(value = "/enviachavepublica")
	public ResponseEntity<Response<ChaveDto>> adicionar(@Valid @RequestBody ChaveDto chaveDto,
			BindingResult result) throws ParseException {
		
		log.info("Persistindo chave unico: {}", chaveDto.toString());
		Response<ChaveDto> response = new Response<ChaveDto>();
		validarUsuario(chaveDto, result);
		Chave chave = new Chave();
		try {
			chave = this.converterDtoParaChave(chaveDto, result);
		} catch (Exception e) {
			log.error("Erro persistir chave pública: {}", e.getMessage());
		}

		if (result.hasErrors()) {
			log.error("Erro validando chave a ser salva: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		if(chave != null) {
			chave = this.chaveService.persistir(chave);
			response.setData(this.converterChaveDto(chave));
		}
		
		criptografaDadosUsuarioComChaveEAtualiza(chave);
		
		return ResponseEntity.ok(response); 
	}
	
	private Chave converterDtoParaChave(ChaveDto chaveDto, BindingResult result) throws Exception {
		Chave chave = new Chave();
		Long idChave = chaveDto.getUsuarioId();
		
		if(idChave != null) {
			chave.setUsuario(new Usuario());
			chave.getUsuario().setId(chaveDto.getUsuarioId());
		}
		chave.setChave(chaveDto.getChave());

		return chave;
	}
	
	/**
	 * Converte uma entidade chave para seu respectivo DTO.
	 * 
	 * @param chave
	 * @return Chave
	 */
	private ChaveDto converterChaveDto(Chave chave) {
		
		ChaveDto chaveDto = new ChaveDto();
		chaveDto.setUsuarioId(chave.getUsuario().getId());
		chaveDto.setId(chave.getId());
		chaveDto.setChave(chave.getChave());

		return chaveDto;
	}
	
	/**
	 * Valida um usuário, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param chaveDto
	 * @param result
	 */
	private void validarUsuario(ChaveDto chaveDto, BindingResult result) {

		if(chaveDto.getUsuarioId() != null) {
			log.info("Validando usuário id {}: ", chaveDto.getUsuarioId());
			usuarioAssociado = this.usuarioService.buscarPorId(chaveDto.getUsuarioId());
			if (!usuarioAssociado.isPresent()) {
				result.addError(new ObjectError("Usuário", "Usuário não encontrado. ID inexistente."));
			}
		}
	}
	
	private void criptografaDadosUsuarioComChaveEAtualiza(Chave chave) {

		Criptografia cripto = new Criptografia();
		Usuario usuario = usuarioAssociado.get();
		if(usuario!= null) {
			String nome = usuario.getNome();
			String email = usuario.getEmail();
			
			String nomeCriptografado = cripto.encryptData(nome, chave.getChave());
			String emailCriptografado =cripto.encryptData(email, chave.getChave());
			
			usuario.setNome(nomeCriptografado);
			usuario.setEmail(emailCriptografado);
			
			usuarioService.persistir(usuario);
		}
		
	}
	
}

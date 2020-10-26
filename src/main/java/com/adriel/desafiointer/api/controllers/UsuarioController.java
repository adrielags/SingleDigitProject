package com.adriel.desafiointer.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adriel.desafiointer.api.services.UsuarioService;
import com.adriel.desafiointer.api.dto.UsuarioDto;
import com.adriel.desafiointer.api.entities.Usuario;
import com.adriel.desafiointer.api.response.Response;


@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*") // está assim para fins de teste - geralmente tem que ser definido pra não vir de qualquer lugar
public class UsuarioController {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Cadastra um usuário no sistema.
	 * 
	 * @param cadastroUsuario
	 * @param result
	 * @return ResponseEntity<Response<CadastroPJDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping(value = "/cadastrar")
	public ResponseEntity<Response<UsuarioDto>> cadastrar(@Valid @RequestBody UsuarioDto cadastroUsuario,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando usuario: {}", cadastroUsuario.toString());
		Response<UsuarioDto> response = new Response<UsuarioDto>();

		validarDadosExistentes(cadastroUsuario, result);
		Usuario usuario = this.converterDtoParaUsuario(cadastroUsuario);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.persistir(usuario);

		response.setData(this.converterUsuarioDto(usuario));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um usuário
	 * 
	 * @param id
	 * @param usuarioDto
	 * @param result
	 * @return ResponseEntity<Response<UsuarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody UsuarioDto usuarioDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando funcionário: {}", usuarioDto.toString());
		Response<UsuarioDto> response = new Response<UsuarioDto>();

		Optional<Usuario> usuario = this.usuarioService.buscarPorId(id);
		if (!usuario.isPresent()) {
			result.addError(new ObjectError("usuario", "Usuário não encontrado."));
		}

		this.atualizarDadosUsuario(usuario.get(), usuarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando funcionário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.persistir(usuario.get());
		
		response.setData(this.converterUsuarioDto(usuario.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove um usuario por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Usuario>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo usuário: {}", id);
		Response<String> response = new Response<String>();
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(id);

		if (!usuario.isPresent()) {
			log.info("Erro ao remover devido ao usuário ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover usuário. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Retorna a usuario por id.
	 * 
	 * @param funcionarioId
	 * @return ResponseEntity<Response<LancamentoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDto>> retornaFuncionarioPorId(@PathVariable("id") Long id){
		log.info("Pesquisando usuário: {}", id);
		Response<UsuarioDto> response = new Response<UsuarioDto>();
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(id);

		response.setData(this.converterUsuarioDto(usuario.get()));
		return ResponseEntity.ok(response);
	}
		
	/**
	 * Verifica se a usuario existe na base de dados.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 */
	private void validarDadosExistentes(UsuarioDto cadastroUsuarioDto, BindingResult result) {
		this.usuarioService.buscarPorEmail(cadastroUsuarioDto.getEmail())
				.ifPresent(emp -> result.addError(new ObjectError("usuario", "Email de usuário já existente.")));
		
	}
	
	/**
	 * Converte os dados do DTO para usuario.
	 * 
	 * @param cadastroUsuarioDto
	 * @return Usuario
	 */
	private Usuario converterDtoParaUsuario(UsuarioDto cadastroUsuarioDto) {
		Usuario user = new Usuario();
		user.setNome(cadastroUsuarioDto.getNome());
		user.setEmail(cadastroUsuarioDto.getEmail());

		return user;
	}
	
	/**
	 * Popula o DTO de cadastro com os dados do usuário.
	 * 
	 * @param usuario
	 * @return CadastroUsuarioDto
	 */
	private UsuarioDto converterUsuarioDto(Usuario usuario) {
		UsuarioDto cadastroUsuarioDto = new UsuarioDto();
		cadastroUsuarioDto.setId(usuario.getId());
		cadastroUsuarioDto.setNome(usuario.getNome());
		cadastroUsuarioDto.setEmail(usuario.getEmail());
		
		return cadastroUsuarioDto;
	}
	
	/**
	 * Atualiza os dados do usuário com base nos dados encontrados no DTO.
	 * 
	 * @param funcionario
	 * @param funcionarioDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizarDadosUsuario(Usuario usuario, UsuarioDto usuarioDto, BindingResult result)
			throws NoSuchAlgorithmException {
		
			usuario.setNome(usuarioDto.getNome());
	
			if (!usuario.getEmail().equals(usuarioDto.getEmail())) {
				this.usuarioService.buscarPorEmail(usuarioDto.getEmail())
						.ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
				usuario.setEmail(usuarioDto.getEmail());
			}
			
	}

}

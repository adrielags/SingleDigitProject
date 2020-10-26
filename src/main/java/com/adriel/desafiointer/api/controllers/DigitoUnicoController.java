package com.adriel.desafiointer.api.controllers;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adriel.desafiointer.api.dto.DigitoUnicoDto;
import com.adriel.desafiointer.api.entities.DigitoUnico;
import com.adriel.desafiointer.api.entities.Usuario;
import com.adriel.desafiointer.api.response.Response;
import com.adriel.desafiointer.api.services.DigitoUnicoService;
import com.adriel.desafiointer.api.services.UsuarioService;
import com.adriel.desafiointer.utils.DigitoUnicoMathUtils;


@RestController
@RequestMapping("/api/digitounico")
@CrossOrigin(origins = "*")
public class DigitoUnicoController {	
	private static final Logger log = LoggerFactory.getLogger(DigitoUnicoController.class);
	
	private final int qtdPorPagina = 25;
	
	@Autowired
	private DigitoUnicoService digitoUnicoService;

	@Autowired
	private UsuarioService usuarioService;
	
	
	/**
	 * Retorna a listagem de digitos únicos de um usuário.
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<DigitoUnicoDto>>
	 */
	@GetMapping(value = "/usuario/{usuarioId}")
	public ResponseEntity<Response<Page<DigitoUnicoDto>>> listarPorUsuarioId(
			@PathVariable("usuarioId") Long usuarioId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando digitos únicos por ID do usuário: {}, página: {}", usuarioId, pag);
		Response<Page<DigitoUnicoDto>> response = new Response<Page<DigitoUnicoDto>>();

		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<DigitoUnico> digitos = this.digitoUnicoService.buscarPorUsuarioId(usuarioId, pageRequest);
		Page<DigitoUnicoDto> digitosDto = digitos.map(lancamento -> this.converterDigitoUnicoDto(lancamento));

		response.setData(digitosDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Calcula e adiciona dígito único
	 * 
	 * @param digitoDto
	 * @param result
	 * @return ResponseEntity<Response<DigitoUnicoDto>>
	 * @throws ParseException 
	 */
	@PostMapping(value = "/calcularDigitoUnico")
	public ResponseEntity<Response<DigitoUnicoDto>> adicionar(@Valid @RequestBody DigitoUnicoDto digitoUnicoDto,
			BindingResult result) throws ParseException {
		log.info("Calculando digito unico: {}", digitoUnicoDto.toString());
		Response<DigitoUnicoDto> response = new Response<DigitoUnicoDto>();
		validarUsuario(digitoUnicoDto, result);
		DigitoUnico digitoUnico = new DigitoUnico();
		
		try {
			digitoUnico = this.converterDtoParaDigitoUnico(digitoUnicoDto, result, false);
		} catch (Exception e) {
			log.error("Erro calculo digito unico: {}", e.getMessage());
		}

		if (result.hasErrors()) {
			log.error("Erro validando digito unico: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		if(digitoUnico != null) {
			digitoUnico = this.digitoUnicoService.persistir(digitoUnico);
			response.setData(this.converterDigitoUnicoDto(digitoUnico));
		}
		return ResponseEntity.ok(response); 
	}
	
	/**
	 * Calcula e adiciona dígito único simplificado
	 * 
	 * @param digito
	 * @param result
	 * @return ResponseEntity<Response<DigitoUnicoDto>>
	 * @throws ParseException 
	 */
	@PostMapping(value = "/calcularDigitoUnicoSimplificado")
	public ResponseEntity<Response<DigitoUnicoDto>> adicionarSimplificado(@Valid @RequestBody DigitoUnicoDto digitoUnicoDto,
			BindingResult result) throws ParseException {
		log.info("Calculando digito unico: {}", digitoUnicoDto.toString());
		Response<DigitoUnicoDto> response = new Response<DigitoUnicoDto>();
		validarUsuario(digitoUnicoDto, result);
		DigitoUnico digitoUnico = new DigitoUnico();
		
		try {
			digitoUnico = this.converterDtoParaDigitoUnico(digitoUnicoDto, result, true);
		} catch (Exception e) {
			log.error("Erro calculo digito unico: {}", e.getMessage());
		}

		if (result.hasErrors()) {
			log.error("Erro validando digito unico: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		if(digitoUnico != null) {
			digitoUnico = this.digitoUnicoService.persistir(digitoUnico);
			response.setData(this.converterDigitoUnicoDto(digitoUnico));
		}
		return ResponseEntity.ok(response); 
	}
	
	
	/**
	 * Valida um usuário, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param digitoUnicoDto
	 * @param result
	 */
	private void validarUsuario(DigitoUnicoDto digitoUnicoDto, BindingResult result) {

		if(digitoUnicoDto.getusuarioId() != null) {
			log.info("Validando usuário id {}: ", digitoUnicoDto.getusuarioId());
			Optional<Usuario> usuario = this.usuarioService.buscarPorId(digitoUnicoDto.getusuarioId());
			if (!usuario.isPresent()) {
				result.addError(new ObjectError("Usuário", "Usuário não encontrado. ID inexistente."));
			}
		}
	}
	
	/**
	 * Converte um DigitoUnicoDto para uma entidade DigitoUnico.
	 * 
	 * @param digitoUnicoDto
	 * @param result
	 * @return DigitoUnico
	 * @throws Exception 
	 */
	private DigitoUnico converterDtoParaDigitoUnico(DigitoUnicoDto digitoUnicoDto, BindingResult result, boolean simplificado) throws Exception {
		DigitoUnico digitoUnico = new DigitoUnico();
		DigitoUnicoMathUtils calculaDigitoUnico =  new DigitoUnicoMathUtils();

		String parametroN = digitoUnicoDto.getParametroN();
		int parametroK = digitoUnicoDto.getParametroK();
		Long idUsuario = digitoUnicoDto.getusuarioId();
		
		if(idUsuario!= null) {
			digitoUnico.setUsuario(new Usuario());
			digitoUnico.getUsuario().setId(digitoUnicoDto.getusuarioId());
		}
		digitoUnico.setParametroK(parametroK);
		digitoUnico.setParametroN(parametroN);
		
		BigInteger valorCalculado =  BigInteger.valueOf(0);
		
		if(!simplificado) {
			valorCalculado = calculaDigitoUnico.calculaDigitoUnico(parametroN, parametroK);
		}else {
			valorCalculado = calculaDigitoUnico.calculaDigitoUnicoSimplificado(parametroN, parametroK);
		}
		
		digitoUnico.setValorCalculado(valorCalculado.toString());

		return digitoUnico;
	}
	
	/**
	 * Converte uma entidade digitoUnico para seu respectivo DTO.
	 * 
	 * @param digitoUnico
	 * @return DigitoUnicoDto
	 */
	private DigitoUnicoDto converterDigitoUnicoDto(DigitoUnico digitoUnico) {
		
		DigitoUnicoDto digitoUnicoDto = new DigitoUnicoDto();
		digitoUnicoDto.setId(digitoUnico.getId());
		digitoUnicoDto.setParametroK(digitoUnico.getParametroK());
		digitoUnicoDto.setParametroN(digitoUnico.getParametroN());
		digitoUnicoDto.setValorCalculado(digitoUnico.getValorCalculado());
		
		Usuario usuario = digitoUnico.getUsuario();
		if(usuario!=null) {
			digitoUnicoDto.setUsuarioId(digitoUnico.getUsuario().getId());
		}

		return digitoUnicoDto;
	}
}

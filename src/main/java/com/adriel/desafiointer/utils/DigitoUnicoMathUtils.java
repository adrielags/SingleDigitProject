package com.adriel.desafiointer.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.adriel.desafiointer.api.cache.CacheLRU;

public class DigitoUnicoMathUtils {
	private static Map<String, BigInteger> cache = new CacheLRU<>(10);
	private final int MIN_K_VALUE = 1;
	private final int MAX_K_VALUE = 100000; 
	
	private Pattern padraoNumerico = Pattern.compile("-?\\d+(\\.\\d+)?");
	
	public BigInteger calculaDigitoUnico(String representacaoNumerica, int parametroK) throws Exception {
		
		
		
		if(!isNumeric(representacaoNumerica)){
			throw new Exception ("Valor de entrada não corresponde a string com representação numérica");				
		}
			
		if(!validaParametroK(parametroK)){	
			throw new Exception("Parametro k fora do intervalo");
		}
		BigInteger digitoUnico = verificaCalculoCache(parametroK,representacaoNumerica);
		
		if(digitoUnico != null) {
			return digitoUnico;
		}
		
		digitoUnico = BigInteger.valueOf(0);
		
		List<String>listaNumerosConcatenados = new ArrayList<String>(); // Representação com tamanho maior que length de 2147483647
		
		String novoNumeroP = "";
		
		for (int i = 0; i< parametroK; i++) {
			int estimativaTamanho = novoNumeroP.length() + representacaoNumerica.length();
			
			if(estimativaTamanho < 0 || estimativaTamanho <novoNumeroP.length() || estimativaTamanho < representacaoNumerica.length()) {
				listaNumerosConcatenados.add(novoNumeroP); //verifica se ultrapassou limite de armazenamento em string
				//se sim adiciona uma nova string em listas de strings
				break;
			}
			
			novoNumeroP += representacaoNumerica;
		}
		
		listaNumerosConcatenados.add(novoNumeroP);
		
		
		for(int i=0; i < listaNumerosConcatenados.size(); i++) {
			BigInteger soma = somaDigitosRepresentacaoNumerica(listaNumerosConcatenados.get(i));
			digitoUnico = digitoUnico.add(soma);
		}
		
		armazenaEmCache(parametroK,representacaoNumerica,digitoUnico);
		return digitoUnico;
	}
	
	public BigInteger calculaDigitoUnicoSimplificado(String representacaoNumerica, int parametroK) throws Exception {
		
		
		if(!isNumeric(representacaoNumerica)){
			throw new Exception ("Valor de entrada não corresponde a string com representação numérica");				
		}
			
		if(!validaParametroK(parametroK)){	
			throw new Exception("Parametro k fora do intervalo");
		}
		
		BigInteger digitoUnico = verificaCalculoCache(parametroK,representacaoNumerica);
		
		if(digitoUnico != null) {
			return digitoUnico;
		}
		
		digitoUnico = BigInteger.valueOf(0);
		
		/*Concatenar digitos para um número muito grandes é muito demorado
		 * e logicamente quando for realizar o somatório isso equivale a multiplicar o número obtido por somaDigitosRepresentacaoNumerica
		 * multiplicado por K por questões de performace para números grandes mudei a lógica aqui*/
			
		
			BigInteger soma = somaDigitosRepresentacaoNumerica(representacaoNumerica);
			digitoUnico = digitoUnico.add(soma);

		
		/* A soma maior soma de 1 milhao (10E1000000) de digitos vai ser 9 * 1 000 000 = 9 000 000 e 
		 * e o maior valor obtido será 900 000 000 000 com o k em 10E5 portanto o número poderá ser armazenado
		 * assim que a operação for realizada */
		
		digitoUnico = digitoUnico.multiply(BigInteger.valueOf(parametroK));
		
		armazenaEmCache(parametroK,representacaoNumerica,digitoUnico);
		return digitoUnico; 
	}
	
	private BigInteger somaDigitosRepresentacaoNumerica(String representacaoNumerica) {
		
		BigInteger soma = BigInteger.valueOf(0);
		
		for(char c : representacaoNumerica.replaceAll("\\D", "").toCharArray()) {
			int digit = c - '0';
			BigInteger valorAtual = BigInteger.valueOf(digit);
			soma = soma.add(valorAtual);
		}	
		return soma;
	}

	private boolean isNumeric(String strNum) {
		
		if(strNum == null) {
			return false;
		}
		return padraoNumerico.matcher(strNum).matches();
	}
	
	private boolean validaParametroK(int parametroK) {
		
		if(MIN_K_VALUE <= parametroK && parametroK <= MAX_K_VALUE) {
			return true;
		}
		return false;
	}
	
	private void armazenaEmCache(int parametroK, String parametroN, BigInteger resultado) {	
		String key = parametroK +"k"+ parametroN;
		cache.put(key, resultado);	
	}
	
	private BigInteger verificaCalculoCache(int parametroK,String parametroN){
		String key =  parametroK +"k"+ parametroN;
		BigInteger resultado = cache.get(key);
		return resultado;
	}

}

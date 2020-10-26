package com.adriel.desafiointer.api.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.adriel.desafiointer.utils.DigitoUnicoMathUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DigitoUnicoMathUtilsTest {
	
	private DigitoUnicoMathUtils digitoUnicoCalc;
	
	@Before
	public void setUp() {
		digitoUnicoCalc = new DigitoUnicoMathUtils();
	}
	
	@Test
	public void testaCalculoDigitoUnicoRealizado() {
		
		try {	
			String parametroN = "1234";
		    int parametroK = 2;
		    BigInteger valorEsperado =  BigInteger.valueOf(20);
		    BigInteger resultado = BigInteger.valueOf(0);

		    resultado = digitoUnicoCalc.calculaDigitoUnico(parametroN, parametroK);

			assertEquals(resultado,valorEsperado);
				
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
	
	@Test
	public void testaCalculoDigitoUnicoSimplificadoRealizado() {
		
		try {	
			String parametroN = "1234";
		    int parametroK = 2;
		    BigInteger valorEsperado =  BigInteger.valueOf(20);
		    BigInteger resultado = BigInteger.valueOf(0);

		    resultado = digitoUnicoCalc.calculaDigitoUnicoSimplificado(parametroN, parametroK);

			assertEquals(resultado,valorEsperado);
				
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
	
	@Test
	public void testaCalculoDigitoUnicoSomenteUmCaracte() {
		
		try {	
			String parametroN = "9";
		    int parametroK = 1;
		    BigInteger valorEsperado =  BigInteger.valueOf(9);
		    BigInteger resultado = BigInteger.valueOf(0);

		    resultado = digitoUnicoCalc.calculaDigitoUnico(parametroN, parametroK);

			assertEquals(resultado,valorEsperado);
				
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
	
}

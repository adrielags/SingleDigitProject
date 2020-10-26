package com.adriel.desafiointer.api.security;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CriptografiaTest {
	
	Criptografia criptografia;

	@Before
	public void setUp() {
		criptografia = new Criptografia();
	}
	
	@Test
	public void buscaChavePublica() {

		try {
			PublicKey pubKey = criptografia.recuperaChavePublicaAPI(Criptografia.PUBLIC_KEY_FILE);
			assertNotNull(pubKey);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void buscaChavePrivada() {
		try {
			PrivateKey privKey = criptografia.recuperaChavePrivadaAPI(Criptografia.PRIVATE_KEY_FILE);
			assertNotNull(privKey);
		} catch (IOException e) {
			fail(e.getMessage());
		}	
	}
	
	@Test
	public void testEncryptDecrypt() {
		
		String mensagem = "Mensagem secreta";
		PublicKey pubKey;
		try {
			pubKey = criptografia.recuperaChavePublicaAPI(Criptografia.PUBLIC_KEY_FILE);
			byte[] byte_pubkey = pubKey.getEncoded();

			String str_key = Base64.getEncoder().encodeToString(byte_pubkey);
			String criptografado = criptografia.encryptData(mensagem,str_key);
			
			String resultado = criptografia.decryptData(criptografado);
			
			assertEquals(resultado,mensagem);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
}

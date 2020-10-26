package Principal;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cliente {
	
 public static final String PUBLIC_KEY_FILE = "publicClient.key";
 public static final String PRIVATE_KEY_FILE = "privateClient.key";
	
 public static void main(String [] args) throws IOException {
	 
	 
		File publicKey = new File(PUBLIC_KEY_FILE);
		File privateFile = new File(PRIVATE_KEY_FILE);
		
		if(!publicKey.isFile() || !privateFile.isFile() ) { 
			 try {
				geraParChavesCliente();
			} catch (Exception e ) {
				e.printStackTrace();
			} 
		}
		
	 PublicKey publica = recuperaChavePublicaCliente(PUBLIC_KEY_FILE);
	 
	//Convertendo pra byte          
	 byte[] byte_pubkey = publica.getEncoded();
	 System.out.println("\nBYTE KEY::: " + byte_pubkey);

	 //convertendo para  String 
	 String str_key = Base64.getEncoder().encodeToString(byte_pubkey);

	 System.out.println("\nCHAVE PÚBLICA::" + str_key);
	 
	 while(true) {
		 
		 BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		 System.out.print("\nEntre valor a ser descriptografado:");
		 String s;
		 
			try {
				s = br.readLine();
				if(s.equals("sair")) {
					break;
				}
				String resultado = "";
				try {
					resultado = decryptData(s);
				} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
						| NoSuchPaddingException e) {
					e.printStackTrace();
				}
				System.out.print("Informação Descriptografada:" + resultado + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}	 
	 }

 }
 
	public static String decryptData(String data) throws IllegalBlockSizeException, BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
	
		 final String cipherName = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";  
		 byte[] decryptedBytes = null;
		 byte[] dataBytes = Base64.getDecoder().decode(data);
		 
		 PrivateKey privKey = recuperaChavePrivadaCliente(PRIVATE_KEY_FILE); 
	     Cipher cipher = Cipher.getInstance(cipherName);
		 cipher.init(Cipher.DECRYPT_MODE, privKey);
	     decryptedBytes = cipher.doFinal(dataBytes);
	     decryptedBytes = Base64.getDecoder().decode(decryptedBytes);
	     String decryptedString = new String(decryptedBytes);   
	     return decryptedString;

	}
 
   private static void  geraParChavesCliente() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        final KeyPairGenerator gerador = KeyPairGenerator.getInstance("RSA");
        gerador.initialize(2048);
        final KeyPair par = gerador.generateKeyPair();
        final PublicKey chavePublica = par.getPublic();
        final PrivateKey chavePrivada = par.getPrivate();	
        
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(chavePublica, RSAPublicKeySpec.class);
        RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(chavePrivada, RSAPrivateKeySpec.class);
        
        salvarChave(PUBLIC_KEY_FILE,rsaPubKeySpec.getModulus(),rsaPubKeySpec.getPublicExponent());
        salvarChave(PRIVATE_KEY_FILE,rsaPrivKeySpec.getModulus(),rsaPrivKeySpec.getPrivateExponent());
        
	}
   
	private static void salvarChave(String nomeArquivo, BigInteger mod,BigInteger exp) throws IOException {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(nomeArquivo);
			oos = new ObjectOutputStream(new BufferedOutputStream(fos));
			oos.writeObject(mod);
			oos.writeObject(exp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			if(oos!= null) {
				oos.close();
				if(fos != null) {
					fos.close();
				}
			}
			
		}
	}
	
	
	public static PrivateKey recuperaChavePrivadaCliente(String nomeArquivo) throws IOException {
		
		File privateFile = new File(PRIVATE_KEY_FILE);
	
		if(!privateFile.isFile() ) { 
			 try {
				geraParChavesCliente();
			} catch (Exception e ) {
				e.printStackTrace();
			} 
		}
	
	  FileInputStream fis = null;
	  ObjectInputStream ois = null;
	  
	try {
		  fis = new  FileInputStream(new File(nomeArquivo));
		  ois = new ObjectInputStream(fis);
		  
		  BigInteger modulus = (BigInteger) ois.readObject();
		  BigInteger exponent = (BigInteger) ois.readObject();
		  
		  RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus,exponent);
		  KeyFactory fact = KeyFactory.getInstance("RSA");
		  PrivateKey privateKey = fact.generatePrivate(rsaPrivateKeySpec);
		  
		  return privateKey;  
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		if(ois!= null) {
			ois.close();
			if(fis != null) {
				fis.close();
			}
		}
	}
	
	    return null;	
   }
	
	
	public static PublicKey recuperaChavePublicaCliente(String nomeArquivo) throws IOException {
		
		File publicFile = new File(PUBLIC_KEY_FILE);
		
		if(!publicFile.isFile() ) { 
			 try {
				geraParChavesCliente();
			} catch (Exception e ) {
				e.printStackTrace();
			} 
		}
		
		  FileInputStream fis = null;
		  ObjectInputStream ois = null;
		  
		try {
			  fis = new  FileInputStream(new File(nomeArquivo));
			  ois = new ObjectInputStream(fis);
			  
			  BigInteger modulus = (BigInteger) ois.readObject();
			  BigInteger exponent = (BigInteger) ois.readObject();
			  
			  RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus,exponent);
			  KeyFactory fact = KeyFactory.getInstance("RSA");
			  PublicKey publicKey = fact.generatePublic(rsaPublicKeySpec);
			  
			  return publicKey;  
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ois!= null) {
				ois.close();
				if(fis != null) {
					fis.close();
				}
			}
		}
		
		return null;	
	}

	
}

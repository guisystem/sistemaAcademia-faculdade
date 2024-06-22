package servico;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Criptografar {

	private static final String chave = "6ZyWuSqOmKiGeCa1";

    public static String criptografar(String dado){
    	byte[] senhaCriptografada = null;
    	
    	try{
    		SecretKeySpec chaveSecreta = new SecretKeySpec(chave.getBytes(), "AES");
    		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    		cipher.init(Cipher.ENCRYPT_MODE, chaveSecreta);
    		senhaCriptografada = cipher.doFinal(dado.getBytes());
    	}catch (Exception e) {
    		
		}

    	return Base64.getEncoder().encodeToString(senhaCriptografada);    		
    }

    public static String descriptografar(String dadoCriptografado) {
    	byte[] senhaDescriptografada = null;
    	
    	try {
    		SecretKeySpec chaveSecreta = new SecretKeySpec(chave.getBytes(), "AES");
    		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
    		cipher.init(Cipher.DECRYPT_MODE, chaveSecreta);
    		senhaDescriptografada = cipher.doFinal(Base64.getDecoder().decode(dadoCriptografado));
    	}catch (Exception e) {

    	}

    	return new String(senhaDescriptografada);   		
    }
	
}

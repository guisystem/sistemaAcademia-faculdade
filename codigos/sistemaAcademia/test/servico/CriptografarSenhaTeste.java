package servico;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CriptografarSenhaTeste {

	@Test
	void testarDescriptografarSenha() {
		String senhaOriginal = "Senha";
		String senhaCriptografada = Criptografar.criptografar(senhaOriginal);
		String senhaDescriptografada = Criptografar.descriptografar(senhaCriptografada);
		
		assertEquals("Senha", senhaDescriptografada);
	}
}

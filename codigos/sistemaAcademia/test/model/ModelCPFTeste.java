package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.text.DefaultFormatterFactory;

import org.junit.jupiter.api.Test;

class ModelCPFTeste {

	ModelCPF cpf = new ModelCPF("00000000000");
	
	@Test
	void testarIsCpfFalso() {
		boolean isNotCpf0 = cpf.isCPF();
		assertFalse(isNotCpf0);
		
		cpf = new ModelCPF("11111111111");
		boolean isNotCpf1 = cpf.isCPF();
		assertFalse(isNotCpf1);
		
		cpf = new ModelCPF("22222222222");
		boolean isNotCpf2 = cpf.isCPF();
		assertFalse(isNotCpf2);
		
		cpf = new ModelCPF("33333333333");
		boolean isNotCpf3 = cpf.isCPF();
		assertFalse(isNotCpf3);
		
		cpf = new ModelCPF("44444444444");
		boolean isNotCpf4 = cpf.isCPF();
		assertFalse(isNotCpf4);
		
		cpf = new ModelCPF("55555555555");
		boolean isNotCpf5 = cpf.isCPF();
		assertFalse(isNotCpf5);
		
		cpf = new ModelCPF("66666666666");
		boolean isNotCpf6 = cpf.isCPF();
		assertFalse(isNotCpf6);
		
		cpf = new ModelCPF("77777777777");
		boolean isNotCpf7 = cpf.isCPF();
		assertFalse(isNotCpf7);
		
		cpf = new ModelCPF("88888888888");
		boolean isNotCpf8 = cpf.isCPF();
		assertFalse(isNotCpf8);
		
		cpf = new ModelCPF("99999999999");
		boolean isNotCpf9 = cpf.isCPF();
		assertFalse(isNotCpf9);

		cpf = new ModelCPF("999999999912");
		boolean isNotCpf12 = cpf.isCPF();
		assertFalse(isNotCpf12);
	}
	
	@Test
	void testarIsCpfFalso2() {
		cpf = new ModelCPF("12345678900");
		boolean isCpf = cpf.isCPF();
		assertFalse(isCpf);
	}

	@Test
	void testarIsCpfVerdade() {
		cpf = new ModelCPF("45415958428"); /** NOTE: CPF gerado automaticamente em um sistema WEB */
		boolean isCpf = cpf.isCPF();
		assertTrue(isCpf);
	}
	
	@Test
	void testarGetCpfSemMascara() {
		cpf = new ModelCPF("45415958428"); /** NOTE: CPF gerado automaticamente em um sistema WEB */
		assertEquals("45415958428", cpf.getCPF(false));
	}
	
	@Test
	void testarGetCpfComMascara() {
		cpf = new ModelCPF("45415958428"); /** NOTE: CPF gerado automaticamente em um sistema WEB */
		assertEquals("454.159.584-28", cpf.getCPF(true));
	}
	
	@Test
    public void testarGetFormatSuccess() {
        DefaultFormatterFactory factory = ModelCPF.getFormat();
        assertNotNull(factory);
    }

}

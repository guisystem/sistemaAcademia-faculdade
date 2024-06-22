package model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class ModelCoresTeste {
	
	@Test
	void testarTonsDeCorPrimaria() {
		assertEquals(Color.BLACK, ModelCores.tonsDeCorPrimaria(Color.BLACK).get(0));
		assertEquals(Color.BLUE, ModelCores.tonsDeCorPrimaria(Color.BLUE).get(0));
		assertEquals(Color.GREEN, ModelCores.tonsDeCorPrimaria(Color.GREEN).get(0));
		assertEquals(Color.RED, ModelCores.tonsDeCorPrimaria(Color.RED).get(0));
		assertEquals(Color.WHITE, ModelCores.tonsDeCorPrimaria(Color.WHITE).get(0));
		assertEquals(Color.YELLOW, ModelCores.tonsDeCorPrimaria(Color.YELLOW).get(0));
		assertEquals(new Color(225, 225, 225), ModelCores.tonsDeCorPrimaria(new Color(225, 225, 225)).get(0));
		assertEquals(new Color(255, 140, 0), ModelCores.tonsDeCorPrimaria(new Color(255, 140, 0)).get(0));
		assertEquals(new Color(140, 70, 35), ModelCores.tonsDeCorPrimaria(new Color(140, 70, 35)).get(0));
		assertEquals(new Color(255, 0, 255), ModelCores.tonsDeCorPrimaria(new Color(255, 0, 255)).get(0));
		assertEquals(new Color(170, 0, 255), ModelCores.tonsDeCorPrimaria(new Color(170, 0, 255)).get(0));
		
	}

	@Test
	void testarTonsDeCorSecundaria() {
		assertEquals(Color.BLACK, ModelCores.tonsDeCorSecundaria(Color.BLACK).get(0));
		assertEquals(Color.BLUE, ModelCores.tonsDeCorSecundaria(Color.BLUE).get(0));
		assertEquals(Color.GREEN, ModelCores.tonsDeCorSecundaria(Color.GREEN).get(0));
		assertEquals(Color.RED, ModelCores.tonsDeCorSecundaria(Color.RED).get(0));
		assertEquals(Color.WHITE, ModelCores.tonsDeCorSecundaria(Color.WHITE).get(0));
		assertEquals(Color.YELLOW, ModelCores.tonsDeCorSecundaria(Color.YELLOW).get(0));
		assertEquals(new Color(225, 225, 225), ModelCores.tonsDeCorSecundaria(new Color(225, 225, 225)).get(0));
		assertEquals(new Color(255, 140, 0), ModelCores.tonsDeCorSecundaria(new Color(255, 140, 0)).get(0));
		assertEquals(new Color(140, 70, 35), ModelCores.tonsDeCorSecundaria(new Color(140, 70, 35)).get(0));
		assertEquals(new Color(255, 0, 255), ModelCores.tonsDeCorSecundaria(new Color(255, 0, 255)).get(0));
		assertEquals(new Color(170, 0, 255), ModelCores.tonsDeCorSecundaria(new Color(170, 0, 255)).get(0));
		
	}

}

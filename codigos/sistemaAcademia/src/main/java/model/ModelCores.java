package model;

import java.awt.Color;
import java.util.ArrayList;

public class ModelCores {

	public static ArrayList<Color> tonsDeCorPrimaria(Color colorPrimaria){
		
		ArrayList<Color> coresPrimarias = new ArrayList<>();
		int mudarRed = 255;
		int mudarGreen = 255;
		int mudarBlue = 255;
		
		if(colorPrimaria.equals(Color.YELLOW)) { /** NOTE: retorna tons de amarelo */
			for(int i = 0; i < 8; i++) {
				coresPrimarias.add(new Color(mudarRed, mudarGreen, 0));
				mudarRed -= 30;
				mudarGreen -= 30;
			}
		}
		
		if(colorPrimaria.equals(Color.BLUE)) { /** NOTE: retorna tons de azul */
			for (int i = 0; i < 8; i++) {
				coresPrimarias.add(new Color(0, 0, mudarBlue));
				mudarBlue -= 30;
			}
		}
		
		if(colorPrimaria.equals(Color.WHITE)) { /** NOTE: retorna tons de branco */
			coresPrimarias.add(new Color(255, 255, 255));
		}
		
		if(colorPrimaria.equals(new Color(225, 225, 225))) { /** NOTE: retorna tons de cinza */
			mudarRed = 225;
			mudarGreen = 225;
			mudarBlue = 225;
			for (int i = 0; i < 7; i++) {
				coresPrimarias.add(new Color(mudarRed, mudarGreen, mudarBlue));
				mudarRed -= 30;
				mudarGreen -= 30;
				mudarBlue -= 30;
			}
		}
		
		if(colorPrimaria.equals(new Color(255, 140, 0))) { /** NOTE: retorna tons de laranja */
			mudarGreen = 140;
			for (int i = 0; i < 5; i++) {
				coresPrimarias.add(new Color(255, mudarGreen, 0));
				mudarGreen -= 20;
			}
		}
		
		if(colorPrimaria.equals(new Color(140, 70, 35))) { /** NOTE: retorna tons de marrom */
			mudarRed = 140;
			mudarGreen = 70;
			mudarBlue = 35;
			for (int i = 0; i < 6; i++) {
				coresPrimarias.add(new Color(mudarRed, mudarGreen, mudarBlue));
				mudarRed -= 20;
				mudarGreen -= 10;
				mudarBlue -= 5;
			}
		}
		
		if(colorPrimaria.equals(Color.BLACK)) { /** NOTE: retorna tons de preto */
			coresPrimarias.add(new Color(0, 0, 0));
		}
		
		if(colorPrimaria.equals(new Color(255, 0, 255))) { /** NOTE: retorna tons de rosa */
			for (int i = 0; i < 7; i++) {
				coresPrimarias.add(new Color(255, 0, mudarBlue));
				mudarBlue -= 30;
			}
		}
		
		if(colorPrimaria.equals(new Color(170, 0, 255))) { /** NOTE: retorna tons de roxo */
			mudarRed = 170;
			mudarBlue = 255;
			for (int i = 0; i < 7; i++) {
				coresPrimarias.add(new Color(mudarRed, 0, mudarBlue));
				mudarRed -= 20;
				mudarBlue -= 20;
			}
		}
		
		if(colorPrimaria.equals(Color.GREEN)) { /** NOTE: retorna tons de verde */
			for (int i = 0; i < 8; i++) {
				coresPrimarias.add(new Color(0, mudarGreen, 0));
				mudarGreen -= 30;
			}
		}
		
		if(colorPrimaria.equals(Color.RED)) { /** NOTE: retorna tons de vermelho */
			for (int i = 0; i < 8; i++) {
				coresPrimarias.add(new Color(mudarRed, 0, 0));
				mudarRed -= 30;
			}
		}
		
		return coresPrimarias;
	}
	
	
	public static ArrayList<Color> tonsDeCorSecundaria(Color corSecundaria) {
		
		ArrayList<Color> coresSecundarias = new ArrayList<>();
		int mudarRed = 255;
		int mudarGreen = 255;
		int mudarBlue = 255;
		
		if(corSecundaria.equals(Color.YELLOW)) {
			for(int i = 0; i < 8; i++) {
				coresSecundarias.add(new Color(mudarRed, mudarGreen, 0));
				mudarRed -= 30;
				mudarGreen -= 30;
			}
		}
		
		if(corSecundaria.equals(Color.BLUE)) {
			for (int i = 0; i < 8; i++) {
				coresSecundarias.add(new Color(0, 0, mudarBlue));
				mudarBlue -= 30;
			}
		}
		
		if(corSecundaria.equals(Color.WHITE)) {
			coresSecundarias.add(new Color(255, 255, 255));
		}
		
		if(corSecundaria.equals(new Color(225, 225, 225))) {
			mudarRed = 225;
			mudarGreen = 225;
			mudarBlue = 225;
			for (int i = 0; i < 7; i++) {
				coresSecundarias.add(new Color(mudarRed, mudarGreen, mudarBlue));
				mudarRed -= 30;
				mudarGreen -= 30;
				mudarBlue -= 30;
			}
		}
		
		if(corSecundaria.equals(new Color(255, 140, 0))) {
			mudarGreen = 140;
			for (int i = 0; i < 5; i++) {
				coresSecundarias.add(new Color(255, mudarGreen, 0));
				mudarGreen -= 20;
			}
		}
		
		if(corSecundaria.equals(new Color(140, 70, 35))) {
			mudarRed = 140;
			mudarGreen = 70;
			mudarBlue = 35;
			for (int i = 0; i < 6; i++) {
				coresSecundarias.add(new Color(mudarRed, mudarGreen, mudarBlue));
				mudarRed -= 20;
				mudarGreen -= 10;
				mudarBlue -= 5;
			}
		}
		
		if(corSecundaria.equals(Color.BLACK)) {
			coresSecundarias.add(new Color(0, 0, 0));
		}
		
		if(corSecundaria.equals(new Color(255, 0, 255))) {
			for (int i = 0; i < 7; i++) {
				coresSecundarias.add(new Color(255, 0, mudarBlue));
				mudarBlue -= 30;
			}
		}
		
		if(corSecundaria.equals(new Color(170, 0, 255))) {
			mudarRed = 170;
			mudarBlue = 255;
			for (int i = 0; i < 7; i++) {
				coresSecundarias.add(new Color(mudarRed, 0, mudarBlue));
				mudarRed -= 20;
				mudarBlue -= 20;
			}
		}
		
		if(corSecundaria.equals(Color.GREEN)) {
			for (int i = 0; i < 8; i++) {
				coresSecundarias.add(new Color(0, mudarGreen, 0));
				mudarGreen -= 30;
			}
		}
		
		if(corSecundaria.equals(Color.RED)) {
			for (int i = 0; i < 8; i++) {
				coresSecundarias.add(new Color(mudarRed, 0, 0));
				mudarRed -= 30;
			}
		}
		
		return coresSecundarias;
	}
}

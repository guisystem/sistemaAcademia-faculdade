package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelConfiguracaoTeste {

	@SuppressWarnings("unused")
	private ModelConfiguracao configuracaoConstructor = new ModelConfiguracao();
	private ModelConfiguracao configuracao;
	
	@BeforeEach
	void iniciarTeste() {
		configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Terça", "00:00", "00:00", "Branco", "Preto", new ModelUsuario());
	}

	@Test
	void construtorHoraExcecao() {
		configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Terça", "", "", "Branco", "Preto", new ModelUsuario());
		assertEquals(null, configuracao.getHoraDeAbrir());
		assertEquals(null, configuracao.getHoraDeFechar());
	}
	
	@Test
	void testarSetId() {
		configuracao.setId(2);
		assertEquals(2, configuracao.getId());
	}
	
	@Test
	void testarSetNomeAcademia() {
		configuracao.setNomeDaAcademia("NomeAcademia");
		assertEquals("NomeAcademia", configuracao.getNomeDaAcademia());
	}
	
	@Test
	void testarGetDiaAbrir() {
		assertEquals(DayOfWeek.MONDAY, configuracao.getDiaDeAbrirDate());
		
		configuracao.setDiaDeAbrir("Terça");
		assertEquals(DayOfWeek.TUESDAY, configuracao.getDiaDeAbrirDate());
		
		configuracao.setDiaDeAbrir("Quarta");
		assertEquals(DayOfWeek.WEDNESDAY, configuracao.getDiaDeAbrirDate());
		
		configuracao.setDiaDeAbrir("Quinta");
		assertEquals(DayOfWeek.THURSDAY, configuracao.getDiaDeAbrirDate());
		
		configuracao.setDiaDeAbrir("Sexta");
		assertEquals(DayOfWeek.FRIDAY, configuracao.getDiaDeAbrirDate());
		
		configuracao.setDiaDeAbrir("Sábado");
		assertEquals(DayOfWeek.SATURDAY, configuracao.getDiaDeAbrirDate());
		
		configuracao.setDiaDeAbrir("Domingo");
		assertEquals(DayOfWeek.SUNDAY, configuracao.getDiaDeAbrirDate());

		configuracao.setDiaDeAbrir("");
		assertEquals(null, configuracao.getDiaDeAbrirDate());
		
	}

	@Test
	void testarSetDiaAbrir() {
		configuracao.setDiaDeAbrir("Terça");
		assertEquals("Terça", configuracao.getDiaDeAbrir());
	}
	
	@Test
	void testarGetDiaFechar() {
		configuracao.setDiaDeFechar("Segunda");
		assertEquals(DayOfWeek.MONDAY, configuracao.getDiaDeFecharDate());
		
		configuracao.setDiaDeFechar("Terça");
		assertEquals(DayOfWeek.TUESDAY, configuracao.getDiaDeFecharDate());
		
		configuracao.setDiaDeFechar("Quarta");
		assertEquals(DayOfWeek.WEDNESDAY, configuracao.getDiaDeFecharDate());
		
		configuracao.setDiaDeFechar("Quinta");
		assertEquals(DayOfWeek.THURSDAY, configuracao.getDiaDeFecharDate());
		
		configuracao.setDiaDeFechar("Sexta");
		assertEquals(DayOfWeek.FRIDAY, configuracao.getDiaDeFecharDate());
		
		configuracao.setDiaDeFechar("Sábado");
		assertEquals(DayOfWeek.SATURDAY, configuracao.getDiaDeFecharDate());
		
		configuracao.setDiaDeFechar("Domingo");
		assertEquals(DayOfWeek.SUNDAY, configuracao.getDiaDeFecharDate());

		configuracao.setDiaDeFechar("");
		assertEquals(null, configuracao.getDiaDeFecharDate());
	}
	
	@Test
	void testarSetDiaFechar() {
		configuracao.setDiaDeFechar("Sábado");
		assertEquals("Sábado", configuracao.getDiaDeFechar());
	}
	
	@Test
	void testarSetHoraAbrir() {
		Date horaAbrir = null;
		try {
			horaAbrir = new SimpleDateFormat("HH:mm").parse("20:00");
		} catch (ParseException e) {
			
		}
		
		configuracao.setHoraDeAbrir(horaAbrir);
		
		assertEquals("20:00", configuracao.getHoraDeAbrirFormatada());
	}
	
	@Test
	void testarSetHoraAbrirFormatada() {
		configuracao.setHoraDeAbrirFormatado("10:00");
		assertEquals("10:00", configuracao.getHoraDeAbrirFormatada());
	}
	
	@Test
	void testarSetHoraAbrirFormatadaExcecao() {
		configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Terça", null, "", "Branco", "Preto", new ModelUsuario());
		configuracao.setHoraDeAbrirFormatado("e0:0f");
		
		assertThrows(NullPointerException.class, () ->{
			configuracao.getHoraDeAbrirFormatada();
		});
	}
	
	@Test
	void testarSetHoraFechar() {
		Date horaFechar = null;
		try {
			horaFechar = new SimpleDateFormat("HH:mm").parse("20:00");
		} catch (ParseException e) {
			
		}
		
		configuracao.setHoraDeFechar(horaFechar);
		
		assertEquals("20:00", configuracao.getHoraDeFecharFormatada());
	}
	
	@Test
	void testarSetHoraFecharFormatada() {
		configuracao.setHoraDeFecharFormatado("10:00");
		assertEquals("10:00", configuracao.getHoraDeFecharFormatada());
	}
	
	@Test
	void testarSetHoraFecharFormatadaExcecao() {
		configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Terça", "", null, "Branco", "Preto", new ModelUsuario());
		configuracao.setHoraDeFecharFormatado("e0:0f");
		
		assertThrows(NullPointerException.class, () ->{
			configuracao.getHoraDeFecharFormatada();
		});
	}
	
	@Test
	void testarSetCorPrimaria() {
		configuracao.setCorAtualPrimaria("Amarelo");
		assertEquals("Amarelo", configuracao.getCorAtualPrimaria());
	}
	
	@Test
	void testarSetCorSecundaria() {
		configuracao.setCorAtualSecundaria("Vermelho");
		assertEquals("Vermelho", configuracao.getCorAtualSecundaria());
	}
	
	@Test
	void testarSetUsuario() {
		ModelUsuario usuarioNovo = new ModelUsuario();
		configuracao.setUsuario(usuarioNovo);
		assertEquals(usuarioNovo, configuracao.getUsuario());
	}
	
	@Test
	void testarGetCorPrimaria() {
		
		assertThrows(NullPointerException.class, () ->{
			configuracao.getCorPrimariaColor(null);
		});
		
		ModelUsuario usuarioNovo = new ModelUsuario();
		configuracao.setUsuario(usuarioNovo);
		assertEquals(Color.WHITE, configuracao.getCorPrimariaColor(usuarioNovo));

		usuarioNovo.setConfiguracao(configuracao);
		
		Color cor = Color.YELLOW;
		configuracao.setCorAtualPrimaria("Amarelo");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = Color.BLUE;
		configuracao.setCorAtualPrimaria("Azul");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = Color.WHITE;
		configuracao.setCorAtualPrimaria("Branco");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = new Color(225, 225, 225);
		configuracao.setCorAtualPrimaria("Cinza");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = new Color(255, 140, 0);
		configuracao.setCorAtualPrimaria("Laranja");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = new Color(140, 70, 35);
		configuracao.setCorAtualPrimaria("Marrom");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = Color.BLACK;
		configuracao.setCorAtualPrimaria("Preto");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = new Color(255, 0, 255);
		configuracao.setCorAtualPrimaria("Rosa");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = new Color(170, 0, 255);
		configuracao.setCorAtualPrimaria("Roxo");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = Color.GREEN;
		configuracao.setCorAtualPrimaria("Verde");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		cor = Color.RED;
		configuracao.setCorAtualPrimaria("Vermelho");
		assertEquals(cor, configuracao.getCorPrimariaColor(usuarioNovo));
		
		configuracao.setCorAtualPrimaria("N/S");
		assertEquals(null, configuracao.getCorPrimariaColor(usuarioNovo));
		
	}
	
	@Test
	void testarGetCorSecundaria() {
		assertThrows(NullPointerException.class, () ->{
			configuracao.getCorPrimariaColor(null);
		});
		
		ModelUsuario usuarioNovo = new ModelUsuario();
		configuracao.setUsuario(usuarioNovo);
		assertEquals(Color.BLACK, configuracao.getCorSecundariaColor(usuarioNovo));

		usuarioNovo.setConfiguracao(configuracao);
		
		Color cor = Color.YELLOW;
		configuracao.setCorAtualSecundaria("Amarelo");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = Color.BLUE;
		configuracao.setCorAtualSecundaria("Azul");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = Color.WHITE;
		configuracao.setCorAtualSecundaria("Branco");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = new Color(225, 225, 225);
		configuracao.setCorAtualSecundaria("Cinza");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = new Color(255, 140, 0);
		configuracao.setCorAtualSecundaria("Laranja");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = new Color(140, 70, 35);
		configuracao.setCorAtualSecundaria("Marrom");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = Color.BLACK;
		configuracao.setCorAtualSecundaria("Preto");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = new Color(255, 0, 255);
		configuracao.setCorAtualSecundaria("Rosa");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = new Color(170, 0, 255);
		configuracao.setCorAtualSecundaria("Roxo");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = Color.GREEN;
		configuracao.setCorAtualSecundaria("Verde");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		cor = Color.RED;
		configuracao.setCorAtualSecundaria("Vermelho");
		assertEquals(cor, configuracao.getCorSecundariaColor(usuarioNovo));
		
		configuracao.setCorAtualSecundaria("N/S");
		assertEquals(null, configuracao.getCorSecundariaColor(usuarioNovo));
	}
	
	@Test
	void testarToString() {
		ModelUsuario usuarioNovo = new ModelUsuario("Nome", "Senha");
		configuracao.setUsuario(usuarioNovo);
		assertEquals(usuarioNovo.getNomeUsuario(), configuracao.toString());
	}
}

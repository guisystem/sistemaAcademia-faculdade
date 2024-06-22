package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelProfessorTeste {

	@SuppressWarnings("unused")
	private ModelProfessor professorConstructor = new ModelProfessor();
	private ModelProfessor professor;
	private ModelEspecialidade especialidadeNova;
	
	@BeforeEach
	void iniciarTeste() {
		especialidadeNova = new ModelEspecialidade();
		professor = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", "00:00", 0, especialidadeNova);
	}
	
	@Test
	void testarConstrutorHoraNull() {
		ModelProfessor professorHoraNull = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", null, 0, new ModelEspecialidade());
		assertEquals(null, professorHoraNull.getHorarioSaida());
	}

	@Test
	void testarConstrutorHoraVazia() {
		ModelProfessor professorHoraNull = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "", "00:00", 0, new ModelEspecialidade());
		assertEquals(null, professorHoraNull.getHorarioEntrada());
	}
	
	@Test
	void testarSetEspecialidade() {
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		especialidades.add(especialidadeNova);
		professor.setEspecialidades(especialidades);
		assertEquals(especialidadeNova, professor.getEspecialidades().get(0));
	}
	
	@Test
	void testarSetDiaDeTrabalho() {
		ArrayList<DayOfWeek> dias = new ArrayList<>();
		dias.add(DayOfWeek.SUNDAY);
		dias.add(DayOfWeek.MONDAY);
		
		professor.setDiasDeTrabalho(dias);
		assertEquals(DayOfWeek.SUNDAY, professor.getDiasDeTrabalho().get(0));
	}
	
	@Test
	void testarGetAdicionarDia() {
		professor.adicionarDias("Segunda");
		assertEquals(DayOfWeek.MONDAY, professor.getDiasDeTrabalho().get(0));
		
		professor.adicionarDias("Terca");
		assertEquals(DayOfWeek.TUESDAY, professor.getDiasDeTrabalho().get(1));
		
		professor.adicionarDias("Quarta");
		assertEquals(DayOfWeek.WEDNESDAY, professor.getDiasDeTrabalho().get(2));
		
		professor.adicionarDias("Quinta");
		assertEquals(DayOfWeek.THURSDAY, professor.getDiasDeTrabalho().get(3));
		
		professor.adicionarDias("Sexta");
		assertEquals(DayOfWeek.FRIDAY, professor.getDiasDeTrabalho().get(4));
		
		professor.adicionarDias("Sábado");
		assertEquals(DayOfWeek.SATURDAY, professor.getDiasDeTrabalho().get(5));
		
		professor.adicionarDias("Domingo");
		assertEquals(DayOfWeek.SUNDAY, professor.getDiasDeTrabalho().get(6));

	}
	
	@Test
	void testarGetDiaFormatado() {
		professor.adicionarDias("Segunda");
		assertEquals("Segunda", professor.getDiaFormatado().get(0));
		
		professor.adicionarDias("Terça");
		assertEquals("Terça", professor.getDiaFormatado().get(1));
		
		professor.adicionarDias("Quarta");
		assertEquals("Quarta", professor.getDiaFormatado().get(2));
		
		professor.adicionarDias("Quinta");
		assertEquals("Quinta", professor.getDiaFormatado().get(3));
		
		professor.adicionarDias("Sexta");
		assertEquals("Sexta", professor.getDiaFormatado().get(4));
		
		professor.adicionarDias("Sábado");
		assertEquals("Sábado", professor.getDiaFormatado().get(5));
		
		professor.adicionarDias("Domingo");
		assertEquals("Domingo", professor.getDiaFormatado().get(6));
		
	}
	
	@Test
	void testarSetHoraEntrada() {
		Date agora = new Date();
		professor.setHorarioEntrada(new Date());
		assertEquals(agora, professor.getHorarioEntrada());
	}
	
	@Test
	void testarSetHoraEntradaFormatada() {
		professor.setHoraEntradaFormatada("11:00");
		assertEquals("11:00", professor.getHoraEntradaFormatada());
	}
	
	@Test
	void testarSetHoraSaida() {
		Date agora = new Date();
		professor.setHorarioSaida(new Date());
		assertEquals(agora, professor.getHorarioSaida());
		
	}
	
	@Test
	void testarSetHoraSaidaFormatada() {
		professor.setHoraSaidaFormatada("12:00");
		assertEquals("12:00", professor.getHoraSaidaFormatada());
	}
	
	@Test
	void testarSetDataAdmissao() {
		Date dataAdmissao = null;
		try {
			dataAdmissao = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("11/01/2000 11:00");
		} catch (ParseException e) {

		}
		
		professor.setDataAdmissao(dataAdmissao);
		assertEquals(dataAdmissao, professor.getDataAdmissao());
	}
	
	@Test
	void testarGetDataAdmissaoFormatada() {
		Date dataAdmissao = null;
		try {
			dataAdmissao = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("11/01/2000 12:00");
		} catch (ParseException e) {

		}
		
		professor.setDataAdmissao(dataAdmissao);
		assertEquals("11/01/2000 12:00", professor.getDataAdmissaoFormatada());
	}
	
	@Test
	void testarSetSalario() {
		professor.setSalario(1000);
		assertEquals(1000, professor.getSalario());
	}
	
	@Test
	void testarSetUsuario() {
		ModelUsuario novoUsuario = new ModelUsuario();
		professor.setUsuario(novoUsuario);
		assertEquals(novoUsuario, professor.getUsuario());
	}
	
	@Test
	void testarSetTipoDeDataContrato() {
		professor.setTipoDeDataContrato("Ano");
		assertEquals("Ano", professor.getTipoDeDataContrato());
	}
	
	@Test
	void testarSetTempoDeContrato() {
		professor.setTempoContrato(5);
		assertEquals(5, professor.getTempoContrato());
	}
	
	@Test
	void testarSetRegistroProfessor() {
		ModelRegistroProfessor registroNovo = new ModelRegistroProfessor(professor, "Não");
		professor.setRegistroProfessor(registroNovo);
		assertEquals(registroNovo, professor.getRegistroProfessor());
	}
	
	@Test
	void testarSetFoto() {
		ModelFoto foto = new ModelFoto("", "", professor);
		professor.setFoto(foto);
		assertEquals(foto, professor.getFoto());
	}
	
	@Test
	void testarAdicionarEspecialidade() {
		ModelEspecialidade especialidade2 = new ModelEspecialidade("Nome2");
		professor.adicionarEspecialidade(especialidade2);
		assertEquals(especialidade2, professor.getEspecialidades().get(1));
	
	}

}

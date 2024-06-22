package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelUsuarioTeste {

	@SuppressWarnings("unused")
	private ModelUsuario usuarioConstructor = new ModelUsuario();
	private ModelUsuario usuario;
	private ModelUsuario usuarioMaior;
	
	@BeforeEach
	void iniciarTeste() {
		usuarioMaior = new ModelUsuario("NomeMaior", "12345678901", "emailMaior" ,"NomeUsuarioMaior", "SenhaMaior", new ModelConfiguracao());
		usuario = new ModelUsuario("NomeUsuario", "SenhaUsuario");
	}
	
	@Test
	void testarSetNomeUsuario(){
		usuario.setNomeUsuario("NovoNome");
		assertEquals("NovoNome", usuario.getNomeUsuario());
	}
	
	@Test
	void testarSetSenhaUsuario(){
		usuarioMaior.setSenha("NovaSenha");
		assertEquals("NovaSenha", usuarioMaior.getSenha());
	}
	
	@Test
	void testarSetConfiguracao(){
		ModelConfiguracao configuracao = new ModelConfiguracao();
		usuario.setConfiguracao(configuracao);
		assertEquals(configuracao, usuario.getConfiguracao());
	}
	
	@Test
	void testarSetCustoBasico(){
		ModelCustoBasico novoCusto = new ModelCustoBasico();
		ArrayList<ModelCustoBasico> custos = new ArrayList<>();
		custos.add(novoCusto);
		usuario.setCustosBasicos(custos);
		
		assertEquals(novoCusto, usuario.getCustosBasicos().get(0));
	}
	
	@Test
	void testarSetFuncionario(){
		ModelFuncionario novoFuncionario = new ModelFuncionario();
		ArrayList<ModelFuncionario> funcionarios = new ArrayList<>();
		funcionarios.add(novoFuncionario);
		usuario.setFuncionarios(funcionarios);
		
		assertEquals(novoFuncionario, usuario.getFuncionarios().get(0));
	}
	
	@Test
	void testarSetModalidade(){
		ModelModalidade novaModalidade = new ModelModalidade();
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		modalidades.add(novaModalidade);
		usuario.setModalidades(modalidades);
		
		assertEquals(novaModalidade, usuario.getModalidades().get(0));
	}
	
	@Test
	void testarSetEspecialidade(){
		ModelEspecialidade novaEspecialidade = new ModelEspecialidade();
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		especialidades.add(novaEspecialidade);
		usuario.setEspecialidades(especialidades);
		
		assertEquals(novaEspecialidade, usuario.getEspecialidades().get(0));
	}
	
	@Test
	void testarSetPlano(){
		ModelPlano novoPlano = new ModelPlano();
		ArrayList<ModelPlano> planos = new ArrayList<>();
		planos.add(novoPlano);
		usuario.setPlanos(planos);
		
		assertEquals(novoPlano, usuario.getPlanos().get(0));
	}
	
	@Test
	void testarSetAluno(){
		ModelAluno novoAluno = new ModelAluno();
		ArrayList<ModelAluno> alunos = new ArrayList<>();
		alunos.add(novoAluno);
		usuario.setAlunos(alunos);
		
		assertEquals(novoAluno, usuario.getAlunos().get(0));
	}
	
	@Test
	void testarSetProfessor(){
		ModelProfessor novoProfessor = new ModelProfessor();
		ArrayList<ModelProfessor> professores = new ArrayList<>();
		professores.add(novoProfessor);
		usuario.setProfessores(professores);
		
		assertEquals(novoProfessor, usuario.getProfessores().get(0));
	}
	
	@Test
	void testarSetRegistroAluno(){
		ModelRegistroAluno novoRegistroAluno = new ModelRegistroAluno();
		ArrayList<ModelRegistroAluno> registros = new ArrayList<>();
		registros.add(novoRegistroAluno);
		usuario.setRegistroAlunos(registros);
		
		assertEquals(novoRegistroAluno, usuario.getRegistroAlunos().get(0));
	}
	
	@Test
	void testarSetRegistroProfessor(){
		ModelRegistroProfessor novoRegistroProfessor = new ModelRegistroProfessor();
		ArrayList<ModelRegistroProfessor> registros = new ArrayList<>();
		registros.add(novoRegistroProfessor);
		usuario.setRegistroProfessores(registros);
		
		assertEquals(novoRegistroProfessor, usuario.getRegistroProfessores().get(0));
	}
	
	@Test
	void testarGetGastosZerado(){
		assertEquals(0, usuario.getGastosTotal());
	}
	
	@Test
	void testarGetGastosComCusto(){
		ModelCustoBasico novoCusto = new ModelCustoBasico("NomeCusto", 10);
		usuario.getCustosBasicos().add(novoCusto);
		assertEquals(10, usuario.getGastosTotal());
	}
	
	@Test
	void testarGetGastosComFuncionario(){
		ModelFuncionario novoFuncionario = new ModelFuncionario("NomeFuncionario", "Cargo", 1000);
		usuario.getFuncionarios().add(novoFuncionario);
		assertEquals(1000, usuario.getGastosTotal());
	}
	
	@Test
	void testarGetGastosProfessorAtivo(){
		ModelProfessor novoProfessor = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", "00:00", 1500, new ModelEspecialidade());
		ModelRegistroProfessor novoRegistroProfessor = new ModelRegistroProfessor();
		novoRegistroProfessor.setAtivo("Sim");
		novoProfessor.setRegistroProfessor(novoRegistroProfessor);
		
		usuario.getProfessores().add(novoProfessor);
		assertEquals(1500, usuario.getGastosTotal());
	}
	
	@Test
	void testarGetGastosProfessorNaoAtivo(){
		ModelProfessor novoProfessor = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", "00:00", 5500, new ModelEspecialidade());
		ModelRegistroProfessor novoRegistroProfessor = new ModelRegistroProfessor();
		novoRegistroProfessor.setAtivo("Não");
		novoProfessor.setRegistroProfessor(novoRegistroProfessor);
		
		usuario.getProfessores().add(novoProfessor);
		assertEquals(0, usuario.getGastosTotal());
	}
	
	@Test
	void testarGetGanhosZerado(){
		assertEquals(0, usuario.getGanhosTotal());
	}
	
	@Test
	void testarGetGanhosComPlanoSemModalidade(){
		ModelAluno aluno = new ModelAluno();
		ModelRegistroAluno novoRegistroAluno = new ModelRegistroAluno();
		
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date()); 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(dataHoje, dtf);
		novoRegistroAluno.setProximoPagamento(proximoPagamento); // setando o proximo pagamento para a data atual para que o ativo seja "Sim"

		novoRegistroAluno.setAluno(aluno); // Adicionando o aluno ao registro
		aluno.setRegistroAluno(novoRegistroAluno);
		
		ModelPlano novoPlano = new ModelPlano("NovoPlano", 1, 50);
		aluno.setPlano(novoPlano); // Setando o plano ao aluno

		// Adicionando o plano, aluno e o registro ao Usuário
		usuario.getPlanos().add(novoPlano);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(novoRegistroAluno);
		
		assertEquals(50, usuario.getGanhosTotal());
		
	}
	
	@Test
	void testarGetGanhosComPlanoComModalidade(){
		ModelAluno aluno = new ModelAluno();
		ModelRegistroAluno novoRegistroAluno = new ModelRegistroAluno();
		
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(dataHoje, dtf);
		novoRegistroAluno.setProximoPagamento(proximoPagamento);

		novoRegistroAluno.setAluno(aluno);
		aluno.setRegistroAluno(novoRegistroAluno);
		
		ModelPlano novoPlano = new ModelPlano("NovoPlano", 1, 50);
		aluno.setPlano(novoPlano);
		
		ModelModalidade novaModalidade = new ModelModalidade("NovaModalidade", 5);
		aluno.getModalidades().add(novaModalidade);
		
		// Agora foi criada uma nova modalidade e setada tanto ao aluno quanto ao usuário.
		usuario.getPlanos().add(novoPlano);
		usuario.getModalidades().add(novaModalidade);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(novoRegistroAluno);
		
		assertEquals(55, usuario.getGanhosTotal());
	}
	
	@Test
	void testarGetLucroZerado(){
		assertEquals(0, usuario.getLucroTotal());
	}
	
	@Test
	void testarGetLucro(){
		ModelProfessor novoProfessor = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", "00:00", 1500, new ModelEspecialidade());
		ModelRegistroProfessor novoRegistroProfessor = new ModelRegistroProfessor();
		novoRegistroProfessor.setAtivo("Sim");
		novoProfessor.setRegistroProfessor(novoRegistroProfessor);
		
		usuario.getProfessores().add(novoProfessor);
		
		ModelAluno aluno = new ModelAluno();
		ModelRegistroAluno novoRegistroAluno = new ModelRegistroAluno();
		
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(dataHoje, dtf);
		novoRegistroAluno.setProximoPagamento(proximoPagamento);

		novoRegistroAluno.setAluno(aluno);
		aluno.setRegistroAluno(novoRegistroAluno);
		
		ModelPlano novoPlano = new ModelPlano("NovoPlano", 1, 50);
		aluno.setPlano(novoPlano);
		
		ModelModalidade novaModalidade = new ModelModalidade("NovaModalidade", 5);
		aluno.getModalidades().add(novaModalidade);
		
		usuario.getModalidades().add(novaModalidade);
		usuario.getPlanos().add(novoPlano);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(novoRegistroAluno);
		
		assertEquals(-1445, usuario.getLucroTotal());
	}
	
	@Test
	void testarGetQuantidadePlano(){
		ModelPlano novoPlano = new ModelPlano("NovoPlano", 1, 50);
		usuario.getPlanos().add(novoPlano);

		ModelPlano novoPlano2 = new ModelPlano("NovoPlano2", 1, 50);
		usuario.getPlanos().add(novoPlano2);
		
		assertEquals(2, usuario.getQuantidadePlanos());
	}
	
	@Test
	void testarGetQuantidadeAlunoAtivo(){
		ModelAluno aluno = new ModelAluno();
		ModelRegistroAluno novoRegistroAluno = new ModelRegistroAluno();
		
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date()); 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(dataHoje, dtf);
		novoRegistroAluno.setProximoPagamento(proximoPagamento);

		novoRegistroAluno.setAluno(aluno);
		aluno.setRegistroAluno(novoRegistroAluno);
		
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(novoRegistroAluno);
		
		assertEquals(aluno.getRegistroAluno(), usuario.getAlunosAtivos().get(0));
	}
	
	@Test
	void testarGetQuantidadeProfessorAtivoRegistroNull(){
		
		assertEquals(0, usuario.getProfessoresAtivos().size());
	}

	@Test
	void testarGetQuantidadeProfessorAtivo(){
		ModelProfessor novoProfessor = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", "00:00", 1500, new ModelEspecialidade());
		ModelRegistroProfessor novoRegistroProfessor = new ModelRegistroProfessor();
		novoRegistroProfessor.setAtivo("Sim");
		novoProfessor.setRegistroProfessor(novoRegistroProfessor);
		
		usuario.getRegistroProfessores().add(novoRegistroProfessor);
		usuario.getProfessores().add(novoProfessor);
		
		assertEquals(novoRegistroProfessor, usuario.getProfessoresAtivos().get(0));
	}
	
	@Test
	void testarGetPlanoPopular(){
		ModelAluno aluno = new ModelAluno();
		ModelAluno aluno2 = new ModelAluno();
		ModelAluno aluno3 = new ModelAluno();
		ModelRegistroAluno novoRegistroAluno = new ModelRegistroAluno();
		ModelRegistroAluno novoRegistroAluno2 = new ModelRegistroAluno();
		ModelRegistroAluno novoRegistroAluno3 = new ModelRegistroAluno();
		
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date()); 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(dataHoje, dtf);
		novoRegistroAluno.setProximoPagamento(proximoPagamento); 
		novoRegistroAluno2.setProximoPagamento(proximoPagamento);
		novoRegistroAluno3.setProximoPagamento(proximoPagamento);

		novoRegistroAluno.setAluno(aluno); 
		novoRegistroAluno2.setAluno(aluno2); 
		novoRegistroAluno3.setAluno(aluno3); 
		aluno.setRegistroAluno(novoRegistroAluno);
		aluno2.setRegistroAluno(novoRegistroAluno2);
		aluno3.setRegistroAluno(novoRegistroAluno3);
		
		ModelPlano novoPlano = new ModelPlano("NovoPlano", 1, 50);
		novoPlano.setId(1L);
		aluno.setPlano(novoPlano);

		ModelPlano novoPlano2 = new ModelPlano("NovoPlano2", 1, 50);
		novoPlano2.setId(2L);
		aluno2.setPlano(novoPlano2);
		aluno3.setPlano(novoPlano2);
		
		usuario.getPlanos().add(novoPlano);
		usuario.getPlanos().add(novoPlano2);
		usuario.getAlunos().add(aluno);
		usuario.getAlunos().add(aluno2);
		usuario.getAlunos().add(aluno3);
		usuario.getRegistroAlunos().add(novoRegistroAluno);
		usuario.getRegistroAlunos().add(novoRegistroAluno2);
		usuario.getRegistroAlunos().add(novoRegistroAluno3);

		assertEquals(novoPlano2.toString(), usuario.getPlanoPopular());
	}
	
	@Test
	void testarRemoverEspecialidade(){
		ModelEspecialidade novaEspecialidade = new ModelEspecialidade("Musculação");
		novaEspecialidade.setId(1L);
		ModelEspecialidade novaEspecialidade2 = new ModelEspecialidade("Dança");
		novaEspecialidade2.setId(2L);
		
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		especialidades.add(novaEspecialidade);
		especialidades.add(novaEspecialidade2);

		ModelModalidade novaModalidade = new ModelModalidade("Musculacao", 10);
		novaModalidade.setId(1L);
		
		usuario.setEspecialidades(especialidades);
		usuario.getModalidades().add(novaModalidade);
	
		assertEquals(especialidades, usuario.getEspecialidades());
		
		usuario.removerEspecialidadePorId(novaModalidade);
		
		assertEquals(novaEspecialidade2, usuario.getEspecialidades().get(0));
	}
	
	@Test
	void testarToString(){
		usuario.setNome("NomeTeste");
		usuario.setNomeUsuario("NomeUsuario");
		
		assertEquals("NomeUsuario - NomeTeste", usuario.toString());
	}

}

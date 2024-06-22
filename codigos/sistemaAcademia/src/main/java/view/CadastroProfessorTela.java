package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.MenuElement;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import controller.CadastroProfessorController;
import controller.NavegacaoController;
import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelUsuario;
import javax.swing.JRadioButton;

public class CadastroProfessorTela extends JFrame {

	private JPanel contentPane;
	private JLabel lblTempoDeContrato;
	private JLabel lblDataDeAdmisso;
	private JLabel lblHorarioSaida;
	private JLabel lblHorarioEntrada;
	private JLabel lblEspecialidade;
	private JLabel lblMatriculaProfessor;
	private JLabel lblPeriodoTrabalho;
	private JLabel lblImagemCadastroProfessor;
	private JLabel lblAdicionarFoto;
	private JLabel lblEmailProfessor;
	private JLabel lblCpfProfessor;
	private JLabel lblNomeProfessor;
	private JLabel lblCadastroDeProfessor;
	private JLabel lblPainelCadastroProfessor;

	private JTextField textFieldHoraEntrada;
	private JTextField textFieldSalarioProfessor;
	private JTextField textFieldHoraSaida;
	private JTextField textFieldTempoContrato;
	private JTextField textFieldNomeProfessor;
	private JTextField textFieldCpfProfessor;
	private JTextField textFieldEmailProfessor;
	private JTextField nomeImagemField = new JTextField(20);
	private String caminhoImagem;

	private JCheckBox chckbxSegunda;
	private JCheckBox chckbxTerca;
	private JCheckBox chckbxQuarta;
	private JCheckBox chckbxQuinta;
	private JCheckBox chckbxSexta;
	private JCheckBox chckbxSabado;
	private JCheckBox chckbxDomingo;

	private JButton btnCadastrarProfessor;
	private JButton btnCarregarFoto;
	
	private JRadioButton rdbtnMes;
	private JRadioButton rdbtnAno;
	
	private JComboBox comboBoxEspecialidade;
	
	private JTable table;
	

	private CadastroProfessorController controller;
	private NavegacaoController controllerNavegacao;
	
	private static CadastroProfessorTela singleton = null;
	private static ModelUsuario usuario;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroProfessorTela frame = new CadastroProfessorTela(usuario);
					frame.setTitle("Cadastro de Professor");
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/** @implNote Se a tela for nula, cria uma nova, se não, retorna ela própria */
	public static CadastroProfessorTela getCadastroProfessor(ModelUsuario usuario){
		if (singleton == null){
			singleton = new CadastroProfessorTela(usuario);
			singleton.setVisible(true);
			singleton.setLocationRelativeTo(null);
			singleton.setResizable(false);
			singleton.setTitle("Cadastro de Professor");
		}
		
		if(singleton.isShowing() == false) {
			singleton = null;
		}
		
		return singleton;
		
	}

	/**
	 * Create the frame.
	 */
	public CadastroProfessorTela(ModelUsuario usuario) {
		
		CadastroProfessorTela.usuario = usuario;

		controllerNavegacao = new NavegacaoController(this);
		controller = new CadastroProfessorController(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1383, 744);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(new Color(0, 0, 0));
		setJMenuBar(menuBar);
		
		
		JMenu MenuCadastro = new JMenu("Cadastro");
		menuBar.add(MenuCadastro);
		
		JMenuItem MenuItemAluno = new JMenuItem("Cadastrar aluno");
		MenuItemAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaCadastroAluno(usuario);
			}
		});
		MenuCadastro.add(MenuItemAluno);
		
		JMenuItem MenuItemProfessor = new JMenuItem("Cadastrar professor");
		MenuCadastro.add(MenuItemProfessor);
		
		JMenu MenuRegistro = new JMenu("Registros");
		menuBar.add(MenuRegistro);
		
		JMenuItem MenuItemRegAlunos = new JMenuItem("Alunos");
		MenuItemRegAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaRegistroAluno(usuario);
			}
		});
		MenuRegistro.add(MenuItemRegAlunos);
		
		JMenuItem MenuItemRegProfessor = new JMenuItem("Professores");
		MenuItemRegProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaRegistroProfessor(usuario);
			}
		});
		MenuRegistro.add(MenuItemRegProfessor);
		
		JMenu MenuPerfil = new JMenu("Perfil");
		menuBar.add(MenuPerfil);
		
		JMenuItem NewMenuItemDashboard = new JMenuItem("DashBoard");
		NewMenuItemDashboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaDashBoard(usuario);
			}
		});
		MenuPerfil.add(NewMenuItemDashboard);
		
		JMenuItem MenuItemMudarSenha = new JMenuItem("Mudar senha");
		MenuItemMudarSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaMudarSenha(usuario);
			}
		});
		MenuPerfil.add(MenuItemMudarSenha);
		
		JMenuItem MenuItemConfiguracao = new JMenuItem("Configura\u00E7\u00E3o");
		MenuItemConfiguracao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaConfiguracao(usuario);
			}
		});
		MenuPerfil.add(MenuItemConfiguracao);
		
		JMenuItem MenuItemSair = new JMenuItem("Sair");
		MenuItemSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.sair();
			}
		});
		MenuPerfil.add(MenuItemSair);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rdbtnAno = new JRadioButton("Ano");
		rdbtnAno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!rdbtnAno.isSelected()) {
					rdbtnMes.setSelected(true);
				}else {
					rdbtnMes.setSelected(false);
				}
			}
		});
		rdbtnAno.setSelected(true);
		rdbtnAno.setFont(new Font("Arial", Font.PLAIN, 12));
		rdbtnAno.setBounds(794, 442, 52, 23);
		contentPane.add(rdbtnAno);
		
		rdbtnMes = new JRadioButton("M\u00EAs");
		rdbtnMes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!rdbtnMes.isSelected()) {
					rdbtnAno.setSelected(true);
				}else {
					rdbtnAno.setSelected(false);
				}
			}
		});
		rdbtnMes.setFont(new Font("Arial", Font.PLAIN, 12));
		rdbtnMes.setBounds(794, 412, 52, 23);
		contentPane.add(rdbtnMes);
		
		chckbxSegunda = new JCheckBox("Segunda");
		chckbxSegunda.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxSegunda.setBackground(new Color(0, 0, 0));
		chckbxSegunda.setForeground(new Color(236, 202, 15));
		chckbxSegunda.setOpaque(false);
		chckbxSegunda.setBounds(197, 516, 89, 22);
		contentPane.add(chckbxSegunda);
		
		chckbxTerca = new JCheckBox("Ter\u00E7a");
		chckbxTerca.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxTerca.setBackground(new Color(0, 0, 0));
		chckbxTerca.setForeground(new Color(236, 202, 15));
		chckbxTerca.setOpaque(false);
		chckbxTerca.setBounds(296, 516, 72, 22);
		contentPane.add(chckbxTerca);
		
		chckbxQuarta = new JCheckBox("Quarta");
		chckbxQuarta.setFont(new Font("Arial", Font.PLAIN, 14)); 
		chckbxQuarta.setBackground(new Color(0, 0, 0));
		chckbxQuarta.setForeground(new Color(236, 202, 15));
		chckbxQuarta.setOpaque(false);
		chckbxQuarta.setBounds(378, 516, 72, 22);
		contentPane.add(chckbxQuarta);
		
		chckbxQuinta = new JCheckBox("Quinta");
		chckbxQuinta.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxQuinta.setBackground(new Color(0, 0, 0));
		chckbxQuinta.setForeground(new Color(236, 202, 15));
		chckbxQuinta.setOpaque(false);
		chckbxQuinta.setBounds(464, 516, 72, 22);
		contentPane.add(chckbxQuinta);
		
		chckbxSexta = new JCheckBox("Sexta");
		chckbxSexta.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxSexta.setBackground(new Color(0, 0, 0));
		chckbxSexta.setForeground(new Color(236, 202, 15));
		chckbxSexta.setOpaque(false);
		chckbxSexta.setBounds(197, 544, 72, 22);
		contentPane.add(chckbxSexta);
		
		chckbxSabado = new JCheckBox("S\u00E1bado");
		chckbxSabado.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxSabado.setBackground(new Color(0, 0, 0));
		chckbxSabado.setForeground(new Color(236, 202, 15));
		chckbxSabado.setOpaque(false);
		chckbxSabado.setBounds(296, 544, 80, 22);
		contentPane.add(chckbxSabado);
		
		chckbxDomingo = new JCheckBox("Domingo");
		chckbxDomingo.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxDomingo.setBackground(new Color(0, 0, 0));
		chckbxDomingo.setForeground(new Color(236, 202, 15));
		chckbxDomingo.setOpaque(false);
		chckbxDomingo.setBounds(378, 544, 89, 22);
		contentPane.add(chckbxDomingo);
		
		textFieldTempoContrato = new JTextField();
		textFieldTempoContrato.setForeground(Color.WHITE);
		textFieldTempoContrato.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldTempoContrato.setColumns(10);
		textFieldTempoContrato.setBackground(Color.BLACK);
		textFieldTempoContrato.setBounds(606, 412, 180, 53);
		contentPane.add(textFieldTempoContrato);
		
		lblTempoDeContrato = new JLabel("Tempo de Contrato");
		lblTempoDeContrato.setForeground(new Color(237, 198, 63));
		lblTempoDeContrato.setFont(new Font("Arial", Font.PLAIN, 20));
		lblTempoDeContrato.setBounds(606, 384, 171, 24);
		contentPane.add(lblTempoDeContrato);
		
		lblDataDeAdmisso = new JLabel("Dias de Trabalho");
		lblDataDeAdmisso.setForeground(new Color(237, 198, 63));
		lblDataDeAdmisso.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDataDeAdmisso.setBounds(200, 485, 155, 24);
		contentPane.add(lblDataDeAdmisso);
		
		lblHorarioSaida = new JLabel("Hor\u00E1rio de Sa\u00EDda:");
		lblHorarioSaida.setForeground(new Color(237, 198, 63));
		lblHorarioSaida.setFont(new Font("Arial", Font.PLAIN, 16));
		lblHorarioSaida.setBounds(606, 242, 137, 18);
		contentPane.add(lblHorarioSaida);
		
		lblHorarioEntrada = new JLabel("Hor\u00E1rio de Entrada:");
		lblHorarioEntrada.setForeground(new Color(237, 198, 63));
		lblHorarioEntrada.setFont(new Font("Arial", Font.PLAIN, 16));
		lblHorarioEntrada.setBounds(606, 214, 137, 18);
		contentPane.add(lblHorarioEntrada);
		
		textFieldHoraEntrada = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldHoraEntrada).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##:##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldHoraEntrada.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldHoraEntrada.setForeground(Color.WHITE);
		textFieldHoraEntrada.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldHoraEntrada.setColumns(10);
		textFieldHoraEntrada.setBackground(Color.BLACK);
		textFieldHoraEntrada.setBounds(791, 210, 55, 25);
		contentPane.add(textFieldHoraEntrada);
		
		textFieldHoraSaida = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldHoraSaida).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##:##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldHoraSaida.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldHoraSaida.setForeground(Color.WHITE);
		textFieldHoraSaida.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldHoraSaida.setColumns(10);
		textFieldHoraSaida.setBackground(Color.BLACK);
		textFieldHoraSaida.setBounds(791, 239, 55, 25);
		contentPane.add(textFieldHoraSaida);
		
		textFieldSalarioProfessor = new JTextField();
		textFieldSalarioProfessor.setForeground(Color.WHITE);
		textFieldSalarioProfessor.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldSalarioProfessor.setColumns(10);
		textFieldSalarioProfessor.setBackground(Color.BLACK);
		textFieldSalarioProfessor.setBounds(606, 513, 240, 53);
		contentPane.add(textFieldSalarioProfessor);
		
		btnCadastrarProfessor = new JButton("CADASTRAR PROFESSOR");
		btnCadastrarProfessor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setBackground(Color.WHITE);
			    		((JButton)temp).setForeground(Color.BLACK);
			    		((JButton)temp).setBorder(new LineBorder(Color.BLACK));		    	
			    	}else {
			    		((JButton)temp).setBackground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    		((JButton)temp).setForeground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0));
			    		
			    		if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).size() == 1 || 
			    				ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).size() == 1) {
			    			
			    			((JButton)temp).setBorder(new BevelBorder(BevelBorder.LOWERED, ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0), 
			    					ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0)));			    	
			    		}else {
			    			((JButton)temp).setBorder(new BevelBorder(BevelBorder.LOWERED, ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0), 
			    					ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(4)));
			    		}
			    	}
			    }	
			}

			@Override
			public void mouseExited(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setBackground(Color.BLACK);
			    		((JButton)temp).setForeground(Color.WHITE);
			    		((JButton)temp).setBorder(new LineBorder(Color.WHITE));		    	
			    	}else {
			    		((JButton)temp).setBackground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0));
			    		((JButton)temp).setForeground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    		
			    		if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).size() == 1 || 
			    				ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).size() == 1) {
			    			
			    			((JButton)temp).setBorder(new BevelBorder(BevelBorder.LOWERED, ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0), 
			    					ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0)));
			    		}else {
			    			((JButton)temp).setBorder(new BevelBorder(BevelBorder.LOWERED, ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(4), 
			    					ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0)));
			    		}
			    	}
			    }
			}
		});
		btnCadastrarProfessor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCadastrarProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.cadastrarProfessor(usuario);
			}
		});
		btnCadastrarProfessor.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCadastrarProfessor.setBackground(new Color(0, 0, 0));
		btnCadastrarProfessor.setForeground(new Color(236, 202, 15));
		btnCadastrarProfessor.setBorder(new LineBorder(new Color(236, 202, 15)));
		btnCadastrarProfessor.setBounds(946, 516, 220, 48);
		contentPane.add(btnCadastrarProfessor);
		
		btnCarregarFoto = new JButton("Carregar Foto");
		btnCarregarFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setBackground(Color.WHITE);
			    		((JButton)temp).setForeground(Color.BLACK);
			    		((JButton)temp).setBorder(new LineBorder(Color.BLACK));		    	
			    	}else {
			    		((JButton)temp).setBackground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    		((JButton)temp).setForeground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0));
			    		((JButton)temp).setBorder(new LineBorder(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0)));		    	
			    	}
			    }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setBackground(Color.BLACK);
			    		((JButton)temp).setForeground(Color.WHITE);
			    		((JButton)temp).setBorder(new LineBorder(Color.WHITE));		    	
			    	}else {
			    		((JButton)temp).setBackground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0));
			    		((JButton)temp).setForeground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    		((JButton)temp).setBorder(new LineBorder(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0)));		    			    	
			    	}
			    }
			}
		});
		btnCarregarFoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCarregarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.carregarFoto();
			}
		});
		btnCarregarFoto.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCarregarFoto.setBounds(966, 430, 180, 32);
		btnCarregarFoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnCarregarFoto);
		
		lblAdicionarFoto = new JLabel("");
		lblAdicionarFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdicionarFoto.setHorizontalTextPosition(SwingConstants.LEADING);
		lblAdicionarFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		lblAdicionarFoto.setIcon(new ImageIcon(CadastroProfessorTela.class.getResource("/view/icones/icone-foto.png")));
		lblAdicionarFoto.setBounds(966, 190, 180, 240);
		contentPane.add(lblAdicionarFoto);
		
		comboBoxEspecialidade = new JComboBox();
		comboBoxEspecialidade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JComboBox) {
			    	if(usuario.getConfiguracao() == null) {

			    	}else {
			    		if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).size() == 1) {
			    			((JComboBox)temp).setBorder(new LineBorder(new Color(125, 125, 125)));					
			    		}else {
			    			if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0).equals(new Color(255, 0, 255))) {
			    				((JComboBox)temp).setBorder(new LineBorder(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(4)));
			    			}else {
			    				((JComboBox)temp).setBorder(new LineBorder(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0)));		    							
			    			}
			    		}
			    	}
			    }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JComboBox) {
			    	if(usuario.getConfiguracao() == null) {

			    	}else {
			    		if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).size() == 1) {
			    			((JComboBox)temp).setBorder(new LineBorder(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0)));	
			    		}else {
			    			((JComboBox)temp).setBorder(new LineBorder(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(2)));		    			    	
			    		}
			    	}
			    }
			}
		});
		comboBoxEspecialidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxEspecialidade.setForeground(new Color(255, 255, 255));
		comboBoxEspecialidade.setBackground(new Color(0, 0, 0));
		comboBoxEspecialidade.setFont(new Font("Arial", Font.PLAIN, 20));
		comboBoxEspecialidade.setBounds(606, 311, 240, 53);
		contentPane.add(comboBoxEspecialidade);
		
		textFieldEmailProfessor = new JTextField();
		textFieldEmailProfessor.setForeground(new Color(255, 255, 255));
		textFieldEmailProfessor.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldEmailProfessor.setBackground(new Color(0, 0, 0));
		textFieldEmailProfessor.setColumns(10);
		textFieldEmailProfessor.setBounds(200, 412, 326, 53);
		contentPane.add(textFieldEmailProfessor);
		
		textFieldCpfProfessor = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldCpfProfessor).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###-##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldCpfProfessor.setForeground(new Color(255, 255, 255));
		textFieldCpfProfessor.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldCpfProfessor.setBackground(new Color(0, 0, 0));
		textFieldCpfProfessor.setColumns(10);
		textFieldCpfProfessor.setBounds(200, 311, 326, 53);
		contentPane.add(textFieldCpfProfessor);
		
		textFieldNomeProfessor = new JTextField();
		textFieldNomeProfessor.setForeground(new Color(255, 255, 255));
		textFieldNomeProfessor.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldNomeProfessor.setBackground(new Color(0, 0, 0));
		textFieldNomeProfessor.setBounds(200, 210, 326, 53);
		contentPane.add(textFieldNomeProfessor);
		textFieldNomeProfessor.setColumns(10);
		
		lblEspecialidade = new JLabel("Especialidade");
		lblEspecialidade.setForeground(new Color(237, 198, 63));
		lblEspecialidade.setFont(new Font("Arial", Font.PLAIN, 20));
		lblEspecialidade.setBounds(606, 283, 122, 24);
		contentPane.add(lblEspecialidade);
		
		lblMatriculaProfessor = new JLabel("Sal\u00E1rio");
		lblMatriculaProfessor.setForeground(new Color(237, 198, 63));
		lblMatriculaProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblMatriculaProfessor.setBounds(606, 485, 61, 24);
		contentPane.add(lblMatriculaProfessor);
		
		lblPeriodoTrabalho = new JLabel("Periodo de Trabalho");
		lblPeriodoTrabalho.setForeground(new Color(237, 198, 63));
		lblPeriodoTrabalho.setFont(new Font("Arial", Font.PLAIN, 20));
		lblPeriodoTrabalho.setBounds(606, 182, 179, 24);
		contentPane.add(lblPeriodoTrabalho);
		
		lblEmailProfessor = new JLabel("Email");
		lblEmailProfessor.setForeground(new Color(237, 198, 63));
		lblEmailProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblEmailProfessor.setBounds(200, 384, 48, 24);
		contentPane.add(lblEmailProfessor);
		
		lblCpfProfessor = new JLabel("CPF");
		lblCpfProfessor.setForeground(new Color(237, 198, 63));
		lblCpfProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblCpfProfessor.setBounds(200, 283, 39, 24);
		contentPane.add(lblCpfProfessor);
		
		lblNomeProfessor = new JLabel("Nome Completo");
		lblNomeProfessor.setForeground(new Color(237, 198, 63));
		lblNomeProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomeProfessor.setBounds(200, 182, 141, 24);
		contentPane.add(lblNomeProfessor);
		
		lblCadastroDeProfessor = new JLabel("Cadastro de Professor");
		lblCadastroDeProfessor.setForeground(new Color(236, 202, 15));
		lblCadastroDeProfessor.setFont(new Font("Arial", Font.PLAIN, 36));
		lblCadastroDeProfessor.setBounds(503, 86, 360, 40);
		contentPane.add(lblCadastroDeProfessor);
		
		lblPainelCadastroProfessor = new JLabel("");
		lblPainelCadastroProfessor.setIcon(new ImageIcon(CadastroProfessorTela.class.getResource("/view/imagens/painel-cadastro-professor.png")));
		lblPainelCadastroProfessor.setBounds(100, 73, 1166, 537);
		contentPane.add(lblPainelCadastroProfessor);
		
		iniciar();
		customizeTela();
		customizeMenuBar(menuBar);
		
	}

	/** @implNote Quando a ação não pode ser concluída devido a algum dado incorreto ou lógica de programação */
	public void exibirMensagemErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.ERROR_MESSAGE);
	}
	
	/** @implNote Quando for para exibir uma explicação de um dado/método ou para mostrar o resultado da acão realizada do usuário */
	public void exibirMensagemInformacao(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/** @implNote Quando o usuário precisa tomar uma decisão entre duas ou mais opções */
	public boolean exibirMensagemDecisao(String mensagem) {
		if (JOptionPane.showConfirmDialog(null, mensagem, "Deseja cadastrar?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return true;
		}
		
		return false;
	}

	private void iniciar() {
		controller.preencherEspecialidades(usuario);
		
	}
	
	private void customizeTela() {
		controller.atualizarCores(usuario);
		
	}

	/** @implNote Altera as cores da barra de menu */
	private void customizeMenuBar(JMenuBar menuBar) {

	    menuBar.setUI(new BasicMenuBarUI() {

	        @Override
	        public void paint(Graphics g, JComponent c) {
	        	if(usuario.getConfiguracao() != null) {
	        		g.setColor(usuario.getConfiguracao().getCorSecundariaColor(usuario));	
	        	}else {
	        		g.setColor(Color.BLACK);	
	        	}
	            g.fillRect(0, 0, c.getWidth(), c.getHeight());
	        }

	    });

	    MenuElement[] menus = menuBar.getSubElements();

	    for (MenuElement menuElement : menus) {

	        JMenu menu = (JMenu) menuElement.getComponent();
	        changeComponentColors(menu);
	        menu.setOpaque(true);

	        MenuElement[] menuElements = menu.getSubElements();

	        for (MenuElement popupMenuElement : menuElements) {

	            JPopupMenu popupMenu = (JPopupMenu) popupMenuElement.getComponent();
	            popupMenu.setBorder(null);

	            MenuElement[] menuItens = popupMenuElement.getSubElements();

	            for (MenuElement menuItemElement : menuItens) {

	                JMenuItem menuItem = (JMenuItem) menuItemElement.getComponent();
	                changeComponentColors(menuItem);
	                menuItem.setOpaque(true);

	            }
	        }
	    }
	}

	private void changeComponentColors(Component comp) {
		if(usuario.getConfiguracao() != null) {
			comp.setForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			comp.setBackground(usuario.getConfiguracao().getCorSecundariaColor(usuario));			
		}else {
			comp.setForeground(Color.WHITE);
			comp.setBackground(Color.BLACK);			
		}
	}

	public JLabel getLblTempoDeContrato() {
		return lblTempoDeContrato;
	}

	public void setLblTempoDeContrato(JLabel lblTempoDeContrato) {
		this.lblTempoDeContrato = lblTempoDeContrato;
	}

	public JLabel getLblDataDeAdmisso() {
		return lblDataDeAdmisso;
	}

	public void setLblDataDeAdmisso(JLabel lblDataDeAdmisso) {
		this.lblDataDeAdmisso = lblDataDeAdmisso;
	}

	public JLabel getLblHorarioSaida() {
		return lblHorarioSaida;
	}

	public void setLblHorarioSaida(JLabel lblHorarioSaida) {
		this.lblHorarioSaida = lblHorarioSaida;
	}

	public JLabel getLblHorarioEntrada() {
		return lblHorarioEntrada;
	}

	public void setLblHorarioEntrada(JLabel lblHorarioEntrada) {
		this.lblHorarioEntrada = lblHorarioEntrada;
	}

	public JLabel getLblEspecialidade() {
		return lblEspecialidade;
	}

	public void setLblEspecialidade(JLabel lblEspecialidade) {
		this.lblEspecialidade = lblEspecialidade;
	}

	public JLabel getLblMatriculaProfessor() {
		return lblMatriculaProfessor;
	}

	public void setLblMatriculaProfessor(JLabel lblMatriculaProfessor) {
		this.lblMatriculaProfessor = lblMatriculaProfessor;
	}

	public JLabel getLblPeriodoTrabalho() {
		return lblPeriodoTrabalho;
	}

	public void setLblPeriodoTrabalho(JLabel lblPeriodoTrabalho) {
		this.lblPeriodoTrabalho = lblPeriodoTrabalho;
	}

	public JLabel getLblImagemCadastroProfessor() {
		return lblImagemCadastroProfessor;
	}

	public void setLblImagemCadastroProfessor(JLabel lblImagemCadastroProfessor) {
		this.lblImagemCadastroProfessor = lblImagemCadastroProfessor;
	}

	public JLabel getLblAdicionarFoto() {
		return lblAdicionarFoto;
	}

	public void setLblAdicionarFoto(JLabel lblAdicionarFoto) {
		this.lblAdicionarFoto = lblAdicionarFoto;
	}

	public JLabel getLblEmailProfessor() {
		return lblEmailProfessor;
	}

	public void setLblEmailProfessor(JLabel lblEmailProfessor) {
		this.lblEmailProfessor = lblEmailProfessor;
	}

	public JLabel getLblCpfProfessor() {
		return lblCpfProfessor;
	}

	public void setLblCpfProfessor(JLabel lblCpfProfessor) {
		this.lblCpfProfessor = lblCpfProfessor;
	}

	public JLabel getLblNomeProfessor() {
		return lblNomeProfessor;
	}

	public void setLblNomeProfessor(JLabel lblNomeProfessor) {
		this.lblNomeProfessor = lblNomeProfessor;
	}

	public JLabel getLblCadastroDeProfessor() {
		return lblCadastroDeProfessor;
	}

	public void setLblCadastroDeProfessor(JLabel lblCadastroDeProfessor) {
		this.lblCadastroDeProfessor = lblCadastroDeProfessor;
	}

	public JLabel getLblPainelCadastroProfessor() {
		return lblPainelCadastroProfessor;
	}

	public void setLblPainelCadastroProfessor(JLabel lblPainelCadastroProfessor) {
		this.lblPainelCadastroProfessor = lblPainelCadastroProfessor;
	}

	public JTextField getTextFieldHoraEntrada() {
		return textFieldHoraEntrada;
	}

	public void setTextFieldHoraEntrada(JTextField textFieldHoraEntrada) {
		this.textFieldHoraEntrada = textFieldHoraEntrada;
	}

	public JTextField getTextFieldSalarioProfessor() {
		return textFieldSalarioProfessor;
	}

	public void setTextFieldSalarioProfessor(JTextField textFieldSalarioProfessor) {
		this.textFieldSalarioProfessor = textFieldSalarioProfessor;
	}

	public JTextField getTextFieldHoraSaida() {
		return textFieldHoraSaida;
	}

	public void setTextFieldHoraSaida(JTextField textFieldHoraSaida) {
		this.textFieldHoraSaida = textFieldHoraSaida;
	}

	public JTextField getTextFieldTempoContrato() {
		return textFieldTempoContrato;
	}

	public void setTextFieldTempoContrato(JTextField textFieldTipoContrato) {
		this.textFieldTempoContrato = textFieldTipoContrato;
	}

	public JTextField getTextFieldNomeProfessor() {
		return textFieldNomeProfessor;
	}

	public void setTextFieldNomeProfessor(JTextField textFieldNomeProfessor) {
		this.textFieldNomeProfessor = textFieldNomeProfessor;
	}

	public JTextField getTextFieldCpfProfessor() {
		return textFieldCpfProfessor;
	}

	public void setTextFieldCpfProfessor(JTextField textFieldCpfProfessor) {
		this.textFieldCpfProfessor = textFieldCpfProfessor;
	}

	public JTextField getTextFieldEmailProfessor() {
		return textFieldEmailProfessor;
	}

	public void setTextFieldEmailProfessor(JTextField textFieldEmailProfessor) {
		this.textFieldEmailProfessor = textFieldEmailProfessor;
	}

	public JTextField getNomeImagemField() {
		return nomeImagemField;
	}

	public void setNomeImagemField(JTextField nomeImagemField) {
		this.nomeImagemField = nomeImagemField;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public JCheckBox getChckbxSegunda() {
		return chckbxSegunda;
	}

	public void setChckbxSegunda(JCheckBox chckbxSegunda) {
		this.chckbxSegunda = chckbxSegunda;
	}

	public JCheckBox getChckbxTerca() {
		return chckbxTerca;
	}

	public void setChckbxTerca(JCheckBox chckbxTerca) {
		this.chckbxTerca = chckbxTerca;
	}

	public JCheckBox getChckbxQuarta() {
		return chckbxQuarta;
	}

	public void setChckbxQuarta(JCheckBox chckbxQuarta) {
		this.chckbxQuarta = chckbxQuarta;
	}

	public JCheckBox getChckbxQuinta() {
		return chckbxQuinta;
	}

	public void setChckbxQuinta(JCheckBox chckbxQuinta) {
		this.chckbxQuinta = chckbxQuinta;
	}

	public JCheckBox getChckbxSexta() {
		return chckbxSexta;
	}

	public void setChckbxSexta(JCheckBox chckbxSexta) {
		this.chckbxSexta = chckbxSexta;
	}

	public JCheckBox getChckbxSabado() {
		return chckbxSabado;
	}

	public void setChckbxSabado(JCheckBox chckbxSabado) {
		this.chckbxSabado = chckbxSabado;
	}

	public JCheckBox getChckbxDomingo() {
		return chckbxDomingo;
	}

	public void setChckbxDomingo(JCheckBox chckbxDomingo) {
		this.chckbxDomingo = chckbxDomingo;
	}

	public JButton getBtnCadastrarProfessor() {
		return btnCadastrarProfessor;
	}

	public void setBtnCadastrarProfessor(JButton btnCadastrarProfessor) {
		this.btnCadastrarProfessor = btnCadastrarProfessor;
	}

	public JButton getBtnCarregarFoto() {
		return btnCarregarFoto;
	}

	public void setBtnCarregarFoto(JButton btnCarregarFoto) {
		this.btnCarregarFoto = btnCarregarFoto;
	}

	public JRadioButton getRdbtnMes() {
		return rdbtnMes;
	}

	public void setRdbtnMes(JRadioButton rdbtnMes) {
		this.rdbtnMes = rdbtnMes;
	}

	public JRadioButton getRdbtnAno() {
		return rdbtnAno;
	}

	public void setRdbtnAno(JRadioButton rdbtnAno) {
		this.rdbtnAno = rdbtnAno;
	}

	public JComboBox getComboBoxEspecialidade() {
		return comboBoxEspecialidade;
	}

	public void setComboBoxEspecialidade(JComboBox comboBoxEspecialidade) {
		this.comboBoxEspecialidade = comboBoxEspecialidade;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

}

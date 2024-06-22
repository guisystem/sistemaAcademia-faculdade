package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import controller.ConfiguracaoController;
import controller.NavegacaoController;
import model.ModelUsuario;

public class ConfiguracaoTela extends JFrame {

	private JPanel contentPane;
	private RoundedPanel roundedPanel;

	private JLabel lblInformacaoConfiguracao;
	private JLabel lblExceçãoFuncionario;
	private JLabel lblParaPrimaria;
	private JLabel lblCorPrimaria;
	private JLabel lblParaSecundaria;
	private JLabel lblCorSecundaria;
	private JLabel lblUsuarioPermitidos;
	private JLabel lblConfiguracao;
	private JLabel lblModalidadas;
	private JLabel lblCustosBasicos;
	private JLabel lblFuncionarios;
	private JLabel lblPlanos;
	private JLabel lblNomeAcademia;
	private JLabel lblPeriodoFuncionamento;
	private JLabel lblDias;
	private JLabel lblHorario;
	private JLabel lblPeriodoDiaA;
	private JLabel lblInformacoesAlteraveis;
	private JLabel lblPeriodoHorarioAS;
	private JLabel lblInformacaoCorP;
	private JLabel lblInformacaoCorS;
	private JLabel lblCamposImportantes;
	private JLabel lblCampoImportanteNomeAcademia;
	private JLabel lblCampoImportanteCorSec;
	private JLabel lblCampoImportanteCorPri;
	private JLabel lblCampoImportanteFuncionamentoNome;

	private JTextField textFieldNomeAcademia;
	private JTextField textFieldHoraAbrir;
	private JTextField textFieldHoraFechar;
	private JTextField textFieldCorAtualPrimaria;
	private JTextField textFieldCorAtualSecundaria;
	private JTextField textFieldNomeUsuario;

	private JTable tableCustosBasicos;
	private JTable tableFuncionarios;
	private JTable tableModalidades;
	private JTable tablePlanos;
	
	private JComboBox comboBoxMudarCorPrimaria;
	private JComboBox comboBoxMudarCorSecundaria;
	private JComboBox comboBoxDiaComeco;
	private JComboBox comboBoxDiaFim;
	
	private JButton btnEditarCustos;
	private JButton btnEditarModalidades;
	private JButton btnEditarFuncionarios;
	private JButton btnEditarPlanos;
	private JButton btnConfirmarAlteracoes;
	
	private Color corPrimaria;
	private Color corSecundaria;

	private ConfiguracaoController controller;
	private NavegacaoController controllerNavegacao;

	private static ConfiguracaoTela singleton = null;
	private static ModelUsuario usuario;

	String nomeAcademia;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfiguracaoTela frame = new ConfiguracaoTela(usuario);
					frame.setTitle("Configuração");
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
	public static ConfiguracaoTela getConfiguracao(ModelUsuario usuario){
		if (singleton == null){
			singleton = new ConfiguracaoTela(usuario);
			singleton.setTitle("Configuração");
			singleton.setResizable(false);
			singleton.setLocationRelativeTo(null);
			singleton.setVisible(true);
		}
		
		if(singleton.isShowing() == false) {
			singleton = null;
		}
		
		return singleton;
		
	}

	/**
	 * Create the frame.
	 * @param usuario 
	 */
	public ConfiguracaoTela(ModelUsuario usuario) {
		
		ConfiguracaoTela.usuario = usuario;
		if(usuario.getConfiguracao() != null) {
			nomeAcademia = usuario.getConfiguracao().getNomeDaAcademia();
		}
		
		controller = new ConfiguracaoController(this);
		controllerNavegacao = new NavegacaoController(this);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
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
		MenuItemProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaCadastroProfessor(usuario);
			}
		});
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
				controllerNavegacao.navegarParaDashBoardDeConfiguracao(usuario, nomeAcademia);
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
		MenuPerfil.add(MenuItemConfiguracao);
		
		JMenuItem MenuItemSair = new JMenuItem("Sair");
		MenuItemSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.sair();
			}
		});
		MenuPerfil.add(MenuItemSair);
		
		btnConfirmarAlteracoes = new JButton("CONFIRMAR ALTERA\u00C7\u00D5ES");
		btnConfirmarAlteracoes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setBackground(Color.WHITE);
			    		((JButton)temp).setForeground(Color.BLACK);
			    		((JButton)temp).setBorder(new LineBorder(Color.BLACK));		    	
			    	}else {
			    		((JButton)temp).setBackground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corSecundaria()));			    	
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
			    		((JButton)temp).setBackground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corPrimaria()));			    	
			    	}
			    }
			}
		});
		btnConfirmarAlteracoes.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnConfirmarAlteracoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.confirmarAlteracao(usuario);
				
				customizeMenuBar(menuBar);
				customizeTela();
			}
		});
		btnConfirmarAlteracoes.setFont(new Font("Arial", Font.PLAIN, 18));
		btnConfirmarAlteracoes.setBounds(936, 518, 280, 52);
		contentPane.add(btnConfirmarAlteracoes);
		
		btnEditarPlanos = new JButton("Editar");
		btnEditarPlanos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setBackground(Color.WHITE);
			    		((JButton)temp).setForeground(Color.BLACK);
			    		((JButton)temp).setBorder(new LineBorder(Color.BLACK));		    	
			    	}else {
			    		((JButton)temp).setBackground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corSecundaria()));			    	
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
			    		((JButton)temp).setBackground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corPrimaria()));			    	
			    	}
			    }
			}
		});
		btnEditarPlanos.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEditarPlanos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaEditarPlanos(usuario);
			}
		});
		
		lblInformacaoCorS = new JLabel("");
		lblInformacaoCorS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "As cores secundárias são as cores de fundo, seja dos painéis, \r\n"
						+ "do plano de fundo das telas ou dos campos em que você digita.", "A cor secundária vai afetar o que?", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		lblInformacaoCorS.setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios.png")));
		lblInformacaoCorS.setForeground(Color.GRAY);
		lblInformacaoCorS.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInformacaoCorS.setBounds(1182, 421, 16, 16);
		contentPane.add(lblInformacaoCorS);
		 
		lblInformacaoCorP = new JLabel("");
		lblInformacaoCorP.setForeground(new Color(255, 255, 0));
		lblInformacaoCorP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "As cores primárias são as cores das letras/números do seu sistema. \r\n"
						+ "São os dados que você digita e os dados que você recebe.", "A cor primária vai afetar o que?", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		lblInformacaoCorP.setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios.png")));
		lblInformacaoCorP.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInformacaoCorP.setBounds(1182, 345, 16, 16);
		contentPane.add(lblInformacaoCorP);
		
		lblCampoImportanteCorSec = new JLabel("*");
		lblCampoImportanteCorSec.setHorizontalAlignment(SwingConstants.CENTER);
		lblCampoImportanteCorSec.setForeground(Color.WHITE);
		lblCampoImportanteCorSec.setFont(new Font("Arial", Font.PLAIN, 26));
		lblCampoImportanteCorSec.setBounds(1206, 421, 10, 24);
		contentPane.add(lblCampoImportanteCorSec);
		
		lblCampoImportanteCorPri = new JLabel("*");
		lblCampoImportanteCorPri.setHorizontalAlignment(SwingConstants.CENTER);
		lblCampoImportanteCorPri.setForeground(Color.WHITE);
		lblCampoImportanteCorPri.setFont(new Font("Arial", Font.PLAIN, 26));
		lblCampoImportanteCorPri.setBounds(1206, 345, 10, 24);
		contentPane.add(lblCampoImportanteCorPri);
		
		lblCampoImportanteFuncionamentoNome = new JLabel("*");
		lblCampoImportanteFuncionamentoNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblCampoImportanteFuncionamentoNome.setForeground(Color.WHITE);
		lblCampoImportanteFuncionamentoNome.setFont(new Font("Arial", Font.PLAIN, 26));
		lblCampoImportanteFuncionamentoNome.setBounds(419, 306, 21, 28);
		contentPane.add(lblCampoImportanteFuncionamentoNome);
		
		lblCampoImportanteNomeAcademia = new JLabel("*");
		lblCampoImportanteNomeAcademia.setForeground(new Color(255, 255, 255));
		lblCampoImportanteNomeAcademia.setHorizontalAlignment(SwingConstants.CENTER);
		lblCampoImportanteNomeAcademia.setFont(new Font("Arial", Font.PLAIN, 26));
		lblCampoImportanteNomeAcademia.setBounds(419, 182, 21, 28);
		contentPane.add(lblCampoImportanteNomeAcademia);
		btnEditarPlanos.setBounds(1116, 178, 100, 24);
		contentPane.add(btnEditarPlanos);
		
		btnEditarModalidades = new JButton("Editar");
		btnEditarModalidades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setBackground(Color.WHITE);
			    		((JButton)temp).setForeground(Color.BLACK);
			    		((JButton)temp).setBorder(new LineBorder(Color.BLACK));		    	
			    	}else {
			    		((JButton)temp).setBackground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corSecundaria()));	    	
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
			    		((JButton)temp).setBackground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corPrimaria()));			    	
			    	}
			    }
			}
		});
		btnEditarModalidades.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEditarModalidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaEditarModalidades(usuario);
			}
		});
		btnEditarModalidades.setBounds(756, 342, 100, 24);
		contentPane.add(btnEditarModalidades);
		
		btnEditarFuncionarios = new JButton("Editar");
		btnEditarFuncionarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setBackground(Color.WHITE);
			    		((JButton)temp).setForeground(Color.BLACK);
			    		((JButton)temp).setBorder(new LineBorder(Color.BLACK));		    	
			    	}else {
			    		((JButton)temp).setBackground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corSecundaria()));	    	
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
			    		((JButton)temp).setBackground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corPrimaria()));			    	
			    	}		    	
			    }
			}
		});
		btnEditarFuncionarios.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEditarFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaEditarFuncionarios(usuario);
			}
		});
		btnEditarFuncionarios.setBounds(756, 178, 100, 24);
		contentPane.add(btnEditarFuncionarios);
		
		btnEditarCustos = new JButton("Editar");
		btnEditarCustos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setBackground(Color.WHITE);
			    		((JButton)temp).setForeground(Color.BLACK);
			    		((JButton)temp).setBorder(new LineBorder(Color.BLACK));		    	
			    	}else {
			    		((JButton)temp).setBackground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corSecundaria()));	    	
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
			    		((JButton)temp).setBackground(ConfiguracaoController.corSecundaria());
			    		((JButton)temp).setForeground(ConfiguracaoController.corPrimaria());
			    		((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corPrimaria()));			    	
			    	}		    				    	
			    }
			}
		});
		btnEditarCustos.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEditarCustos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaEditarCustosBasicos(usuario);
			}
		});
		btnEditarCustos.setBounds(340, 447, 100, 24);
		contentPane.add(btnEditarCustos);
		
		textFieldNomeUsuario = new JTextField(usuario.getNomeUsuario());
		textFieldNomeUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldNomeUsuario.setColumns(10);
		textFieldNomeUsuario.setBounds(521, 518, 335, 52);
		textFieldNomeUsuario.setEditable(false);
		contentPane.add(textFieldNomeUsuario);
		
		JScrollPane scrollPanePlanos = new JScrollPane();
		scrollPanePlanos.setBounds(936, 210, 280, 91);
		contentPane.add(scrollPanePlanos);
		
		tablePlanos = new JTable();
		tablePlanos.setEnabled(false);
		tablePlanos.setOpaque(true);
		tablePlanos.setFillsViewportHeight(true);
		tablePlanos.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPanePlanos.setViewportView(tablePlanos);
		tablePlanos.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
				"Plano", "Tempo(Em mês)", "Valor", "Alunos"
			}
		){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if(column == 3) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		});
		tablePlanos.getColumnModel().getColumn(0).setPreferredWidth(85);
		tablePlanos.getColumnModel().getColumn(1).setPreferredWidth(150);
		tablePlanos.getColumnModel().getColumn(2).setPreferredWidth(90);
		tablePlanos.getColumnModel().getColumn(3).setPreferredWidth(67);
		tablePlanos.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPaneModalidades = new JScrollPane();
		scrollPaneModalidades.setBounds(521, 374, 335, 91);
		contentPane.add(scrollPaneModalidades);
		
		tableModalidades = new JTable();
		tableModalidades.setEnabled(false);
		tableModalidades.setOpaque(true);
		tableModalidades.setFillsViewportHeight(true);
		tableModalidades.setFont(new Font("Arial", Font.PLAIN, 12));	
		scrollPaneModalidades.setViewportView(tableModalidades);
		tableModalidades.setModel(new DefaultTableModel(
			new Object[][] {

			},
			new String[] {
				"Nome", "Taxa extra(Por mês)", "Alunos", "Professores"
			}
		){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if(column == 2) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		});
		
		tableModalidades.getColumnModel().getColumn(0).setPreferredWidth(90);
		tableModalidades.getColumnModel().getColumn(1).setPreferredWidth(125);
		tableModalidades.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableModalidades.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPaneFuncionarios = new JScrollPane();
		scrollPaneFuncionarios.setBounds(521, 210, 335, 91);
		contentPane.add(scrollPaneFuncionarios);
		
		tableFuncionarios = new JTable();
		tableFuncionarios.setEnabled(false);
		tableFuncionarios.setOpaque(true);
		tableFuncionarios.setFillsViewportHeight(true);
		tableFuncionarios.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneFuncionarios.setViewportView(tableFuncionarios);
		tableFuncionarios.setModel(new DefaultTableModel(
			new Object[][] {

			},
			new String[] {
				"Nome", "Cargo", "Sal\u00E1rio"
			}
		));
		tableFuncionarios.getColumnModel().getColumn(0).setPreferredWidth(120);
		tableFuncionarios.getColumnModel().getColumn(1).setPreferredWidth(85);
		tableFuncionarios.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPaneCustosBasicos = new JScrollPane();
		scrollPaneCustosBasicos.setBounds(150, 479, 290, 91);
		contentPane.add(scrollPaneCustosBasicos);
		
		tableCustosBasicos = new JTable();
		tableCustosBasicos.setEnabled(false);
		tableCustosBasicos.setOpaque(true);
		tableCustosBasicos.setFillsViewportHeight(true);
		tableCustosBasicos.setFont(new Font("Arial", Font.PLAIN, 12));
		tableCustosBasicos.getTableHeader().setReorderingAllowed(false);
		scrollPaneCustosBasicos.setViewportView(tableCustosBasicos);
		tableCustosBasicos.setModel(new DefaultTableModel(
			new Object[][] {

			},
			new String[] {
				"Nome", "Valor(Por mês)"
			}
		));
		
		lblPeriodoHorarioAS = new JLabel("\u00E0s");
		lblPeriodoHorarioAS.setForeground(Color.WHITE);
		lblPeriodoHorarioAS.setFont(new Font("Arial", Font.PLAIN, 20));
		lblPeriodoHorarioAS.setBounds(351, 378, 21, 24);
		contentPane.add(lblPeriodoHorarioAS);
		
		lblPeriodoDiaA = new JLabel("\u00E0");
		lblPeriodoDiaA.setForeground(Color.WHITE);
		lblPeriodoDiaA.setFont(new Font("Arial", Font.PLAIN, 20));
		lblPeriodoDiaA.setBounds(329, 338, 11, 24);
		contentPane.add(lblPeriodoDiaA);
		
		textFieldCorAtualSecundaria = new JTextField();
		textFieldCorAtualSecundaria.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldCorAtualSecundaria.setColumns(10);
		textFieldCorAtualSecundaria.setBounds(936, 445, 111, 32);
		contentPane.add(textFieldCorAtualSecundaria);
		
		comboBoxDiaComeco = new JComboBox();
		comboBoxDiaComeco.setModel(new DefaultComboBoxModel(new String[] {"Segunda", "Ter\u00E7a", "Quarta", "Quinta", "Sexta", "S\u00E1bado", "Domingo"}));
		comboBoxDiaComeco.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxDiaComeco.setBounds(228, 334, 93, 32);
		contentPane.add(comboBoxDiaComeco);
		
		textFieldCorAtualPrimaria = new JTextField();
		textFieldCorAtualPrimaria.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldCorAtualPrimaria.setColumns(10);
		textFieldCorAtualPrimaria.setBounds(936, 369, 111, 32);
		contentPane.add(textFieldCorAtualPrimaria);
		
		comboBoxMudarCorPrimaria = new JComboBox();
		comboBoxMudarCorPrimaria.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				controller.compararCores(usuario);
				customizeMenuBar(menuBar);
				customizeTela();
			}
		});
		comboBoxMudarCorPrimaria.setModel(new DefaultComboBoxModel(new String[] {"Amarelo", "Azul", "Branco", "Cinza", "Laranja", "Marrom", "Preto", "Rosa", "Roxo", "Verde", "Vermelho"}));
		comboBoxMudarCorPrimaria.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxMudarCorPrimaria.setBounds(1105, 369, 111, 32);
		contentPane.add(comboBoxMudarCorPrimaria);
		
		textFieldHoraAbrir = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldHoraAbrir).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##:##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldHoraAbrir.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldHoraAbrir.setColumns(10);
		textFieldHoraAbrir.setBounds(282, 374, 61, 32);
		contentPane.add(textFieldHoraAbrir);
		
		comboBoxDiaFim = new JComboBox();
		comboBoxDiaFim.setModel(new DefaultComboBoxModel(new String[] {"Segunda", "Ter\u00E7a", "Quarta", "Quinta", "Sexta", "S\u00E1bado", "Domingo"}));
		comboBoxDiaFim.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxDiaFim.setBounds(348, 334, 93, 32);
		contentPane.add(comboBoxDiaFim);
		
		textFieldNomeAcademia = new JTextField();
		textFieldNomeAcademia.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldNomeAcademia.setBounds(150, 210, 291, 52);
		contentPane.add(textFieldNomeAcademia);
		textFieldNomeAcademia.setColumns(10);
		
		comboBoxMudarCorSecundaria = new JComboBox();
		comboBoxMudarCorSecundaria.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				controller.compararCores(usuario);
				customizeMenuBar(menuBar);
				customizeTela();
			}
		});
		comboBoxMudarCorSecundaria.setModel(new DefaultComboBoxModel(new String[] {"Amarelo", "Azul", "Branco", "Cinza", "Laranja", "Marrom", "Preto", "Rosa", "Roxo", "Verde", "Vermelho"}));
		comboBoxMudarCorSecundaria.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxMudarCorSecundaria.setBounds(1105, 445, 111, 32);
		contentPane.add(comboBoxMudarCorSecundaria);
		
		textFieldHoraFechar = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldHoraFechar).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##:##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldHoraFechar.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldHoraFechar.setColumns(10);
		textFieldHoraFechar.setBounds(380, 374, 61, 32);
		contentPane.add(textFieldHoraFechar);
		
		lblFuncionarios = new JLabel("Funcion\u00E1rios:");
		lblFuncionarios.setForeground(Color.WHITE);
		lblFuncionarios.setFont(new Font("Arial", Font.PLAIN, 20));
		lblFuncionarios.setBounds(521, 178, 116, 24);
		contentPane.add(lblFuncionarios);
		
		lblCorSecundaria = new JLabel("Mudar a cor secund\u00E1ria de:");
		lblCorSecundaria.setForeground(Color.WHITE);
		lblCorSecundaria.setFont(new Font("Arial", Font.PLAIN, 20));
		lblCorSecundaria.setBounds(936, 417, 242, 24);
		contentPane.add(lblCorSecundaria);
		
		lblExceçãoFuncionario = new JLabel("*com exce\u00E7\u00E3o dos personais/professores");
		lblExceçãoFuncionario.setForeground(new Color(192, 192, 192));
		lblExceçãoFuncionario.setFont(new Font("Arial", Font.PLAIN, 12));
		lblExceçãoFuncionario.setBounds(521, 305, 230, 16);
		contentPane.add(lblExceçãoFuncionario);
		
		lblDias = new JLabel("Dias:");
		lblDias.setForeground(Color.WHITE);
		lblDias.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDias.setBounds(150, 338, 45, 24);
		contentPane.add(lblDias);
		
		lblModalidadas = new JLabel("Modalidades:");
		lblModalidadas.setForeground(Color.WHITE);
		lblModalidadas.setFont(new Font("Arial", Font.PLAIN, 20));
		lblModalidadas.setBounds(521, 342, 118, 24);
		contentPane.add(lblModalidadas);
		
		lblCorPrimaria = new JLabel("Mudar a cor primaria de:");
		lblCorPrimaria.setForeground(Color.WHITE);
		lblCorPrimaria.setFont(new Font("Arial", Font.PLAIN, 20));
		lblCorPrimaria.setBounds(936, 341, 218, 24);
		contentPane.add(lblCorPrimaria);
		
		lblParaPrimaria = new JLabel("para");
		lblParaPrimaria.setForeground(Color.WHITE);
		lblParaPrimaria.setFont(new Font("Arial", Font.PLAIN, 20));
		lblParaPrimaria.setBounds(1056, 374, 40, 24);
		contentPane.add(lblParaPrimaria);
		
		lblCustosBasicos = new JLabel("Custos B\u00E1sicos:");
		lblCustosBasicos.setForeground(Color.WHITE);
		lblCustosBasicos.setFont(new Font("Arial", Font.PLAIN, 20));
		lblCustosBasicos.setBounds(150, 447, 142, 24);
		contentPane.add(lblCustosBasicos);
		
		lblHorario = new JLabel("Hor\u00E1rio:");
		lblHorario.setForeground(Color.WHITE);
		lblHorario.setFont(new Font("Arial", Font.PLAIN, 20));
		lblHorario.setBounds(150, 378, 75, 24);
		contentPane.add(lblHorario);
		
		lblPlanos = new JLabel("Planos:");
		lblPlanos.setForeground(Color.WHITE);
		lblPlanos.setFont(new Font("Arial", Font.PLAIN, 20));
		lblPlanos.setBounds(936, 178, 65, 24);
		contentPane.add(lblPlanos);
		
		lblParaSecundaria = new JLabel("para");
		lblParaSecundaria.setForeground(Color.WHITE);
		lblParaSecundaria.setFont(new Font("Arial", Font.PLAIN, 20));
		lblParaSecundaria.setBounds(1056, 449, 40, 24);
		contentPane.add(lblParaSecundaria);
		
		lblPeriodoFuncionamento = new JLabel("Per\u00EDodo de Funcionamento:");
		lblPeriodoFuncionamento.setForeground(Color.WHITE);
		lblPeriodoFuncionamento.setFont(new Font("Arial", Font.PLAIN, 20));
		lblPeriodoFuncionamento.setBounds(150, 302, 242, 24);
		contentPane.add(lblPeriodoFuncionamento);
		
		lblUsuarioPermitidos = new JLabel("Nome de Usu\u00E1rio:");
		lblUsuarioPermitidos.setForeground(Color.WHITE);
		lblUsuarioPermitidos.setFont(new Font("Arial", Font.PLAIN, 20));
		lblUsuarioPermitidos.setBounds(521, 486, 157, 24);
		contentPane.add(lblUsuarioPermitidos);
		
		lblNomeAcademia = new JLabel("Nome da Academia:");
		lblNomeAcademia.setForeground(new Color(255, 255, 255));
		lblNomeAcademia.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomeAcademia.setBounds(150, 178, 180, 24);
		contentPane.add(lblNomeAcademia);
		
		lblConfiguracao = new JLabel("Configura\u00E7\u00F5es da Academia e Aplicativo");
		lblConfiguracao.setFont(new Font("Arial", Font.PLAIN, 36));
		lblConfiguracao.setBounds(364, 38, 637, 40);
		contentPane.add(lblConfiguracao);
		
		lblInformacaoConfiguracao = new JLabel("Atributos importantes de serem preenchidos");
		lblInformacaoConfiguracao.setForeground(new Color(0, 0, 0));
		lblInformacaoConfiguracao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblInformacaoConfiguracao.setBounds(132, 614, 308, 20);
		contentPane.add(lblInformacaoConfiguracao);
		
		lblInformacoesAlteraveis = new JLabel("Todas essas informa\u00E7\u00F5es podem ser alteradas posteriormente");
		lblInformacoesAlteraveis.setForeground(Color.BLACK);
		lblInformacoesAlteraveis.setFont(new Font("Arial", Font.PLAIN, 16));
		lblInformacoesAlteraveis.setBounds(819, 614, 437, 20);
		contentPane.add(lblInformacoesAlteraveis);
		
		lblCamposImportantes = new JLabel("*");
		lblCamposImportantes.setHorizontalAlignment(SwingConstants.CENTER);
		lblCamposImportantes.setForeground(Color.WHITE);
		lblCamposImportantes.setFont(new Font("Arial", Font.PLAIN, 26));
		lblCamposImportantes.setBounds(110, 614, 21, 28);
		contentPane.add(lblCamposImportantes);
		
		roundedPanel = new RoundedPanel(20);
		roundedPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				controller.atualizarTabelas(usuario);
			}
		
		});
		roundedPanel.setBackground(new Color(23, 23, 21, 100));
		roundedPanel.setLayout(new BorderLayout());
		contentPane.add(roundedPanel);
        
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
		if (JOptionPane.showConfirmDialog(null, mensagem, "Deseja ir para o Dashboard?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return true;
		}
		
		return false;
	}
	
	private void iniciar() {
		controller.atualizarTela(usuario);
		
	}

	private void customizeTela() {
		controller.atualizarCores(usuario);
	}
	
	/** @implNote Deixa um painel com as bordas arredondadas */
	public class RoundedPanel extends JPanel {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int cornerRadius;

	    public RoundedPanel(int cornerRadius) {
	        this.cornerRadius = cornerRadius;
	        setOpaque(false);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g.create();
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(110, 138, 1146, 472, cornerRadius, cornerRadius);
	        g2d.setColor(getBackground());
	        g2d.fill(roundedRectangle);
	        g2d.setColor(getForeground());
	        g2d.draw(roundedRectangle);
	        g2d.dispose();
	    }

	    @Override
	    public Dimension getPreferredSize() {
	        return super.getPreferredSize();
	    }
	}

	/** @implNote Altera as cores da barra de menu */
	private void customizeMenuBar(JMenuBar menuBar) {

	    menuBar.setUI(new BasicMenuBarUI() {

	        @Override
	        public void paint(Graphics g, JComponent c) {
	            g.setColor(ConfiguracaoController.corSecundaria());
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
	    comp.setBackground(ConfiguracaoController.corSecundaria());
	    comp.setForeground(ConfiguracaoController.corPrimaria());
	}

	public RoundedPanel getRoundedPanel() {
		return roundedPanel;
	}

	public void setRoundedPanel(RoundedPanel roundedPanel) {
		this.roundedPanel = roundedPanel;
	}

	public JLabel getLblInformacaoConfiguracao() {
		return lblInformacaoConfiguracao;
	}

	public void setLblInformacaoConfiguracao(JLabel lblInformacaoConfiguracao) {
		this.lblInformacaoConfiguracao = lblInformacaoConfiguracao;
	}

	public JLabel getLblExceçãoFuncionario() {
		return lblExceçãoFuncionario;
	}

	public void setLblExceçãoFuncionario(JLabel lblExceçãoFuncionario) {
		this.lblExceçãoFuncionario = lblExceçãoFuncionario;
	}

	public JLabel getLblParaPrimaria() {
		return lblParaPrimaria;
	}

	public void setLblParaPrimaria(JLabel lblParaPrimaria) {
		this.lblParaPrimaria = lblParaPrimaria;
	}

	public JLabel getLblCorPrimaria() {
		return lblCorPrimaria;
	}

	public void setLblCorPrimaria(JLabel lblCorPrimaria) {
		this.lblCorPrimaria = lblCorPrimaria;
	}

	public JLabel getLblParaSecundaria() {
		return lblParaSecundaria;
	}

	public void setLblParaSecundaria(JLabel lblParaSecundaria) {
		this.lblParaSecundaria = lblParaSecundaria;
	}

	public JLabel getLblCorSecundaria() {
		return lblCorSecundaria;
	}

	public void setLblCorSecundaria(JLabel lblCorSecundaria) {
		this.lblCorSecundaria = lblCorSecundaria;
	}

	public JLabel getLblUsuarioPermitidos() {
		return lblUsuarioPermitidos;
	}

	public void setLblUsuarioPermitidos(JLabel lblUsuarioPermitidos) {
		this.lblUsuarioPermitidos = lblUsuarioPermitidos;
	}

	public JLabel getLblConfiguracao() {
		return lblConfiguracao;
	}

	public void setLblConfiguracao(JLabel lblConfiguracao) {
		this.lblConfiguracao = lblConfiguracao;
	}

	public JLabel getLblModalidadas() {
		return lblModalidadas;
	}

	public void setLblModalidadas(JLabel lblModalidadas) {
		this.lblModalidadas = lblModalidadas;
	}

	public JLabel getLblCustosBasicos() {
		return lblCustosBasicos;
	}

	public void setLblCustosBasicos(JLabel lblCustosBasicos) {
		this.lblCustosBasicos = lblCustosBasicos;
	}

	public JLabel getLblFuncionarios() {
		return lblFuncionarios;
	}

	public void setLblFuncionarios(JLabel lblFuncionarios) {
		this.lblFuncionarios = lblFuncionarios;
	}

	public JLabel getLblPlanos() {
		return lblPlanos;
	}

	public void setLblPlanos(JLabel lblPlanos) {
		this.lblPlanos = lblPlanos;
	}

	public JLabel getLblNomeAcademia() {
		return lblNomeAcademia;
	}

	public void setLblNomeAcademia(JLabel lblNomeAcademia) {
		this.lblNomeAcademia = lblNomeAcademia;
	}

	public JLabel getLblPeriodoFuncionamento() {
		return lblPeriodoFuncionamento;
	}

	public void setLblPeriodoFuncionamento(JLabel lblPeriodoFuncionamento) {
		this.lblPeriodoFuncionamento = lblPeriodoFuncionamento;
	}

	public JLabel getLblDias() {
		return lblDias;
	}

	public void setLblDias(JLabel lblDias) {
		this.lblDias = lblDias;
	}

	public JLabel getLblHorario() {
		return lblHorario;
	}

	public void setLblHorario(JLabel lblHorario) {
		this.lblHorario = lblHorario;
	}

	public JLabel getLblPeriodoDiaA() {
		return lblPeriodoDiaA;
	}

	public void setLblPeriodoDiaA(JLabel lblPeriodoDiaA) {
		this.lblPeriodoDiaA = lblPeriodoDiaA;
	}

	public JLabel getLblInformacoesAlteraveis() {
		return lblInformacoesAlteraveis;
	}

	public void setLblInformacoesAlteraveis(JLabel lblInformacoesAlteraveis) {
		this.lblInformacoesAlteraveis = lblInformacoesAlteraveis;
	}

	public JLabel getLblPeriodoHorarioAS() {
		return lblPeriodoHorarioAS;
	}

	public void setLblPeriodoHorarioAS(JLabel lblPeriodoHorarioAS) {
		this.lblPeriodoHorarioAS = lblPeriodoHorarioAS;
	}

	public JLabel getLblInformacaoCorP() {
		return lblInformacaoCorP;
	}

	public void setLblInformacaoCorP(JLabel lblInformacaoCorP) {
		this.lblInformacaoCorP = lblInformacaoCorP;
	}

	public JLabel getLblInformacaoCorS() {
		return lblInformacaoCorS;
	}

	public void setLblInformacaoCorS(JLabel lblInformacaoCorS) {
		this.lblInformacaoCorS = lblInformacaoCorS;
	}

	public JLabel getLblCamposImportantes() {
		return lblCamposImportantes;
	}

	public void setLblCamposImportantes(JLabel lblCamposImportantes) {
		this.lblCamposImportantes = lblCamposImportantes;
	}

	public JLabel getLblCampoImportanteNomeAcademia() {
		return lblCampoImportanteNomeAcademia;
	}

	public void setLblCampoImportanteNomeAcademia(JLabel lblCampoImportanteNomeAcademia) {
		this.lblCampoImportanteNomeAcademia = lblCampoImportanteNomeAcademia;
	}

	public JLabel getLblCampoImportanteCorSec() {
		return lblCampoImportanteCorSec;
	}

	public void setLblCampoImportanteCorSec(JLabel lblCampoImportanteCorSec) {
		this.lblCampoImportanteCorSec = lblCampoImportanteCorSec;
	}

	public JLabel getLblCampoImportanteCorPri() {
		return lblCampoImportanteCorPri;
	}

	public void setLblCampoImportanteCorPri(JLabel lblCampoImportanteCorPri) {
		this.lblCampoImportanteCorPri = lblCampoImportanteCorPri;
	}

	public JLabel getLblCampoImportanteFuncionamentoNome() {
		return lblCampoImportanteFuncionamentoNome;
	}

	public void setLblCampoImportanteFuncionamentoNome(JLabel lblCampoImportanteFuncionamentoNome) {
		this.lblCampoImportanteFuncionamentoNome = lblCampoImportanteFuncionamentoNome;
	}

	public JTextField getTextFieldNomeAcademia() {
		return textFieldNomeAcademia;
	}

	public void setTextFieldNomeAcademia(JTextField textFieldNomeAcademia) {
		this.textFieldNomeAcademia = textFieldNomeAcademia;
	}

	public JTextField getTextFieldHoraAbrir() {
		return textFieldHoraAbrir;
	}

	public void setTextFieldHoraAbrir(JTextField textFieldHoraAbrir) {
		this.textFieldHoraAbrir = textFieldHoraAbrir;
	}

	public JTextField getTextFieldHoraFechar() {
		return textFieldHoraFechar;
	}

	public void setTextFieldHoraFechar(JTextField textFieldHoraFechar) {
		this.textFieldHoraFechar = textFieldHoraFechar;
	}

	public JTextField getTextFieldCorAtualPrimaria() {
		return textFieldCorAtualPrimaria;
	}

	public void setTextFieldCorAtualPrimaria(JTextField textFieldCorAtualPrimaria) {
		this.textFieldCorAtualPrimaria = textFieldCorAtualPrimaria;
	}

	public JTextField getTextFieldCorAtualSecundaria() {
		return textFieldCorAtualSecundaria;
	}

	public void setTextFieldCorAtualSecundaria(JTextField textFieldCorAtualSecundaria) {
		this.textFieldCorAtualSecundaria = textFieldCorAtualSecundaria;
	}

	public JTextField getTextFieldNomeUsuario() {
		return textFieldNomeUsuario;
	}

	public void setTextFieldNomeUsuario(JTextField textFieldNomeUsuario) {
		this.textFieldNomeUsuario = textFieldNomeUsuario;
	}

	public JTable getTableCustosBasicos() {
		return tableCustosBasicos;
	}

	public void setTableCustosBasicos(JTable tableCustosBasicos) {
		this.tableCustosBasicos = tableCustosBasicos;
	}

	public JTable getTableFuncionarios() {
		return tableFuncionarios;
	}

	public void setTableFuncionarios(JTable tableFuncionarios) {
		this.tableFuncionarios = tableFuncionarios;
	}

	public JTable getTableModalidades() {
		return tableModalidades;
	}

	public void setTableModalidades(JTable tableModalidades) {
		this.tableModalidades = tableModalidades;
	}

	public JTable getTablePlanos() {
		return tablePlanos;
	}

	public void setTablePlanos(JTable tablePlanos) {
		this.tablePlanos = tablePlanos;
	}

	public JComboBox getComboBoxMudarCorPrimaria() {
		return comboBoxMudarCorPrimaria;
	}

	public void setComboBoxMudarCorPrimaria(JComboBox comboBoxMudarCorPrimaria) {
		this.comboBoxMudarCorPrimaria = comboBoxMudarCorPrimaria;
	}

	public JComboBox getComboBoxMudarCorSecundaria() {
		return comboBoxMudarCorSecundaria;
	}

	public void setComboBoxMudarCorSecundaria(JComboBox comboBoxMudarCorSecundaria) {
		this.comboBoxMudarCorSecundaria = comboBoxMudarCorSecundaria;
	}

	public JComboBox getComboBoxDiaComeco() {
		return comboBoxDiaComeco;
	}

	public void setComboBoxDiaComeco(JComboBox comboBoxDiaComeco) {
		this.comboBoxDiaComeco = comboBoxDiaComeco;
	}

	public JComboBox getComboBoxDiaFim() {
		return comboBoxDiaFim;
	}

	public void setComboBoxDiaFim(JComboBox comboBoxDiaFim) {
		this.comboBoxDiaFim = comboBoxDiaFim;
	}

	public JButton getBtnEditarCustos() {
		return btnEditarCustos;
	}

	public void setBtnEditarCustos(JButton btnEditarCustos) {
		this.btnEditarCustos = btnEditarCustos;
	}

	public JButton getBtnEditarModalidades() {
		return btnEditarModalidades;
	}

	public void setBtnEditarModalidades(JButton btnEditarModalidades) {
		this.btnEditarModalidades = btnEditarModalidades;
	}

	public JButton getBtnEditarFuncionarios() {
		return btnEditarFuncionarios;
	}

	public void setBtnEditarFuncionarios(JButton btnEditarFuncionarios) {
		this.btnEditarFuncionarios = btnEditarFuncionarios;
	}

	public JButton getBtnEditarPlanos() {
		return btnEditarPlanos;
	}

	public void setBtnEditarPlanos(JButton btnEditarPlanos) {
		this.btnEditarPlanos = btnEditarPlanos;
	}

	public JButton getBtnConfirmarAlteracoes() {
		return btnConfirmarAlteracoes;
	}

	public void setBtnConfirmarAlteracoes(JButton btnConfirmarAlteracoes) {
		this.btnConfirmarAlteracoes = btnConfirmarAlteracoes;
	}

	public Color getCorPrimaria() {
		return corPrimaria;
	}

	public void setCorPrimaria(Color corPrimaria) {
		this.corPrimaria = corPrimaria;
	}

	public Color getCorSecundaria() {
		return corSecundaria;
	}

	public void setCorSecundaria(Color corSecundaria) {
		this.corSecundaria = corSecundaria;
	}

}

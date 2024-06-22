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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.MenuElement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicMenuBarUI;

import controller.DashboardController;
import controller.NavegacaoController;
import model.ModelCores;
import model.ModelUsuario;
import servico.Criptografar;

public class DashboardTela extends JFrame {

	private JPanel contentPane;
	private RoundedPanel painelFinancas;
	
	private JLabel lblFundoDashboard;
	private JLabel lblNomeAcademia;
	private JLabel lblEditarFinancas;
	private JLabel lblGanhos;
	private JLabel lblLucro;
	private JLabel lblGastos;
	private JLabel lblQuantPlanos;
	private JLabel lblPlanoPopular;
	private JLabel lblAlunoPorModalidade;
	private JLabel lblModalidades;
	private JLabel lblQuantProfessores;
	private JLabel lblQuantAlunos;
	private JLabel lblProfessoresEmExpediente;
	private JLabel lblPainelDados;
	private JLabel lblPainelPlanos;

	private JTextField textFieldQuantAlunos;
	private JTextField textFieldQuantProfessores;
	private JTextField textFieldQuantAluno1;
	private JTextField textFieldQuantAluno2;
	private JTextField textFieldQuantAluno3;
	private JTextField textFieldNomeModalidade1;
	private JTextField textFieldNomeModalidade2;
	private JTextField textFieldNomeModalidade3;
	private JTextField textFieldQuantPlanos;
	private JTextField textFieldPlanoPopular;
	private JTextField textFieldGastos;
	private JTextField textFieldGanhos;
	private JTextField textFieldLucro;
	private JTextField textFieldQuantProfessoresExpediente;
	
	private JButton btnVerMaisProfessores;
	private JButton btnVerMaisQuantPlanos;
	private JButton btnVerMaisPlanoPopular;
	private JButton btnVerMaisAlunos;
	private JButton btnFinancas;
	private JButton btnVerMaisProfessoresExpediente;
	
	private DashboardController controller;
	private NavegacaoController controllerNavegacao;
	
	private static DashboardTela singleton = null;
	private static ModelUsuario usuario;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DashboardTela frame = new DashboardTela(usuario);
					frame.setTitle("Dashboard");
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
	public static DashboardTela getDashboard(ModelUsuario usuario){
		if (singleton == null){
			singleton = new DashboardTela(usuario);
			singleton.setTitle("Dashboard");
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
	 */
	public DashboardTela(ModelUsuario usuario) {
		
		DashboardTela.usuario = usuario;
		
		controllerNavegacao = new NavegacaoController(this);
		controller = new DashboardController(this);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				/** @implNote Como a tela de dashboard já estará aberta, não será preciso abrir uma nova */
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
		
		btnFinancas = new JButton("Editar finan\u00E7as");
		btnFinancas.setFont(new Font("Arial", Font.PLAIN, 24));
		btnFinancas.setBorderPainted(false); 
		btnFinancas.setContentAreaFilled(false); 
		btnFinancas.setFocusPainted(false); 
		btnFinancas.setOpaque(false);
		btnFinancas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnFinancas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setForeground(Color.WHITE);
			    		painelFinancas.setBackground(Color.WHITE);
			    	}else {
			    		if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).size() == 1) {
			    			if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0).equals(new Color(0, 0, 0))) {
				    			((JButton)temp).setForeground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    			}else {
			    				((JButton)temp).setForeground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    			}
			    			
			    			painelFinancas.setBackground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    			
			    		}else {
			    			((JButton)temp).setForeground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    			painelFinancas.setBackground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    		}
			    	}
			    }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	if(usuario.getConfiguracao() == null) {
			    		((JButton)temp).setForeground(new Color(125, 125, 125));
			    		painelFinancas.setBackground(Color.WHITE);
			    	}else {
			    		if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).size() == 1) {
			    			if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0).equals(new Color(0, 0, 0))){
			    				painelFinancas.setBackground(new Color(23, 23, 21));
			    				((JButton)temp).setForeground(new Color(50, 50, 50));
			    			}else {
			    				painelFinancas.setBackground(new Color(232, 232, 234));
			    				((JButton)temp).setForeground(new Color(205, 205, 205));
			    			}

			    		}else {
			    			((JButton)temp).setForeground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(2));
			    			painelFinancas.setBackground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(2));
			    		}
			    	}
			    }
			}
		});
		btnFinancas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.paraConfiguracaoFinancas(usuario);
			}
		});
		
		textFieldQuantProfessoresExpediente = new JTextField();
		textFieldQuantProfessoresExpediente.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldQuantProfessoresExpediente.setForeground(Color.YELLOW);
		textFieldQuantProfessoresExpediente.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldQuantProfessoresExpediente.setEditable(false);
		textFieldQuantProfessoresExpediente.setColumns(10);
		textFieldQuantProfessoresExpediente.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldQuantProfessoresExpediente.setBackground(Color.BLACK);
		textFieldQuantProfessoresExpediente.setBounds(519, 370, 70, 32);
		contentPane.add(textFieldQuantProfessoresExpediente);
		
		btnVerMaisProfessoresExpediente = new JButton("Ver mais");
		btnVerMaisProfessoresExpediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.irParaRegistroProfessorEmExpediente(controller.getProfessores(usuario), usuario);
			}
		});
		btnVerMaisProfessoresExpediente.addMouseListener(new MouseAdapter() {
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
		btnVerMaisProfessoresExpediente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVerMaisProfessoresExpediente.setForeground(new Color(236, 202, 15));
		btnVerMaisProfessoresExpediente.setFont(new Font("Arial", Font.BOLD, 13));
		btnVerMaisProfessoresExpediente.setBorder(new LineBorder(new Color(236, 202, 15)));
		btnVerMaisProfessoresExpediente.setBackground(new Color(23, 23, 21));
		btnVerMaisProfessoresExpediente.setBounds(604, 370, 95, 32);
		contentPane.add(btnVerMaisProfessoresExpediente);
		
		lblProfessoresEmExpediente = new JLabel("Professores em expediente:");
		lblProfessoresEmExpediente.setForeground(new Color(236, 192, 91));
		lblProfessoresEmExpediente.setFont(new Font("Arial", Font.PLAIN, 20));
		lblProfessoresEmExpediente.setBounds(142, 374, 248, 24);
		contentPane.add(lblProfessoresEmExpediente);
		btnFinancas.setBounds(794, 336, 470, 46);
		contentPane.add(btnFinancas);
		
		textFieldLucro = new JTextField();
		textFieldLucro.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldLucro.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldLucro.setForeground(Color.YELLOW);
		textFieldLucro.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldLucro.setColumns(10);
		textFieldLucro.setBackground(Color.BLACK);
		textFieldLucro.setEditable(false);
		textFieldLucro.setBounds(1104, 281, 141, 35);
		contentPane.add(textFieldLucro);
		
		textFieldGastos = new JTextField();
		textFieldGastos.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldGastos.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldGastos.setToolTipText("");
		textFieldGastos.setForeground(Color.YELLOW);
		textFieldGastos.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldGastos.setColumns(10);
		textFieldGastos.setBackground(Color.BLACK);
		textFieldGastos.setEditable(false);
		textFieldGastos.setBounds(1104, 195, 141, 35);
		contentPane.add(textFieldGastos);
		
		textFieldGanhos = new JTextField();
		textFieldGanhos.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldGanhos.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldGanhos.setForeground(Color.YELLOW);
		textFieldGanhos.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldGanhos.setColumns(10);
		textFieldGanhos.setBackground(Color.BLACK);
		textFieldGanhos.setEditable(false);
		textFieldGanhos.setBounds(1104, 238, 141, 35);
		contentPane.add(textFieldGanhos);
		
		lblEditarFinancas = new JLabel("");
		lblEditarFinancas.setIcon(new ImageIcon(DashboardTela.class.getResource("/view/imagens/botao-financas.png")));
		lblEditarFinancas.setBounds(794, 336, 470, 46);
		contentPane.add(lblEditarFinancas);
		
		lblGanhos = new JLabel("Ganhos");
		lblGanhos.setForeground(new Color(0, 0, 0));
		lblGanhos.setFont(new Font("Arial", Font.PLAIN, 20));
		lblGanhos.setBounds(814, 244, 70, 24);
		contentPane.add(lblGanhos);
		
		lblLucro = new JLabel("Lucro/Preju\u00EDzo");
		lblLucro.setForeground(new Color(0, 0, 0));
		lblLucro.setFont(new Font("Arial", Font.PLAIN, 20));
		lblLucro.setBounds(814, 288, 126, 24);
		contentPane.add(lblLucro);
		
		lblGastos = new JLabel("Gastos");
		lblGastos.setForeground(new Color(0, 0, 0));
		lblGastos.setFont(new Font("Arial", Font.PLAIN, 20));
		lblGastos.setBounds(814, 200, 64, 24);
		contentPane.add(lblGastos);
		
		btnVerMaisQuantPlanos = new JButton("Ver mais");
		btnVerMaisQuantPlanos.addMouseListener(new MouseAdapter() {
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
		btnVerMaisQuantPlanos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.paraConfiguracaoPlanos(usuario);
			}
		});
		btnVerMaisQuantPlanos.setBorder(new LineBorder(new Color(236, 202, 15)));
		btnVerMaisQuantPlanos.setForeground(new Color(236, 202, 15));
		btnVerMaisQuantPlanos.setBackground(new Color(23, 23, 21));
		btnVerMaisQuantPlanos.setFont(new Font("Arial", Font.BOLD, 13));
		btnVerMaisQuantPlanos.setBounds(1149, 448, 95, 35);
		btnVerMaisQuantPlanos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnVerMaisQuantPlanos);
		
		btnVerMaisPlanoPopular = new JButton("Ver mais");
		btnVerMaisPlanoPopular.addMouseListener(new MouseAdapter() {
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
		btnVerMaisPlanoPopular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.paraConfiguracaoPlanos(usuario);
			}
		});
		btnVerMaisPlanoPopular.setBorder(new LineBorder(new Color(236, 202, 15)));
		btnVerMaisPlanoPopular.setForeground(new Color(236, 202, 15));
		btnVerMaisPlanoPopular.setBackground(new Color(23, 23, 21));
		btnVerMaisPlanoPopular.setFont(new Font("Arial", Font.BOLD, 13));
		btnVerMaisPlanoPopular.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVerMaisPlanoPopular.setBounds(1149, 537, 95, 35);
		contentPane.add(btnVerMaisPlanoPopular);
		
		textFieldQuantPlanos = new JTextField();
		textFieldQuantPlanos.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldQuantPlanos.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldQuantPlanos.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldQuantPlanos.setForeground(Color.YELLOW);
		textFieldQuantPlanos.setColumns(10);
		textFieldQuantPlanos.setBackground(Color.BLACK);
		textFieldQuantPlanos.setEditable(false);
		textFieldQuantPlanos.setBounds(1057, 448, 80, 35);
		contentPane.add(textFieldQuantPlanos);
		
		textFieldPlanoPopular = new JTextField();
		textFieldPlanoPopular.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPlanoPopular.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldPlanoPopular.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldPlanoPopular.setForeground(Color.YELLOW);
		textFieldPlanoPopular.setColumns(10);
		textFieldPlanoPopular.setBackground(Color.BLACK);
		textFieldPlanoPopular.setEditable(false);
		textFieldPlanoPopular.setBounds(1057, 537, 80, 35);
		contentPane.add(textFieldPlanoPopular);
		
		lblQuantPlanos = new JLabel("Quantidade de Planos");
		lblQuantPlanos.setForeground((new Color(236, 192, 91)));
		lblQuantPlanos.setFont(new Font("Arial", Font.PLAIN, 20));
		lblQuantPlanos.setBounds(814, 453, 194, 24);
		contentPane.add(lblQuantPlanos);
		
		lblPlanoPopular = new JLabel("Plano mais Popular");
		lblPlanoPopular.setForeground((new Color(236, 192, 91)));
		lblPlanoPopular.setFont(new Font("Arial", Font.PLAIN, 20));
		lblPlanoPopular.setBounds(814, 542, 169, 24);
		contentPane.add(lblPlanoPopular);
		
		btnVerMaisProfessores = new JButton("Ver mais");
		btnVerMaisProfessores.addMouseListener(new MouseAdapter() {
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
		btnVerMaisProfessores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaRegistroProfessor(usuario);
			}
		});
		btnVerMaisProfessores.setBorder(new LineBorder(new Color(236, 202, 15)));
		btnVerMaisProfessores.setForeground(new Color(236, 202, 15));
		btnVerMaisProfessores.setBackground(new Color(23, 23, 21));
		btnVerMaisProfessores.setFont(new Font("Arial", Font.BOLD, 13));
		btnVerMaisProfessores.setBounds(604, 316, 95, 32);
		btnVerMaisProfessores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnVerMaisProfessores);
		
		btnVerMaisAlunos = new JButton("Ver mais");
		btnVerMaisAlunos.addMouseListener(new MouseAdapter() {
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
		btnVerMaisAlunos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVerMaisAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerNavegacao.navegarParaRegistroAluno(usuario);
			}
		});
		btnVerMaisAlunos.setBorder(new LineBorder(new Color(236, 202, 15)));
		btnVerMaisAlunos.setForeground(new Color(236, 202, 15));
		btnVerMaisAlunos.setBackground(new Color(23, 23, 21));
		btnVerMaisAlunos.setFont(new Font("Arial", Font.BOLD, 13));
		btnVerMaisAlunos.setBounds(602, 251, 95, 32);
		contentPane.add(btnVerMaisAlunos);
		
		textFieldNomeModalidade3 = new JTextField();
		textFieldNomeModalidade3.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNomeModalidade3.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldNomeModalidade3.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldNomeModalidade3.setForeground(new Color(255, 255, 0));
		textFieldNomeModalidade3.setColumns(10);
		textFieldNomeModalidade3.setBackground(Color.BLACK);
		textFieldNomeModalidade3.setEditable(false);
		textFieldNomeModalidade3.setBounds(385, 550, 206, 35);
		contentPane.add(textFieldNomeModalidade3);
		
		textFieldNomeModalidade2 = new JTextField();
		textFieldNomeModalidade2.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNomeModalidade2.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldNomeModalidade2.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldNomeModalidade2.setForeground(new Color(255, 255, 0));
		textFieldNomeModalidade2.setColumns(10);
		textFieldNomeModalidade2.setBackground(Color.BLACK);
		textFieldNomeModalidade2.setEditable(false);
		textFieldNomeModalidade2.setBounds(385, 507, 206, 35);
		contentPane.add(textFieldNomeModalidade2);
		
		textFieldNomeModalidade1 = new JTextField();
		textFieldNomeModalidade1.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNomeModalidade1.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldNomeModalidade1.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldNomeModalidade1.setForeground(new Color(255, 255, 0));
		textFieldNomeModalidade1.setColumns(10);
		textFieldNomeModalidade1.setBackground(Color.BLACK);
		textFieldNomeModalidade1.setEditable(false);
		textFieldNomeModalidade1.setBounds(385, 464, 206, 35);
		contentPane.add(textFieldNomeModalidade1);
		
		textFieldQuantAluno3 = new JTextField();
		textFieldQuantAluno3.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldQuantAluno3.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldQuantAluno3.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldQuantAluno3.setForeground(new Color(255, 255, 0));
		textFieldQuantAluno3.setColumns(10);
		textFieldQuantAluno3.setBackground(Color.BLACK);
		textFieldQuantAluno3.setEditable(false);
		textFieldQuantAluno3.setBounds(607, 550, 91, 35);
		contentPane.add(textFieldQuantAluno3);
		
		textFieldQuantAluno2 = new JTextField();
		textFieldQuantAluno2.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldQuantAluno2.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldQuantAluno2.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldQuantAluno2.setForeground(new Color(255, 255, 0));
		textFieldQuantAluno2.setColumns(10);
		textFieldQuantAluno2.setBackground(Color.BLACK);
		textFieldQuantAluno2.setEditable(false);
		textFieldQuantAluno2.setBounds(607, 507, 91, 35);
		contentPane.add(textFieldQuantAluno2);
		
		textFieldQuantAluno1 = new JTextField();
		textFieldQuantAluno1.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldQuantAluno1.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldQuantAluno1.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldQuantAluno1.setForeground(new Color(255, 255, 0));
		textFieldQuantAluno1.setColumns(10);
		textFieldQuantAluno1.setBackground(Color.BLACK);
		textFieldQuantAluno1.setEditable(false);
		textFieldQuantAluno1.setBounds(607, 464, 91, 35);
		contentPane.add(textFieldQuantAluno1);
		
		textFieldQuantProfessores = new JTextField();
		textFieldQuantProfessores.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldQuantProfessores.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldQuantProfessores.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldQuantProfessores.setForeground(new Color(255, 255, 0));
		textFieldQuantProfessores.setColumns(10);
		textFieldQuantProfessores.setBackground(Color.BLACK);
		textFieldQuantProfessores.setEditable(false);
		textFieldQuantProfessores.setBounds(519, 316, 70, 32);
		contentPane.add(textFieldQuantProfessores);
		
		textFieldQuantAlunos = new JTextField();
		textFieldQuantAlunos.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldQuantAlunos.setBorder(new LineBorder(new Color(236, 192, 91)));
		textFieldQuantAlunos.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldQuantAlunos.setBackground(new Color(0, 0, 0));
		textFieldQuantAlunos.setForeground(new Color(255, 255, 0));
		textFieldQuantAlunos.setEditable(false);
		textFieldQuantAlunos.setBounds(628, 208, 70, 35);
		contentPane.add(textFieldQuantAlunos);
		textFieldQuantAlunos.setColumns(10);
		
		lblAlunoPorModalidade = new JLabel("N\u00B0 de Alunos");
		lblAlunoPorModalidade.setForeground((new Color(236, 192, 91)));
		lblAlunoPorModalidade.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAlunoPorModalidade.setBounds(607, 432, 91, 24);
		contentPane.add(lblAlunoPorModalidade);
		
		lblModalidades = new JLabel("Modalidades Esportivas:");
		lblModalidades.setForeground((new Color(236, 192, 91)));
		lblModalidades.setFont(new Font("Arial", Font.PLAIN, 20));
		lblModalidades.setBounds(142, 459, 225, 24);
		contentPane.add(lblModalidades);
		
		lblQuantProfessores = new JLabel("Quantidade de Professores:");
		lblQuantProfessores.setForeground((new Color(236, 192, 91)));
		lblQuantProfessores.setFont(new Font("Arial", Font.PLAIN, 20));
		lblQuantProfessores.setBounds(142, 321, 248, 24);
		contentPane.add(lblQuantProfessores);
		
		lblQuantAlunos = new JLabel("Quantidade de Alunos ativos:");
		lblQuantAlunos.setForeground((new Color(236, 192, 91)));
		lblQuantAlunos.setFont(new Font("Arial", Font.PLAIN, 20));
		lblQuantAlunos.setBounds(142, 208, 256, 24);
		contentPane.add(lblQuantAlunos);
		
		lblPainelPlanos = new JLabel("");
		lblPainelPlanos.setIcon(new ImageIcon(DashboardTela.class.getResource("/view/imagens/painel-planos-dashboard.png")));
		lblPainelPlanos.setBounds(794, 433, 470, 177);
		contentPane.add(lblPainelPlanos);
		
		lblPainelDados = new JLabel("");
		lblPainelDados.setIcon(new ImageIcon(DashboardTela.class.getResource("/view/imagens/painel-dados-dashboard.png")));
		lblPainelDados.setBounds(102, 175, 636, 435);
		contentPane.add(lblPainelDados);
		
		lblNomeAcademia = new JLabel("");
		lblNomeAcademia.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblNomeAcademia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controllerNavegacao.paraConfiguracaoPeriodo(usuario);
			}
		});
		lblNomeAcademia.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeAcademia.setForeground(new Color(236, 202, 15));
		lblNomeAcademia.setFont(new Font("Times New Roman", Font.PLAIN, 48));
		lblNomeAcademia.setBounds(396, 40, 573, 52);
		contentPane.add(lblNomeAcademia);
		
		lblFundoDashboard = new JLabel("");
		lblFundoDashboard.addMouseListener(new MouseAdapter() {
			private String resposta = "";

			@Override
			public void mouseEntered(MouseEvent e) {
				iniciar();
				if(usuario.getConfiguracao().getNomeDaAcademia().equalsIgnoreCase("") && !resposta.equalsIgnoreCase("Não")) {
					if (exibirMensagemDecisao("Você ainda não realizou sua configuração, fazer isso agora?")) {
						controllerNavegacao.navegarParaConfiguracao(usuario);
					}else {
						resposta = "Não";
						exibirMensagemInformacao("Caso deseje configurar posteriormente, é so ir em \"Perfil > Configuração\"");
					}
				}
			}
		});
		lblFundoDashboard.setBounds(0, 0, 1366, 683);
		contentPane.add(lblFundoDashboard);

		painelFinancas = new RoundedPanel(10);
		painelFinancas.setBackground(new Color(23, 23, 21, 255));
		painelFinancas.setBounds(789, 170, 470, 161);
		painelFinancas.setLayout(new BorderLayout());
		contentPane.add(painelFinancas);
		
		customizeMenuBar(menuBar);
		customizeTela();
		iniciar();
	}
	
	/** @implNote Quando for para exibir uma explicação de um dado/método ou para mostrar o resultado da acão realizada do usuário */
	public void exibirMensagemInformacao(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/** @implNote Quando o usuário precisa tomar uma decisão entre duas ou mais opções */
	public boolean exibirMensagemDecisao(String mensagem) {
		if (JOptionPane.showConfirmDialog(null, mensagem, "Deseja configurar sua conta agora?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return true;
		}
		
		return false;
	}

	private void iniciar() {
		controller.preencherTela(usuario);
		
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
	    	super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g.create();
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(788, 170, 470, 161, cornerRadius, cornerRadius);
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

	public RoundedPanel getPainelFinancas() {
		return painelFinancas;
	}

	public void setPainelFinancas(RoundedPanel painelFinancas) {
		this.painelFinancas = painelFinancas;
	}

	public JLabel getLblFundoDashboard() {
		return lblFundoDashboard;
	}

	public void setLblFundoDashboard(JLabel lblFundoDashboard) {
		this.lblFundoDashboard = lblFundoDashboard;
	}

	public JLabel getLblNomeAcademia() {
		return lblNomeAcademia;
	}

	public void setLblNomeAcademia(JLabel lblNomeAcademia) {
		this.lblNomeAcademia = lblNomeAcademia;
	}

	public JLabel getLblEditarFinancas() {
		return lblEditarFinancas;
	}

	public void setLblEditarFinancas(JLabel lblEditarFinancas) {
		this.lblEditarFinancas = lblEditarFinancas;
	}

	public JLabel getLblGanhos() {
		return lblGanhos;
	}

	public void setLblGanhos(JLabel lblGanhos) {
		this.lblGanhos = lblGanhos;
	}

	public JLabel getLblLucro() {
		return lblLucro;
	}

	public void setLblLucro(JLabel lblLucro) {
		this.lblLucro = lblLucro;
	}

	public JLabel getLblGastos() {
		return lblGastos;
	}

	public void setLblGastos(JLabel lblGastos) {
		this.lblGastos = lblGastos;
	}

	public JLabel getLblQuantPlanos() {
		return lblQuantPlanos;
	}

	public void setLblQuantPlanos(JLabel lblQuantPlanos) {
		this.lblQuantPlanos = lblQuantPlanos;
	}

	public JLabel getLblPlanoPopular() {
		return lblPlanoPopular;
	}

	public void setLblPlanoPopular(JLabel lblPlanoPopular) {
		this.lblPlanoPopular = lblPlanoPopular;
	}

	public JLabel getLblAlunoPorModalidade() {
		return lblAlunoPorModalidade;
	}

	public void setLblAlunoPorModalidade(JLabel lblAlunoPorModalidade) {
		this.lblAlunoPorModalidade = lblAlunoPorModalidade;
	}

	public JLabel getLblModalidades() {
		return lblModalidades;
	}

	public void setLblModalidades(JLabel lblModalidades) {
		this.lblModalidades = lblModalidades;
	}

	public JLabel getLblQuantProfessores() {
		return lblQuantProfessores;
	}

	public void setLblQuantProfessores(JLabel lblQuantProfessores) {
		this.lblQuantProfessores = lblQuantProfessores;
	}

	public JLabel getLblQuantAlunos() {
		return lblQuantAlunos;
	}

	public void setLblQuantAlunos(JLabel lblQuantAlunos) {
		this.lblQuantAlunos = lblQuantAlunos;
	}

	public JLabel getLblProfessoresEmExpediente() {
		return lblProfessoresEmExpediente;
	}

	public void setLblProfessoresEmExpediente(JLabel lblProfessoresEmExpediente) {
		this.lblProfessoresEmExpediente = lblProfessoresEmExpediente;
	}

	public JLabel getLblPainelDados() {
		return lblPainelDados;
	}

	public void setLblPainelDados(JLabel lblPainelDados) {
		this.lblPainelDados = lblPainelDados;
	}

	public JLabel getLblPainelPlanos() {
		return lblPainelPlanos;
	}

	public void setLblPainelPlanos(JLabel lblPainelPlanos) {
		this.lblPainelPlanos = lblPainelPlanos;
	}

	public JTextField getTextFieldQuantAlunos() {
		return textFieldQuantAlunos;
	}

	public void setTextFieldQuantAlunos(JTextField textFieldQuantAlunos) {
		this.textFieldQuantAlunos = textFieldQuantAlunos;
	}

	public JTextField getTextFieldQuantProfessores() {
		return textFieldQuantProfessores;
	}

	public void setTextFieldQuantProfessores(JTextField textFieldQuantProfessores) {
		this.textFieldQuantProfessores = textFieldQuantProfessores;
	}

	public JTextField getTextFieldQuantAluno1() {
		return textFieldQuantAluno1;
	}

	public void setTextFieldQuantAluno1(JTextField textFieldQuantAluno1) {
		this.textFieldQuantAluno1 = textFieldQuantAluno1;
	}

	public JTextField getTextFieldQuantAluno2() {
		return textFieldQuantAluno2;
	}

	public void setTextFieldQuantAluno2(JTextField textFieldQuantAluno2) {
		this.textFieldQuantAluno2 = textFieldQuantAluno2;
	}

	public JTextField getTextFieldQuantAluno3() {
		return textFieldQuantAluno3;
	}

	public void setTextFieldQuantAluno3(JTextField textFieldQuantAluno3) {
		this.textFieldQuantAluno3 = textFieldQuantAluno3;
	}

	public JTextField getTextFieldNomeModalidade1() {
		return textFieldNomeModalidade1;
	}

	public void setTextFieldNomeModalidade1(JTextField textFieldNomeModalidade1) {
		this.textFieldNomeModalidade1 = textFieldNomeModalidade1;
	}

	public JTextField getTextFieldNomeModalidade2() {
		return textFieldNomeModalidade2;
	}

	public void setTextFieldNomeModalidade2(JTextField textFieldNomeModalidade2) {
		this.textFieldNomeModalidade2 = textFieldNomeModalidade2;
	}

	public JTextField getTextFieldNomeModalidade3() {
		return textFieldNomeModalidade3;
	}

	public void setTextFieldNomeModalidade3(JTextField textFieldNomeModalidade3) {
		this.textFieldNomeModalidade3 = textFieldNomeModalidade3;
	}

	public JTextField getTextFieldQuantPlanos() {
		return textFieldQuantPlanos;
	}

	public void setTextFieldQuantPlanos(JTextField textFieldQuantPlanos) {
		this.textFieldQuantPlanos = textFieldQuantPlanos;
	}

	public JTextField getTextFieldPlanoPopular() {
		return textFieldPlanoPopular;
	}

	public void setTextFieldPlanoPopular(JTextField textFieldPlanoPopular) {
		this.textFieldPlanoPopular = textFieldPlanoPopular;
	}

	public JTextField getTextFieldGastos() {
		return textFieldGastos;
	}

	public void setTextFieldGastos(JTextField textFieldGastos) {
		this.textFieldGastos = textFieldGastos;
	}

	public JTextField getTextFieldGanhos() {
		return textFieldGanhos;
	}

	public void setTextFieldGanhos(JTextField textFieldGanhos) {
		this.textFieldGanhos = textFieldGanhos;
	}

	public JTextField getTextFieldLucro() {
		return textFieldLucro;
	}

	public void setTextFieldLucro(JTextField textFieldLucro) {
		this.textFieldLucro = textFieldLucro;
	}

	public JTextField getTextFieldQuantProfessoresExpediente() {
		return textFieldQuantProfessoresExpediente;
	}

	public void setTextFieldQuantProfessoresExpediente(JTextField textFieldQuantProfessoresExpediente) {
		this.textFieldQuantProfessoresExpediente = textFieldQuantProfessoresExpediente;
	}

	public JButton getBtnVerMaisProfessores() {
		return btnVerMaisProfessores;
	}

	public void setBtnVerMaisProfessores(JButton btnVerMaisProfessores) {
		this.btnVerMaisProfessores = btnVerMaisProfessores;
	}

	public JButton getBtnVerMaisQuantPlanos() {
		return btnVerMaisQuantPlanos;
	}

	public void setBtnVerMaisQuantPlanos(JButton btnVerMaisQuantPlanos) {
		this.btnVerMaisQuantPlanos = btnVerMaisQuantPlanos;
	}

	public JButton getBtnVerMaisPlanoPopular() {
		return btnVerMaisPlanoPopular;
	}

	public void setBtnVerMaisPlanoPopular(JButton btnVerMaisPlanoPopular) {
		this.btnVerMaisPlanoPopular = btnVerMaisPlanoPopular;
	}

	public JButton getBtnVerMaisAlunos() {
		return btnVerMaisAlunos;
	}

	public void setBtnVerMaisAlunos(JButton btnVerMaisAlunos) {
		this.btnVerMaisAlunos = btnVerMaisAlunos;
	}

	public JButton getBtnFinancas() {
		return btnFinancas;
	}

	public void setBtnFinancas(JButton btnFinancas) {
		this.btnFinancas = btnFinancas;
	}

	public JButton getBtnVerMaisProfessoresExpediente() {
		return btnVerMaisProfessoresExpediente;
	}

	public void setBtnVerMaisProfessoresExpediente(JButton btnVerMaisProfessoresExpediente) {
		this.btnVerMaisProfessoresExpediente = btnVerMaisProfessoresExpediente;
	}
	
	
}

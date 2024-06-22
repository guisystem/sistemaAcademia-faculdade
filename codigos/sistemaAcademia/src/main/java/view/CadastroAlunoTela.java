package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.MenuElement;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import controller.CadastroAlunoController;
import controller.NavegacaoController;
import model.ModelCores;
import model.ModelUsuario;

public class CadastroAlunoTela extends JFrame {

	private JPanel contentPane;
	private JLabel lblImagemCadastroAluno;
	private JLabel lblModalidades;
	private JLabel lblTotal;
	private JLabel lblPlano;
	private JLabel lblMatriculaAluno;
	private JLabel lblNascimentoAluno;
	private JLabel lblEmailAluno;
	private JLabel lblCpfAluno;
	private JLabel lblNomeAluno;
	private JLabel lblCadastroDeAluno;
	private JLabel lblPainelCadastroAluno;
	private JLabel lblTempoPagamento;
	private JLabel lblAdicionarFoto;

	private JTextField textFieldNomeAluno;
	private JTextField textFieldCpfAluno;
	private JTextField textFieldEmailAluno; 
	private JTextField textFieldDataNascimento;
	private JTextField textFieldDataMatricula;
	private JTextField textFieldTotal;
	private JTextField nomeImagemField = new JTextField(20);
	private String caminhoImagem;
	
	private JComboBox comboBoxPlano;
	private JComboBox comboBoxAdicionarModalidade;
	private JComboBox comboBoxRemoverModalidade;
	
	private JButton btnCadastrarAluno;
	private JButton btnCarregarFoto;
	private JButton btnRemoverModalidade;
	private JButton btnAdicionarModalidade;
	
	private JTable table;
	
	private NavegacaoController controllerNavegacao;
	private CadastroAlunoController controller;

	private static CadastroAlunoTela singleton = null;
	private static ModelUsuario usuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroAlunoTela frame = new CadastroAlunoTela(usuario);
					frame.setTitle("Cadastro de Aluno");
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
	public static CadastroAlunoTela getCadastroAluno(ModelUsuario usuario){
		if (singleton == null){
			singleton = new CadastroAlunoTela(usuario);
			singleton.setVisible(true);
			singleton.setLocationRelativeTo(null);
			singleton.setResizable(false);
			singleton.setTitle("Cadastro de Aluno");
		}
		
		if(singleton.isShowing() == false) {
			singleton = null;
		}
		
		return singleton;
		
	}

	/**
	 * Create the frame.
	 */
	public CadastroAlunoTela(ModelUsuario usuario) {
		
		CadastroAlunoTela.usuario = usuario;

		controllerNavegacao = new NavegacaoController(this);
		controller = new CadastroAlunoController(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1383, 744);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(new Color(0, 0, 0));
		setJMenuBar(menuBar);
		
		
		JMenu MenuCadastro = new JMenu("Cadastro");
		menuBar.add(MenuCadastro);
		
		JMenuItem MenuItemAluno = new JMenuItem("Cadastrar aluno");
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
		
		btnCadastrarAluno = new JButton("CADASTRAR ALUNO");
		btnCadastrarAluno.addMouseListener(new MouseAdapter() {
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
		btnCadastrarAluno.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCadastrarAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.cadastrarAluno(usuario);
			}
		});
		btnCadastrarAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCadastrarAluno.setBackground(new Color(0, 0, 0));
		btnCadastrarAluno.setForeground(new Color(236, 202, 15));
		btnCadastrarAluno.setBorder(new LineBorder(new Color(236, 202, 15)));
		btnCadastrarAluno.setBounds(946, 516, 220, 48);
		btnCadastrarAluno.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnCadastrarAluno);
		
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
		
		comboBoxRemoverModalidade = new JComboBox();
		comboBoxRemoverModalidade.addMouseListener(new MouseAdapter() {
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
		comboBoxRemoverModalidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxRemoverModalidade.setForeground(Color.WHITE);
		comboBoxRemoverModalidade.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBoxRemoverModalidade.setBackground(Color.BLACK);
		comboBoxRemoverModalidade.setBounds(606, 441, 150, 24);
		contentPane.add(comboBoxRemoverModalidade);
		
		comboBoxAdicionarModalidade = new JComboBox();
		comboBoxAdicionarModalidade.addMouseListener(new MouseAdapter() {
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
		comboBoxAdicionarModalidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxAdicionarModalidade.setForeground(Color.WHITE);
		comboBoxAdicionarModalidade.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBoxAdicionarModalidade.setBackground(Color.BLACK);
		comboBoxAdicionarModalidade.setBounds(606, 412, 150, 24);
		contentPane.add(comboBoxAdicionarModalidade);
		
		btnRemoverModalidade = new JButton("Remover");
		btnRemoverModalidade.addMouseListener(new MouseAdapter() {
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
		btnRemoverModalidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRemoverModalidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.removerModalidade(usuario);
			}
		});
		btnRemoverModalidade.setFont(new Font("Arial", Font.PLAIN, 12));
		btnRemoverModalidade.setBounds(760, 441, 86, 24);
		contentPane.add(btnRemoverModalidade);
		
		btnAdicionarModalidade = new JButton("Adicionar");
		btnAdicionarModalidade.addMouseListener(new MouseAdapter() {
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
		btnAdicionarModalidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionarModalidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.adicionarModalidade(usuario);
			}
		});
		btnAdicionarModalidade.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAdicionarModalidade.setBounds(760, 412, 86, 24);
		contentPane.add(btnAdicionarModalidade);
		
		lblModalidades = new JLabel("Modalidades");
		lblModalidades.setForeground(new Color(237, 198, 63));
		lblModalidades.setFont(new Font("Arial", Font.PLAIN, 20));
		lblModalidades.setBounds(606, 384, 112, 24);
		contentPane.add(lblModalidades);
		
		lblTempoPagamento = new JLabel("");
		lblTempoPagamento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTempoPagamento.setForeground(new Color(255, 255, 255));
		lblTempoPagamento.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTempoPagamento.setBounds(734, 489, 112, 16);
		contentPane.add(lblTempoPagamento);
		
		textFieldTotal = new JTextField();
		textFieldTotal.setForeground(Color.WHITE);
		textFieldTotal.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldTotal.setColumns(10);
		textFieldTotal.setBackground(Color.BLACK);
		textFieldTotal.setBounds(606, 513, 240, 53);
		textFieldTotal.setEditable(false);
		contentPane.add(textFieldTotal);
		
		lblTotal = new JLabel("Total:");
		lblTotal.setForeground(new Color(237, 198, 63));
		lblTotal.setFont(new Font("Arial", Font.PLAIN, 20));
		lblTotal.setBounds(606, 485, 52, 24);
		contentPane.add(lblTotal);
		
		textFieldDataMatricula = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldDataMatricula).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldDataMatricula.setForeground(Color.WHITE);
		textFieldDataMatricula.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldDataMatricula.setColumns(10);
		textFieldDataMatricula.setBackground(Color.BLACK);
		textFieldDataMatricula.setBounds(606, 210, 240, 53);
		contentPane.add(textFieldDataMatricula);
		
		textFieldDataNascimento = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldDataNascimento).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldDataNascimento.setForeground(Color.WHITE);
		textFieldDataNascimento.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldDataNascimento.setColumns(10);
		textFieldDataNascimento.setBackground(Color.BLACK);
		textFieldDataNascimento.setBounds(200, 513, 240, 53);
		contentPane.add(textFieldDataNascimento);
		
		lblAdicionarFoto = new JLabel("");
		lblAdicionarFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdicionarFoto.setHorizontalTextPosition(SwingConstants.LEADING);
		lblAdicionarFoto.setIcon(new ImageIcon(CadastroAlunoTela.class.getResource("/view/icones/icone-foto.png")));
		lblAdicionarFoto.setBounds(966, 190, 180, 240);
		contentPane.add(lblAdicionarFoto);
		
		comboBoxPlano = new JComboBox();
		comboBoxPlano.addMouseListener(new MouseAdapter() {
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
		comboBoxPlano.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxPlano.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				controller.atualizarValor(usuario);
				controller.atualizarPeriodo();
			}
		});
		comboBoxPlano.setForeground(new Color(255, 255, 255));
		comboBoxPlano.setBackground(new Color(0, 0, 0));
		comboBoxPlano.setFont(new Font("Arial", Font.PLAIN, 20));
		comboBoxPlano.setBounds(606, 311, 240, 53);
		contentPane.add(comboBoxPlano);
		
		textFieldEmailAluno = new JTextField();
		textFieldEmailAluno.setForeground(new Color(255, 255, 255));
		textFieldEmailAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldEmailAluno.setBackground(new Color(0, 0, 0));
		textFieldEmailAluno.setColumns(10);
		textFieldEmailAluno.setBounds(200, 412, 326, 53);
		contentPane.add(textFieldEmailAluno);
		
		textFieldCpfAluno = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldCpfAluno).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###-##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldCpfAluno.setForeground(new Color(255, 255, 255));
		textFieldCpfAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldCpfAluno.setBackground(new Color(0, 0, 0));
		textFieldCpfAluno.setColumns(10);
		textFieldCpfAluno.setBounds(200, 311, 326, 53);
		contentPane.add(textFieldCpfAluno);
		
		textFieldNomeAluno = new JTextField();
		textFieldNomeAluno.setForeground(new Color(255, 255, 255));
		textFieldNomeAluno.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldNomeAluno.setBackground(new Color(0, 0, 0));
		textFieldNomeAluno.setBounds(200, 210, 326, 53);
		contentPane.add(textFieldNomeAluno);
		textFieldNomeAluno.setColumns(10);
		
		lblPlano = new JLabel("Plano");
		lblPlano.setForeground(new Color(237, 198, 63));
		lblPlano.setFont(new Font("Arial", Font.PLAIN, 20));
		lblPlano.setBounds(606, 283, 52, 24);
		contentPane.add(lblPlano);
		
		lblMatriculaAluno = new JLabel("Data de Matr\u00EDcula");
		lblMatriculaAluno.setForeground(new Color(237, 198, 63));
		lblMatriculaAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblMatriculaAluno.setBounds(606, 182, 158, 24);
		contentPane.add(lblMatriculaAluno);
		
		lblNascimentoAluno = new JLabel("Data de Nascimento");
		lblNascimentoAluno.setForeground(new Color(237, 198, 63));
		lblNascimentoAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNascimentoAluno.setBounds(200, 485, 178, 24);
		contentPane.add(lblNascimentoAluno);
		
		lblEmailAluno = new JLabel("Email");
		lblEmailAluno.setForeground(new Color(237, 198, 63));
		lblEmailAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblEmailAluno.setBounds(200, 384, 48, 24);
		contentPane.add(lblEmailAluno);
		
		lblCpfAluno = new JLabel("CPF");
		lblCpfAluno.setForeground(new Color(237, 198, 63));
		lblCpfAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblCpfAluno.setBounds(200, 283, 39, 24);
		contentPane.add(lblCpfAluno);
		
		lblNomeAluno = new JLabel("Nome Completo");
		lblNomeAluno.setForeground(new Color(237, 198, 63));
		lblNomeAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomeAluno.setBounds(200, 182, 141, 24);
		contentPane.add(lblNomeAluno);
		
		lblCadastroDeAluno = new JLabel("Cadastro de Aluno");
		lblCadastroDeAluno.setForeground(new Color(236, 202, 15));
		lblCadastroDeAluno.setFont(new Font("Arial", Font.PLAIN, 36));
		lblCadastroDeAluno.setBounds(533, 86, 299, 40);
		contentPane.add(lblCadastroDeAluno);
		
		lblPainelCadastroAluno = new JLabel("");
		lblPainelCadastroAluno.setIcon(new ImageIcon(CadastroAlunoTela.class.getResource("/view/imagens/painel-cadastro-aluno.png")));
		lblPainelCadastroAluno.setBounds(100, 73, 1166, 537);
		contentPane.add(lblPainelCadastroAluno);
		
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
	
	private void iniciar() {
		controller.preencherComboBoxPlanos(usuario);
		controller.preencherComboBoxModalidades(usuario);
		controller.atualizarValor(usuario);
		
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

	public JLabel getLblImagemCadastroAluno() {
		return lblImagemCadastroAluno;
	}

	public void setLblImagemCadastroAluno(JLabel lblImagemCadastroAluno) {
		this.lblImagemCadastroAluno = lblImagemCadastroAluno;
	}

	public JLabel getLblModalidades() {
		return lblModalidades;
	}

	public void setLblModalidades(JLabel lblModalidades) {
		this.lblModalidades = lblModalidades;
	}

	public JLabel getLblTotal() {
		return lblTotal;
	}

	public void setLblTotal(JLabel lblTotal) {
		this.lblTotal = lblTotal;
	}

	public JLabel getLblPlano() {
		return lblPlano;
	}

	public void setLblPlano(JLabel lblPlano) {
		this.lblPlano = lblPlano;
	}

	public JLabel getLblMatriculaAluno() {
		return lblMatriculaAluno;
	}

	public void setLblMatriculaAluno(JLabel lblMatriculaAluno) {
		this.lblMatriculaAluno = lblMatriculaAluno;
	}

	public JLabel getLblNascimentoAluno() {
		return lblNascimentoAluno;
	}

	public void setLblNascimentoAluno(JLabel lblNascimentoAluno) {
		this.lblNascimentoAluno = lblNascimentoAluno;
	}

	public JLabel getLblEmailAluno() {
		return lblEmailAluno;
	}

	public void setLblEmailAluno(JLabel lblEmailAluno) {
		this.lblEmailAluno = lblEmailAluno;
	}

	public JLabel getLblCpfAluno() {
		return lblCpfAluno;
	}

	public void setLblCpfAluno(JLabel lblCpfAluno) {
		this.lblCpfAluno = lblCpfAluno;
	}

	public JLabel getLblNomeAluno() {
		return lblNomeAluno;
	}

	public void setLblNomeAluno(JLabel lblNomeAluno) {
		this.lblNomeAluno = lblNomeAluno;
	}

	public JLabel getLblCadastroDeAluno() {
		return lblCadastroDeAluno;
	}

	public void setLblCadastroDeAluno(JLabel lblCadastroDeAluno) {
		this.lblCadastroDeAluno = lblCadastroDeAluno;
	}

	public JLabel getLblPainelCadastroAluno() {
		return lblPainelCadastroAluno;
	}

	public void setLblPainelCadastroAluno(JLabel lblPainelCadastroAluno) {
		this.lblPainelCadastroAluno = lblPainelCadastroAluno;
	}

	public JLabel getLblTempoPagamento() {
		return lblTempoPagamento;
	}

	public void setLblTempoPagamento(JLabel lblTempoPagamento) {
		this.lblTempoPagamento = lblTempoPagamento;
	}

	public JLabel getLblAdicionarFoto() {
		return lblAdicionarFoto;
	}

	public void setLblAdicionarFoto(JLabel lblAdicionarFoto) {
		this.lblAdicionarFoto = lblAdicionarFoto;
	}

	public JTextField getTextFieldNomeAluno() {
		return textFieldNomeAluno;
	}

	public void setTextFieldNomeAluno(JTextField textFieldNomeAluno) {
		this.textFieldNomeAluno = textFieldNomeAluno;
	}

	public JTextField getTextFieldCpfAluno() {
		return textFieldCpfAluno;
	}

	public void setTextFieldCpfAluno(JTextField textFieldCpfAluno) {
		this.textFieldCpfAluno = textFieldCpfAluno;
	}

	public JTextField getTextFieldEmailAluno() {
		return textFieldEmailAluno;
	}

	public void setTextFieldEmailAluno(JTextField textFieldEmailAluno) {
		this.textFieldEmailAluno = textFieldEmailAluno;
	}

	public JTextField getTextFieldDataNascimento() {
		return textFieldDataNascimento;
	}

	public void setTextFieldDataNascimento(JTextField textFieldDataNascimento) {
		this.textFieldDataNascimento = textFieldDataNascimento;
	}

	public JTextField getTextFieldDataMatricula() {
		return textFieldDataMatricula;
	}

	public void setTextFieldDataMatricula(JTextField textFieldDataMatricula) {
		this.textFieldDataMatricula = textFieldDataMatricula;
	}

	public JTextField getTextFieldTotal() {
		return textFieldTotal;
	}

	public void setTextFieldTotal(JTextField textFieldTotal) {
		this.textFieldTotal = textFieldTotal;
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

	public JComboBox getComboBoxPlano() {
		return comboBoxPlano;
	}

	public void setComboBoxPlano(JComboBox comboBoxPlano) {
		this.comboBoxPlano = comboBoxPlano;
	}

	public JComboBox getComboBoxAdicionarModalidade() {
		return comboBoxAdicionarModalidade;
	}

	public void setComboBoxAdicionarModalidade(JComboBox comboBoxAdicionarModalidade) {
		this.comboBoxAdicionarModalidade = comboBoxAdicionarModalidade;
	}

	public JComboBox getComboBoxRemoverModalidade() {
		return comboBoxRemoverModalidade;
	}

	public void setComboBoxRemoverModalidade(JComboBox comboBoxRemoverModalidade) {
		this.comboBoxRemoverModalidade = comboBoxRemoverModalidade;
	}

	public JButton getBtnCadastrarAluno() {
		return btnCadastrarAluno;
	}

	public void setBtnCadastrarAluno(JButton btnCadastrarAluno) {
		this.btnCadastrarAluno = btnCadastrarAluno;
	}

	public JButton getBtnCarregarFoto() {
		return btnCarregarFoto;
	}

	public void setBtnCarregarFoto(JButton btnCarregarFoto) {
		this.btnCarregarFoto = btnCarregarFoto;
	}

	public JButton getBtnRemoverModalidade() {
		return btnRemoverModalidade;
	}

	public void setBtnRemoverModalidade(JButton btnRemoverModalidade) {
		this.btnRemoverModalidade = btnRemoverModalidade;
	}

	public JButton getBtnAdicionarModalidade() {
		return btnAdicionarModalidade;
	}

	public void setBtnAdicionarModalidade(JButton btnAdicionarModalidade) {
		this.btnAdicionarModalidade = btnAdicionarModalidade;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	
}

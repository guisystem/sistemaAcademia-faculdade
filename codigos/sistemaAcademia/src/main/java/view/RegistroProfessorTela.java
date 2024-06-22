package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.MenuElement;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicMenuBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import controller.NavegacaoController;
import controller.RegistroProfessorController;
import model.ModelCores;
import model.ModelUsuario;

public class RegistroProfessorTela extends JFrame {

	private JPanel contentPane;
	private JLabel lblImagemRegistroProfessor;
	private JLabel lblTotalDeBuscas;
	private JLabel lblPesquisarProfessor;
	private JLabel lblRegistroProfessores;
	
	private JTextField textFieldTotalDeBuscas;
	private JTextField textFieldPesquisar;

	private JTable tableRegistroProfessores;

	private RegistroProfessorController controller;
	private NavegacaoController controllerNavegacao;

	private static RegistroProfessorTela singleton = null;
	private static ModelUsuario usuario;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroProfessorTela frame = new RegistroProfessorTela(usuario);
					frame.setTitle("Registro de Professores");
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
	public static RegistroProfessorTela getRegistroProfessor(ModelUsuario usuario){
		if (singleton == null){
			singleton = new RegistroProfessorTela(usuario);
			singleton.setVisible(true);
			singleton.setLocationRelativeTo(null);
			singleton.setResizable(false);
			singleton.setTitle("Registro de Professor");
		}
		
		if(singleton.isShowing() == false) {
			singleton = null;
		}
		
		return singleton;
		
	}

	/**
	 * Create the frame.
	 */
	public RegistroProfessorTela(ModelUsuario usuario) {
		
		RegistroProfessorTela.usuario = usuario;

		controller = new RegistroProfessorController(this);
		controllerNavegacao = new NavegacaoController(this);
		
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
		
		lblTotalDeBuscas = new JLabel("Total:");
		lblTotalDeBuscas.setForeground(new Color(237, 198, 63));
		lblTotalDeBuscas.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTotalDeBuscas.setBounds(1144, 98, 30, 16);
		contentPane.add(lblTotalDeBuscas);
		
		textFieldTotalDeBuscas = new JTextField();
		textFieldTotalDeBuscas.setEditable(false);
		textFieldTotalDeBuscas.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTotalDeBuscas.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldTotalDeBuscas.setColumns(10);
		textFieldTotalDeBuscas.setBorder(new LineBorder(new Color(236, 202, 15)));
		textFieldTotalDeBuscas.setBounds(1114, 114, 60, 24);
		contentPane.add(textFieldTotalDeBuscas);
		
		lblPesquisarProfessor = new JLabel("Pesquisar professor:");
		lblPesquisarProfessor.setForeground(new Color(237, 198, 63));
		lblPesquisarProfessor.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPesquisarProfessor.setBounds(193, 98, 116, 16);
		contentPane.add(lblPesquisarProfessor);
		
		textFieldPesquisar = new JTextField();
		textFieldPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { /** @implNote Método para o usuário pesquisar na tabela */
				DefaultTableModel table = (DefaultTableModel) tableRegistroProfessores.getModel();
				TableRowSorter<DefaultTableModel> pesquisar = new TableRowSorter<>(table);
				tableRegistroProfessores.setRowSorter(pesquisar);
				
				pesquisar.setRowFilter(RowFilter.regexFilter(textFieldPesquisar.getText()));
				textFieldTotalDeBuscas.setText(tableRegistroProfessores.getRowCount() + "");
			}
		});
		textFieldPesquisar.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldPesquisar.setColumns(10);
		textFieldPesquisar.setBorder(new LineBorder(new Color(236, 202, 15)));
		textFieldPesquisar.setBounds(193, 114, 140, 24);
		contentPane.add(textFieldPesquisar);
		
		JScrollPane scrollPaneRegistroProfessores = new JScrollPane();
		scrollPaneRegistroProfessores.setBounds(193, 138, 981, 472);
		contentPane.add(scrollPaneRegistroProfessores);
		
		lblRegistroProfessores = new JLabel("Registro de Professores");
		lblRegistroProfessores.setForeground(new Color(236, 202, 15));
		lblRegistroProfessores.setFont(new Font("Arial", Font.PLAIN, 36));
		lblRegistroProfessores.setBounds(490, 52, 385, 40);
		contentPane.add(lblRegistroProfessores);
		
		tableRegistroProfessores = new JTable();
		tableRegistroProfessores.addMouseMotionListener(new MouseMotionAdapter(){
			
			public void mouseMoved(MouseEvent e){
				   
				int row = tableRegistroProfessores.rowAtPoint(e.getPoint());
				if (row > -1){
					tableRegistroProfessores.clearSelection(); 
					tableRegistroProfessores.setRowSelectionInterval(row, row);
					if(usuario.getConfiguracao() == null) {
						tableRegistroProfessores.setSelectionBackground(new Color(25, 25, 25));
						tableRegistroProfessores.setSelectionForeground(Color.WHITE);
					}else {
						if(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0).equals(new Color(255, 255, 255))) {
							tableRegistroProfessores.setSelectionBackground(new Color(225, 225, 225));		    		  
						}else {
							if(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0).equals(new Color(0, 0, 0))) {
								tableRegistroProfessores.setSelectionBackground(new Color(25, 25, 25));		    		  
							}else {
								tableRegistroProfessores.setSelectionBackground(usuario.getConfiguracao().getCorSecundariaColor(usuario));		    		  		    		  
							}
						}	
			
						tableRegistroProfessores.setSelectionForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
					}
				}else{
					if(usuario.getConfiguracao() == null) {
						tableRegistroProfessores.setSelectionBackground(Color.BLACK);
						tableRegistroProfessores.repaint();

					}else {
						if(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).size() == 1) {
							tableRegistroProfessores.setSelectionBackground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0));
						}else {
							tableRegistroProfessores.setSelectionBackground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(2));
						}
						
						tableRegistroProfessores.repaint();
					}
				}
			}
		});
		
		tableRegistroProfessores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 || e.getClickCount() == 2) {
					if (tableRegistroProfessores.getSelectedColumn() == 5) {
						controllerNavegacao.irParaDadosProfessor(controller.getProfessorSelecionado(usuario), usuario);
					}
				}
			}
		});
		 
		tableRegistroProfessores.addMouseMotionListener(new MouseMotionListener() {
		    @Override
		    public void mouseMoved(MouseEvent e) {
		    	if(tableRegistroProfessores.getRowCount() > 0 && tableRegistroProfessores.columnAtPoint(e.getPoint())==5) {
		    		tableRegistroProfessores.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		    	}else{
		    		tableRegistroProfessores.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
		    	}
		    }

		    @Override
		    public void mouseDragged(MouseEvent e) {
		    }
		});
		
		tableRegistroProfessores.setDefaultRenderer(Object.class, new CellRenderer());
		scrollPaneRegistroProfessores.setViewportView(tableRegistroProfessores);
		tableRegistroProfessores.setForeground(new Color(237, 198, 63));
		tableRegistroProfessores.setBackground(new Color(0, 0, 0));
		tableRegistroProfessores.setFont(new Font("Arial", Font.PLAIN, 16));
		tableRegistroProfessores.setRowHeight(30);
		tableRegistroProfessores.setOpaque(true);
		tableRegistroProfessores.setFillsViewportHeight(true);
		tableRegistroProfessores.getTableHeader().setReorderingAllowed(false);
		tableRegistroProfessores.getTableHeader().setResizingAllowed(false);
		tableRegistroProfessores.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Horário de Chegada", "Horário de Saída", "Salário", "Ativo", "Detalhes"
			}
			
		){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if(column != 9) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		});
		
		JTableHeader header = tableRegistroProfessores.getTableHeader();
		header.setBackground(Color.BLACK);
		header.setForeground(new Color(236, 202, 15));
		header.setFont(new Font("Arial", Font.PLAIN, 20));
		DefaultTableCellRenderer centralizado = (DefaultTableCellRenderer) header.getDefaultRenderer();
		centralizado.setHorizontalAlignment(SwingConstants.CENTER);
		
		tableRegistroProfessores.getColumnModel().getColumn(0).setPreferredWidth(250);
		tableRegistroProfessores.getColumnModel().getColumn(1).setPreferredWidth(180);
		tableRegistroProfessores.getColumnModel().getColumn(2).setPreferredWidth(180);
		tableRegistroProfessores.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableRegistroProfessores.getColumnModel().getColumn(4).setPreferredWidth(60);
		
		iniciar();
		customizeTela();
		customizeMenuBar(menuBar);
	}

	private void customizeTela() {
		controller.atualizarCores(usuario);
		
	}

	public void iniciar() {
		RegistroProfessorController.atualizarTabela(usuario);
		textFieldTotalDeBuscas.setText(tableRegistroProfessores.getRowCount() + "");
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
	
	public class CellRenderer extends DefaultTableCellRenderer {

		/**
		*
		*/
		private static final long serialVersionUID = 1L;

		public CellRenderer() {
			super();
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			this.setHorizontalAlignment(CENTER);

			return super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
		}
	}

	public JLabel getLblImagemRegistroProfessor() {
		return lblImagemRegistroProfessor;
	}

	public void setLblImagemRegistroProfessor(JLabel lblImagemRegistroProfessor) {
		this.lblImagemRegistroProfessor = lblImagemRegistroProfessor;
	}

	public JLabel getLblTotalDeBuscas() {
		return lblTotalDeBuscas;
	}

	public void setLblTotalDeBuscas(JLabel lblTotalDeBuscas) {
		this.lblTotalDeBuscas = lblTotalDeBuscas;
	}

	public JLabel getLblPesquisarProfessor() {
		return lblPesquisarProfessor;
	}

	public void setLblPesquisarProfessor(JLabel lblPesquisarProfessor) {
		this.lblPesquisarProfessor = lblPesquisarProfessor;
	}

	public JLabel getLblRegistroProfessores() {
		return lblRegistroProfessores;
	}

	public void setLblRegistroProfessores(JLabel lblRegistroProfessores) {
		this.lblRegistroProfessores = lblRegistroProfessores;
	}

	public JTextField getTextFieldTotalDeBuscas() {
		return textFieldTotalDeBuscas;
	}

	public void setTextFieldTotalDeBuscas(JTextField textFieldTotalDeBuscas) {
		this.textFieldTotalDeBuscas = textFieldTotalDeBuscas;
	}

	public JTextField getTextFieldPesquisar() {
		return textFieldPesquisar;
	}

	public void setTextFieldPesquisar(JTextField textFieldPesquisar) {
		this.textFieldPesquisar = textFieldPesquisar;
	}

	public JTable getTableRegistroProfessores() {
		return tableRegistroProfessores;
	}

	public void setTableRegistroProfessores(JTable tableRegistroProfessores) {
		this.tableRegistroProfessores = tableRegistroProfessores;
	}

}

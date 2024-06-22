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
import javax.swing.JOptionPane;
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
import controller.RegistroAlunoController;
import model.ModelCores;
import model.ModelUsuario;

public class RegistroAlunoTela extends JFrame {
	
	private JPanel contentPane;
	private JLabel lblTotalDeBuscas;
	private JLabel lblPesquisarAluno;
	private JLabel lblRegistroAlunos;
	private JLabel lblImagemFundoRegistroAluno;

	private JTextField textFieldTotalDeBuscas;
	private JTextField textFieldPesquisar;
	
	private JTable tableRegistroAlunos;

	private RegistroAlunoController controller;
	private NavegacaoController controllerNavegacao;

	private static RegistroAlunoTela singleton = null;
	private static ModelUsuario usuario;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroAlunoTela frame = new RegistroAlunoTela(usuario);
					frame.setTitle("Registro de Alunos");
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
	public static RegistroAlunoTela getRegistroAluno(ModelUsuario usuario){
		if (singleton == null){
			singleton = new RegistroAlunoTela(usuario);
			singleton.setVisible(true);
			singleton.setLocationRelativeTo(null);
			singleton.setResizable(false);
			singleton.setTitle("Registro de Aluno");
		}
		
		if(singleton.isShowing() == false) {
			singleton = null;
		}
		
		return singleton;
		
	}

	/**
	 * Create the frame.
	 */
	public RegistroAlunoTela(ModelUsuario usuario) {
		
		RegistroAlunoTela.usuario = usuario;

		controller = new RegistroAlunoController(this);
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
		
		lblTotalDeBuscas = new JLabel("Total:");
		lblTotalDeBuscas.setForeground(new Color(237, 198, 63));
		lblTotalDeBuscas.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTotalDeBuscas.setBounds(1144, 98, 30, 16);
		contentPane.add(lblTotalDeBuscas);
		
		textFieldTotalDeBuscas = new JTextField();
		textFieldTotalDeBuscas.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTotalDeBuscas.setEditable(false);
		textFieldTotalDeBuscas.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldTotalDeBuscas.setColumns(10);
		textFieldTotalDeBuscas.setBorder(new LineBorder(new Color(236, 202, 15)));
		textFieldTotalDeBuscas.setBounds(1114, 114, 60, 24);
		contentPane.add(textFieldTotalDeBuscas);
		
		lblPesquisarAluno = new JLabel("Pesquisar aluno:");
		lblPesquisarAluno.setForeground(new Color(237, 198, 63));
		lblPesquisarAluno.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPesquisarAluno.setBounds(193, 98, 94, 16);
		contentPane.add(lblPesquisarAluno);
		
		textFieldPesquisar = new JTextField();
		textFieldPesquisar.setBorder(new LineBorder(new Color(236, 202, 15)));
		textFieldPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { /** @implNote Método para o usuário pesquisar na tabela */
				DefaultTableModel table = (DefaultTableModel) tableRegistroAlunos.getModel();
				TableRowSorter<DefaultTableModel> pesquisar = new TableRowSorter<>(table);
				tableRegistroAlunos.setRowSorter(pesquisar);
				
				pesquisar.setRowFilter(RowFilter.regexFilter(textFieldPesquisar.getText()));
				textFieldTotalDeBuscas.setText(tableRegistroAlunos.getRowCount() + "");
			}
		});
		textFieldPesquisar.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldPesquisar.setBounds(193, 114, 140, 24);
		contentPane.add(textFieldPesquisar);
		textFieldPesquisar.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(193, 138, 981, 472);
		contentPane.add(scrollPane);
		
		lblRegistroAlunos = new JLabel("Registro de Alunos");
		lblRegistroAlunos.setForeground(new Color(236, 202, 15));
		lblRegistroAlunos.setFont(new Font("Arial", Font.PLAIN, 36));
		lblRegistroAlunos.setBounds(533, 52, 299, 40);
		contentPane.add(lblRegistroAlunos);
		
		tableRegistroAlunos = new JTable();
		tableRegistroAlunos.addMouseMotionListener(new MouseMotionAdapter(){
			
			public void mouseMoved(MouseEvent e){
	   
				int row = tableRegistroAlunos.rowAtPoint(e.getPoint());
				if (row > -1){
					tableRegistroAlunos.clearSelection();
					tableRegistroAlunos.setRowSelectionInterval(row, row);
					if(usuario.getConfiguracao() == null) {
						tableRegistroAlunos.setSelectionBackground(new Color(25, 25, 25));
						tableRegistroAlunos.setSelectionForeground(Color.WHITE);
					}else {
						if(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0).equals(new Color(255, 255, 255))) {
							tableRegistroAlunos.setSelectionBackground(new Color(225, 225, 225));		    		  
						}else {
							if(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0).equals(new Color(0, 0, 0))) {
								tableRegistroAlunos.setSelectionBackground(new Color(25, 25, 25));		    		  
							}else {
								tableRegistroAlunos.setSelectionBackground(usuario.getConfiguracao().getCorSecundariaColor(usuario));		    		  		    		  
							}
						}
						
						tableRegistroAlunos.setSelectionForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
					}
				}else{
					if(usuario.getConfiguracao() == null) {
						tableRegistroAlunos.setSelectionBackground(Color.BLACK);
						tableRegistroAlunos.repaint();

					}else {
						if(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).size() == 1) {
							tableRegistroAlunos.setSelectionBackground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0));
						}else {
							tableRegistroAlunos.setSelectionBackground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(2));
						}
						
						tableRegistroAlunos.repaint();
					}
				}
			}
		});
		
		tableRegistroAlunos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 || e.getClickCount() == 2) {
					if (tableRegistroAlunos.getSelectedColumn() == 2) {
						controller.atualizarProximoPagamento(controller.getDadosAlunoSelecionado(usuario), usuario);			
					}
				}
				
				if (e.getClickCount() == 1 || e.getClickCount() == 2) {
					if (tableRegistroAlunos.getSelectedColumn() == 5) {
						controllerNavegacao.irParaDadosAluno(controller.getDadosAlunoSelecionado(usuario), usuario);
					}
				}
			}
		});
		
		tableRegistroAlunos.addMouseMotionListener(new MouseMotionListener() {
		    @Override
		    public void mouseMoved(MouseEvent e) {
		    	if(tableRegistroAlunos.getRowCount() > 0 && tableRegistroAlunos.columnAtPoint(e.getPoint())==2 || 
		    			tableRegistroAlunos.getRowCount() > 0 && tableRegistroAlunos.columnAtPoint(e.getPoint())==5) {
		    		tableRegistroAlunos.setCursor(new Cursor(Cursor.HAND_CURSOR));
		    	}else{
		    		tableRegistroAlunos.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
		    	}
		    }

		    @Override
		    public void mouseDragged(MouseEvent e) {
		    }
		});
		tableRegistroAlunos.setDefaultRenderer(Object.class, new CellRenderer());
		tableRegistroAlunos.setForeground(new Color(237, 198, 63));
		tableRegistroAlunos.setBackground(new Color(0, 0, 0));
		tableRegistroAlunos.setFont(new Font("Arial", Font.PLAIN, 16));
		tableRegistroAlunos.setRowHeight(30);
		tableRegistroAlunos.getTableHeader().setReorderingAllowed(false);
		tableRegistroAlunos.getTableHeader().setResizingAllowed(false);
		tableRegistroAlunos.setOpaque(true);
		tableRegistroAlunos.setFillsViewportHeight(true);
		scrollPane.setViewportView(tableRegistroAlunos);
		tableRegistroAlunos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Próximo Pagamento", "Pagar" , "Ativo", "Total", "Detalhes",
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
		
		JTableHeader header =  tableRegistroAlunos.getTableHeader();
		header.setBackground(Color.BLACK);
		header.setForeground(new Color(236, 202, 15));
		header.setFont(new Font("Arial", Font.PLAIN, 20));
		DefaultTableCellRenderer centralizado = (DefaultTableCellRenderer) header.getDefaultRenderer();
		centralizado.setHorizontalAlignment(SwingConstants.CENTER);
		
		tableRegistroAlunos.getColumnModel().getColumn(0).setPreferredWidth(250);
		tableRegistroAlunos.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableRegistroAlunos.getColumnModel().getColumn(2).setPreferredWidth(150);
		tableRegistroAlunos.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableRegistroAlunos.getColumnModel().getColumn(4).setPreferredWidth(60);
		
		iniciar();
		customizeTela();
		customizeMenuBar(menuBar);
	}
	
	/** @implNote Quando a ação não pode ser concluída devido a algum dado incorreto ou lógica de programação */
	public void exibirMensagemErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.ERROR_MESSAGE);
	}
	
	/** @implNote Quando o usuário precisa tomar uma decisão entre duas ou mais opções */
	public boolean exibirMensagemDecisao(String mensagem) {
		if (JOptionPane.showConfirmDialog(null, mensagem, "Deseja prosseguir com a sua decisão?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return true;
		}
		
		return false;
	}

	public void iniciar() {
		RegistroAlunoController.atualizarTabela(usuario);
		textFieldTotalDeBuscas.setText(tableRegistroAlunos.getRowCount() + "");
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

	public JLabel getLblTotalDeBuscas() {
		return lblTotalDeBuscas;
	}

	public void setLblTotalDeBuscas(JLabel lblTotalDeBuscas) {
		this.lblTotalDeBuscas = lblTotalDeBuscas;
	}

	public JLabel getLblPesquisarAluno() {
		return lblPesquisarAluno;
	}

	public void setLblPesquisarAluno(JLabel lblPesquisarAluno) {
		this.lblPesquisarAluno = lblPesquisarAluno;
	}

	public JLabel getLblRegistroAlunos() {
		return lblRegistroAlunos;
	}

	public void setLblRegistroAlunos(JLabel lblRegistroAlunos) {
		this.lblRegistroAlunos = lblRegistroAlunos;
	}

	public JLabel getLblImagemFundoRegistroAluno() {
		return lblImagemFundoRegistroAluno;
	}

	public void setLblImagemFundoRegistroAluno(JLabel lblImagemFundoRegistroAluno) {
		this.lblImagemFundoRegistroAluno = lblImagemFundoRegistroAluno;
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

	public JTable getTableRegistroAlunos() {
		return tableRegistroAlunos;
	}

	public void setTableRegistroAlunos(JTable tableRegistroAlunos) {
		this.tableRegistroAlunos = tableRegistroAlunos;
	}

	
}



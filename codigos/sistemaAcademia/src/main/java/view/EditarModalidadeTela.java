package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controller.ConfiguracaoController;
import controller.EditarModalidadeController;
import model.ModelModalidade;
import model.ModelUsuario;

public class EditarModalidadeTela extends JDialog {

	private JPanel contentPane;
	private JLabel lblModalidades;
	private JLabel lblNomeModalidade;
	private JLabel lblTaxaExtraModalidade;
	private JLabel lblInformacao;

	private JTextField textFieldNomeModalidade;
	private JTextField textFieldlTaxaExtraModalidade;
	
	private JButton btnAdicionar;
	private JButton btnAtualizar;
	private JButton btnExcluir;

	private JTable tableModalidades;
	
	private EditarModalidadeController controller;
	
	private static ModelUsuario usuario;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarModalidadeTela dialog = new EditarModalidadeTela(usuario);
					dialog.setTitle("Editar modalidades");
	                dialog.setLocationRelativeTo(null);
	                dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditarModalidadeTela(ModelUsuario usuario) {
		super();
	    setModal(true);
	    
	    EditarModalidadeTela.usuario = usuario;

	    controller= new EditarModalidadeController(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 561, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblModalidades = new JLabel("Modalidades");
		lblModalidades.setHorizontalAlignment(SwingConstants.CENTER);
		lblModalidades.setFont(new Font("Arial", Font.PLAIN, 28));
		lblModalidades.setBounds(192, 28, 160, 32);
		contentPane.add(lblModalidades);
		
		lblNomeModalidade = new JLabel("Nome:");
		lblNomeModalidade.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomeModalidade.setBounds(30, 112, 57, 24);
		contentPane.add(lblNomeModalidade);
		
		textFieldNomeModalidade = new JTextField();
		textFieldNomeModalidade.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldNomeModalidade.setBounds(95, 108, 200, 32);
		contentPane.add(textFieldNomeModalidade);
		textFieldNomeModalidade.setColumns(10);
		
		lblTaxaExtraModalidade = new JLabel("Taxa Extra:");
		lblTaxaExtraModalidade.setFont(new Font("Arial", Font.PLAIN, 20));
		lblTaxaExtraModalidade.setBounds(325, 112, 101, 24);
		contentPane.add(lblTaxaExtraModalidade);
		
		textFieldlTaxaExtraModalidade = new JTextField();
		textFieldlTaxaExtraModalidade.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldlTaxaExtraModalidade.setColumns(10);
		textFieldlTaxaExtraModalidade.setBounds(434, 108, 80, 32);
		contentPane.add(textFieldlTaxaExtraModalidade);
		
		btnAdicionar = new JButton("ADICIONAR");
		btnAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	((JButton)temp).setBackground(ConfiguracaoController.corPrimaria());
			    	((JButton)temp).setForeground(ConfiguracaoController.corSecundaria());
			    	((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corSecundaria()));			    	
			    }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	((JButton)temp).setBackground(ConfiguracaoController.corSecundaria());
			    	((JButton)temp).setForeground(ConfiguracaoController.corPrimaria());
			    	((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corPrimaria()));			    	
			    }
			}
		});
		btnAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.adicionarModalidade(usuario);
			}
		});
		btnAdicionar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAdicionar.setBounds(30, 188, 120, 32);
		contentPane.add(btnAdicionar);
		
		btnAtualizar = new JButton("ATUALIZAR");
		btnAtualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	((JButton)temp).setBackground(ConfiguracaoController.corPrimaria());
			    	((JButton)temp).setForeground(ConfiguracaoController.corSecundaria());
			    	((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corSecundaria()));			    	
			    }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	((JButton)temp).setBackground(ConfiguracaoController.corSecundaria());
			    	((JButton)temp).setForeground(ConfiguracaoController.corPrimaria());
			    	((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corPrimaria()));			    	
			    }
			}
		});
		btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { /** @implNote valor 0 seleciona a modalidade, qualquer outro seleciona a especialidade */
				controller.atualizarModalidade((ModelModalidade) controller.getDados(usuario, 0), usuario); 
			}
		});
		btnAtualizar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAtualizar.setBounds(180, 188, 120, 32);
		contentPane.add(btnAtualizar);
		
		btnExcluir = new JButton("EXCLUIR");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	((JButton)temp).setBackground(ConfiguracaoController.corPrimaria());
			    	((JButton)temp).setForeground(ConfiguracaoController.corSecundaria());
			    	((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corSecundaria()));			    	
			    }
			}

			@Override
			public void mouseExited(MouseEvent e) {
			    Object temp = e.getSource();
			    if (temp instanceof JButton) {
			    	((JButton)temp).setBackground(ConfiguracaoController.corSecundaria());
			    	((JButton)temp).setForeground(ConfiguracaoController.corPrimaria());
			    	((JButton)temp).setBorder(new LineBorder(ConfiguracaoController.corPrimaria()));			    	
			    }
			}
		});
		btnExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { /** @implNote valor 0 seleciona a modalidade, qualquer outro seleciona a especialidade */
				controller.excluirModalidade((ModelModalidade) controller.getDados(usuario, 0), usuario);
			}
		});
		btnExcluir.setFont(new Font("Arial", Font.PLAIN, 12));
		btnExcluir.setBounds(394, 188, 120, 32);
		contentPane.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 260, 547, 148);
		contentPane.add(scrollPane);
		
		tableModalidades = new JTable();
		tableModalidades.setOpaque(true);
		tableModalidades.setFillsViewportHeight(true);
		tableModalidades.setRowHeight(20);
		tableModalidades.getTableHeader().setReorderingAllowed(false);
		tableModalidades.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 || e.getClickCount() == 2) {
					if (tableModalidades.getSelectedRow() > -1) {
						controller.mostrarDados();
					}
				}
			}
		});
		tableModalidades.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(tableModalidades.getSelectedRow() != -1) {
					controller.mostrarDados();					
				}
			}
		});
		tableModalidades.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome da Modalidade", "Taxa Extra"
			}
		){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if(column > -1) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		});
		tableModalidades.getColumnModel().getColumn(0).setPreferredWidth(120);
		scrollPane.setViewportView(tableModalidades);
		tableModalidades.setFont(new Font("Arial", Font.PLAIN, 12));
		
		lblInformacao = new JLabel("O que s\u00E3o as modalidades e taxas?");
		lblInformacao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Modalidades: São as atividades físicas que os seus alunos podem praticar em sua academia. \r\n"
						+ "Além disso, os professores poderão ter a sua especialidade em alguma dessas modalidades. \r\n"
						+ "Exemplos: Musculação, Dança, Ginástica, Aeróbicos, Artes Marciais, etc. \r\n\r\n"
						+ "Taxas: Tirando o valor do plano, o aluno terá que pagar uma taxa a mais para praticar \r\n"
						+ "determinada modalidade? \r\n\r\nSe sim: Coloque o valor. \r\nSe não: Coloque 0.",
						"Informação - O que são as modalidades e taxas?", JOptionPane.WARNING_MESSAGE);
			}
		});
		lblInformacao.setIcon(new ImageIcon(EditarModalidadeTela.class.getResource("/view/icones/informacoes-usuarios.png")));
		lblInformacao.setForeground(Color.GRAY);
		lblInformacao.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInformacao.setBounds(95, 144, 187, 16);
		contentPane.add(lblInformacao);
		
		iniciar();
		customizeTela();
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
		if (JOptionPane.showConfirmDialog(null, mensagem, "Deseja prosseguir com a sua decisão?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return true;
		}
		
		return false;
	}

	private void iniciar() {
		controller.atualizarTabela(usuario);
		
	}
	
	private void customizeTela() {
		controller.atualizarCores(usuario);
	}

	public JLabel getLblModalidades() {
		return lblModalidades;
	}

	public void setLblModalidades(JLabel lblModalidades) {
		this.lblModalidades = lblModalidades;
	}

	public JLabel getLblNomeModalidade() {
		return lblNomeModalidade;
	}

	public void setLblNomeModalidade(JLabel lblNomeModalidade) {
		this.lblNomeModalidade = lblNomeModalidade;
	}

	public JLabel getLblTaxaExtraModalidade() {
		return lblTaxaExtraModalidade;
	}

	public void setLblTaxaExtraModalidade(JLabel lblTaxaExtraModalidade) {
		this.lblTaxaExtraModalidade = lblTaxaExtraModalidade;
	}

	public JLabel getLblInformacao() {
		return lblInformacao;
	}

	public void setLblInformacao(JLabel lblInformacao) {
		this.lblInformacao = lblInformacao;
	}

	public JTextField getTextFieldNomeModalidade() {
		return textFieldNomeModalidade;
	}

	public void setTextFieldNomeModalidade(JTextField textFieldNomeModalidade) {
		this.textFieldNomeModalidade = textFieldNomeModalidade;
	}

	public JTextField getTextFieldlTaxaExtraModalidade() {
		return textFieldlTaxaExtraModalidade;
	}

	public void setTextFieldlTaxaExtraModalidade(JTextField textFieldlTaxaExtraModalidade) {
		this.textFieldlTaxaExtraModalidade = textFieldlTaxaExtraModalidade;
	}

	public JButton getBtnAdicionar() {
		return btnAdicionar;
	}

	public void setBtnAdicionar(JButton btnAdicionar) {
		this.btnAdicionar = btnAdicionar;
	}

	public JButton getBtnAtualizar() {
		return btnAtualizar;
	}

	public void setBtnAtualizar(JButton btnAtualizar) {
		this.btnAtualizar = btnAtualizar;
	}

	public JButton getBtnExcluir() {
		return btnExcluir;
	}

	public void setBtnExcluir(JButton btnExcluir) {
		this.btnExcluir = btnExcluir;
	}

	public JTable getTableModalidades() {
		return tableModalidades;
	}

	public void setTableModalidades(JTable tableModalidades) {
		this.tableModalidades = tableModalidades;
	}

}

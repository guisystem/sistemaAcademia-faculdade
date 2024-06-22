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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controller.ConfiguracaoController;
import controller.EditarFuncionarioController;
import controller.helper.ConfiguracaoHelper;
import model.ModelUsuario;

public class EditarFuncionariosTela extends JDialog {

	private JPanel contentPane;
	private JLabel lblFuncionario;
	private JLabel lblNomeFuncionario;
	private JLabel lblCargoFuncionario;
	private JLabel lblSalarioFuncionario;
	private JLabel lblInformacao;

	private JTextField textFieldNomeFuncionario;
	private JTextField textFieldCargoFuncionario;
	private JTextField textFieldSalarioFuncionario;
	
	private JTable tableFuncionarios;

	private JButton btnAtualizar;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	
	private EditarFuncionarioController controller;

	private static ModelUsuario usuario;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarFuncionariosTela dialog = new EditarFuncionariosTela(usuario);
	                dialog.setTitle("Editar funcionários");
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
	public EditarFuncionariosTela(ModelUsuario usuario) {
		super();
	    setModal(true);
	    
	    EditarFuncionariosTela.usuario = usuario;

	    controller = new EditarFuncionarioController(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblFuncionario = new JLabel("Funcion\u00E1rios");
		lblFuncionario.setFont(new Font("Arial", Font.PLAIN, 28));
		lblFuncionario.setBounds(295, 28, 160, 32);
		contentPane.add(lblFuncionario);
		
		lblNomeFuncionario = new JLabel("Nome:");
		lblNomeFuncionario.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomeFuncionario.setBounds(30, 112, 57, 24);
		contentPane.add(lblNomeFuncionario);
		
		textFieldNomeFuncionario = new JTextField();
		textFieldNomeFuncionario.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldNomeFuncionario.setBounds(95, 108, 225, 32);
		contentPane.add(textFieldNomeFuncionario);
		textFieldNomeFuncionario.setColumns(10);
		
		lblCargoFuncionario = new JLabel("Cargo:");
		lblCargoFuncionario.setFont(new Font("Arial", Font.PLAIN, 20));
		lblCargoFuncionario.setBounds(350, 112, 60, 24);
		contentPane.add(lblCargoFuncionario);
		
		textFieldCargoFuncionario = new JTextField();
		textFieldCargoFuncionario.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldCargoFuncionario.setColumns(10);
		textFieldCargoFuncionario.setBounds(418, 108, 100, 32);
		contentPane.add(textFieldCargoFuncionario);
		
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
				controller.adicionarFuncionario(usuario);
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
			public void actionPerformed(ActionEvent e) {
				controller.atualizarFuncionario(controller.getDados(usuario), usuario);
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
			public void actionPerformed(ActionEvent e) {
				controller.excluirFuncionario(controller.getDados(usuario), usuario);
			}
		});
		btnExcluir.setFont(new Font("Arial", Font.PLAIN, 12));
		btnExcluir.setBounds(583, 188, 120, 32);
		contentPane.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 260, 736, 148);
		contentPane.add(scrollPane);
		
		tableFuncionarios = new JTable();
		tableFuncionarios.setOpaque(true);
		tableFuncionarios.setFillsViewportHeight(true);
		tableFuncionarios.setRowHeight(20);
		tableFuncionarios.getTableHeader().setReorderingAllowed(false);
		tableFuncionarios.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 || e.getClickCount() == 2) {
					if (tableFuncionarios.getSelectedRow() > -1) {
						controller.mostrarDados();
					}
				}
			}
		});
		tableFuncionarios.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(tableFuncionarios.getSelectedRow() != -1) {
					controller.mostrarDados();					
				}
			}
		});
		tableFuncionarios.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome do Funcionario", "Cargo", "Salário"
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
		tableFuncionarios.getColumnModel().getColumn(0).setPreferredWidth(120);
		scrollPane.setViewportView(tableFuncionarios);
		tableFuncionarios.setFont(new Font("Arial", Font.PLAIN, 12));
		
		lblSalarioFuncionario = new JLabel("Sal\u00E1rio:");
		lblSalarioFuncionario.setFont(new Font("Arial", Font.PLAIN, 20));
		lblSalarioFuncionario.setBounds(548, 112, 67, 24);
		contentPane.add(lblSalarioFuncionario);
		
		textFieldSalarioFuncionario = new JTextField();
		textFieldSalarioFuncionario.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldSalarioFuncionario.setColumns(10);
		textFieldSalarioFuncionario.setBounds(623, 108, 80, 32);
		contentPane.add(textFieldSalarioFuncionario);
		
		lblInformacao = new JLabel("Quais funcion\u00E1rios adicionar?");
		lblInformacao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Os funcionários que você deve cadastrar são as pessoas que trabalham para a academia, seja de \r\n"
						+ "forma interna ou externa ao ambiente de musculação. \r\n\r\n"
						+ "Exemplos de funcionarios: Recepcionista, Diarista, Social Media, Etc. \r\n\r\n"
						+ "ATENÇÃO: Professor/Personais não se encaixam aqui. O Cadastro desses profissionais está em: \r\nCadastro > Cadastro de Professores.",
						"Informação - Quais funcionários adicionar?", JOptionPane.WARNING_MESSAGE);
			}
		});
		lblInformacao.setIcon(new ImageIcon(EditarFuncionariosTela.class.getResource("/view/icones/informacoes-usuarios.png")));
		lblInformacao.setForeground(Color.GRAY);
		lblInformacao.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInformacao.setBounds(95, 144, 162, 16);
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

	public JLabel getLblFuncionario() {
		return lblFuncionario;
	}

	public void setLblFuncionario(JLabel lblFuncionario) {
		this.lblFuncionario = lblFuncionario;
	}

	public JLabel getLblNomeFuncionario() {
		return lblNomeFuncionario;
	}

	public void setLblNomeFuncionario(JLabel lblNomeFuncionario) {
		this.lblNomeFuncionario = lblNomeFuncionario;
	}

	public JLabel getLblCargoFuncionario() {
		return lblCargoFuncionario;
	}

	public void setLblCargoFuncionario(JLabel lblCargoFuncionario) {
		this.lblCargoFuncionario = lblCargoFuncionario;
	}

	public JLabel getLblSalarioFuncionario() {
		return lblSalarioFuncionario;
	}

	public void setLblSalarioFuncionario(JLabel lblSalarioFuncionario) {
		this.lblSalarioFuncionario = lblSalarioFuncionario;
	}

	public JLabel getLblInformacao() {
		return lblInformacao;
	}

	public void setLblInformacao(JLabel lblInformacao) {
		this.lblInformacao = lblInformacao;
	}

	public JTextField getTextFieldNomeFuncionario() {
		return textFieldNomeFuncionario;
	}

	public void setTextFieldNomeFuncionario(JTextField textFieldNomeFuncionario) {
		this.textFieldNomeFuncionario = textFieldNomeFuncionario;
	}

	public JTextField getTextFieldCargoFuncionario() {
		return textFieldCargoFuncionario;
	}

	public void setTextFieldCargoFuncionario(JTextField textFieldCargoFuncionario) {
		this.textFieldCargoFuncionario = textFieldCargoFuncionario;
	}

	public JTextField getTextFieldSalarioFuncionario() {
		return textFieldSalarioFuncionario;
	}

	public void setTextFieldSalarioFuncionario(JTextField textFieldSalarioFuncionario) {
		this.textFieldSalarioFuncionario = textFieldSalarioFuncionario;
	}

	public JTable getTableFuncionarios() {
		return tableFuncionarios;
	}

	public void setTableFuncionarios(JTable tableFuncionarios) {
		this.tableFuncionarios = tableFuncionarios;
	}

	public JButton getBtnAtualizar() {
		return btnAtualizar;
	}

	public void setBtnAtualizar(JButton btnAtualizar) {
		this.btnAtualizar = btnAtualizar;
	}

	public JButton getBtnAdicionar() {
		return btnAdicionar;
	}

	public void setBtnAdicionar(JButton btnAdicionar) {
		this.btnAdicionar = btnAdicionar;
	}

	public JButton getBtnExcluir() {
		return btnExcluir;
	}

	public void setBtnExcluir(JButton btnExcluir) {
		this.btnExcluir = btnExcluir;
	}

}

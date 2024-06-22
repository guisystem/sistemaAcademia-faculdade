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
import controller.EditarCustoController;
import model.ModelUsuario;

public class EditarCustosTela extends JDialog {

	private JPanel contentPane;
	private JLabel lblCustosBasicos;
	private JLabel lblNomeCusto;
	private JLabel lblValorCusto;
	private JLabel lblInformacao;

	private JTextField textFieldNomeCusto;
	private JTextField textFieldValorCusto;
	
	private JTable tableCustosBasicos;
	
	private JButton btnAdicionar;
	private JButton btnAtualizar;
	private JButton btnExcluir;
	
	private EditarCustoController controller;
	private static ModelUsuario usuario;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
	                EditarCustosTela dialog = new EditarCustosTela(usuario);
	                dialog.setTitle("Editar custos básicos");
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
	public EditarCustosTela(ModelUsuario usuario) {
		super();
	    setModal(true);
	    
	    EditarCustosTela.usuario = usuario;
		
	    controller = new EditarCustoController(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 514, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblCustosBasicos = new JLabel("Custos B\u00E1sicos");
		lblCustosBasicos.setFont(new Font("Arial", Font.PLAIN, 28));
		lblCustosBasicos.setBounds(152, 28, 192, 32);
		contentPane.add(lblCustosBasicos);
		
		lblNomeCusto = new JLabel("Nome:");
		lblNomeCusto.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomeCusto.setBounds(30, 112, 59, 24);
		contentPane.add(lblNomeCusto);
		
		textFieldNomeCusto = new JTextField();
		textFieldNomeCusto.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldNomeCusto.setBounds(97, 108, 200, 32);
		contentPane.add(textFieldNomeCusto);
		textFieldNomeCusto.setColumns(10);
		
		lblValorCusto = new JLabel("Valor:");
		lblValorCusto.setFont(new Font("Arial", Font.PLAIN, 20));
		lblValorCusto.setBounds(327, 112, 52, 24);
		contentPane.add(lblValorCusto);
		
		textFieldValorCusto = new JTextField();
		textFieldValorCusto.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldValorCusto.setColumns(10);
		textFieldValorCusto.setBounds(387, 108, 80, 32);
		contentPane.add(textFieldValorCusto);
		
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
				controller.adicionarCusto(usuario);
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
				controller.atualizarCustos(controller.getDados(usuario), usuario);
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
				controller.excluirCustos(controller.getDados(usuario), usuario);
			}
		});
		btnExcluir.setFont(new Font("Arial", Font.PLAIN, 12));
		btnExcluir.setBounds(347, 188, 120, 32);
		contentPane.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 260, 500, 148);
		contentPane.add(scrollPane);
		
		tableCustosBasicos = new JTable();

		tableCustosBasicos.setOpaque(true);
		tableCustosBasicos.setFillsViewportHeight(true);
		tableCustosBasicos.setRowHeight(20);
		tableCustosBasicos.getTableHeader().setReorderingAllowed(false);
		tableCustosBasicos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 || e.getClickCount() == 2) {
					if (tableCustosBasicos.getSelectedRow() > -1) {
						controller.mostrarDados();
					}
				}
			}
		});
		tableCustosBasicos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(tableCustosBasicos.getSelectedRow() != -1) {
					controller.mostrarDados();					
				}
			}
		});
		tableCustosBasicos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome do Custo", "Valor"
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
		tableCustosBasicos.getColumnModel().getColumn(0).setPreferredWidth(120);
		scrollPane.setViewportView(tableCustosBasicos);
		tableCustosBasicos.setFont(new Font("Arial", Font.PLAIN, 12));
		
		lblInformacao = new JLabel("O que s\u00E3o custos b\u00E1sicos?");
		lblInformacao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Custos Básicos são os custos que você terá por mês para manter a academia funcionando\r\n"
						+ "de forma adequada.\r\n\r\nExemplos: Luz, Água, Manutenção, Etc.",
						"Informação - O que são os custos básicos?", JOptionPane.WARNING_MESSAGE);
			}
		});
		lblInformacao.setForeground(new Color(128, 128, 128));
		lblInformacao.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInformacao.setIcon(new ImageIcon(EditarCustosTela.class.getResource("/view/icones/informacoes-usuarios.png")));
		lblInformacao.setBounds(97, 144, 146, 16);
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

	public JLabel getLblCustosBasicos() {
		return lblCustosBasicos;
	}

	public void setLblCustosBasicos(JLabel lblCustosBasicos) {
		this.lblCustosBasicos = lblCustosBasicos;
	}

	public JLabel getLblNomeCusto() {
		return lblNomeCusto;
	}

	public void setLblNomeCusto(JLabel lblNomeCusto) {
		this.lblNomeCusto = lblNomeCusto;
	}

	public JLabel getLblValorCusto() {
		return lblValorCusto;
	}

	public void setLblValorCusto(JLabel lblValorCusto) {
		this.lblValorCusto = lblValorCusto;
	}

	public JLabel getLblInformacao() {
		return lblInformacao;
	}

	public void setLblInformacao(JLabel lblInformacao) {
		this.lblInformacao = lblInformacao;
	}

	public JTextField getTextFieldNomeCusto() {
		return textFieldNomeCusto;
	}

	public void setTextFieldNomeCusto(JTextField textFieldNomeCusto) {
		this.textFieldNomeCusto = textFieldNomeCusto;
	}

	public JTextField getTextFieldValorCusto() {
		return textFieldValorCusto;
	}

	public void setTextFieldValorCusto(JTextField textFieldValorCusto) {
		this.textFieldValorCusto = textFieldValorCusto;
	}

	public JTable getTableCustosBasicos() {
		return tableCustosBasicos;
	}

	public void setTableCustosBasicos(JTable tableCustosBasicos) {
		this.tableCustosBasicos = tableCustosBasicos;
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

}

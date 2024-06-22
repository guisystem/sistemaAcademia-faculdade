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
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

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
import controller.EditarPlanoController;
import controller.helper.ConfiguracaoHelper;
import model.ModelUsuario;
import java.awt.event.MouseMotionAdapter;

public class EditarPlanosTela extends JDialog {

	private JPanel contentPane;
	private JLabel lblPlanos;
	private JLabel lblNomePlano;
	private JLabel lblTempoPlano;
	private JLabel lblValorPlano;
	private JLabel lblMesesTempoPlano;
	private JLabel lblInformacao;

	private JTextField textFieldNomePlano;
	private JTextField textFieldTempoPlano;
	private JTextField textFieldValorPlano;
	
	private JButton btnAdicionar;
	private JButton btnAtualizar;
	private JButton btnExcluir;

	private JTable tablePlanos;

	private EditarPlanoController controller;
	
	private static ModelUsuario usuario;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarPlanosTela dialog = new EditarPlanosTela(usuario);
					dialog.setTitle("Editar planos");
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
	public EditarPlanosTela(ModelUsuario usuario) {
		super();
	    setModal(true);
	    
	    EditarPlanosTela.usuario = usuario;

	    controller = new EditarPlanoController(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 719, 445);
		contentPane = new JPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				controller.atualizarLabelTempo();
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPlanos = new JLabel("Planos");
		lblPlanos.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlanos.setFont(new Font("Arial", Font.PLAIN, 28));
		lblPlanos.setBounds(306, 28, 90, 32);
		contentPane.add(lblPlanos);
		
		lblNomePlano = new JLabel("Nome:");
		lblNomePlano.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomePlano.setBounds(30, 112, 57, 24);
		contentPane.add(lblNomePlano);
		
		textFieldNomePlano = new JTextField();
		textFieldNomePlano.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldNomePlano.setBounds(95, 108, 200, 32);
		contentPane.add(textFieldNomePlano);
		textFieldNomePlano.setColumns(10);
		
		lblTempoPlano = new JLabel("Tempo:");
		lblTempoPlano.setFont(new Font("Arial", Font.PLAIN, 20));
		lblTempoPlano.setBounds(325, 112, 67, 24);
		contentPane.add(lblTempoPlano);
		
		textFieldTempoPlano = new JTextField();
		textFieldTempoPlano.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldTempoPlano.setColumns(10);
		textFieldTempoPlano.setBounds(400, 108, 40, 32);
		contentPane.add(textFieldTempoPlano);
		
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
				controller.adicionarPlano(usuario);
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
				controller.atualizarPlano(controller.getDados(usuario), usuario);
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
				controller.excluirPlano(controller.getDados(usuario), usuario);
			}
		});
		btnExcluir.setFont(new Font("Arial", Font.PLAIN, 12));
		btnExcluir.setBounds(552, 188, 120, 32);
		contentPane.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 260, 705, 148);
		contentPane.add(scrollPane);
		
		tablePlanos = new JTable();
		tablePlanos.setOpaque(true);
		tablePlanos.setFillsViewportHeight(true);
		tablePlanos.setRowHeight(20);
		tablePlanos.getTableHeader().setReorderingAllowed(false);
		tablePlanos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 || e.getClickCount() == 2) {
					if (tablePlanos.getSelectedRow() > -1) {
						controller.mostrarDados();
					}
				}
			}
		});
		tablePlanos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(tablePlanos.getSelectedRow() != -1) {
					controller.mostrarDados();					
				}
			}
		});
		tablePlanos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome do Plano", "Tempo", "Valor"
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
		tablePlanos.getColumnModel().getColumn(0).setPreferredWidth(120);
		scrollPane.setViewportView(tablePlanos);
		tablePlanos.setFont(new Font("Arial", Font.PLAIN, 12));
		
		lblValorPlano = new JLabel("Valor:");
		lblValorPlano.setFont(new Font("Arial", Font.PLAIN, 20));
		lblValorPlano.setBounds(532, 112, 52, 24);
		contentPane.add(lblValorPlano);
		
		textFieldValorPlano = new JTextField();
		textFieldValorPlano.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldValorPlano.setColumns(10);
		textFieldValorPlano.setBounds(592, 108, 80, 32);
		contentPane.add(textFieldValorPlano);
		
		lblMesesTempoPlano = new JLabel("meses");
		lblMesesTempoPlano.setFont(new Font("Arial", Font.PLAIN, 20));
		lblMesesTempoPlano.setBounds(444, 112, 58, 24);
		contentPane.add(lblMesesTempoPlano);
		
		lblInformacao = new JLabel("O que s\u00E3o os planos?");
		lblInformacao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "São os planos disponíveis para os alunos se cadastrarem. \r\n"
						+ "O período é por quanto tempo (meses) o plano irá durar. \r\n"
						+ "O valor é referente ao período total de mês(es). \r\n\r\n"
						+ "Exemplos: Mensal - 1 - 40 \r\nSemestral - 6 - 200.",
						"Informação - O que são os planos?", JOptionPane.WARNING_MESSAGE);
			}
		});
		lblInformacao.setIcon(new ImageIcon(EditarPlanosTela.class.getResource("/view/icones/informacoes-usuarios.png")));
		lblInformacao.setForeground(Color.GRAY);
		lblInformacao.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInformacao.setBounds(95, 144, 121, 16);
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
		controller.atualizarLabelTempo();
		controller.atualizarTabela(usuario);

	}
	
	private void customizeTela() {
		controller.atualizarCores(usuario);
	}

	public JLabel getLblPlanos() {
		return lblPlanos;
	}

	public void setLblPlanos(JLabel lblPlanos) {
		this.lblPlanos = lblPlanos;
	}

	public JLabel getLblNomePlano() {
		return lblNomePlano;
	}

	public void setLblNomePlano(JLabel lblNomePlano) {
		this.lblNomePlano = lblNomePlano;
	}

	public JLabel getLblTempoPlano() {
		return lblTempoPlano;
	}

	public void setLblTempoPlano(JLabel lblTempoPlano) {
		this.lblTempoPlano = lblTempoPlano;
	}

	public JLabel getLblValorPlano() {
		return lblValorPlano;
	}

	public void setLblValorPlano(JLabel lblValorPlano) {
		this.lblValorPlano = lblValorPlano;
	}

	public JLabel getLblMesesTempoPlano() {
		return lblMesesTempoPlano;
	}

	public void setLblMesesTempoPlano(JLabel lblMesesTempoPlano) {
		this.lblMesesTempoPlano = lblMesesTempoPlano;
	}

	public JLabel getLblInformacao() {
		return lblInformacao;
	}

	public void setLblInformacao(JLabel lblInformacao) {
		this.lblInformacao = lblInformacao;
	}

	public JTextField getTextFieldNomePlano() {
		return textFieldNomePlano;
	}

	public void setTextFieldNomePlano(JTextField textFieldNomePlano) {
		this.textFieldNomePlano = textFieldNomePlano;
	}

	public JTextField getTextFieldTempoPlano() {
		return textFieldTempoPlano;
	}

	public void setTextFieldTempoPlano(JTextField textFieldTempoPlano) {
		this.textFieldTempoPlano = textFieldTempoPlano;
	}

	public JTextField getTextFieldValorPlano() {
		return textFieldValorPlano;
	}

	public void setTextFieldValorPlano(JTextField textFieldValorPlano) {
		this.textFieldValorPlano = textFieldValorPlano;
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

	public JTable getTablePlanos() {
		return tablePlanos;
	}

	public void setTablePlanos(JTable tablePlanos) {
		this.tablePlanos = tablePlanos;
	}
	
}

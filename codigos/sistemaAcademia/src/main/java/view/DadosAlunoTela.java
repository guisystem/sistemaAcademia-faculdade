package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import controller.DadosAlunoController;
import model.ModelCores;
import model.ModelModalidade;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DadosAlunoTela extends JDialog {

	private JPanel contentPane;
	private JLabel lblMudarFoto;
	private JLabel lblDadosAluno;
	private JLabel lblNomeAluno;
	private JLabel lblCpfAluno;
	private JLabel lblEmailAluno;
	private JLabel lblDataDeMatriculaAluno;
	private JLabel lblDataDeNascimentoAluno;
	private JLabel lblPlano;
	private JLabel lblModalidadesAluno;
	private JLabel lblUltimoPagamento;
	private JLabel lblProximoPagamento;
	private JLabel lblTotal;

	private JTextField textFieldNomeAluno;
	private JTextField textFieldCpfAluno;
	private JTextField textFieldEmailAluno;
	private JTextField textFieldDataMatricula;
	private JTextField textFieldDataNascimento;
	private JTextField textFieldDataProximoPagamento;
	private JTextField textFieldTotal;
	private JTextField textFieldDataUltimoPagamento;
	private JTextField nomeImagemField = new JTextField(20);
	private String caminhoImagem;

	private JComboBox comboBoxPlano;
	private JComboBox comboBoxExcluirModalidade;
	private JComboBox comboBoxAdicionarModalidade;

	private JButton btnMudarFotoAluno;
	private JButton btnAdicionarModalidade;
	private JButton btnExcluirModalidade;
	private JButton btnConfirmar;
	private JButton btnCancelar;	
	
	private JTable tableModalidadeAluno;

	private ArrayList<ModelModalidade> modalidades = null;	
	
	private static ModelRegistroAluno registroAluno;
	private static ModelUsuario usuario;
	
	private DadosAlunoController controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					DadosAlunoTela dialog = new DadosAlunoTela(usuario, registroAluno);
	                dialog.setLocationRelativeTo(null);
	                dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public DadosAlunoTela(ModelUsuario usuario, ModelRegistroAluno registroAluno) {
		super();
	    setModal(true);
		
	    DadosAlunoTela.usuario = usuario;
	    DadosAlunoTela.registroAluno = registroAluno;

	    controller = new DadosAlunoController(this);
		
		setBounds(100, 100, 816, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDadosAluno = new JLabel("DADOS DO ALUNO");
		lblDadosAluno.setFont(new Font("Arial", Font.PLAIN, 24));
		lblDadosAluno.setBounds(300, 26, 215, 28);
		contentPane.add(lblDadosAluno);
		
		lblNomeAluno = new JLabel("Nome:");
		lblNomeAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomeAluno.setBounds(162, 80, 57, 24);
		contentPane.add(lblNomeAluno);
		
		lblCpfAluno = new JLabel("CPF:");
		lblCpfAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblCpfAluno.setBounds(162, 180, 45, 24);
		contentPane.add(lblCpfAluno);
		
		lblEmailAluno = new JLabel("Email:");
		lblEmailAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblEmailAluno.setBounds(40, 264, 54, 24);
		contentPane.add(lblEmailAluno);
		
		lblDataDeMatriculaAluno = new JLabel("Data de Matricula:");
		lblDataDeMatriculaAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDataDeMatriculaAluno.setBounds(41, 348, 162, 24);
		contentPane.add(lblDataDeMatriculaAluno);
		
		lblDataDeNascimentoAluno = new JLabel("Data de Nascimento:");
		lblDataDeNascimentoAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDataDeNascimentoAluno.setBounds(41, 400, 184, 24);
		contentPane.add(lblDataDeNascimentoAluno);
		
		lblPlano = new JLabel("Plano:");
		lblPlano.setFont(new Font("Arial", Font.PLAIN, 20));
		lblPlano.setBounds(41, 452, 57, 24);
		contentPane.add(lblPlano);
		
		textFieldNomeAluno = new JTextField();
		textFieldNomeAluno.setBounds(162, 108, 201, 32);
		contentPane.add(textFieldNomeAluno);
		textFieldNomeAluno.setColumns(10);
		
		textFieldCpfAluno = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldCpfAluno).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###-##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldCpfAluno.setColumns(10);
		textFieldCpfAluno.setBounds(162, 208, 201, 32);
		contentPane.add(textFieldCpfAluno);
		
		textFieldEmailAluno = new JTextField();
		textFieldEmailAluno.setColumns(10);
		textFieldEmailAluno.setBounds(40, 292, 323, 32);
		contentPane.add(textFieldEmailAluno);
		
		textFieldDataMatricula = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldDataMatricula).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldDataMatricula.setColumns(10);
		textFieldDataMatricula.setBounds(240, 344, 123, 32);
		contentPane.add(textFieldDataMatricula);
		
		textFieldDataNascimento = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldDataNascimento).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldDataNascimento.setColumns(10);
		textFieldDataNascimento.setBounds(240, 396, 123, 32);
		contentPane.add(textFieldDataNascimento);
		
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
		comboBoxPlano.setBounds(240, 448, 123, 32);
		contentPane.add(comboBoxPlano);
		
		lblModalidadesAluno = new JLabel("Modalidades:");
		lblModalidadesAluno.setFont(new Font("Arial", Font.PLAIN, 20));
		lblModalidadesAluno.setBounds(434, 80, 123, 24);
		contentPane.add(lblModalidadesAluno);
		
		JScrollPane scrollPaneModalidades = new JScrollPane();
		scrollPaneModalidades.setBounds(434, 108, 321, 112);
		contentPane.add(scrollPaneModalidades);
		
		tableModalidadeAluno = new JTable();
		tableModalidadeAluno.setEnabled(false);
		tableModalidadeAluno.setOpaque(true);
		tableModalidadeAluno.setFillsViewportHeight(true);
		scrollPaneModalidades.setViewportView(tableModalidadeAluno);
		tableModalidadeAluno.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome da Modalidade", "Taxa"
			}
		));
		tableModalidadeAluno.getColumnModel().getColumn(0).setPreferredWidth(127);
		
		btnAdicionarModalidade = new JButton("Adicionar Modalidade");
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
				controller.adicionarModalidade(usuario, registroAluno);
			}
		});
		btnAdicionarModalidade.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAdicionarModalidade.setBounds(608, 270, 152, 28);
		contentPane.add(btnAdicionarModalidade);
		
		btnExcluirModalidade = new JButton("Excluir Modalidade");
		btnExcluirModalidade.addMouseListener(new MouseAdapter() {
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
		btnExcluirModalidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluirModalidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.removerModalidade(usuario, registroAluno);
			}
		});
		btnExcluirModalidade.setFont(new Font("Arial", Font.PLAIN, 12));
		btnExcluirModalidade.setBounds(608, 230, 152, 28);
		contentPane.add(btnExcluirModalidade);
		
		comboBoxExcluirModalidade = new JComboBox();
		comboBoxExcluirModalidade.addMouseListener(new MouseAdapter() {
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
		comboBoxExcluirModalidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxExcluirModalidade.setBounds(434, 228, 151, 32);
		contentPane.add(comboBoxExcluirModalidade);
		
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
		comboBoxAdicionarModalidade.setBounds(434, 268, 151, 32);
		contentPane.add(comboBoxAdicionarModalidade);
		
		lblUltimoPagamento = new JLabel("\u00DAltimo pagamento:");
		lblUltimoPagamento.setFont(new Font("Arial", Font.PLAIN, 20));
		lblUltimoPagamento.setBounds(434, 320, 164, 24);
		contentPane.add(lblUltimoPagamento);
		
		lblProximoPagamento = new JLabel("Pr\u00F3ximo pagamento:");
		lblProximoPagamento.setFont(new Font("Arial", Font.PLAIN, 20));
		lblProximoPagamento.setBounds(434, 360, 184, 24);
		contentPane.add(lblProximoPagamento);
		
		textFieldDataUltimoPagamento = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldDataUltimoPagamento).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldDataUltimoPagamento.setColumns(10);
		textFieldDataUltimoPagamento.setBounds(637, 316, 123, 32);
		contentPane.add(textFieldDataUltimoPagamento);
		
		textFieldDataProximoPagamento = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldDataProximoPagamento).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/####")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldDataProximoPagamento.setColumns(10);
		textFieldDataProximoPagamento.setBackground(new Color(50, 50, 50));
		textFieldDataProximoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldDataProximoPagamento.setEnabled(false);
		textFieldDataProximoPagamento.setBounds(637, 356, 123, 32);
		contentPane.add(textFieldDataProximoPagamento);
		
		btnConfirmar = new JButton("CONFIRMAR");
		btnConfirmar.addMouseListener(new MouseAdapter() {
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
		btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.confirmarAlteracoes(usuario, registroAluno, modalidades);
			}
		});
		btnConfirmar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnConfirmar.setBounds(590, 448, 170, 32);
		contentPane.add(btnConfirmar);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.addMouseListener(new MouseAdapter() {
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
			    			((JButton)temp).setBorder(new BevelBorder(BevelBorder.LOWERED, ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(2), 
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
			    		if(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).size() == 1 || 
			    				ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).size() == 1) {
			    			
			    			((JButton)temp).setBorder(new LineBorder(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0)));
			    			((JButton)temp).setBackground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(0));
			    			((JButton)temp).setForeground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(0));
			    		}else {
			    			((JButton)temp).setBackground(ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario)).get(1));
			    			((JButton)temp).setForeground(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(1));
			    			((JButton)temp).setBorder(new LineBorder(ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario)).get(1)));
			    		}
			    	}
			    }
			}
		});
		btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.cancelarAlteracoes(registroAluno, modalidades);
			}
		});
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCancelar.setBounds(434, 448, 130, 32);
		contentPane.add(btnCancelar);
		
		lblMudarFoto = new JLabel("");
		if(usuario.getConfiguracao() != null) {
			if(usuario.getConfiguracao().getCorSecundariaColor(usuario).equals(new Color(255, 255, 255))) {
				lblMudarFoto.setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-mudar-foto-fundobranco.png")));
			
			}
		}else {
			lblMudarFoto.setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-mudar-foto.png")));
		}
		lblMudarFoto.setHorizontalTextPosition(SwingConstants.LEADING);
		lblMudarFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblMudarFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		lblMudarFoto.setBounds(40, 80, 102, 136);
		contentPane.add(lblMudarFoto);
		
		btnMudarFotoAluno = new JButton("MUDAR FOTO");
		btnMudarFotoAluno.addMouseListener(new MouseAdapter() {
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
		btnMudarFotoAluno.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMudarFotoAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.carregarFoto();
			}
		});
		btnMudarFotoAluno.setFont(new Font("Arial", Font.PLAIN, 11));
		btnMudarFotoAluno.setBounds(40, 216, 102, 24);
		contentPane.add(btnMudarFotoAluno);
		
		lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Arial", Font.PLAIN, 20));
		lblTotal.setBounds(434, 400, 54, 24);
		contentPane.add(lblTotal);
		
		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setBounds(637, 396, 123, 32);
		contentPane.add(textFieldTotal);
		textFieldTotal.setColumns(10);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.sairDaTelaDefaultClose(registroAluno, modalidades);
			}
		});
		
		customizeTela();
		iniciar();
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
		if (JOptionPane.showConfirmDialog(null, mensagem, "O que deseja fazer?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return true;
		}
		
		return false;
	}

	private void iniciar() {
		controller.atualizarDados(usuario, registroAluno);
		modalidades = controller.getModalidadesInicio();
		
	}
	
	private void customizeTela() {
		controller.atualizarCores(usuario);
		
	}

	public JLabel getLblMudarFoto() {
		return lblMudarFoto;
	}

	public void setLblMudarFoto(JLabel lblMudarFoto) {
		this.lblMudarFoto = lblMudarFoto;
	}

	public JLabel getLblDadosAluno() {
		return lblDadosAluno;
	}

	public void setLblDadosAluno(JLabel lblDadosAluno) {
		this.lblDadosAluno = lblDadosAluno;
	}

	public JLabel getLblNomeAluno() {
		return lblNomeAluno;
	}

	public void setLblNomeAluno(JLabel lblNomeAluno) {
		this.lblNomeAluno = lblNomeAluno;
	}

	public JLabel getLblCpfAluno() {
		return lblCpfAluno;
	}

	public void setLblCpfAluno(JLabel lblCpfAluno) {
		this.lblCpfAluno = lblCpfAluno;
	}

	public JLabel getLblEmailAluno() {
		return lblEmailAluno;
	}

	public void setLblEmailAluno(JLabel lblEmailAluno) {
		this.lblEmailAluno = lblEmailAluno;
	}

	public JLabel getLblDataDeMatriculaAluno() {
		return lblDataDeMatriculaAluno;
	}

	public void setLblDataDeMatriculaAluno(JLabel lblDataDeMatriculaAluno) {
		this.lblDataDeMatriculaAluno = lblDataDeMatriculaAluno;
	}

	public JLabel getLblDataDeNascimentoAluno() {
		return lblDataDeNascimentoAluno;
	}

	public void setLblDataDeNascimentoAluno(JLabel lblDataDeNascimentoAluno) {
		this.lblDataDeNascimentoAluno = lblDataDeNascimentoAluno;
	}

	public JLabel getLblPlano() {
		return lblPlano;
	}

	public void setLblPlano(JLabel lblPlano) {
		this.lblPlano = lblPlano;
	}

	public JLabel getLblModalidadesAluno() {
		return lblModalidadesAluno;
	}

	public void setLblModalidadesAluno(JLabel lblModalidadesAluno) {
		this.lblModalidadesAluno = lblModalidadesAluno;
	}

	public JLabel getLblUltimoPagamento() {
		return lblUltimoPagamento;
	}

	public void setLblUltimoPagamento(JLabel lblUltimoPagamento) {
		this.lblUltimoPagamento = lblUltimoPagamento;
	}

	public JLabel getLblProximoPagamento() {
		return lblProximoPagamento;
	}

	public void setLblProximoPagamento(JLabel lblProximoPagamento) {
		this.lblProximoPagamento = lblProximoPagamento;
	}

	public JLabel getLblTotal() {
		return lblTotal;
	}

	public void setLblTotal(JLabel lblTotal) {
		this.lblTotal = lblTotal;
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

	public JTextField getTextFieldDataMatricula() {
		return textFieldDataMatricula;
	}

	public void setTextFieldDataMatricula(JTextField textFieldDataMatricula) {
		this.textFieldDataMatricula = textFieldDataMatricula;
	}

	public JTextField getTextFieldDataNascimento() {
		return textFieldDataNascimento;
	}

	public void setTextFieldDataNascimento(JTextField textFieldDataNascimento) {
		this.textFieldDataNascimento = textFieldDataNascimento;
	}

	public JTextField getTextFieldDataProximoPagamento() {
		return textFieldDataProximoPagamento;
	}

	public void setTextFieldDataProximoPagamento(JTextField textFieldDataProximoPagamento) {
		this.textFieldDataProximoPagamento = textFieldDataProximoPagamento;
	}

	public JTextField getTextFieldTotal() {
		return textFieldTotal;
	}

	public void setTextFieldTotal(JTextField textFieldTotal) {
		this.textFieldTotal = textFieldTotal;
	}

	public JTextField getTextFieldDataUltimoPagamento() {
		return textFieldDataUltimoPagamento;
	}

	public void setTextFieldDataUltimoPagamento(JTextField textFieldDataUltimoPagamento) {
		this.textFieldDataUltimoPagamento = textFieldDataUltimoPagamento;
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

	public JComboBox getComboBoxExcluirModalidade() {
		return comboBoxExcluirModalidade;
	}

	public void setComboBoxExcluirModalidade(JComboBox comboBoxExcluirModalidade) {
		this.comboBoxExcluirModalidade = comboBoxExcluirModalidade;
	}

	public JComboBox getComboBoxAdicionarModalidade() {
		return comboBoxAdicionarModalidade;
	}

	public void setComboBoxAdicionarModalidade(JComboBox comboBoxAdicionarModalidade) {
		this.comboBoxAdicionarModalidade = comboBoxAdicionarModalidade;
	}

	public JButton getBtnMudarFotoAluno() {
		return btnMudarFotoAluno;
	}

	public void setBtnMudarFotoAluno(JButton btnMudarFotoAluno) {
		this.btnMudarFotoAluno = btnMudarFotoAluno;
	}

	public JButton getBtnAdicionarModalidade() {
		return btnAdicionarModalidade;
	}

	public void setBtnAdicionarModalidade(JButton btnAdicionarModalidade) {
		this.btnAdicionarModalidade = btnAdicionarModalidade;
	}

	public JButton getBtnExcluirModalidade() {
		return btnExcluirModalidade;
	}

	public void setBtnExcluirModalidade(JButton btnExcluirModalidade) {
		this.btnExcluirModalidade = btnExcluirModalidade;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JTable getTableModalidadeAluno() {
		return tableModalidadeAluno;
	}

	public void setTableModalidadeAluno(JTable tableModalidadeAluno) {
		this.tableModalidadeAluno = tableModalidadeAluno;
	}

	public ArrayList<ModelModalidade> getModalidades() {
		return modalidades;
	}

	public void setModalidades(ArrayList<ModelModalidade> modalidades) {
		this.modalidades = modalidades;
	}
	
}

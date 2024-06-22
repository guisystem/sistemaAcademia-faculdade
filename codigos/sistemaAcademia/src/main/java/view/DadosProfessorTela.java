package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import controller.DadosProfessorController;
import model.ModelCores;
import model.ModelEspecialidade;
import model.ModelRegistroProfessor;
import model.ModelUsuario;

public class DadosProfessorTela extends JDialog {

	private JPanel contentPane;
	private JTextField textFieldNomeProfessor;
	private JTextField textFieldCpfProfessor;
	private JTextField textFieldEmailProfessor;
	private JTextField textFieldDataAdmissao;
	private JTextField textFieldHoraEntrada;
	private JTextField textFieldSalarioProfessor;
	private JTextField textFieldContrato;
	private JTextField textFieldHoraSaida;
	private JTextField nomeImagemField = new JTextField(20);
	private String caminhoImagem;

	private JComboBox comboBoxExcluirEspecialidade;
	private JComboBox comboBoxAdicionarEspecialidade;
	private JComboBox comboBoxAtivo;

	private JLabel lblNomeProfessor;
	private JLabel lblCpfProfessor;
	private JLabel lblEmailProfessor;
	private JLabel lblDataDeAdmissaoProfessor;
	private JLabel lblHorarioEntradaProfessor;
	private JLabel lblHorarioSaidaProfessor;
	private JLabel lblEspecialidadeProfessor;
	private JLabel lblSalarioProfessor;
	private JLabel lblAtivoProfessor;
	private JLabel lblDiasDeTrabalho;
	private JLabel lblDadosProfessor;
	private JLabel lblContrato;
	private JLabel lblMudarFoto;
	
	private JCheckBox chckbxSegunda;
	private JCheckBox chckbxTerca;
	private JCheckBox chckbxQuarta;
	private JCheckBox chckbxQuinta;
	private JCheckBox chckbxSexta;
	private JCheckBox chckbxSabado;
	private JCheckBox chckbxDomingo;
	
	private JTable tableEspecialidadeProfessor;
	
	private JButton btnMudarFotoProfessor;
	private JButton btnAdicionarEspecialidade;
	private JButton btnExcluirEspecialidade;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	
	private JRadioButton rdbtnMes;
	private JRadioButton rdbtnAno;
	
	private ArrayList<ModelEspecialidade> especialidades = null;
	
	private DadosProfessorController controller;

	private static ModelUsuario usuario;
	private static ModelRegistroProfessor registroProfessor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DadosProfessorTela dialog = new DadosProfessorTela(usuario, registroProfessor);
	                dialog.setLocationRelativeTo(null);
	                dialog.setModalityType(Dialog.ModalityType.MODELESS);
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
	public DadosProfessorTela(ModelUsuario usuario, ModelRegistroProfessor registroProfessor) {
		super();
	    setModal(true);
		
	    DadosProfessorTela.usuario = usuario;
	    DadosProfessorTela.registroProfessor = registroProfessor;

	    controller = new DadosProfessorController(this);
		
		setBounds(100, 100, 816, 582);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDadosProfessor = new JLabel("DADOS DO PROFESSOR");
		lblDadosProfessor.setFont(new Font("Arial", Font.PLAIN, 24));
		lblDadosProfessor.setBounds(264, 26, 287, 28);
		contentPane.add(lblDadosProfessor);
		
		lblNomeProfessor = new JLabel("Nome:");
		lblNomeProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomeProfessor.setBounds(162, 80, 57, 24);
		contentPane.add(lblNomeProfessor);
		
		lblCpfProfessor = new JLabel("CPF:");
		lblCpfProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblCpfProfessor.setBounds(162, 180, 45, 24);
		contentPane.add(lblCpfProfessor);
		
		lblEmailProfessor = new JLabel("Email:");
		lblEmailProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblEmailProfessor.setBounds(40, 264, 54, 24);
		contentPane.add(lblEmailProfessor);
		
		lblDataDeAdmissaoProfessor = new JLabel("Data de Admiss\u00E3o:");
		lblDataDeAdmissaoProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDataDeAdmissaoProfessor.setBounds(40, 348, 170, 24);
		contentPane.add(lblDataDeAdmissaoProfessor);
		
		lblHorarioEntradaProfessor = new JLabel("Hor\u00E1rio de Entrada:");
		lblHorarioEntradaProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblHorarioEntradaProfessor.setBounds(437, 398, 173, 24);
		contentPane.add(lblHorarioEntradaProfessor);
		
		lblHorarioSaidaProfessor = new JLabel("Hor\u00E1rio de Sa\u00EDda:");
		lblHorarioSaidaProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblHorarioSaidaProfessor.setBounds(437, 442, 156, 24);
		contentPane.add(lblHorarioSaidaProfessor);
		
		textFieldNomeProfessor = new JTextField();
		textFieldNomeProfessor.setBounds(162, 108, 201, 32);
		contentPane.add(textFieldNomeProfessor);
		textFieldNomeProfessor.setColumns(10);
		
		textFieldCpfProfessor = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldCpfProfessor).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###-##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldCpfProfessor.setColumns(10);
		textFieldCpfProfessor.setBounds(162, 208, 201, 32);
		contentPane.add(textFieldCpfProfessor);
		
		textFieldEmailProfessor = new JTextField();
		textFieldEmailProfessor.setColumns(10);
		textFieldEmailProfessor.setBounds(40, 292, 323, 32);
		contentPane.add(textFieldEmailProfessor);
		
		textFieldDataAdmissao = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldDataAdmissao).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##/##/#### ##:##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldDataAdmissao.setColumns(10);
		textFieldDataAdmissao.setBounds(247, 344, 116, 32);
		contentPane.add(textFieldDataAdmissao);
		
		textFieldHoraEntrada = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldHoraEntrada).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##:##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldHoraEntrada.setColumns(10);
		textFieldHoraEntrada.setBounds(688, 396, 72, 28);
		contentPane.add(textFieldHoraEntrada);
		
		textFieldHoraSaida = new JFormattedTextField();
		try {
			((JFormattedTextField) textFieldHoraSaida).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##:##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textFieldHoraSaida.setColumns(10);
		textFieldHoraSaida.setBounds(688, 440, 72, 28);
		contentPane.add(textFieldHoraSaida);

		lblEspecialidadeProfessor = new JLabel("Especialidades:");
		lblEspecialidadeProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblEspecialidadeProfessor.setBounds(434, 80, 138, 24);
		contentPane.add(lblEspecialidadeProfessor);
		
		JScrollPane scrollPaneEspecialidade = new JScrollPane();
		scrollPaneEspecialidade.setBounds(434, 108, 326, 112);
		contentPane.add(scrollPaneEspecialidade);
		
		tableEspecialidadeProfessor = new JTable();
		tableEspecialidadeProfessor.setEnabled(false);
		tableEspecialidadeProfessor.setOpaque(true);
		tableEspecialidadeProfessor.setFillsViewportHeight(true);
		scrollPaneEspecialidade.setViewportView(tableEspecialidadeProfessor);
		tableEspecialidadeProfessor.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome da Especialidade"
			}
		){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if(column != 1) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		});
		tableEspecialidadeProfessor.getColumnModel().getColumn(0).setPreferredWidth(171);
		
		btnAdicionarEspecialidade = new JButton("Adicionar Especialidade");
		btnAdicionarEspecialidade.addMouseListener(new MouseAdapter() {
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
		btnAdicionarEspecialidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionarEspecialidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.adicionarEspecialidade(usuario, registroProfessor);
			}
		});
		btnAdicionarEspecialidade.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAdicionarEspecialidade.setBounds(593, 270, 167, 28);
		contentPane.add(btnAdicionarEspecialidade);
		
		btnExcluirEspecialidade = new JButton("Excluir Especialidade");
		btnExcluirEspecialidade.addMouseListener(new MouseAdapter() {
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
		btnExcluirEspecialidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluirEspecialidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.removerEspecialidade(usuario, registroProfessor);
			}
		});
		btnExcluirEspecialidade.setFont(new Font("Arial", Font.PLAIN, 12));
		btnExcluirEspecialidade.setBounds(593, 230, 167, 28);
		contentPane.add(btnExcluirEspecialidade);
		
		comboBoxExcluirEspecialidade = new JComboBox();
		comboBoxExcluirEspecialidade.addMouseListener(new MouseAdapter() {
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
		comboBoxExcluirEspecialidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxExcluirEspecialidade.setBounds(434, 228, 151, 32);
		contentPane.add(comboBoxExcluirEspecialidade);
		
		comboBoxAdicionarEspecialidade = new JComboBox();
		comboBoxAdicionarEspecialidade.addMouseListener(new MouseAdapter() {
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
		comboBoxAdicionarEspecialidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxAdicionarEspecialidade.setBounds(434, 268, 151, 32);
		contentPane.add(comboBoxAdicionarEspecialidade);
		
		lblSalarioProfessor = new JLabel("Sal\u00E1rio:");
		lblSalarioProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblSalarioProfessor.setBounds(40, 396, 67, 24);
		contentPane.add(lblSalarioProfessor);
		
		lblAtivoProfessor = new JLabel("Ativo:");
		lblAtivoProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		lblAtivoProfessor.setBounds(40, 444, 50, 24);
		contentPane.add(lblAtivoProfessor);
		
		textFieldSalarioProfessor = new JTextField();
		textFieldSalarioProfessor.setColumns(10);
		textFieldSalarioProfessor.setBounds(247, 392, 116, 32);
		contentPane.add(textFieldSalarioProfessor);
		
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
				controller.confirmarAlteracoes(usuario, registroProfessor, especialidades);
			}
		});
		btnConfirmar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnConfirmar.setBounds(590, 488, 170, 32);
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
				controller.cancelarAlteracoes(registroProfessor, especialidades);
			}
		});
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCancelar.setBounds(434, 488, 130, 32);
		contentPane.add(btnCancelar);
		
		lblMudarFoto = new JLabel("");
		lblMudarFoto.setIcon(new ImageIcon(DadosProfessorTela.class.getResource("/view/icones/icone-mudar-foto.png")));
		lblMudarFoto.setHorizontalTextPosition(SwingConstants.LEADING);
		lblMudarFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblMudarFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		lblMudarFoto.setBounds(40, 80, 102, 136);
		contentPane.add(lblMudarFoto);
		
		btnMudarFotoProfessor = new JButton("MUDAR FOTO");
		btnMudarFotoProfessor.addMouseListener(new MouseAdapter() {
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
		btnMudarFotoProfessor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMudarFotoProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.carregarFoto();
			}
		});
		btnMudarFotoProfessor.setFont(new Font("Arial", Font.PLAIN, 11));
		btnMudarFotoProfessor.setBounds(40, 216, 102, 24);
		contentPane.add(btnMudarFotoProfessor);
		
		comboBoxAtivo = new JComboBox();
		comboBoxAtivo.addMouseListener(new MouseAdapter() {
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
		comboBoxAtivo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBoxAtivo.setBounds(247, 440, 116, 32);
		contentPane.add(comboBoxAtivo);
		
		lblDiasDeTrabalho = new JLabel("Dias:");
		lblDiasDeTrabalho.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDiasDeTrabalho.setBounds(434, 320, 45, 24);
		contentPane.add(lblDiasDeTrabalho);
		
		lblContrato = new JLabel("Tempo de contrato:");
		lblContrato.setFont(new Font("Arial", Font.PLAIN, 20));
		lblContrato.setBounds(40, 492, 179, 24);
		contentPane.add(lblContrato);
		
		textFieldContrato = new JTextField();
		textFieldContrato.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				textFieldContrato.setToolTipText(controller.calcularTempo(registroProfessor));
			}
		});
		textFieldContrato.setColumns(10);
		textFieldContrato.setBounds(247, 488, 57, 32);
		contentPane.add(textFieldContrato);
		
		chckbxSegunda = new JCheckBox("Segunda");
		chckbxSegunda.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxSegunda.setOpaque(false);
		chckbxSegunda.setBounds(499, 321, 85, 22);
		contentPane.add(chckbxSegunda);
		
		chckbxTerca = new JCheckBox("Ter\u00E7a");
		chckbxTerca.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxTerca.setOpaque(false);
		chckbxTerca.setBounds(604, 321, 67, 22);
		contentPane.add(chckbxTerca);
		
		chckbxQuarta = new JCheckBox("Quarta");
		chckbxQuarta.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxQuarta.setOpaque(false);
		chckbxQuarta.setBounds(691, 321, 75, 22);
		contentPane.add(chckbxQuarta);
		
		chckbxQuinta = new JCheckBox("Quinta");
		chckbxQuinta.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxQuinta.setOpaque(false);
		chckbxQuinta.setBounds(430, 354, 71, 22);
		contentPane.add(chckbxQuinta);
		
		chckbxSexta = new JCheckBox("Sexta");
		chckbxSexta.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxSexta.setOpaque(false);
		chckbxSexta.setBounds(514, 354, 63, 22);
		contentPane.add(chckbxSexta);
		
		chckbxSabado = new JCheckBox("S\u00E1bado");
		chckbxSabado.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxSabado.setOpaque(false);
		chckbxSabado.setBounds(590, 354, 77, 22);
		contentPane.add(chckbxSabado);
		
		chckbxDomingo = new JCheckBox("Domingo");
		chckbxDomingo.setFont(new Font("Arial", Font.PLAIN, 14));
		chckbxDomingo.setOpaque(false);
		chckbxDomingo.setBounds(679, 354, 85, 22);
		contentPane.add(chckbxDomingo);
		
		rdbtnMes = new JRadioButton("M\u00EAs");
		rdbtnMes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!rdbtnMes.isSelected()) {
					rdbtnAno.setSelected(true);
				}else {
					rdbtnAno.setSelected(false);
				}
			}
		});
		rdbtnMes.setFont(new Font("Arial", Font.PLAIN, 12));
		rdbtnMes.setBounds(310, 488, 57, 14);
		contentPane.add(rdbtnMes);
		
		rdbtnAno = new JRadioButton("Ano");
		rdbtnAno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!rdbtnAno.isSelected()) {
					rdbtnMes.setSelected(true);
				}else {
					rdbtnMes.setSelected(false);
				}
			}
		});
		rdbtnAno.setFont(new Font("Arial", Font.PLAIN, 12));
		rdbtnAno.setBounds(310, 506, 57, 14);
		contentPane.add(rdbtnAno);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.sairDaTelaDefaultClose(registroProfessor, especialidades);
			}
		});
		
		
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
		if (JOptionPane.showConfirmDialog(null, mensagem, "O que deseja fazer?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return true;
		}
		
		return false;
	}

	private void iniciar() {
		controller.atualizarDados(usuario, registroProfessor); 
		especialidades = controller.getEspecialidadesInicio();
	}
	
	private void customizeTela() {
		controller.atualizarCores(usuario);
		
	}

	public JTextField getTextFieldNomeProfessor() {
		return textFieldNomeProfessor;
	}

	public void setTextFieldNomeProfessor(JTextField textFieldNomeProfessor) {
		this.textFieldNomeProfessor = textFieldNomeProfessor;
	}

	public JTextField getTextFieldCpfProfessor() {
		return textFieldCpfProfessor;
	}

	public void setTextFieldCpfProfessor(JTextField textFieldCpfProfessor) {
		this.textFieldCpfProfessor = textFieldCpfProfessor;
	}

	public JTextField getTextFieldEmailProfessor() {
		return textFieldEmailProfessor;
	}

	public void setTextFieldEmailProfessor(JTextField textFieldEmailProfessor) {
		this.textFieldEmailProfessor = textFieldEmailProfessor;
	}

	public JTextField getTextFieldDataAdmissao() {
		return textFieldDataAdmissao;
	}

	public void setTextFieldDataAdmissao(JTextField textFieldDataAdmissao) {
		this.textFieldDataAdmissao = textFieldDataAdmissao;
	}

	public JTextField getTextFieldHoraEntrada() {
		return textFieldHoraEntrada;
	}

	public void setTextFieldHoraEntrada(JTextField textFieldHoraEntrada) {
		this.textFieldHoraEntrada = textFieldHoraEntrada;
	}

	public JTextField getTextFieldSalarioProfessor() {
		return textFieldSalarioProfessor;
	}

	public void setTextFieldSalarioProfessor(JTextField textFieldSalarioProfessor) {
		this.textFieldSalarioProfessor = textFieldSalarioProfessor;
	}

	public JTextField getTextFieldContrato() {
		return textFieldContrato;
	}

	public void setTextFieldContrato(JTextField textFieldContrato) {
		this.textFieldContrato = textFieldContrato;
	}

	public JTextField getTextFieldHoraSaida() {
		return textFieldHoraSaida;
	}

	public void setTextFieldHoraSaida(JTextField textFieldHoraSaida) {
		this.textFieldHoraSaida = textFieldHoraSaida;
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

	public JComboBox getComboBoxExcluirEspecialidade() {
		return comboBoxExcluirEspecialidade;
	}

	public void setComboBoxExcluirEspecialidade(JComboBox comboBoxExcluirEspecialidade) {
		this.comboBoxExcluirEspecialidade = comboBoxExcluirEspecialidade;
	}

	public JComboBox getComboBoxAdicionarEspecialidade() {
		return comboBoxAdicionarEspecialidade;
	}

	public void setComboBoxAdicionarEspecialidade(JComboBox comboBoxAdicionarEspecialidade) {
		this.comboBoxAdicionarEspecialidade = comboBoxAdicionarEspecialidade;
	}

	public JComboBox getComboBoxAtivo() {
		return comboBoxAtivo;
	}

	public void setComboBoxAtivo(JComboBox comboBoxAtivo) {
		this.comboBoxAtivo = comboBoxAtivo;
	}

	public JLabel getLblNomeProfessor() {
		return lblNomeProfessor;
	}

	public void setLblNomeProfessor(JLabel lblNomeProfessor) {
		this.lblNomeProfessor = lblNomeProfessor;
	}

	public JLabel getLblCpfProfessor() {
		return lblCpfProfessor;
	}

	public void setLblCpfProfessor(JLabel lblCpfProfessor) {
		this.lblCpfProfessor = lblCpfProfessor;
	}

	public JLabel getLblEmailProfessor() {
		return lblEmailProfessor;
	}

	public void setLblEmailProfessor(JLabel lblEmailProfessor) {
		this.lblEmailProfessor = lblEmailProfessor;
	}

	public JLabel getLblDataDeAdmissaoProfessor() {
		return lblDataDeAdmissaoProfessor;
	}

	public void setLblDataDeAdmissaoProfessor(JLabel lblDataDeAdmissaoProfessor) {
		this.lblDataDeAdmissaoProfessor = lblDataDeAdmissaoProfessor;
	}

	public JLabel getLblHorarioEntradaProfessor() {
		return lblHorarioEntradaProfessor;
	}

	public void setLblHorarioEntradaProfessor(JLabel lblHorarioEntradaProfessor) {
		this.lblHorarioEntradaProfessor = lblHorarioEntradaProfessor;
	}

	public JLabel getLblHorarioSaidaProfessor() {
		return lblHorarioSaidaProfessor;
	}

	public void setLblHorarioSaidaProfessor(JLabel lblHorarioSaidaProfessor) {
		this.lblHorarioSaidaProfessor = lblHorarioSaidaProfessor;
	}

	public JLabel getLblEspecialidadeProfessor() {
		return lblEspecialidadeProfessor;
	}

	public void setLblEspecialidadeProfessor(JLabel lblEspecialidadeProfessor) {
		this.lblEspecialidadeProfessor = lblEspecialidadeProfessor;
	}

	public JLabel getLblSalarioProfessor() {
		return lblSalarioProfessor;
	}

	public void setLblSalarioProfessor(JLabel lblSalarioProfessor) {
		this.lblSalarioProfessor = lblSalarioProfessor;
	}

	public JLabel getLblAtivoProfessor() {
		return lblAtivoProfessor;
	}

	public void setLblAtivoProfessor(JLabel lblAtivoProfessor) {
		this.lblAtivoProfessor = lblAtivoProfessor;
	}

	public JLabel getLblDiasDeTrabalho() {
		return lblDiasDeTrabalho;
	}

	public void setLblDiasDeTrabalho(JLabel lblDiasDeTrabalho) {
		this.lblDiasDeTrabalho = lblDiasDeTrabalho;
	}

	public JLabel getLblDadosProfessor() {
		return lblDadosProfessor;
	}

	public void setLblDadosProfessor(JLabel lblDadosProfessor) {
		this.lblDadosProfessor = lblDadosProfessor;
	}

	public JLabel getLblContrato() {
		return lblContrato;
	}

	public void setLblContrato(JLabel lblContrato) {
		this.lblContrato = lblContrato;
	}

	public JLabel getLblMudarFoto() {
		return lblMudarFoto;
	}

	public void setLblMudarFoto(JLabel lblMudarFoto) {
		this.lblMudarFoto = lblMudarFoto;
	}

	public JCheckBox getChckbxSegunda() {
		return chckbxSegunda;
	}

	public void setChckbxSegunda(JCheckBox chckbxSegunda) {
		this.chckbxSegunda = chckbxSegunda;
	}

	public JCheckBox getChckbxTerca() {
		return chckbxTerca;
	}

	public void setChckbxTerca(JCheckBox chckbxTerca) {
		this.chckbxTerca = chckbxTerca;
	}

	public JCheckBox getChckbxQuarta() {
		return chckbxQuarta;
	}

	public void setChckbxQuarta(JCheckBox chckbxQuarta) {
		this.chckbxQuarta = chckbxQuarta;
	}

	public JCheckBox getChckbxQuinta() {
		return chckbxQuinta;
	}

	public void setChckbxQuinta(JCheckBox chckbxQuinta) {
		this.chckbxQuinta = chckbxQuinta;
	}

	public JCheckBox getChckbxSexta() {
		return chckbxSexta;
	}

	public void setChckbxSexta(JCheckBox chckbxSexta) {
		this.chckbxSexta = chckbxSexta;
	}

	public JCheckBox getChckbxSabado() {
		return chckbxSabado;
	}

	public void setChckbxSabado(JCheckBox chckbxSabado) {
		this.chckbxSabado = chckbxSabado;
	}

	public JCheckBox getChckbxDomingo() {
		return chckbxDomingo;
	}

	public void setChckbxDomingo(JCheckBox chckbxDomingo) {
		this.chckbxDomingo = chckbxDomingo;
	}

	public JTable getTableEspecialidadeProfessor() {
		return tableEspecialidadeProfessor;
	}

	public void setTableEspecialidadeProfessor(JTable tableEspecialidadeProfessor) {
		this.tableEspecialidadeProfessor = tableEspecialidadeProfessor;
	}

	public JButton getBtnMudarFotoProfessor() {
		return btnMudarFotoProfessor;
	}

	public void setBtnMudarFotoProfessor(JButton btnMudarFotoProfessor) {
		this.btnMudarFotoProfessor = btnMudarFotoProfessor;
	}

	public JButton getBtnAdicionarEspecialidade() {
		return btnAdicionarEspecialidade;
	}

	public void setBtnAdicionarEspecialidade(JButton btnAdicionarEspecialidade) {
		this.btnAdicionarEspecialidade = btnAdicionarEspecialidade;
	}

	public JButton getBtnExcluirEspecialidade() {
		return btnExcluirEspecialidade;
	}

	public void setBtnExcluirEspecialidade(JButton btnExcluirEspecialidade) {
		this.btnExcluirEspecialidade = btnExcluirEspecialidade;
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

	public JRadioButton getRdbtnMes() {
		return rdbtnMes;
	}

	public void setRdbtnMes(JRadioButton rdbtnMes) {
		this.rdbtnMes = rdbtnMes;
	}

	public JRadioButton getRdbtnAno() {
		return rdbtnAno;
	}

	public void setRdbtnAno(JRadioButton rdbtnAno) {
		this.rdbtnAno = rdbtnAno;
	}

	public ArrayList<ModelEspecialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(ArrayList<ModelEspecialidade> especialidades) {
		this.especialidades = especialidades;
	}
	
}

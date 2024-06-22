package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import controller.CadastroUsuarioController;

public class CadastroUsuarioTela extends JFrame {

	private JPanel contentPane;
	private JTextField textNomeCompletoUsuario;
	private JTextField textEmailUsuario;
	private JTextField textUsuario;
	private JTextField textCpfUsuario;
	private JPasswordField passwordSenhaUsuario;
	private JPasswordField passwordConfirmarSenhaUsuario;
	
	private CadastroUsuarioController controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					CadastroUsuarioTela frame = new CadastroUsuarioTela();
					frame.setTitle("Cadastro de Usuário");
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadastroUsuarioTela() {
		
		controller = new CadastroUsuarioController(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1382, 752);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblVoltar = new JLabel();
		lblVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblVoltar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.voltarParaLogin();
			}
		});
		
		JLabel lblNomeCompleto = new JLabel("Nome completo");
		lblNomeCompleto.setForeground(new Color(236, 202, 15));
		lblNomeCompleto.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeCompleto.setBounds(495, 190, 110, 20);
		contentPane.add(lblNomeCompleto);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(new Color(236, 202, 15));
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSenha.setBounds(495, 424, 45, 20);
		contentPane.add(lblSenha);
		
		JLabel lblCadastro = new JLabel("Cadastro");
		lblCadastro.setFont(new Font("Arial", Font.PLAIN, 24));
		lblCadastro.setForeground(new Color(255, 255, 0));
		lblCadastro.setBounds(635, 142, 97, 28);
		contentPane.add(lblCadastro);
		lblVoltar.setFont(new Font("Arial", Font.PLAIN, 12));
		lblVoltar.setText("VOLTAR");
		lblVoltar.setForeground(new Color(0, 0, 0));
		lblVoltar.setBounds(61, 343, 46, 20);
		contentPane.add(lblVoltar);
		
		JLabel lblSetaEsquerda = new JLabel("");
		lblSetaEsquerda.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblSetaEsquerda.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.voltarParaLogin();
			}
		});
		lblSetaEsquerda.setIcon(new ImageIcon(CadastroUsuarioTela.class.getResource("/view/icones/seta-esquerda.png")));
		lblSetaEsquerda.setBounds(20, 343, 33, 20);
		contentPane.add(lblSetaEsquerda);
		
		JLabel lblNomeDeUsurio = new JLabel("Nome de Usu\u00E1rio");
		lblNomeDeUsurio.setForeground(new Color(236, 202, 15));
		lblNomeDeUsurio.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeDeUsurio.setBounds(495, 268, 122, 20);
		contentPane.add(lblNomeDeUsurio);
		
		JLabel lblUsuario_1_2 = new JLabel("Email");
		lblUsuario_1_2.setForeground(new Color(236, 202, 15));
		lblUsuario_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUsuario_1_2.setBounds(495, 346, 45, 20);
		contentPane.add(lblUsuario_1_2);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setForeground(new Color(236, 202, 15));
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBounds(691, 268, 33, 20);
		contentPane.add(lblCpf);
		
		JLabel lblConfirmarSenha = new JLabel("Confirmar Senha");
		lblConfirmarSenha.setForeground(new Color(236, 202, 15));
		lblConfirmarSenha.setFont(new Font("Arial", Font.PLAIN, 16));
		lblConfirmarSenha.setBounds(691, 424, 122, 20);
		contentPane.add(lblConfirmarSenha);
		
		textCpfUsuario = new JFormattedTextField();
		try {
			((JFormattedTextField) textCpfUsuario).setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###-##")));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		textCpfUsuario.setToolTipText("CPF");
		textCpfUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
		textCpfUsuario.setColumns(10);
		textCpfUsuario.setBorder(new LineBorder(new Color(236, 192, 91)));
		textCpfUsuario.setBounds(691, 292, 180, 42);
		contentPane.add(textCpfUsuario);
		
		textUsuario = new JTextField();
		textUsuario.setToolTipText("Nome de Usuário");
		textUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
		textUsuario.setColumns(10);
		textUsuario.setBorder(new LineBorder(new Color(236, 192, 91)));
		textUsuario.setBounds(495, 292, 180, 42);
		contentPane.add(textUsuario);
		
		textEmailUsuario = new JTextField();
		textEmailUsuario.setToolTipText("Email");
		textEmailUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
		textEmailUsuario.setColumns(10);
		textEmailUsuario.setBorder(new LineBorder(new Color(236, 192, 91)));
		textEmailUsuario.setBounds(495, 370, 376, 42);
		contentPane.add(textEmailUsuario);
		
		textNomeCompletoUsuario = new JTextField();
		textNomeCompletoUsuario.setToolTipText("Nome completo");
		textNomeCompletoUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
		textNomeCompletoUsuario.setBorder(new LineBorder(new Color(236, 192, 91)));
		textNomeCompletoUsuario.setBounds(495, 214, 376, 42);
		contentPane.add(textNomeCompletoUsuario);
		textNomeCompletoUsuario.setColumns(10);
		
		passwordConfirmarSenhaUsuario = new JPasswordField();
		passwordConfirmarSenhaUsuario.setToolTipText("Confirmar Senha");
		passwordConfirmarSenhaUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordConfirmarSenhaUsuario.setBorder(new LineBorder(new Color(236, 202, 91)));
		passwordConfirmarSenhaUsuario.setBounds(691, 448, 180, 42);
		contentPane.add(passwordConfirmarSenhaUsuario);
		
		passwordSenhaUsuario = new JPasswordField();
		passwordSenhaUsuario.setToolTipText("Senha");
		passwordSenhaUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordSenhaUsuario.setBorder(new LineBorder(new Color(236, 202, 91)));
		passwordSenhaUsuario.setBounds(495, 448, 180, 42);
		contentPane.add(passwordSenhaUsuario);
		
		JButton btnCadastrarUsuario = new JButton("FAZER CADASTRO");
		btnCadastrarUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCadastrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.cadastrarNoSistema();
			}
		});
		
		
		btnCadastrarUsuario.setForeground(new Color(236, 202, 15));
		btnCadastrarUsuario.setBackground(new Color(52, 50, 41));
		btnCadastrarUsuario.setBorder(new LineBorder(new Color(236, 202, 15)));
		btnCadastrarUsuario.setBounds(593, 522, 180, 42);
		btnCadastrarUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnCadastrarUsuario);
		
		JLabel lblPainelLogin = new JLabel("");
		lblPainelLogin.setIcon(new ImageIcon(CadastroUsuarioTela.class.getResource("/view/imagens/painel-cadastro-usuario.png")));
		lblPainelLogin.setBounds(0, 0, 1366, 705);
		contentPane.add(lblPainelLogin);
		
		JLabel lblImagemFundoCadastroUsuario = new JLabel("");
		lblImagemFundoCadastroUsuario.setBounds(0, 0, 1366, 713);
		lblImagemFundoCadastroUsuario.setIcon(new ImageIcon(CadastroUsuarioTela.class.getResource("/view/imagens/imagem-fundo-cadastro-usuario.jpg")));
		contentPane.add(lblImagemFundoCadastroUsuario);
	
	}
	
	/** @implNote Quando a ação não pode ser concluída devido a algum dado incorreto ou lógica de programação */
	public void exibirMensagemErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.ERROR_MESSAGE);
	}
	
	/** @implNote Quando o usuário precisa tomar uma decisão entre duas ou mais opções */
	public boolean exibirMensagemDecisao(String mensagem) {
		if(JOptionPane.showConfirmDialog(null, mensagem, "O que deseja fazer?", JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				return true;
			}
		return false;
	}
	

	public JTextField getTextNomeCompletoUsuario() {
		return textNomeCompletoUsuario;
	}

	public void setTextNomeCompletoUsuario(JTextField textNomeCompletoUsuario) {
		this.textNomeCompletoUsuario = textNomeCompletoUsuario;
	}

	public JTextField getTextEmailUsuario() {
		return textEmailUsuario;
	}

	public void setTextEmailUsuario(JTextField textEmailUsuario) {
		this.textEmailUsuario = textEmailUsuario;
	}

	public JTextField getTextUsuario() {
		return textUsuario;
	}

	public void setTextUsuario(JTextField textUsuario) {
		this.textUsuario = textUsuario;
	}

	public JTextField getTextCpfUsuario() {
		return textCpfUsuario;
	}

	public void setTextCpfUsuario(JTextField textCpfUsuario) {
		this.textCpfUsuario = textCpfUsuario;
	}

	public JPasswordField getPasswordSenhaUsuario() {
		return passwordSenhaUsuario;
	}

	public void setPasswordSenhaUsuario(JPasswordField passwordSenhaUsuario) {
		this.passwordSenhaUsuario = passwordSenhaUsuario;
	}

	public JPasswordField getPasswordConfirmarSenhaUsuario() {
		return passwordConfirmarSenhaUsuario;
	}

	public void setPasswordConfirmarSenhaUsuario(JPasswordField passwordConfirmarSenhaUsuario) {
		this.passwordConfirmarSenhaUsuario = passwordConfirmarSenhaUsuario;
	}
	
}

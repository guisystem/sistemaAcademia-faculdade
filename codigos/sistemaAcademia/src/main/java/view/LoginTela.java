package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.LoginController;

public class LoginTela extends JFrame {

	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textSenha;
	
	private LoginController controller; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try {
					UIManager.put("OptionPane.yesButtonText", "Sim");
					UIManager.put("OptionPane.noButtonText", "Não");
					UIManager.put("OptionPane.cancelButtonText", "Cancelar");
					
					LoginTela frame = new LoginTela();
					frame.setTitle("Login");
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
	public LoginTela() {
		
		controller = new LoginController(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1382, 752);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JButton btnCadastrar = new JButton("Cadastre-se");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.irParaCadastro(); 
			}
		});
		btnCadastrar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCadastrar.setBounds(720, 438, 69, 23);
		btnCadastrar.setForeground(new Color(236, 202, 15));
		btnCadastrar.setBorder(null);
		btnCadastrar.setContentAreaFilled(false);
		btnCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnCadastrar);
		
		JLabel lblMensagemCadastro = new JLabel("N\u00E3o tem uma conta ainda?");
		lblMensagemCadastro.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMensagemCadastro.setForeground(new Color(236, 192, 91));
		lblMensagemCadastro.setBounds(568, 441, 149, 16);
		contentPane.add(lblMensagemCadastro);
		
		JButton btnEntrar = new JButton("ENTRAR");
		btnEntrar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.entrarNoSistema();
			}
		});
		
		btnEntrar.setForeground(new Color(236, 202, 15));
		btnEntrar.setBackground(Color.BLACK);
		btnEntrar.setBorder(new LineBorder(new Color(236, 202, 15)));
		btnEntrar.setBounds(619, 381, 96, 32);
		btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnEntrar);
		
		textSenha = new JPasswordField();
		textSenha.setFont(new Font("Arial", Font.PLAIN, 12));
		textSenha.setBorder(new LineBorder(new Color(236, 202, 91)));
		textSenha.setBounds(619, 340, 240, 32);
		contentPane.add(textSenha);
		
		textUsuario = new JTextField();		
		textUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		textUsuario.setBorder(new LineBorder(new Color(236, 192, 91)));
		textUsuario.setBounds(619, 301, 240, 32);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(new Color(236, 192, 91));
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSenha.setBounds(500, 344, 45, 20);
		contentPane.add(lblSenha);
		
		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setForeground(new Color(236, 192, 91));
		lblUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUsuario.setBounds(500, 304, 57, 20);
		contentPane.add(lblUsuario);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 24));
		lblLogin.setForeground(new Color(236, 202, 15));
		lblLogin.setBounds(653, 245, 59, 32);
		contentPane.add(lblLogin);
		
		JLabel lblPainelLogin = new JLabel("");
		lblPainelLogin.setIcon(new ImageIcon(LoginTela.class.getResource("/view/imagens/login-transparente.png")));
		lblPainelLogin.setBounds(0, 0, 1366, 705);
		contentPane.add(lblPainelLogin);
		
		JLabel lblFundoLogin = new JLabel("");
		lblFundoLogin.setBounds(0, 0, 1366, 713);
		lblFundoLogin.setIcon(new ImageIcon(LoginTela.class.getResource("/view/imagens/imagem-fundo-login.jpg")));
		contentPane.add(lblFundoLogin);
		
	}

	public void exibirMensagemErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.ERROR_MESSAGE);
	}

	public JTextField getTextUsuario() {
		return textUsuario;
	}

	public void setTextUsuario(JTextField textUsuario) {
		this.textUsuario = textUsuario;
	}

	public JPasswordField getTextSenha() {
		return textSenha;
	}

	public void setTextSenha(JPasswordField textSenha) {
		this.textSenha = textSenha;
	}
	
	
}

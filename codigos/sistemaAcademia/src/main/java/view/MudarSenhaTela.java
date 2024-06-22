package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.MudarSenhaController;
import model.ModelUsuario;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MudarSenhaTela extends JDialog {

	private JPanel contentPane;
	private JTextField textFieldNomeUsuario;
	private JTextField textFieldSenhaAtual;
	private JPasswordField passwordFieldNovaSenha;
	private JPasswordField passwordFieldDigitarSenhaNovamente;
	
	private static ModelUsuario usuario;
	
	private MudarSenhaController controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MudarSenhaTela frame = new MudarSenhaTela(usuario);
					frame.setTitle("Mudar senha");
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
	public MudarSenhaTela(ModelUsuario usuario) {
		super();
	    setModal(true);
	    
		MudarSenhaTela.usuario = usuario;
		
		controller = new MudarSenhaController(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 417, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMudarSenha = new JLabel("Mudar senha");
		lblMudarSenha.setFont(new Font("Arial", Font.PLAIN, 24));
		lblMudarSenha.setBounds(129, 20, 142, 24);
		contentPane.add(lblMudarSenha);
		
		JLabel lblNomeUsuario = new JLabel("Nome de usu\u00E1rio:");
		lblNomeUsuario.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNomeUsuario.setBounds(20, 82, 162, 24);
		contentPane.add(lblNomeUsuario);
		
		JLabel lblSenhaAtual = new JLabel("Senha atual:");
		lblSenhaAtual.setFont(new Font("Arial", Font.PLAIN, 20));
		lblSenhaAtual.setBounds(20, 126, 109, 24);
		contentPane.add(lblSenhaAtual);
		
		JLabel lblNovaSenha = new JLabel("Nova senha:");
		lblNovaSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNovaSenha.setBounds(20, 203, 109, 24);
		contentPane.add(lblNovaSenha);
		
		JLabel lblDigitarSenhaNovamente = new JLabel("Digite novamente:");
		lblDigitarSenhaNovamente.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDigitarSenhaNovamente.setBounds(20, 247, 162, 24);
		contentPane.add(lblDigitarSenhaNovamente);
		
		textFieldNomeUsuario = new JTextField();
		textFieldNomeUsuario.setEditable(false);
		textFieldNomeUsuario.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldNomeUsuario.setBounds(240, 76, 140, 36);
		contentPane.add(textFieldNomeUsuario);
		textFieldNomeUsuario.setColumns(10);
		
		textFieldSenhaAtual = new JTextField();
		textFieldSenhaAtual.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldSenhaAtual.setColumns(10);
		textFieldSenhaAtual.setBounds(240, 120, 140, 36);
		contentPane.add(textFieldSenhaAtual);
		
		passwordFieldNovaSenha = new JPasswordField();
		passwordFieldNovaSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		passwordFieldNovaSenha.setBounds(240, 197, 140, 36);
		contentPane.add(passwordFieldNovaSenha);
		
		passwordFieldDigitarSenhaNovamente = new JPasswordField();
		passwordFieldDigitarSenhaNovamente.setFont(new Font("Arial", Font.PLAIN, 20));
		passwordFieldDigitarSenhaNovamente.setBounds(240, 241, 140, 36);
		contentPane.add(passwordFieldDigitarSenhaNovamente);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.confirmarMudancaSenha(usuario);
			}
		});
		btnConfirmar.setForeground(Color.WHITE);
		btnConfirmar.setBackground(Color.BLACK);
		btnConfirmar.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(100, 100, 100), new Color(200, 200, 200)));
		btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnConfirmar.setFont(new Font("Arial", Font.PLAIN, 20));
		btnConfirmar.setBounds(200, 328, 180, 40);
		contentPane.add(btnConfirmar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.cancelarMudancaSenha(usuario);
			}
		});
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(Color.GRAY);
		btnCancelar.setBorder(new LineBorder(Color.WHITE));
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCancelar.setBounds(20, 332, 143, 36);
		contentPane.add(btnCancelar);
		
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
		if (JOptionPane.showConfirmDialog(null, mensagem, "Deseja prosseguir com a sua decisão?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return true;
		}
		
		return false;
	}
	
	private void iniciar() {
		controller.atualizarTela(usuario);
		
	}

	/** @implNote Cria uma linha na tela */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Line2D lin = new Line2D.Float(26, 206, 386, 206);
		g2.draw(lin);
	}

	public JTextField getTextFieldNomeUsuario() {
		return textFieldNomeUsuario;
	}

	public void setTextFieldNomeUsuario(JTextField textFieldNomeUsuario) {
		this.textFieldNomeUsuario = textFieldNomeUsuario;
	}

	public JTextField getTextFieldSenhaAtual() {
		return textFieldSenhaAtual;
	}

	public void setTextFieldSenhaAtual(JTextField textFieldSenhaAtual) {
		this.textFieldSenhaAtual = textFieldSenhaAtual;
	}

	public JPasswordField getPasswordFieldNovaSenha() {
		return passwordFieldNovaSenha;
	}

	public void setPasswordFieldNovaSenha(JPasswordField passwordFieldNovaSenha) {
		this.passwordFieldNovaSenha = passwordFieldNovaSenha;
	}

	public JPasswordField getPasswordFieldDigitarSenhaNovamente() {
		return passwordFieldDigitarSenhaNovamente;
	}

	public void setPasswordFieldDigitarSenhaNovamente(JPasswordField passwordFieldDigitarSenhaNovamente) {
		this.passwordFieldDigitarSenhaNovamente = passwordFieldDigitarSenhaNovamente;
	}
	
	
}

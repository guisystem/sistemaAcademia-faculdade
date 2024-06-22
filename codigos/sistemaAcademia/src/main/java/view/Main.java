package view;

import javax.swing.UIManager;

public class Main { 

	public static void main(String[] args) { 
		UIManager.put("OptionPane.yesButtonText", "Sim"); 
		UIManager.put("OptionPane.noButtonText", "Não"); 
		UIManager.put("OptionPane.cancelButtonText", "Cancelar"); 
		
		LoginTela telaPrincipal = new LoginTela(); 
		telaPrincipal.setResizable(false); 
		telaPrincipal.setTitle("Login"); 
		telaPrincipal.setLocationRelativeTo(null); 
		telaPrincipal.setVisible(true); 
	
	} 

}

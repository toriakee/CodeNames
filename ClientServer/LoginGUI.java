package ClientServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.ConnectException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * LoginGUI is used by the user to log in to the system, if the user name and
 * password they pass into the text fields has a match in the database. LoginGUI
 * calls the client which then calls the server requesting to login.
 */
public class LoginGUI extends JPanel implements Observer {
	private JTextField txUsername;
	private JTextField txPassword;
	/**
	 * Create the panel.
	 */
	public LoginGUI(Client client) {
		
		setLayout(null);
		this.setBounds(0, 0, 450, 250);


		JLabel lblSignUp = new JLabel("Sign In");
		lblSignUp.setBounds(34, 25, 61, 16);
		add(lblSignUp);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(34, 73, 79, 16);
		add(lblUsername);

		txUsername = new JTextField();
		txUsername.setBounds(118, 68, 130, 26);
		add(txUsername);
		txUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(34, 114, 79, 16);
		add(lblPassword);

		txPassword = new JPasswordField();
		txPassword.setBounds(118, 109, 130, 26);
		add(txPassword);
		txPassword.setColumns(10);

		JButton backButton = new JButton("Go Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.setChangedClient();
				
				String [] response = {"goBack", "true"};
				client.notifyObservers(response);
			
				
			}
		});

		backButton.setBounds(150, 200, 91, 60);
		backButton.setSize(100, 50);
		add(backButton);

		JButton btnNewButton = new JButton("Sign In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				client.start(); 	
					
					
				System.out.println("Client started");
				String username = txUsername.getText();
				String password = txPassword.getText();
				if (username.equals("") || password.equals("")) {
					 JOptionPane.showMessageDialog(lblSignUp, "Username and password cannot be empty");
					return;
				}
				String[] response = client.signin(username, password);
				System.out.println(Arrays.toString(response));
				if(response[0].equals("true1")){
					JOptionPane.showMessageDialog(lblSignUp, "Sign- in successful");
					return;
			
				}
				if(response[0].equals("true2")){
					JOptionPane.showMessageDialog(btnNewButton, "Sign- in successful");
					return;
				}
				
				if(response[1].equals("false1")){
					JOptionPane.showMessageDialog(backButton, "User already signed in!");
					return;
				}
				
				if(response[1].equals("false2")){
					JOptionPane.showMessageDialog(null, "Username and password do not match");
					return;
				}
			}
		});
		btnNewButton.setBounds(34, 200, 91, 40);
		btnNewButton.setSize(100, 50);
		add(btnNewButton);

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}


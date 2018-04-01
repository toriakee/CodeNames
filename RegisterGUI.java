package ClientServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * RegistrationGUI is used by the user to add a new user name and password.
 * This only happens if the user name isn't contained already within the database.
 * Registration calls the client which then calls the server requesting to register
 * a new user
 */
public class RegisterGUI extends JPanel implements Observer {
	/**
	 * 
	 */
	
	private JTextField txUsername;
	private JTextField txPassword;
	private JTextField txPassword1;
	public Client client;
	
	public RegisterGUI(Client client) {
		this.client = client;
		setLayout(null);
		this.setBounds(0, 0, 450, 250);

		JLabel lblSignUp = new JLabel("Register");
		lblSignUp.setBounds(34, 25, 61, 16);
		add(lblSignUp);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(34, 73, 79, 16);
		add(lblUsername);

		txUsername = new JTextField();
		txUsername.setBounds(118, 68, 130, 26);
		add(txUsername);
		txUsername.setColumns(10);

		JLabel pass1 = new JLabel("Password:");
		pass1.setBounds(34, 114, 79, 16);
		add(pass1);

		txPassword = new JPasswordField();
		txPassword.setBounds(118, 109, 130, 26);
		add(txPassword);
		txPassword.setColumns(10);

		JLabel pass2 = new JLabel("Re-enter:");
		pass2.setBounds(34, 150, 79, 16);
		add(pass2);

		txPassword1 = new JPasswordField();
		txPassword1.setBounds(118, 150, 130, 26);
		add(txPassword1);
		txPassword1.setColumns(10);
		
		
		JButton backButton = new JButton("Go Back");
		backButton.addActionListener(new ActionListener() {
			private Object loginGUI;

			public void actionPerformed(ActionEvent e) {
				
				client.setChangedClient();
				String [] response = {"goBack", "true"};
				client.notifyObservers(response);
			}
		});
		
		backButton.setBounds(150, 200, 91, 60);
		backButton.setSize(100, 50);
		add(backButton);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.start();
				String username = txUsername.getText();
				String password = txPassword.getText();
				String password1 = txPassword1.getText();

				if (username.equals("") || password.equals("") || password1.equals("")) {
					JOptionPane.showMessageDialog(null, "Both username and password can not be empty!",
							"Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (!password.equals(password1)) {
					JOptionPane.showMessageDialog(null, "Passwords do not match", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if(username.length() > 9) {
					JOptionPane.showMessageDialog(null, "Username cannot exceed 9 characters ", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(password.length() > 6) {
					JOptionPane.showMessageDialog(null, "Password cannot exceed 6 characters ", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				
				String[] response = client.register(username, password);

				if (response[1].equals("true")) {
					JOptionPane.showMessageDialog(null , "Registration successful!", "Warning",
							JOptionPane.WARNING_MESSAGE);

				} else if (response[1].equals("false")) {
					JOptionPane.showMessageDialog(null, response[2], "Username taken please try again!",
							JOptionPane.WARNING_MESSAGE);
					client.stop();
				}
			}
		});
		btnNewButton.setBounds(34, 200, 91, 40);
		btnNewButton.setSize(100, 50);
		add(btnNewButton);

	}

	public static void main(String[] args) {

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}

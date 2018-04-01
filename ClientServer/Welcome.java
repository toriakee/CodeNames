package ClientServer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * Frame class to welcome users
 *
 */

@SuppressWarnings("serial")
public class Welcome extends JPanel implements Observer {

	private Client client;
	private JFrame welcome;
	private JButton btnLogin;
	private JButton btnRegister;
	// private LoginGUI loginGUI = new LoginGUI(null);

	
	public Welcome(Client client) {
		this.client = client;
		setLayout(null);
		this.setBounds(0, 0, 450, 250);

		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));

		panel.setLayout(null);

		JLabel label_1 = new JLabel("Welcome");
		label_1.setBackground(Color.LIGHT_GRAY);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(120, 11, 79, 27);
		add(label_1);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLogin.setBounds(40, 75, 100, 100);
		add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.setChangedClient();
				
				String [] response = {"changelogin", "true"};
				client.notifyObservers(response);
			}
		});

		btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRegister.setBounds(170, 75, 100, 100);
		add(btnRegister);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBounds(0, 0, 350, 300);
				
				client.setChangedClient();
				
				String [] response = {"changeRegister", "true"};
				client.notifyObservers(response);
			}

		});

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("welcome observer reached");
	}
}

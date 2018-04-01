package ClientServer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")

/**
 * Class ChatBox is for the pop-up chat enabling users to communicate with each
 * other during the game. ChatBox extends JPanel and is a Panel which is added
 * to the container frame ClientApp2.
 * 
 * @author Tori Keegan & James Fowke
 *
 */

public class ChatBox extends JPanel implements Observer {
	// instance variables
	private JTextField input;
	private JButton send;
	private JTextArea chat;
	private Client client;
	private JScrollPane scrollPane = new JScrollPane();

	
	/**
	 * Constructor for ChatBox takes the client as the parameter
	 * @param client of type Client
	 */
	
	public ChatBox(Client client) {
		System.out.println("chatbox constructor");
		System.out.println(client == null);
		this.client = client;
		
		//set the layout
		setLayout(new BorderLayout(10, 10));

		//components defined
		chat = new JTextArea();
		chat.setBounds(6, 6, 788, 514);
		chat.setEditable(false);
<<<<<<< .mine
||||||| .r145
=======
		
		chat.add(scrollPane);

>>>>>>> .r150
		
<<<<<<< .mine
		//add to the panel 
||||||| .r145
=======
	
		

		

>>>>>>> .r150
		this.add(chat, BorderLayout.CENTER);

		// create input TextField for users to input text
		input = new JTextField();
		
		//ActionListener implements ActionPerformed method 
		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				send.doClick();
			}
		});

		// added to the panel
		JPanel inputPane = new JPanel(new GridLayout(1, 2));
		inputPane.add(input);
		
		//send button component created 
		send = new JButton("Send");
		
		//ActionListener implements ActionPerformed method when the button is clicked
		send.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String message = input.getText();
				if (message.equals("")) {
					JOptionPane.showMessageDialog(send, "Message cannot be empty!");
				}
				input.setText("");
				System.out.println("Message is: " + message);
				client.send_message(message);
			}
		});
		send.setBounds(640, 533, 83, 29);

		//add to the panel
		inputPane.add(send);
		this.add(inputPane, BorderLayout.SOUTH);
		JLabel chatTitle = new JLabel("Chat", SwingConstants.CENTER);
		
		//scrollpane
		JScrollPane jsp = new JScrollPane(chat);
		add(jsp,BorderLayout.CENTER);
		chatTitle.setForeground(new Color(59, 89, 182));
		
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.add(chatTitle, BorderLayout.NORTH);

	}
	
	/**
	 * Update method which takes o of type Observable and arg of type Object. 
	 * Allows the messages to be sent and received. 
	 */

	public void update(Observable o, Object arg) {
		String[] fromServer = (String[]) arg;

		System.out.println("fromserver: " + Arrays.deepToString(fromServer));

		if (fromServer[0].equals("send-message")) {
			System.out.println("chat append reached");

			chat.append(fromServer[1] + ": " + fromServer[2]);
			chat.append("\n");
		}

	}

}

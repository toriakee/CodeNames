package ClientServer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import GUI2.GameModel;
import GUI2.PlayerView;
import GUI2.SpyView;

/**
 * Class ClientApp2
 * 
 * @author James Fowke, Benjamin Fellows, Tori Keegan
 *
 */

@SuppressWarnings("serial")
public class ClientApp2 extends JFrame implements Observer {

	public Welcome welcome;
	public RegisterGUI registerGUI;
	public LoginGUI loginGUI;
	public PlayerView player;
	public SpyView spy;
	public ChatBox chatbox;
	public GameModel model;
	public JFrame frame;
	public Client client;
	public String[] words = new String[25];
	public String[] colors = new String[25];
	public static final Color red = new Color(255, 51, 51);
	public static final Color blue = new Color(51, 153, 255);
	public Color[] colorCol = new Color[25];
	public Server thread;
	public serverThread serverThread;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					/*
					 * 'frame' is the initial frame onto which we place LoginGUI
					 * and, once the user has logged in, the instance of
					 * PlayerView which loads the board.
					 * 
					 */

					int port = 4000;
					String host = "147.188.195.162";

					Client client = new Client(host, port);

					ClientApp2 frame = new ClientApp2(client);
					frame.setVisible(true);
					System.out.println("client app called 2");

				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientApp2(Client client) {
		System.out.println("vlient app const");
		System.out.println(client == null);
		this.client = client;
		setTitle("Welcome");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		model = new GameModel();
		loginGUI = new LoginGUI(client);
		registerGUI = new RegisterGUI(client);

		chatbox = new ChatBox(client);
	

		welcome = new Welcome(client);
		player = new PlayerView(chatbox, client, words);
		spy = new SpyView(chatbox, client, words, colorCol);
		serverThread = new serverThread(client);

		client.addObserver(this);
		client.addObserver(loginGUI);
		client.addObserver(registerGUI);
		client.addObserver(chatbox);
		client.addObserver(player);
		client.addObserver(spy);
		client.addObserver(welcome);

		setTitle("Welcome");

		setContentPane(welcome);

	}

	/**
	 * update method takes updates from the client and reacts accordingly
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("");
		System.out.println("");

		String[] fromServer = (String[]) arg;
		String[] wordsFromServerPlayer = new String[25];
		String[] colorsToSpy = new String[25];
		String[] wordsToSpy = new String[25];
		Color[] spyColors = new Color[25];

		System.out.println("from server: " + Arrays.toString(fromServer));
		System.out.println("");
		System.out.println("");

		if (fromServer[0].equals("changeRegister")) {
			setTitle("Register");
			registerGUI = new RegisterGUI(client);

			setContentPane(registerGUI);
		}

		else if (fromServer[0].equals("changelogin")) {
			setTitle("Log in");
			loginGUI = new LoginGUI(client);

			setContentPane(loginGUI);
		}

		else if (fromServer[0].equals("goBack")) {
			setTitle("Welcome");
			welcome = new Welcome(client);

			setContentPane(welcome);
		}

		else if (fromServer[0].equals("register") && fromServer[1].equals("true")) {
			setTitle("Log in");
			loginGUI = new LoginGUI(client);

			setContentPane(loginGUI);
		}

		else if (fromServer[0].equals("true2")) {
			setLayout(new BorderLayout(5, 5));
			setSize(1800, 1020);
			setTitle("Player");

			for (int i = 0; i < 25; i++) {
				wordsFromServerPlayer[i] = fromServer[i + 1];
			}

			player = new PlayerView(chatbox, client, wordsFromServerPlayer);

			this.serverThread.start();

			this.setContentPane(player);

		}

		else if (fromServer[0].equals("true1")) {
			setLayout(new BorderLayout(5, 5));
			setSize(1800, 1020);
			setTitle("Spy View");

			for (int k = 0; k < 25; k++) {
				wordsToSpy[k] = fromServer[k + 1];
				colorsToSpy[k] = fromServer[k + 26];
			}

			for (int h = 0; h < 25; h++) {

				if (colorsToSpy[h].equals("blue")) {
					spyColors[h] = blue;
				} else if (colorsToSpy[h].equals("red")) {
					spyColors[h] = red;
				}
			}

			spy = new SpyView(chatbox, client, wordsToSpy, spyColors);

			this.serverThread.start();
			this.setContentPane(spy);

		}

	}

}

package ClientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Protocol.SimpleProtocol;

public class ClientThread extends Thread {
	private Server server;
	private SimpleProtocol sp;
	private Socket socket;
	private boolean running = true;
	private BufferedReader input;
	private DataOutputStream output;
	private String username;
	public static List<String> allMessages = new ArrayList<String>();

	/**
	 * Constructor for client thread
	 * 
	 * @param server
	 * @param clientSocket
	 */
	public ClientThread(Server server, Socket clientSocket) {
		this.sp = new SimpleProtocol();
		this.server = server;
		this.socket = clientSocket;

		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());
			System.out.println("");
			System.out.println("");

			output.writeBytes(sp.createMessage("welcome", "Welcome to my server!") + "\n");

		} catch (IOException e) {
			System.out.println("Sorry, there was a problem with the client/server connection!");
			e.printStackTrace();
		}
	}

	/**
	 * Run method of the thread is called in the server, when a new client is
	 * created. Method checks for client inputs and depending on the contents of
	 * the string array it contains it will call a specific method.
	 */
	public void run() {
		try {

			String[] input = getInput();

			if (input[0].equals("register")) {
				register(input[1], input[2]);
			}

			if (input[0].equals("sign-in")) {
				username = input[1];
				signIn(input[1], input[2]);
			}

			while (running) {
				String[] newInput = getInput();

				if (newInput.length != 0) {

					if (newInput[0].equals("playerguess")) {
						playerGuess(newInput[1]);
					}

					if (newInput[0].equals("send-message")) {
						sendMessage(newInput[1]);
					}

				}

			}

		} catch (IOException e) {
			System.out.println("Sorry, there was a problem!");
			e.printStackTrace();
		}
	}

	public String[] getInput() throws IOException {
		String readLine = input.readLine();
		return sp.decodeMessage(readLine);

	}

	/**
	 * Sign is used request a sign in from the server. The server gives a
	 * response in the form of an integer. A response of 2 means user name and
	 * password are correct and you have been assigned the player view. A
	 * response of 1 means user name and password are correct and you have been
	 * assigned the spy view. A response of 0 means the user is already logged
	 * in. A response of -1 means that the user name and password combination is
	 * incorrect.
	 * 
	 * @param username
	 *            a user name.
	 * @param password
	 *            a password.
	 */
	public void signIn(String username, String password) {
		try {

			String[] response = server.authenticate(username, password);

			if (response[0].equals("2")) {

				output.writeBytes(sp.createMessage("true1", response[1], response[2], response[3], response[4],
						response[5], response[6], response[7], response[8], response[9], response[10], response[11],
						response[12], response[13], response[14], response[15], response[16], response[17],
						response[18], response[19], response[20], response[21], response[22], response[23],
						response[24], response[25], response[26], response[27], response[28], response[29],
						response[30], response[31], response[32], response[33], response[34], response[35],
						response[36], response[37], response[38], response[39], response[40], response[41],
						response[42], response[43], response[44], response[45], response[46], response[47],
						response[48], response[49], response[50], "!") + "\n");

			}

			else if (response[0].equals("1")) {

				output.writeBytes(sp.createMessage("true2", response[1], response[2], response[3], response[4],
						response[5], response[6], response[7], response[8], response[9], response[10], response[11],
						response[12], response[13], response[14], response[15], response[16], response[17],
						response[18], response[19], response[20], response[21], response[22], response[23],
						response[24], response[25], response[26], response[27], response[28], response[29],
						response[30], response[31], response[32], response[33], response[34], response[35],
						response[36], response[37], response[38], response[39], response[40], response[41],
						response[42], response[43], response[44], response[45], response[46], response[47],
						response[48], response[49], response[50], "!") + "\n");

			}

			else if (response[0].equals("0")) {

				output.writeBytes(sp.createMessage("sign-in", "false1", "User already logged in!") + "\n");
				stopThread();

			}

			else {
				output.writeBytes(sp.createMessage("sign-in", "false2", "LogIn unsuccessful") + "\n");
				stopThread();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Register is called to request a the registration of a new user to the
	 * user database.
	 * 
	 * @param username
	 * @param password
	 */
	public void register(String username, String password) {
		System.out.println("Register called");
		try {

			if (!server.newUser(username, password)) {
				output.writeBytes(sp.createMessage("register", "false", "Username taken!") + "\n");
				stopThread();
			} else {
				output.writeBytes(sp.createMessage("register", "true", "Registration successful!") + "\n");
				stopThread();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendMessage(String message) throws IOException {
		try {
			System.out.println("clientThread called");
			String message1 = this.username + ": " + message;
			
			server.getMessage(this.username, message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a player guess to the server
	 * 
	 * @param index
	 */
	public void playerGuess(String index) {
		System.out.println("ClientThread: Player guess index: " + index);
		try {
			server.test(index);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopThread() {
		running = false;
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Problem closing client socket!");
			e.printStackTrace();
		}

		this.interrupt();
	}

	public DataOutputStream getOutput() {
		return output;
	}

}

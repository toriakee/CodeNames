package ClientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.util.Arrays;
import java.util.Observable;

import javax.swing.JOptionPane;

import Protocol.SimpleProtocol;

public class Client extends Observable {

	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	private String host = "";
	private Integer port = 0;
	private SimpleProtocol proto = new SimpleProtocol();

	
	/**
	 * Constructor for client
	 * @param host the server host number
	 * @param port the port address to listen on
	 */
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	/**
	 * gets a response from the server
	 * @return the decoded server message
	 */
	public String[] getResponse() {
		try {

			System.out.println("get repsonse called");
			return proto.decodeMessage(inFromServer.readLine());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * sign in takes a sign in response from a server. The response it receives
	 * says whether the sign in was successful or not, if it was the response
	 * also contains a list of words and colors which will be used to create the
	 * game board. sign in notifies observers
	 * 
	 * @param username
	 *            - user name
	 * @param password
	 *            - password
	 */
	public String[] signin(String username, String password) {
		String string = proto.createMessage("sign-in", username, password);
		try {
			outToServer.writeBytes(string + "\n");
			String[] response = this.getResponse();

			setChanged();
			notifyObservers(response);

			return response;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			System.out.println(" connection to server has been lost");
			JOptionPane.showMessageDialog(null, "Connection has been lost!");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public DataOutputStream getOutToServer() {
		return outToServer;
	}

	/**
	 * Register takes in a register response from the server.
	 * 
	 * @param username-username.
	 * @param password- username.
	 * @return
	 */
	public String[] register(String username, String password) {
		String string = proto.createMessage("register", username, password);

		try {
			outToServer.writeBytes((string) + "\n");
			String[] response = this.getResponse();
			if (response[1].equals("true1")) {
				System.out.println("Username not in DB");
			} else {
				System.out.println(response[2]);
			}

			setChanged();
			notifyObservers(response);
			return response;
		} catch (ConnectException e) {
			System.out.println(" connection to server has been lost");
			JOptionPane.showMessageDialog(null, "Connection has been lost!");
			return null;
		}

		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Sends a message to the server
	 */
	public void send_message(String msg) {
		String string = proto.createMessage("send-message", msg);
		try {

			System.out.println("send message client called: " + msg);
			outToServer.writeBytes(string + "\n");

		} catch (ConnectException e) {
			System.out.println(" connection to server has been lost");
			JOptionPane.showMessageDialog(null, "Connection has been lost!");
			return;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection has been lost!");
			return;
		}
	}

	/**
	 * sends the player guess to the server
	 * @param index the positon of the button array pressed by the player view
	 */
	public void playerGuess(String index) {
		System.out.println("Client: playerGuess reached");
		String string = proto.createMessage("playerguess", index);

		try {
			System.out.println("Client: accepted");
			outToServer.writeBytes(string + "\n");
			System.out.println("Client: OutToServer accepted");

		} catch (IOException e) {
			System.out.println("Exception at Client" + e);
			JOptionPane.showMessageDialog(null, "Connection has been lost");

		}
	}

	
	/**
	 * helper method, allowing setChanged to be called in other classes
	 */
	public void setChangedClient() {
		setChanged();
	}

	
	
	public void start() {
		try {
			clientSocket = new Socket(this.host, this.port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println(this.getResponse()[1]);
		} catch (ConnectException e) {
			System.out.println(" connection to server has been lost");
			JOptionPane.showMessageDialog(null, "Connection has been lost!");
			return;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {

			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}

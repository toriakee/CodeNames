package ClientServer;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Database.ConnectionManager;
import GUI2.GameModel;
import Protocol.SimpleProtocol;

/**
 * This server will accept input from a client, setting up a socket connection
 * and allowing the client to close the connection once finished.
 */
public class Server {

	String host;
	int port;
	public SimpleProtocol proto;
	public ExecutorService executor;
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	public List<String> messageList;
	public ArrayList<String> users = new ArrayList<>();
	public GameModel model = new GameModel();
	public ArrayList<ClientThread> threadList = new ArrayList<>();

	/**
	 * Constructor for server
	 * 
	 * @param port
	 */
	public Server(String host, int port) { ///////////////// addd host
		// this.model = new GameModel();
		this.port = port; // given port number
		this.host = host;
		this.proto = new SimpleProtocol();
		this.executor = Executors.newFixedThreadPool(1000);
		users = new ArrayList<>();
		messageList = new ArrayList<>();
	}

	/**
	 * Starts the server, and initiates all the sockets and client thread:
	 */
	public void start() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {

				Socket clientSocket = serverSocket.accept();
				ClientThread tmp = new ClientThread(this, clientSocket);
				executor.execute(tmp);
				threadList.add(tmp);
			}
		}

		catch (IOException e) {
			System.out.println("IOException on socket listen: " + e);
			e.printStackTrace();
		}
	}

	public ArrayList<ClientThread> getThreadList() {
		return threadList;
	}

	/**
	 * Authenticate is called by a client. The method first checks that the user
	 * isn't already logged into the system. This is done by checking if the
	 * user name is contained with the users array list. If it is not, the
	 * database is queried to check for a user name. If the user name is
	 * contained within the database its corresponding password is checked for a
	 * match. If the user name and password match authenticate returns an
	 * integer depending on the amount of users logged into the system. The
	 * value of the integer is used to determine which view the user will load.
	 * Also if a user logs in successfully all of their game data(colors and
	 * words) is retrieved and included in the message sent back to the client.
	 * 
	 * @param username
	 *            to check against the database.
	 * @param password
	 *            to check against the database.
	 * @return an integer either 2, 1, 0, -1 or -100.
	 * @throws Exception
	 *             if an error occurs
	 */
	public String[] authenticate(String username, String password) {
		String selectUsername = "SELECT * FROM gamePlayers WHERE username=?;";

		String[] response = new String[5];

		int id = users.size();

		try {
			this.connection = ConnectionManager.getConnection();

			connection.setAutoCommit(false);
			PreparedStatement selectUs = connection.prepareStatement(selectUsername);
			selectUs.setString(1, username);

			resultSet = selectUs.executeQuery();

			if (users.contains(username)) {
				response[0] = "0";
				return response;
			}

			if (resultSet.next()) {

				// incorrect password
				if (!(resultSet.getString(3).equals(password))) {
					System.out.println("Incorrect Password!");
					System.out.println(password);

					response[0] = "-1";
					return response;

					// loads player board and send words to client to set up
					// board
				} else if ((resultSet.getString(3).equals(password)) && id % 2 != 0) { /// change
					connection.close();
					System.out.println("Correct Password!");
					users.add(username);

					String[] colorsArr = model.getColorsArr();
					String[] wordsArr = model.getWordsArr();

					ArrayList<String> combinedArr = new ArrayList<>();
					combinedArr.add("1");

					for (int i = 0; i < wordsArr.length; i++) {
						combinedArr.add(wordsArr[i]);
					}

					for (int j = 0; j < colorsArr.length; j++) {
						combinedArr.add(colorsArr[j]);
					}

					String[] Serversponse = combinedArr.toArray(new String[combinedArr.size()]);

					return Serversponse;

				}

				// loads spy view and sends colors and words to client.
				else if ((resultSet.getString(3).equals(password)) && id % 2 == 0) {
					connection.close();
					System.out.println("Correct Password!");

					users.add(username);

					String[] colorsArr = model.getColorsArr();
					String[] wordsArr = model.getWordsArr();

					ArrayList<String> combinedArr = new ArrayList<>();
					combinedArr.add("2");

					for (int i = 0; i < wordsArr.length; i++) {
						combinedArr.add(wordsArr[i]);
					}

					for (int j = 0; j < colorsArr.length; j++) {
						combinedArr.add(colorsArr[j]);
					}

					String[] Serversponse1 = combinedArr.toArray(new String[combinedArr.size()]);

					System.out.println("server game data resposne: " + Arrays.toString(Serversponse1));

					return Serversponse1;
				}
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response[0] = "-100";
		return response;
	}

	/**
	 * newUser is called by a client if they wish to register a new account to
	 * the database. newUser adds the user name and password to the database if
	 * the user name doesn't match one already in the database.
	 * 
	 * @param username
	 *            a user name
	 * @param password
	 *            a password
	 * @return a boolean, true if registration was successful, false if not.
	 * @throws Exception
	 *             if an error occurs
	 */
	public boolean newUser(String username, String password) throws Exception {
		String selectUsername = "SELECT * FROM gameplayers WHERE username=?;";
		try {

			this.connection = ConnectionManager.getConnection();

			connection.setAutoCommit(false);
			PreparedStatement selectUs = connection.prepareStatement(selectUsername);

			selectUs.setString(1, username);
			resultSet = selectUs.executeQuery();

			if (resultSet.next()) {
				System.out.println("Username taken!");
				return false;
			}

			addUser(username, password);

			System.out.println("New player has been inserted into Players");

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return true;
	}

	/**
	 * addUser is called by the client when a new user is registered to the
	 * database.
	 * 
	 * @param username
	 *            the user name to be registered
	 * @param password
	 *            the corresponding password for the user name.
	 * @throws Exception
	 */
	public void addUser(String username, String password) throws Exception {
		System.out.println("Inserting new player into GamePlayers...");
		System.out.println(password.length());
		try {
			this.connection = ConnectionManager.getConnection();
			PreparedStatement stmt;
			stmt = connection
					.prepareStatement("INSERT INTO GamePlayers VALUES (" + nextUserID() + ", ?, ?, null, null)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public String playerGuess(String index) {

		String[] colorsArr = model.getColorsArr();

		System.out.println("Server:" + index);

		String color = colorsArr[Integer.parseInt(index)];

		int blueScore = model.getBlueScoreCounter();

		System.out.println("Blue Score: " + blueScore);

		if (color.equals("blue") && blueScore == 19) {
			color = "bluewin";
		} else if (color.equals("blue") && blueScore < 20) {
			blueScore++;
			System.out.println("SERVER: Blue Score reached");
		} else if (color.equals("red")) {
			color = "redwin";
		}

		model.setBlueScoreCounter(blueScore);

		System.out.println("Server color sent:    " + color);

		return color;
	}

	public void test(String index) {
		String outcome = "";

		if (playerGuess(index).equals("blue")) {
			outcome = "blue";
		} else if (playerGuess(index).equals("bluewin")) {
			outcome = "bluewin";
		} else if (playerGuess(index).equals("redwin")) {
			outcome = "redwin";
		}

		for (int i = 0; i < threadList.size(); i++) {
			try {
				threadList.get(i).getOutput().writeBytes(proto.createMessage("playerguess", index, outcome) + "\n");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Helper method for newUser.
	 * 
	 * @return the number of users in the database +1.
	 * @throws SQLException
	 */
	public static int nextUserID() throws SQLException {

		Connection connection = null;
		int id = 0;

		try {

			connection = ConnectionManager.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT userID" + " FROM gameplayers " + "ORDER BY userID DESC " + "LIMIT 1");

			while (rs.next()) {
				id = rs.getInt(1);
				System.out.println(id + 1);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return id + 1;
	}

	public void getMessage(String username, String message) {
		for (int i = 0; i < threadList.size(); i++) {
			try {
				threadList.get(i).getOutput().writeBytes(proto.createMessage("send-message", username, message) + "\n");
				System.out.println("thread list called: " + i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Main method to run server
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				String host = "127.0.0.1";
				int port = 4000;
				Server x = new Server(host, port);
				x.start();
			}
		});
	}
}

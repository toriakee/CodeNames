package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;;


public class DBmanager {

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public boolean login(String username, String password) throws Exception {
		Player player = null;
		String selectUsername = "SELECT * FROM Players WHERE username=?;";

		try {

			this.connection = ConnectionManager.getConnection(); // establishes connection

			connection.setAutoCommit(false);
			PreparedStatement selectUs = connection.prepareStatement(selectUsername); // queries DB
			selectUs.setString(1, username);

			resultSet = selectUs.executeQuery();
			
			if (resultSet.next()) {

				if (!(resultSet.getString(3).equals(password))) {
					// username correct, password incorrect
					System.out.println("Incorrect Password!");
					return false;
				} else {
					// username and password correct
					System.out.println("Password correct");
					//gets player info sets to new player object
//					player = new Player(resultSet.getInt("userid"), resultSet.getString("username"),
//							resultSet.getString("password"), resultSet.getInt("victories"), resultSet.getInt("losses"));
					return true;
				}
			}
			
			
			connection.setAutoCommit(true);
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false; // returns players
	}

	public static void main(String[] args) throws Exception {
		DBmanager dbTest = new DBmanager();

		dbTest.login("player1", "aaa001");

		System.out.println("");
		dbTest.login("player12", "aaa012");

		System.out.println("");
		dbTest.login("dfafaffa", "aaa002");
	}
}

package Database;

import java.sql.*;
import java.util.ArrayList;

/**
 * A class that contains a randomSet method for querying the Words table in the
 * database to retrieve a new set of 25 words for each new game. randomSet makes
 * use of the helper method random.
 *
 */

public class WordsMethods {
	public static String[] wordsArray;


	public static String random(int min, int max) {
		int range = (max - min) + 1;
		int intt = (int) ((Math.random() * range) + min);
		return Integer.toString(intt);
	}

	public static String[] randomSet(){
		
		
		
		ArrayList<String> words = new ArrayList<String>();
		Connection connection = null;

		try {
			connection = ConnectionManager.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT set" + random(1, 27) + " FROM Words");
			while (rs.next()) {
				words.add(rs.getString(1));
			}
			for (String word : words) {
				System.out.println(word);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		wordsArray = words.toArray(new String[words.size()]);
		return wordsArray;
	}


	public static void main(String[] args) throws Exception {
		randomSet();
	}

}

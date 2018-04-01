package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A class that contains a randomSet method for querying the GameWords relation
 * to retrieve a new set of 25 words for each new game. randomSet makes use of
 * the helper method random.
 *
 */
public class GameWordsMethods {

	public static String[] wordsArray;

	public static Integer[] random(int min, int max) {

		int range = (max - min) + 1;
		Set<Integer> ints = new LinkedHashSet<>();

		while (ints.size() < 25) {
			ints.add((int) ((Math.random() * range) + min));// randomly generate array of 25 1-675
		}

		Integer[] arr = ints.toArray(new Integer[ints.size()]);
		return arr;
	}

	public static String[] randomSet() throws SQLException {
		ArrayList<String> words = new ArrayList<String>();
		Connection connection = null;

		try {
			connection = ConnectionManager.getConnection();
			PreparedStatement stmt;
			stmt = connection.prepareStatement("SELECT word FROM GameWords WHERE wordID=?" + "OR wordID=?"
					+ "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?"
					+ "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?"
					+ "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?"
					+ "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?" + "OR wordID=?");

			Integer[] nums = random(1, 675);

			stmt.setInt(1, nums[0]);
			stmt.setInt(2, nums[1]);
			stmt.setInt(3, nums[2]);
			stmt.setInt(4, nums[3]);
			stmt.setInt(5, nums[4]);
			stmt.setInt(6, nums[5]);
			stmt.setInt(7, nums[6]);
			stmt.setInt(8, nums[7]);
			stmt.setInt(9, nums[8]);
			stmt.setInt(10, nums[9]);
			stmt.setInt(11, nums[10]);
			stmt.setInt(12, nums[11]);
			stmt.setInt(13, nums[12]);
			stmt.setInt(14, nums[13]);
			stmt.setInt(15, nums[14]);
			stmt.setInt(16, nums[15]);
			stmt.setInt(17, nums[16]);
			stmt.setInt(18, nums[17]);
			stmt.setInt(19, nums[18]);
			stmt.setInt(20, nums[19]);
			stmt.setInt(21, nums[20]);
			stmt.setInt(22, nums[21]);
			stmt.setInt(23, nums[22]);
			stmt.setInt(24, nums[23]);
			stmt.setInt(25, nums[24]);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				words.add(rs.getString(1));
			}
		

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		wordsArray = words.toArray(new String[words.size()]);
		return wordsArray;
	}

	

}

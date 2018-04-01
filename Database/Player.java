package Database;

public class Player {
	
	private int userid;
	private String username;
	private String password;
	private int victories;
	private int losses;
	

	public Player(int userid, String username, String password, int victories, int losses) {
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.victories = victories;
		this.losses = losses;
	}


	public int getUserid() {
		return userid;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	public int getVictories() {
		return victories;
	}


	public int getLosses() {
		return losses;
	}
	
	
}

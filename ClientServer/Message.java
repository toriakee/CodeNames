package ClientServer;

import java.util.Date;


/**
 * 
 * @author jxf790
 *
 */
public class Message {
	private int offset; 
	private String username; 
	private Date time; 
	private String message; 
	
	public Message(int offset, String username, String message){
		this.offset=offset;
		this.username=username;
		this.time = new Date(); 
		this.message=message;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public String getUserName() { 
		return username;
	}
	
	public String getTime() {
		return time.toString(); 
	}
	
	public String getMessage() { 
		return message;
	}
}

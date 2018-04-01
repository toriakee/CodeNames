package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {

	int port;
	private DataInputStream dis;
	private DataOutputStream dos;

	
	public Server(int port) {
		this.port = port;
	}

	public void start() {
		try {
			ServerSocket ss = new ServerSocket(port);
			int id = 0;

			while (true) {
				Socket s = null;
				
				// socket object to receive incoming client requests
				s = ss.accept();
				System.out.println("A new client is connected : " + s);				
				
				// obtaining input and out streams
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());

				System.out.println("Assigning new thread for this client");

				// create a new thread object
				Thread t = new ClientHandler(s, dis, dos);
				
				// Invoking the start() method
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
			int port = 3002;
			Server serv = new Server(port);
			serv.start();
	}
		
}

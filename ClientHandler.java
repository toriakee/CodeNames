package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {

	private DataInputStream dis;
	private DataOutputStream dos;
	private Socket s;


	// Constructor
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
		this.s = s;
		this.dis = dis;
		this.dos = dos;

	}

	@Override
	public void run() {
		String received;
		while (true) {
			try {

				// Ask user
				dos.writeUTF("Type Exit to terminate connection.\n" + "Please enter a hint: ");

				// receive the hint from client
				received = dis.readUTF();
				dos.writeUTF(received);

				System.out.println(received);

				if (received.equals("Exit")) {
					System.out.println("Client " + this.s + " sends exit...");
					System.out.println("Closing this connection.");
					this.s.close();
					System.out.println("Connection closed");
					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			// closing resources
			this.dis.close();
			this.dos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
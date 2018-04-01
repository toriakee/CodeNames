package ClientServer;

import java.util.Arrays;

public class serverThread extends Thread {
	private Client client;

	// public serverThread(Client client) {
	// this.client = client;
	// }

	public serverThread(Client client) {
		this.client = client;
	}

	public void run() {
		while (true) {

			String[] response = client.getResponse();

			System.out.println("client thread:   ");
			System.out.println(response);
			System.out.println("client response thread:" + Arrays.toString(response));

			if (response[0].equals("playerguess")) {

				client.setChangedClient();
				client.notifyObservers(response);
				System.out.println("serverThread called");

			}

			if (response[0].equals("send-message")) {

				
			
				client.setChangedClient();
				client.notifyObservers(response);
			
			}

		}
	}

}

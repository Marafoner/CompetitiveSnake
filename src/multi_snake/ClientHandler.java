package multi_snake;

import java.net.Socket;

public class ClientHandler implements Runnable{
	@SuppressWarnings("unused")
	private Socket client;
	public ClientHandler(Socket client) {
		this.client = client;
	}
	@Override
	public void run() {
		// run stuff here i guess
		
	}

}

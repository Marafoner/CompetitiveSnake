package multi_snake;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable{
	@SuppressWarnings("unused")
	private Socket client;
	private InputStream direction_input;
	private DataInputStream dataInput;
	private OutputStream snake_stream;
	private ObjectOutputStream objectOutput;
	private Snake[] board;
	private int id; //ID of the board we send input for
	public ClientHandler(Socket client, int id) throws IOException {
		this.client = client;
		this.id = id;
		
		//Setting Up Input OutPut streams the moment the ClientHandlers are Declared
		this.direction_input = client.getInputStream();
		this.dataInput = new DataInputStream(direction_input);
		this.snake_stream = client.getOutputStream();
		this.objectOutput = new ObjectOutputStream(snake_stream);
		
	}
	@Override
	public void run(){
		// run stuff here i guess
		
		System.err.println("code here pls");
		return;
	}

}

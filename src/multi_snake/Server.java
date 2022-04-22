package multi_snake;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
//added a comment
public class Server {
	/*
	 * "Server" class is managing all of the logic through using Snake class,
	 * which is later sent through "ObjectOutputStream" as a Snake[] to the client which
	 * visualizes all of the boards through using an ArrayList<Tail> of both
	 * Snake objects in the array.
	 */
	
	private Snake client_snake = new Snake(0, 9, 1);
	private Snake client2_snake = new Snake(1, 9, 1);
	private Snake[] snake_board = {client_snake, client2_snake};
	
	private ServerSocket server;
	private Socket client;
	private Socket client2;
	
	private InputStream direction_input;
	private DataInputStream dataInput;
	
	private OutputStream snake_stream;
	private ObjectOutputStream objectOutput;
	
	private ArrayList<Tail> tail_list;
	private int ClientsConnected = 0;
	
	public Server(int PORT) throws IOException, InterruptedException {
		//Initializing Server and client connections
		ServerSocket server = new ServerSocket(PORT);
		System.out.println("[Server has started]");
		Socket client = server.accept();
		//Socket client = server.accept();
		System.out.println("[Client has Connected]:"+ get_ip(client));
		//Initializing Output and Input Streams to communicate with clients
		InputStream direction_input = client.getInputStream();
		DataInputStream dataInput = new DataInputStream(direction_input);
		
		OutputStream snake_stream = client.getOutputStream();
		ObjectOutputStream objectOutput = new ObjectOutputStream(snake_stream);
		
		run(client, dataInput, objectOutput);
		
		objectOutput.close();
		client.close();
		server.close();
	}
	
	/*
	 * @method "run" is used to thread a while loop, which sends and receives
	 * KeyInput and a "Snake" object which contains two different boards for
	 * clients to visualize. It's needed to insure that all of the information
	 * is sent at the same time. 
	 * 
	 * objectOutput.reset() is used to reset the Object going through the "ObjectOutputStream", which
	 * in other case cannot change when received in "Client" class.
	 */
	public void run(Socket client, DataInputStream dataInput, ObjectOutputStream objectOutput) throws InterruptedException, IOException {
		Snake user = null;
		if (this.client == client)
			user = snake_board[0];
		else
			user = snake_board[1];
		try {
			while(client.isConnected()) {
				TimeUnit.SECONDS.sleep(2);
				char direction = dataInput.readChar();
				user.get_tail_list().get(0).set_direction(direction);
				user.move_tails();
				user.collision_check();
				user.eaten_apple();
				objectOutput.reset();
				objectOutput.writeObject(snake_board);
				System.out.println(user.get_tail_list().get(0).get_direction());
			}
		} catch (IOException e) {
			objectOutput.flush();
			objectOutput.close();
			client.close();
			System.err.println("Client Crashed :(");
			e.printStackTrace();
		}
	}
	
	public String get_ip(Socket client) {
		return client.getRemoteSocketAddress().toString().replace("/", "");
	}
	
	public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException {
		new Server(8080);
	}
}

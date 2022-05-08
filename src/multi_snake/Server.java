package multi_snake;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	
	private InputStream direction_input;
	private DataInputStream dataInput;
	
	private OutputStream snake_stream;
	private ObjectOutputStream objectOutput;
	
	private ArrayList<Tail> tail_list;
	private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	private ExecutorService USER_LIMIT = Executors.newFixedThreadPool(2); //Only two Client Threads can run when the server runs, preventing more unnecessary connections
	private int ClientsConnected = 0;
	
	public Server(int PORT) throws IOException, InterruptedException {
		//Initializing Server and client connections
		ServerSocket server = new ServerSocket(PORT);
		//Waiting for Both Clients to Connect
		while (ClientsConnected != 2) {
			System.out.println("[Server has started]");
			Socket client = server.accept();
			System.out.println("[Client #" + (ClientsConnected+1) + " has Connected]:"+ get_ip(client));
			clients.add(new ClientHandler(client, ClientsConnected));
			ClientsConnected++;
		}
		//Activate the Client Handlers
		for (ClientHandler element : clients) {
			USER_LIMIT.execute(element);
		}
		
		USER_LIMIT.shutdownNow();
		//Socket client = server.accept();
		//System.out.println("[Client has Connected]:"+ get_ip(client));
		//Initializing Output and Input Streams to communicate with clients
		/*
		InputStream direction_input = client.getInputStream();
		DataInputStream dataInput = new DataInputStream(direction_input);
		
		OutputStream snake_stream = client.getOutputStream();
		ObjectOutputStream objectOutput = new ObjectOutputStream(snake_stream);
		
		
		
		//run(client, dataInput, objectOutput);
		
		objectOutput.close();
		client.close();
		server.close();
		*/
		
		
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
		Snake user = snake_board[0];

		try {
			while(client.isConnected()) {
				TimeUnit.MILLISECONDS.sleep(150);
				char direction = dataInput.readChar();
				//Runs all of the logic right here
				if(user.get_tail_list().size() > 1) {
					switch(user.get_tail_list().get(0).get_direction()) {
					case 'w':
						if (direction == 's')
							break;
						user.get_tail_list().get(0).set_direction(direction);
						break;
					case 's':
						if (direction == 'w')
							break;
						user.get_tail_list().get(0).set_direction(direction);
						break;
					case 'd':
						if (direction == 'a')
							break;
						user.get_tail_list().get(0).set_direction(direction);
						break;
					case 'a':
						if (direction == 'd')
							break;
						user.get_tail_list().get(0).set_direction(direction);
						break;
					default:
						user.get_tail_list().get(0).set_direction(direction);
						break;
					}
				}
				else {
					user.get_tail_list().get(0).set_direction(direction);
				}
				// user.get_tail_list().get(0).set_direction(direction);
				user.move_tails();
				if(user.collision_check()) {
					System.err.println("I hit something :(");
					break;
				}
				user.eaten_apple();
				//Sends the snake boards to the client to render
				objectOutput.reset();
				objectOutput.writeObject(snake_board);
			}
		} catch (IOException e) {
			objectOutput.flush();
			objectOutput.close();
			client.close();
			System.err.println("Client Crashed :(");
			e.printStackTrace();
		}
	}
	
	public Snake[] get_snake_list() {
		return snake_board;
	}
	public String get_ip(Socket client) {
		return client.getRemoteSocketAddress().toString().replace("/", "");
	}
	public Snake[] get_board() {
		return snake_board;
	}
	public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException {
		new Server(25565);
	}
}

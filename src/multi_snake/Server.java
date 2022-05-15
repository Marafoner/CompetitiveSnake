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
	public boolean stop_server = false;
	private int PORT;
	private ServerSocket server;
	private ArrayList<Tail> tail_list;
	private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	private ExecutorService USER_LIMIT = Executors.newFixedThreadPool(2); //Only two Client Threads can run when the server runs, preventing more unnecessary connections
	private int ClientsConnected = 0;
	
	public Server(int PORT) throws IOException, InterruptedException {
		//Initializing Server and client connections
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(PORT);
		//Waiting for Both Clients to Connect
		while (ClientsConnected != 2) {
			System.out.println("[Server is waiting for the Connections] | Connection Count: " + ClientsConnected);
			Socket client = server.accept();
			System.out.println("[Client #" + (ClientsConnected+1) + " has Connected]:"+ get_ip(client));
			clients.add(new ClientHandler(client, ClientsConnected, clients));
			ClientsConnected++;
		}
		System.out.println("[Server has started]");
		//Activate the Client Handlers
		for (ClientHandler element : clients) {
			USER_LIMIT.execute(element);
		}
	}
	public String get_ip(Socket client) {
		return client.getRemoteSocketAddress().toString().replace("/", "");
	}
	public boolean get_stop_server() {
		return stop_server;
	}
}

package multi_snake;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class Client {
	/*
	 * Client class is managing all of the visualizations of the snake game
	 * through using "WindowManager" class. "WindowManager" class receives user's Key Inputs, which are 
	 * sent using "DataOutputStream" as char type to the server to modify the direction of the
	 * snake, which are sent back by the server.
	 */
	private Socket client;
	
	private InputStream snake_stream;
	private ObjectInputStream objectInput;
	
	private OutputStream direction;
	private DataOutputStream dataOutput;
	
	private WindowManager WM;
	private ArrayList<Tail> tail_list;
	
	public Client(String IP, int PORT) throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException {
		//Initializing client server connection
		WM = new WindowManager();
		Socket client = new Socket(IP, PORT);
		System.out.println("[Client has Connected]");
		InputStream snake_stream = client.getInputStream();
		ObjectInputStream objectInput = new ObjectInputStream(snake_stream);
		
		OutputStream direction = client.getOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(direction);
		try {
			while(client.isConnected()) {
				TimeUnit.SECONDS.sleep(2);
				dataOutput.writeChar(WM.get_pressed_key());
				Object object = objectInput.readObject();
				Snake[] snake_list = (Snake[]) object;
				System.out.println(snake_list[1].toString_tail(snake_list[1].get_snakeHead()));
			}
		} catch (IOException e) {
			dataOutput.flush();
			dataOutput.close();
			client.close();
			System.out.println("Server crashed :(");
			e.printStackTrace();
		}
		dataOutput.flush();
		dataOutput.close();
		client.close();
	}
	

	public static void main(String args[]) throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException {
		new Client("localhost", 8080);
	}

   
}

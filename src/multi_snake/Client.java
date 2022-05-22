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
	private boolean Client_Connected = false;
	private Socket client;
	private InputStream snake_stream;
	private ObjectInputStream objectInput;
	private OutputStream direction;
	private DataOutputStream dataOutput;
	private WindowManager WM;
	private Snake[] snake_list;
	
	public Client(String IP, int PORT, WindowManager WM) throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException {
		//Initializing client server connection
		//TimeUnit.SECONDS.sleep(6);
		//WM = new WindowManager(0);
		this.WM = WM;
		TimeUnit.MILLISECONDS.sleep(700);
		Socket client = new Socket(IP, PORT);
		client.setTcpNoDelay(true);
		Client_Connected = client.isConnected();
		System.out.println("[Client has Connected]");
		InputStream snake_stream = client.getInputStream();
		ObjectInputStream objectInput = new ObjectInputStream(snake_stream);
		OutputStream direction = client.getOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(direction);
		
		try {
		
			while(client.isConnected()) {
				TimeUnit.MILLISECONDS.sleep(100);
				//send the input to the server
				dataOutput.writeChar(WM.get_pressed_key());
				Object object = objectInput.readObject();
				snake_list = (Snake[]) object;
				WM.set_BOARDSIZE(snake_list[0].get_BOARDSIZE()+1);
				//Sends new snake boards to the GUI to render
				WM.set_snake_list(snake_list);
			}
		} catch (IOException e) {
			dataOutput.flush();
			dataOutput.close();
			client.close();
			System.err.println("Server crashed :(");
			e.printStackTrace();
		} finally {
			dataOutput.flush();
			dataOutput.close();
			client.close();
		}
	}
	public boolean get_client_connected() {
		return Client_Connected;
	}
	public static void main(String args[]) throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException {
		new WindowManager(10);
		//new Client("localhost", 25565, new WindowManager(0));
	}
	
}

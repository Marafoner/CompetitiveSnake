package multi_snake;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable{
	
	private boolean StopThread = false;
	private Socket client;
	private InputStream direction_input;
	private DataInputStream dataInput;
	private OutputStream snake_stream;
	private ObjectOutputStream objectOutput;
	private Snake user_board = new Snake(0, 9, 1);
	private ArrayList<ClientHandler> clients;
	private Snake[] snake_board = {user_board, user_board};
	private int id; //ID of the board we send input for
	
	public ClientHandler(Socket client, int id, ArrayList<ClientHandler> clients) throws IOException {
		this.client = client;
		this.id = id;
		this.clients = clients;
		//Setting Up Input OutPut streams the moment the ClientHandlers are Declared
		this.direction_input = client.getInputStream();
		this.dataInput = new DataInputStream(direction_input);
		this.snake_stream = client.getOutputStream();
		this.objectOutput = new ObjectOutputStream(snake_stream);
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
	
	public void run(){
		// run stuff here i guess
		Snake user = user_board;
		try {
			while(client.isConnected() && !StopThread) {
				TimeUnit.MILLISECONDS.sleep(100);
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
				user.eaten_apple();
				if(!user.get_defeat())
					user.move_tails();
				if(user.collision_check()) {
					user.set_defeat(true);
				}
				//Sends the snake boards to the client to render
				snake_board[id] = user;
				for (ClientHandler element : clients) {
					element.set_boards(snake_board[id], id);
				}
				objectOutput.reset();
				objectOutput.writeObject(snake_board);
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Client Crashed :(");
			e.printStackTrace();
		} finally {
			try {
				objectOutput.flush();
				objectOutput.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Snake[] get_boards() {
		return snake_board;
	}
	public Snake get_board() {
		return user_board;
	}
	public int get_id() {
		return id;
	}
	public void set_boards(Snake board, int id) {
		snake_board[id] = board;
	}
	public boolean get_StopThread() {
		return StopThread;
	}
	
}

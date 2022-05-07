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
	private int id = 0;
	public ClientHandler(Socket client, int id) throws IOException {
		this.client = client;
		this.id = id;
		direction_input = client.getInputStream();
		dataInput = new DataInputStream(direction_input);
		snake_stream = client.getOutputStream();
		objectOutput = new ObjectOutputStream(snake_stream);
	}
	@Override
	public void run(){
		// run stuff here i guess
		
		try {
			while(client.isConnected()) {
				TimeUnit.MILLISECONDS.sleep(250);
				char direction = dataInput.readChar();
				//Runs all of the logic right here
				if(board[id].get_tail_list().size() > 1) {
					switch(board[id].get_tail_list().get(0).get_direction()) {
					case 'w':
						if (direction == 's')
							break;
						board[id].get_tail_list().get(0).set_direction(direction);
						break;
					case 's':
						if (direction == 'w')
							break;
						board[id].get_tail_list().get(0).set_direction(direction);
						break;
					case 'd':
						if (direction == 'a')
							break;
						board[id].get_tail_list().get(0).set_direction(direction);
						break;
					case 'a':
						if (direction == 'd')
							break;
						board[id].get_tail_list().get(0).set_direction(direction);
						break;
					default:
						board[id].get_tail_list().get(0).set_direction(direction);
						break;
					}
				}
				else {
					board[id].get_tail_list().get(0).set_direction(direction);
				}
				// user.get_tail_list().get(0).set_direction(direction);
				board[id].move_tails();
				if(board[id].collision_check()) {
					System.err.println("I hit something :(");
					break;
				}
				board[id].eaten_apple();
				//Sends the snake boards to the client to render
				objectOutput.reset();
				objectOutput.writeObject(board);
			}
		} catch (IOException e) {
			System.err.println("Client Crashed :(");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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

}

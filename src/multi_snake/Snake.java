package multi_snake;
import java.util.ArrayList;
import java.io.Serializable;

public class Snake implements Serializable{
	/*
	 * @param serialVersionUID is needed in order to serialize a class in order to
	 * create an Snake object and send it and receive it successfully.
	 * 
	 * Snake class manages the game logic and allows to host two different boards for
	 * two different clients.
	 */
	private static final long serialVersionUID = 1L;

	private int clientID;
	private ArrayList<Tail> tail_list = new ArrayList<Tail>();
	Apple apple;
	
	Snake(int clientID) {
		tail_list.add(new Tail(3,3, 'd'));
		apple = new Apple(tail_list, 9);
		this.clientID = clientID;
	}
	
	public Tail create_tail(int x, int y, char direction) {
		return new Tail(x, y, direction);
	}
	public ArrayList<Tail> get_tail_list() {
		return tail_list;
	}

	public int get_ClientID() {
		return clientID;
	}


}

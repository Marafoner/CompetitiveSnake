package multi_snake;
import java.util.ArrayList;
import java.io.Serializable;

public class Snake implements Serializable{
	/*
	 * @param serialVersionUID is needed in order to serialize a class in order to
	 * create a Snake object and send it and receive it successfully.
	 * 
	 * "Snake" class manages the game logic and allows to host two different boards for
	 * two different clients using Snake[].
	 */
	private static final long serialVersionUID = 1L;

	private int clientID;
	private int SCORE;
	private int MAX_SCORE;
	private int BOARD_SIZE;
	private ArrayList<Tail> tail_list = new ArrayList<Tail>();
	Apple apple;
	
	Snake(int clientID, int BOARD_SIZE) {
		this.BOARD_SIZE = BOARD_SIZE;
		this.clientID = clientID;
		tail_list.add(new Tail(3,3, 'd'));
		this.MAX_SCORE = BOARD_SIZE-tail_list.size()-1;
		apple = new Apple(tail_list, 9);
	}
	
	public Tail create_tail() {
		Tail new_tail = new Tail(-1, -1, ' ');
		switch (get_lastTail().get_direction()) {
		case 'w':
			new_tail.set_direction('w');
			new_tail.set_x(get_lastTail().get_x());
			new_tail.set_y(get_lastTail().get_y()-1);
			break;
		case 's':
			new_tail.set_direction('s');
			new_tail.set_x(get_lastTail().get_x());
			new_tail.set_y(get_lastTail().get_y()+1);
			break;
		case 'a':
			new_tail.set_direction('a');
			new_tail.set_x(get_lastTail().get_x()+1);
			new_tail.set_y(get_lastTail().get_y());
			break;
		case 'd':
			new_tail.set_direction('d');
			new_tail.set_x(get_lastTail().get_x()-1);
			new_tail.set_y(get_lastTail().get_y());
			break;
		}
		return new_tail;
	}
	public void add_tail(Tail tail) {
		tail_list.add(tail);
	}

	public void eaten_apple() {
		if (get_snakeHead().get_x() == apple.get_x() && get_snakeHead().get_y() == apple.get_y()) {
			add_tail(create_tail());
			apple.spawn_apple(tail_list, BOARD_SIZE);
			SCORE++;
		}
			
	}
	
	public void move_tails() {
		for (int i = 0; i < tail_list.size(); i++) {
			Tail current_tail = tail_list.get(i);
			switch (current_tail.get_direction()) {
			case 'w':
				if (current_tail.get_direction() == 's')
					break;
				current_tail.set_y(current_tail.get_y()+1);
				break;
			case 's':
				if (current_tail.get_direction() == 'w')
					break;
				current_tail.set_y(current_tail.get_y()-1);
				break;
			case 'a':
				if (current_tail.get_direction() == 'd')
					break;
				current_tail.set_x(current_tail.get_x()-1);
				break;
			case 'd':
				if (current_tail.get_direction() == 'a')
					break;
				current_tail.set_x(current_tail.get_x()+1);
				break;
			}
		}
	}
	
	public boolean collision_check() {
		//Left and Bottom Wall Collision
		if (get_snakeHead().get_x() < 0 || get_snakeHead().get_y() < 0)
			return true;
		//Right and Upper Wall Collision
		if (get_snakeHead().get_x() > BOARD_SIZE || get_snakeHead().get_y() > BOARD_SIZE)
			return true;
		//Tail Collision
		for (int i = 1; i < tail_list.size(); i++) {
			if (get_snakeHead().get_x() == tail_list.get(i).get_x() && get_snakeHead().get_y() == tail_list.get(i).get_y())
				return true;
		}
		return false;
	}
	
	public ArrayList<Tail> get_tail_list() {
		return tail_list;
	}
	public Tail get_snakeHead() {
		return tail_list.get(0);
	}
	public Tail get_lastTail() {
		return tail_list.get(tail_list.size()-1);
	}
	public int get_ClientID() {
		return clientID;
	}
	public int get_score() {
		return SCORE;
	}
	public int get_MAXSCORE() {
		return MAX_SCORE;
	}
	public int get_tail_SIZE() {
		return tail_list.size();
	}
	public String toString_tail(Tail tail) {
		return "(" + tail.get_x() + "," + tail.get_y() + ")";
	}


}

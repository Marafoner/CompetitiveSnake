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
	private int SNAKE_SIZE = 0;
	private int BOARD_SIZE;
	private int APPLE_COUNT;
	private boolean defeat = false;
	private ArrayList<Tail> tail_list = new ArrayList<Tail>();
	private ArrayList<Apple> apple_list = new ArrayList<Apple>();
	
	Snake(int clientID, int BOARD_SIZE, int APPLE_COUNT) {
		//Checking the legitimacy of the inputed values
		if (BOARD_SIZE <= 4) {
			System.err.println("Board size is too small! | Setting it to DEFAULT value of 7");
			//Default Value of the snake Size
			this.BOARD_SIZE = 7;
		}
		else
			this.BOARD_SIZE = BOARD_SIZE;
		if (APPLE_COUNT <= 0)
			this.set_APPLE_COUNT(1);
		else
			this.set_APPLE_COUNT(APPLE_COUNT);
		this.MAX_SCORE = BOARD_SIZE-SNAKE_SIZE-APPLE_COUNT;
		this.clientID = clientID;
		//populating
		add_tail(new Tail(BOARD_SIZE-6, BOARD_SIZE-4, 'd'));
		for (int i = 0; i < SNAKE_SIZE-1; i++) {
				add_tail((create_tail()));
		}
		for (int i = 0; i < APPLE_COUNT; i++) {
			apple_list.add(new Apple(tail_list, apple_list, BOARD_SIZE, i));
		}
	}
	
	public Tail create_tail() {
		switch (get_lastTail().get_direction()) {
		case 'w':
			return new Tail(get_lastTail().get_x(), get_lastTail().get_y()+1, get_lastTail().get_direction());
		case 's':
			return new Tail(get_lastTail().get_x(), get_lastTail().get_y()-1, get_lastTail().get_direction());
		case 'a':
			return new Tail(get_lastTail().get_x()+1, get_lastTail().get_y(), get_lastTail().get_direction());
		case 'd':
			return new Tail(get_lastTail().get_x()-1, get_lastTail().get_y(), get_lastTail().get_direction());
		default:
			return new Tail(get_lastTail().get_x(), get_lastTail().get_y(), get_lastTail().get_direction());
		}
	}
	public void add_tail(Tail tail) {
		tail_list.add(tail);
	}
	
	/*
	 * There's Definitely a better way to move the snake through the snake list
	 * I will do it later due to I need it to work to make sure GUI renders everything properly
	 * As well as I need it to just barely function right now, which it does.
	 */
	
	public void move_tails() {
		for (int i = 0; i < tail_list.size(); i++) {
			switch(tail_list.get(i).get_direction()) {
			case 'w':
				tail_list.get(i).set_y(tail_list.get(i).get_y()-1);
				break;
			case 's':
				tail_list.get(i).set_y(tail_list.get(i).get_y()+1);
				break;
			case 'd':
				tail_list.get(i).set_x(tail_list.get(i).get_x()+1);
				break;
			case 'a':
				tail_list.get(i).set_x(tail_list.get(i).get_x()-1);
				break;
			default:
				break;
			}
		}
		for (int i = tail_list.size()-1; i > 0; i--) {
			if (tail_list.get(i).get_direction() != tail_list.get(i-1).get_direction()) {
				tail_list.get(i).set_direction(tail_list.get(i-1).get_direction());
			}
		}
	}
	
	/*
	 * For Competitive GameMode where if a user eats an apple
	 * then their opponent's snake will increase in size and vice versa.
	 */
	
	public void increase_size(boolean opponent_apple_eaten) {
		if(opponent_apple_eaten) 
			add_tail(create_tail());
	}
	
	public boolean eaten_apple() {
		for (Apple element : apple_list) {
			Apple current_apple = element;
			if (get_snakeHead().get_x() == current_apple.get_x() && get_snakeHead().get_y() == current_apple.get_y()) {
				add_tail(create_tail());
				current_apple.spawn_apple(tail_list, apple_list, BOARD_SIZE, current_apple.get_index());
				SCORE++;
				return true;
			}
			
		}
		return false;
			
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
			if (get_snakeHead().get_x() == tail_list.get(i).get_x() && get_snakeHead().get_y() == tail_list.get(i).get_y()) {
				//System.err.println("I collided with tail somehow? :(");
				return true;
			}
		}
		return false;
	}

	public ArrayList<Tail> get_tail_list() {
		return tail_list;
	}
	public ArrayList<Apple> get_apple_list() {
		return apple_list;
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
	public int get_SCORE() {
		return SCORE;
	}
	public int get_BOARDSIZE() {
		return BOARD_SIZE;
	}
	public int get_APPLE_COUNT() {
		return APPLE_COUNT;
	}
	public void set_APPLE_COUNT(int APPLE_COUNT) {
		this.APPLE_COUNT = APPLE_COUNT;
	}

	public int get_MAXSCORE() {
		return MAX_SCORE;
	}
	public String toString_tail() {
		return "(" + get_snakeHead().get_x() + "," + get_snakeHead().get_y() + ")" + "Score: " + get_SCORE() + "\n Apple: (" + get_apple_list().get(0).get_x() + get_apple_list().get(0).get_y() + ")";
	}
}
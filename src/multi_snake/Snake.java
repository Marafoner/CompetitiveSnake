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
	private int SNAKE_SIZE = 1;
	private int BOARD_SIZE;
	private int APPLE_COUNT;
	private ArrayList<Tail> tail_list = new ArrayList<Tail>();
	private ArrayList<Apple> apple_list = new ArrayList<Apple>();
	
	Snake(int clientID, int BOARD_SIZE, int APPLE_COUNT) {
		//Checking the legitimicy of the inputed values
		if (BOARD_SIZE < 4) {
			System.err.println("Board size is too small! | Setting it to DEFAULT value of 7");
			//Default Value of the snake Size
			this.BOARD_SIZE = 7;
		}
		else
			this.BOARD_SIZE = BOARD_SIZE;
		if (APPLE_COUNT <= 0)
			this.APPLE_COUNT = 1;
		else
			this.APPLE_COUNT = APPLE_COUNT;
		this.MAX_SCORE = BOARD_SIZE-SNAKE_SIZE-APPLE_COUNT;
		this.clientID = clientID;
		//populating
		for (int i = 0; i < SNAKE_SIZE; i++) {
			if (i == 0)
				tail_list.add(new Tail(BOARD_SIZE-3, BOARD_SIZE-3, 'd')
			if (i > 0)
				tail_list.add(add_tail(create_tail));
		}
		for (int i = 0; i < APPLE_COUNT; i++) {
			apple_list.add(new Apple());
		}
	}
	
	public Tail create_tail() {
		Tail new_tail = new Tail(-1, -1, ' '); // <-- might not need this
		switch (get_lastTail().get_direction()) {
		case 'w':
			/*
			new_tail.set_direction('w');
			new_tail.set_x(get_lastTail().get_x());
			new_tail.set_y(get_lastTail().get_y()-1);
			*/
			return new Tail(get_lastTail().get_x(), get_lastTail().get_y, get_lastTail.get_direction()); //?? can i do this ??
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

	public void move_tails() {
		int last_tail;
		for (int i = 0; i < tail_list.size(); i++) {
			//making it easier to read
			Tail current_tail = tail_list.get(i);
			Tail last_checked_tail = tail_list.get(last_tail);
			switch (current_tail.get_direction()) {
			case 'w':
				current_tail.set_y(current_tail.get_y()+1);
				if (i==0) { // <- make it ran once better
					if (collision_check()) {
						//make the game stop
						System.err.println("oof");
						break;
					}
					eaten_apple();
				}
				if (current_tail.get_direction() != last_checked_tail.get_direction())
					current_tail.set_direction(last_checked_tail.get_direction());
					
				break;
			case 's':
				current_tail.set_y(current_tail.get_y()-1);
				if (i==0) {
					if (collision_check()) {
						//make the game stop
						System.err.println("oof");
						break;
					}
					eaten_apple();
				}
				if (current_tail.get_direction() != last_checked_tail.get_direction())
					current_tail.set_direction(last_checked_tail.get_direction());

				break;
			case 'a':
				current_tail.set_x(current_tail.get_x()-1);
				if (i==0) {
					if (collision_check()) {
						//make the game stop
						System.err.println("oof");
						break;
					}
					eaten_apple();
				}
				if (current_tail.get_direction() != last_checked_tail.get_direction())
					current_tail.set_direction(last_checked_tail.get_direction());

				break;
			case 'd':
				current_tail.set_x(current_tail.get_x()+1);
				if (i==0) { 
					if (collision_check()) {
						//make the game stop
						System.err.println("oof");
						break;
					}
					eaten_apple();
				}
				if (current_tail.get_direction() != last_checked_tail.get_direction())
					current_tail.set_direction(last_checked_tail.get_direction());

				break;
			}
			last_tail = i;
		}
		/* old bad code for reference
			for (int i = tail_list.size(); i > 0; i++) {
				if (tail_list.get(i).get_direction() != tail_list.get(i-1).get_direction())
					tail_list.get(i).set_direction()(Tail_List.get(i-1).get_direction());
			}
		*/
	}
					      
	public void eaten_apple() {
		for (int i = 0; i < apple_list.size(); i++) {
			Apple current_apple = apple_list.get(i);
			if (get_snakeHead().get_x() == current_apple.get_x() && get_snakeHead().get_y() == current_apple.get_y()) {
				add_tail(create_tail());
				SCORE++;
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
	public int get_score() {
		return SCORE;
	}
	public int get_MAXSCORE() {
		return MAX_SCORE;
	}
	public String toString_tail(Tail tail) {
		return "(" + tail.get_x() + "," + tail.get_y() + ")";
	}


}

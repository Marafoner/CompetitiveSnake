package multi_snake;

import java.io.Serializable;
import java.util.ArrayList;

public class Apple implements Serializable{
	
	/*
	 * @param serialVersionUID is needed in order to serialize a class in order to
	 * create an Apple object and send it and receive it successfully.
	 * 
	 * Apples class just manages two different coordinates and spawns an apple for
	 * the snake to eat meanwhile making sure it doesn't spawn inside of the snake
	 * through iterating across the "tail_list" and checking Tail Object coordinates
	 * using getters of the Tail class.
	 */
	private static final long serialVersionUID = 2L;
	private int x, y;
	
	Apple(ArrayList<Tail> tail_list, int board_size) {
		spawn_apple(tail_list, board_size);
	}
	public int get_x() {
		return x;
	}
	public int get_y() {
		return y;
	}
	
	public void spawn_apple(ArrayList<Tail> tail_list, int board_size) {
		x = (int) ((Math.random() * (board_size)));
		y = (int) ((Math.random() * (board_size)));
		for (Tail element : tail_list) {
			if (x == element.get_x() && y == element.get_y())
				spawn_apple(tail_list, board_size);
		}
	}
	
}

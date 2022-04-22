package multi_snake;

import java.io.Serializable;

public class Tail implements Serializable{
	/*
	 * @param serialVersionUID is needed in order to serialize a class to
	 * create a Tail object and send it and receive it successfully in any form.
	 * 
	 * Tail class manages two x,y coordinates of the snake tail. By creating an
	 * ArrayList<Tail> we can manage several tails separately from each other.
	 */
	private static final long serialVersionUID = 1L;
	private int x, y;
	private char direction;
	
	Tail(int x, int y, char direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public int get_x() {
		return x;
	}
	public int get_y() {
		return y;
	}
	public void set_x(int x) {
		this.x = x;
	}
	public void set_y(int y) {
		this.y = y;
	}

	public char get_direction() {
		return direction;
	}
	public void set_direction(char direction) {
		this.direction = direction;
	}
	public String toString() {
		return "(" + get_x() + "," + get_y() + ")" + get_direction();
	}
}

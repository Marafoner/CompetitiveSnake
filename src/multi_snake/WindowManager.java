package multi_snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class WindowManager extends JFrame implements KeyListener{
	/*
	 * "WindowManager" class manages the GUI and key inputs.
	 * 
	 */
	private char pressed_key;
	JFrame frame = new JFrame("Snake");
	JPanel board = new JPanel();
	WindowManager() {
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}
	
	public void paint(Graphics g) {
		((Graphics2D) g).drawLine(0, 0, 300, 300);
	}
	
	public void set_pressed_key(char pressend_key) {
		this.pressed_key = pressend_key;
	}
	public char get_pressed_key() {
		return pressed_key;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		set_pressed_key(e.getKeyChar());
		System.out.println("The key Released was: " + e.getKeyChar());
	}
}

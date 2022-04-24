package multi_snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class WindowManager extends JFrame {
	/*
	 * "WindowManager" class manages the GUI of the snake game and key inputs.
	 * 
	 */
	private int screenWidth = 800;
	private int screenHeight = 700;
	private char pressed_key;
	private int BOARD_SIZE;
	private Snake[] snake_list;
	BufferedImage BI = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
	Graphics GI = BI.createGraphics();
	
	BufferedImage test;
	
	Timer graphics;
	WindowManager(int BOARD_SIZE) {
		this.BOARD_SIZE = BOARD_SIZE;
		addKeyListener(
				new KeyListener() {
					//Not sure if key pressed or keyrelesed is better yet.
					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						set_pressed_key(e.getKeyChar());
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						//set_pressed_key(e.getKeyChar());
						//System.out.println("The key Released was: " + e.getKeyChar());
					}
					
				});
		graphics = new Timer(30, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				repaint();
			}
			
		});
		setSize(screenWidth, screenHeight);
	    setLocation(600, 300);
	    setTitle("Snake");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    
	    graphics.start();
	}
	
	public void paint(Graphics g) {
		GI.setColor(Color.WHITE);
		GI.fillRect(0, 0, screenWidth, screenHeight);
		GI.setColor(Color.black);
		for(int i = 0; i < BOARD_SIZE; i++) 
			for(int j = 0; j < BOARD_SIZE; j++)
				GI.drawRect(i*50+50, j*50+50, 50, 50);
		
		if(snake_list != null) {
			GI.drawString("SCORE: " + snake_list[1].get_SCORE(), 500, 500);
			for (int i = 0; i < user_list().size(); i++) {
				if (i == 0) {
					GI.setColor(Color.blue);
					GI.fillRect(user_list().get(0).get_x()*50+50, user_list().get(0).get_y()*50+50, 50, 50);
				}
				else {
					GI.setColor(Color.red);
					GI.fillRect(user_list().get(i).get_x()*50+50, user_list().get(i).get_y()*50+50, 50, 50);
					GI.setColor(Color.black);
				}
			}
			for (int i = 0; i < user_apple_list().size(); i++) {
				GI.setColor(Color.green);
				GI.fillRect(user_apple_list().get(i).get_x()*50+50, user_apple_list().get(i).get_y()*50+50, 50, 50);
			}
		}
		g.drawImage(BI, 0, 0, null);
	}
	
	public void set_pressed_key(char pressend_key) {
		this.pressed_key = pressend_key;
	}
	public char get_pressed_key() {
		return pressed_key;
	}
	public ArrayList<Tail> user_list() {
		return snake_list[1].get_tail_list(); // <-- CHANGE IT TO 0 WHEN MAKE IT WORK
	}
	public ArrayList<Apple> user_apple_list() {
		return snake_list[1].get_apple_list();
	}
	public ArrayList<Tail> opponent_list() {
		return snake_list[0].get_tail_list(); // <--- CHANGE IT TO 1 WHEN MAKE SERVER WORK MULTI THREADING
	}
	public void set_snake_list(Snake[] snake_list) {
		this.snake_list = snake_list;
	}
}

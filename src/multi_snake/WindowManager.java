package multi_snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class WindowManager extends JFrame {
	
	/*
	 * "WindowManager" class manages the GUI of the snake game and key inputs.
	 * 
	 */
	private int mouseX, mouseY;
	private int screenWidth = 1200;
	private int screenHeight = 700;
	private char pressed_key;
	private int BOARD_SIZE;
	private Snake[] snake_list;
	//Flags for Buttons
	/*
	* "main" is the main menu flag for method "paint" to draw
	* "host" is the host menu flag for method "paint" to draw
	* "join" is the join menu flag for method "paint" to draw
	* "game" is the game flag for method "paint" to draw
	*/
	private boolean main, host, join, game = false;
	private Button HOST = new Button(400, 500, "HOST");
	private ArrayList<Button> button_list = new ArrayList<Button>();
	BufferedImage BI = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
	Graphics GI = BI.createGraphics();
	BufferedImage test;
	Timer graphics;
	
	WindowManager(int BOARD_SIZE) {
		game = true;
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
						if (e.getKeyChar() == 'l') {
							try {
								new Client("localhost", 25565);
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						//System.out.println("The key Released was: " + e.getKeyChar());
					}
					
				});
		addMouseListener( 
				new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
		
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (mouseX >= HOST.get_x() && mouseX <= HOST.get_Width()+HOST.get_x() && (mouseY >= HOST.get_y() && mouseY <= HOST.get_y()+HOST.get_Height())) {
					
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (mouseX >= HOST.get_x() && mouseX <= HOST.get_Width()+HOST.get_x() && (mouseY >= HOST.get_y() && mouseY <= HOST.get_y()+HOST.get_Height())) {
					
					System.err.print("clicked ");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseX = e.getX();
				mouseY = e.getY();
				if (mouseX >= HOST.get_x() && mouseX <= HOST.get_Width()+HOST.get_x() && (mouseY >= HOST.get_y() && mouseY <= HOST.get_y()+HOST.get_Height())) {
					HOST.set_y(mouseY-40);
					HOST.set_x(mouseX-40);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseX = e.getX();
				mouseY = e.getY();
			}
			
		});
		
		//Looking at the mouse
		
		graphics = new Timer(30, new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				repaint();
			}
			
		});
		setSize(screenWidth, screenHeight);
	    setLocation(300, 150);
	    setTitle("Snake");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setResizable(!false);
	    setVisible(true);
	    graphics.start();
	}
	
	public void paint(Graphics g) {
		GI.setColor(Color.WHITE);
		GI.fillRect(0, 0, screenWidth, screenHeight);
		GI.setColor(Color.black);
		GI.fillRect(mouseX, mouseY, 30, 30);
		GI.drawString("(" + mouseX + "," + mouseY + ")", 30, 50);
		//Waiting Menu for the Game to begin
		if (!main) {
			GI.setColor(Color.green);
			GI.fillRect(HOST.get_x(), HOST.get_y(), HOST.get_Width(), HOST.get_Height());
		}
		if(snake_list == null && game) {
			GI.drawString("Waiting for another Client to Connect...", screenWidth/2-100, screenHeight/2);
		}
		//Rendering Snakes
		else if (snake_list != null && game) {
			GI.drawString("SCORE: " + snake_list[0].get_SCORE(), 500, 40);
			GI.drawString("SCORE: " + snake_list[1].get_SCORE(), 1100, 40);
			//Drawing a Girded Board
			for(int i = 0; i < BOARD_SIZE; i++) 
				for(int j = 0; j < BOARD_SIZE; j++) {
					GI.drawRect(i*50+50, j*50+50, 50, 50);
					GI.drawRect(i*50+650, j*50+50, 50, 50);
			}
			//Rendering the entire User's Snake
			for (int i = 0; i < user_list().size(); i++) {
					GI.setColor(Color.gray);
					GI.fillRect(user_list().get(i).get_x()*50+50, user_list().get(i).get_y()*50+50, 50, 50);
					GI.setColor(Color.black);
			}
			//Rendering the entire Opponent's Snake
			for (int i = 0; i < opponent_list().size(); i++) {
				GI.setColor(Color.gray);
				GI.fillRect(opponent_list().get(i).get_x()*50+650, opponent_list().get(i).get_y()*50+50, 50, 50);
				GI.setColor(Color.black);
			}
			//Renders apples
			for (int i = 0; i < user_apple_list().size(); i++) {
				GI.setColor(Color.green);
				GI.fillRect(user_apple_list().get(i).get_x()*50+50, user_apple_list().get(i).get_y()*50+50, 50, 50);
			}
			for (int i = 0; i < opponent_apple_list().size(); i++) {
				GI.setColor(Color.red);
				GI.fillRect(opponent_apple_list().get(i).get_x()*50+650, opponent_apple_list().get(i).get_y()*50+50, 50, 50);
			}
			//Drawing Head separately 
			GI.setColor(Color.darkGray);
			GI.fillRect(user_list().get(0).get_x()*50+50, user_list().get(0).get_y()*50+50, 50, 50);
			GI.fillRect(opponent_list().get(0).get_x()*50+650, opponent_list().get(0).get_y()*50+50, 50, 50);
		}
		g.drawImage(BI, 0, 0, null);
	}
	private void draw_button() {
		for (Button button : button_list) {
			if (button.get_Active()) {
				GI.setColor(Color.red);
				GI.fillRect(button.get_x(), button.get_y(), button.get_Width(), button.get_Height());
				GI.setColor(Color.WHITE);
				GI.drawString(button.get_Text(), button.get_x()+button.get_Width()/2, button.get_y()+button.get_Height()/2);
			}
		}
	}
	public void set_pressed_key(char pressend_key) {
		this.pressed_key = pressend_key;
	}
	public void set_BOARDSIZE(int BOARD_SIZE) {
		this.BOARD_SIZE = BOARD_SIZE;
	}
	public char get_pressed_key() {
		return pressed_key;
	}
	public ArrayList<Tail> user_list() {
		return snake_list[0].get_tail_list(); // <-- CHANGE IT TO 0 WHEN MAKE IT WORK
	}
	public ArrayList<Apple> user_apple_list() {
		return snake_list[0].get_apple_list();
	}
	public ArrayList<Tail> opponent_list() {
		return snake_list[1].get_tail_list(); // <--- CHANGE IT TO 1 WHEN MAKE SERVER WORK MULTI THREADING
	}
	public ArrayList<Apple> opponent_apple_list() {
		return snake_list[1].get_apple_list();
	}
	public void set_snake_list(Snake[] snake_list) {
		this.snake_list = snake_list;
	}
}

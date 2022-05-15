package multi_snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.Timer;

public class WindowManager extends JFrame {
	
	/*
	 * "WindowManager" class manages the GUI of the snake game and key inputs.
	 */
	private static final long serialVersionUID = 1L;
	private boolean debug = false;
	private int mouseX, mouseY;
	private int screenWidth = 1200;
	private int screenHeight = 700;
	private char pressed_key;
	private int BOARD_SIZE;
	private Snake[] snake_list;
	//IPV4 for GUI to show for connection
	private ExecutorService SOCKET_LIMIT = Executors.newFixedThreadPool(2);
	private String ip = "";
	private String MYIPV4;
	/* need this to self reference when I start the Client, so it listens to the inputs
	 * of the "WindowManger" class
	 */
	private WindowManager WM;
	//Flags for Buttons
	/*
	* "main" is the main menu flag for method "paint" to draw
	* "host" is the host menu flag for method "paint" to draw
	* "join" is the join menu flag for method "paint" to draw
	* "game" is the game flag for method "paint" to draw
	*/
	private boolean main, host, game = false;
	private boolean join = true;
	private Button HOST = new Button(500, 500, "HOST");
	private Button JOIN = new Button(500, 600, "JOIN");
	private ArrayList<Button> button_list = new ArrayList<Button>();
	BufferedImage BI = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
	Graphics GI = BI.createGraphics();
	BufferedImage test;
	Timer graphics;
	
	WindowManager(int BOARD_SIZE) {
		this.WM = this;
		try {
			this.MYIPV4 = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		button_list.add(HOST);
		button_list.add(JOIN);
		this.BOARD_SIZE = BOARD_SIZE;
		addKeyListener(
				new KeyListener() {
				
					public void keyTyped(KeyEvent e) {
						
					}

					public void keyPressed(KeyEvent e) {
						set_pressed_key(e.getKeyChar());
						if (e.getKeyCode() == 61440) {
							debug = !debug;
							game = !game;
						}
						if(join) {
							if(e.getKeyCode() != 8) {
								ip += e.getKeyChar();
							}
							else {
								if(ip.length() > 0)
								ip = ip.substring(0, ip.length()-1);
							}
							
						}
					}

					public void keyReleased(KeyEvent e) {
					
					}
					
				});
		addMouseListener( 
				new MouseListener() {

			public void mouseClicked(MouseEvent e) {
		
			}

			public void mousePressed(MouseEvent e) {
				if (mouseX >= HOST.get_x() && mouseX <= HOST.get_Width()+HOST.get_x() && (mouseY >= HOST.get_y() && mouseY <= HOST.get_y()+HOST.get_Height())) {
					
				}
			}

			public void mouseReleased(MouseEvent e) {
				for (Button button : button_list) {
					if (mouseX >= button.get_x() && mouseX <= button.get_Width()+button.get_x() && (mouseY >= button.get_y() && mouseY <= button.get_y()+button.get_Height()) && button.get_Active()) {
						switch(button.get_Text()) {
						case "JOIN":
							join = !join;
							game = !game;
							button.switch_Active();
							HOST.switch_Active();
							start_client(ip);
							break;
						case "HOST":
							host = !host;
							join = !join;
							button.switch_Active();
							JOIN.switch_Active();
							start_server();
							start_client("localhost");
						}
					}
				}
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}
			
		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				if (mouseX >= HOST.get_x() && mouseX <= HOST.get_Width()+HOST.get_x() && (mouseY >= HOST.get_y() && mouseY <= HOST.get_y()+HOST.get_Height())) {
					HOST.set_y(mouseY-40);
					HOST.set_x(mouseX-40);
				}
			}

			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
			
		});
		
		
		graphics = new Timer(33, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
			
		});
		setSize(screenWidth, screenHeight);
		setLocationRelativeTo(null); //null centers it
	    setTitle("Snake");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setResizable(false);
	    setVisible(true);
	    graphics.start();
	}
	
	public void paint(Graphics g) {
		GI.setColor(Color.WHITE);
		GI.fillRect(0, 0, screenWidth, screenHeight);
		GI.setColor(Color.black);
		//Waiting Menu for the Game to begin
		if (debug) {
			GI.drawString(MYIPV4, 10, 40);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			GI.drawString("how did i make this work wth?", 10, 50);
		}
		if (!main) {
			draw_button();
		}
		if (join) {
			GI.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			GI.drawString("IP: " + ip, screenWidth/2-100, screenHeight/2+70);
		}
		if(host) {
			GI.setColor(Color.black);
			GI.drawString("Waiting for another Client to Connect...", screenWidth/2-100, screenHeight/2);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			GI.drawString(MYIPV4, screenWidth/2-100, screenHeight/2+70);
			game = true;
			
		}
		//Rendering Snakes
		if (snake_list != null && game) {
			host = false;
			join = false;
			GI.setColor(Color.black);
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
				GI.drawString(button.get_Text(), button.get_x()+button.get_Width()/2-20, button.get_y()+button.get_Height()/2);
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
		return snake_list[0].get_tail_list();
	}
	public ArrayList<Apple> user_apple_list() {
		return snake_list[0].get_apple_list();
	}
	public ArrayList<Tail> opponent_list() {
		return snake_list[1].get_tail_list();
	}
	public ArrayList<Apple> opponent_apple_list() {
		return snake_list[1].get_apple_list();
	}
	public void set_snake_list(Snake[] snake_list) {
		this.snake_list = snake_list;
	}
	/*
	 *  Creating "Runnable" threads of "Server" and "Client" class to
	 */
	private void start_server() {
		Runnable ServerThread = new Runnable () {
			  public void run() {
				  try {
					new Server(25565);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			  }
			};
			SOCKET_LIMIT.execute(ServerThread);
	}
	private void start_client(final String ip) {
		Runnable ClientThread = new Runnable () {
			  public void run() {
				  try {
					new Client(ip, 25565, WM);
				} catch (ClassNotFoundException | IOException | InterruptedException e) {
					e.printStackTrace();
				}
			  }
			};
			SOCKET_LIMIT.execute(ClientThread);
	}
}

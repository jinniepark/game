
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class JoustScreen extends KeyAdapter implements ActionListener {

	public static void main(String[] args) {
		System.out
				.println("A “game over” mechanism once someone gets enough points (5)");
		System.out
				.println("Collisions where neither bird is clearly above the other cause birds to bounce off each other instead of scoring points");
		System.out
				.println("Birds visually flaps their wings when you press the flap keys");
		new JoustScreen();
	}

	private JFrame window;
	private BufferedImage content;
	private Graphics2D paintbrush;
	private Timer gameTimer;

	private Bird birdg;
	private Bird birdr;
	private Rectangle wall;
	private Rectangle wall2;

	private int a;
	private int s;
	private int k;
	private int l;

	public JoustScreen() {
		this.window = new JFrame("Joust Clone");
		this.content = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		this.paintbrush = (Graphics2D) this.content.getGraphics();
		this.window.setContentPane(new JLabel(new ImageIcon(this.content)));
		this.window.pack();
		this.window.setVisible(true);
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.window.addKeyListener(this);

		this.birdg = new Bird("birdg", 0, 100.2, 150.1);
		this.birdr = new Bird("birdr", 3, 600.2, 150.1);
		this.wall = new Rectangle(200, 100, 15, 300);
		this.wall2 = new Rectangle(400, 400, 300, 15);

		this.gameTimer = new Timer(20, this);
		this.gameTimer.start();
	}

	public void keyPressed(KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.VK_A) {
			this.birdg.movebirdleft(-1.5, -6.2);
			a = 1;
		}
		if (event.getKeyCode() == KeyEvent.VK_S) {
			this.birdg.movebirdright(1.5, -6.2);
			s = 1;
		}
		if (event.getKeyCode() == KeyEvent.VK_K) {
			this.birdr.movebirdleft(-1.5, -6.2);
			k = 1;
		}
		if (event.getKeyCode() == KeyEvent.VK_L) {
			this.birdr.movebirdright(1.5, -6.2);
			l = 1;
			
		}

	}

	/**
	 * Java will call this every time the gameTimer ticks (50 times a second).
	 * If you want to stop the game, invoke this.gameTimer.stop() in this
	 * method.
	 */
	public void actionPerformed(ActionEvent event) {
		// DO NOT CHANGE the next four lines, and add nothing above them
		if (!this.window.isValid()) { // the "close window" button
			this.gameTimer.stop(); // should stop the timer
			return; // and stop doing anything else
		}
		// DO NOT CHANGE the previous four lines

		
		if (this.birdr.getX() < this.birdg.getX()) { // green bird is right of
														// red bird
			if (this.birdr.getdX() > 0 && l != 0) { // red bird is moving right
				this.birdr.setI(1);
			} else if (this.birdr.getdX() < 0 && k != 0) { // red bird is moving
															// left
				this.birdr.setI(2);
			} else if (this.birdg.getdX() > 0 && s != 0) { // green bird is
															// moving right
				this.birdg.setI(5);
			} else if (this.birdg.getdX() < 0 && a != 0) { // green bird is
															// moving left
				this.birdg.setI(4);
			} else {
				this.birdg.setI(3);
				this.birdr.setI(0);
			}

		}
		if (this.birdr.getX() > this.birdg.getX()) { // red bird is right of
														// green
			if (this.birdr.getdX() > 0 && l != 0) { // red bird is moving right
				this.birdr.setI(5);
			} else if (this.birdr.getdX() < 0 && k != 0) { // red bird is moving left
				this.birdr.setI(4);
			} else if (this.birdg.getdX() > 0 && s != 0) { // green bird is moving right
				this.birdg.setI(1);
			} else if (this.birdg.getdX() < 0 && a != 0) { // green bird is moving left
				this.birdg.setI(2);
			} else {
				this.birdg.setI(0);
				this.birdr.setI(3);
			}

		}

		this.birdg.setFx(0);
		this.birdg.setFy(0);
		this.birdr.setFx(0);
		this.birdr.setFy(0);
		this.birdg.applyGravity();
		this.birdr.applyGravity();
		this.birdg.applyDrag(.05, .02);
		this.birdr.applyDrag(.05, .02);
		this.birdr.bounceIfOutsideOf(this.window.getContentPane().getBounds(),
				.5);
		this.birdg.bounceIfOutsideOf(this.window.getContentPane().getBounds(),
				.5);
		birdr.hits(birdg, .5);
		birdr.getPoints();
		birdg.getPoints();

		this.birdr.bounceOffWalls(this.wall, .5);
		this.birdg.bounceOffWalls(this.wall2, .5);

		this.birdr.bounceOffWalls(this.wall2, .5);
		this.birdg.bounceOffWalls(this.wall, .5);

		a = 0;
		s = 0;
		k = 0;
		l = 0;
		// DO NOT CHANGE the next line; it must be last in this method
		this.refreshScreen(); // redraws the screen after things move
		// DO NOT CHANGE the above line; it must be last in this method
	}

	/**
	 * Re-draws the screen. You should erase the old image and draw a new one,
	 * but you should not change anything in this method (use actionPerformed
	 * instead if you need something to change).
	 */
	private void refreshScreen() {
		this.paintbrush.setColor(new Color(150, 210, 255)); // pale blue
		this.paintbrush.fillRect(0, 0, this.content.getWidth(),
				this.content.getHeight()); // erases the previous frame

		// TODO: replace the following example code with code that does
		// the right thing (i.e., draw the birds, walls, and score)

		// example bird drawing; replace with something sensible instead of
		// making a new bird every frame

		this.birdr.draw(this.paintbrush);
		this.birdg.draw(this.paintbrush);

		this.paintbrush.setColor(Color.BLACK);
		this.paintbrush.fill(this.wall);

		this.paintbrush.setColor(Color.BLACK);
		this.paintbrush.fill(this.wall2);

		// example text drawing, for scores and/or other messages
		Font f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		this.paintbrush.setFont(f);
		this.paintbrush.setColor(new Color(127, 0, 0)); // dark red

		this.paintbrush.drawString("" + birdr.getPoints(), 760, 30);
		this.paintbrush.setColor(new Color(0, 127, 0)); // dark green

		this.paintbrush.drawString("" + birdg.getPoints(), 30, 30);

		String msg = "";
		if (birdr.getPoints() == 5) {
			msg += "Game Over! Red player wins!";
			gameTimer.stop();
		}
		if (birdg.getPoints() == 5) {
			msg += "Game Over! Green player wins!";
			gameTimer.stop();
		}

		f = new Font(Font.SANS_SERIF, Font.BOLD, 30);
		Rectangle2D r = f.getStringBounds(msg,
				this.paintbrush.getFontRenderContext());
		this.paintbrush.setFont(f);
		this.paintbrush.setColor(Color.BLUE);
		this.paintbrush.drawString(msg, 200, 300);

		this.window.repaint();
		// DO NOT CHANGE the above line; it must be last in this method
	}

}



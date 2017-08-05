import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Bird {

	private BufferedImage[] imgs;

	private double x; // starting position
	private double y; // starting position
	private int i;
	private int points;

	private double dx;
	private double dy;
	private double gravity;
	private double fx;
	private double fy;
	private double mass;
	private CollisionBox box;
	private CollisionBox obstacleBox;

	public Bird(String basename, int i, double x, double y) {
		this.imgs = new BufferedImage[6];
		try {
			this.imgs[0] = ImageIO.read(new File(basename + ".png"));
			this.imgs[1] = ImageIO.read(new File(basename + "f.png"));
			this.imgs[2] = ImageIO.read(new File(basename + "b.png"));
			this.imgs[3] = Bird.makeFlipped(this.imgs[0]);
			this.imgs[4] = Bird.makeFlipped(this.imgs[1]);
			this.imgs[5] = Bird.makeFlipped(this.imgs[2]);

			this.x = x;
			this.y = y;
			this.i = i;

			this.dy = 0;
			this.gravity = 1.1;
			this.mass = .5;
			this.dx = 0;
			this.dy = 0;
			this.points = 0;
			

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public double getX() {
		return this.x;
	}
	public double getdX() {
		return this.dx;
	}
	public double getdY() {
		return this.dy;
	}

	private static BufferedImage makeFlipped(BufferedImage original) {
		AffineTransform af = AffineTransform.getScaleInstance(-1, 1);
		af.translate(-original.getWidth(), 0);
		BufferedImage ans = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		Graphics2D g = (Graphics2D) ans.getGraphics();
		g.drawImage(original, af, null);
		return ans;
	}

	public void draw(Graphics g) {

		
		g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
				(int) y - this.imgs[i].getHeight() + 19, null);
	}

	public void applyGravity() {

		dy = dy + gravity;
		y = y + dy;
		x = x + dx;

	}

	

	public void bounceIfOutsideOf(Rectangle r, double bounciness) {
		if (this.x - 25 < r.x) {
			this.x = r.x + 25;
			this.dx = Math.abs(this.dx * bounciness);

		}
		if (this.x + 25 > r.x + r.width) {
			this.x = r.x + r.width - 25;
			this.dx = -1 * Math.abs(this.dx * bounciness);
		}

		if (this.y - 40 < r.y) {
			this.y = r.y + 40;
			this.dy = Math.abs(this.dy * bounciness);
		}
		if (this.y > r.y + r.height) {
			this.y = r.y + r.height;
			this.dy = -1 * Math.abs(this.dy * bounciness);
		}

	}

	public double getFx() {
		return fx;
	}

	public void setFx(double fx) {
		this.fx = fx;
	}

	public double getFy() {
		return fy;
	}

	public void setFy(double fy) {
		this.fy = fy;
	}

	public void applyForce(double fx, double fy, double dt) {
		this.dx += dt * (fx / mass);
		this.dy += dt * (fy / mass);
	}

	public void applyDrag(double drag, double dt) {

		this.applyForce(-1 * this.dx * this.speed() * drag,
				-1 * this.dy * this.speed() * drag, dt);

	}

	public double speed() {
		double dx2 = Math.pow(this.dx, 2);
		double dy2 = Math.pow(this.dy, 2);

		return Math.sqrt(dx2 + dy2);
	}
	
	

	public void movebirdleft(double fx, double fy) {
		this.fx = fx;
		this.fy = fy;
		this.applyForce(fx, fy, 1);


	}

	public void movebirdright(double fx, double fy) {
		this.fx = fx;
		this.fy = fy;
		this.applyForce(fx, fy, 1);

	}

	public void bounceOffWalls(Rectangle r, double bounciness) {

		obstacleBox = new CollisionBox(r.x, r.y, r.width, r.height);
		box = new CollisionBox((int) this.x - 22, (int) this.y - 45, 45, 45);

		if (obstacleBox.collidesWith(box)) {

			Rectangle intersection = new Rectangle(
					obstacleBox.rect.intersection(box.rect));

			if (box.isSmallerOverlapVertical(obstacleBox)) {

				if (intersection.y == r.y) {
					this.y -= intersection.height;
					this.dy = -Math.abs(this.dy * bounciness);
				} else {
					this.y += intersection.height;
					this.dy = Math.abs(this.dy * bounciness);

				}

			}

			else {
				if (intersection.x == r.x) {
					this.x -= intersection.width;
					this.dx = -Math.abs(this.dx * bounciness);
				} else {
					this.x += intersection.width;
					this.dx = Math.abs(this.dx * bounciness);
				}

			}

		}

	}

	public void setI(int i) {
		this.i = i;

	}

	public void hits(Bird b, double bounciness) {

		box = new CollisionBox((int) this.x - 22, (int) this.y - 45, 45, 45);
		CollisionBox box2 = new CollisionBox((int) b.x - 22, (int) b.y - 45,
				45, 45);
		Random rand = new Random();
		int random = rand.nextInt(800);

		if (box.collidesWith(box2)) {
			Rectangle intersection = box.rect.intersection(box2.rect);

			if (box.isHigherThan(box2)) {

				b.x = random;
				b.y = 0;
				this.points++;
			} else if (box2.isHigherThan(box)) {
				this.x = random;
				this.y = 0;
				b.points++;

			} else {
				if (b.x > this.x) {
					b.x += intersection.width;
					this.x -=intersection.width;
							
							
					b.dx = Math.abs(this.dx * bounciness);
					this.dx = -Math.abs(this.dx * bounciness);

				} else if (b.x < this.x) {
					b.x -= intersection.width;
					this.x +=intersection.width;
					b.dx = -Math.abs(this.dx * bounciness);
					this.dx = Math.abs(this.dx * bounciness);

				}

			}
		}
	}

	public int getPoints() {
		return this.points;
	}
	


}

























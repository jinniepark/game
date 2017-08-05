import java.awt.Rectangle;

public class CollisionBox {

	Rectangle rect;

	public CollisionBox(Rectangle rect) {
		this.rect = new Rectangle(rect.x, rect.y, rect.width, rect.height);
	}

	public CollisionBox(int x, int y, int width, int height) {

		this.rect = new Rectangle(x, y, width, height);
	}

	public Rectangle getRectangle() {
		return this.rect;
	}

	public boolean collidesWith(CollisionBox other) {

		Rectangle newrect = new Rectangle(other.rect.x, other.rect.y,
				other.rect.width, other.rect.height);

		if (rect.intersects(newrect)) {
			return true;

		}
		return false;
	}

	public void moveTo(int x, int y) {
		this.rect = new Rectangle(x, y, rect.width, rect.height);

	}

	public void moveCenterTo(int x, int y) {
		this.rect = new Rectangle(x - (rect.width / 2), y - (rect.height / 2),
				rect.width, rect.height);
	}




public boolean isHigherThan(CollisionBox other) {
		if ((this.rect.y + this.rect.y + this.rect.height) / 2   +38 < (other.rect.y + other.rect.y + other.rect.height) / 2) {
			return true;

		} else {
			return false;
		}

	}


	public boolean isLeftOf(CollisionBox other) {
		if ((rect.getCenterX()) < (other.rect.getCenterX())) { // I got this
																// method from
																// http://docs.oracle.com/javase/7/docs/api/java/awt/geom/RectangularShape.html
			return true;
		}
		return false;
	}

	public boolean isSmallerOverlapVertical(CollisionBox other) {

		// I got this idea from a TA, Matt, on Piazza.

		if (verticalDifference(other) < horizontalDifference(other)) {
			return true;
		} else {
			return false;
		}
	}

	public int horizontalDifference(CollisionBox other) {

		int hd1 = Math.abs(this.rect.x - (other.rect.x + other.rect.width));
		int hd2 = Math.abs(other.rect.x - (this.rect.x + this.rect.width));

		if (hd1 < hd2) {
			return hd1;
		} else {
			return hd2;
		}

	}

	public int verticalDifference(CollisionBox other) {

		int vd1 = Math.abs(this.rect.y - (other.rect.y + other.rect.height));
		int vd2 = Math.abs(other.rect.y - (this.rect.y + this.rect.height));

		if (vd1 < vd2) {
			return vd1;
		} else {
			return vd2;
		}
	}

}
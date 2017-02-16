import java.awt.Point;

public class GameObject implements windowFrame {
	
	protected Point topLeft; // initial coordinates of top left corner of object
	protected Point bottomRight; // initial coordinates of bottom right corner of
									// object
	protected int bouncingSpeed = 10;
	protected int xSpeed;
	protected int ySpeed;

	/**
	 * This method is a static method. Does not have access to objects w/o parameters
	 * Nested if statements
	 * 1st If = If the ball's topLeft x positioning has surpassed the paddle's top left & top right corner, trigger
	 * 2nd If = If the ball's topLeft y positioning has surpassed the paddle's top left and bottom left corner, trigger
	 * 
	 * @return The two objects have collided.
	 * @author djmlee
	 */

	public static boolean collide(bawls object1, Player object2) {
		
		//Player One
		if(object2.left == true){
			if(object1.topLeft.x < object2.topLeft.getX() && object1.topLeft.x < object2.topLeft.x + object2.getWidth()){
				if(object1.topLeft.y > object2.topLeft.getY() && object1.topLeft.y < object2.topLeft.y + object2.getHeight()){
					System.out.println("PLAYER ONE BANG");
					return true;
				}//nested if
			}//nested if
		}//if
		
		//Player Two
		if(object2.left == false){
			if(object1.topLeft.x > (object2.topLeft.getX()-object2.getWidth()*2) && object1.topLeft.x > object2.topLeft.x - object2.getWidth()){
				if(object1.topLeft.y > object2.topLeft.getY() && object1.topLeft.y < object2.topLeft.y + object2.getHeight()){
					System.out.println("PLAYER TWO COLLISION");
					return true;
				}//nested if
			}//nested if
		}//if
		
		return false;
	}

	/*
	 * Method sees if P1 is passed in and sends the ball in the according direction
	 * If the ball has been hit twice by players, increase the speed & reset bouncecount
	 * @param Player
	 * @author djmlee
	 */
	public int bounced = 0; //Counter for amount of bounces
	public void collide_bounce(Player player) {
		if(player.left == true)
			this.setXSpeed(bouncingSpeed);
		else if(player.left == false)
			this.setXSpeed(0-bouncingSpeed);

		if(bounced > 3 && player.left == true)
			this.accelX(5);
		else if(bounced > 3  && player.left == false)
			this.accelX(-5);
		
		if(bounced == 6 && player.left == true){
			this.accelX(10);
			bounced = 0;
		}
		else if(bounced == 6 && player.left == true){
			this.accelX(10);
			bounced = 0;
		}
		
	}
	/**
	 * Initialize object with top and bottom corners and initial x- and y-speed
	 * 
	 */

	public GameObject(Point topLeft, Point bottomRight, int xSpeed, int ySpeed) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;

		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	/**
	 * Initialize object with top corner, height, width, and initial x- and
	 * y-speed
	 * 
	 */

	public GameObject(int initX, int initY, int height, int width, int xSpeed, int ySpeed) {
		this(new Point(initX, initY), new Point(initX + width, initY + height), xSpeed, ySpeed);
	}

	/**
	 * Initialize object with top corner at (0,0), with given height and width,
	 * and initial speed 5 in the x-dimension
	 * 
	 */

	public GameObject(int height, int width) {
		this(0, 0, height, width, 5, 0);
	}

	/**
	 * Set the GameObject's speed in the x dimension
	 * 
	 * @param xSpeed
	 *            The desired x-speed.
	 */

	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * Set the GameObject's speed in the x dimension
	 * 
	 * @param ySpeed
	 *            The desired y-speed.
	 */
	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	/**
	 * Increase the GameObject's speed in the x dimension
	 * 
	 * @param x
	 *            The amount of increase.
	 */

	public void accelX(int x) {
		xSpeed += x;
	}

	/**
	 * Increase the GameObject's speed in the y dimension
	 * 
	 * @param y
	 *            The amount of increase.
	 */

	public void accelY(int y) {
		ySpeed += y;
	}

	/**
	 *
	 * @return A Point representing the top-left corner of the GameObject's
	 *         bounding box
	 */
	public Point getTopLeft() {
		return topLeft;
	}

	/**
	 *
	 * @return A Point representing the bottom-right corner of the GameObject's
	 *         bounding box
	 */

	public Point getBottomRight() {
		return bottomRight;
	}

	/**
	 * 
	 * @return The height (in pixels) of the GameObject
	 */
	public int getHeight() {
		return bottomRight.y - topLeft.y;
	}

	/**
	 * 
	 * @return The width (in pixels) of the GameObject
	 */

	public int getWidth() {
		return bottomRight.x - topLeft.x;

	}

	/**
	 * Changes the location of the object for the next "animation frame"
	 * THIS method makes the object MOVE
	 */

	public void step() {//update
		
		topLeft.x += xSpeed;
		bottomRight.x += xSpeed;

		topLeft.y += ySpeed;
		bottomRight.y += ySpeed;
		
	}

	/**
	 * If the ball's topLeft/bottomRight x/y position is less/greater & equal to the edges of the playable area,
	 * set the x/ySpeed opposite to send the ball to the opposing side
	 * @author djmlee
	 */

	public void bounce() {
		if(this.topLeft.x <= x_border) //<=
			this.xSpeed = bouncingSpeed;
		if(this.topLeft.y <= y_topBorder) //50 for the white bar <=
			this.ySpeed = bouncingSpeed;
		if(this.bottomRight.x >= frame_width) // >=
			this.xSpeed = -bouncingSpeed;
		if(this.bottomRight.y >= frame_height-this.getHeight()) //>=		
			this.ySpeed = -bouncingSpeed;
	} //bounce
	
	/**
	 * scorekeeping()
	 * If the X Coordinate of the ball's topLeft Point is less than the Window's X edge.
	 * @return true
	 * @param: bawls, Player
	 * @author: djmlee
	 */
	public static boolean scorekeeping (bawls ball, Player player) {
		
		if(player.left == true){
			if(ball.topLeft.x <= x_border){
				//ball.xSpeed = 20;
				return true;
			}//nest if
			else 
				return false;
		}//if
		
		if(player.left == false){
			if(ball.bottomRight.x > frame_width){
				return true;
			}//nested if
			else 
				return false;
		}//if
		else
			return false;
	}
	
}//class

import java.awt.Point;

public class Player extends GameObject implements windowFrame{
	
	public boolean left; //Determines if it is P1 or P2

	public Player(int initX, int initY, int height, int width, int xSpeed, int ySpeed, boolean left){
		super(initX, initY, height, width, xSpeed, ySpeed);
		this.left = left;
	}
	
	public Player(Point topLeft, Point bottomRight, int xSpeed, int ySpeed) {
		super(topLeft, bottomRight, xSpeed, ySpeed);
	}
	
	@Override
	public void step() {
		topLeft.y += ySpeed; 
		bottomRight.y += ySpeed;
	}
	
	/**
	 * Method to make the sliders stop within the Playable Area
	 * @author djmlee
	 */
	public void stayFrame(){
		if(this.topLeft.y <= y_topBorder){
			this.setYSpeed(0);
		}
		if(this.bottomRight.y + (this.getHeight()/5)>= frame_height){ 
			this.setYSpeed(0);
		}
	}
	
}//paddle
 
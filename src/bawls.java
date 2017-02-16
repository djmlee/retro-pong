import java.awt.Point;

public class bawls extends GameObject {
	
	public bawls(int initX, int initY, int height, int width, int xSpeed, int ySpeed){
		super(initX, initY, height, width, xSpeed, ySpeed);
	}
	@Override
	public void step() {

		topLeft.x += xSpeed;
		bottomRight.x += xSpeed;

		topLeft.y += ySpeed;
		bottomRight.y += ySpeed;
		
	}
}

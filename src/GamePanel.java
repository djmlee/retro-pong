import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Animates a simple graphical game.
 *
 * Uses a Swing Timer to advance the animation; keeps track of, and renders, all
 * GameObjects. Handles all relevant game events.
 *
 * @author sdexter72
 *
 */

public class GamePanel extends JPanel implements windowFrame, ActionListener, KeyListener {

	static final int sliderSpeed = 20;
	static final int FRAME_RATE = 30;

	Timer t; // animation timer
	Timer score_t;
	Timer winStop;
	bawls ball; // bare-bones animation: just a simple object that slides across
				// the panel
	Player p1, p2;

	public int p1_score = 0;
	public int p2_score = 0;

	public boolean start = false;

	/**
	 * Sets up panel background, creates game Timer, creates initial GameObjects
	 *
	 * @author djmlee
	 */

	GamePanel() {
		setBackground(Color.BLACK);

		// !! not sure what's up with these Timers, or why you changed the
		// numerator--you're not going to get an animation with the given
		// FRAME_RATE if you're dividing it into 500!

		t = new Timer(500 / FRAME_RATE, this); // 1st Param = measured in
												// Milliseconds. After this
												// time, do this. Could be
												// actionlistener
		winStop = new Timer(0, this);
		// If first param is higher, gives lower program runtime/ball speed
		ball = new bawls(this.getWidth() / 2, frame_height / 2, 30, 30, 5, 5); // (X,
																			// Y,
																			// obj
																			// Height/Length,obj
																			// Height/Length,
																			// Trajectory(Y),Trajectory(X)

		// !! why are you doing all this frame_height frame_width stuff? why
		// don't just find out what the Panel's width and height are?
		
		p1 = new Player(80, frame_height / 2, 100, 25, 0, 0, true);
		//p2 = new Player(this.getWidth() - 90, frame_height / 2, 100, 25, 0, 0, false);
		p2 = new Player(this.getWidth()+900, frame_height / 2, 100, 25, 0, 0, false);

		this.addKeyListener(this); // Allows keyInput
		this.setFocusable(true); // This makes the keys only apply when game is
									// the "Active Window" ie, pressing W/S in
									// the bg

	}

	/**
	 * How to render one "frame" of the animation
	 */

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Giant Pong Log
		g.setFont(new Font("Helvetica", Font.BOLD, 250));
		g.setColor(Color.magenta);
		g.drawString("PONG", (((this.getWidth() / 2) / 2) / 2) + 25, frame_height / 2);

		g.setColor(Color.gray);
		g.setFont(new Font("Helvetica", Font.BOLD, 50));
		g.drawString("Space to Start", (this.getWidth() / 2) + 30, (frame_height / 2 + 40));

		g.setFont(new Font("Helvetica", Font.BOLD, 25));
		g.drawString("Dinsdale Lee", (this.getWidth() / 2) + 210, (frame_height / 2 + 60));

		if (start == true) {
			repaint(); // !! DUDE: NEVER call repaint() inside
						// paintComponent()!!!!
			super.paintComponent(g);
			g.setFont(new Font("Helvetica", Font.BOLD, 250));
			g.setColor(Color.darkGray);
			g.drawString("PONG", ((this.getWidth() / 2) / 2 / 2) + 25, this.getWidth() / 2);

			// Ball
			g.setColor(Color.blue);
			g.fillOval(ball.topLeft.x, ball.topLeft.y, ball.getWidth(), ball.getHeight());

			// Player paddles
			g.setColor(Color.white);
			g.fillRect(p1.topLeft.x, p1.topLeft.y, p1.getWidth(), p1.getHeight());
			g.fillRect(p2.topLeft.x, p2.topLeft.y, p2.getWidth(), p2.getHeight());

			// Pong Table Line, Top Border
			g.drawLine(this.getWidth() / 2, frame_height, this.getWidth() / 2, 0);
			g.fillRect(0, 0, this.getWidth(), 50);

			// Box
			g.setColor(Color.gray);
			g.fillRect(5, 5, this.getWidth() - 10, 40);

			// Player Scores
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", Font.BOLD, 20));
			g.drawString("Player 1: " + p1_score, 25, 35);
			g.drawString("Player 2: " + p2_score, this.getWidth() - 125, 35);

			g.setColor(Color.darkGray);
			g.drawString("First to 15", 50, frame_height - 100);

			// Big Score Logo
			g.setColor(Color.darkGray);
			g.setFont(new Font("Helvetica", Font.BOLD, 50));
			g.drawString("SCORES", (this.getWidth() / 2) - 88, 43);

			winning(g);
			
			//System.out.println(this.getHeight());
			//System.out.println(this.getWidth());


		} // if
	}

	/**
	 * This method just determines which frame to put up if a player wins P1/P2
	 * is reset to -500 as a simple way to prevent changing from P1 win screen
	 * to P2 win screen & vice versa
	 *
	 * @param Graphics
	 *            g
	 * @author djmlee
	 */
	public void winning(Graphics g) {
		if (p1_score >= 15) {
			repaint(); // !!
			super.paintComponent(g);
			g.setFont(new Font("Helvetica", Font.BOLD, 250));
			g.setColor(Color.magenta);
			g.drawString("PONG", (((this.getWidth() / 2) / 2) / 2) + 25, frame_height / 2);
			g.setFont(new Font("Helvetica", Font.BOLD, 50));
			g.setColor(Color.darkGray);
			g.drawString("Victory for Player One!", (this.getWidth() / 2 - 250), (frame_height / 2 + 50));
			g.drawString("Thanks for playing!", (this.getWidth() / 2 - 225), (frame_height / 2 + 100));
			p2_score = -500;
		} // if
		else if (p2_score >= 15) {
			repaint();
			super.paintComponent(g);
			g.setFont(new Font("Helvetica", Font.BOLD, 250));
			g.setColor(Color.magenta);
			g.drawString("PONG", (((frame_width / 2) / 2) / 2) + 25, frame_height / 2);

			g.setFont(new Font("Helvetica", Font.BOLD, 50));
			g.setColor(Color.darkGray);
			g.drawString("Victory for Player Two!", (this.getWidth() / 2 - 250), (frame_height / 2 + 50));
			g.drawString("Thanks for playing!", (this.getWidth() / 2 - 225), (frame_height / 2 + 100));
			p1_score = -500;

		}

	}

	/**
	 * Responds to all actionPerformed events. In bare-bones implementation,
	 * these are just 'ticks' from the timer.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		// if this is an event from the Timer, call the method that advances the
		// animation
		if (e.getSource() == t)
			tick();
	}

	/**
	 * Make sure all GameObjects are in the right place (do any need to be
	 * removed? do we need to create any new ones?), then redraw game This makes
	 * the objects enact/move in the frame.
	 *
	 * @author djmlee
	 */

	private void tick() {
		ball.step(); // we just have the ball right now, so this is easy
		ball.bounce();
		p1.step();
		p1.stayFrame(); // !! why can't the step() function for p1 and p2 invoke
						// the stayFrame() method itself--objects should manage
						// their OWN behavior
		p2.step();
		p2.stayFrame();
		specialBounce();
		winLose();
		repaint(); // ask to have the game redrawn (this will invoke
					// paintComponent() when the system says the time is right)
	}

	/**
	 * Start the Timer: this will cause events to be fired, and thus the
	 * animation to begin
	 */

	void go() {
		t.start();
	}

	/**
	 * Increments the Player Scores if a particular side wins a round
	 *
	 * @author djmlee
	 */
	void winLose() {

		// !! why is scorekeeping a static GameObject method? why can't each
		// Player object keep track of its score, and also tell if the ball has
		// got past them?

		if (GameObject.scorekeeping(ball, p1) == true) {
			ball = new bawls(frame_width / 2, frame_height / 2, 30, 30, 5, 10);
			p2_score++;
			System.out.println(p2_score);
		}
		if (GameObject.scorekeeping(ball, p2) == true) {
			ball = new bawls(frame_width / 2, frame_height / 2, 30, 30, -5, 10);
			p1_score++;
			System.out.println(p1_score);
		}
	}

	void specialBounce() {
		if (GameObject.collide(ball, p1) == true) {
			ball.collide_bounce(p1);
			ball.bounced++;
		}

		if (GameObject.collide(ball, p2) == true) {
			ball.collide_bounce(p2);
			ball.bounced++;
		}
	}

	/*
	 * While our game is running, in the background, JVM is listening for key
	 * input Computer detects it, packages all info about event eg. Press P key,
	 * registers it, packages all info about event (What key was pressed, if it
	 * was held, etc.) Then Looks to see if it has registered KeyListeners Any
	 * registered KeyListeners it will notify but calling one of these three
	 * methods It will tell GamePanel that a key was pressed (Cuz it implements
	 * ActionListener) These methods will be called automatically when key is
	 * pressed JPanel has a method addKeyListener()
	 */

	public void keyPressed(KeyEvent e) { // called when Key is pressed and Held
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			start = true;
		if (e.getKeyCode() == KeyEvent.VK_W) {
			p1.setYSpeed(-sliderSpeed);
			// p1.stayFrame();
		} // if
		if (e.getKeyCode() == KeyEvent.VK_S) {
			p1.setYSpeed(sliderSpeed);
			// p1.stayFrame();
		} // if

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			p2.setYSpeed(-sliderSpeed);
			// p2.stayFrame();
		} // if
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			p2.setYSpeed(sliderSpeed);
			// p2.stayFrame();
		} // if
	}// KeyPressed

	public void keyReleased(KeyEvent e) {
		// Called when key is released
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
			p2.setYSpeed(0);
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S)
			p1.setYSpeed(0);

	}// keyReleased

	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			start = true;
		if (e.getKeyCode() == KeyEvent.VK_W) {
			p1.setYSpeed(-sliderSpeed);
			p1.setYSpeed(0);
			// p1.stayFrame();
		} // if
		if (e.getKeyCode() == KeyEvent.VK_S) {
			p1.setYSpeed(sliderSpeed);
			p1.setYSpeed(0);
			// p1.stayFrame();
		} // if
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			p2.setYSpeed(-sliderSpeed);
			p2.setYSpeed(0);
			// p2.stayFrame();
		} // if
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			p2.setYSpeed(sliderSpeed);
			p2.setYSpeed(0);
			// p2.stayFrame();
		} // if
	}// keyTyped
}

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * Sets up the GUI and starts the game.
 * @author sdexter72
 *
 */
public class Main{

public static void main(String [] args){   	
        JFrame frame = new JFrame("DJM Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes the Game on Exit
        frame.setLayout(new BorderLayout()); //Adjust the Frame to BorderLayout setting
        
        // bare bones: just add a panel where the game objects are drawn

        GamePanel gamePanel = new GamePanel();
		frame.add(gamePanel, BorderLayout.CENTER); //GamePanel in the center of the BorderLayout
		frame.setSize(1024,768); //width,height
		frame.setResizable(false);
		frame.setVisible(true);
		
		// the game starts when the gamepanel animation begins
		gamePanel.go();
        }     
}

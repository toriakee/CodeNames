package GUI2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ClientServer.ChatBox;
import ClientServer.Client;

/**
 * Class SpyView is the JPanel to be placed on the final ClientApp2 frame.
 * 
 * SpyView represents the SpyMaster panel which differs from that of the
 * PlayerView panel, as they have visibility of the colors on the game board. 
 * 
 * @author Tori Keegan & Benjamin Fellows
 *
 */

public class SpyView extends JPanel implements Observer {

	public static final Color red = new Color(255, 51, 51);
	public static final Color blue = new Color(51, 153, 255);
	private String[] colors = new String[25];
	public String[] words;
	public String[] colorsStr = new String[25];
	public Color[] colorsCol;
	static GameButton[] spyButtonArr = new GameButton[25];
	private Client client;

	/*
	 * SpyView is the main panel
	 */

	public SpyView(ChatBox chatbox, Client client, String[] words, Color[] colorsCol) {
		this.words = words;
		this.colors = colors;
		this.client = client;

		this.setLayout(new BorderLayout(5, 5));
		this.setSize(2000, 1000);

		// create a new grid panel to add board and chat onto
		JPanel gridPane = new JPanel(new GridLayout(1, 2));
		JPanel instructionPane = new JPanel(new BorderLayout(10, 10));
		JPanel chatPane = new JPanel(new GridLayout(2, 1));
		JPanel contain = new JPanel(new BorderLayout());

		/*
		 * Set up the player board
		 */

		for (int i = 0; i < spyButtonArr.length; i++) {
			JButton spyButton = new JButton();
			spyButton.setOpaque(true);
			GameButton spyGameButton = new GameButton(spyButton);
			spyButtonArr[i] = spyGameButton;
		}

		JPanel spyButtonPane = new JPanel();
		spyButtonPane.setLayout(new GridLayout(5, 5));

		// Add to spyButtonPane
		for (int k = 0; k < spyButtonArr.length; k++) {

			// Create button
			JButton spyButton = spyButtonArr[k].getButton();

			// Add to Pane
			spyButtonPane.add(spyButton);

			spyButtonArr[k].setButton(spyButton);

			for (int l = 0; l < 25; l++) {
				spyButton.setText(words[k]);
				spyButton.setBackground(colorsCol[k]);
			}

		}

		/*
		 * Set up titlePane
		 */
		JPanel titlePane = new JPanel(new BorderLayout());
		titlePane.setBackground(Color.WHITE);
		titlePane.setBorder(BorderFactory.createRaisedBevelBorder());
		// Title Image
		ImageIcon title = new javax.swing.ImageIcon(getClass().getResource("/img/CodeNames1.png"));
		Image img = title.getImage();
		Image newimg = img.getScaledInstance(550, 100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon titleIcon = new ImageIcon(newimg);
		JLabel titleText = new JLabel(titleIcon);
		titlePane.add(titleText);

		/*
		 * Set up instructionPane
		 */
		ImageIcon instructions = new javax.swing.ImageIcon(getClass().getResource("/img/Instructions.png"));
		Image img1 = instructions.getImage();
		Image newimg1 = img1.getScaledInstance(720, 270, java.awt.Image.SCALE_SMOOTH);
		ImageIcon instructIcon = new ImageIcon(newimg1);
		JLabel instruction = new JLabel(instructIcon);
		instructionPane.add(instruction);
		instruction.setBackground(Color.WHITE);
		instruction.setBorder(BorderFactory.createRaisedBevelBorder());


		// add board and chat to play panel
		gridPane.add(spyButtonPane);
		chatPane.add(instructionPane);
		chatPane.add(chatbox);
		gridPane.add(chatPane);
		contain.add(titlePane, BorderLayout.NORTH);
		contain.add(gridPane, BorderLayout.CENTER);

		// add play panel to PlayerView pane
		this.add(contain);
	}


	@Override
	public void update(Observable o, Object arg) {


		String[] fromServer = (String[]) arg;

		System.out.println("SPY Update args: " + Arrays.toString(fromServer));

		if (fromServer[0].equals("playerguess")) {
			int index = Integer.parseInt(fromServer[1]);

			JButton btn = SpyView.spyButtonArr[index].getButton();

			if (fromServer[2].equals("blue")) {
				btn.setBackground(Color.black);
				btn.setForeground(blue);
				btn.repaint();
			} else if (fromServer[2].equals("bluewin")) {
				btn.setBackground(Color.black);
				btn.setForeground(blue);
				btn.repaint();
				JOptionPane.showMessageDialog(null, "You win!", "Victory", JOptionPane.OK_OPTION);
				System.exit(0);
			} else if (fromServer[2].equals("redwin")) {
				btn.setBackground(Color.black);
				btn.setForeground(red);
				btn.repaint();
				JOptionPane.showMessageDialog(null, "Game Over!", "Defeat", JOptionPane.OK_OPTION);
				System.exit(0);
			}

			SpyView.spyButtonArr[index].setButton(btn);

		
		if(fromServer[0].equals("send-message")){
			
			
		
			System.out.println("send-message called");
		}
	}
}
}
package GUI2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * Class PlayerView is the JPanel to be placed on the final ClientApp2 frame.
 * 
 * PlayerView represents the Player panel which differs from that of the
 * SpyMaster panel, as they do not have visibility of the colors on the game board. 
 * 
 * @author Tori Keegan & Benjamin Fellows
 *
 */

public class PlayerView extends JPanel implements Observer {

	static GameButton[] playerButtonArr = new GameButton[25];
	private String[] words;
	public Client client;
	public static final Color red = new Color(255, 51, 51);
	public static final Color blue = new Color(51, 153, 255);
	static JButton[] playerButton = new JButton[25];

	/*
	 * PlayerView is the main panel
	 */

	public PlayerView(ChatBox chatbox, Client client, String[] words) {
		this.words = words;
		this.client = client;

		//set the layout and size 
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

		for (int i = 0; i < playerButtonArr.length; i++) {
			JButton playerButton = new JButton();
			playerButton.setOpaque(true);
			GameButton playerGameButton = new GameButton(playerButton);
			playerButtonArr[i] = playerGameButton;
		}

		JPanel playerButtonPane = new JPanel();
		playerButtonPane.setLayout(new GridLayout(5, 5));

		// Add to playerButtonPane
		for (int k = 0; k < playerButtonArr.length; k++) {

			// Create button
			JButton playerButton = playerButtonArr[k].getButton();
	
			// Add to Pane
			playerButtonPane.add(playerButton);
			int index = k;

			for (int l = 0; l < 25; l++) {
				playerButton.setText(words[k]);
			}

			//ActionListener for the playerButton
			playerButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					String guess = String.valueOf(index);
					System.out.println("Player Guess: " + guess);
					client.playerGuess(guess);

					if (playerButton.getBackground().equals(blue)) {
						JOptionPane.showMessageDialog(chatbox, "You cannot click a button twice", "Error",
								JOptionPane.OK_OPTION);
						throw new IllegalArgumentException("Cannot click a button twice");

					}
				}

			});
			playerButtonArr[index].setButton(playerButton);

		}

		/*
		 * Set up titlePane which says 'CodeNames'
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
		gridPane.add(playerButtonPane);
		chatPane.add(instructionPane);
		chatPane.add(chatbox);
		gridPane.add(chatPane);
		contain.add(titlePane, BorderLayout.NORTH);
		contain.add(gridPane, BorderLayout.CENTER);

		// add play panel to PlayerView pane
		this.add(contain);
	}

	public void update(Observable o, Object arg) {

		// arg object assigned to String[]
		String[] fromServer = (String[]) arg;
		// if arg recieves an array which equals playerguess
		if (fromServer[0].equals("playerguess")) {

			// index is initialised to index of button clicked by player
			int index = Integer.parseInt(fromServer[1]);

			// btn is given a static reference to the JButton in position index
			// of playerButtonArr
			JButton btn = PlayerView.playerButtonArr[index].getButton();

			// colour changed dependent on value of playerGuess[2]
			if (fromServer[2].equals("blue")) {
				btn.setBackground(blue);
				btn.repaint();
			} else if (fromServer[2].equals("bluewin")) {
				btn.setBackground(blue);
				btn.repaint();
				JOptionPane.showMessageDialog(null, "You win!", "Victory", JOptionPane.OK_OPTION);
				System.exit(0);
			} else if (fromServer[2].equals("redwin")) {
				btn.setBackground(red);
				btn.repaint();
				JOptionPane.showMessageDialog(null, "Game Over!", "Defeat", JOptionPane.OK_OPTION);
				System.exit(0);
			}

			// btn is returned to playerButtonArr
			PlayerView.playerButtonArr[index].setButton(btn);

		}

	}

}

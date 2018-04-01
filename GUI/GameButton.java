package GUI2;

import javax.swing.JButton;

public class GameButton extends JButton {
	
	JButton button = new JButton();
	
	public GameButton(JButton button){
		this.button = button;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

}

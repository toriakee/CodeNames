package GUI2;

import javax.swing.JFrame;

public class MainTest extends JFrame {
	public static void main(String[] args) {
		JFrame playerframe = new JFrame();
		playerframe.setVisible(true);
		playerframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerframe.setSize(1000, 1000);
		
		JFrame spyframe = new JFrame();
		spyframe.setVisible(true);
		spyframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		spyframe.setSize(1000, 1000);

		PlayerView player = new PlayerView();
		SpyView spy = new SpyView();

		playerframe.add(player);
		spyframe.add(spy);
	}
}

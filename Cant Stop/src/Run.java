import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class Run {
	JFrame frame = new JFrame("Run");

	public static void main(String[] args) {
		new Run();
	}
	public Run() {
		MainMenu main= new MainMenu(this);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Can't Stop");
		frame.getContentPane().add(main);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
}






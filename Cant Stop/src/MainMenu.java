import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainMenu extends JPanel {
	Run game;
	MainMenu(Run game) {
		setBackground(Color.red);
		setVisible(true);
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (e.getX() >= 600 && e.getX() <= 1000) {
					if (e.getY() >= 330 && e.getY() <= 330 + 150) {
						newGameScreen();
					}
				}
				if (e.getX() >= 600 && e.getX() <= 1000) {
					if (e.getY() >= 580 && e.getY() <= 580 + 150) {
						resumeGameScreen();
					}
				}

			}

		});
		this.game = game;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.yellow);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 120));
		g2.drawString("CAN'T STOP", 405, 235);
		g.setColor(Color.white);
		g2.drawString("CAN'T STOP", 410, 240);
		g.setColor(Color.yellow);
		g.fillRect(590, 320, 400, 150);
		g.setColor(Color.white);
		g.fillRect(600, 330, 400, 150);
		g.setColor(Color.BLACK);
		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 40));
		g2.drawString("Play New Game", 630, 410);
		g.setColor(Color.yellow);
		g.fillRect(590, 570, 400, 150);
		g.setColor(Color.white);
		g.fillRect(600, 580, 400, 150);
		g.setColor(Color.BLACK);
		g2.drawString("Resume Game", 630, 660);
		Image image = Toolkit.getDefaultToolkit().getImage("Images/Dice.png");
		g.drawImage(image, 100, 300, this);
		g.drawImage(image, 1310, 300, this);
	}

	public void newGameScreen() {
		game.frame.getContentPane().removeAll();
		PlayerSelection select= new PlayerSelection(game);
		game.frame.getContentPane().add(select);
		game.frame.revalidate();
	}

	public void resumeGameScreen() {
		game.frame.getContentPane().removeAll();
		SavedGames saved= new SavedGames(game);
		game.frame.getContentPane().add(saved);
		game.frame.revalidate();
	}

}






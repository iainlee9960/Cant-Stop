import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CantStopGameScreen extends JPanel implements MouseListener {

	private int mostrecentchoice = 99; // intentionally out of bounds for usage so no buttons start highlighted
	private boolean isfast = false;
	private boolean makingchoice = false; // FALSE when player prompted to make a roll, TRUE when player choosing move
	// options
	private boolean busted = false;
	private boolean mousedown = false;
	private int[][] choices; // SET TO THE LIST OF CHOICES FROM CANTSTOPGAME IN THE PAINTCOMPONENT
	private boolean AIComp = false;
	private CantStopGame game;

	public CantStopGameScreen(boolean AIComparison, CantStopGame newGame) {
		AIComp = AIComparison;
		game = newGame;
	}

	// TODO USE isFast() and mostRecentChoice() to get the speed the player wants to
	// run the game at and the most recent choice made.
	public boolean isFast() {
		return isfast;
	}

	public int mostRecentChoice() {
		return mostrecentchoice;
	}
	private void background (Graphics g) {
		Color shadeColor;
		int runner = 0;
		while(runner != 200) {
			shadeColor = new Color(255-runner,0,0+(int)(runner/2));
			g.setColor(shadeColor);
			g.drawLine(400+runner,-200+runner, -200+runner, 600+runner); //ratio of lines, 600 diff on x and 800 diff on y
			g.drawLine(401+runner,-200+runner, -200+runner, 601+runner);
			runner++;
		}
		while(runner != 0) {
			shadeColor = new Color(255-runner,0,0+(int)(runner/2));
			g.setColor(shadeColor);
			g.drawLine(2000-runner, 400-runner, 1400-runner, 1200-runner);
			g.drawLine(2001-runner, 400-runner, 1400-runner, 1201-runner);
			runner--;
		}
	}

	// Determine which button is pressed
	public void mouseReleased(MouseEvent arg0) { //NOTE: CHANGED BACK TO THIS SYSTEM TO AVOID DOUBLE-REGISTERING
		//PROBLEM WHERE MOUSE LISTENER TICKS ARE FASTER THAN SCREEN UPDATE
		//PLEASE NOTE MOUSEPRESSED FUNCTION
		if(mousedown) {
			if(in(1150, 460, 1300, 580)) {
				handlechoice(0);
			} else if(in(1298, 460, 1448, 580)) {
				handlechoice(1);
			} else if(in(1150, 578, 1300, 698)) {
				handlechoice(2);
			} else if(in(1298, 578, 1448, 698)) {
				handlechoice(3);
			} else if(in(1150, 696, 1300, 816)) {
				handlechoice(4);
			} else if(in(1298, 696, 1448, 816)) {
				handlechoice(5);
			} else if(in(1450, 30, 1525, 45)) {
				handlespeed(false);
			} else if(in(1450, 60, 1525, 75)) {
				handlespeed(true);
			} else if(in(1310, 830, 1435, 890)) {
				if(makingchoice) {
					if(mostrecentchoice < 7) { //MAKE SURE A BUTTON HAS BEEN PRESSED AND NOT AT DEFAULT 99 VALUE
						handledone();
					} else if(busted) {
						handlebust();
					}
				} else {
					handleroll();
				}
			} else if(in(1310, 810, 1435, 870)) {
				handlestop();
			}
		}

		mousedown = false;
	}


	private void handlestop() {
		// TODO PUT CODE HERE TO RUN WHEN STOP BUTTON PRESSED
		makingchoice = false;
	}

	private void handleroll() {
		makingchoice = true;
		mostrecentchoice = 99; // OUT OF BOUNDS TO INDICATE NO CHOICE
		// TODO PUT CODE HERE TO RUN WHEN ROLL BUTTON PRESSED

		this.repaint();
	}

	private void handledone() {
		makingchoice = false;
		// TODO PUT CODE HERE TO RUN WHEN DONE BUTTON PRESSED

		this.repaint();
	}

	private void handlebust() {
		makingchoice = false;
		//TODO PUT CODE HERE TO RUN WHEN PLAYER PRESSES TO CONTINUE AFTER THEY ROLL AND HAVE NO MOVES

		this.repaint();
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		if(!mousedown) {
			mousedown = true;
		}
	}


	private boolean in(int x1, int y1, int x2, int y2) {
		Point mousepoint = MouseInfo.getPointerInfo().getLocation();
		// System.out.println(mousepoint.x + ", " + mousepoint.y);
		// System.out.println(x1 + ", " + x2 + ", " + y1 + ", " + y2);
		if ((mousepoint.x > x1 && mousepoint.x < x2 || mousepoint.y > x2 && mousepoint.y < x1)
				&& (mousepoint.y > y1 && mousepoint.y < y2 || mousepoint.y > y2 && mousepoint.y < y1)) {
			return true;
		} else {
			return false;
		}
	}

	private void handlechoice(int tohandle) {
		if (tohandle < choices.length) {
			mostrecentchoice = tohandle;
			this.repaint();
		}
	}

	private void handlespeed(boolean fast) {
		if(AIComp) {
			isfast = fast;
			this.repaint();
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		background(g2d);
		g.setColor(new Color(255, 216, 176));
		g.fillRect(0, 0, 1600, 900);
		g.setColor(Color.WHITE);
		g.fillRect(20, 20, 150, 90);
		g.setColor(Color.BLACK);
		g.drawRect(20, 20, 150, 90);

		g2d.drawString("QUIT", 47, 73);
		Image grid = new ImageIcon("images/grid.png").getImage();

		g.setColor(Color.BLUE); // THE COLOR OF THE PLAYER WHO IS GOING AT THE MOMENT
		g.fillRect(1100, 150, 400, 90);
		g.setColor(Color.BLACK);
		g.drawRect(1100, 150, 400, 90);
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		g.drawString("Player Name", 1110, 210); // THE NAME OF THE PLAYER WHO IS GOING AT THE MOMENT

		// int[][] choicesimport = game.getChoices();
		// TODO integrate, comment out the following line of test data and use value
		// from cantstopgame, possibly the above line will work?
		int[][] choicesimport = { { 0, 0}};
		choices = choicesimport;

		// If the player is presented with choices, display them. Otherwise, draw the
		// "Stop" and "Roll" buttons.
		if(makingchoice) {
			if((choices.length == 1 && choices[0][1] == 0 && choices[0][0] == 0)) {
				g.setColor(Color.BLACK);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				g.drawString("You Busted!", 1156, 432);
				g.setColor(Color.WHITE);
				g.fillRect(1310, 810, 125, 60);
				g.setColor(Color.BLACK);
				g.drawRect(1310, 810, 125, 60);
				g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
				g.drawString("DONE", 1320, 855);
				busted = true;
			} else {
				busted = false;
				g.setColor(Color.BLACK);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				g.drawString("Click to Select:", 1156, 432);
				boolean highlightme;
				if(choices.length > 0) {
					if(mostrecentchoice == 0) {
						highlightme = true;
					} else {
						highlightme = false;
					}
					g = drawbutton(g, 1150, 440, 150, 120, choicetext(choices, 0), highlightme);   
				}
				if(choices.length > 1) {
					if(mostrecentchoice == 1) {
						highlightme = true;
					} else {
						highlightme = false;
					}
					g = drawbutton(g, 1298, 440, 150, 120, choicetext(choices, 1), highlightme);   
				}
				if(choices.length > 2) {
					if(mostrecentchoice == 2) {
						highlightme = true;
					} else {
						highlightme = false;
					}
					g = drawbutton(g, 1150, 558, 150, 120, choicetext(choices, 2), highlightme);   
				}
				if(choices.length > 3) {
					if(mostrecentchoice == 3) {
						highlightme = true;
					} else {
						highlightme = false;
					}
					g = drawbutton(g, 1298, 558, 150, 120, choicetext(choices, 3), highlightme);   
				}
				if(choices.length > 4) {
					if(mostrecentchoice == 4) {
						highlightme = true;
					} else {
						highlightme = false;
					}
					g = drawbutton(g, 1150, 676, 150, 120, choicetext(choices, 4), highlightme);   
				}
				if(choices.length > 5) {
					if(mostrecentchoice == 5) {
						highlightme = true;
					} else {
						highlightme = false;
					}
					g = drawbutton(g, 1298, 676, 150, 120, choicetext(choices, 5), highlightme);   
				}

				if(mostrecentchoice < 7) { //MAKE SURE A BUTTON HAS BEEN PRESSED AND NOT AT DEFAULT 99 VALUE
					g.setColor(Color.WHITE);
					g.fillRect(1310, 810, 125, 60);
					g.setColor(Color.BLACK);
					g.drawRect(1310, 810, 125, 60);
					g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
					g.drawString("DONE", 1320, 855);
				}
			}
		} else { //If there are no choices, let the player end their turn or roll again
			g.setColor(Color.WHITE);
			g.fillRect(1150, 810, 125, 60);
			g.setColor(Color.BLACK);
			g.drawRect(1150, 810, 125, 60);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g.drawString("STOP", 1165, 855);

			g.setColor(Color.WHITE);
			g.fillRect(1310, 810, 125, 60);
			g.setColor(Color.BLACK);
			g.drawRect(1310, 810, 125, 60);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g.drawString("ROLL", 1323, 855);
		}
		g.drawImage(grid, 40, 60, this);
		addMouseListener(this);

		if(AIComp) {
			if (!isfast) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.GRAY);
			}
			g.fillOval(1450, 10, 15, 15);
			if (isfast) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.GRAY);
			}
			g.fillOval(1450, 40, 15, 15);
			g.setColor(Color.BLACK);
			g.drawOval(1450, 10, 15, 15);
			g.drawOval(1450, 40, 15, 15);

			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g2d.drawString("SLOW", 1470, 25);
			g2d.drawString("FAST", 1470, 55);
		}

		drawDice(g2d);
		// testing placepiece

		for(int i=0;i<3;i++) {
			new PlacePiece(new Color(0, 255, 0), 2, i+1, 1,g2d);
		}
		for(int i=0;i<5;i++) {
			new PlacePiece(new Color(255, 0, 0), 3, i+1, 1,g2d);
		}
		for(int i=0;i<7;i++) {
			new PlacePiece(new Color(255, 255, 255), 4, i+1, 1,g2d);
		}
		for(int i=0;i<9;i++) {
			new PlacePiece(new Color(0, 255, 255), 5, i+1, 1,g2d);
		}
		for(int i=0;i<11;i++) {
			new PlacePiece(new Color(255, 0, 255), 6, i+1, 1,g2d);
		}
		for(int i=0;i<13;i++) {
			new PlacePiece(new Color(255, 255, 0), 7, i+1, 1,g2d);
		}
		for(int i=0;i<11;i++) {
			new PlacePiece(new Color(255, 255, 255), 8, i+1, 1,g2d);
		}
		for(int i=0;i<9;i++) {
			new PlacePiece(rand(), 9, i+1,1, g2d);
		}
		for(int i=0;i<7;i++) {
			new PlacePiece(rand(), 10, i+1, 1,g2d);
		}
		for(int i=0;i<5;i++) {
			new PlacePiece(rand(), 11, i+1,1, g2d);
		}
		for(int i=0;i<3;i++) {
			new PlacePiece(rand(), 12, i+1,1, g2d);
		}
		new PlacePiece(new Color(25,255,25), 12, 3, 2, g2d);
		new PlacePiece(new Color(255,0,0), 7, 5, 2, g2d);

	}
	private Color rand() {
		int R = (int) (Math.random() * 256);
		int G = (int) (Math.random() * 256);
		int B = (int) (Math.random() * 256);
		return new Color(R, G, B);
	}
	private String choicetext(int[][] arr, int in) {
		if (arr[in].length == 1) {
			return "" + arr[in][0];
		} else {
			return "" + arr[in][0] + ", " + arr[in][1];
		}
	}

	// TO USE THE drawbutton METHOD, SET g TO THE RETURN OF THE METHOD.
	// EX: g = drawbutton(g, 1150, 440, 150, 120, "monky", false);
	private Graphics drawbutton(Graphics g, int x, int y, int w, int h, String text, boolean highlighted) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
		if (highlighted) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.RED);
		}
		g.fillRect(x + 2, y + 2, w - 4, h - 4);
		g.setColor(Color.BLACK);
		g.drawString(text, x + ((w - (text.length() * 15)) / 2), y + 60);
		return g;
	}
	private void drawDice(Graphics g) {
		Image d1 = new ImageIcon("images/die1.gif").getImage();
		Image d2 = new ImageIcon("images/die2.gif").getImage();
		Image d3 = new ImageIcon("images/die3.gif").getImage();
		Image d4 = new ImageIcon("images/die4.gif").getImage();
		Image d5 = new ImageIcon("images/die5.gif").getImage();
		Image d6 = new ImageIcon("images/die6.gif").getImage();
		int initial = 1125;

		int[] ayo = { 4, 2, 3, 6 };
		for (int i = 0; i < 4; i++) {
			switch (ayo[i]) {
			case 1:g.drawImage(d1, initial, 270, this);break;
			case 2:g.drawImage(d2, initial, 270, this);break;
			case 3:g.drawImage(d3, initial, 270, this);break;
			case 4:g.drawImage(d4, initial, 270, this);break;
			case 5:g.drawImage(d5, initial, 270, this);break;
			case 6:g.drawImage(d6, initial, 270, this);break;
			}
			initial = initial + 90;
		}
	}
}








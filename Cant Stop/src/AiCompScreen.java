import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class AiCompScreen extends JPanel implements ActionListener {
	final static String LABEL_TEXT = "This is custom:";
	JFrame frame;
	JPanel panel;
	JTextField numGames;
	final int aiButtonHeight = 80;
	final int aiButtonWidth = 500;
	final int aiNameX = 100;
	final int buttonW = 160;
	final int buttonH = 60;
	final int buttonY = 730;
	final int promptTell = 130;
	final int promptBoxX = 775;

	/*int[] aiTypes;
	String[] aiNames;
	Color[] aiColors;*/
	ComputerPlayer[] Cpus;
	int[] currentRunWins = { 1, 6, 2, 3 };
	int numAi;

	String confirmBox = "";
	Boolean confirmBoxOn = false;
	Boolean ranEngine = false;
	Boolean didntRun = false;
	int numGamesRun = 0;
	int confirmNum = 0;
	String numGameShow = "";
	Font font = new Font("Copperplate Gothic Bold", Font.BOLD, 60);
	JFormattedTextField textField = new JFormattedTextField("");
	Run game;

	public AiCompScreen(Run game, CantStopPlayer[] players/*int[] aiDiffTypes, String[] aiGivenNames, Color[] aiColors*/) {
		this.game = game;
		/*aiTypes = aiDiffTypes;
		aiNames = aiGivenNames;
		this.aiColors = aiColors;
		if (aiTypes.length == aiNames.length)
			numAi = aiTypes.length;
		else
			System.out.println("Wrong size ERROR!!");*/
		numAi = players.length;
		Cpus = new ComputerPlayer[numAi];
		for(int i=0; i<numAi; i++) {
			Cpus[i] = (ComputerPlayer)players[i];
		}
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		textField.addActionListener(this);
		textField.setOpaque(false);
		textField.setBorder(null);
		textField.setFont(font);
		add(textField);
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				String value = textField.getText();
				int l = value.length();
				if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == (char)8) {
					textField.setEditable(true);
				} else {
					textField.setEditable(false);
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (didntRun) {
					if ((e.getX() > 400 && e.getX() < (400 + buttonW))
							&& (e.getY() > 500 && e.getY() < (500 + buttonH))) {
						didntRun = false;
						repaint();
					}
				}
				if (confirmBoxOn) { //handles confirm 
					if ((e.getX() > 400 && e.getX() < (400 + buttonW))
							&& (e.getY() > 500 && e.getY() < (500 + buttonH))) {
						confirmBoxOn = false;
						if (confirmNum == 1) { //handles main menu confirmation
							MainMenu main = new MainMenu(game);
							game.frame.getContentPane().removeAll();
							game.frame.getContentPane().add(main);
							game.frame.revalidate();
						} else if (confirmNum == 2) { //handles run confirmation
							if (numGamesRun == 0) {
								didntRun = true;
								confirmBoxOn = false;
							} else {
								ranEngine = true;
								AiComparisonEngine run = new AiComparisonEngine();
								/*ComputerPlayer[] players = new ComputerPlayer[numAi];
								int runner = 0;
								while (numAi > runner) {
									players[runner] = new ComputerPlayer();
									if (aiTypes[runner] == 0)
										players[runner].setAI("SimpleStrategy");
									else
										players[runner].setAI("RandomStrategy");
									players[runner].setPlayerColor(aiColors[runner]);
									players[runner].setPlayerName(aiNames[runner]);
									runner++;
								}*/
								currentRunWins = run.compareAIs(Cpus, numGamesRun);
							}
						} else { //handles back confirmation
							PlayerSelection select = new PlayerSelection(game);
							game.frame.getContentPane().removeAll();
							game.frame.getContentPane().add(select);
							game.frame.revalidate();
						}
						repaint();
					}
					if ((e.getX() > 1030 && e.getX() < (1030 + buttonW)) //handles cancel
							&& (e.getY() > 500 && e.getY() < (500 + buttonH))) {
						confirmBoxOn = false;
						repaint();
					}
				} else {
					if ((e.getX() > 90 && e.getX() < (90 + buttonW))
							&& (e.getY() > buttonY && e.getY() < (buttonY + buttonH))) {
						confirmBox = "quit to the main menu?";
						confirmBoxOn = true;
						confirmNum = 1;
						repaint();
					}
					if ((e.getX() > 590 && e.getX() < (590 + buttonW))
							&& (e.getY() > buttonY && e.getY() < (buttonY + buttonH))) {
						confirmBox = "run with " + numGamesRun + " games?";
						confirmBoxOn = true;
						confirmNum = 2;
						repaint();
					}
					if ((e.getX() > 1090 && e.getX() < (1090 + buttonW))
							&& (e.getY() > buttonY && e.getY() < (buttonY + buttonH))) {
						confirmBox = "go back?";
						confirmBoxOn = true;
						confirmNum = 3;
						repaint();
					}
				}
			}
		});
		repaint();

	}

	public void paintComponent(Graphics g) {
		if (confirmBoxOn) {
			textField.setBounds(2000, 285, 550, 70);
		}
		if (!confirmBoxOn) {
			textField.setBounds(promptBoxX, 45, 550, 70);
			if (textField.getText().length() == 10) {
				getText();
			}
		}
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);

		background(g);
		backBoxes(g);
		buttonBoxes(g);
		g.setColor(new Color(200, 200, 200));
		g.fillRect(promptBoxX - 10, 40, aiButtonWidth + 50, aiButtonHeight);

		int runner = 0;
		while (numAi > runner) {
			nameBox(g, g2, runner);
			if (ranEngine) {
				winAmounts(g, g2, runner);
			}
			runner++;
		}
		writing(g, g2);
		if (confirmBoxOn)
			confirmation(g, g2);
		if (didntRun)
			confirmDidntRun(g, g2);
	}

	private void background(Graphics g) {
		Color shadeColor;
		int runner = 0;
		while (runner != 200) {
			shadeColor = new Color(255 - runner, 0, 0 + (int) (runner / 2));
			g.setColor(shadeColor);
			g.drawLine(400 + runner, -200 + runner, -200 + runner, 600 + runner); // ratio of lines, 600 diff on x and
																					// 800 diff on y
			g.drawLine(401 + runner, -200 + runner, -200 + runner, 601 + runner);
			runner++;
		}
		while (runner != 0) {
			shadeColor = new Color(255 - runner, 0, 0 + (int) (runner / 2));
			g.setColor(shadeColor);
			g.drawLine(1800 - runner, 400 - runner, 1200 - runner, 1200 - runner);
			g.drawLine(1801 - runner, 400 - runner, 1200 - runner, 1201 - runner);
			runner--;
		}
	}

	private void confirmDidntRun(Graphics g, Graphics g2) {
		g.setColor(new Color(130, 130, 130));
		g.fillRect(350, 200, 900, 400);
		g.setColor(new Color(30, 30, 30));
		g.drawRect(350, 200, 900, 400);

		g.setColor(new Color(0, 155, 20));
		g.fillRect(400, 500, buttonW, buttonH);

		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Times New Roman", Font.PLAIN, 55));
		g2.drawString("It didn't run. Please enter a", 390, 270);
		g2.drawString("new number.", 390, 340);

		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 25));
		g2.drawString("Confirm", 415, 535);
	}

	private void confirmation(Graphics g, Graphics g2) {
		g.setColor(new Color(130, 130, 130));
		g.fillRect(350, 200, 900, 400);
		g.setColor(new Color(30, 30, 30));
		g.drawRect(350, 200, 900, 400);

		g.setColor(new Color(0, 155, 20));
		g.fillRect(400, 500, buttonW, buttonH);
		g.setColor(new Color(171, 17, 4));
		g.fillRect(1030, 500, buttonW, buttonH);

		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Times New Roman", Font.PLAIN, 55));
		g2.drawString("Are you sure you want to", 390, 270);
		g2.drawString(confirmBox, 390, 340);

		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 25));
		g2.drawString("Confirm", 415, 535);
		g2.drawString("Cancel", 1045, 535);
	}

	private void buttonBoxes(Graphics g) {
		g.setColor(new Color(120, 120, 120));
		g.fillRect(150, buttonY, buttonW, buttonH);
		g.setColor(new Color(0, 155, 20));
		g.fillRect(650, buttonY, buttonW, buttonH);
		g.setColor(new Color(171, 17, 4));
		g.fillRect(1150, buttonY, buttonW, buttonH);
	}

	private void winAmounts(Graphics g, Graphics g2, int numPlayer) {
		g2.setFont(new Font("Times New Roman", Font.PLAIN, 55));
		g2.drawString("" + currentRunWins[numPlayer], promptBoxX + 20, 270 + (120 * numPlayer));
	}

	private void nameBox(Graphics g, Graphics g2, int boxNum) {
		g.setColor(Color.white);
		g.fillRect(aiNameX - 10, 190 + (130 * boxNum), aiButtonWidth, aiButtonHeight);

		g.setColor(Cpus[boxNum].getPlayerColor());
		g.fillRect(aiNameX, 180 + (130 * boxNum), aiButtonWidth, aiButtonHeight);
		g.fillRect(promptBoxX - 140, 220 + (120 * boxNum), 145, 65);

		g2.setColor(Color.BLACK);
		g.drawRect(promptBoxX - 140, 220 + (120 * boxNum), 145, 65);
		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 70));
		g2.drawString(Cpus[boxNum].getPlayerName(), aiNameX + 40, 245 + (130 * boxNum));
		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 35));
		int lvl;
		if(Cpus[boxNum].getAIName()=="SimpleStrategy") {
			lvl = 2;
		} else {
			lvl = 1;
		}
		g2.drawString(" " + lvl + " ", aiNameX + 5, 245 + (130 * boxNum));
		g2.setFont(new Font("Times New Roman", Font.PLAIN, 55));
		g2.drawString("Wins: ", promptBoxX - 130, 270 + (120 * boxNum));
	}

	private void backBoxes(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(640, buttonY + 10, buttonW, buttonH);
		g.fillRect(140, buttonY + 10, buttonW, buttonH);
		g.fillRect(1140, buttonY + 10, buttonW, buttonH);
	}

	private void writing(Graphics g, Graphics g2) {
		g2.setColor(Color.WHITE);

		g2.setFont(new Font("Times New Roman", Font.PLAIN, 50));
		g2.drawString("Please enter the number of", promptTell, 70);
		g2.drawString("games you would like to play:", promptTell, 120);
		g2.drawString("Current run number: " + numGameShow, 640, 185);

		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 25));
		g2.drawString("Menu", 165, buttonY + 35);
		g2.drawString("Run", 665, buttonY + 35);
		g2.drawString("Back", 1165, buttonY + 35);
	}

	private void getText() {
		String hold = textField.getText();
		numGameShow = hold;
		numGamesRun = Integer.parseInt(hold);
		textField.setText("");
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		getText();
	}
}

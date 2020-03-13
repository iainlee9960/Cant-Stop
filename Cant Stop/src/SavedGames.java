import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class SavedGames extends JPanel implements ActionListener {
	private int ScreenAmount = 3;
	private int GameAmount = 4;
	private int move = 0;
	private boolean arrow = false;
	private Run game;
	private String[] dates;
	private int numberOfSaves = 3;

	SavedGames(Run game) {

		setBackground(Color.red);
		setVisible(true);
		this.game = game;
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if ((e.getY() <= 95) && (e.getY() >= 35)) {
					if ((e.getX() <= 205) && (e.getX() >= 45)) {
						MainMenu main = new MainMenu(game);
						game.frame.getContentPane().removeAll();
						game.frame.getContentPane().add(main);
						game.frame.revalidate();
					}
				}

				if (e.getX() >= 1080 && e.getX() <= 1130) {
					if (ScreenAmount >= 1) {
						if (e.getY() >= 225 && e.getY() <= 300) {
							GameAmount--;
							checkScreenAmount(GameAmount);
							repaint();
						}
					}
					if (ScreenAmount >= 2) {
						if (e.getY() >= 425 && e.getY() <= 500) {
							GameAmount--;
							checkScreenAmount(GameAmount);
							repaint();
						}
					}
					if (ScreenAmount >= 3) {
						if (e.getY() >= 625 && e.getY() <= 700) {
							GameAmount--;
							checkScreenAmount(GameAmount);
							repaint();
						}
					}
				}

				for (int i = 0; i < 100; i++) {
					if (arrow == false) {
						if ((e.getY() <= 375) && (e.getY() >= 275)) {
							if ((e.getX() <= (480 - i / 2)) && (e.getX() >= (380 + i / 2))) {
								arrow = true;
								move--;
								if (move <= 0) {
									move = 0;
								}
								repaint();
							}
						}
						if ((e.getY() <= 675) && (e.getY() >= 575)) {
							if ((e.getX() <= (480 - i / 2)) && (e.getX() >= (380 + i / 2))) {
								arrow = true;
								move++;
								if (move >= GameAmount - 3) {
									move = GameAmount - 3;
								}
								repaint();
							}
						}
					}
				}
				arrow = false;

			}

		});
		updateDateArray();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		background(g);
		g.setColor(Color.WHITE);
		g.fillRect(40, 40, 160, 60);
		g2.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 55));

		if (ScreenAmount >= 3) {
			g.setColor(Color.WHITE);
			g.fillRect(540, 200, 500, 150);
			g.fillRect(540, 400, 500, 150);
			g.fillRect(540, 600, 500, 150);
			g.setColor(new Color(50, 205, 50));
			g.fillRect(550, 190, 500, 150);
			g.fillRect(550, 390, 500, 150);
			g.fillRect(550, 590, 500, 150);
			g.setColor(Color.orange);
			g.fillRect(1080, 225, 50, 75);
			g.fillRect(1080, 425, 50, 75);
			g.fillRect(1080, 625, 50, 75);
			g.setColor(Color.WHITE);
			for (int i = 0; i < 5; i++) {
				g.drawLine(1087 + i, 235, 1117 + i, 290);
				g.drawLine(1087 + i, 290, 1117 + i, 235);

				g.drawLine(1087 + i, 435, 1117 + i, 490);
				g.drawLine(1087 + i, 490, 1117 + i, 435);

				g.drawLine(1087 + i, 635, 1117 + i, 690);
				g.drawLine(1087 + i, 690, 1117 + i, 635);
			}
		} else if (ScreenAmount == 2) {
			g.setColor(Color.WHITE);
			g.fillRect(540, 200, 500, 150);
			g.fillRect(540, 400, 500, 150);
			g.setColor(new Color(50, 205, 50));
			g.fillRect(550, 190, 500, 150);
			g.fillRect(550, 390, 500, 150);
			g.setColor(Color.orange);
			g.fillRect(1080, 225, 50, 75);
			g.fillRect(1080, 425, 50, 75);
			g.setColor(Color.WHITE);
			for (int i = 0; i < 5; i++) {
				g.drawLine(1087 + i, 235, 1117 + i, 290);
				g.drawLine(1087 + i, 290, 1117 + i, 235);

				g.drawLine(1087 + i, 435, 1117 + i, 490);
				g.drawLine(1087 + i, 490, 1117 + i, 435);
			}
		} else if (ScreenAmount == 1) {
			g.setColor(Color.WHITE);
			g.fillRect(540, 200, 500, 150);
			g.setColor(new Color(50, 205, 50));
			g.fillRect(550, 190, 500, 150);
			g.setColor(Color.orange);
			g.fillRect(1080, 225, 50, 75);
			g.setColor(Color.WHITE);
			for (int i = 0; i < 5; i++) {
				g.drawLine(1087 + i, 235, 1117 + i, 290);
				g.drawLine(1087 + i, 290, 1117 + i, 235);
			}
		}

		if (GameAmount >= 4) {
			for (int i = 0; i < 100; i++) {
				g.drawLine(380 + i / 2, 375 - i, 480 - i / 2, 375 - i);
				g.drawLine(380 + i / 2, 575 + i, 480 - i / 2, 575 + i);
			}
		}

		g.setColor(Color.GRAY);
		g.fillRect(45, 35, 160, 60);

		g.setColor(Color.white);
		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 80));
		g2.drawString("Saved Games", 510, 120);
		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 25));
		g2.drawString("Back", 70, 70);

		// NEED GAME NAMES
		if (GameAmount >= 3) {
			String name = move + "";
			g2.drawString(name, 600, 250);
			name = move + 1 + "";
			g2.drawString(name, 600, 450);
			name = move + 2 + "";
			g2.drawString(name, 600, 650);
		} else if (GameAmount == 2) {
			String name = move + "";
			g2.drawString(name, 600, 250);
			name = move + 1 + "";
			g2.drawString(name, 600, 450);
		} else if (GameAmount == 1) {
			String name = move + "";
			g2.drawString(name, 600, 250);
		}
		// NEED GAME NAMES
	}

	private void updateDateArray() {
		String[] dateArray = new String[numberOfSaves];
		for (int i = 0; i < numberOfSaves; i++) {
			File tempFile = new File("Save\\SaveGame" + (i + 1) + ".xml");
			boolean exists = tempFile.exists();
			if (exists) {
				try {
					File file = new File("Save\\SaveGame" + (i + 1) + ".xml");
					DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					Document doc = dBuilder.parse(file);
					NodeList Nodes = doc.getChildNodes().item(0).getChildNodes();
					String dateAndTime = Nodes.item(0).getTextContent();
					dateArray[i] = dateAndTime;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			} else {
				dateArray[i] = "new game";
			}

		}
		dates = dateArray;
	}

	private void deleteSave(int numSave) {
		try {
			Files.deleteIfExists(Paths.get("Save\\SaveGame" + numSave + ".xml"));
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
		}
		numberOfSaves--;
		updateDateArray();
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
			g.drawLine(2000 - runner, 400 - runner, 1400 - runner, 1200 - runner);
			g.drawLine(2001 - runner, 400 - runner, 1400 - runner, 1201 - runner);
			runner--;
		}
	}

	private void checkScreenAmount(int GameAmount) {
		if (GameAmount >= 3) {
			ScreenAmount = 3;
		} else if (GameAmount == 2) {
			ScreenAmount = 2;
		} else if (GameAmount == 1) {
			ScreenAmount = 1;
		} else if (GameAmount == 0) {
			ScreenAmount = 0;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

}

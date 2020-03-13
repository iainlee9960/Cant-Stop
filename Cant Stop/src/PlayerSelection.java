import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerSelection extends JPanel implements ActionListener{
	final int pButtonHeight = 160;
	final int pButtonWidth = 500;
	final int oButtonHeight = 80;
	final int oButtonWidth = 140;
	
	int[] area = {0,0,0,0};
	String[] names = {"","","",""};
	int[] AIlvls = {1,1,1,1};
	boolean[] changeLvl = {false,false,false,false};
	CantStopPlayer[] players;
	Run game;

	Font font = new Font("Copperplate Gothic Bold", Font.BOLD, 55);
	JFormattedTextField[] textFields = {new JFormattedTextField("Player1"), new JFormattedTextField("Player2"), new JFormattedTextField("Player3"), new JFormattedTextField("Player4")};

	
	PlayerSelection(Run game){
		setBackground(Color.RED);
		setVisible(true);
		
		for(JFormattedTextField field: textFields) {
			field.addActionListener(this);
			field.setOpaque(false);
			field.setBorder(null);
			field.setFont(font);
			add(field);
		}
		
		this.game = game;
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getY() >= 350 && e.getY() <= 430) {
					if ((e.getX() >= 150 )&& (e.getX() <= 150 + oButtonWidth)) {
						area[0] = 1;
						repaint();
						//add action
					}
					if (e.getX() >= 320 && e.getX() <= 320 + oButtonWidth) {
						area[0] = 2;
						repaint();
						//add action
					}
					if ((e.getX() >= 490) && (e.getX() <= 490 + oButtonWidth)) {
						area[0] = 3;
						repaint();
						//add action
					}
					
					if ((e.getX() >= 910 )&& (e.getX() <= 910 + oButtonWidth)) {
						area[1] = 1;
						repaint();
						//add action
					}
					if (e.getX() >= 1080 && e.getX() <= 1080 + oButtonWidth) {
						area[1] = 2;
						repaint();
						//add action
					}
					if (e.getX() >= 1250 && e.getX() <= 1250 + oButtonWidth) {
						area[1] = 3;
						repaint();
						//add action
					}
				}
				
				if (e.getY() >= 710 && e.getY() <= 790) {
					if ((e.getX() >= 150 )&& (e.getX() <= 150 + oButtonWidth)) {
						area[2] = 1;
						repaint();
						//add action
					}
					if (e.getX() >= 320 && e.getX() <= 320 + oButtonWidth) {
						area[2] = 2;
						repaint();
						//add action
					}
					if (e.getX() >= 490 && e.getX() <= 490 + oButtonWidth) {
						area[2] = 3;
						repaint();
						//add action
					}
					
					if ((e.getX() >= 910 )&& (e.getX() <= 910 + oButtonWidth)) {
						area[3] = 1;
						repaint();
						//add action
					}
					if (e.getX() >= 1080 && e.getX() <= 1080 + oButtonWidth) {
						area[3] = 2;
						repaint();
						//add action
					}
					if (e.getX() >= 1250 && e.getX() <= 1250 + oButtonWidth) {
						area[3] = 3;
						repaint();
						//add action
					}
				}
				
				for (int i = 0; i <= 20; i ++) {
					if (changeLvl[3] == false) {
						if ((e.getX() <= 1130) && (e.getX() >= 1110)) {
							if ((e.getY() <= (840 - i/2)) && (e.getY() >= (820 + i/2))) {
								AIlvls[3]--;
								changeLvl[3] = true;
								if (AIlvls[3] >= 3)
									AIlvls[3] = 3;
								if (AIlvls[3] <= 1) 
									AIlvls[3] = 1;
								repaint();
							}
						}
						if ((e.getX() >= 1155) && (e.getX() <= 1175)) {
							if ((e.getY() <= 840 - i/2) && (e.getY() >= 820 + i/2)) {
								AIlvls[3] ++;
								changeLvl[3] = true;
								if (AIlvls[3] >= 3)
									AIlvls[3] = 3;
								if (AIlvls[3] <= 1) 
									AIlvls[3] = 1;
								repaint();
							}
						}	
					}
				}
				
				for (int i = 0; i <= 20; i ++) {
					if (changeLvl[2] == false) {
						if ((e.getX() <= 370) && (e.getX() >= 350)) {
							if ((e.getY() <= (840 - i/2)) && (e.getY() >= (820 + i/2))) {
								AIlvls[2] --;
								changeLvl[2] = true;
								if (AIlvls[2] >= 3)
									AIlvls[2] = 3;
								if (AIlvls[2] <= 1) 
									AIlvls[2] = 1;
								repaint();
							}
						}
						if ((e.getX() >= 395) && (e.getX() <= 415)) {
							if ((e.getY() <= 840 - i/2) && (e.getY() >= 820 + i/2)) {
								AIlvls[2] ++;
								changeLvl[2] = true;
								if (AIlvls[2] >= 3)
									AIlvls[2] = 3;
								if (AIlvls[2] <= 1) 
									AIlvls[2] = 1;
								repaint();
							}
						}	
					}
				}
				
				for (int i = 0; i <= 20; i ++) {
					if (changeLvl[1] == false) {
						if ((e.getX() <= 1130) && (e.getX() >= 1110)) {
							if ((e.getY() <= (470 - i/2)) && (e.getY() >= (450 + i/2))) {
								AIlvls[1] --;
								changeLvl[1] = true;
								if (AIlvls[1] >= 3)
									AIlvls[1] = 3;
								if (AIlvls[1] <= 1) 
									AIlvls[1] = 1;
								repaint();
							}
						}
						if ((e.getX() >= 1155) && (e.getX() <= 1175)) {
							if ((e.getY() <= 470 - i/2) && (e.getY() >= 450 + i/2)) {
								AIlvls[1] ++;
								changeLvl[1] = true;
								if (AIlvls[1] >= 3)
									AIlvls[1] = 3;
								if (AIlvls[1] <= 1) 
									AIlvls[1] = 1;
								repaint();
							}
						}	
					}
				}
					
				for (int i = 0; i <= 20; i ++) {
					if (changeLvl[0] == false) {
						if ((e.getX() <= 370) && (e.getX() >= 350)) {
							if ((e.getY() <= (470 - i/2)) && (e.getY() >= (450 + i/2))) {
								AIlvls[0] --;
								changeLvl[0] = true;
								if (AIlvls[0] >= 3)
									AIlvls[0] = 3;
								if (AIlvls[0] <= 1) 
									AIlvls[0] = 1;
								repaint();
							}
						}
						if ((e.getX() >= 395) && (e.getX() <= 415)) {
							if ((e.getY() <= 470 - i/2) && (e.getY() >= 450 + i/2)) {
								AIlvls[0] ++;
								changeLvl[0] = true;
								if (AIlvls[0] >= 3)
									AIlvls[0] = 3;
								if (AIlvls[0] <= 1) 
									AIlvls[0] = 1;
								repaint();
							}
						}	
					}
				}
				changeLvl[0] = false;
				changeLvl[1] = false;
				changeLvl[2] = false;
				changeLvl[3] = false;
				
				if ((e.getX() >= 690) && (e.getX() <= 850)) {
					if ((e.getY() >= 810) && (e.getY() <= 870)) {
						int numPlayers = 0, numCpu = 0, numHuman = 0;
						for(int i=0;i<4;i++) {
							names[i] = textFields[i].getText();
							if(area[i]!=3 && area[i]!=0) {
								numPlayers++;
							}
						}
						if(numPlayers>1) {
							players = new CantStopPlayer[numPlayers];
							int index = 0;
							for(int i=0;i<4;i++) {
								if(area[i]==3) {
									index--;
								}
								if(area[i]==1) {
									players[index] = new HumanPlayer();
									players[index].setPlayerName(names[i]);
									players[index].setPlayerColor(getColor(i));
									numHuman++;
								}
								if(area[i]==2) {
									players[index] = new ComputerPlayer();
									players[index].setPlayerName(names[i]);
									players[index].setPlayerColor(getColor(i));
									if(AIlvls[i]==1) {
										ComputerPlayer temp = (ComputerPlayer)players[index];
										temp.setAI("Random");
										players[index] = temp;
									} else {
										ComputerPlayer temp = (ComputerPlayer)players[index];
										temp.setAI("SimpleStrategy");
										players[index] = temp;									}
									numCpu++;
								}
								index++;
							}
							if(numHuman==0) {
								int[] lvls = new int[numPlayers];
								String[] CpuNames = new String[numPlayers];
								Color[] CpuColors = new Color[numPlayers];
								index = 0;
								for(int i=0;i<4;i++) {
									if(area[i]==3) {
										index--;
									}
									if(area[i]==2) {
										lvls[index] = AIlvls[i];
										CpuNames[index] = names[i];
										CpuColors[index] = getColor(i);
									}
									index++;
								}
								game.frame.getContentPane().removeAll();
								AiCompScreen aiHoldScreen = new AiCompScreen(game, lvls, CpuNames, CpuColors, players);
								aiHoldScreen.setBackground(Color.red);	
								game.frame.getContentPane().add(aiHoldScreen);
								game.frame.revalidate();
							} else if(numCpu>0){
								CantStopGame newGame = new CantStopGame(players);
								CantStopGameScreen GameScreen= new CantStopGameScreen(true, newGame);
								game.frame.getContentPane().removeAll();
								game.frame.getContentPane().add(GameScreen);
								game.frame.revalidate();
							} else {
								CantStopGame newGame = new CantStopGame(players);
								CantStopGameScreen GameScreen= new CantStopGameScreen(false, newGame);
								game.frame.getContentPane().removeAll();
								game.frame.getContentPane().add(GameScreen);
								game.frame.revalidate();
							}
						}
					}
				}
				if ((e.getX() >= 20) && (e.getX() <= 120)) {
					if ((e.getY() >= 20) && (e.getY() <= 60)) {
						MainMenu main= new MainMenu(game);
						game.frame.getContentPane().removeAll();
						game.frame.getContentPane().add(main);
						game.frame.revalidate();
					}
				}
			}
		});
	}
	public Color getColor(int num) {
		switch(num) {
		case 0: return Color.yellow;
		case 1: return new Color(51, 255, 255);
		case 2: return Color.green;
		case 3: return Color.orange;
		default: return Color.white;
		}	
	}
	public void paintComponent(Graphics g) {
		for(int i=0; i<4; i++) {
			switch(i) {
			case 0: textFields[i].setBounds(160,180,400,70); break;
			case 1: textFields[i].setBounds(920,180,400,70); break;
			case 2: textFields[i].setBounds(160,540,400,70); break;
			case 3: textFields[i].setBounds(920,540,400,70); break;
			}
			if (textFields[i].getText().length() <= 9) {
				names[i] = textFields[i].getText();
			} else if (textFields[i].getText().length() > 9) {
				textFields[i].setText(names[i]);
			}
		}
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		background(g);
		g.setColor(Color.WHITE);
		g.fillRect(20,20,100,40);
		g.fillRect(690, 810, 160, 60);
		g.fillRect(140, 140, pButtonWidth, pButtonHeight);
		g.fillRect(900, 140, pButtonWidth, pButtonHeight);
		g.fillRect(140, 500, pButtonWidth, pButtonHeight);
		g.fillRect(900, 500, pButtonWidth, pButtonHeight);
		
		g.fillRect(140, 360, oButtonWidth, oButtonHeight);
		g.fillRect(310, 360, oButtonWidth, oButtonHeight);
		g.fillRect(480, 360, oButtonWidth, oButtonHeight);
		g.fillRect(900, 360, oButtonWidth, oButtonHeight);
		g.fillRect(1070, 360, oButtonWidth, oButtonHeight);
		g.fillRect(1240, 360, oButtonWidth, oButtonHeight);
		
		g.fillRect(140, 720, oButtonWidth, oButtonHeight);
		g.fillRect(310, 720, oButtonWidth, oButtonHeight);
		g.fillRect(480, 720, oButtonWidth, oButtonHeight);
		g.fillRect(900, 720, oButtonWidth, oButtonHeight);
		g.fillRect(1070, 720, oButtonWidth, oButtonHeight);
		g.fillRect(1240, 720, oButtonWidth, oButtonHeight);
		
		g.setColor(Color.yellow);
		g.fillRect(150, 130, pButtonWidth, pButtonHeight);
		g.setColor(new Color(51, 255, 255));
		g.fillRect(910, 130, pButtonWidth, pButtonHeight);
		g.setColor(Color.green);
		g.fillRect(150, 490, pButtonWidth, pButtonHeight);
		g.setColor(Color.orange);
		g.fillRect(910, 490, pButtonWidth, pButtonHeight);
		
		g.setColor(new Color(171, 17, 4));
		g.fillRect(700, 800, 160, 60);
		
		g.setColor(Color.gray);
		g.fillRect(25,15,100,40);
		
		if (area[0] == 1) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(150, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else 
			g.fillRect(150, 350, oButtonWidth, oButtonHeight);
		if(area[0] == 2) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(320, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(320, 350, oButtonWidth, oButtonHeight);
		if (area[0] == 3) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(490, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(490, 350, oButtonWidth, oButtonHeight);
		
		if (area[1] == 1) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(910, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else 
			g.fillRect(910, 350, oButtonWidth, oButtonHeight);
		if(area[1] == 2) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(1080, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(1080, 350, oButtonWidth, oButtonHeight);
		if (area[1] == 3) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(1250, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(1250, 350, oButtonWidth, oButtonHeight);
		
		
		if (area[2] == 1) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(150, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else 
			g.fillRect(150, 710, oButtonWidth, oButtonHeight);
		if(area[2] == 2) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(320, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(320, 710, oButtonWidth, oButtonHeight);
		if (area[2] == 3) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(490, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(490, 710, oButtonWidth, oButtonHeight);
		
		if (area[3] == 1) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(910, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else 
			g.fillRect(910, 710, oButtonWidth, oButtonHeight);
		if(area[3] == 2) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(1080, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(1080, 710, oButtonWidth, oButtonHeight);
		if (area[3] == 3) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(1250, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(1250, 710, oButtonWidth, oButtonHeight);
		
		for (int i = 0; i <= 20; i ++) {
			g.setColor(Color.WHITE);
			g.drawLine(1130 - i, 840 - i/2, 1130 - i, 820 + i/2);
			g.drawLine(1155 + i, 840 - i/2, 1155 + i, 820 + i/2);
			
			g.drawLine(370 - i, 840 - i/2, 370 - i, 820 + i/2);
			g.drawLine(395 + i, 840 - i/2, 395 + i, 820 + i/2);
			
			g.drawLine(1130 - i, 470 - i/2, 1130 - i, 450 + i/2);
			g.drawLine(1155 + i, 470 - i/2, 1155 + i, 450 + i/2);
			
			g.drawLine(370 - i, 470 - i/2, 370 - i, 450 + i/2);
			g.drawLine(395 + i, 470 - i/2, 395 + i, 450 + i/2);
		}
		
		g.setColor(Color.WHITE);
		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 40));
		g2.drawString("Choose Who's Playing", 530, 80);
		g2.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 25));
		
		g2.drawString("Human", 170, 395);
		g2.drawString("Human", 930, 395);
		g2.drawString("Human", 170, 755);
		g2.drawString("Human", 930, 755);
		
		if (AIlvls[0] == 1) {
			g2.drawString("Easy AI", 340, 395);
		} else if (AIlvls[0]  == 2) {
			g2.drawString("Medium AI", 322, 395);
		} else if (AIlvls[0]  == 3) {
			g2.drawString("Hard AI", 335, 395);
		}
		if (AIlvls[1]  == 1) {
			g2.drawString("Easy AI", 1100, 395);
		} else if (AIlvls[1] == 2) {
			g2.drawString("Medium AI", 1082, 395);
		} else if (AIlvls[1] == 3) {
			g2.drawString("Hard AI", 1095, 395);
		}
		if (AIlvls[2] == 1) {
			g2.drawString("Easy AI", 340, 755);
		} else if (AIlvls[2] == 2) {
			g2.drawString("Medium AI", 322, 755);
		} else if (AIlvls[2] == 3) {
			g2.drawString("Hard AI", 335, 755);
		}
		if (AIlvls[3] == 1) {
			g2.drawString("Easy AI", 1100, 755);
		} else if (AIlvls[3] == 2) {
			g2.drawString("Medium AI", 1082, 755);
		} else if (AIlvls[3] == 3) {
			g2.drawString("Hard AI", 1095, 755);
		}
		
		g2.drawString("Non-", 525, 385);
		g2.drawString("Playing", 505, 405);
		g2.drawString("Non-", 1285, 385);
		g2.drawString("Playing", 1265, 405);
		g2.drawString("Non-", 525, 745);
		g2.drawString("Playing", 505, 765);
		g2.drawString("Non-", 1285, 745);
		g2.drawString("Playing", 1265, 765);
		
		g2.drawString("Back", 40, 42);
		g2.drawString("CONFIRM", 715, 835);
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

	public void actionPerformed(ActionEvent e) {}
}



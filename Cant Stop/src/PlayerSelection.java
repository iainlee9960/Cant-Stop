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
	static int area1 = 0;
	static int area2 = 0;
	static int area3 = 0;
	static int area4 = 0;
	String name1 = "";
	String name2 = "";
	String name3 = "";
	String name4 = "";
	int AI1lvl = 1;
	int AI2lvl = 1;
	int AI3lvl = 1;
	int AI4lvl = 1;
	boolean changelvl1 = false;
	boolean changelvl2 = false;
	boolean changelvl3 = false;
	boolean changelvl4 = false;
	Run game;

	Font font = new Font("Copperplate Gothic Bold", Font.BOLD, 55);
	JFormattedTextField textField = new JFormattedTextField("Player1");
	JFormattedTextField textField1 = new JFormattedTextField("Player2");
	JFormattedTextField textField2 = new JFormattedTextField("Player3");
	JFormattedTextField textField3 = new JFormattedTextField("Player4");
	
	PlayerSelection(Run game){
		setBackground(Color.RED);
		setVisible(true);
		textField.addActionListener(this);
		textField.setOpaque(false);
		textField.setBorder(null);
		textField.setFont(font);
		add(textField);
		textField1.addActionListener(this);
		textField1.setOpaque(false);
		textField1.setBorder(null);
		textField1.setFont(font);
		add(textField1);
		textField2.addActionListener(this);
		textField2.setOpaque(false);
		textField2.setBorder(null);
		textField2.setFont(font);
		add(textField2);
		textField3.addActionListener(this);
		textField3.setOpaque(false);
		textField3.setBorder(null);
		textField3.setFont(font);
		add(textField3);
		this.game = game;
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getY() >= 350 && e.getY() <= 430) {
					if ((e.getX() >= 150 )&& (e.getX() <= 150 + oButtonWidth)) {
						area1 = 1;
						repaint();
						//add action
					}
					if (e.getX() >= 320 && e.getX() <= 320 + oButtonWidth) {
						area1 = 2;
						repaint();
						//add action
					}
					if ((e.getX() >= 490) && (e.getX() <= 490 + oButtonWidth)) {
						area1 = 3;
						repaint();
						//add action
					}
					
					if ((e.getX() >= 910 )&& (e.getX() <= 910 + oButtonWidth)) {
						area2 = 1;
						repaint();
						//add action
					}
					if (e.getX() >= 1080 && e.getX() <= 1080 + oButtonWidth) {
						area2 = 2;
						repaint();
						//add action
					}
					if (e.getX() >= 1250 && e.getX() <= 1250 + oButtonWidth) {
						area2 = 3;
						repaint();
						//add action
					}
				}
				
				if (e.getY() >= 710 && e.getY() <= 790) {
					if ((e.getX() >= 150 )&& (e.getX() <= 150 + oButtonWidth)) {
						area3 = 1;
						repaint();
						//add action
					}
					if (e.getX() >= 320 && e.getX() <= 320 + oButtonWidth) {
						area3 = 2;
						repaint();
						//add action
					}
					if (e.getX() >= 490 && e.getX() <= 490 + oButtonWidth) {
						area3 = 3;
						repaint();
						//add action
					}
					
					if ((e.getX() >= 910 )&& (e.getX() <= 910 + oButtonWidth)) {
						area4 = 1;
						repaint();
						//add action
					}
					if (e.getX() >= 1080 && e.getX() <= 1080 + oButtonWidth) {
						area4 = 2;
						repaint();
						//add action
					}
					if (e.getX() >= 1250 && e.getX() <= 1250 + oButtonWidth) {
						area4 = 3;
						repaint();
						//add action
					}
				}
				
				for (int i = 0; i <= 20; i ++) {
					if (changelvl4 == false) {
						if ((e.getX() <= 1130) && (e.getX() >= 1110)) {
							if ((e.getY() <= (840 - i/2)) && (e.getY() >= (820 + i/2))) {
								AI4lvl --;
								changelvl4 = true;
								if (AI4lvl >= 3)
									AI4lvl = 3;
								if (AI4lvl <= 1) 
									AI4lvl = 1;
								repaint();
							}
						}
						if ((e.getX() >= 1155) && (e.getX() <= 1175)) {
							if ((e.getY() <= 840 - i/2) && (e.getY() >= 820 + i/2)) {
								AI4lvl ++;
								changelvl4 = true;
								if (AI4lvl >= 3)
									AI4lvl = 3;
								if (AI4lvl <= 1) 
									AI4lvl = 1;
								repaint();
							}
						}	
					}
				}
				
				for (int i = 0; i <= 20; i ++) {
					if (changelvl3 == false) {
						if ((e.getX() <= 370) && (e.getX() >= 350)) {
							if ((e.getY() <= (840 - i/2)) && (e.getY() >= (820 + i/2))) {
								AI3lvl --;
								changelvl3 = true;
								if (AI3lvl >= 3)
									AI3lvl = 3;
								if (AI3lvl <= 1) 
									AI3lvl = 1;
								repaint();
							}
						}
						if ((e.getX() >= 395) && (e.getX() <= 415)) {
							if ((e.getY() <= 840 - i/2) && (e.getY() >= 820 + i/2)) {
								AI3lvl ++;
								changelvl3 = true;
								if (AI3lvl >= 3)
									AI3lvl = 3;
								if (AI3lvl <= 1) 
									AI3lvl = 1;
								repaint();
							}
						}	
					}
				}
				
				for (int i = 0; i <= 20; i ++) {
					if (changelvl2 == false) {
						if ((e.getX() <= 1130) && (e.getX() >= 1110)) {
							if ((e.getY() <= (470 - i/2)) && (e.getY() >= (450 + i/2))) {
								AI2lvl --;
								changelvl2 = true;
								if (AI2lvl >= 3)
									AI2lvl = 3;
								if (AI2lvl <= 1) 
									AI2lvl = 1;
								repaint();
							}
						}
						if ((e.getX() >= 1155) && (e.getX() <= 1175)) {
							if ((e.getY() <= 470 - i/2) && (e.getY() >= 450 + i/2)) {
								AI2lvl ++;
								changelvl2 = true;
								if (AI2lvl >= 3)
									AI2lvl = 3;
								if (AI2lvl <= 1) 
									AI2lvl = 1;
								repaint();
							}
						}	
					}
				}
					
				for (int i = 0; i <= 20; i ++) {
					if (changelvl1 == false) {
						if ((e.getX() <= 370) && (e.getX() >= 350)) {
							if ((e.getY() <= (470 - i/2)) && (e.getY() >= (450 + i/2))) {
								AI1lvl --;
								changelvl1 = true;
								if (AI1lvl >= 3)
									AI1lvl = 3;
								if (AI1lvl <= 1) 
									AI1lvl = 1;
								repaint();
							}
						}
						if ((e.getX() >= 395) && (e.getX() <= 415)) {
							if ((e.getY() <= 470 - i/2) && (e.getY() >= 450 + i/2)) {
								AI1lvl ++;
								changelvl1 = true;
								if (AI1lvl >= 3)
									AI1lvl = 3;
								if (AI1lvl <= 1) 
									AI1lvl = 1;
								repaint();
							}
						}	
					}
				}
				changelvl1 = false;
				changelvl2 = false;
				changelvl3 = false;
				changelvl4 = false;
				
				if ((e.getX() >= 690) && (e.getX() <= 850)) {
					if ((e.getY() >= 810) && (e.getY() <= 870)) {
						CantStopGameScreen GameScreen= new CantStopGameScreen();
						game.frame.getContentPane().removeAll();
						game.frame.getContentPane().add(GameScreen);
						game.frame.revalidate();
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
	
	public void paintComponent(Graphics g) {
		textField.setBounds(160,180,400,70);
		if (textField.getText().length() == 9) {
			name1 = textField.getText();
		} else if (textField.getText().length() > 9) {
			textField.setText(name1);
		}
		textField1.setBounds(920,180,400,70);
		if (textField1.getText().length() == 9) {
			name2 = textField1.getText();
		} else if (textField1.getText().length() > 9) {
			textField1.setText(name2);
		}
		textField2.setBounds(160,540,400,70);
		if (textField2.getText().length() == 9) {
			name3 = textField2.getText();
		} else if (textField2.getText().length() > 9) {
			textField2.setText(name3);
		}
		textField3.setBounds(920,540,400,70);
		if (textField3.getText().length() == 9) {
			name4 = textField3.getText();
		} else if (textField3.getText().length() > 9) {
			textField3.setText(name4);
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
		
		if (area1 == 1) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(150, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else 
			g.fillRect(150, 350, oButtonWidth, oButtonHeight);
		if(area1 == 2) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(320, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(320, 350, oButtonWidth, oButtonHeight);
		if (area1 == 3) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(490, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(490, 350, oButtonWidth, oButtonHeight);
		
		if (area2 == 1) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(910, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else 
			g.fillRect(910, 350, oButtonWidth, oButtonHeight);
		if(area2 == 2) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(1080, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(1080, 350, oButtonWidth, oButtonHeight);
		if (area2 == 3) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(1250, 350, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(1250, 350, oButtonWidth, oButtonHeight);
		
		
		if (area3 == 1) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(150, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else 
			g.fillRect(150, 710, oButtonWidth, oButtonHeight);
		if(area3 == 2) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(320, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(320, 710, oButtonWidth, oButtonHeight);
		if (area3 == 3) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(490, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(490, 710, oButtonWidth, oButtonHeight);
		
		if (area4 == 1) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(910, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else 
			g.fillRect(910, 710, oButtonWidth, oButtonHeight);
		if(area4 == 2) {
			g.setColor(new Color(25, 240, 240));
			g.fillRect(1080, 710, oButtonWidth, oButtonHeight);
			g.setColor(Color.gray);
		} else
			g.fillRect(1080, 710, oButtonWidth, oButtonHeight);
		if (area4 == 3) {
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
		
		if (AI1lvl == 1) {
			g2.drawString("Easy AI", 340, 395);
		} else if (AI1lvl == 2) {
			g2.drawString("Medium AI", 322, 395);
		} else if (AI1lvl == 3) {
			g2.drawString("Hard AI", 335, 395);
		}
		if (AI2lvl == 1) {
			g2.drawString("Easy AI", 1100, 395);
		} else if (AI2lvl == 2) {
			g2.drawString("Medium AI", 1082, 395);
		} else if (AI2lvl == 3) {
			g2.drawString("Hard AI", 1095, 395);
		}
		if (AI3lvl == 1) {
			g2.drawString("Easy AI", 340, 755);
		} else if (AI3lvl == 2) {
			g2.drawString("Medium AI", 322, 755);
		} else if (AI3lvl == 3) {
			g2.drawString("Hard AI", 335, 755);
		}
		if (AI4lvl == 1) {
			g2.drawString("Easy AI", 1100, 755);
		} else if (AI4lvl == 2) {
			g2.drawString("Medium AI", 1082, 755);
		} else if (AI4lvl == 3) {
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

	public void actionPerformed(ActionEvent arg0) {}
}






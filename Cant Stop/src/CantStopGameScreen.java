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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
	private boolean busted = false;
	private int[][] choices; // SET TO THE LIST OF CHOICES FROM CANTSTOPGAME IN THE PAINTCOMPONENT
	private boolean AIExist = false;
	private CantStopGame game;
	Run screen;
	
	public CantStopGameScreen(boolean AIComparison, CantStopGame newGame, Run screen) {
		this.screen = screen;
		AIExist = AIComparison;
		game = newGame;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		addMouseListener(this);
	}
	// TODO USE isFast() and mostRecentChoice() to get the speed the player wants to
	// run the game at and the most recent choice made.
	public boolean isFast() {
		return isfast;
	}

	public int mostRecentChoice() {
		return mostrecentchoice;
	}
	
	private void handlestop() {
		// TODO PUT CODE HERE TO RUN WHEN STOP BUTTON PRESSED
		if(game.getCurrentPlayer().isHuman()) {
			makingchoice = false;
			//shiftPreviousPieces(); //uses new drawPieces
			//updatePieceArray(false, true); //uses new drawPieces
			//updatePieceArray(false, false); //uses new drawPieces
			game.afterStopPressed();
		}
		this.repaint();
	}

	private void handleroll() {
		// TODO PUT CODE HERE TO RUN WHEN ROLL BUTTON PRESSED
		if(game.getCurrentPlayer().isHuman()) {
			makingchoice = true;
			mostrecentchoice = 99; // OUT OF BOUNDS TO INDICATE NO CHOICE
			game.afterRollPressed();
		}
		this.repaint();
	}

	private void handledone() {
		// TODO PUT CODE HERE TO RUN WHEN DONE BUTTON PRESSED
		if(game.getCurrentPlayer().isHuman()) {
			makingchoice = false;
			//updatePieceArray(false, true); //uses new drawPieces
			game.afterDonePressed(mostRecentChoice());
			//updatePieceArray(true, false); //uses new drawPieces
		}
		this.repaint();
	}
	private void shiftPreviousPieces() {
		int[] locations = game.getCurrentRecord().getNeutralMarkerLocations();
		Color current = game.getCurrentPlayer().getPlayerColor();
		for(int i=0; i<locations.length; i++) {
			if(locations[i]>0) {
				for(int j=0; j<locations[i]; j++) {
					for(int k=0; k<4; k++) {
						if(Pieces[i][j][k]!=null && Pieces[i][j][k].getColor()==current) {
							System.out.println(i+" "+j+" "+k);
							System.out.println(current);
							Pieces[i][j][k] = null;
							for(int y=0; y<3-k; y++) {
								if(Pieces[i][j][k+y+1]!=null) {
									Pieces[i][j][k+y] = Pieces[i][j][k+y+1];
									Pieces[i][j][k+y].subtractStack();
									Pieces[i][j][k+y+1]=null;
								}
							}
						}
						
					}
				}
			}
		}
	}
	private void updatePieceArray(boolean neutralPiece, boolean bust) {
		int[] locations = game.getCurrentRecord().getNeutralMarkerLocations();
		Color current = game.getCurrentPlayer().getPlayerColor();
		for(int i=0; i<locations.length; i++) {
			if(locations[i]>0) {
				for(int j=0; j<4; j++) {
					if(Pieces[i][locations[i]-1][j]==null && !bust) {
						if(neutralPiece) {
							Pieces[i][locations[i]-1][j] = new PlacePiece(Color.WHITE, i+2, locations[i], j+1);
							break;
						} else {
							if(current == Color.orange) {
								Pieces[i][locations[i]-1][j] = new PlacePiece(new Color(255,120,0), i+2, locations[i], j+1);
							} else {
								Pieces[i][locations[i]-1][j] = new PlacePiece(current, i+2, locations[i], j+1);
							}
							break;
						}
					} else if(bust && Pieces[i][locations[i]-1][j]!=null && Pieces[i][locations[i]-1][j].getColor()==Color.WHITE) {
						Pieces[i][locations[i]-1][j] = null;
					} 
				}
			}
		}
	}

	private void handlebust() {
		//TODO PUT CODE HERE TO RUN WHEN PLAYER PRESSES TO CONTINUE AFTER THEY ROLL AND HAVE NO MOVES
		if(game.getCurrentPlayer().isHuman()) {
			makingchoice = false;
			//updatePieceArray(false, true); //uses new drawPieces
			game.resetForNewTurn();
		}
		this.repaint();
	}
	boolean quitConfirm = false;
	private void handlequit() {
		if(!quitConfirm) {
			quitConfirm = true;
		} else {
			MainMenu main= new MainMenu(screen);
			screen.frame.getContentPane().removeAll();
			screen.frame.getContentPane().add(main);
			screen.frame.revalidate();
		}
		this.repaint();
	}
	private void handlecancel() {
		quitConfirm = false;
		this.repaint();
	}
	private void handlesave() {
		SaveGame file = new SaveGame();
		file.saveGame(game, 1);
		System.out.println("save");
	}
	private boolean chooseStep = false;
	private boolean stopStep = false;
	private void handleAIMove() {
		if(isFast()) {
			/*boolean bust = false;
			boolean toContinue = true;
			while (toContinue) {
				game.dice = CantStopGame.roll();
				choices = game.generateChoices(game.dice);
				if (choices.length>0) {
					int choiceNum = game.getCurrentPlayer().chooseDice(game, game.dice, game.choices);
					game.afterDonePressed(choiceNum);
					toContinue = game.getCurrentPlayer().rollAgain(game);
				} else {
					game.getCurrentRecord().removeNeutralMarkers();
					toContinue = false;
					bust = true;
				}
			}
			if(!bust) {
				shiftPreviousPieces();
				updatePieceArray(false, true);
				updatePieceArray(false, false);
				game.endTurnWithoutBust();
			}
			game.resetForNewTurn();*/
			makingchoice = false;
			game.runAITurn();
		} else {
			if(!makingchoice) {
				makingchoice = true;
				mostrecentchoice = 99;
				game.afterRollPressed();
				chooseStep = true;
			} else if(choices!=null && choices.length==0) {
				makingchoice = false;
				chooseStep = false;
				//updatePieceArray(false, true); //uses new drawPieces
				game.resetForNewTurn();
			} else {
				if(chooseStep) {
					choices = game.choices;
					mostrecentchoice = game.getCurrentPlayer().chooseDice(game, game.dice, choices);
					//updatePieceArray(false, true); //uses new drawPieces
					game.afterDonePressed(mostRecentChoice());
					//updatePieceArray(true, false); //uses new drawPieces
					chooseStep = false;
					stopStep = true;
				} else if(stopStep) {
					if(game.getCurrentPlayer().rollAgain(game)) {
						mostrecentchoice = 99;
						game.afterRollPressed();
						chooseStep = true;
						stopStep = false;
					} else {
						makingchoice = false;
						chooseStep = false;
						stopStep = false;
						//shiftPreviousPieces(); //uses new drawPieces
						//updatePieceArray(false, true); //uses new drawPieces
						//updatePieceArray(false, false); //uses new drawPieces
						game.afterStopPressed();
					}
				}
			}
		}
		this.repaint();
	}
	// Determine which button is pressed
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		//System.out.println("("+e.getX()+","+e.getY()+")");
		if(game.checkGameOver()==null) {	
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
			} else if(in(1150, 830, 1435, 895)) {
				if(game.getCurrentPlayer().isHuman()) {
					if(in(1310, 830, 1435, 895)) {
						if(makingchoice) {
							if(mostrecentchoice < 7) { //MAKE SURE A BUTTON HAS BEEN PRESSED AND NOT AT DEFAULT 99 VALUE
								handledone();
							} else if(busted) {
								handlebust();
							}
						} else {
							handleroll();
						}
					} else if (in(1150, 830, 1275, 895)) {
						if(!makingchoice) {
							handlestop();
						}
					} 
				} else {
					handleAIMove();
				}
			} else if(in(e, 20, 20, 120, 68)) {
				handlequit();
			} else if(in(e, 140, 20, 240, 68)) {
				handlesave();
			} else if(in(e, 510, 540, 640, 590)) {
				if(quitConfirm) {
					handlequit();
				}
			} else if(in(e, 960, 540, 1090, 590)) {
				handlecancel();
			}
		}
	}
	private boolean in(MouseEvent e, int x1, int y1, int x2, int y2) {
		if ((e.getX() > x1 && e.getX() < x2 || e.getX() > x2 && e.getX() < x1)
				&& (e.getY() > y1 && e.getY() < y2 || e.getY() > y2 && e.getY() < y1)) {
			return true;
		} else {
			return false;
		}
	}
	private boolean in(int x1, int y1, int x2, int y2) {
		Point mousepoint = MouseInfo.getPointerInfo().getLocation();
		if ((mousepoint.x > x1 && mousepoint.x < x2 || mousepoint.x > x2 && mousepoint.x < x1)
				&& (mousepoint.y > y1 && mousepoint.y < y2 || mousepoint.y > y2 && mousepoint.y < y1)) {
			return true;
		} else {
			return false;
		}
	}

	private void handlechoice(int tohandle) {
		if (game.getCurrentPlayer().isHuman()&&makingchoice && tohandle < choices.length) {
			mostrecentchoice = tohandle;
			this.repaint();
		}
	}

	private void handlespeed(boolean fast) {
		if(AIExist) {
			isfast = fast;
			this.repaint();
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//draw background
		g2d.setColor(new Color(255, 216, 176));
		g2d.fillRect(0, 0, 1600, 900);
		//draw grid
		Image grid = new ImageIcon("images/grid.png").getImage();
		g2d.drawImage(grid, 40, 60, this);
		drawQuitButton(g2d);
		drawSaveButton(g2d);
		drawCurrentPlayer(g2d);
		if(game.getCurrentPlayer().isHuman() || !isFast()) {
			drawChoices(g,g2d);
		} 
		if(!game.getCurrentPlayer().isHuman()){
			drawAITurnButton(g2d);
		}
		drawAIRadioButtons(g2d);
		drawDice(g2d);
		drawPieces(g2d);
		drawWinnerScreen(g2d);
		drawQuitConfirmScreen(g2d);
	}
	private void drawQuitConfirmScreen(Graphics g2d) {
		if(quitConfirm) {
			g2d.setColor(new Color(100,100,100 ));
			g2d.fillRect(500, 300, 600, 300);
			
			g2d.setColor(new Color(0, 155, 20));
			g2d.fillRect(510, 540, 130, 50);
			g2d.setColor(new Color(171, 17, 4));
			g2d.fillRect(960, 540, 130, 50);
			
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 55));
			g2d.drawString("Are you sure", 590, 390);
			g2d.drawString("you want to quit?", 530, 480);

			g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 25));
			g2d.drawString("Confirm", 516, 573);
			g2d.drawString("Cancel", 973, 573);
		}
	}
	private void drawAITurnButton(Graphics g2d) {
		if(isFast()) {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(1150, 810, 285, 60);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(1150, 810, 285, 60);
			g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 35));
			g2d.drawString("Run AIs Turn", 1165, 852);
		} else {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(1150, 810, 285, 60);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(1150, 810, 285, 60);
			g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 40));
			g2d.drawString("Next", 1235, 855);
		}
		
	}
	private void drawCurrentPlayer(Graphics g2d) {
		Color currentColor = game.getPlayerOrder()[game.getCurrentPlayerIndex()].getPlayerColor();
		g2d.setColor(currentColor); // THE COLOR OF THE PLAYER WHO IS GOING AT THE MOMENT
		g2d.fillRect(1100, 150, 400, 90);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(1100, 150, 400, 90);
		g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 50));
		String currentPlayer = game.getPlayerOrder()[game.getCurrentPlayerIndex()].getPlayerName();
		g2d.drawString(currentPlayer, 1110, 210); // THE NAME OF THE PLAYER WHO IS GOING AT THE MOMENT
	}
	private void drawWinnerScreen(Graphics g2d) {
		if(!(game.getWinner()==null)) {
			int red = game.getWinner().getPlayerColor().getRed();
			int green = game.getWinner().getPlayerColor().getGreen();
			int blue = game.getWinner().getPlayerColor().getBlue();
			g2d.setColor(new Color(red,green,blue,170));
			g2d.fillRect(300, 200, 1000, 500);
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 150));	
			String winner = game.getWinner().getPlayerName();
			g2d.drawString(winner, 800-(winner.length()*55), 400);
			g2d.drawString("WINS", 540, 590);
		}
	}
	PlacePiece[][][] Pieces={{{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null}},
							{{null,null,null,null},{null,null,null,null},{null,null,null,null}}};
	private void drawPieces(Graphics g2d, boolean real) {
		for(int i=0; i<11; i++) {
			int rowLength = Pieces[i].length;
			for(int j=rowLength-1; j>=0; j--) {
				for(int y=0; y<4; y++) {
					if(Pieces[i][j][y]!=null) {
						Pieces[i][j][y].draw(g2d);
					}
				}
			}
		}
		for(int i=0; i<game.getNumPlayers(); i++) {
			for(int y=0; y<3; y++) {
				if(game.getPlayer(i).getPlayerColor()==Color.orange) {
					new PlacePiece(new Color(255,120,0), 13+i, y+1, 1, true, g2d);
				} else {
					new PlacePiece(game.getPlayer(i).getPlayerColor(), 13+i, y+1, 1, true, g2d);
				}
			}
		}
	}
	private void drawPieces(Graphics g2d) {
		ArrayList<PlacePiece> pieces = new ArrayList<PlacePiece>();
		for(int i=0; i<game.getNumPlayers(); i++) {
			int[] locations = game.getPlayer(i).getRecord().getPieceLocations();
			//test 
			/*for(int j=0; j<locations.length; j++) {
				if(locations[j]>0) {
					PlacePiece temp = new PlacePiece(game.getPlayer(i).getPlayerColor(), j+2, locations[j], 1, false, g2d);
					int numStack = 1, index = 0, set = 9;
					for(PlacePiece piece: pieces) {
						if(temp.equals(piece)) {
							set = index;
							numStack++;
						}
						index++;
					}
					if(game.getPlayer(i).getPlayerColor()==Color.orange) {
						if(numStack>1) {
							pieces.add(set+2-numStack, new PlacePiece(new Color(255,120,0), j+2, locations[j], numStack, false, g2d));
						} else {
							pieces.add(new PlacePiece(new Color(255,120,0), j+2, locations[j], numStack, false, g2d));
						}
					} else {
						if(numStack>1) {
							pieces.add(set+2-numStack, new PlacePiece(game.getPlayer(i).getPlayerColor(), j+2, locations[j], numStack, false, g2d));
						} else {
							pieces.add(new PlacePiece(game.getPlayer(i).getPlayerColor(), j+2, locations[j], numStack, false, g2d));
						}
					}
				}
			}*/
			//test
			
			for(int j=0; j<locations.length; j++) {
				if(locations[j]>0) {
					PlacePiece temp = new PlacePiece(game.getPlayer(i).getPlayerColor(), j+2, locations[j], 1, false, g2d);
					int numStack = 1;
					for(PlacePiece piece: pieces) {
						if(temp.equals(piece)) {
							numStack++;
						}
					}
					if(game.getPlayer(i).getPlayerColor()==Color.orange) {
						pieces.add(new PlacePiece(new Color(255,120,0), j+2, locations[j], numStack, true, g2d));
					} else {
						pieces.add(new PlacePiece(game.getPlayer(i).getPlayerColor(), j+2, locations[j], numStack, true, g2d));
					}
				}
			}
		}
		
		int[] whitePieces = game.getCurrentRecord().getNeutralMarkerLocations();
		for(int i=0; i<whitePieces.length; i++) {
			if(whitePieces[i]>0) {
				PlacePiece temp = new PlacePiece(Color.white, i+2, whitePieces[i], 1, false, g2d);
				int numStack = 1;
				for(PlacePiece piece: pieces) {
					if(temp.equals(piece)) {
						numStack++;
					}
				}
				pieces.add(new PlacePiece(Color.white, i+2, whitePieces[i], numStack, true, g2d));
			}
		}
		//test
		/*for(int i=0; i<whitePieces.length; i++) {
			if(whitePieces[i]>0) {
				PlacePiece temp = new PlacePiece(Color.white, i+2, whitePieces[i], 1, false, g2d);
				int numStack = 1, index = 0, set = 9;
				for(PlacePiece piece: pieces) {
					if(temp.equals(piece)) {
						set = index;
						numStack++;
					}
					index++;
				}
				if(numStack>1) {
					pieces.add(set+2-numStack, new PlacePiece(Color.white, i+2, whitePieces[i], numStack, false, g2d));
				} else {
					pieces.add(new PlacePiece(Color.white, i+2, whitePieces[i], numStack, false, g2d));
				}
			}
		}
		for(int i=pieces.size()-1; i>=0; i--) {
			pieces.get(i).draw(g2d);
		}*/
		//test
		for(int i=0; i<game.getNumPlayers(); i++) {
			for(int y=0; y<3; y++) {
				if(game.getPlayer(i).getPlayerColor()==Color.orange) {
					new PlacePiece(new Color(255,120,0), 13+i, y+1, 1, true, g2d);
				} else {
					new PlacePiece(game.getPlayer(i).getPlayerColor(), 13+i, y+1, 1, true, g2d);
				}
			}
		}
	}
	private void drawChoices(Graphics g, Graphics g2d) {
		int[][] choicesimport = game.getChoices();
		choices = choicesimport;
		// If the player is presented with choices, display them. Otherwise, draw the
		// "Stop" and "Roll" buttons.
		if(makingchoice) {
			if((choices.length == 0)) {
				g2d.setColor(Color.BLACK);
				g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 30));
				g2d.drawString("You Busted!", 1156, 432);
				g2d.setColor(Color.WHITE);
				g2d.fillRect(1310, 810, 125, 60);
				g2d.setColor(Color.BLACK);
				g2d.drawRect(1310, 810, 125, 60);
				g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 32));
				g2d.drawString("DONE", 1320, 852);
				busted = true;
			} else {
				busted = false;
				g2d.setColor(Color.BLACK);
				//g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 25));
				if(game.getCurrentPlayer().isHuman()) {
					g2d.drawString("Click to Select:", 1156, 432);
				}
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
					g2d.setColor(Color.WHITE);
					g2d.fillRect(1310, 810, 125, 60);
					g2d.setColor(Color.BLACK);
					g2d.drawRect(1310, 810, 125, 60);
					g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 32));
					g2d.drawString("DONE", 1320, 852);
				}
			}
		} else if(game.getCurrentPlayer().isHuman()) { //If there are no choices, let the player end their turn or roll again
			g2d.setColor(Color.WHITE);
			g2d.fillRect(1150, 810, 125, 60);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(1150, 810, 125, 60);
			g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 35));
			g2d.drawString("STOP", 1161, 853);

			g2d.setColor(Color.WHITE);
			g2d.fillRect(1310, 810, 125, 60);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(1310, 810, 125, 60);
			g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 35));
			g2d.drawString("ROLL", 1321, 853);
		}
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
	private Graphics drawbutton(Graphics g2d, int x, int y, int w, int h, String text, boolean highlighted) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(x, y, w, h);
		if (highlighted) {
			g2d.setColor(Color.YELLOW);
		} else {
			g2d.setColor(Color.RED);
		}
		g2d.fillRect(x + 2, y + 2, w - 4, h - 4);
		g2d.setColor(Color.BLACK);
		g2d.drawString(text, x + ((w - (text.length() * 15)) / 2), y + 65);
		return g2d;
	}
	private void drawAIRadioButtons(Graphics g2d) {
		if(AIExist) {
			if (!isfast) {
				g2d.setColor(Color.YELLOW);
			} else {
				g2d.setColor(Color.GRAY);
			}
			g2d.fillOval(1450, 10, 15, 15);
			if (isfast) {
				g2d.setColor(Color.YELLOW);
			} else {
				g2d.setColor(Color.GRAY);
			}
			g2d.fillOval(1450, 40, 15, 15);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(1450, 10, 15, 15);
			g2d.drawOval(1450, 40, 15, 15);

			g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
			g2d.drawString("SLOW", 1470, 25);
			g2d.drawString("FAST", 1470, 55);
		}
	}
	private void drawQuitButton(Graphics g2d) {
		g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 30));
		g2d.setColor(Color.WHITE);
		g2d.fillRect(20, 20, 100, 48);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(20, 20, 100, 48);
		g2d.drawString("QUIT", 30, 55);
	}
	private void drawSaveButton(Graphics g2d) {
		g2d.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 28));
		g2d.setColor(Color.WHITE);
		g2d.fillRect(140, 20, 100, 48);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(140, 20, 100, 48);
		g2d.drawString("SAVE", 147, 55);
	}
	private void drawDice(Graphics g) {
		Image d1 = new ImageIcon("images/die1.gif").getImage();
		Image d2 = new ImageIcon("images/die2.gif").getImage();
		Image d3 = new ImageIcon("images/die3.gif").getImage();
		Image d4 = new ImageIcon("images/die4.gif").getImage();
		Image d5 = new ImageIcon("images/die5.gif").getImage();
		Image d6 = new ImageIcon("images/die6.gif").getImage();
		int initial = 1125;
		//int[] dice = CantStopGame.roll();
		int[] dice = game.getDice();
		//int[] dice = { 4, 2, 3, 6 };
		if(dice != null) {
			for (int i = 0; i < 4; i++) {
				switch (dice[i]) {
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
}








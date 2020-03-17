
public class CantStopGame {
	private CantStopPlayer[] playerOrder;
	private int currentPlayerIndex;
	private CantStopPlayer[] completedColumns = new CantStopPlayer[11];
	public final static int[] LENGTHS = { 3, 5, 7, 9, 11, 13, 11, 9, 7, 5, 3 };
	int[] dice;
	int[][] choices;

	public CantStopGame(CantStopGame toBeCopied) {
		playerOrder = toBeCopied.getPlayerOrder();
		currentPlayerIndex = toBeCopied.getCurrentPlayerIndex();
		for (int i = 0; i < toBeCopied.getNumPlayers(); i++) {
			completedColumns[i] = toBeCopied.whoCompletedColumn(i);
		}
	}

	public CantStopGame(CantStopPlayer[] players) {
		//completedColumns = new CantStopPlayer[players.length];
		playerOrder = players;
		for (CantStopPlayer p : playerOrder) {
			p.setRecord();
		}
	}
	public CantStopGame(CantStopPlayer[] playerOrder, int currentPlayerIndex, CantStopPlayer[] completedColumns) {
		this.playerOrder = playerOrder;
		this.currentPlayerIndex = currentPlayerIndex;
		this.completedColumns = completedColumns;
	}
	public int runGame() {
		//CantStopPlayer winner = new ComputerPlayer();
		boolean gameOver = false;
		while(!gameOver) {
			for(int i=0; i<getNumPlayers(); i++) {
				int current = getCurrentPlayerIndex();
				runAITurn();
				if(getWinner()!=null) {
					gameOver = true;
					return current;
				}
			}
		}
		return getCurrentPlayerIndex();
	}
	public CantStopPlayer[] getPlayerOrder() {
		return playerOrder;
	}
	public int getNumPlayers() {
		return playerOrder.length;
	}
	public int[] getDice () {
		return dice;
	}
	public int[][] getChoices() {
		return choices;
	}
	public CantStopPlayer getPlayer(int num) {
		return playerOrder[num];
	}
	public CantStopPlayer getCurrentPlayer() {
		return getPlayer(getCurrentPlayerIndex());
	}
	public PlayerRecord getCurrentRecord() {
		return getCurrentPlayer().getRecord();
	}
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}
	public CantStopPlayer whoCompletedColumn(int colNum) {
		return completedColumns[colNum];
	}
	public void columnCompleted(int colNum) {
		completedColumns[colNum] = getCurrentPlayer();
		for(CantStopPlayer player : playerOrder) {
			if(player != playerOrder[currentPlayerIndex]) {
				for(int i = 0; i < player.getRecord().getPieceLocations().length; i++) {
					player.getRecord().getPieceLocations()[colNum] = 0;
				}
			}
		}
	}
	public CantStopPlayer getWinner () {
		return checkGameOver();
	}
	public CantStopPlayer checkGameOver () {
		int cols = 0;
		for (int i=0; i<playerOrder.length; i++) {
			for (int j=0; j<completedColumns.length; j++) {
				if (completedColumns[j] == playerOrder[i]) {
					cols++;
				}
				if (cols>=3) {
					return playerOrder[i];
				}
			}
			cols = 0;
		}
		return null;
	}

	public void resetForNewTurn() {
		currentPlayerIndex++;
		if (currentPlayerIndex >= playerOrder.length)
			currentPlayerIndex = 0;
		PlayerRecord currRecord = playerOrder[currentPlayerIndex].getRecord();
		currRecord.setNumNeutralLeft();
		currRecord.removeNeutralMarkers();
		dice = new int[4];
	}
	public static int[] roll() {
		int[] ranDice = new int[4];
		for(int i = 0; i<=3; i++) {
			ranDice[i] = (int)(Math.random()*6)+1; 
			//control dice
			/*switch(i) {
			case 0:	ranDice[i] = 3; break;
			case 1: ranDice[i] = 4; break;
			case 2: ranDice[i] = 6; break;
			case 3: ranDice[i] = 2; break;
			}*/
		}
		return ranDice;
	}
	
	public void runAITurn () {
		//Rolls and gives the player a choice, informs them that there is only one option, or displays that the player has busted. 
		//Asks the player whether they want to continue rolling (if not already busted) and repeat the process until the player declines or busts. 
		//signal that dice should be drawn
		//public methods in this class to access whether there is currently a choice to be made
		boolean bust = false;
		boolean toContinue = true;
		while (toContinue) {
			dice = roll();
			choices = generateChoices(dice);
			if (choices.length>0) {
				int choiceNum = getCurrentPlayer().chooseDice(this, dice, choices);
				afterDonePressed(choiceNum);
				toContinue = getCurrentPlayer().rollAgain(this);
				//signal that choices should be drawn (actually called from paintComponent)
			} else {
				getCurrentRecord().removeNeutralMarkers();
				//signal that showBust should be called
				toContinue = false;
				bust = true;
			}
		}
		if(!bust) {
			endTurnWithoutBust();
		}
		resetForNewTurn();
	}
	public void afterRollPressed () {
		//human players
		//method in CantStopGameScreen will call this and then call a method in CantStopGameScreen displaying the choices panel
		//have the graphics method get the choices array and if not length zero drawPanel. If length zero showBust
		dice = roll();
		choices = generateChoices(dice);
		if (choices.length == 0) {
			getCurrentRecord().removeNeutralMarkers();
			//new turn
		}
	}
	public void afterDonePressed(int choice) {
		int[] locations = getCurrentRecord().getNeutralMarkerLocations();
		if(choices!=null) {
			for(int i=0; i<choices[choice].length; i++) {
				if(locations[choices[choice][i]-2]==0) {
					int current = getCurrentRecord().getPieceLocations()[choices[choice][i]-2];
					locations[choices[choice][i]-2] = current+1;
				} else {
					locations[choices[choice][i]-2]++;
				}
			}
		}
		getCurrentRecord().setNeutralLocations(locations);
	}
	public void afterChoosePressed () {
		//graphics method repaints for neutral marker and shows roll and stop buttons, disables choice panel
		int choiceNum = getCurrentPlayer().chooseDice(this, dice, choices); //choose Dice in human player will be specialized to get the most recent choice from CantStopGameScreen
		int[] choice = choices[choiceNum];
		for (int i=0; i<choice.length; i++) {
			placeNeutralMarker(choice[i]);
		}
	}
	public void afterStopPressed() {
		//either after player chooses to stop their turn or clicks the screen after an AI turn or a bust //but already have endTurnWithoutBust so idk, 
		//just switch to next player and call resetForNewTurn and idk what else
		endTurnWithoutBust();
		resetForNewTurn();
	}
	public void placeNeutralMarker (int col) {

		PlayerRecord record = getCurrentRecord();
		CantStopPlayer player = getCurrentPlayer();
		int numNeutralLeft = record.getNumNeutralLeft();
		int[] neutralLocations = record.getNeutralMarkerLocations();
		int[] pieceLocations = record.getPieceLocations();

		if(numNeutralLeft>0 && neutralLocations[col]<LENGTHS[col] && completedColumns[col]!=player) {
			if(neutralLocations[col] == 0 && pieceLocations[col]==0) {
				neutralLocations[col]=1;
			}else if(neutralLocations[col]!=0) {
				neutralLocations[col] =+ 1;
			}else if(neutralLocations[col]==0 && pieceLocations[col]!=0) {
				neutralLocations[col]=pieceLocations[col]+1;
			}
			numNeutralLeft = numNeutralLeft -1;
		}
		//locationOfNeutralMarkers[col]=neutralLocations[col];

	}


	public void endTurnWithoutBust () {
		PlayerRecord tempRecord = getCurrentRecord();
		tempRecord.setNumNeutralLeft();
		tempRecord.addPermanentMarkers();
		tempRecord.removeNeutralMarkers();
		for (int i=0; i<LENGTHS.length; i++) {
			if (tempRecord.getPieceLocations()[i] == LENGTHS[i] && completedColumns[i] == null) {
				columnCompleted(i);
			}
		}
	}
	public int[][] generateChoices(int[] rolledDice) {
		int[][] returnVal = new int[30][];
		int current = 0;
		for (int y = 1; y < 4; y++) {
			int sum = rolledDice[0] + rolledDice[y];
			int sum2 = rolledDice[3] + rolledDice[3 - y];
			if(y==3) {
				sum2 = rolledDice[1]+rolledDice[2];
			}
			if (getCurrentRecord().getNumNeutralLeft() == 1 && sum!=sum2) {
				//makes sure both don't rely on the last neutral marker
				if(canPlace(sum)&&canPlace(sum2)) {
					boolean both = false;
					for(int i=0; i<getCurrentRecord().getNeutralMarkerLocations().length; i++) {
						if (getCurrentRecord().getNeutralMarkerLocations()[i]>0 && i+2 == sum) {
							if(!(getCurrentRecord().getNeutralMarkerLocations()[i]==LENGTHS[i])) {
								both = true;
							} 
						}
						if (getCurrentRecord().getNeutralMarkerLocations()[i]>0 && i+2 == sum2) {
							if(!(getCurrentRecord().getNeutralMarkerLocations()[i]==LENGTHS[i])) {
								both = true;
							} 
						}
					}
					if(both) {
						returnVal[current] = new int[2];
						returnVal[current][0] = sum;
						returnVal[current][1] = sum2;
						current++;
					} else {
						returnVal[current] = new int[1];
						returnVal[current][0] = sum;
						current++;
						returnVal[current] = new int[1];
						returnVal[current][0] = sum2;
						current++;
					}
				} else if(canPlace(sum)){
					returnVal[current] = new int[1];
					returnVal[current][0] = sum;
					current++;
				} else if(canPlace(sum2)) {
					returnVal[current] = new int[1];
					returnVal[current][0] = sum2;
					current++;
				}
			} else if (canPlace(sum)) {
				if (canPlace(sum2)) {
					if(sum==sum2) {
						if(getCurrentRecord().getNeutralMarkerLocations()[sum-2]==LENGTHS[sum-2]-1 ||
								getCurrentRecord().getPieceLocations()[sum-2]==LENGTHS[sum-2]-1) {
							returnVal[current] = new int[1];
							returnVal[current][0] = sum;
							current++;
						} else {
							returnVal[current] = new int[2];
							returnVal[current][0] = sum;
							returnVal[current][1] = sum2;
							current++;
						}
					} else {
						returnVal[current] = new int[2];
						returnVal[current][0] = sum;
						returnVal[current][1] = sum2;
						current++;
					}
				} else {
					returnVal[current] = new int[1];
					returnVal[current][0] = sum;
					current++;
				}
			} else if(canPlace(sum2)) {
				returnVal[current] = new int[1];
				returnVal[current][0] = sum2;
				current++;
			}
		}
		//		add to new array of correct size
		int length = 0;
		if(returnVal[0]==null) {
			int[][] noChoices = new int[0][];
			return noChoices;
		}
		for(int i=0; i<returnVal.length; i++) {
			if(!(returnVal[i]==null)) {
				length++;
			} 
		}
		int[][] choices = new int[length][];
		for(int i = 0; i<choices.length; i++) {
			choices[i] = returnVal[i];
		}

		//		remove duplicates
		int[][] finalArray = removeDuplicates(choices);
		return finalArray;
	}

	private boolean canPlace(int sum) {
		if (getCurrentRecord().getNumNeutralLeft() < 3) {
			for(int i=0; i<getCurrentRecord().getNeutralMarkerLocations().length; i++) {
				if(getCurrentRecord().getNeutralMarkerLocations()[i]>0 && i+2==sum) {
					if(getCurrentRecord().getNeutralMarkerLocations()[i]==LENGTHS[i]) {
						return false;
					}
					return true;
				}
			}
		}
		if(getCurrentRecord().getNumNeutralLeft()==0) {
			return false;
		} 
		for (int i = 0; i < completedColumns.length; i++) {
			if (completedColumns[i] != null) {
				if (sum == i + 2) {
					return false;
				}
			}
		}
		return true;
	}
	private static int[][] removeDuplicates(int[][] arr) {
		int end = arr.length;
		for (int i = 0; i < end; i++) {
			for (int j = i + 1; j < end; j++) {
				if (arr[i].length==arr[j].length&&arr[i].length == 1) {
					if (arr[i][0] == arr[j][0]) {
						arr = removeTheElement(arr, j);
						end--;
						j--;
					}
				} else if (arr[i].length==arr[j].length&&arr[i].length== 2) {
					if ((arr[i][0] == arr[j][0] && arr[i][1] == arr[j][1])||(arr[i][0] == arr[j][1] && arr[i][0] == arr[j][1])) {
						arr = removeTheElement(arr, j);
						end--;
						j--;
					}
				}
			}
		}
		int[][] whitelist = new int[end][];
		System.arraycopy(arr, 0, whitelist, 0, end);
		return whitelist;
	}
	private static int[][] removeTheElement(int[][] arr, int index) {
		if (arr == null || index < 0 || index >= arr.length) {
			return arr;
		}
		int[][] anotherArray = new int[arr.length - 1][];
		for (int i = 0, k = 0; i < arr.length; i++) {
			if (i == index) {
				continue;
			}
			anotherArray[k++] = arr[i];
		}
		return anotherArray;
	}
}









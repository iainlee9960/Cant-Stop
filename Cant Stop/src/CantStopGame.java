
public class CantStopGame {
	private CantStopPlayer[] playerOrder;
	private int currentPlayerIndex;
	private CantStopPlayer[] completedColumns;
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
		completedColumns = new CantStopPlayer[players.length];
		playerOrder = players;
		for (CantStopPlayer p : playerOrder) {
			p.setRecord();
		}
	}
	public CantStopGame(CantStopPlayer[] players, CantStopPlayer currentPlayer, int[][] locationOfAllPiecies,
			int[] locationOfNeutralMarkers, int numNeutralLeft, CantStopPlayer[] completedCols) {

		//needs to be done still

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

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}
	public CantStopPlayer whoCompletedColumn(int colNum) {
		return completedColumns[colNum];
	}
	public void columnCompleted(int colNum) {
		completedColumns[colNum] = playerOrder[currentPlayerIndex];
		playerOrder[currentPlayerIndex].addCompleted();
		for(CantStopPlayer player : playerOrder) {
			if(player != playerOrder[currentPlayerIndex]) {
				for(int i = 0; i < player.getRecord().getPieceLocations().length; i++) 
				{
					player.getRecord().getPieceLocations()[colNum] = 0;
				}
			}
		}
	}


	public void resetForNewTurn() {
		currentPlayerIndex++;
		if (currentPlayerIndex > playerOrder.length)
			currentPlayerIndex = 0;
		PlayerRecord currRecord = playerOrder[currentPlayerIndex].getRecord();
		currRecord.setNumNeutralLeft();
		currRecord.removeNeutralMarkers();
	}
	public static int[] roll() {
		int[] ranDice = new int[4];
		for(int i = 0; i<=3; i++) {
			ranDice[i] = (int)(Math.random()*6)+1; 
		}
		return ranDice;
	}
	public int[][] generateChoices(int[] rolledDice) {
		int[][] returnVal = new int[6][];
		int current = 0;
		
		for (int y = 1; y < 4; y++) {
			int sum = rolledDice[0] + rolledDice[y];
			int sum2 = rolledDice[3] + rolledDice[3 - y];
			if(y==3) {
				sum2 = rolledDice[1]+rolledDice[2];
			}
			if (playerOrder[currentPlayerIndex].getRecord().getNumNeutralLeft() == 1&&sum!=sum2) {
				//makes sure both don't rely on the last neutral marker
				if(canPlace(sum)&&canPlace(sum2)) {
					boolean both = false;
					for (int index :  playerOrder[currentPlayerIndex].getRecord().getNeutralMarkerLocations()) {
						if (index == sum) {
							both = true;
						}
						if (index == sum2) {
							both = true;
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
				}
			}
			if (canPlace(sum)) {
				if (canPlace(sum2)) {
					returnVal[current] = new int[2];
					returnVal[current][0] = sum;
					returnVal[current][1] = sum2;
					current++;
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
			int[][] noChoices = new int[1][];
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

	public boolean canPlace(int sum) {
		if (playerOrder[currentPlayerIndex].getRecord().getNumNeutralLeft() < 3) {
			for (int index : playerOrder[currentPlayerIndex].getRecord().getNeutralMarkerLocations()) {
				if (index == sum) {
					return true;
				}
			}
		} else if(playerOrder[currentPlayerIndex].getRecord().getNumNeutralLeft()==0) {
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
	public static int[][] removeDuplicates(int[][] arr) {
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
	public static int[][] removeTheElement(int[][] arr, int index) {
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



	void runTurn () {
		//Rolls and gives the player a choice, informs them that there is only one option, or displays that the player has busted. 
		//Asks the player whether they want to continue rolling (if not already busted) and repeat the process until the player declines or busts. 
		dice = roll();
		//signal that dice should be drawn
		//public methods in this class to access whether there is currently a choice to be made
		choices = generateChoices(dice);
		boolean toContinue = true;
		while (toContinue) {
			if (choices.length>0) {
				int choiceNum = playerOrder[currentPlayerIndex].chooseDice(this, dice, choices);
				toContinue = playerOrder[currentPlayerIndex].rollAgain(this);
				//signal that choices should be drawn (actually called from paintComponent)
			}
			else {
				playerOrder[currentPlayerIndex].getRecord().removeNeutralMarkers();
				//signal that showBust should be called
				toContinue = false;
			}
		}
	}
	public void afterRollPressed () {
		//human players
		//method in CantStopGameScreen will call this and then call a method in CantStopGameScreen displaying the choices panel
		//have the graphics method get the choices array and if not length zero drawPanel. If length zero showBust
		dice = roll();
		choices = generateChoices(dice);
		if (choices.length == 0) {
			playerOrder[currentPlayerIndex].getRecord().removeNeutralMarkers();
			//new turn
		}
	}
	public void afterChoosePressed () {
		//graphics method repaints for neutral marker and shows roll and stop buttons, disables choice panel
		int choiceNum = playerOrder[currentPlayerIndex].chooseDice(this, dice, choices); //choose Dice in human player will be specialized to get the most recent choice from CantStopGameScreen
		int[] choice = choices[choiceNum];
		for (int i=0; i<choice.length; i++) {
			placeNeutralMarker(choice[i]);
		}
	}
	public void afterDonePressed() {
		//either after player chooses to stop their turn or clicks the screen after an AI turn or a bust //but already have endTurnWithoutBust so idk, 
		//just switch to next player and call resetForNewTurn and idk what else

	}
	public void placeNeutralMarker (int col) {
		//Takes a neutral marker away from the current player, and updates the neutral markers array. 
		//If there is one of the player’s permanent markers in the column but no neutral marker, place the neutral marker one above that. 
		//If there is currently a neutral marker in the column, remove it and place a neutral marker one above it. 
		//If there are no neutral markers in the column and none of the player’s pieces, place it at index 0.
		//CantStopPlayer[] players, CantStopPlayer currentPlayer, int[][] locationOfAllPiecies,
		//int[] locationOfNeutralMarkers, int numNeutralLeft, CantStopPlayer[] completedCols
		boolean update;
		int  x= currentPlayer.getPieceLocations();
		if(numNeutralLeft>0) {
			
			if(locationOfNeutralMarkers[col] == 0 && ) {
				locationOfNeutralMarkers[col]=1;
			}else if(locationOfNeutralMarkers[col]==) {

			}
			numNeutralLeft = numNeutralLeft -1;
		}

		
	}


	public void endTurnWithoutBust () {
		PlayerRecord tempRecord = playerOrder[currentPlayerIndex].getRecord();
		tempRecord.setNumNeutralLeft();
		tempRecord.addPermanentMarkers();
		tempRecord.removeNeutralMarkers();
		for (int i=0; i<LENGTHS.length; i++) {
			if (tempRecord.getPieceLocations()[i] == LENGTHS[i] && completedColumns[i] == null) {
				columnCompleted(i);
			}
		}
	}
}







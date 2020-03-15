
public class SimpleStrategy extends Strategy {
	int counter = 0;
	public int chooseDice(CantStopGame game, int[] rolled, int[][]choices) {
		int[] locations = game.getPlayer(game.getCurrentPlayerIndex()).getRecord().getPieceLocations();
		int[] whitelocations = game.getPlayer(game.getCurrentPlayerIndex()).getRecord().getNeutralMarkerLocations();
		
		int besti=-1;
		int bestdistance=20;
		for (int i=0; i<(choices.length); i++) {
			for (int j=0; j<choices[i].length; j++) {
				//System.out.println("I,J:"+i+","+j+". choices[i][j]: "+choices[i][j]+". whitelocations of: "+whitelocations[choices[i][j]-2]+". Locations of: "+locations[choices[i][j]-2]);
				if (whitelocations[choices[i][j]-2]!=0) {
					if (determineDistance(choices[i][j]-2, whitelocations[choices[i][j]-2])<bestdistance) {
						bestdistance=determineDistance(choices[i][j]-2, whitelocations[choices[i][j]-2]);
						besti=i;
						//System.out.println("white new besti: "+besti);
					}
				}
				if (locations[choices[i][j]-2]!=0) {
					if (determineDistance(choices[i][j]-2, locations[choices[i][j]-2])<bestdistance) {
						bestdistance=determineDistance(choices[i][j]-2, locations[choices[i][j]-2]);
						besti=i;
						//System.out.println("locations new besti: "+besti);
					}
				}
			}
		}
		if (besti!=-1) {
			return besti;
		}
		return 0;
	}
	private int determineDistance(int column, int spaces) {
		if (column==0) {
			return 3-spaces;
		} else if (column==1) {
			return 5-spaces;
		} else if (column==2) {
			return 7-spaces;
		} else if (column==3) {
			return 9-spaces;
		} else if (column==4) {
			return 11-spaces;
		} else if (column==5) {
			return 13-spaces;
		} else if (column==6) {
			return 11-spaces;
		} else if (column==7) {
			return 9-spaces;
		} else if (column==8) {
			return 7-spaces;
		} else if (column==9) {
			return 5-spaces;
		} else {
			return 3-spaces;
		}
	}
	public boolean rollAgain(CantStopGame game) {
		counter++;
		if (counter >=3) {
			counter=0;
			return false;
		}
		return true;
	}
}

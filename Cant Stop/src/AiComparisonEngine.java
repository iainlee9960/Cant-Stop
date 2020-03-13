
public class AiComparisonEngine {
	public int[] compareAIs(ComputerPlayer[] players, int games) {
		int currentPlayer = 0;
		int [] gamesWonPerPlayer = new int [players.length];
		for (int i=0; i<players.length; i++) {
			gamesWonPerPlayer[i]=0;
		}
		for (int i=0; i<games; i++) {
			currentPlayer = (i%players.length);
			CantStopPlayer[] completedColumns = {null, null, null, null, null, null, null, null, null, null, null};
			CantStopGame game = new CantStopGame (players, currentPlayer, completedColumns);
			game.runGame();
			int index = 0;
			for(int y=0; y<game.getPlayerOrder().length;y++) {
				if(game.getPlayerOrder()[y]==game.getWinner()) {
					index = y;
				}
			}
			gamesWonPerPlayer[index]++;
		}
		return gamesWonPerPlayer;
	}
}




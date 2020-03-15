import java.awt.Color;

public abstract class CantStopPlayer {
	String playerName;
	Color color;
	PlayerRecord record;
	int completed;

	abstract void startGame(CantStopGame game);

	abstract int chooseDice(CantStopGame game, int[] rolled, int[][] choices);

	abstract boolean rollAgain(CantStopGame game);
	abstract boolean isHuman();
	void setRecord() {
		record = new PlayerRecord();
	}
	String getPlayerName() {
		return playerName;
	}

	void setPlayerName(String name) {
		playerName = name;
	}

	Color getPlayerColor() {
		return color;
	}

	void setPlayerColor(Color rgb) {
		color = rgb;
	}

	int getCompleted() {
		return completed;
	}

	void addCompleted() {
		completed++;
	}

	PlayerRecord getRecord() {
		return record;
	}

}


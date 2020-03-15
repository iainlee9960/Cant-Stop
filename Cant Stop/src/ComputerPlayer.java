public class ComputerPlayer extends CantStopPlayer {
	Strategy strategy;

	String getAIName() {
		return strategy.getClass().getSimpleName();
	}

	void setAI(String AIName) {
		if (AIName.equals("SimpleStrategy")) {
			strategy = new SimpleStrategy();
		} else {
			strategy = new RandomStrategy();
		}
	}
	
	boolean isHuman() {
		return false;
	}

	void startGame(CantStopGame game) {

	}

	int chooseDice(CantStopGame game, int[] rolled, int[][] choices) {
		return strategy.chooseDice(game, rolled, choices);
	}

	boolean rollAgain(CantStopGame game) {
		return strategy.rollAgain(game);
	}
}

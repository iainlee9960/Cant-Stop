
public class HumanPlayer extends CantStopPlayer{
	boolean isHuman(){
		return true;
	}
	
	@Override
	void startGame(CantStopGame game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	int chooseDice(CantStopGame game, int[] rolled, int[][] choices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	boolean rollAgain(CantStopGame game) {
		// TODO Auto-generated method stub
		return false;
	}
}



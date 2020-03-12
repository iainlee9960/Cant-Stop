
public class RandomStrategy extends Strategy {
	public int chooseDice(CantStopGame game, int[] rolled, int[][]choices) { 
		int row = (int)(Math.random()*choices.length);
		return row;
	}
	public boolean rollAgain(CantStopGame game) {
		int decide = (int)(Math.random()*2);
		if (decide==0) return false;
		return true;
	}
}







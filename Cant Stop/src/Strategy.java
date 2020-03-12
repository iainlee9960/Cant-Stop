
public abstract class Strategy {
	abstract int chooseDice(CantStopGame game, int[] rolled, int[][]choices);
	abstract boolean rollAgain(CantStopGame game);
}




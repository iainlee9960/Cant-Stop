

public class PlayerRecord {
	private int[] pieceLocations;
	private int numNeutralLeft;
	private int[] neutralMarkerLocations;
	public PlayerRecord () {
		initializePieceLocations();
		setNumNeutralLeft();
		initializeNeutralMarkerLocations();
	}
	public int[] getPieceLocations () {
		return pieceLocations;
	}	
	public int getNumNeutralLeft () {
		return numNeutralLeft;
	}
	public int[] getNeutralMarkerLocations () {
		return neutralMarkerLocations;
	}
	private void initializePieceLocations () {
		pieceLocations = new int[CantStopGame.LENGTHS.length];
		for (int i=0; i<pieceLocations.length; i++) {
			pieceLocations[i] = 0;
		}
	}
	public void setPieceLocations(int[] locations) {
		pieceLocations = locations;
	}
	public void setNuetralLocations(int[] neutralLocations) {
		neutralMarkerLocations = neutralLocations;
	}
	public void setNumNeutralLeft () {
		numNeutralLeft = 3;
	}
	private void initializeNeutralMarkerLocations () {
		neutralMarkerLocations = new int[CantStopGame.LENGTHS.length];
		for (int i=0; i<neutralMarkerLocations.length; i++) {
			neutralMarkerLocations[i] = 0;
		}
	}
	public void removeNeutralMarkers () {
		for (int i=0; i<neutralMarkerLocations.length; i++) {
			neutralMarkerLocations[i] = 0;
		}
	}
	public void addPermanentMarkers () {
		for (int i=0; i<pieceLocations.length; i++) {
			if (neutralMarkerLocations[i]>pieceLocations[i]) {
				pieceLocations[i] = neutralMarkerLocations[i];
			}
		}
	}
	
}




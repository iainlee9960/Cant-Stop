import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

class PlacePiece extends JPanel {
	private int squareX,x;
	private int squareY,y;
	private int pieceW = 42;
	private int pieceH = 10;
	private int colorR;
	private int colorG;
	private int colorB;
	private int stackNum;
	Color assignColor;
	private int[][] xCoord= {{97,97,97},
							{169,169,171,169,169},
							{235,235,235,235,235,235,235},
							{298,298,298,298,298,298,298,298,298},
							{357,357,357,357,357,357,357,357,357,357,357},
							{415,415,415,415,415,415,415,415,415,415,415,415,415},
							{469,469,469,469,469,469,469,469,469,469,469},
							{528,528,528,528,528,528,528,528,528},
							{589,589,589,589,589,589,589},
							{657,657,657,657,657},
							{728,728,727},
							//pieces on the side
							{41,146,55},
							{676,793,771},
							{47,54,152},
							{676,793,771}};
	private int[][] yCoord= {{553,464,369},
							{611,538,463,386,309},
							{674,599,533,464,390,323,252},
							{717,653,590,526,464,398,330,267,203},
							{756,695,633,577,519,464,404,347,284,225,169},
							{776,724,674,619,569,517,464,410,355,304,253,201,148},
							{756,696,633,577,520,464,404,347,285,225,170},
							{717,653,591,527,464,398,330,266,204},
							{674,599,533,463,390,323,252},
							{611,537,464,386,309},
							{554,463,369},
							//pieces on the side
							{128,131,217},
							{113,109,204},
							{726,831,789},
							{772,694,811}};
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Color getColor() {
		return assignColor;
	}
	public void subtractStack() {
		stackNum--;
	}
	public void draw(Graphics g) {
		int startX = xCoord[x-2][y-1];
		int startY = yCoord[x-2][y-1];
		colorR = assignColor.getRed();
		colorG = assignColor.getGreen();
		colorB = assignColor.getBlue();
		if(colorR<41)
			colorR = 41;
		if(colorR>202)
			colorR = 202; 
		if(colorG<41)
			colorG = 41;
		if(colorG>202)
			colorG = 202;
		if(colorB<41)
			colorB = 41;
		if(colorB>202)
			colorB = 202;
		moveSquare(startX-6, startY-32-(10*(stackNum-1)), g);
	}
	public PlacePiece(Color assignColor, int x, int y, int num) {
		this.x = x;
		this.y = y;
		this.assignColor = assignColor;
		this.stackNum = num;
	}
	public PlacePiece(Color assignColor, int x, int y, int num, boolean show, Graphics g) {
		this.x = x;
		this.y = y;
		this.assignColor = assignColor;
		this.stackNum = num;
		if(show) {
			draw(g);
			/*int startX = xCoord[x-2][y-1];
			int startY = yCoord[x-2][y-1];
			colorR = assignColor.getRed();
			colorG = assignColor.getGreen();
			colorB = assignColor.getBlue();
			if(colorR<41)
				colorR = 41;
			if(colorR>202)
				colorR = 202; 
			if(colorG<41)
				colorG = 41;
			if(colorG>202)
				colorG = 202;
			if(colorB<41)
				colorB = 41;
			if(colorB>202)
				colorB = 202;
			moveSquare(startX-6, startY-32-(10*(num-1)), g);*/
		}
	}
	public boolean equals(PlacePiece p) {
		if(p.getX()==x && p.getY()==y) {
			return true;
		}
		return false;
	}
	public void moveSquare(int x, int y, Graphics g) {
		squareX = x;
		squareY = y;
		pieceTop(g);
		pieceBase(g);
	}
	protected void pieceTop(Graphics g) {
		Color shade;
		int runner = 0;
		while(runner!=32) {
			shade = new Color(colorR-40+(runner*3),colorG-40+(runner*3),colorB-40+(runner*3));
			g.setColor(shade);
			g.drawLine(squareX+21,squareY,squareX+5+runner,squareY+42);
			runner++;
		}
		g.setColor(Color.BLACK);
		g.drawLine(squareX+21,squareY,squareX+5,squareY+42);
		g.drawLine(squareX+21,squareY,squareX+pieceW-5,squareY+42);
	}
	protected void pieceBase(Graphics g) {
		g.setColor(new Color(colorR,colorG,colorB));
		g.fillRect(squareX,squareY+35,pieceW,pieceH);
		g.setColor(Color.BLACK);
		g.drawRect(squareX,squareY+35,pieceW,pieceH);
	}
}







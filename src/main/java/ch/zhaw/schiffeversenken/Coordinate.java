package ch.zhaw.schiffeversenken;

public class Coordinate {
	private int xPosition;
	private int yPosition;
	private Boolean isHit;
	
	public Coordinate(int xPosition, int yPosition, Boolean isHit) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.isHit = isHit;
	}
	
	public int getyPosition() {
		return yPosition;
	}
	
	public int getxPosition() {
		return xPosition;
	}

	public boolean isHit() {
		return isHit;
	}
	
	
}

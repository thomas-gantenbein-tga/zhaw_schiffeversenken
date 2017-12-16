package ch.zhaw.schiffeversenken.helpers;

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
	
	public void setIsHit(Boolean isHit) {
		this.isHit = isHit;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coordinate) {
			Coordinate coordinate = (Coordinate) obj;
			if (this.yPosition == coordinate.yPosition && this.xPosition == coordinate.xPosition) {
				return true;
			}
		}
		return false;
	}
}

package ch.zhaw.schiffeversenken.helpers;

public class Coordinate {
	private int xPosition;
	private int yPosition;
	private Boolean isHit;
	private Boolean isSunk;
	
	

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

	public boolean getIsHit() {
		return isHit;
	}
	
	public void setIsHit(Boolean isHit) {
		this.isHit = isHit;
	}
	
	public Boolean getIsSunk() {
		if(isSunk == null) {
			return false;
		}
		return isSunk;
	}

	public void setIsSunk(Boolean isSunk) {
		this.isSunk = isSunk;
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

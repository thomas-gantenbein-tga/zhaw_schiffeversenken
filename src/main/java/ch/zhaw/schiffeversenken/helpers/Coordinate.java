package ch.zhaw.schiffeversenken.helpers;

import ch.zhaw.schiffeversenken.data.PlayField;

/**
 * Represents positions on a x- and y-axis and also holds other information: Was
 * this position shot at before and does this position contain a fully destroyed
 * ship? Could later be used to also contain "bonuses" such as triple shot for
 * the player.
 *
 */
public class Coordinate {
	private int xPosition;
	private int yPosition;
	private Boolean isHit;
	private Boolean isSunk;

	/**
	 * Expects a position represented by a combination of x and y integers.
	 * xPosition and yPosition start at 0. If the coordinate is only meant to
	 * give a position (e.g. when making a shot), parameters isHit and isSunk
	 * should be null.
	 * 
	 * @param xPosition
	 *            position on x-axis (top-left corner has xPosition 0)
	 * @param yPosition
	 *            position on y-axis (top-left corner has yPosition 0)
	 * @param isHit
	 *            Was this position shot at before? Can be null.
	 * @param isSunk
	 *            Does this position contain a fully destroyed ship? Can be
	 *            null.
	 */
	public Coordinate(int xPosition, int yPosition, Boolean isHit, Boolean isSunk) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.isHit = isHit;
		this.isSunk = isSunk;
	}

	/**
	 * Gets the y-position of this object (starting at 0).
	 * 
	 * @return y-position of this object
	 */
	public int getyPosition() {
		return yPosition;
	}

	/**
	 * Gets the x-position of this object (starting at 0).
	 * 
	 * @return x-position of this object
	 */
	public int getxPosition() {
		return xPosition;
	}

	/**
	 * Was this position shot at before?
	 * 
	 * @return returns true if position was shot at before
	 */
	public boolean getIsHit() {
		return isHit;
	}

	/**
	 * Marks position as "was shot before".
	 * 
	 * @return returns true if position was shot at before
	 */
	public void setIsHit(Boolean isHit) {
		this.isHit = isHit;
	}

	/**
	 * Does this position contain a fully destroyed ship?
	 * 
	 * @return returns true if position is occupied by a destroyed ship
	 */
	public Boolean getIsSunk() {
		if (isSunk == null) {
			return false;
		}
		return isSunk;
	}

	/**
	 * Marks position as "occupied by destroyed ship".
	 */
	public void setIsSunk(Boolean isSunk) {
		this.isSunk = isSunk;
	}

	/**
	 * Two coordinates are considered equal if their x- and y-coordinates are
	 * the same.
	 */
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

	/**
	 * Checks if the Coordinate is in the PlayField
	 * 
	 * @return When in the PlayField return true, otherwise false
	 * @author uelik
	 * 
	 */
	public boolean isCoordinateInPlayField(PlayField playField) {
		if (getxPosition() >= playField.getColumnCount())
			return false;
		else if (getxPosition() < 0)
			return false;
		else if (getyPosition() >= playField.getRowCount())
			return false;
		else if (getyPosition() < 0)
			return false;
		return true;
	}
}

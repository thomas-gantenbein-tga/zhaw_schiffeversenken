package ch.zhaw.schiffeversenken.data;
/**
 * List with directions
 */
public enum Directions{
	  EAST , 
	  WEST, 
	  NORTH , 
	  SOUTH 
	  ; 
	
	 /**
	 * Creates a random direction
	 */
	  public static Directions getRandom() {
	      return values()[(int) (Math.random() * values().length)];
	  }
}

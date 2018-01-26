package ch.zhaw.schiffeversenken.model;
/**
 * List with directions
 * @author uelik
 */
public enum Directions{
	  EAST , 
	  WEST, 
	  NORTH , 
	  SOUTH 
	  ; 
	
	 /**
	 * Creates a random direction
	 * @author uelik
	 */
	  public static Directions getRandom() {	
	      return values()[(int) (Math.random() * values().length)];
	  }
	  
}

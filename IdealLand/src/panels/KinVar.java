/**
 * 
 */
package panels;

/**
 * @author Randy Lin
 *
 */
public enum KinVar {
	
	Initial_Velocity("Initial Velocity"),
	Final_Velocity("Final Velocity"),
	Acceleration("Acceleration"),
	Time("Time");
	//Displacement("Displacement");

	private String displayVar;

	KinVar(String displayVar) {
		this.displayVar = displayVar;
	}

	public String displayName() { return displayVar; }

	// Optionally and/or additionally, toString.
	@Override public String toString() { return displayVar; }
}

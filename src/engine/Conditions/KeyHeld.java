package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.World;

/**
 * 
 * @author aaronpaskin
 *
 */
public class KeyHeld extends Condition {

	private int priorityNum;
	private String check;
	
	public KeyHeld(int priorityNum, String check) {
		this.priorityNum = priorityNum;
		this.check = check;
	}
	
	
	/**
	 * Returns true when the key named "check" is down
	 */
	@Override
	public boolean isTrue(GameObject asking, World world) {
		//TODO make inputmanager (explicitly or implicitly)
		return world.getInputManager().getKeysDown().contains(check);
	}
	
}

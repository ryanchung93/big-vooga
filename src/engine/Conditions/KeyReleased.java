package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.World;

/**
 * 
 * NOTE: implement KeyPressed when implementing this condition.
 * 
 * @author aaronpaskin
 *
 */
public class KeyReleased extends Condition {
	
	private String check;
	
	public KeyReleased(int priorityNum, String check) {
		this.priorityNum = priorityNum;
		this.check = check;
	}
	
	/**
	 * Returns true when the key named "check" is not down but was 
	 */
	@Override
	public boolean isTrue(GameObject asking, World world) {
		//TODO make inputmanager (explicitly or implicitly)
		return !world.getInputManager().getKeysDown().contains(check) && world.getInputManager().getPrevKeysDown().contains(check);
	}

}
package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.World;

/**
 * 
 * NOTE: implement KeyReleased when implementing this condition
 * 
 * @author aaronpaskin
 *
 */
public class KeyPressed extends Condition {

	private String check;
	
	public KeyPressed(int priorityNum, String check) {
		this.priorityNum = priorityNum;
		this.check = check;
	}
	
	/**
	 * Returns true when the key named "check" is down but was not down in the previous step, i.e. when it is pressed/tapped
	 */
	@Override
	public boolean isTrue(GameObject asking, World world) {
		//TODO make inputmanager (explicitly or implicitly)
		return world.getInputManager().getKeysDown().contains(check) && !world.getInputManager().getPrevKeysDown().contains(check);
	}
	
}

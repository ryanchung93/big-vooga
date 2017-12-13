package engine.sprite;

import java.util.HashMap;
import java.util.Map;

import engine.VoogaException;

/**
 * Holds multiple animation sequences and allows the animation to be set.
 * 
 * @author Ian Eldridge-Allegra
 * 
 */
public class Sprite {
	private Map<String, AnimationSequence> animations = new HashMap<String, AnimationSequence>();
	private AnimationSequence currentAnimation;
	private AnimationSequence tempAnimation = null;

	public void addAnimationSequence(AnimationSequence animation) {
		animations.put(animation.getName(), animation);
	}

	/**
	 * @param name The new animation sequence to switch to
	 */
	public void setAnimation(String name) {
		if (!animations.containsKey(name))
			throw new VoogaException("AnimationNotFound", name);
		currentAnimation = animations.get(name);
		currentAnimation.reset();
	}

	/**
	 * Increments the AnimationSequences
	 */
	public void step() {
		if(tempAnimation!= null)
		{
			tempAnimation.increment();
			if(tempAnimation.isDone())
				tempAnimation = null;
			return;
		}
		currentAnimation.increment();
	}

	public BoundedImage getImage() {
		if(tempAnimation!= null)
		{
			return tempAnimation.getImage();
		}
		if (currentAnimation == null)
			throw new VoogaException("UndefinedAnimation");
		return currentAnimation.getImage();
	}

	public Sprite clone() {
		Sprite clone = new Sprite();
		for (String s : animations.keySet()) {
			clone.addAnimationSequence(animations.get(s).clone());
		}
		clone.setAnimation(currentAnimation.getName());
		return clone;
	}
	
	public void playOnce(String name)
	{
		tempAnimation = animations.get(name);
	}

}

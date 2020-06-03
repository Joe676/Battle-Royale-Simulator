package Item;

import Agents.Agent;

/**
 * Interface for things that can be picked up by agents
 * @author Jozef Bossowski
 *
 */
public interface Pickupable {
	/**
	 * Method that manages what happens when an Item is picked up
	 * @param owner Agent who picked up the item
	 * @return The item
	 */
	public Item pickUp(Agent owner);
}

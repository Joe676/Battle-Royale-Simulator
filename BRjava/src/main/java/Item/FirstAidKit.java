package Item;

import Agents.Agent;

public class FirstAidKit extends Item{
	
	public FirstAidKit(Agent owner) {
		super(owner);
	}
	
	@Override
	public Item pickUp() {
		return null;
	}

}

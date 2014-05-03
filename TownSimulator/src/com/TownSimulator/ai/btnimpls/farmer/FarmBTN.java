package com.TownSimulator.ai.btnimpls.farmer;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.entity.Man;

public class FarmBTN extends SelectorNode{
	private Man man;
	
	public FarmBTN(Man man)
	{
		this.man = man;
		init();
	}
	
	private void init()
	{
		this.addNode(new SowBTN(man))
			.addNode(new ReapBTN(man));
	}
}

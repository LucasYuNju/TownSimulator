package com.TownSimulator.ai.btnimpls;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.construct.ConstructionBTN;
import com.TownSimulator.entity.Man;

public class IdleBTN extends SelectorNode{
	private Man man;
	
	public IdleBTN(Man man) {
		this.man = man;
		init();
	}

	protected void init() {
		this.addNode(new FindJobBTN(man))
			.addNode(new ConstructionBTN(man))
			.addNode(new RandomMoveBTN(man));
	}

}

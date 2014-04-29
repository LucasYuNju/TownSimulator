package com.TownSimulator.ai.btnimpls;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.construct.ConstructionBTN;
import com.TownSimulator.entity.Man;

public class IdleBTN extends SelectorNode{
	private Man mMan;
	
	public IdleBTN(Man man) {
		this.mMan = man;
		initTree();
	}

	protected void initTree() {
		this.addNode(new ConstructionBTN(mMan))
			.addNode(new RandomMoveBTN(mMan));
	}

}

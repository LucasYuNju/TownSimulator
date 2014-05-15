package com.TownSimulator.ai.btnimpls.farmer;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.general.RandomMoveBTN;
import com.TownSimulator.entity.Man;

public class FarmerBTN extends SelectorNode{
	private Man mMan;
	
	public FarmerBTN(Man man) {
		this.mMan = man;
		initTree();
	}

	protected void initTree() {
		this.addNode(new FarmBTN(mMan))
			.addNode(new RandomMoveBTN(mMan));
	}
}

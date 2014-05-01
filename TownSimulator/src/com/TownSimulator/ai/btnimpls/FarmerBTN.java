package com.TownSimulator.ai.btnimpls;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.farm.FarmBTN;
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

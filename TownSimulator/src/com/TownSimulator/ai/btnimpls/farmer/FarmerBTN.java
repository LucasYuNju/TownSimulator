package com.TownSimulator.ai.btnimpls.farmer;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.general.GeneralBTN;
import com.TownSimulator.ai.btnimpls.general.RandomMoveBTN;
import com.TownSimulator.entity.Man;

public class FarmerBTN extends SelectorNode{
	private static final long serialVersionUID = 1L;
	private Man mMan;
	
	public FarmerBTN(Man man) {
		this.mMan = man;
		initTree();
	}

	protected void initTree() {
		this.addNode(new GeneralBTN(mMan))
			.addNode(new FarmBTN(mMan))
			.addNode(new RandomMoveBTN(mMan));
	}
}

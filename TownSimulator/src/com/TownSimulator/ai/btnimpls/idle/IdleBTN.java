package com.TownSimulator.ai.btnimpls.idle;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.construct.ConstructionBTN;
import com.TownSimulator.ai.btnimpls.general.GeneralBTN;
import com.TownSimulator.ai.btnimpls.general.RandomMoveBTN;
import com.TownSimulator.entity.Man;

public class IdleBTN extends SelectorNode{
	private Man man;
	
	public IdleBTN(Man man) {
		this.man = man;
		man.stopMove();
		init();
	}

	protected void init() {
		this.addNode(new GeneralBTN(man))
			.addNode(new ConstructionBTN(man))
			.addNode(new FindJobBTN(man))
			.addNode(new RandomMoveBTN(man));
	}

}

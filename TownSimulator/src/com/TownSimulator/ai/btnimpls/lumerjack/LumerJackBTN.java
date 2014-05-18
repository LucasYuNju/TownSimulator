package com.TownSimulator.ai.btnimpls.lumerjack;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.general.GeneralBTN;
import com.TownSimulator.ai.btnimpls.general.RandomMoveBTN;
import com.TownSimulator.entity.Man;

public class LumerJackBTN extends SelectorNode{
	private static final long serialVersionUID = 1L;
	private Man mMan;
	
	public LumerJackBTN(Man man) {
		this.mMan = man;
		init();
	}

	protected void init() {
		this.addNode(new GeneralBTN(mMan))
			.addNode(new FellingBTN(mMan))
			.addNode(new RandomMoveBTN(mMan));
	}
}

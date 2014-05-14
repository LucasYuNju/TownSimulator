package com.TownSimulator.ai.btnimpls.bartender;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.general.GeneralBTN;
import com.TownSimulator.entity.Man;

public class BarTenderBTN extends SelectorNode{
	private Man man;
	
	public BarTenderBTN(Man man)
	{
		this.man = man;
		init();
	}

	private void init() {
		addNode(new GeneralBTN(man));
		addNode(new BarTendingBTN(man));
	}

}

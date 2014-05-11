package com.TownSimulator.ai.btnimpls.grazier;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.general.GeneralBTN;
import com.TownSimulator.entity.Man;

public class GrazierBTN extends SelectorNode{
	private Man man;
	
	public GrazierBTN(Man man)
	{
		this.man = man;
		
		init();
	}

	private void init() {
		
		this.addNode(new GeneralBTN(man))
			.addNode(new GrazingBTN(man));
	}

}

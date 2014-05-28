package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.general.GeneralBTN;
import com.TownSimulator.ai.btnimpls.general.RandomMoveBTN;
import com.TownSimulator.entity.Man;

public class BuilderBTN extends SelectorNode{
	private static final long serialVersionUID = 1L;
	private Man man;
	
	public BuilderBTN(Man man)
	{
		this.man = man;
		
		init();
	}

	private void init() {
		this.addNode(new GeneralBTN(man))
			.addNode(new ConstructionBTN(man))
			.addNode(new RandomMoveBTN(man));
	}

}

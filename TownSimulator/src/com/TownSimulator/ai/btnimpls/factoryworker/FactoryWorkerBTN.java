package com.TownSimulator.ai.btnimpls.factoryworker;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.general.GeneralBTN;
import com.TownSimulator.entity.Man;

public class FactoryWorkerBTN extends SelectorNode{
	private static final long serialVersionUID = 1L;
	private Man man;
	
	public FactoryWorkerBTN(Man man)
	{
		this.man = man;
		
		init();
	}

	private void init() {
		
		this.addNode(new GeneralBTN(man))
			.addNode(new FactoryProducingBTN(man));
	}
}

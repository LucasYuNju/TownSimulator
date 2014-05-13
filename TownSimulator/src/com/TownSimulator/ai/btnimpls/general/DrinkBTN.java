package com.TownSimulator.ai.btnimpls.general;

import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;

public class DrinkBTN extends SequenceNode{
	private Man man;
	
	public DrinkBTN(Man man) {
		this.man = man;
		initSubNodes();
	}

	private void initSubNodes() {
		
	}
}

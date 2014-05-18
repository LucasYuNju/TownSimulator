package com.TownSimulator.ai.btnimpls.general;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.entity.Man;

public class GeneralBTN extends SelectorNode{
	private static final long serialVersionUID = 4402146961270887927L;
	public Man man;
	
	public GeneralBTN(Man man)
	{
		this.man = man;
		init();
	}
	
	private void init()
	{
		addNode( new FindHomeBTN(man));
		addNode( new FindFoodBTN(man));
		addNode(new GotoSchoolBTN(man));
		addNode(new SeeADoctorBTN(man));
		addNode(new DrinkBTN(man));
	}
}

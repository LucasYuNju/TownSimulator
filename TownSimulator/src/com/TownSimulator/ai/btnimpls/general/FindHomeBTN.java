package com.TownSimulator.ai.btnimpls.general;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.LivingHouse;

public class FindHomeBTN extends SequenceNode{
	private Man man;
	
	public FindHomeBTN(Man man)
	{
		this.man = man;
		init();
	}
	
	private LivingHouse findHome()
	{
		for (Building building : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllBuildings()) {
			if(building.getType() == BuildingType.LOW_COST_HOUSE || building.getType() == BuildingType.APARTMENT)
			{
				LivingHouse house = (LivingHouse)building;
				if(house.hasAvailableRoom())
					return house;
			}
		}
		
		return null;
	}
	
	private void init()
	{
		ConditionNode judgeNoHome = new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(man.getInfo().home == null)
					return ExecuteResult.TRUE;
				else
					return ExecuteResult.FALSE;
			}
		};
		
		ActionNode findHomeExecute = new ActionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				LivingHouse home = findHome();
				if(home == null)
					return ExecuteResult.FALSE;
				else
				{
					home.addResident(man.getInfo());
					return ExecuteResult.TRUE;
				}
			}
		};
		
		
		this.addNode(judgeNoHome)
			.addNode(findHomeExecute);
	}
	
}

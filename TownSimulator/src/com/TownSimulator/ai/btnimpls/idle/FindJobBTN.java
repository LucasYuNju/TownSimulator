package com.TownSimulator.ai.btnimpls.idle;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.WorkingBuilding;

public class FindJobBTN implements ActionNode{
	private Man man;
	
	public FindJobBTN(Man man)
	{
		this.man = man;
	}
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		for (Building buidling : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllBuildings()) {
			if(buidling instanceof WorkingBuilding)
			{
				WorkingBuilding workingBuilding = (WorkingBuilding)buidling;
				if(workingBuilding.getState() == Building.State.Constructed 
						&& workingBuilding.getCurWorkerCnt() < workingBuilding.getOpenJobCnt())
				{
					workingBuilding.addWorker(man);
					return ExecuteResult.TRUE;
				}
			}
		}
		
		return ExecuteResult.FALSE;
	}

}

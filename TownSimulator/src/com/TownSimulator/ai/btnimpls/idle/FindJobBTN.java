package com.TownSimulator.ai.btnimpls.idle;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.WorkableBuilding;

public class FindJobBTN extends ActionNode{
	private static final long serialVersionUID = -3603187264907570028L;
	private Man man;
	
	public FindJobBTN(Man man)
	{
		this.man = man;
	}
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		if( man.getInfo().getAge() < ManInfo.AGE_ADULT ){
			return ExecuteResult.FALSE;
		}
		
		for (Building buidling : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllBuildings()) {
			if(buidling instanceof WorkableBuilding)
			{
				WorkableBuilding workingBuilding = (WorkableBuilding)buidling;
				if(workingBuilding.getState() == Building.State.Constructed 
						&& workingBuilding.getCurWorkerCnt() < workingBuilding.getOpenJobCnt())
				{
					workingBuilding.addWorker(man);
					return ExecuteResult.TRUE;
				}
			}
		}
//		man.getInfo().hpWorkless(deltaTime);
		return ExecuteResult.FALSE;
	}

}

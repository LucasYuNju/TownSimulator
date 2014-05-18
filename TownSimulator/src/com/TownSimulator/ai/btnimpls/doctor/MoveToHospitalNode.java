package com.TownSimulator.ai.btnimpls.doctor;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManStateType;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class MoveToHospitalNode extends ActionNode{
	private static final long serialVersionUID = 1L;
	private Man man;
	
	public MoveToHospitalNode(Man man) {
		this.man = man;
	}

	@Override
	public ExecuteResult execute(float deltaTime) {
		float destX = man.getInfo().workingBuilding.getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float destY = man.getInfo().workingBuilding.getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		man.setMoveDestination(destX, destY);
		man.getInfo().manState = ManStateType.Working;
		
		if( !man.move(deltaTime) )
		{
			man.getInfo().animeType = ManAnimeType.STANDING;
		}
		else {
			man.getInfo().animeType = ManAnimeType.MOVE;
//			return ExecuteResult.FALSE;
		}
		return ExecuteResult.TRUE;
	}
}

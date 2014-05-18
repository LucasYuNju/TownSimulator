package com.TownSimulator.ai.btnimpls.factoryworker;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManStateType;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class FactoryProducingBTN extends SequenceNode{
	private Man man;
	
	public FactoryProducingBTN(Man man)
	{
		this.man = man;
		
		init();
	}

	private void init() {
//		ConditionNode isNotAroundFactory = new ConditionNode() {
//			
//			@Override
//			public ExecuteResult execute(float deltaTime) {
//				float x = man.getPositionXWorld();
//				float y = man.getPositionYWorld();
//				if( man.getInfo().workingBuilding.isAround(x, y) )
//					return ExecuteResult.FALSE;
//				else
//					return ExecuteResult.TRUE;
//			}
//		};
		
		ActionNode moveToFactory = new ActionNode() {
			
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
				else
					man.getInfo().animeType = ManAnimeType.MOVE;
//				float x = man.getPositionXWorld();
//				float y = man.getPositionYWorld();
//				if( man.getInfo().workingBuilding.isAround(x, y) )
//				{
//					
//				}
				
				return ExecuteResult.TRUE;
			}
		};
		
		this.addNode(moveToFactory);
	}
}

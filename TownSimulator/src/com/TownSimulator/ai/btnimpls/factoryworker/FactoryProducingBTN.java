package com.TownSimulator.ai.btnimpls.factoryworker;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManStateType;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class FactoryProducingBTN extends SequenceNode{
	private static final long serialVersionUID = 1L;
	private Man man;
	
	public FactoryProducingBTN(Man man)
	{
		this.man = man;
		
		init();
	}

	private void init() {
		
		ActionNode moveToFactory = new ActionNode() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				float destX = man.getInfo().workingBuilding.getAABBWorld(QuadTreeType.COLLISION).getCenterX();
				float destY = man.getInfo().workingBuilding.getAABBWorld(QuadTreeType.COLLISION).getCenterY();
				man.setMoveDestination(destX, destY);
				man.getInfo().manStates.add( ManStateType.Working );
				
				if( !man.move(deltaTime) )
				{
					man.getInfo().animeType = ManAnimeType.Standing;
				}
				else
					man.getInfo().animeType = ManAnimeType.Move;
				
				return ExecuteResult.TRUE;
			}
		};
		
		this.addNode(moveToFactory);
	}
}

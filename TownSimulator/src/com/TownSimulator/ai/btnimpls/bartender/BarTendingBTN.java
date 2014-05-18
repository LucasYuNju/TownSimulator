package com.TownSimulator.ai.btnimpls.bartender;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManStateType;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class BarTendingBTN extends SequenceNode{
	private Man man;
	
	public BarTendingBTN(Man man) {
		this.man = man;
		initSubNodes();
	}
	
	private void initSubNodes() {
		ActionNode moveToBarNode = new ActionNode() {
			@Override
			public ExecuteResult execute(float deltaTime) {
				float destX = man.getInfo().workingBuilding.getAABBWorld(QuadTreeType.COLLISION).getCenterX();
				float destY = man.getInfo().workingBuilding.getAABBWorld(QuadTreeType.COLLISION).getCenterY();
				man.setMoveDestination(destX, destY);
				man.getInfo().manState = ManStateType.Working;
				
				if( !man.move(deltaTime) )
				{
					man.getInfo().animeType = ManAnimeType.STANDING;
					return ExecuteResult.TRUE;
				}
				else {
					man.getInfo().animeType = ManAnimeType.MOVE;
					return ExecuteResult.FALSE;
				}
			}
		};
		addNode(moveToBarNode);
		addNode(new BarExecuteBTN(man));
	}
}

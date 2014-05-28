package com.TownSimulator.ai.btnimpls.teacher;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManStateType;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class TeachworkBTN extends SequenceNode{
	private static final long serialVersionUID = 1L;
	private Man man;
	
	public TeachworkBTN(Man man){
		this.man=man;
		init();
	}
	
	public void init(){
		ActionNode moveToSchoolNode=new ActionNode() {
			private static final long serialVersionUID = -1630089335151546638L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				moveToSchool(deltaTime);
				return ExecuteResult.TRUE;
			}
		};
		
		this.addNode(moveToSchoolNode);
	}
	
	public void moveToSchool(float deltaTime){
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
	}
	
}

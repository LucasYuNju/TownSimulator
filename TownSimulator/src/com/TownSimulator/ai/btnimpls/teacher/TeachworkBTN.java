package com.TownSimulator.ai.btnimpls.teacher;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class TeachworkBTN extends SequenceNode{
	private Man man;
	
	public TeachworkBTN(Man man){
		this.man=man;
		init();
	}
	
	public void init(){
		ActionNode moveToSchoolNode=new ActionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				// TODO Auto-generated method stub
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
		
		if( !man.move(deltaTime) )
		{
			man.getInfo().animeType = ManAnimeType.STANDING;
		}
		else
			man.getInfo().animeType = ManAnimeType.MOVE;
	}
	
}
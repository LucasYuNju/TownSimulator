package com.TownSimulator.ai.btnimpls.farm;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FarmLand;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class SowExecuteBTN implements ActionNode{
	private Man man;
	private static final float SOW_TIME_PER_LAND = 2.0f;
	private float timeAccum = 0.0f;
	
	public SowExecuteBTN(Man man){
		this.man=man;
	}
	
	private void sowFinish(){
		FarmHouse farmHouse=(FarmHouse)man.getInfo().workingBuilding;
		farmHouse.setSowed(true);
		farmHouse.clearSowedLandCnt();
		
		timeAccum=0f;
	}
	
	private void doSow(float deltaTime){
		FarmHouse farmHouse = (FarmHouse)man.getInfo().workingBuilding;
		timeAccum += deltaTime;
		while(timeAccum >= SOW_TIME_PER_LAND)
		{
			timeAccum -= SOW_TIME_PER_LAND;
			
			farmHouse.addSowedLand();
			
			if( farmHouse.getSowedLandCnt() >= farmHouse.getFarmLands().size )
				sowFinish();
		}
	}

	@Override
	public ExcuteResult execute(float deltaTime) {
		// TODO Auto-generated method stub
		FarmHouse farmHouse = (FarmHouse)man.getInfo().workingBuilding;
		FarmLand middleFarmLand = farmHouse.getFarmLands().get(4);
		float destX = middleFarmLand.getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float destY = middleFarmLand.getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		man.setMoveDestination(destX, destY);
		
		if( !man.move(deltaTime) )
		{
			doSow(deltaTime);
		}
		
		return ExcuteResult.RUNNING;
	}

}

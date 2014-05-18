package com.TownSimulator.ai.btnimpls.farmer;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManStateType;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FarmLand;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class SowExecuteBTN extends ActionNode{
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
		farmHouse.setCurCropType(farmHouse.getSowCropType());
		farmHouse.setSowStart(false);
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
	public ExecuteResult execute(float deltaTime) {
		FarmHouse farmHouse = (FarmHouse)man.getInfo().workingBuilding;
		farmHouse.setSowStart(true);
		FarmLand middleFarmLand = farmHouse.getFarmLands().get(4);
		float destX = middleFarmLand.getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float destY = middleFarmLand.getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		man.setMoveDestination(destX, destY);
		man.getInfo().animeType = ManAnimeType.MOVE;
		man.getInfo().manState = ManStateType.Working;
		
		if( !man.move(deltaTime) )
		{
			doSow(deltaTime);
		}
		
		return ExecuteResult.RUNNING;
	}

}

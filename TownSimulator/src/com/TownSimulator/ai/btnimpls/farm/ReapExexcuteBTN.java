package com.TownSimulator.ai.btnimpls.farm;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FarmLand;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class ReapExexcuteBTN implements ActionNode{
	private Man man;
	private static final float SOW_TIME_PER_LAND = 2.0f;
	private float timeAccum = 0.0f;
	
	public ReapExexcuteBTN(Man man)
	{
		this.man = man;
	}
	
	private void reapFinish()
	{
		FarmHouse farmHouse = (FarmHouse)man.getInfo().workingBuilding;
		farmHouse.setReapStart(false);
		farmHouse.setSowed(false);
		farmHouse.clearReappedLandCnt();
		
		timeAccum = 0.0f;
	}
	
	private void doReap(float deltaTime)
	{
		FarmHouse farmHouse = (FarmHouse)man.getInfo().workingBuilding;
		timeAccum += deltaTime;
		while(timeAccum >= SOW_TIME_PER_LAND)
		{
			timeAccum -= SOW_TIME_PER_LAND;
			
			int landIndex = farmHouse.getReappedLandCnt();
			farmHouse.addReappedLand();
			
			FarmLand land = farmHouse.getFarmLands().get(landIndex);
			float reappedAmount = land.getCurCropAmount();
			System.out.println(reappedAmount);
			land.addCropAmount(-reappedAmount);
			
			if( farmHouse.getReappedLandCnt() >= farmHouse.getFarmLands().size )
				reapFinish();
		}
		
	}
	
	@Override
	public ExcuteResult execute(float deltaTime) {
		FarmHouse farmHouse = (FarmHouse)man.getInfo().workingBuilding;
		FarmLand middleFarmLand = farmHouse.getFarmLands().get(4);
		float destX = middleFarmLand.getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float destY = middleFarmLand.getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		man.setMoveDestination(destX, destY);
		
		if( !man.move(deltaTime) )
		{
			doReap(deltaTime);
		}
		
		return ExcuteResult.RUNNING;
	}

}

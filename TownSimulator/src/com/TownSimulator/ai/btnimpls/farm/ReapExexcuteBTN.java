package com.TownSimulator.ai.btnimpls.farm;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FarmLand;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class ReapExexcuteBTN implements ActionNode{
	private Man man;
	private static final float REAP_TIME_PER_LAND = 2.0f;
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
	
	private Warehouse findNearestWarehouse()
	{
		double minDest = Double.MAX_VALUE;
		Warehouse result = null;
		for( Building building : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllBuildings() )
		{
			if(building.getType() == BuildingType.WAREHOUSE)
			{
				Warehouse warehouse = (Warehouse)building;
				double dest = Math.pow(warehouse.getPositionXWorld() - man.getPositionXWorld(), 2)
							+ Math.pow(warehouse.getPositionYWorld() - man.getPositionYWorld(), 2);
				if(dest < minDest)
				{
					minDest = dest;
					result = warehouse;
				}
			}
		}
		return result;
	}
	
	private void doReap(float deltaTime)
	{
		FarmHouse farmHouse = (FarmHouse)man.getInfo().workingBuilding;
		timeAccum += deltaTime;
		while(timeAccum >= REAP_TIME_PER_LAND)
		{
			timeAccum -= REAP_TIME_PER_LAND;
			
			Warehouse warehouse = findNearestWarehouse();
			if(warehouse == null)
				return;
			
			int landIndex = farmHouse.getReappedLandCnt();
			farmHouse.addReappedLand();
			
			FarmLand land = farmHouse.getFarmLands().get(landIndex);
			float reappedAmount = land.getCurCropAmount();
			System.out.println(reappedAmount);
			land.addCropAmount(-reappedAmount);
			land.updateView();
			
			warehouse.addStoredResource(farmHouse.getCurCropType().getResourceType(), (int)reappedAmount);
			ResourceInfoCollector.getInstance(ResourceInfoCollector.class)
			.addResourceAmount(farmHouse.getCurCropType().getResourceType(), (int)reappedAmount);
			
			if( farmHouse.getReappedLandCnt() >= farmHouse.getFarmLands().size )
				reapFinish();
		}
		
	}
	
	@Override
	public ExcuteResult execute(float deltaTime) {
		FarmHouse farmHouse = (FarmHouse)man.getInfo().workingBuilding;
		farmHouse.setReapStart(true);
		FarmLand middleFarmLand = farmHouse.getFarmLands().get(4);
		float destX = middleFarmLand.getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float destY = middleFarmLand.getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		man.setMoveDestination(destX, destY);
		man.getInfo().animeType = ManAnimeType.MOVE;
		
		if( !man.move(deltaTime) )
		{
			doReap(deltaTime);
		}
		
		return ExcuteResult.RUNNING;
	}

}

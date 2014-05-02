package com.TownSimulator.ai.btnimpls.lumerjack;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Warehouse;

public class TransportWoodBTN implements ActionNode{
	private Man man;
	private FellingInfo fellingInfo;
	private Warehouse warehouse;
	private static final int FELLING_WOOD_AMOUNT = 30;
	
	public TransportWoodBTN(Man man, FellingInfo fellingInfo)
	{
		this.man = man;
		this.fellingInfo = fellingInfo;
	}
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		if(fellingInfo.hasWood == false)
			return ExecuteResult.FALSE;
		
		if(warehouse == null)
		{
			warehouse = EntityInfoCollector.getInstance(EntityInfoCollector.class)
					.findNearestWareHouse(man.getPositionXWorld(), man.getPositionYWorld());
			if(warehouse == null)
				return ExecuteResult.FALSE;
		}
		
		man.setMoveDestination(warehouse.getPositionXWorld(), warehouse.getPositionYWorld());
		
		if( !man.move(deltaTime) )
		{
			man.getInfo().animeType = ManAnimeType.STANDING;
			warehouse.addStoredResource(ResourceType.RS_WOOD, FELLING_WOOD_AMOUNT);
			fellingInfo.hasWood = false;
		}
		else
			man.getInfo().animeType = ManAnimeType.MOVE;
		
		return ExecuteResult.TRUE;
	}
	
}

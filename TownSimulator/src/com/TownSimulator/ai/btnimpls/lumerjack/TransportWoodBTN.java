package com.TownSimulator.ai.btnimpls.lumerjack;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManStateType;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.entity.building.WorkableBuilding;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.TipsBillborad;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.Color;

public class TransportWoodBTN extends ActionNode{
	private static final long serialVersionUID = 1L;
	private Man man;
	private FellingInfo fellingInfo;
	//private Warehouse warehouse;
	private static final int FELLING_WOOD_AMOUNT = 80;
	
	public TransportWoodBTN(Man man, FellingInfo fellingInfo)
	{
		this.man = man;
		this.fellingInfo = fellingInfo;
	}
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		if(fellingInfo.hasWood == false)
			return ExecuteResult.FALSE;
		
		WorkableBuilding house = man.getInfo().workingBuilding;
//		if(house == null)
//		{
//			house = EntityInfoCollector.getInstance(EntityInfoCollector.class)
//					.findNearestWareHouse(man.getPositionXWorld(), man.getPositionYWorld());
//			if(house == null)
//				return ExecuteResult.FALSE;
//		}
		
		man.setMoveDestination(house.getPositionXWorld(), house.getPositionYWorld());
		man.getInfo().manStates.add( ManStateType.Working );
		
		if( !man.move(deltaTime) )
		{
			man.getInfo().animeType = ManAnimeType.Standing;
			Warehouse warehouse = EntityInfoCollector.getInstance(EntityInfoCollector.class)
									.findNearestWareHouse(man.getPositionXWorld(), man.getPositionYWorld());
			if(warehouse != null)
			{
				float efficency = man.getInfo().workEfficency;
				int amount = (int)(FELLING_WOOD_AMOUNT * efficency);
				warehouse.addStoredResource(ResourceType.Wood, amount, false);
				float originX = house.getAABBWorld(QuadTreeType.DRAW).getCenterX();
				float originY = house.getAABBWorld(QuadTreeType.DRAW).maxY + Settings.UNIT * 0.4f;
				Color color = Color.WHITE;
				TipsBillborad.showTips(
						ResourceType.Wood + " + " + amount,
						originX,
						originY, color);
			}
			
			fellingInfo.hasWood = false;
		}
		else
			man.getInfo().animeType = ManAnimeType.Move;
		
		return ExecuteResult.TRUE;
	}
	
}

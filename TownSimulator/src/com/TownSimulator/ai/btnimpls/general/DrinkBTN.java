package com.TownSimulator.ai.btnimpls.general;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.building.Bar;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.utils.Array;

public class DrinkBTN extends SequenceNode{
	private Man man;
	
	public DrinkBTN(Man man) {
		this.man = man;
		initSubNodes();
	}
	
	private void initSubNodes() {
		ConditionNode judgeDepressedNode = new ConditionNode() {
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(man.getInfo().isDepressed()) {
					return ExecuteResult.TRUE;
				}
				return ExecuteResult.FALSE;
			}
		};
		
		ActionNode headForBarNode = new ActionNode() {
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(man.getInfo().isDepressed()) {
					Bar bar;
					if((bar = getAvailableBar()) != null) {
						float destX = bar.getAABBWorld(QuadTreeType.COLLISION).getCenterX();
						float destY = bar.getAABBWorld(QuadTreeType.COLLISION).getCenterY();
						man.setMoveDestination(destX, destY);
						if( !man.move(deltaTime) )
						{
							man.getInfo().animeType = ManAnimeType.STANDING;
							if(bar.getWine()) {
								man.getInfo().drinkWine();							
							}
						}
						else
							man.getInfo().animeType = ManAnimeType.MOVE;
						return ExecuteResult.TRUE;
					}
				}
				return ExecuteResult.FALSE;
			}
		};
		
		addNode(judgeDepressedNode);
		addNode(headForBarNode);
	}
	
	public Bar getAvailableBar() {
		Array<Building> bars = Singleton.getInstance(EntityInfoCollector.class).getBuildings(BuildingType.Bar);
		for(Building building : bars) {
			Bar bar = (Bar)building;
			if (bar.isAvailable()) {
				return bar;
			}
		}
		return null;
	}
}

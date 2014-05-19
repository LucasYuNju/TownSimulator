package com.TownSimulator.ai.btnimpls.general;

import java.util.List;

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

public class DrinkBTN extends SequenceNode{
	private static final long serialVersionUID = 5130801064184087015L;
	private Man man;
	
	public DrinkBTN(Man man) {
		this.man = man;
		initSubNodes();
	}
	
	private void initSubNodes() {
		ConditionNode judgeDepressedNode = new ConditionNode() {
			private static final long serialVersionUID = 2299976049705656346L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				if(man.getInfo().isDepressed()) {
					return ExecuteResult.TRUE;
				}
				return ExecuteResult.FALSE;
			}
		};
		
		ActionNode headForBarNode = new ActionNode() {
			private static final long serialVersionUID = 2936435038993189629L;

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
		List<Building> bars = Singleton.getInstance(EntityInfoCollector.class).getBuildings(BuildingType.Bar);
		for(Building building : bars) {
			Bar bar = (Bar)building;
			if (bar.isAvailable()) {
				return bar;
			}
		}
		return null;
	}
}

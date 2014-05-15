package com.TownSimulator.ai.btnimpls.general;

import java.util.Iterator;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.Warehouse;

public class FindFoodBTN extends SequenceNode{
	private Man man;
	private static final float EAT_TIME_INTERVAL = 2.0f;
	private static final float EAT_HUNGER_INCRE = ManInfo.HUNGER_POINTS_MAX / 3.0f; // points per eat
	private static final int   EAT_FOOD_RESOURCE_AMOUNT = 1000;
	private float eatTimeAccum = 0.0f;
	private boolean eatStart = false;
	
	public FindFoodBTN(Man man)
	{
		this.man = man;
		
		init();
	}
	
	private void decreFoodRS()
	{
		int amount = EAT_FOOD_RESOURCE_AMOUNT;
		
		for (Building building : EntityInfoCollector.getInstance(EntityInfoCollector.class).getBuildings(BuildingType.WAREHOUSE)) {
//			if(building.getType() == BuildingType.WAREHOUSE)
//			{
				Warehouse wareHouse = (Warehouse)building;
				Iterator<Resource> itr = wareHouse.getStoredResource();
				while(itr.hasNext())
				{
					Resource resource = itr.next();
					if(resource.getType().isFood())
					{
						int decre = Math.min(amount, resource.getAmount());
						amount -= decre;
						//resource.addAmount(-decre);
						wareHouse.addStoredResource(resource.getType(), -decre);
						
						//System.out.println(ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getFoodAmount());
						if(amount <= 0)
							return;
					}
//				}
			}
		}
		
	}
	
	private void eatFinish()
	{
		eatStart = false;
		eatTimeAccum = 0.0f;
	}
	
	private void doEat(float deltaTime)
	{
		eatStart = true;
		
		eatTimeAccum += deltaTime;
		while( eatTimeAccum >= EAT_TIME_INTERVAL )
		{
			eatTimeAccum -= EAT_TIME_INTERVAL;
			man.getInfo().hungerPoints += EAT_HUNGER_INCRE;
			man.getInfo().hungerPoints = Math.min(ManInfo.HUNGER_POINTS_MAX, man.getInfo().hungerPoints);
			decreFoodRS();
			
			if(		man.getInfo() .hungerPoints >= ManInfo.HUNGER_POINTS_MAX
				|| 	ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getFoodAmount()
						< EAT_FOOD_RESOURCE_AMOUNT)
				eatFinish();
		}
	}
	
	private ExecuteResult findFood(float deltaTime)
	{
		Building moveBuilding = null;
		if(man.getInfo().home != null)
			moveBuilding = man.getInfo().home;
		else
			moveBuilding = EntityInfoCollector.getInstance(EntityInfoCollector.class)
					.findNearestWareHouse(man.getPositionXWorld(), man.getPositionYWorld());
		
		man.setMoveDestination(moveBuilding.getPositionXWorld(), moveBuilding.getPositionYWorld());
		if( !man.move(deltaTime) )
		{
			doEat(deltaTime);
		}
		else
			man.getInfo().animeType = ManAnimeType.MOVE;
		
		return ExecuteResult.FALSE;
	}
	
	private void init()
	{
		ConditionNode judgeHunger = new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(man.getInfo().hungerPoints <= ManInfo.HUNGER_POINTS_FIND_FOOD)
					return ExecuteResult.TRUE;
				else
					return ExecuteResult.FALSE;
			}
		};
		
		ConditionNode judgeEatStart = new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(eatStart)
					return ExecuteResult.TRUE;
				else
					return ExecuteResult.FALSE;
			}
		};
		
		ConditionNode judgeFoodEnough = new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getFoodAmount()
						>= EAT_FOOD_RESOURCE_AMOUNT)
					return ExecuteResult.TRUE;
				else
					return ExecuteResult.FALSE;
			}
		};
		
		ActionNode findFoodExecute = new ActionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				findFood(deltaTime);
				return ExecuteResult.TRUE;
			}
		};
		
		this.addNode( new SelectorNode().addNode(judgeHunger)
										.addNode(judgeEatStart)
					)
			.addNode(judgeFoodEnough)
			.addNode(findFoodExecute);
		
	}
}

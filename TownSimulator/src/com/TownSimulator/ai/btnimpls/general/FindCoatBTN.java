package com.TownSimulator.ai.btnimpls.general;

import java.util.Iterator;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.Warehouse;

public class FindCoatBTN extends SequenceNode{
	private static final long serialVersionUID = -7283702940048921461L;
	private Man man;
//	private static final float EAT_TIME_INTERVAL = 2.0f;
//	private static final float EAT_HUNGER_INCRE = ManInfo.HUNGER_POINTS_MAX / 3.0f; // points per eat
//	private static final int   EAT_FOOD_RESOURCE_AMOUNT = 100;
	private static final int   COAT_TO_TEMPERATURE = 5;
//	private float eatTimeAccum = 0.0f;
//	private boolean eatStart = false;
	
	public FindCoatBTN(Man man)
	{
		this.man = man;
		
		init();
	}
	
//	private void decreCoatRS()
//	{
//		int needAmount = (int) ((ManInfo.TEMPERATURE_POINTS_MAX - man.getInfo().temperature) / 5);
//		int takeAmount = Math.min(needAmount, ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getResourceAmount(ResourceType.RS_COAT));
//		
//		for (Building building : EntityInfoCollector.getInstance(
//				EntityInfoCollector.class).getBuildings(BuildingType.WAREHOUSE)) {
//			Warehouse wareHouse = (Warehouse) building;
//			Iterator<Resource> itr = wareHouse.getStoredResource();
//			while (itr.hasNext()) {
//				Resource resource = itr.next();
//				if (resource.getType() == ResourceType.RS_COAT) {
//					int decre = Math.min(takeAmount, resource.getAmount());
//					takeAmount -= decre;
//					wareHouse.addStoredResource(resource.getType(), -decre);
//
//					if (takeAmount <= 0)
//						return;
//				}
//			}
//		}
//	}
//	
//	private void eatFinish()
//	{
//		eatStart = false;
//		eatTimeAccum = 0.0f;
//	}
	
	private void doGetCoat(float deltaTime)
	{
//		eatStart = true;
//		
//		eatTimeAccum += deltaTime;
//		while( eatTimeAccum >= EAT_TIME_INTERVAL )
//		{
//			eatTimeAccum -= EAT_TIME_INTERVAL;
//			man.getInfo().hungerPoints += EAT_HUNGER_INCRE;
//			man.getInfo().hungerPoints = Math.min(ManInfo.HUNGER_POINTS_MAX, man.getInfo().hungerPoints);
//			decreCoatRS();
//			
//			if(		man.getInfo() .hungerPoints >= ManInfo.HUNGER_POINTS_MAX
//				|| 	ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getFoodAmount()
//						< EAT_FOOD_RESOURCE_AMOUNT)
//				eatFinish();
//		}
		
		int needCoatAmount = (int) ((ManInfo.TEMPERATURE_POINTS_MAX - man.getInfo().temperature) / COAT_TO_TEMPERATURE);
		int takeCoatAmount = Math.min(needCoatAmount, ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getResourceAmount(ResourceType.RS_COAT));
		
		man.getInfo().temperature += takeCoatAmount * COAT_TO_TEMPERATURE;
		
		int reamainAmount = takeCoatAmount;
		for (Building building : EntityInfoCollector.getInstance(
				EntityInfoCollector.class).getBuildings(BuildingType.WAREHOUSE)) {
			Warehouse wareHouse = (Warehouse) building;
			Iterator<Resource> itr = wareHouse.getStoredResource();
			while (itr.hasNext()) {
				Resource resource = itr.next();
				if (resource.getType() == ResourceType.RS_COAT) {
					int decre = Math.min(reamainAmount, resource.getAmount());
					reamainAmount -= decre;
					wareHouse.addStoredResource(resource.getType(), -decre);

					if (reamainAmount <= 0)
						return;
				}
			}
		}
	}
	
	private ExecuteResult findCoat(float deltaTime)
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
			doGetCoat(deltaTime);
		}
		else
			man.getInfo().animeType = ManAnimeType.MOVE;
		
		return ExecuteResult.FALSE;
	}
	
	private void init()
	{
		ConditionNode judgeTemperature = new ConditionNode() {
			private static final long serialVersionUID = 4926750509596310709L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				if(man.getInfo().temperature <= ManInfo.TEMPERATURE_POINTS_FIND_COAT)
					return ExecuteResult.TRUE;
				else
					return ExecuteResult.FALSE;
			}
		};
		
		ConditionNode judgeCoatEnough = new ConditionNode() {
			private static final long serialVersionUID = -1547258567750446975L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				if(ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getResourceAmount(ResourceType.RS_COAT)
						> 0)
					return ExecuteResult.TRUE;
				else
					return ExecuteResult.FALSE;
			}
		};
		
		ActionNode findCoatExecute = new ActionNode() {
			private static final long serialVersionUID = 2551367178740081730L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				findCoat(deltaTime);
				return ExecuteResult.TRUE;
			}
		};
		
		this.addNode(judgeTemperature)
			.addNode(judgeCoatEnough)
			.addNode(findCoatExecute);
		
	}
}

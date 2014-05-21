package com.TownSimulator.ai.btnimpls.general;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.EntityInfoCollector.EntityInfoCollectorListener;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.LivingHouse;
import com.TownSimulator.utility.Settings;

public class FindHomeBTN extends SequenceNode{
	private static final long serialVersionUID = -2390475739803251930L;
	private static final double DIST_THRESHOILD = Settings.UNIT * 3.0;
	private Man man;
	private EntityInfoCollectorListener entityInfoListener;
	private boolean bNewLivingBuilding = false;
	
	public FindHomeBTN(Man man)
	{
		this.man = man;
		init();
		
		entityInfoListener = new EntityInfoCollectorListener() {
			
			@Override
			public void newBuildingAdded(Building building) {
				if(building instanceof LivingHouse)
					bNewLivingBuilding = true;
			}
		};
	}
	
	
	public LivingHouse findBestLivingHouse(float x, float y)
	{
		LivingHouse result = null;
		
		LivingHouse lowcost = null;
		double dstMin0 = -1.0f;
		LivingHouse apartment = null;
		double dstMin1 = -1.0f;
		
		if(man.getInfo().home != null)
		{
			if(man.getInfo().home.getType() == BuildingType.LOW_COST_HOUSE)
			{
				lowcost = man.getInfo().home;
				dstMin0 =	Math.pow(man.getInfo().home.getPositionXWorld() - x, 2)
						+	Math.pow(man.getInfo().home.getPositionYWorld() - y, 2);
			}
			else if(man.getInfo().home.getType() == BuildingType.APARTMENT)
			{
				apartment = man.getInfo().home;
				dstMin1 =	Math.pow(man.getInfo().home.getPositionXWorld() - x, 2)
						+	Math.pow(man.getInfo().home.getPositionYWorld() - y, 2);
			}
				
		}
		
		for (Building building : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllBuildings()) {
			
			if(building instanceof LivingHouse)
			{
				LivingHouse lm = (LivingHouse) building;
				if( !lm.hasAvailableRoom() )
					continue;
				
				if(lm.getType() == BuildingType.LOW_COST_HOUSE)
				{
					double dst = 	Math.pow(building.getPositionXWorld() - x, 2)
								+	Math.pow(building.getPositionYWorld() - y, 2);
					
					if(dstMin0 == -1.0f || dst < dstMin0)
					{
						dstMin0 = dst;
						lowcost = (LivingHouse) building;
					}
				}
				else if(lm.getType() == BuildingType.APARTMENT)
				{
					double dst = 	Math.pow(building.getPositionXWorld() - x, 2)
								+	Math.pow(building.getPositionYWorld() - y, 2);
				
					if(dstMin1 == -1.0f || dst < dstMin1)
					{
						dstMin1 = dst;
						apartment = (LivingHouse) building;
					}
				}
			}
		}
		
		if(lowcost == null)
		{
			if(apartment == null)
				result = null;
			else
				result = apartment;
		}
		else
		{
			if(apartment == null)
				result = lowcost;
			else
			{
//				double dist0 =  Math.pow(lowcost.getPositionXWorld() - x, 2)
//							+	Math.pow(lowcost.getPositionYWorld() - y, 2);
//				double dist1 =  Math.pow(apartment.getPositionXWorld() - x, 2)
//							+	Math.pow(apartment.getPositionYWorld() - y, 2);
				
				if( (Math.pow(dstMin1, 0.5) - DIST_THRESHOILD) <= Math.pow(dstMin0, 0.5) )
					result = apartment;
				else
					result = lowcost;
			}
		}
		
		return result;
	}
	
	private LivingHouse findHome()
	{
		LivingHouse house = null;
		
		float x;
		float y;
		
		if(man.getInfo().workingBuilding != null)
		{
			x = man.getInfo().workingBuilding.getPositionXWorld();
			y = man.getInfo().workingBuilding.getPositionYWorld();
			
		}
		else
		{
			if(man.getInfo().home != null)
				return null;
			
			x = man.getPositionXWorld();
			y = man.getPositionYWorld();
		}
		
//		for (Building building : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllBuildings()) {
//			if(building.getType() == BuildingType.LOW_COST_HOUSE || building.getType() == BuildingType.APARTMENT)
//			{
//				house = (LivingHouse)building;
//				if(house.hasAvailableRoom())
//					return house;
//			}
//		}
		
		return findBestLivingHouse(x, y);
	}
	
	private void init()
	{
		ConditionNode judgeHome = new ConditionNode() {
			private static final long serialVersionUID = -6854019655291676036L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				if(man.getInfo().home == null || bNewLivingBuilding)
					return ExecuteResult.TRUE;
				else {
//					if (man.getInfo().home instanceof LowCostHouse) {
//						man.getInfo().hpResideInLowCostHouse(deltaTime);
//					}
//					else if(man.getInfo().home instanceof ApartmentHouse)
//						man.getInfo().hpResideInApartment(deltaTime);
					return ExecuteResult.FALSE;
				}
			}
		};
		
		ActionNode findHomeExecute = new ActionNode() {
			private static final long serialVersionUID = 1210120106749191549L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				LivingHouse home = findHome();

				if(home != null && home != man.getInfo().home)
				{
					if(man.getInfo().home != null)
						man.getInfo().home.removeResident(man.getInfo());
					
					home.addResident(man.getInfo());
				}
				return ExecuteResult.FALSE;
			}
		};
		
		this.addNode(judgeHome)
			.addNode(findHomeExecute);
	}
	
}

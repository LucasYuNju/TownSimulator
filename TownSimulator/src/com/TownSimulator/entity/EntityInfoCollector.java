package com.TownSimulator.entity;

import com.TownSimulator.ai.btnimpls.construct.ConstructProject;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.WareHouse;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.utils.Array;

public class EntityInfoCollector extends Singleton{
	private Array<Man> 		mPeople;
	private Array<Building>	mBuildings;
	private Array<ConstructProject> mConstructProjs;
	
	private EntityInfoCollector()
	{
		mPeople = new Array<Man>();
		mBuildings = new Array<Building>();
		mConstructProjs = new Array<ConstructProject>();
	}
	
	public void addMan(Man man)
	{
		mPeople.add(man);
	}
	
	public Array<Man> getAllPeople()
	{
		return mPeople;
	}
	
	public void addConstructProj(ConstructProject proj)
	{
		mConstructProjs.add(proj);
	}
	
	public void removeConstructProj(ConstructProject proj)
	{
		mConstructProjs.removeValue(proj, false);
	}
	
	public Array<ConstructProject> getAllConstructProjs()
	{
		return mConstructProjs;
	}
	
	public void addBuilding(Building building)
	{
		mBuildings.add(building);
	}
	
	public Array<Building> getAllBuildings()
	{
		return mBuildings;
	}
	
	public class WareHouseFindResult
	{
		public WareHouse wareHouse;
		public int amount;
	}
	
	public WareHouseFindResult findNearestWareHouseWithRs( ResourceType type, int amount, float x, float y )
	{
		WareHouseFindResult result = new WareHouseFindResult();
		Array<Building> allBuildings = EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllBuildings();
		Array<WareHouse> wareHouseWithRs = new Array<WareHouse>();
		double dstMin = -1.0f;
		for (int i = 0; i < allBuildings.size; i++) {
			Building building = allBuildings.get(i);
			if(building.getType() == BuildingType.WAREHOUSE)
			{
				WareHouse wareHouse = (WareHouse) building;
				if(wareHouse.getWareHousrResourceAmount(type) >= amount)
				{
					double dst = 	Math.pow(wareHouse.getPositionXWorld() - x, 2)
								+	Math.pow(wareHouse.getPositionYWorld() - y, 2);
					if(dstMin == -1.0f || dst < dstMin)
					{
						dstMin = dst;
						result.wareHouse = wareHouse;
						result.amount = amount;
					}
				}
				else if(wareHouse.getWareHousrResourceAmount(type) > 0)
					wareHouseWithRs.add(wareHouse);
			}
		}
		
		dstMin = -1.0f;
		if(result.wareHouse == null)
		{
			for (int i = 0; i < wareHouseWithRs.size; i++) {
				WareHouse wareHouse = wareHouseWithRs.get(i);
				double dst = 	Math.pow(wareHouse.getPositionXWorld() - x, 2)
							+	Math.pow(wareHouse.getPositionYWorld() - y, 2);
				
				if(dstMin == -1.0f || dst < dstMin)
				{
					dstMin = dst;
					result.wareHouse = wareHouse;
					result.amount = wareHouse.getWareHousrResourceAmount(type);;
				}
			}
		}
		
		return result;
	}
	
}

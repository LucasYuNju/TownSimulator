package com.TownSimulator.entity;

import com.TownSimulator.ai.btnimpls.construct.ConstructionProject;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.utils.Array;

public class EntityInfoCollector extends Singleton{
	private Array<Man> 		manList;
	private Array<Building>	buildingList;
	private Array<ConstructionProject> constructProjsList;
	
	private EntityInfoCollector()
	{
		manList = new Array<Man>();
		buildingList = new Array<Building>();
		constructProjsList = new Array<ConstructionProject>();
	}
	
	public void addMan(Man man)
	{
		manList.add(man);
	}
	
	public void removeMan(Man man)
	{
		manList.removeValue(man, false);
	}
	
	public Array<Man> getAllMan()
	{
		return manList;
	}
	
	public void addConstructProj(ConstructionProject proj)
	{
		constructProjsList.add(proj);
	}
	
	public void removeConstructProj(ConstructionProject proj)
	{
		constructProjsList.removeValue(proj, false);
	}
	
	public Array<ConstructionProject> getAllConstructProjs()
	{
		return constructProjsList;
	}
	
	public void addBuilding(Building building)
	{
		buildingList.add(building);
	}
	
	public void removeBuilding(Building building)
	{
		buildingList.removeValue(building, false);
	}
	
	public Array<Building> getAllBuildings()
	{
		return buildingList;
	}
	
	public class WareHouseFindResult
	{
		public Warehouse wareHouse;
		public int amount;
	}
	
	public Warehouse findNearestWareHouse(float x, float y)
	{
		double dstMin = -1.0f;
		Warehouse house = null;
		for (Building building : buildingList) {
			if(building.getType() == BuildingType.WAREHOUSE)
			{
				Warehouse wareHouse = (Warehouse)building;
				double dst = 	Math.pow(wareHouse.getPositionXWorld() - x, 2)
							+	Math.pow(wareHouse.getPositionYWorld() - y, 2);
				
				if(dstMin == -1.0f || dst < dstMin)
				{
					dstMin = dst;
					house = wareHouse;
				}
			}
		}
		
		return house;
	}
	
	public WareHouseFindResult findNearestWareHouseWithRs( ResourceType type, int amount, float x, float y )
	{
		WareHouseFindResult result = new WareHouseFindResult();
		Array<Building> allBuildings = EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllBuildings();
		Array<Warehouse> wareHouseWithRs = new Array<Warehouse>();
		double dstMin = -1.0f;
		for (int i = 0; i < allBuildings.size; i++) {
			Building building = allBuildings.get(i);
			if(building.getType() == BuildingType.WAREHOUSE)
			{
				Warehouse wareHouse = (Warehouse) building;
				if(wareHouse.getStoredResourceAmount(type) >= amount)
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
				else if(wareHouse.getStoredResourceAmount(type) > 0)
					wareHouseWithRs.add(wareHouse);
			}
		}
		
		dstMin = -1.0f;
		if(result.wareHouse == null)
		{
			for (int i = 0; i < wareHouseWithRs.size; i++) {
				Warehouse wareHouse = wareHouseWithRs.get(i);
				double dst = 	Math.pow(wareHouse.getPositionXWorld() - x, 2)
							+	Math.pow(wareHouse.getPositionYWorld() - y, 2);
				
				if(dstMin == -1.0f || dst < dstMin)
				{
					dstMin = dst;
					result.wareHouse = wareHouse;
					result.amount = wareHouse.getStoredResourceAmount(type);;
				}
			}
		}
		
		return result;
	}
	
}

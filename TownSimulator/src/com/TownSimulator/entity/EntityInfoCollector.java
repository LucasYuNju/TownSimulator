package com.TownSimulator.entity;

import java.util.HashMap;

import com.TownSimulator.ai.btnimpls.construct.ConstructionProject;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.utils.Array;

public class EntityInfoCollector extends Singleton{
	private Array<Man> 		manList;
	private HashMap<BuildingType, Array<Building>> buildingsMap;
	private Array<Building>	buildingsList;
	private Array<ConstructionProject> constructProjsList;
	
	private EntityInfoCollector()
	{
		manList = new Array<Man>();
		buildingsList = new Array<Building>();
		buildingsMap = new HashMap<BuildingType, Array<Building>>();
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
		BuildingType type = building.getType();
		if( !buildingsMap.containsKey(type) )
			buildingsMap.put(type, new Array<Building>());
		
		buildingsMap.get(type).add(building);
		buildingsList.add(building);
	}
	
	public void removeBuilding(Building building)
	{
		//buildingList.removeValue(building, false);
		if(buildingsMap.containsKey(building.getType()))
			buildingsMap.get(building.getType()).removeValue(building, false);
		
		buildingsList.removeValue(building, false);
	}
	
	public Array<Building> getBuildingWithType(BuildingType type)
	{
		if( !buildingsMap.containsKey(type) )
			buildingsMap.put(type, new Array<Building>());
		
		return buildingsMap.get(type);
	}
	
	public Array<Building> getAllBuildings()
	{
		return buildingsList;
	}
	
	public class WareHouseFindResult
	{
		public Warehouse wareHouse;
		public int amount;
	}
	
	public Warehouse findNearestWareHouse(float x, float y)
	{
		if( !buildingsMap.containsKey(BuildingType.WAREHOUSE) )
			return null;
		
		double dstMin = -1.0f;
		Warehouse house = null;
		for (Building building : buildingsMap.get(BuildingType.WAREHOUSE)) {
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
		if( !buildingsMap.containsKey(BuildingType.WAREHOUSE) )
			return null;
		
		WareHouseFindResult result = new WareHouseFindResult();
		Array<Building> allBuildings = getBuildingWithType(BuildingType.WAREHOUSE);
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

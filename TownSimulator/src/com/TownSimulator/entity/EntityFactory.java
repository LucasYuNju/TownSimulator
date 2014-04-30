package com.TownSimulator.entity;

import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.WareHouse;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;

public class EntityFactory {
	
	public static MapResource createMapObj(MapResourceType objType)
	{
		MapResource obj = null;
		switch (objType) {
		case TREE:
			obj = new MapResource(ResourceManager.getInstance(ResourceManager.class).createSprite("map_tree"));
			break;
			
		default:
			break;
		}
		return obj;
	}
	
	public static Building createBuilding(BuildingType buildingType)
	{
		Building building = null;
		int xGridSize = 0;
		int yGridSize = 0;
		float yDrawScale = 1.0f;
		
		switch (buildingType) {
		case LOW_COST_HOUSE:
			xGridSize = 2;
			yGridSize = 1;
			yDrawScale = 2.2f;
			building = new Building(ResourceManager.getInstance(ResourceManager.class).createSprite("building_low_cost_house"), buildingType);
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 10);
			building.setNeededConstructionResource(ResourceType.RS_STONE, 5);
			building.setNeededConstructionWork(20);
			break;
		case WAREHOUSE:
			xGridSize = 2;
			yGridSize = 1;
			yDrawScale = 2.2f;
			building = new WareHouse();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 20);
			building.setNeededConstructionResource(ResourceType.RS_STONE, 5);
			building.setNeededConstructionWork(30);
			break;
		case FARM_HOUSE:
			xGridSize = 2;
			yGridSize = 1;
			yDrawScale = 2.2f;
			building = new FarmHouse();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 20);
			building.setNeededConstructionResource(ResourceType.RS_STONE, 5);
			building.setNeededConstructionWork(30);
			break;

		default:
			break;
		}
		
		if(building != null)
		{
			building.setDrawAABBLocal(	0.0f, 						yGridSize * Settings.UNIT * 0.4f,
										xGridSize * Settings.UNIT, 	yGridSize * Settings.UNIT * (0.4f + yDrawScale) );
			
			building.setCollisionAABBLocal(	0.1f, 								0.1f, 
											xGridSize * Settings.UNIT - 0.2f,	yGridSize * Settings.UNIT - 0.2f);
		}
		
		return building;
	}
}

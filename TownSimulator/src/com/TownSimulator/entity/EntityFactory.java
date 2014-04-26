package com.TownSimulator.entity;

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
		case WOOD_HOUSE:
			building = new Building(ResourceManager.getInstance(ResourceManager.class).createSprite("building_wood_house"));
			xGridSize = 2;
			yGridSize = 2;
			yDrawScale = 1.2f;
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

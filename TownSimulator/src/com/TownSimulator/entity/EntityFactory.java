package com.TownSimulator.entity;

import com.TownSimulator.entity.building.ApartmentHouse;
import com.TownSimulator.entity.building.Bar;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.CoatFactory;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FellingHouse;
import com.TownSimulator.entity.building.Hospital;
import com.TownSimulator.entity.building.LowCostHouse;
import com.TownSimulator.entity.building.PowerStation;
import com.TownSimulator.entity.building.Ranch;
import com.TownSimulator.entity.building.School;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.entity.building.Well;
//github.com/LuciusYu/TownSimulator.git
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;

public class EntityFactory {
	
	public static MapEntity createMapObj(MapEntityType objType)
	{
		MapEntity obj = null;
		switch (objType) {
		case TREE:
			obj = new MapEntity(ResourceManager.getInstance(ResourceManager.class).createSprite("map_tree"));
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
		case APARTMENT:
			xGridSize = 3;
			yGridSize = 2;
			yDrawScale = 1.5f;
			building = new ApartmentHouse();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 2000);
			building.setNeededConstructionWork(20);
			break;
		case LOW_COST_HOUSE:
			xGridSize = 3;
			yGridSize = 2;
			yDrawScale = 1.5f;
			building = new LowCostHouse();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 2000);
			building.setNeededConstructionWork(20);
			break;
		case WAREHOUSE:
			xGridSize = 3;
			yGridSize = 1;
			yDrawScale = 1.5f;
			building = new Warehouse();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 4000);
			building.setNeededConstructionWork(40);
			break;
		case FARM_HOUSE:
			xGridSize = 2;
			yGridSize = 2;
			yDrawScale = 1.0f;
			building = new FarmHouse();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 2500);
			building.setNeededConstructionWork(25);
			break;
		case FELLING_HOUSE:
			xGridSize = 2;
			yGridSize = 1;
			yDrawScale = 1.5f;
			building = new FellingHouse();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 2500);
			building.setNeededConstructionWork(25);
			break;
		case POWER_STATION:
			xGridSize = 1;
			yGridSize = 1;
			yDrawScale = 2.0f;
			building = new PowerStation();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 5000);
			building.setNeededConstructionWork(50);
			break;
		case COAT_FACTORY:
			xGridSize = 3;
			yGridSize = 2;
			yDrawScale = 1.0f;
			building = new CoatFactory();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 4000);
			building.setNeededConstructionWork(40);
			break;
		case RANCH:
			xGridSize = 2;
			yGridSize = 1;
			yDrawScale = 1.5f;
			building = new Ranch();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 2500);
			building.setNeededConstructionWork(25);
			break;
		case Hospital:
			xGridSize = 3;
			yGridSize = 3;
			yDrawScale = 1.3f;
			building = new Hospital();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 4000);
			building.setNeededConstructionWork(40);
			break;
		case Bar:
			xGridSize = 2;
			yGridSize = 1;
			yDrawScale = 2.2f;
			building = new Bar();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 4000);
			building.setNeededConstructionWork(40);
			break;
		case SCHOOL:
			xGridSize=2;
			yGridSize=2;
			yDrawScale=1.5f;
			building=new School();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 5000);
			building.setNeededConstructionWork(50);
			break;
		case WELL:
			xGridSize=1;
			yGridSize=1;
			yDrawScale=1.5f;
			building=new Well();
			building.setNeededConstructionResource(ResourceType.RS_WOOD, 2000);
			building.setNeededConstructionWork(20);
		default:
			break;
		}
		
		if(building != null)
		{
			building.setDrawAABBLocal(	0.0f, yGridSize * Settings.UNIT * 0.2f,
										xGridSize * Settings.UNIT, 	yGridSize * Settings.UNIT * (0.2f + yDrawScale) );
			building.setCollisionAABBLocal(	0.1f, 0.1f, 
											xGridSize * Settings.UNIT - 0.2f,	yGridSize * Settings.UNIT - 0.2f);
		}
		
		return building;
	}
}

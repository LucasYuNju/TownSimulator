package com.TownSimulator.entity;

import com.TownSimulator.entity.building.ApartmentHouse;
import com.TownSimulator.entity.building.Bar;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.CoatFactory;
import com.TownSimulator.entity.building.ConstructionCompany;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FellingHouse;
import com.TownSimulator.entity.building.Hospital;
import com.TownSimulator.entity.building.LowCostHouse;
import com.TownSimulator.entity.building.MoneyProducingBuilding;
import com.TownSimulator.entity.building.PowerStation;
import com.TownSimulator.entity.building.Ranch;
import com.TownSimulator.entity.building.School;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.entity.building.Well;
import com.TownSimulator.utility.Settings;
//github.com/LuciusYu/TownSimulator.git
import com.TownSimulator.utility.Settings.MPData;

public class EntityFactory {
	
//	public static MapEntity createMapObj(MapEntityType objType)
//	{
//		MapEntity obj = null;
//		switch (objType) {
//		case TREE:
//			obj = new MapEntity("map_tree");
//			break;
//			
//		default:
//			break;
//		}
//		return obj;
//	}
	
	public static Building createBuilding(BuildingType buildingType)
	{
		Building building = null;
		int xGridSize = 0;
		int yGridSize = 0;
		float yDrawScale = 1.0f;
		MPData mpData;
		
		switch (buildingType) {
		case Apartment:
			xGridSize = 3;
			yGridSize = 2;
			yDrawScale = 1.5f;
			building = new ApartmentHouse();
			break;
		case LowCostHouse:
			xGridSize = 3;
			yGridSize = 2;
			yDrawScale = 1.5f;
			building = new LowCostHouse();
			break;
		case Warehouse:
			xGridSize = 3;
			yGridSize = 1;
			yDrawScale = 1.5f;
			building = new Warehouse();
			break;
		case FarmHouse:
			xGridSize = 2;
			yGridSize = 2;
			yDrawScale = 1.0f;
			building = new FarmHouse();
			break;
		case FellingHouse:
			xGridSize = 3;
			yGridSize = 2;
			yDrawScale = 1.0f;
			building = new FellingHouse();
			break;
		case PowerStation:
			xGridSize = 1;
			yGridSize = 1;
			yDrawScale = 2.0f;
			building = new PowerStation();
			break;
		case CoatFactory:
			xGridSize = 4;
			yGridSize = 3;
			yDrawScale = 1.0f;
			building = new CoatFactory();
			break;
		case Ranch:
			xGridSize = 2;
			yGridSize = 1;
			yDrawScale = 1.5f;
			building = new Ranch();
			break;
		case Hospital:
			xGridSize = 3;
			yGridSize = 3;
			yDrawScale = 1.3f;
			building = new Hospital();
			break;
		case Bar:
			xGridSize = 3;
			yGridSize = 2;
			yDrawScale = 1.5f;
			building = new Bar();
			break;
		case School:
			xGridSize=4;
			yGridSize=3;
			yDrawScale=1.5f;
			building = new School();
			break;
		case Well:
			xGridSize=2;
			yGridSize=2;
			yDrawScale=0.8f;
			building = new Well();
			break;
		case ConstrctionCompany:
			xGridSize=2;
			yGridSize=2;
			yDrawScale=1.1f;
			building = new ConstructionCompany();
			break;
		case MP_Potato:
			xGridSize=1;
			yGridSize=1;
			yDrawScale=1.0f;
			mpData = Settings.mpBuildingDataMap.get(buildingType);
			building = new MoneyProducingBuilding(mpData.textureName, buildingType);
			break;
		case MP_MouseWheel:
			xGridSize=2;
			yGridSize=2;
			yDrawScale=1.0f;
			mpData = Settings.mpBuildingDataMap.get(buildingType);
			building = new MoneyProducingBuilding(mpData.textureName, buildingType);
			break;
		case MP_Factory:
			xGridSize=2;
			yGridSize=2;
			yDrawScale=1.0f;
			mpData = Settings.mpBuildingDataMap.get(buildingType);
			building = new MoneyProducingBuilding(mpData.textureName, buildingType);
			break;
		case MP_Storm:
			xGridSize=3;
			yGridSize=3;
			yDrawScale=1.0f;
			mpData = Settings.mpBuildingDataMap.get(buildingType);
			building = new MoneyProducingBuilding(mpData.textureName, buildingType);
			break;
		case MP_Vocalno:
			xGridSize=4;
			yGridSize=4;
			yDrawScale=1.0f;
			mpData = Settings.mpBuildingDataMap.get(buildingType);
			building = new MoneyProducingBuilding(mpData.textureName, buildingType);
			break;
		case MP_BalckHole:
			xGridSize=5;
			yGridSize=5;
			yDrawScale=1.0f;
			mpData = Settings.mpBuildingDataMap.get(buildingType);
			building = new MoneyProducingBuilding(mpData.textureName, buildingType);
			break;
		default:
			break;
		}
		
		if( !buildingType.isMoneyProducing() )
		{
			for (Resource rs : Settings.normalBuildingRSMap.get(buildingType).needResource) {
				building.setNeededConstructionResource(rs.getType(), rs.getAmount());
			}
			building.setNeededConstructionWork(Settings.normalBuildingRSMap.get(buildingType).needWork);
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

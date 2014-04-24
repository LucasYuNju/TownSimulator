package com.townSimulator.game.objs;

import com.badlogic.gdx.utils.Array;
import com.townSimulator.utility.ResourceManager;
import com.townSimulator.utility.Settings;

public class ObjectFactory {
	/*
	 * 没用
	 */
	private static Array<DrawableObject> mMapObjs = new Array<DrawableObject>();
	
	public static MapObject createMapObj(MapObjectType objType)
	{
		MapObject obj = null;
		switch (objType) {
		case TREE:
			obj = new MapObject(ResourceManager.createSprite("map_tree"));
			mMapObjs.add(obj);
			break;
//		case BUILDING:
//			obj = new MapObject(ResourceManager.createSprite("building_house"));
//			obj.setDrawSize(Settings.UNIT * 2, Settings.UNIT * 2);
//			mMapObjs.add(obj);			
//			break;
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
			building = new Building(ResourceManager.createSprite("building_wood_house"));
			xGridSize = 2;
			yGridSize = 2;
			yDrawScale = 1.2f;
			break;

		default:
			break;
		}
		
		if(building != null)
		{
			building.setDrawSize(xGridSize * Settings.UNIT, yGridSize * Settings.UNIT * yDrawScale);
			building.setRelativeCollisionBounds(0.1f, 								-(yGridSize * Settings.UNIT - 0.2f) * 0.4f,
												xGridSize * Settings.UNIT - 0.2f, 	 (yGridSize * Settings.UNIT - 0.2f) * 0.6f);
		}
		
		return building;
	}
}

package com.townSimulator.game.objs;

import com.badlogic.gdx.utils.Array;
import com.townSimulator.utility.ResourceManager;
import com.townSimulator.utility.Settings;

public class MapObjectFactory {
	/*
	 * 没用
	 */
	private static Array<DrawableObject> mMapObjs = new Array<DrawableObject>();
	
	public static MapObject createMapObj(MapObjectType objType)
	{
		MapObject obj = null;
		switch (objType) {
		case TREE:
			obj = new MapObject(ResourceManager.createSprite("tree"));
			mMapObjs.add(obj);
			break;
		case BUILDING:
			obj = new MapObject(ResourceManager.createSprite("building_house"));
			obj.setDrawSize(Settings.UNIT, Settings.UNIT);
			mMapObjs.add(obj);			
			break;
		default:
			break;
		}
		return obj;
	}
}

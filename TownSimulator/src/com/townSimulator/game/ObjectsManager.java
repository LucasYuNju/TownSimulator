package com.townSimulator.game;

import com.badlogic.gdx.utils.Array;
import com.townSimulator.utility.ResourceManager;

public class ObjectsManager {
	private static Array<GameDrawableObject> mMapObjs = new Array<GameDrawableObject>();
	
	public static MapObject createMapObj(MapObjectType objType)
	{
		MapObject obj = null;
		switch (objType) {
		case TREE:
			obj = new MapObject(ResourceManager.createSprite("tree"));
			mMapObjs.add(obj);
			break;

		default:
			break;
		}
		
		return obj;
	}
}

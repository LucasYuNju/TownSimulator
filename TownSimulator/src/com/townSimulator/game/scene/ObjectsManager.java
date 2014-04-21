package com.townSimulator.game.scene;

import com.badlogic.gdx.utils.Array;
import com.townSimulator.game.objs.MapObject;
import com.townSimulator.game.objs.MapObjectType;
import com.townSimulator.game.render.GameDrawableObject;
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

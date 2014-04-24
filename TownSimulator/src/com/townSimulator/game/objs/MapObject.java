package com.townSimulator.game.objs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.townSimulator.utility.AxisAlignedBoundingBox;

public class MapObject extends BaseObject{
	
	public MapObject(Sprite sp) {
		super(sp);
		
		mCollisionAABB = new AxisAlignedBoundingBox();
	}
	
}

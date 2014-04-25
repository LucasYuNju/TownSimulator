package com.townSimulator.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.townSimulator.utility.AxisAlignedBoundingBox;

public class MapEntity extends Entity{
	
	public MapEntity(Sprite sp) {
		super(sp);
		
		mCollisionAABB = new AxisAlignedBoundingBox();
	}
	
}

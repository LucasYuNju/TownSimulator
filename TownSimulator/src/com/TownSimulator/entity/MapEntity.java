package com.TownSimulator.entity;

import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MapEntity extends Entity{
	
	public MapEntity(Sprite sp) {
		super(sp);
		
		mCollisionAABB = new AxisAlignedBoundingBox();
	}
	
}

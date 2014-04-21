package com.townSimulator.game.scene;

import com.townSimulator.utility.AxisAlignedBoundingBox;


public interface Collidable {
	public AxisAlignedBoundingBox getCollisionBounds();
}

package com.townSimulator.game.scene;

import com.townSimulator.utility.AxisAlignedBoundingBox;

public interface QuadTreeManageble {	
	public AxisAlignedBoundingBox getBoundingBox(QuadTreeType type);
}

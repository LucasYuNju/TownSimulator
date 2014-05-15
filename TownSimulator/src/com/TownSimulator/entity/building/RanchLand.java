package com.TownSimulator.entity.building;

import com.TownSimulator.entity.Entity;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RanchLand extends Entity{

	public RanchLand() {
		super((Sprite)null);
		
		setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
		setCollisionAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
	}

}

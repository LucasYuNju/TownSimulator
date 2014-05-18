package com.TownSimulator.entity.building;

import com.TownSimulator.entity.Entity;
import com.TownSimulator.utility.Settings;

public class RanchLand extends Entity{
	private static final long serialVersionUID = 8999444247880857705L;

	public RanchLand() {
		super(null);
		
		setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
		setCollisionAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
	}

}

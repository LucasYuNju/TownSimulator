package com.TownSimulator.entity;

import com.TownSimulator.utility.Settings;


public class Grass extends Entity{

	public Grass(String texName) {
		super(texName);
		
		setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT * 0.5f);
	}

}

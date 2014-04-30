package com.TownSimulator.entity.building;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.ManInfo;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LowCostHouse extends Building{
	
	private List<ManInfo> residents;
	private int Capacity;
	
	public LowCostHouse(Sprite sp, BuildingType type) {
		super(sp, type);
		residents = new ArrayList<ManInfo>();
	}
	
	public LowCostHouse(String textureName, BuildingType type) {
		super(textureName, type);
		residents = new ArrayList<ManInfo>();
	}
	
	public boolean addResident(ManInfo newResident) {
		return true;
	}
}

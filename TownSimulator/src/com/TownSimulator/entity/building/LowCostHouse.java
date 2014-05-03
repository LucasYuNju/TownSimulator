package com.TownSimulator.entity.building;

import java.util.ArrayList;

import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.ManInfo.Gender;

public class LowCostHouse extends LivingHouse{
	
	public LowCostHouse() {
		super("building_low_cost_house", BuildingType.LOW_COST_HOUSE);
		fakeData();
	}
	
	private void fakeData() {
		residents = new ArrayList<ManInfo>();
		residents.add(new ManInfo(15, Gender.Male));
		residents.add(new ManInfo(25, Gender.Female));
		residents.add(new ManInfo(51, Gender.Male));
		residents.add(new ManInfo(13, Gender.Male));
		residents.add(new ManInfo(24, Gender.Female));
		residents.add(new ManInfo(32, Gender.Male));
		residents.add(new ManInfo(33, Gender.Female));
		capacity = residents.size() * 2;
	}
}

package com.TownSimulator.entity.building;

import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.ManInfo.Gender;

public class LowCostHouse extends LivingHouse{
	
	public LowCostHouse() {
		super("building_low_cost_house", BuildingType.LOW_COST_HOUSE);
		fakeData();
	}
	
	private void fakeData() {
		addResident(new ManInfo(15, Gender.Male));
		addResident(new ManInfo(25, Gender.Female));
		addResident(new ManInfo(51, Gender.Male));
		addResident(new ManInfo(24, Gender.Female));
		addResident(new ManInfo(32, Gender.Male));
		addResident(new ManInfo(33, Gender.Female));
	}
}

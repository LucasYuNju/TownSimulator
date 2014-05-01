package com.TownSimulator.entity.building;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.ManInfo.Gender;

public class LowCostHouse extends Building{
	
	private List<ManInfo> residents;
	private int capacity;
	
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
	
	public boolean addResident(ManInfo newResident) {
		if(capacity > residents.size()) {
			residents.add(newResident);
			return true;
		}
		return false;
	}
	
	@Override
	public String[][] getViewData() {
		//name, gender, age
		String[][] data = new String[residents.size()][3];
		for(int i=0; i<residents.size(); i++) {
			data[i][0] = residents.get(i).getName();
			data[i][1] = residents.get(i).getGender().toString();
			data[i][2] = residents.get(i).getAge() + "";
		}
		return data;
	}
}

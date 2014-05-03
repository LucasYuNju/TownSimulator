package com.TownSimulator.entity.building;

import java.util.List;

import com.TownSimulator.entity.ManInfo;

public class LivingHouse extends Building{
	protected List<ManInfo> residents;
	protected int capacity;
	
	public LivingHouse(String textureName, BuildingType type) {
		super(textureName, type);
	}
	
	public boolean addResident(ManInfo newResident) {
		if(capacity > residents.size()) {
			residents.add(newResident);
			newResident.home = this;
			updataViewWindow();
			return true;
		}
		return false;
	}
	
	public void removeResident(ManInfo resident)
	{
		if(residents.remove(resident)) {
			resident.home = null;
			updataViewWindow();
		}
	}
	
	public boolean hasAvailableRoom()
	{
		return residents.size() < capacity;
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

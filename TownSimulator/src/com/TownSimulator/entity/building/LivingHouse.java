package com.TownSimulator.entity.building;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.ManInfo;

public class LivingHouse extends Building{
	protected List<ManInfo> residents;
	protected int capacity;
	
	public LivingHouse(String textureName, BuildingType type) {
		super(textureName, type);
		residents = new ArrayList<ManInfo>();
		capacity = 8;
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
	public List<List<String>> getViewData() {
		List<List<String>> list = new ArrayList<List<String>>();
		for(ManInfo resident : residents) {
			list.add(resident.toStringList());
		}
		return list;
	}
}

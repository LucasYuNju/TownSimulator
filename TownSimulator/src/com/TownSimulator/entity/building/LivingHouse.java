package com.TownSimulator.entity.building;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.ui.building.view.ScrollViewWindow;

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
			updateViewWindow();
			return true;
		}
		return false;
	}
	
	public void removeResident(ManInfo resident)
	{
		if(residents.remove(resident)) {
			resident.home = null;
			updateViewWindow();
		}
	}
	
	public boolean hasAvailableRoom()
	{
		return residents.size() < capacity;
	}
	
	
	public List<List<String>> getViewData() {
		List<List<String>> list = new ArrayList<List<String>>();
		for(ManInfo resident : residents) {
			list.add(resident.toStringList());
		}
		return list;
	}

	
	/*
	 * 将数据更新到viewWindow
	 */
	protected void updateViewWindow() {
		if(viewWindow instanceof ScrollViewWindow) {
			ScrollViewWindow scrollViewWindow = (ScrollViewWindow) viewWindow;
			scrollViewWindow.updateData(getViewData());
		}
	}
}

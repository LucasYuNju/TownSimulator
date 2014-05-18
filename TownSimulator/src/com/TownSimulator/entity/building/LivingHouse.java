package com.TownSimulator.entity.building;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.ScrollViewWindow;
import com.TownSimulator.ui.building.view.UndockedWindow;

public class LivingHouse extends Building{
	protected List<ManInfo> residents;
	protected int capacity;
	protected ScrollViewWindow scrollWindow;
	
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
	
	@Override
	public void destroy() {
		super.destroy();
		
		ArrayList<ManInfo> infos = new ArrayList<ManInfo>(residents);
		for (ManInfo m : infos) {
			removeResident(m);
		}
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
			list.add(resident.toResidentStringList());
		}
		if (list.isEmpty()) {
			list.add(ManInfo.getEmptyResidentStringList());
		}
		return list;
	}
	
	/*
	 * 将数据更新到viewWindow
	 */
	protected void updateViewWindow() {
		//if(undockedWindow instanceof ScrollViewWindow) {
			//ScrollViewWindow scrollViewWindow = (ScrollViewWindow) undockedWindow;
		scrollWindow.updateData(getViewData());
		//}
	}

	@Override
	protected UndockedWindow createUndockedWindow() {
		scrollWindow = UIManager.getInstance(UIManager.class).getGameUI().createScrollViewWindow(buildingType);
		return scrollWindow;
	}
}

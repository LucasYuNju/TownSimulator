package com.TownSimulator.entity.building;

import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.UndockedWindow;

public class Well extends Building{
	
	public Well() {
		super("building_well",BuildingType.WELL);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected UndockedWindow createUndockedWindow() {
		// TODO Auto-generated method stub
		return UIManager.getInstance(UIManager.class).getGameUI().createWellViewWindow();
	}

	@Override
	protected void updateViewWindow() {
		// TODO Auto-generated method stub
		
	}

}

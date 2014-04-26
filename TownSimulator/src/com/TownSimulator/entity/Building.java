package com.TownSimulator.entity;

import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.build.BuildingInfoWindow;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Building extends Entity{
	
	public Building(Sprite sp) {
		super(sp);
	}
	
	@Override
	public boolean detectTouchDown()
	{
		super.detectTouchDown();
		BuildingInfoWindow infoWindow = 
				UIManager.getInstance(UIManager.class).getGameUI().getBuildingInfoWindow();
		infoWindow.setPosition(100, 100);
		infoWindow.setVisible(true);
		return true;
	}
}

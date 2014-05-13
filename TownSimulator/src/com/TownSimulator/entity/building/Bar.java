package com.TownSimulator.entity.building;

import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.BarViewWindow;
import com.TownSimulator.ui.building.view.UndockedWindow;
import com.TownSimulator.utility.Singleton;

public class Bar extends Building{
	private static final int MAX_WINE_STORAGE = 10;
	private static final int NUM_ALLOWED_WORKER = 2;
	private int wineStorage = 0;
	private BarViewWindow barViewWindow;
	
	public Bar() {
		super("building_bar", BuildingType.Bar);
	}
	
	public boolean getWine() {
		if (wineStorage > 0) {
			wineStorage--;
			return true;
		}
		return false;
	}
	
	@Override
	protected UndockedWindow createUndockedWindow() {
		return Singleton.getInstance(UIManager.class).getGameUI().createBarViewWindow(NUM_ALLOWED_WORKER, MAX_WINE_STORAGE);
	}

	@Override
	protected void updateViewWindow() {
		barViewWindow.updateWineStorage(wineStorage);
	}
}
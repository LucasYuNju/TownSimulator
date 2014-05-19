package com.TownSimulator.entity.building;

import java.util.List;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.bartender.BarTenderBTN;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.BarViewWindow;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.TownSimulator.utility.Singleton;

public class Bar extends WorkableBuilding{
	private static final long serialVersionUID = 4094024332249625038L;
	//一年掉120幸福度，多消耗600粮食
	public static final int HAPPINESS_POINTS_PER_WINE = 10;
	private static final int WHEAT_PER_WINE = 50;
	private static final int MAX_WINE_STORAGE = 10;
	private static final int NUM_ALLOWED_WORKER = 2;
	private int wineStorage;
	private int wheatStorage;
	private transient BarViewWindow barViewWindow;
	
	public Bar() {
		super("building_bar", BuildingType.Bar, JobType.BARTENDER);
		makeWine();
		updateBarViewWindow();
	}
	
	public boolean getWine() {
		if (wineStorage > 0) {
			wineStorage--;
			makeWine();
			updateBarViewWindow();
			return true;
		}
		return false;
	}
	
	public boolean hasWheatToProcess() {
		return wheatStorage > 0;
	}
	
	public void transferSomeWheat() {
		if(wheatStorage >= WHEAT_PER_WINE) {
			wheatStorage -= WHEAT_PER_WINE;
			wineStorage++;
			updateBarViewWindow();
		}
	}
	
	private void makeWine() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Warehouse> warehouses = (List)Singleton.getInstance(EntityInfoCollector.class).getBuildings(BuildingType.WAREHOUSE);
		for(Warehouse warehouse : warehouses) {
			if(warehouse.isWheatAbundant()) {
				int wheatCount = warehouse.requestWheat((int)getNeededWheat());
				if(wheatCount > 0)
					wheatStorage += wheatCount;
			}
		}
	}
	
	private float getNeededWheat() {
		return (MAX_WINE_STORAGE - wineStorage) * WHEAT_PER_WINE - wheatStorage;
	}
	
	public boolean isAvailable() {
		return wineStorage > 0;
	}
	
	@Override
	protected WorkableViewWindow createWorkableWindow() {
		barViewWindow = Singleton.getInstance(UIManager.class).getGameUI().createBarViewWindow(NUM_ALLOWED_WORKER, MAX_WINE_STORAGE);
		return barViewWindow;
	}

	public void updateBarViewWindow() {
		barViewWindow.updateWineStorage(wineStorage);
	}
	
	@Override
	protected int getMaxJobCnt() {
		return NUM_ALLOWED_WORKER;
	}

	@Override
	protected BehaviorTreeNode createBehavior(Man man) {
		return new BarTenderBTN(man);
	}
	
//	@Override
//	protected void reloadViewWindow() {
//		super.reloadViewWindow();
//		barViewWindow.updateWineStorage(wineStorage);
//	}
}

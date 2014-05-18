package com.TownSimulator.entity.building;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.ScrollViewWindow;
import com.TownSimulator.ui.building.view.UndockedWindow;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.TipsBillborad;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.Color;

public class Warehouse extends Building {
	private static final long serialVersionUID = -1934873980764058889L;
	private List<Resource> storedResources;
	protected ScrollViewWindow scrollWindow;
	
	public Warehouse() {
		super("building_warehouse", BuildingType.WAREHOUSE);
		storedResources = new LinkedList<Resource>();
	}
	
	public void addStoredResource(ResourceType type, int amount, boolean showTips)
	{
		if(amount == 0)
			return;
		
		if(storedResources.contains(new Resource(type))) 
			storedResources.get(storedResources.indexOf(new Resource(type))).addAmount(amount);
		else
			storedResources.add(new Resource(type, amount));
		
		ResourceInfoCollector.getInstance(ResourceInfoCollector.class)
			.addResourceAmount(type, amount);
		updateViewWindow();
		
		if (showTips) {
			float originX = getAABBWorld(QuadTreeType.DRAW).getCenterX();
			float originY = getAABBWorld(QuadTreeType.DRAW).maxY + Settings.UNIT * 0.4f;
			Color color = amount > 0 ? Color.WHITE : Color.RED;
			TipsBillborad.showTips(
					type + (amount > 0 ? " + " : " - ") + amount,
					originX,
					originY, color);
		}
	}
	
	public void addStoredResource(ResourceType type, int amount)
	{
		addStoredResource(type, amount, true);
	}

	public Iterator<Resource> getStoredResource()
	{
		return storedResources.iterator();
	}
	
	public int getStoredResourceAmount(ResourceType type)
	{
		if(storedResources.contains(new Resource(type))) 
			return storedResources.get(storedResources.indexOf(new Resource(type))).getAmount();
		return -1;
	}
	
	public List<List<String>> getViewData() {
		List<List<String>> list = new ArrayList<List<String>>();
		for(Resource resource : storedResources) {
			list.add(resource.toStringList());
		}
		if(list.isEmpty())
			list.add(Resource.getEmptyStringList());
		return list;
	}
	
	protected void updateViewWindow() {
		scrollWindow.updateData(getViewData());
	}

	@Override
	protected UndockedWindow createUndockedWindow() {
		scrollWindow = UIManager.getInstance(UIManager.class).getGameUI().createScrollViewWindow(buildingType);
		return scrollWindow;
	}
	
	public boolean isWheatAbundant() {
		return true;
	}
	
	public int requestWheat(int requiredAmount) {
		if(!storedResources.contains(new Resource(ResourceType.RS_WHEAT)))
			return 0;
		Resource wheat = storedResources.get(storedResources.indexOf(new Resource(ResourceType.RS_WHEAT)));
		if(wheat.getAmount() >= requiredAmount) {
			wheat.addAmount(-requiredAmount);
			Singleton.getInstance(ResourceInfoCollector.class).addResourceAmount(ResourceType.RS_WHEAT, -requiredAmount);
			return requiredAmount;
		}
		int resWheatAmount = wheat.getAmount();
		wheat.addAmount(-resWheatAmount);
		Singleton.getInstance(ResourceInfoCollector.class).addResourceAmount(ResourceType.RS_WHEAT, -resWheatAmount);
		return resWheatAmount;
	}
}

package com.TownSimulator.entity.building;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.building.view.ScrollViewWindow;
import com.TownSimulator.utility.ResourceManager;

public class Warehouse extends Building {
	private List<Resource> storedResources;
	
	public Warehouse() {
		super(ResourceManager.getInstance(ResourceManager.class).createSprite("building_warehouse"), BuildingType.WAREHOUSE);
		
		storedResources = new LinkedList<Resource>();
	}
	
	public void addStoredResource(ResourceType type, int amount)
	{
		if(storedResources.contains(new Resource(type))) 
			storedResources.get(storedResources.indexOf(new Resource(type))).addAmount(amount);
		else
			storedResources.add(new Resource(type, amount));
		
		ResourceInfoCollector.getInstance(ResourceInfoCollector.class)
			.addResourceAmount(type, amount);
		updateViewWindow();
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
		return list;
	}
	
	protected void updateViewWindow() {
		if(viewWindow instanceof ScrollViewWindow) {
			ScrollViewWindow scrollViewWindow = (ScrollViewWindow) viewWindow;
			scrollViewWindow.updateData(getViewData());
		}
	}
}

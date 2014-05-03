package com.TownSimulator.entity.building;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
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
		updataViewWindow();
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
		
	@Override
	public String[][] getViewData() {
		String[][] data = new String[storedResources.size()][2];
		for(int i=0; i<storedResources.size(); i++) {
			data[i][0] = storedResources.get(i).getType().toString();
			data[i][1] = storedResources.get(i).getAmount() + "";
		}
		return data;
	}
}

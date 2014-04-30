package com.TownSimulator.entity.building;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.utility.ResourceManager;

public class Warehouse extends Building{
	private List<Resource> resources;
	
	public Warehouse() {
		super(ResourceManager.getInstance(ResourceManager.class).createSprite("building_warehouse"), BuildingType.WAREHOUSE);
		resources = new LinkedList<Resource>();
	}
	
	public void addStoredResource(ResourceType type, int amount)
	{
		if(resources.contains(new Resource(type))) 
			resources.get(resources.indexOf(new Resource(type))).addAmount(amount);
		else
			resources.add(new Resource(type, amount));
	}
	
	public int getStoredResourceAmount(ResourceType type)
	{
		if(resources.contains(new Resource(type))) 
			return resources.get(resources.indexOf(new Resource(type))).getAmount();
		return -1;
	}
	
	@Override
	public String[][] getViewData() {
		String[][] data = new String[resources.size()][2];
		for(int i=0; i<resources.size(); i++) {
			data[i][0] = resources.get(i).getType().toString();
			data[i][1] = resources.get(i).getAmount() + "";
		}
		return data;
	}
}

package com.TownSimulator.entity.building;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.math.Vector3;

public class Warehouse extends Building{
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

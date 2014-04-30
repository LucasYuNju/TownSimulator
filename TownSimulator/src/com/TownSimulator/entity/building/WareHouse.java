package com.TownSimulator.entity.building;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.utility.ResourceManager;

public class WareHouse extends Building{
	private List<Resource> resourceMap;
	
	public WareHouse() {
		super(ResourceManager.getInstance(ResourceManager.class).createSprite("building_warehouse"), BuildingType.WAREHOUSE);
		resourceMap = new LinkedList<Resource>();
	}
	
	public void addWareHousrResource(ResourceType type, int amount)
	{
		for(Resource res : resourceMap) {
			if(res.getType() == type) {
				res.addAmount(amount);
				return;
			}
		}
		//?
	}
	
	public int getWareHousrResourceAmount(ResourceType type)
	{
		for(Resource res : resourceMap) {
			if(res.getType() == type)
				return res.getAmount();
		}
		return -1;
	}
}

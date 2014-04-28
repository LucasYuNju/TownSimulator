package com.TownSimulator.entity.building;

import java.util.HashMap;

import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.utility.ResourceManager;

public class WareHouse extends Building{
	private HashMap<ResourceType, ResourceAmount> mResourceMap;
	
	private class ResourceAmount
	{
		int amount;
	}
	
	public WareHouse() {
		super(ResourceManager.getInstance(ResourceManager.class).createSprite("building_warehouse"), BuildingType.WAREHOUSE);
		
		mResourceMap = new HashMap<ResourceType, ResourceAmount>();
	}
	
	public void addWareHousrResource(ResourceType type, int amount)
	{
		if( !mResourceMap.containsKey(type) )
			mResourceMap.put(type, new ResourceAmount());
		
		mResourceMap.get(type).amount += amount;
	}
	
	public int getWareHousrResourceAmount(ResourceType type)
	{
		if( !mResourceMap.containsKey(type) )
			return 0;
		
		return mResourceMap.get(type).amount;
	}

}

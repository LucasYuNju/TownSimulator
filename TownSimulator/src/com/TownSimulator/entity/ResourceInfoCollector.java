package com.TownSimulator.entity;

import java.util.HashMap;

import com.TownSimulator.utility.Singleton;

public class ResourceInfoCollector extends Singleton{
	private HashMap<ResourceType, Resource> resourceMap;
	private int foodAmount = 0;
	
	private ResourceInfoCollector()
	{
		resourceMap = new HashMap<ResourceType, Resource>();
	}
	
	public void addResourceAmount(ResourceType type, int amount)
	{
		if( !resourceMap.containsKey(type))
		{
			resourceMap.put(type, new Resource(type));
		}
		
		resourceMap.get(type).addAmount(amount);
		
		if(type.isFood())
			foodAmount += amount;
	}
	
	public int getResourceAmount(ResourceType type)
	{
		if(resourceMap.containsKey(type))
			return resourceMap.get(type).getAmount();
		else
			return 0;
	}
	
//	private boolean isFood(ResourceType type)
//	{
//		return 		type == ResourceType.RS_CORN
//				||	type == ResourceType.RS_WHEAT;
//	}
	
	public int getFoodAmount()
	{
		return foodAmount;
	}
	
}

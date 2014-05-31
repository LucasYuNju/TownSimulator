package com.TownSimulator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.utility.Singleton;

public class ResourceInfoCollector extends Singleton 
	implements Serializable
{
	private static final long serialVersionUID = -3695490079998930235L;
	private List<Resource> resourceMap;
	private int foodAmount = 0;
	private int energyAmount = 0;
	private int maxEnergyAmount = 0;
	
	private ResourceInfoCollector()
	{
		resourceMap = new ArrayList<Resource>();
	}
	
	public void clear()
	{
		resourceMap.clear();
		foodAmount = 0;
		energyAmount = 0;
		maxEnergyAmount = 0;
	}
	
	public void addEnergy(int amount)
	{
		int addResult = energyAmount + amount;
		if(amount < 0)
			energyAmount = Math.max(addResult, 0);
		else
		{
			if(addResult < 0)
				energyAmount = Integer.MAX_VALUE;
			else
				energyAmount = addResult;
		}
		
		maxEnergyAmount = Math.max(maxEnergyAmount, energyAmount);
	}
	
	public int getEnergyAmount()
	{
		return energyAmount;
	}
	
	public void addResourceAmount(ResourceType type, int amount)
	{
		if(!resourceMap.contains(new Resource(type)))
		{
			resourceMap.add(new Resource(type));
		}
		resourceMap.get(resourceMap.indexOf(new Resource(type))).addAmount(amount);
		if(type.isFood())
			foodAmount += amount;
	}
	
	public int getResourceAmount(ResourceType type)
	{
		if(resourceMap.contains(new Resource(type)))
			return resourceMap.get(resourceMap.indexOf(new Resource(type))).getAmount();
		else
			return -1;
	}
	
	public int getFoodAmount()
	{
		return foodAmount;
	}

	public int getMaxEnergyAmount() {
		return maxEnergyAmount;
	}
}

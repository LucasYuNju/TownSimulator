package com.TownSimulator.entity;

import com.TownSimulator.utility.ResourceManager;


public enum ResourceType {
	Wood(ResourceManager.stringMap.get("rs_wood"), false, "resource_wood"), 
	Wheat(ResourceManager.stringMap.get("rs_wheat"), true, null),
	Mimosa(ResourceManager.stringMap.get("rs_mimosa"), true, null),
	Rose(ResourceManager.stringMap.get("rs_rose"), true, null),
	Corn(ResourceManager.stringMap.get("rs_corn"), true, null),
	Meat(ResourceManager.stringMap.get("rs_meat"), true, null),
	Fur(ResourceManager.stringMap.get("rs_fur"), false, null),
	Coat(ResourceManager.stringMap.get("rs_coat"), false, null);
	
	private String resName;
	private boolean isFood;
	private String texName;
	
	ResourceType(String resName, boolean isFood, String texName){
		this.resName = resName;
		this.isFood = isFood;
		this.texName = texName;
	}
	
	@Override
	public String toString() {
		return resName;
	}
	
	public boolean isFood()
	{
		return isFood;
	}
	
	public String getTextureName()
	{
		return this.texName;
	}
}

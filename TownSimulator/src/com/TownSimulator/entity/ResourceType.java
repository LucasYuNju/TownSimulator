package com.TownSimulator.entity;


public enum ResourceType {
	RS_WOOD("wood", false, "resource_wood"), 
	RS_WHEAT("wheat", true, null),
	RS_CORN("corn", true, null),
	RS_MEAT("meat", true, null),
	RS_FUR("fur", false, null),
	RS_COAT("coat", false, null);
	
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

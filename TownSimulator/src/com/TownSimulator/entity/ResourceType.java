package com.TownSimulator.entity;


public enum ResourceType {
	RS_WOOD("wood", false), 
	RS_STONE("stone", false),
	//目前还没用到
	RS_WHEAT("wheat", true),
	RS_CORN("corn", true),
	RS_FUR("fur", false),
	RS_COAT("coat", false);
	
	private String resName;
	private boolean isFood;
	
	ResourceType(String resName, boolean isFood){
		this.resName = resName;
		this.isFood = isFood;
	}
	
	@Override
	public String toString() {
		return resName;
	}
	
	public boolean isFood()
	{
		return isFood;
	}
}

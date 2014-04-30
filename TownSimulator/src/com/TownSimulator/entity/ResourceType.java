package com.TownSimulator.entity;


public enum ResourceType {
	RS_WOOD("wood"), 
	RS_STONE("stone"),
	//目前还没用到
	RS_WHEAT("wheat"),
	RS_CORN("corn"),
	RS_FUR("fur"),
	RS_COAT("coat");
	
	private String resName;
	
	ResourceType(String resName){
		this.resName = resName;
	}
	
	public String getName() {
		return resName;
	}
}

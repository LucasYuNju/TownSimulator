package com.TownSimulator.entity.building;

public enum BuildingType {
	LOW_COST_HOUSE("Low-cost House"), 
	WAREHOUSE("Warehoust"), 
	FARM_HOUSE("Farm");
	
	private String name;
	private BuildingType(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}

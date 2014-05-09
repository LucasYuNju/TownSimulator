package com.TownSimulator.entity.building;

public enum BuildingType {
	LOW_COST_HOUSE("Low-cost House"), 
	WAREHOUSE("Warehouse"), 
	FARM_HOUSE("Farm"),
	FELLING_HOUSE("Felling"),
	POWER_STATION("PowerStation"),
	COAT_FACTORY("CoatFactory");
	
	private String name;
	private BuildingType(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}

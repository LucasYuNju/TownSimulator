package com.TownSimulator.entity.building;

public enum BuildingType {
	LOW_COST_HOUSE("Low-cost House"), 
	APARTMENT("apartment"),
	WAREHOUSE("Warehouse"), 
	FARM_HOUSE("Farm"),
	FELLING_HOUSE("Felling House"),
	POWER_STATION("Power Station"),
	COAT_FACTORY("Coat Factory"),
	RANCH("ranch"),
	School("school"),
	Hospital("hospital"),
	Bar("bar");
	
	private String name;
	private BuildingType(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}

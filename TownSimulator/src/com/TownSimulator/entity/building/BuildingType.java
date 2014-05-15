package com.TownSimulator.entity.building;

public enum BuildingType {
	LOW_COST_HOUSE("Low-cost House"), 
	APARTMENT("apartment"),
	WAREHOUSE("Warehouse"), 
	FARM_HOUSE("Farm"),
	FELLING_HOUSE("Felling"),
	POWER_STATION("PowerStation"),
	COAT_FACTORY("CoatFactory"),
	SCHOOL("School"),
	WELL("Well"),
	Hospital("hospital"),
	Bar("bar"),
	RANCH("Ranch");

	
	private String name;
	private BuildingType(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}

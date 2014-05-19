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
	RANCH("Ranch"), 
	
	MP_Store("Store", true, 300);

	
	private String name;
	boolean bMP;
	int mpCostMoney;
	
	private BuildingType(String name) {
		this(name, false, 0);
	}
	
	private BuildingType(String name, boolean bMP, int cost) {
		this.name = name;
		this.bMP = bMP;
		this.mpCostMoney = cost;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean isMoneyProducing()
	{
		return bMP;
	}
	
	public int getMoneyCost()
	{
		return mpCostMoney;
	}
}

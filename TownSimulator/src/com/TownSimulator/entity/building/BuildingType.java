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
	
//	MoneyProducing("");
	MP_Dad_s_Coffers("Dad's Coffers", true),
	MP_Store("Store", true),
	MP_Factory("Dad's Coffers", true),
	MP_CandyRain("Candy Rain", true),
	MP_Rocket("Rocket", true),
	MP_BalckHole("Black Hole", true);

	
	private String name;
	boolean bMP;
	
	private BuildingType(String name) {
		this(name, false);
	}
	
	private BuildingType(String name, boolean bMP) {
		this.name = name;
		this.bMP = bMP;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean isMoneyProducing()
	{
		return bMP;
	}
}

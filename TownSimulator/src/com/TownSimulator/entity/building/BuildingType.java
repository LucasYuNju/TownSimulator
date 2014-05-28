package com.TownSimulator.entity.building;

import com.TownSimulator.utility.ResourceManager;

public enum BuildingType {
	LowCostHouse(ResourceManager.stringMap.get("buildSelect_lowCostHouse")), 
	Apartment(ResourceManager.stringMap.get("buildSelect_apartment")),
	Warehouse(ResourceManager.stringMap.get("buildSelect_warehouse")), 
	FarmHouse(ResourceManager.stringMap.get("buildSelect_farm")),
	FellingHouse(ResourceManager.stringMap.get("buildSelect_fellingHouse")),
	PowerStation("PowerStation"),
	CoatFactory(ResourceManager.stringMap.get("buildSelect_coatFactory")),
	School(ResourceManager.stringMap.get("buildSelect_school")),
	Well("Well"),
	Hospital(ResourceManager.stringMap.get("buildSelect_hospital")),
	Bar(ResourceManager.stringMap.get("buildSelect_bar")),
	Ranch(ResourceManager.stringMap.get("buildSelect_ranch")), 
	ConstrctionCompany(ResourceManager.stringMap.get("buildSelect_constructionCompany")),
	
//	MoneyProducing("");
	MP_Potato(ResourceManager.stringMap.get("buildSelect_potato"), true),
	MP_MouseWheel(ResourceManager.stringMap.get("buildSelect_mouseWheel"), true),
	MP_Factory(ResourceManager.stringMap.get("buildSelect_factory"), true),
	MP_Storm(ResourceManager.stringMap.get("buildSelect_storm"), true),
	MP_Vocalno(ResourceManager.stringMap.get("buildSelect_vocalno"), true),
	MP_BalckHole(ResourceManager.stringMap.get("buildSelect_blackHole"), true);

	
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

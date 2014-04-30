package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.Warehouse;

public class ConstructionInfo {
	public ConstructionProject 	proj = null;
	//public Man 					man = null;
	public Warehouse			transportWareHouse = null;
	public Building				transportBuilding = null;
	public ResourceType 		transportRSType = null;
	public int					transportNeededAmount = 0;
	public int					curRSAmount = 0;
	public boolean				bCancel = false;
}

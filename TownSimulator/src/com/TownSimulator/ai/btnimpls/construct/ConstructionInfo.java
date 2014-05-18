package com.TownSimulator.ai.btnimpls.construct;

import java.io.Serializable;

import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.Warehouse;

public class ConstructionInfo implements Serializable{
	private static final long serialVersionUID = -2390047975480829085L;
	public ConstructionProject 	proj = null;
	public Warehouse			transportWareHouse = null;
	public Building				transportBuilding = null;
	public ResourceType 		transportRSType = null;
	public int					transportNeededAmount = 0;
	public int					curRSAmount = 0;
	public boolean				bCancel = false;
}

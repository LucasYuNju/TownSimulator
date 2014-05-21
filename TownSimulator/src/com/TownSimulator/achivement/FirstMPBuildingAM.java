package com.TownSimulator.achivement;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.building.BuildingType;

public class FirstMPBuildingAM extends Achievement{
	private static final String TITLE = "Beginner";
	private static final String DESC = "Build the first candy building";
	
	public FirstMPBuildingAM() {
		super(TITLE, DESC, "achievement_icon_copper");
	}

	@Override
	protected boolean doCheckAchievement() {
		for (BuildingType type : BuildingType.values()) {
			if( !type.isMoneyProducing() )
				continue;
			
			if(EntityInfoCollector.getInstance(EntityInfoCollector.class).getBuildings(type).size() > 0)
				return true;
		}
		
		return false;
	}
}
	

package com.TownSimulator.achivement;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;

public class FirstMPBuildingAM extends Achievement{
	private static final String TITLE = ResourceManager.stringMap.get("achivement_title_begginer");
	private static final String DESC = ResourceManager.stringMap.get("achivement_desc_begginer");
	
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
	

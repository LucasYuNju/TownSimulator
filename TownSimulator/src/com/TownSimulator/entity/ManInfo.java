package com.TownSimulator.entity;

import com.TownSimulator.ai.btnimpls.construct.ConstructionInfo;
import com.TownSimulator.entity.building.WorkingBuilding;

public class ManInfo {
	public ManAnimeType 		animeType = ManAnimeType.STANDING;
	public boolean				animeFlip = false;
	public ConstructionInfo		constructionInfo = new ConstructionInfo();
	public float				workEfficency = 1.0f;
	public JobType				job;
	public WorkingBuilding		workingBuilding;
}

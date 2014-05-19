package com.TownSimulator.utility;

import java.util.ArrayList;
import java.util.HashMap;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.BuildingType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Settings {
	public static float UNIT = Gdx.graphics.getWidth() / 15.0f;
	
	public static int gameSpeed = 1;
	public static Color backgroundColor = new Color(Color.BLACK);
	
	public static Color gameGroundColor = GameMath.rgbaIntToColor(205, 163, 95, 255);
	/*
	 * UI Settings
	 */
	public static final float MARGIN = UNIT * 0.2f;
	public static final float LABEL_WIDTH = UNIT * 1.3f;
	public static final float LABEL_HEIGHT = UNIT * 0.4f;
	public static final float WORKER_WIDTH = UNIT * 0.6f;
	public static final float WORKER_HEIGHT = UNIT * 0.6f;
	public static final float PROCESS_BAR_PREFERED_WIDTH = LABEL_WIDTH * 2;
	public static final float PROCESS_BAR_HEIGHT = UNIT * 0.4f;
	public static final float UI_ALPHA = 0.7f;
	
	public static final float GRAVITY_X = 0.0f;
	public static final float GRAVITY_Y = UNIT * -6.0f;
	
	
	/**
	 * Building
	 */
	public static HashMap<BuildingType, BuildingRSMapData> normalBuildingRSMap = new HashMap<BuildingType, Settings.BuildingRSMapData>();
	
	public static class BuildingRSMapData
	{
		public ArrayList<Resource> needResource = new ArrayList<Resource>();
		public int needWork;
	}
	
	static
	{
		BuildingRSMapData data;
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 2000));
		data.needWork = 20;
		normalBuildingRSMap.put(BuildingType.APARTMENT, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 2000));
		data.needWork = 20;
		normalBuildingRSMap.put(BuildingType.LOW_COST_HOUSE, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 4000));
		data.needWork = 40;
		normalBuildingRSMap.put(BuildingType.WAREHOUSE, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 2500));
		data.needWork = 25;
		normalBuildingRSMap.put(BuildingType.FARM_HOUSE, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 2500));
		data.needWork = 25;
		normalBuildingRSMap.put(BuildingType.FELLING_HOUSE, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 5000));
		data.needWork = 50;
		normalBuildingRSMap.put(BuildingType.POWER_STATION, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 4000));
		data.needWork = 40;
		normalBuildingRSMap.put(BuildingType.COAT_FACTORY, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 2500));
		data.needWork = 25;
		normalBuildingRSMap.put(BuildingType.RANCH, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 4000));
		data.needWork = 40;
		normalBuildingRSMap.put(BuildingType.Hospital, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 4000));
		data.needWork = 40;
		normalBuildingRSMap.put(BuildingType.Bar, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 5000));
		data.needWork = 50;
		normalBuildingRSMap.put(BuildingType.SCHOOL, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.RS_WOOD, 2000));
		data.needWork = 20;
		normalBuildingRSMap.put(BuildingType.WELL, data);
	}
}

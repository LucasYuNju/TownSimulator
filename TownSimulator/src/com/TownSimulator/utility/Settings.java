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
	public static Color gameNormalGroundColor = GameMath.rgbaIntToColor(205, 163, 95, 255);
	public static Color gameWinterGroundColor = GameMath.rgbaIntToColor(250, 235, 215, 255);
	public static Color gameSummerGroundColor = GameMath.rgbaIntToColor(137, 109, 64, 255);
	/*
	 * UI Settings
	 */
	public static final float MARGIN = UNIT * 0.1f;
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
	
	public static HashMap<BuildingType, MPData> mpBuildingDataMap = new HashMap<BuildingType, Settings.MPData>();
	
	public static class MPData
	{
		public int produceAmount;
		public float timeInterval;
		private int costInit;
		public int cost;
		private int costIncreInit;
		private int costIncre;
		private int costIncre2;
		public String textureName;
		public int maxJobCnt;
		
		public void incre()
		{
			cost += costIncre;
			costIncre += costIncre2;
		}
		
		public void decre()
		{
			costIncre -= costIncre2;
			cost -= costIncre;
		}
		
		public void reset(int cnt)
		{
			costIncre =  costIncreInit + cnt * costIncre2;
			cost = costInit + (costIncreInit + costIncre) * cnt / 2;
		}
	}
	
	static
	{
		MPData data;
		
		data = new MPData();
		data.produceAmount = 1;
		data.timeInterval = 2.0f;
		data.costInit = data.cost = 100;
		data.costIncreInit = data.costIncre = 20;
		data.costIncre2 = 5;
		data.textureName = "building_mp_dad_s_coffers";
		data.maxJobCnt = 0;
		mpBuildingDataMap.put(BuildingType.MP_Dad_s_Coffers, data);
		
		data = new MPData();
		data.produceAmount = 5;
		data.timeInterval = 2.0f;
		data.costInit = data.cost = 1000;
		data.costIncreInit = data.costIncre = 200;
		data.costIncre2 = 20;
		data.textureName = "building_mp_store";
		data.maxJobCnt = 1;
		mpBuildingDataMap.put(BuildingType.MP_Store, data);
		
		data = new MPData();
		data.produceAmount = 100;
		data.timeInterval = 10.0f;
		data.costInit = data.cost = 10000;
		data.costIncreInit = data.costIncre = 2000;
		data.costIncre2 = 500;
		data.textureName = "building_mp_factory";
		data.maxJobCnt = 1;
		mpBuildingDataMap.put(BuildingType.MP_Factory, data);
		
		data = new MPData();
		data.produceAmount = 3000;
		data.timeInterval = 60.0f;
		data.costInit = data.cost = 40000;
		data.costIncreInit = data.costIncre = 5000;
		data.costIncre2 = 1000;
		data.textureName = "building_mp_candy_rain";
		data.maxJobCnt = 2;
		mpBuildingDataMap.put(BuildingType.MP_CandyRain, data);
		
		data = new MPData();
		data.produceAmount = 20000;
		data.timeInterval = 100.0f;
		data.costInit = data.cost = 500000;
		data.costIncreInit = data.costIncre = 40000;
		data.costIncre2 = 10000;
		data.textureName = "building_mp_shipment";
		data.maxJobCnt = 2;
		mpBuildingDataMap.put(BuildingType.MP_Rocket, data);
		
		data = new MPData();
		data.produceAmount = 999999;
		data.timeInterval = 600.0f;
		data.costInit = data.cost = 9999999;
		data.costIncreInit = data.costIncre = 999999;
		data.costIncre2 = 99999;
		data.textureName = "building_mp_black_hole";
		data.maxJobCnt = 0;
		mpBuildingDataMap.put(BuildingType.MP_BalckHole, data);
	}
}

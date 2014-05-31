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
		data.needResource.add(new Resource(ResourceType.Wood, 2000));
		data.needWork = 20;
		normalBuildingRSMap.put(BuildingType.Apartment, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 2000));
		data.needWork = 20;
		normalBuildingRSMap.put(BuildingType.LowCostHouse, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 4000));
		data.needWork = 40;
		normalBuildingRSMap.put(BuildingType.Warehouse, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 2500));
		data.needWork = 25;
		normalBuildingRSMap.put(BuildingType.FarmHouse, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 2500));
		data.needWork = 25;
		normalBuildingRSMap.put(BuildingType.FellingHouse, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 5000));
		data.needWork = 50;
		normalBuildingRSMap.put(BuildingType.PowerStation, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 4000));
		data.needWork = 40;
		normalBuildingRSMap.put(BuildingType.CoatFactory, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 2500));
		data.needWork = 25;
		normalBuildingRSMap.put(BuildingType.Ranch, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 4000));
		data.needWork = 40;
		normalBuildingRSMap.put(BuildingType.Hospital, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 4000));
		data.needWork = 40;
		normalBuildingRSMap.put(BuildingType.Bar, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 5000));
		data.needWork = 50;
		normalBuildingRSMap.put(BuildingType.School, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 2000));
		data.needWork = 20;
		normalBuildingRSMap.put(BuildingType.Well, data);
		
		data = new BuildingRSMapData();
		data.needResource.add(new Resource(ResourceType.Wood, 1000));
		data.needWork = 10;
		normalBuildingRSMap.put(BuildingType.ConstrctionCompany, data);
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
		public String desc;
		
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
		data.textureName = "building_mp_potato";
		data.desc = ResourceManager.stringMap.get("mp_desc_potato");
		data.maxJobCnt = 0;
		mpBuildingDataMap.put(BuildingType.MP_Potato, data);
		
		data = new MPData();
		data.produceAmount = 5;
		data.timeInterval = 2.0f;
		data.costInit = data.cost = 1000;
		data.costIncreInit = data.costIncre = 200;
		data.costIncre2 = 20;
		data.textureName = "building_mp_mouse_wheel";
		data.desc = ResourceManager.stringMap.get("mp_desc_mouseWheel");
		data.maxJobCnt = 1;
		mpBuildingDataMap.put(BuildingType.MP_MouseWheel, data);
		
		data = new MPData();
		data.produceAmount = 100;
		data.timeInterval = 10.0f;
		data.costInit = data.cost = 10000;
		data.costIncreInit = data.costIncre = 2000;
		data.costIncre2 = 500;
		data.textureName = "building_mp_factory";
		data.desc = ResourceManager.stringMap.get("mp_desc_factory");
		data.maxJobCnt = 1;
		mpBuildingDataMap.put(BuildingType.MP_Factory, data);
		
		data = new MPData();
		data.produceAmount = 3000;
		data.timeInterval = 60.0f;
		data.costInit = data.cost = 40000;
		data.costIncreInit = data.costIncre = 5000;
		data.costIncre2 = 1000;
		data.textureName = "building_mp_storm";
		data.desc = ResourceManager.stringMap.get("mp_desc_storm");
		data.maxJobCnt = 2;
		mpBuildingDataMap.put(BuildingType.MP_Storm, data);
		
		data = new MPData();
		data.produceAmount = 20000;
		data.timeInterval = 100.0f;
		data.costInit = data.cost = 500000;
		data.costIncreInit = data.costIncre = 40000;
		data.costIncre2 = 10000;
		data.textureName = "building_mp_vocalno";
		data.desc = ResourceManager.stringMap.get("mp_desc_vocalno");
		data.maxJobCnt = 2;
		mpBuildingDataMap.put(BuildingType.MP_Vocalno, data);
		
		data = new MPData();
		data.produceAmount = 999999;
		data.timeInterval = 600.0f;
		data.costInit = data.cost = 9999999;
		data.costIncreInit = data.costIncre = 999999;
		data.costIncre2 = 99999;
		data.textureName = "building_mp_black_hole";
		data.desc = ResourceManager.stringMap.get("mp_desc_blackHole");
		data.maxJobCnt = 0;
		mpBuildingDataMap.put(BuildingType.MP_BalckHole, data);
	}
}

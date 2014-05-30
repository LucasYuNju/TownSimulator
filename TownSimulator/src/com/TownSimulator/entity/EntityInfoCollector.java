package com.TownSimulator.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.TownSimulator.ai.btnimpls.construct.ConstructionProject;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.EntityInfoCollector.EntityInfoCollectorListener;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.School;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.SingletonPublisher;
import com.badlogic.gdx.Gdx;

public class EntityInfoCollector extends SingletonPublisher<EntityInfoCollectorListener> 
	implements Serializable
{
	private static final long serialVersionUID = -3062222237607985005L;
	private List<Man> 							manList;
	private List<Building>						buildingsList;
	private List<ConstructionProject> 			constructProjsList;
	private Map<BuildingType, List<Building>> 	buildingsMap;
	
	public interface EntityInfoCollectorListener extends Serializable
	{
		public void newBuildingAdded(Building building);
		
		public void buildingConstructed(Building building);
	}
	
	private EntityInfoCollector()
	{
		manList = new ArrayList<Man>();
		buildingsList = new ArrayList<Building>();
		buildingsMap = new HashMap<BuildingType, List<Building>>();
		constructProjsList = new ArrayList<ConstructionProject>();
	}
	
	public void printBuilding() {
		for(Building b : buildingsList) {
			Gdx.app.log("L/S", b.getType() + ":" + b);
		}
	}
	
	public void printMan() {
		for(Man m : manList) {
			Gdx.app.log("L/S", m.getInfo().getName() + ":" + m);
		}
	}
	
	public void addMan(Man man)
	{
		manList.add(man);
	}
	
	public void removeMan(Man man)
	{
		manList.remove(man);
	}
	
	public List<Man> getAllMan()
	{
		return manList;
	}
	
	public void addConstructProj(ConstructionProject proj)
	{
		constructProjsList.add(proj);
	}
	
	public void removeConstructProj(ConstructionProject proj)
	{
		constructProjsList.remove(proj);
	}
	
	public List<ConstructionProject> getAllConstructProjs()
	{
		return constructProjsList;
	}
	
	public void buildingConstructed(Building building)
	{
		for (EntityInfoCollectorListener l : mListeners) {
			l.buildingConstructed(building);
		}
	}
	
	public void addBuilding(Building building)
	{
		BuildingType type = building.getType();
		if( !buildingsMap.containsKey(type) )
			buildingsMap.put(type, new ArrayList<Building>());
		
		buildingsMap.get(type).add(building);
		buildingsList.add(building);
		
		for (EntityInfoCollectorListener l : mListeners) {
			l.newBuildingAdded(building);
		}
	}
	
	public void removeBuilding(Building building)
	{
		//buildingList.removeValue(building, false);
		if(buildingsMap.containsKey(building.getType()))
			buildingsMap.get(building.getType()).remove(building);
		
		buildingsList.remove(building);
	}
	
	public List<Building> getBuildings(BuildingType type)
	{
		if( !buildingsMap.containsKey(type) )
			buildingsMap.put(type, new ArrayList<Building>());
		
		return buildingsMap.get(type);
	}
	
	public List<Building> getAllBuildings()
	{
		return buildingsList;
	}
	
	public class WareHouseFindResult
	{
		public Warehouse wareHouse;
		public int amount;
	}
	
	public Warehouse findNearestWareHouse(float x, float y)
	{
		if( !buildingsMap.containsKey(BuildingType.Warehouse) )
			return null;
		
		double dstMin = -1.0f;
		Warehouse house = null;
		for (Building building : buildingsMap.get(BuildingType.Warehouse)) {
			if(building.getType() == BuildingType.Warehouse)
			{
				Warehouse wareHouse = (Warehouse)building;
				double dst = 	Math.pow(wareHouse.getPositionXWorld() - x, 2)
							+	Math.pow(wareHouse.getPositionYWorld() - y, 2);
				
				if(dstMin == -1.0f || dst < dstMin)
				{
					dstMin = dst;
					house = wareHouse;
				}
			}
		}
		
		return house;
	}
	
	public WareHouseFindResult findNearestWareHouseWithRs( ResourceType type, int amount, float x, float y )
	{
		if( !buildingsMap.containsKey(BuildingType.Warehouse) )
			return null;
		
		WareHouseFindResult result = new WareHouseFindResult();
		List<Building> allBuildings = getBuildings(BuildingType.Warehouse);
		List<Warehouse> wareHouseWithRs = new ArrayList<Warehouse>();
		double dstMin = -1.0f;
		for (int i = 0; i < allBuildings.size(); i++) {
			Building building = allBuildings.get(i);
			if(building.getType() == BuildingType.Warehouse)
			{
				Warehouse wareHouse = (Warehouse) building;
				if(wareHouse.getStoredResourceAmount(type) >= amount)
				{
					double dst = 	Math.pow(wareHouse.getPositionXWorld() - x, 2)
								+	Math.pow(wareHouse.getPositionYWorld() - y, 2);
					if(dstMin == -1.0f || dst < dstMin)
					{
						dstMin = dst;
						result.wareHouse = wareHouse;
						result.amount = amount;
					}
				}
				else if(wareHouse.getStoredResourceAmount(type) > 0)
					wareHouseWithRs.add(wareHouse);
			}
		}
		
		dstMin = -1.0f;
		if(result.wareHouse == null)
		{
			for (int i = 0; i < wareHouseWithRs.size(); i++) {
				Warehouse wareHouse = wareHouseWithRs.get(i);
				double dst = 	Math.pow(wareHouse.getPositionXWorld() - x, 2)
							+	Math.pow(wareHouse.getPositionYWorld() - y, 2);
				
				if(dstMin == -1.0f || dst < dstMin)
				{
					dstMin = dst;
					result.wareHouse = wareHouse;
					result.amount = wareHouse.getStoredResourceAmount(type);
				}
			}
		}
		return result;
	}
	
//	public class BuildingFindResult{
//		public BuildingType buildingType;
//		public Building building;
//	}
	
	public Building findNearestBuilding(BuildingType buildingType,float x, float y)
	{
		double dstMin = -1.0f;
//		BuildingFindResult buildingFindResult=new BuildingFindResult();
		Building result = null;
		for (Building building : buildingsList) {
			if(building.getType() == buildingType)
			{
				double dst = 	Math.pow(building.getPositionXWorld() - x, 2)
							+	Math.pow(building.getPositionYWorld() - y, 2);
				
				if(dstMin == -1.0f || dst < dstMin)
				{
					dstMin = dst;
					result = building;
				}
			}
		}
		
		return result;
	}
	

	
	//只找到还能放学生的最近学校
	public School findNearestSchool(float x, float y)
	{
		double dstMin = -1.0f;
		School school=null;
		for (Building building : buildingsList) {
			if(building.getType() == BuildingType.School)
			{
				School tempSchool=(School)building;
				if(tempSchool.getCurrentStudentNum()>=School.SingleSchoolStudentNum){
					continue;
				}
				double dst = 	Math.pow(building.getPositionXWorld() - x, 2)
							+	Math.pow(building.getPositionYWorld() - y, 2);
				
				if(dstMin == -1.0f || dst < dstMin)
				{
					dstMin = dst;
					school=(School)building;
				}
			}
		}
		return school;
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		Gdx.app.log("L/S", "entityInfoCollector size: " + manList.size() + ":" + buildingsList.size());
		for(Man man : manList) {
			Singleton.getInstance(Renderer.class).attachDrawScissor(man);
		}
		for(Building building : buildingsList) {
			Singleton.getInstance(Renderer.class).attachDrawScissor(building);
			Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(building);
		}
	}
}

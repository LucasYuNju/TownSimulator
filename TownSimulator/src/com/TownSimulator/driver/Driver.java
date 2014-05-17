package com.TownSimulator.driver;


import java.util.Random;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.EntityFactory;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Bar;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.CoatFactory;
//github.com/LuciusYu/TownSimulator.git
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FarmLand;
import com.TownSimulator.entity.building.FellingHouse;
import com.TownSimulator.entity.building.Hospital;
import com.TownSimulator.entity.building.PowerStation;
import com.TownSimulator.entity.building.Ranch;
import com.TownSimulator.entity.building.RanchLand;
import com.TownSimulator.entity.building.School;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.entity.building.Well;
import com.TownSimulator.io.InputMgr;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.SingletonPublisher;
import com.TownSimulator.utility.TipsBillborad;
import com.TownSimulator.utility.particles.ParticleManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Driver extends SingletonPublisher<DriverListener> implements ApplicationListener{
	private Driver()
	{
		
	}
	
	public void init()
	{
		Random rand = new Random(System.currentTimeMillis());
		int initPepleCnt = 15;
		float originPosX = CameraController.getInstance(CameraController.class).getX();
		float originPoxY = CameraController.getInstance(CameraController.class).getY();
		
		//ResourceInfoCollector.getInstance(ResourceInfoCollector.class).addResourceAmount(ResourceType.RS_WOOD, 100);
		//ResourceInfoCollector.getInstance(ResourceInfoCollector.class).addResourceAmount(ResourceType.RS_STONE, 50);
		
		Warehouse wareHouse = (Warehouse) EntityFactory.createBuilding(BuildingType.WAREHOUSE);
		wareHouse.addStoredResource(ResourceType.RS_WOOD, 2300);
		//wareHouse.addStoredResource(ResourceType.RS_STONE, 50);
		wareHouse.addStoredResource(ResourceType.RS_WHEAT, 20000);
		wareHouse.addStoredResource(ResourceType.RS_FUR, 200);
		wareHouse.setState(Building.State.Constructed);
		wareHouse.setPositionWorld(originPosX - 2 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(wareHouse);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(wareHouse);
		Renderer.getInstance(Renderer.class).attachDrawScissor(wareHouse);
		
		
		Building lowCostHouse = EntityFactory.createBuilding(BuildingType.LOW_COST_HOUSE);
		lowCostHouse.setState(Building.State.Constructed);
		lowCostHouse.setPositionWorld(originPosX + 2 * Settings.UNIT, originPoxY);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(lowCostHouse);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(lowCostHouse);
		Renderer.getInstance(Renderer.class).attachDrawScissor(lowCostHouse);
		

//		Driver.getInstance(Driver.class).addListener((DriverListener) lowCostHouse);
//		
//		AxisAlignedBoundingBox aabb = null;
//		aabb = wareHouse.getAABBWorld(QuadTreeType.COLLISION);
//		Map.getInstance(Map.class).setGroundTexture("map_soil", aabb.minX, aabb.minY, aabb.maxX, aabb.maxY);
//		
//		aabb = lowCostHouse.getAABBWorld(QuadTreeType.COLLISION);
//		Map.getInstance(Map.class).setGroundTexture("map_soil", aabb.minX, aabb.minY, aabb.maxX, aabb.maxY);

//		AxisAlignedBoundingBox aabb = null;
//		aabb = wareHouse.getAABBWorld(QuadTreeType.COLLISION);
//		Map.getInstance(Map.class).setGroundTexture("map_soil", aabb.minX, aabb.minY, aabb.maxX, aabb.maxY);
//		
//		aabb = lowCostHouse.getAABBWorld(QuadTreeType.COLLISION);
//		Map.getInstance(Map.class).setGroundTexture("map_soil", aabb.minX, aabb.minY, aabb.maxX, aabb.maxY);
		
		FellingHouse fellingHouse = (FellingHouse)EntityFactory.createBuilding(BuildingType.FELLING_HOUSE);
		fellingHouse.setState(Building.State.Constructed);
		fellingHouse.setPositionWorld(originPosX - 12 * Settings.UNIT, originPoxY - 12 * Settings.UNIT);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(fellingHouse);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(fellingHouse);
		Renderer.getInstance(Renderer.class).attachDrawScissor(fellingHouse);
		
		Hospital hospital = (Hospital)EntityFactory.createBuilding(BuildingType.Hospital);
		hospital.setState(Building.State.Constructed);
		hospital.setPositionWorld(originPosX - 16 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
		Singleton.getInstance(EntityInfoCollector.class).addBuilding(hospital);
		Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(hospital);
		Singleton.getInstance(Renderer.class).attachDrawScissor(hospital);

		Bar bar = (Bar)EntityFactory.createBuilding(BuildingType.Bar);
		bar.setState(Building.State.Constructed);
		bar.setPositionWorld(originPosX - 20 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
		Singleton.getInstance(EntityInfoCollector.class).addBuilding(bar);
		Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(bar);
		Singleton.getInstance(Renderer.class).attachDrawScissor(bar);
		
		School school=(School)EntityFactory.createBuilding(BuildingType.SCHOOL);
		//school.setState(Building.State.Constructed);
		school.setPositionWorld(originPosX - 25 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
		Singleton.getInstance(EntityInfoCollector.class).addBuilding(school);
		Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(school);
		Singleton.getInstance(Renderer.class).attachDrawScissor(school);
		
		Well well=(Well)EntityFactory.createBuilding(BuildingType.WELL);
		well.setState(Building.State.Constructed);
		well.setPositionWorld(originPosX - 30 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
		Singleton.getInstance(EntityInfoCollector.class).addBuilding(well);
		Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(well);
		Singleton.getInstance(Renderer.class).attachDrawScissor(well);
		
		
		CoatFactory coatFactory = (CoatFactory)EntityFactory.createBuilding(BuildingType.COAT_FACTORY);
		coatFactory.setState(Building.State.Constructed);
		coatFactory.setPositionWorld(originPosX - 8 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(coatFactory);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(coatFactory);
		Renderer.getInstance(Renderer.class).attachDrawScissor(coatFactory);
		

		PowerStation powerStation = (PowerStation)EntityFactory.createBuilding(BuildingType.POWER_STATION);
		powerStation.setState(Building.State.Constructed);
		powerStation.setPositionWorld(originPosX + 5 * Settings.UNIT, originPoxY - 5 * Settings.UNIT);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(powerStation);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(powerStation);
		Renderer.getInstance(Renderer.class).attachDrawScissor(powerStation);

		FarmHouse farmHouse=(FarmHouse)EntityFactory.createBuilding(BuildingType.FARM_HOUSE);
		farmHouse.setState(Building.State.Constructed);
		farmHouse.setPositionWorld(originPosX, originPoxY - 3 * Settings.UNIT);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(farmHouse);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(farmHouse);
		Renderer.getInstance(Renderer.class).attachDrawScissor(farmHouse);
		
		
		for (FarmLand land : ((FarmHouse)farmHouse).getFarmLands()) {
			Renderer.getInstance(Renderer.class).attachDrawScissor(land);
		}
		
		Ranch ranch = (Ranch)EntityFactory.createBuilding(BuildingType.RANCH);
		ranch.setPositionWorld(originPosX - 8 * Settings.UNIT, originPoxY + 2 * Settings.UNIT);
		ranch.setState(Building.State.Constructed);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(ranch);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(ranch);
		Renderer.getInstance(Renderer.class).attachDrawScissor(ranch);
		for (RanchLand land : ranch.getRanchLands()) {
			Renderer.getInstance(Renderer.class).attachDrawScissor(land);
			CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(land);
		}

		for (int i = 0; i < initPepleCnt; i++) {
			float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 6;
			float ranxY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 6;
			Man man = new Man();
			man.setPositionWorld(originPosX + randX, originPoxY + ranxY);
			if(i%5==0){
				man.getInfo().setAge(10);				
			}
			EntityInfoCollector.getInstance(EntityInfoCollector.class).addMan(man);
			
			Renderer.getInstance(Renderer.class).attachDrawScissor(man);
		}
		
		school.setState(Building.State.Constructed);//测试时，学校需要在初始的人确定之后，初始化学生列表
	}
	
	@Override
	public void create() {
		ResourceManager.getInstance(ResourceManager.class);
		InputMgr.getInstance(InputMgr.class);
		UIManager.getInstance(UIManager.class);
		CameraController.getInstance(CameraController.class);
		Renderer.getInstance(Renderer.class);
		CollisionDetector.getInstance(CollisionDetector.class);
		TipsBillborad.init();
		ParticleManager.init();
	}

	@Override
	public void dispose() {
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).dispose();
		}
		
		mInstaceMap.clear();
	}

	@Override
	public void render() {	
		Gdx.gl.glClearColor(Settings.backgroundColor.r, Settings.backgroundColor.g, Settings.backgroundColor.b, Settings.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Renderer.getInstance(Renderer.class).render();
		UIManager.getInstance(UIManager.class).render();
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).update(deltaTime * Settings.gameSpeed);
		}
	}

	@Override
	public void resize(int width, int height) {
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).resize(width, height);
		}
	}

	@Override
	public void pause() {
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).pause();
		}
	}

	@Override
	public void resume() {
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).resume();
		}
	}
}

package com.TownSimulator.driver;


import java.util.Random;

import com.TownSimulator.ai.btnimpls.farmer.FarmerBTN;
import com.TownSimulator.camera.CameraController;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.EntityFactory;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.CropType;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FarmLand;
import com.TownSimulator.entity.building.FellingHouse;
import com.TownSimulator.entity.building.CoatFactory;
import com.TownSimulator.entity.building.PowerStation;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.io.InputMgr;
import com.TownSimulator.io.InputMgrListenerBaseImpl;
import com.TownSimulator.map.Map;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.SingletonPublisher;
import com.TownSimulator.utility.VoicePlayer;
import com.TownSimulator.utility.quadtree.QuadTreeType;
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
		int initPepleCnt = 5;
		float originPosX = CameraController.getInstance(CameraController.class).getX();
		float originPoxY = CameraController.getInstance(CameraController.class).getY();
		
		ResourceInfoCollector.getInstance(ResourceInfoCollector.class).addResourceAmount(ResourceType.RS_WOOD, 100);
		ResourceInfoCollector.getInstance(ResourceInfoCollector.class).addResourceAmount(ResourceType.RS_STONE, 50);
		
		Warehouse wareHouse = (Warehouse) EntityFactory.createBuilding(BuildingType.WAREHOUSE);
		wareHouse.addStoredResource(ResourceType.RS_WOOD, 100);
		wareHouse.addStoredResource(ResourceType.RS_STONE, 50);
		wareHouse.addStoredResource(ResourceType.RS_WHEAT, 2000);
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
		
		AxisAlignedBoundingBox aabb = null;
		aabb = wareHouse.getAABBWorld(QuadTreeType.COLLISION);
		Map.getInstance(Map.class).setGroundTexture("map_soil", aabb.minX, aabb.minY, aabb.maxX, aabb.maxY);
		
		aabb = lowCostHouse.getAABBWorld(QuadTreeType.COLLISION);
		Map.getInstance(Map.class).setGroundTexture("map_soil", aabb.minX, aabb.minY, aabb.maxX, aabb.maxY);
		
//		FellingHouse fellingHouse = (FellingHouse)EntityFactory.createBuilding(BuildingType.FELLING_HOUSE);
//		fellingHouse.setState(Building.State.Constructed);
//		fellingHouse.setPositionWorld(originPosX, originPoxY - 8 * Settings.UNIT);
//		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(fellingHouse);
//		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(fellingHouse);
//		Renderer.getInstance(Renderer.class).attachDrawScissor(fellingHouse);
		

		CoatFactory coatFactory = (CoatFactory)EntityFactory.createBuilding(BuildingType.COAT_FACTORY);
		coatFactory.setState(Building.State.Constructed);
		coatFactory.setPositionWorld(originPosX, originPoxY - 8 * Settings.UNIT);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(coatFactory);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(coatFactory);
		Renderer.getInstance(Renderer.class).attachDrawScissor(coatFactory);
		
		PowerStation powerStation = (PowerStation)EntityFactory.createBuilding(BuildingType.POWER_STATION);
		powerStation.setState(Building.State.Constructed);
		powerStation.setPositionWorld(originPosX, originPoxY - 5 * Settings.UNIT);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(powerStation);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(powerStation);
		Renderer.getInstance(Renderer.class).attachDrawScissor(powerStation);

		FarmHouse farmHouse=(FarmHouse)EntityFactory.createBuilding(BuildingType.FARM_HOUSE);
		farmHouse.setState(Building.State.Constructed);
		farmHouse.setPositionWorld(originPosX, originPoxY - 3 * Settings.UNIT);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(farmHouse);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(farmHouse);
		Renderer.getInstance(Renderer.class).attachDrawScissor(farmHouse);
		
		farmHouse.setSowCropType(CropType.Wheat);
		
		for (FarmLand land : ((FarmHouse)farmHouse).getFarmLands()) {
			Renderer.getInstance(Renderer.class).attachDrawScissor(land);
		}
		
//		InputMgr.getInstance(InputMgr.class).addListener(new InputMgrListenerBaseImpl()
//		{
//
//			@Override
//			public boolean touchDown(float screenX, float screenY, int pointer,
//					int button) {
//				VoicePlayer.getInstance(VoicePlayer.class).playSound("cave3.wav");
//				return true;
//			}
//			
//		});
		
		for (int i = 0; i < initPepleCnt; i++) {
			float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 6;
			float ranxY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 6;
			Man man = new Man();
			man.setPositionWorld(originPosX + randX, originPoxY + ranxY);
			//fellingHouse.addWorker(man);
			//man.setBehavior(new FellingBTN(man));
//			farmHouse.addWorker(man);
//			man.setBehavior(new FarmerBTN(man));
			EntityInfoCollector.getInstance(EntityInfoCollector.class).addMan(man);
			
			Renderer.getInstance(Renderer.class).attachDrawScissor(man);
		}
	}
	
	@Override
	public void create() {
		ResourceManager.getInstance(ResourceManager.class);
		InputMgr.getInstance(InputMgr.class);
		UIManager.getInstance(UIManager.class);
		CameraController.getInstance(CameraController.class);
		Renderer.getInstance(Renderer.class);
		CollisionDetector.getInstance(CollisionDetector.class);
	}

	@Override
	public void dispose() {
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).dispose();
		}
	}

	@Override
	public void render() {	
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Renderer.getInstance(Renderer.class).render();
		UIManager.getInstance(UIManager.class).render();
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).update(deltaTime);
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

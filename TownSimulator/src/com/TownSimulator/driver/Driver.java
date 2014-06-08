package com.TownSimulator.driver;

import java.util.ArrayList;
import java.util.Random;

import com.TownSimulator.achivement.AchievementManager;
import com.TownSimulator.camera.CameraController;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.Entity;
import com.TownSimulator.entity.EntityFactory;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.ManInfo.Gender;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.World;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.ConstructionCompany;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FarmLand;
import com.TownSimulator.entity.building.Hospital;
import com.TownSimulator.entity.building.Warehouse;
import com.TownSimulator.io.InputMgr;
import com.TownSimulator.map.Map;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.construction.ConstructionProgressBar;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.BuildingAdjustHelper;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.SingletonPublisher;
import com.TownSimulator.utility.TipsBillborad;
import com.TownSimulator.utility.particle.ParticleControl;
import com.TownSimulator.utility.particles.ParticleManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

public class Driver extends SingletonPublisher<DriverListener> implements ApplicationListener{
	private boolean bIgnoreNextUpdate = false;
	private int frameRemain = 0;
	private ArrayList<BufferredTask> bufferTasks;
	private Vector2 initOriginPos;
	
	private Driver()
	{
		bufferTasks = new ArrayList<BufferredTask>();
	}
	
	private Vector2 searchInitOrigin()
	{
		int initSpaceGridExtendX = 5;
		int initSpaceGridEntendY = 5;
		AxisAlignedBoundingBox collisionAABB = new AxisAlignedBoundingBox();
		collisionAABB.minX = -initSpaceGridExtendX * Settings.UNIT;
		collisionAABB.minY = -initSpaceGridEntendY * Settings.UNIT;
		collisionAABB.maxX = initSpaceGridExtendX * Settings.UNIT;
		collisionAABB.maxY = initSpaceGridEntendY * Settings.UNIT;
		
		float searchOriginX = Map.MAP_WIDTH * Settings.UNIT * 0.5f;
		float searchOriginY = Map.MAP_HEIGHT * Settings.UNIT * 0.5f;
		Vector2 searchPos = BuildingAdjustHelper.searchBuildingPosition(searchOriginX, searchOriginY, collisionAABB);
		
		int gridX = (int) (searchOriginX / Settings.UNIT);
		int gridY = (int) (searchOriginY / Settings.UNIT);
		if(searchPos.x == gridX * Settings.UNIT && searchPos.y == gridY * Settings.UNIT)
		{
			int gridSerchSize = 0;
			
			boolean bPosFind = false;
			float posX = 0.0f;
			float posY = 0.0f;
			float resultX = 0.0f;
			float resultY = 0.0f;
			AxisAlignedBoundingBox searchTempAABB = new AxisAlignedBoundingBox();
			while(!bPosFind)
			{
				for (int x = gridX - gridSerchSize; x <= gridX + gridSerchSize && !bPosFind; x++) {
					for (int y = gridY - gridSerchSize; y <= gridY + gridSerchSize && !bPosFind; y++) {
						resultX = posX;
						resultY = posY;
						posX = x * Settings.UNIT;
						posY = y * Settings.UNIT;
						searchTempAABB.minX = posX + collisionAABB.minX;
						searchTempAABB.minY = posY + collisionAABB.minY;
						searchTempAABB.maxX = posX + collisionAABB.maxX;
						searchTempAABB.maxY = posY + collisionAABB.maxY;
						if( CollisionDetector.getInstance(CollisionDetector.class).detect(searchTempAABB) )
						{
							bPosFind = true;
							resultX = (x - (int)Math.signum(x - gridX)) * Settings.UNIT;
							resultY = (y - (int)Math.signum(y - gridY)) * Settings.UNIT;
						}
					}
				}
				gridSerchSize ++;
				
			}
			
			searchPos.x = resultX;
			searchPos.y = resultY;
		}
		
		return searchPos;
	}
		
	public void init()
	{
		
		initOriginPos = searchInitOrigin();
		
		Random rand = new Random(System.currentTimeMillis());
		int initPepleCnt = 5;
		int minAge = ManInfo.AGE_ADULT;
		int maxAge = ManInfo.AGE_ADULT + 10;
		float minHunger = (ManInfo.HUNGER_POINTS_FIND_FOOD + ManInfo.HUNGER_POINTS_MAX) * 0.5f;
		float maxHunger = ManInfo.HUNGER_POINTS_MAX;
		for (int i = 0; i < initPepleCnt; i++) {
			float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 6;
			float ranxY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 6;
			Man man = new Man();
			int age = rand.nextInt(maxAge - minAge) + minAge;
			man.getInfo().setAge(age);
			man.getInfo().setGender(rand.nextFloat() < 0.5f ? Gender.Male : Gender.Female);
			man.getInfo().hungerPoints = GameMath.lerp(minHunger, maxHunger, rand.nextFloat());
			man.setPositionWorld(initOriginPos.x + randX, initOriginPos.y + ranxY);
			
			EntityInfoCollector.getInstance(EntityInfoCollector.class).addMan(man);
			Renderer.getInstance(Renderer.class).attachDrawScissor(man);
		}
		
		ResourceInfoCollector.getInstance(ResourceInfoCollector.class).addEnergy(100);
		
		Warehouse wareHouse = (Warehouse) EntityFactory.createBuilding(BuildingType.Warehouse);
		wareHouse.addStoredResource(ResourceType.Wood, 15000);
		wareHouse.addStoredResource(ResourceType.Coat, 1000);
		wareHouse.addStoredResource(ResourceType.Wheat, 20000);
		wareHouse.setState(Building.State.Constructed);
		wareHouse.setPositionWorld(initOriginPos.x, initOriginPos.y - Settings.UNIT * 3.0f);
		wareHouse.setDestroyable(false);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(wareHouse);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(wareHouse);
		Renderer.getInstance(Renderer.class).attachDrawScissor(wareHouse);
		
		ConstructionCompany constructionCom = (ConstructionCompany) EntityFactory.createBuilding(BuildingType.ConstrctionCompany);
		constructionCom.setState(Building.State.Constructed);
		constructionCom.setDestroyable(false);
		constructionCom.setPositionWorld(initOriginPos.x - Settings.UNIT * 3.0f, initOriginPos.y - Settings.UNIT * 3.0f);
		constructionCom.setDestroyable(false);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(constructionCom);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(constructionCom);
		Renderer.getInstance(Renderer.class).attachDrawScissor(constructionCom);
		
		Building lowCostHouse = EntityFactory.createBuilding(BuildingType.LowCostHouse);
		lowCostHouse.setState(Building.State.Constructed);
		lowCostHouse.setPositionWorld(initOriginPos.x + Settings.UNIT * 2.0f, initOriginPos.y + Settings.UNIT * 3.0f);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(lowCostHouse);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(lowCostHouse);
		Renderer.getInstance(Renderer.class).attachDrawScissor(lowCostHouse);
		
		FarmHouse farmHouse = (FarmHouse)EntityFactory.createBuilding(BuildingType.FarmHouse);
		farmHouse.setState(Building.State.Constructed);
		farmHouse.setPositionWorld(initOriginPos.x - Settings.UNIT * 4.0f, initOriginPos.y + 4 * Settings.UNIT);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(farmHouse);
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(farmHouse);
		Renderer.getInstance(Renderer.class).attachDrawScissor(farmHouse);
		
		for (FarmLand land : ((FarmHouse)farmHouse).getFarmLands()) {
			Renderer.getInstance(Renderer.class).attachDrawScissor(land);
			CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(land);
		}
		
		CameraController.getInstance(CameraController.class).setPosition(initOriginPos.x, initOriginPos.y);
		
//		lowCostHouse = EntityFactory.createBuilding(BuildingType.LowCostHouse);
//		lowCostHouse.setState(Building.State.Constructed);
//		lowCostHouse.setPositionWorld(originPosX + 5 * Settings.UNIT, originPoxY);
//		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(lowCostHouse);
//		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(lowCostHouse);
//		Renderer.getInstance(Renderer.class).attachDrawScissor(lowCostHouse);
//		
//		Building apartmentHouse = EntityFactory.createBuilding(BuildingType.Apartment);
//		apartmentHouse.setState(Building.State.Constructed);
//		apartmentHouse.setPositionWorld(originPosX + 8 * Settings.UNIT, originPoxY);
//		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(apartmentHouse);
//		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(apartmentHouse);
//		Renderer.getInstance(Renderer.class).attachDrawScissor(apartmentHouse);
//		
//		FellingHouse fellingHouse = (FellingHouse)EntityFactory.createBuilding(BuildingType.FellingHouse);
//		fellingHouse.setState(Building.State.Constructed);
//		fellingHouse.setPositionWorld(originPosX - 12 * Settings.UNIT, originPoxY - 12 * Settings.UNIT);
//		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(fellingHouse);
//		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(fellingHouse);
//		Renderer.getInstance(Renderer.class).attachDrawScissor(fellingHouse);
//		
//		Hospital hospital = (Hospital)EntityFactory.createBuilding(BuildingType.Hospital);
//		hospital.setState(Building.State.Constructed);
//		hospital.setPositionWorld(initOriginPos.x - 16 * Settings.UNIT, initOriginPos.y - 8 * Settings.UNIT);
//		Singleton.getInstance(EntityInfoCollector.class).addBuilding(hospital);
//		Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(hospital);
//		Singleton.getInstance(Renderer.class).attachDrawScissor(hospital);
//
//		Bar bar = (Bar)EntityFactory.createBuilding(BuildingType.Bar);
//		bar.setState(Building.State.Constructed);
//		bar.setPositionWorld(originPosX - 20 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
//		Singleton.getInstance(EntityInfoCollector.class).addBuilding(bar);
//		Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(bar);
//		Singleton.getInstance(Renderer.class).attachDrawScissor(bar);
//		
//		School school=(School)EntityFactory.createBuilding(BuildingType.School);
//		//school.setState(Building.State.Constructed);
//		school.setPositionWorld(originPosX - 25 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
//		Singleton.getInstance(EntityInfoCollector.class).addBuilding(school);
//		Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(school);
//		Singleton.getInstance(Renderer.class).attachDrawScissor(school);
//		
//		Well well=(Well)EntityFactory.createBuilding(BuildingType.Well);
//		well.setState(Building.State.Constructed);
//		well.setPositionWorld(originPosX - 30 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
//		Singleton.getInstance(EntityInfoCollector.class).addBuilding(well);
//		Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(well);
//		Singleton.getInstance(Renderer.class).attachDrawScissor(well);
//		
//		
//		CoatFactory coatFactory = (CoatFactory)EntityFactory.createBuilding(BuildingType.CoatFactory);
//		coatFactory.setState(Building.State.Constructed);
//		coatFactory.setPositionWorld(originPosX - 8 * Settings.UNIT, originPoxY - 8 * Settings.UNIT);
//		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(coatFactory);
//		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(coatFactory);
//		Renderer.getInstance(Renderer.class).attachDrawScissor(coatFactory);
//		
//
//		PowerStation powerStation = (PowerStation)EntityFactory.createBuilding(BuildingType.PowerStation);
//		powerStation.setState(Building.State.Constructed);
//		powerStation.setPositionWorld(originPosX + 5 * Settings.UNIT, originPoxY - 5 * Settings.UNIT);
//		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(powerStation);
//		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(powerStation);
//		Renderer.getInstance(Renderer.class).attachDrawScissor(powerStation);
//
//		FarmHouse farmHouse=(FarmHouse)EntityFactory.createBuilding(BuildingType.FarmHouse);
//		farmHouse.setState(Building.State.Constructed);
//		farmHouse.setPositionWorld(originPosX, originPoxY - 3 * Settings.UNIT);
//		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(farmHouse);
//		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(farmHouse);
//		Renderer.getInstance(Renderer.class).attachDrawScissor(farmHouse);
//		
//		for (FarmLand land : ((FarmHouse)farmHouse).getFarmLands()) {
//			Renderer.getInstance(Renderer.class).attachDrawScissor(land);
//			CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(land);
//		}
//		
//		Ranch ranch = (Ranch)EntityFactory.createBuilding(BuildingType.Ranch);
//		ranch.setPositionWorld(originPosX - 8 * Settings.UNIT, originPoxY + 2 * Settings.UNIT);
//		ranch.setState(Building.State.Constructed);
//		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(ranch);
//		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(ranch);
//		Renderer.getInstance(Renderer.class).attachDrawScissor(ranch);
//		for (RanchLand land : ranch.getRanchLands()) {
//			Renderer.getInstance(Renderer.class).attachDrawScissor(land);
//			CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(land);
//		}
//		school.setState(Building.State.Constructed);//测试时，学校需要在初始的人确定之后，初始化学生列表
	}
	
	@Override
	public void create() {
		ResourceManager.getInstance(ResourceManager.class);
		InputMgr.getInstance(InputMgr.class);
		UIManager.getInstance(UIManager.class);
		CameraController.getInstance(CameraController.class);
		Renderer.getInstance(Renderer.class);
		CollisionDetector.getInstance(CollisionDetector.class);
		AchievementManager.getInstance(AchievementManager.class);
		Entity.initStatic();
		ConstructionProgressBar.initStatic();
		TipsBillborad.initStatic();
		ParticleManager.initStatic();
	}

	@Override
	public void dispose() {
		for (int i = 0; i < mListeners.size(); i++) {
			mListeners.get(i).dispose();
		}
		Singleton.clearInstanceMap();
	}

	@Override
	public void render() {	
		Gdx.gl.glClearColor(Settings.backgroundColor.r, Settings.backgroundColor.g, Settings.backgroundColor.b, Settings.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		ArrayList<BufferredTask> tasksFinished = new ArrayList<BufferredTask>();
		for (BufferredTask t : bufferTasks) {
			if(t.framesRemain -- <= 0)
			{
				t.run();
				tasksFinished.add(t);
			}
		}
		bufferTasks.removeAll(tasksFinished);
		
		Renderer.getInstance(Renderer.class).render();
		ParticleControl.getInstance(ParticleControl.class).render();
		UIManager.getInstance(UIManager.class).render();
		
		if(frameRemain > 0 || bIgnoreNextUpdate == false)
		{
			float deltaTime = Gdx.graphics.getRawDeltaTime();
			for (int i = 0; i < mListeners.size(); i++) {
				mListeners.get(i).update(deltaTime * Settings.gameSpeed);
			}
		}
		
		if(frameRemain <= 0 && bIgnoreNextUpdate == true)
			bIgnoreNextUpdate = false;
		
		if(frameRemain > 0)
			frameRemain--;
	}
	
	/**
	 * 任务会在当前之后的第二帧的清除操作之后立即执行
	 * @param task
	 */
	public void pushToBufferTask(BufferredTask task)
	{
		bufferTasks.add(task);
	}
	
	public void ignoreNextUpdate()
	{
		bIgnoreNextUpdate = true;
		frameRemain = 1;
	}
	
	public void checkGameOver()
	{
		if(EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan().size() <= 0)
			Driver.getInstance(Driver.class).gameOver();
	}
	
	private void gameOver()
	{
		Settings.gameSpeed = 0;
//		isGameOver = true;
		UIManager.getInstance(UIManager.class).getGameUI().showGameOverWindow();
	}
	
	public void gameWin()
	{
		Settings.gameSpeed = 0;
//		isGameOver = true;
		UIManager.getInstance(UIManager.class).getGameUI().showGameWinWindow();
	}
	
	private void clear()
	{
		Singleton.getInstance(EntityInfoCollector.class).clear();
		Singleton.getInstance(AchievementManager.class).initAchievements();
		Singleton.getInstance(Map.class).clear();
		Singleton.getInstance(ResourceInfoCollector.class).clear();
		Singleton.getInstance(ParticleControl.class).clear();
		Singleton.getInstance(World.class).clear();
		ParticleManager.clear();
		TipsBillborad.clear();
	}
	
	public void returnToStartUI()
	{
		clear();
		UIManager.getInstance(UIManager.class).returnToStartUI();
		Renderer.getInstance(Renderer.class).setRenderScene(false);
		Settings.backgroundColor = Settings.gameNormalGroundColor.cpy();
	}

	@Override
	public void resize(int width, int height) {
		for (int i = 0; i < mListeners.size(); i++) {
			mListeners.get(i).resize(width, height);
		}
	}

	@Override
	public void pause() {
		for (int i = 0; i < mListeners.size(); i++) {
			mListeners.get(i).pause();
		}
	}

	@Override
	public void resume() {
		for (int i = 0; i < mListeners.size(); i++) {
			mListeners.get(i).resume();
		}
	}
}

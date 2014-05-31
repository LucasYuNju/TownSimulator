package com.TownSimulator.entity.building;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.grazier.GrazierBTN;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.RanchAnimal;
import com.TownSimulator.entity.RanchAnimalType;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.RanchViewWindow;
import com.TownSimulator.ui.building.view.SelectBoxListener;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.TipsBillborad;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ranch extends WorkableBuilding{
	private static final long 		serialVersionUID = -5200249977002038249L;
	private static final int 		MAX_JOB_CNT = 4;
	private static final float 		PRODUCE_INTERVAL = 20.0f;
	private static final int		PRODUCE_MEAT_AMOUNT = 50;
	private static final int		PRODUCE_FUR_AMOUNT = 80;
	private float					produceAccum;
	private AxisAlignedBoundingBox 	collisionAABBLocalWithLands;
	private AxisAlignedBoundingBox 	collisionAABBWorldWithLands;
	private List<RanchLand>			ranchLands;
	private List<RanchAnimal>		ranchAnimals;
	private RanchAnimalType			ranchAnimalType;
	private transient RanchViewWindow	ranchWindow;
	private DriverListener	driverListener;
	
	public Ranch() {
		super("building_ranch", BuildingType.Ranch, JobType.Grazier);
		
		collisionAABBLocalWithLands = new AxisAlignedBoundingBox();
		collisionAABBWorldWithLands = new AxisAlignedBoundingBox();
		initLands();
		initAnimals();
		
		driverListener = new DriverListenerBaseImpl()
		{
			private static final long serialVersionUID = 8806760497739177385L;

			@Override
			public void update(float deltaTime) {
				if(ranchAnimalType == null)
					return;
				
				for (RanchAnimal animal : ranchAnimals) {
					animal.update(deltaTime);
				}
				produce(deltaTime);
			}
			
		};
	}
	
	
	
	@Override
	public boolean isWorking() {
		return super.isWorking() && ranchAnimalType != null;
	}

	@Override
	protected String getWarningMessage() {
		if(ranchAnimalType == null)
			return ResourceManager.stringMap.get("building_warning_noAnimalSelected");
		else
			return super.getWarningMessage();
	}

	private void initLands()
	{
		ranchLands = new ArrayList<RanchLand>();
		for (int i = 0; i < 25; i++) {
			RanchLand ranchLand = new RanchLand();
			ranchLands.add(ranchLand);
			
			if(i % 5 == 0)
				ranchLand.setTextureName("fence_v");
			if(i % 5 == 4)
			{
				Sprite sp = ResourceManager.getInstance(ResourceManager.class).createSprite("fence_v");
				sp.setFlip(true, false);
				ranchLand.setSprite(sp);
			}
			
			if(i < 5 || i > 19)
				ranchLand.setTextureName("fence_h");
		}
		
		updateRanchLandsPos();
	}
	
	private void initAnimals()
	{
		ranchAnimals = new ArrayList<RanchAnimal>();
		
		for (int i = 0; i < 6; i++) {
			RanchAnimal animal = new RanchAnimal();
			animal.setVisible(false);
			ranchAnimals.add(animal);
		}
	}
	
	public void setType(RanchAnimalType type)
	{
		ranchAnimalType = type;
		for (RanchAnimal animal : ranchAnimals) {
			animal.setType(ranchAnimalType);
		}
		
		if(ranchAnimalType == null)
			produceAccum = 0.0f;
	}
	
	private void produce(float deltaTime)
	{
		produceAccum += deltaTime;
		while(produceAccum >= PRODUCE_INTERVAL)
		{
			produceAccum -= PRODUCE_INTERVAL;
			Warehouse warehouse = EntityInfoCollector.getInstance(EntityInfoCollector.class)
									.findNearestWareHouse(mPosXWorld, mPosYWorld);
			
			if(workers.size() <= 0)
				continue;
			
			int meatAmount = 0;
			int furAmount = 0;
			for (Man man : workers) {
				meatAmount += man.getInfo().workEfficency * PRODUCE_MEAT_AMOUNT;
				furAmount += man.getInfo().workEfficency * PRODUCE_FUR_AMOUNT;
			}
			
			warehouse.addStoredResource(ResourceType.Meat, meatAmount, false);
			float originX = this.getAABBWorld(QuadTreeType.DRAW).getCenterX();
			float originY = this.getAABBWorld(QuadTreeType.DRAW).maxY + Settings.UNIT * 0.6f + TipsBillborad.getTipsHeight();
			Color color = Color.WHITE;
			TipsBillborad.showTips(
					ResourceType.Meat + " + " + meatAmount,
					originX,
					originY, color);
			
			warehouse.addStoredResource(ResourceType.Fur, furAmount, false);
			originY = this.getAABBWorld(QuadTreeType.DRAW).maxY + Settings.UNIT * 0.4f;
			TipsBillborad.showTips(
					ResourceType.Fur + " + " + furAmount,
					originX,
					originY, color);
		}
	}
	
	@Override
	public void setState(State state) {
		super.setState(state);
		
		if(state == Building.State.Constructed)
		{
			AxisAlignedBoundingBox moveRange = new AxisAlignedBoundingBox();
			moveRange.minX = mCollisionAABBWorld.minX + Settings.UNIT;
			moveRange.minY = mCollisionAABBWorld.minY - 4 * Settings.UNIT;
			moveRange.maxX = mCollisionAABBWorld.minX + 4 * Settings.UNIT;
			moveRange.maxY = mCollisionAABBWorld.minY - Settings.UNIT;
			for (RanchAnimal animal : ranchAnimals) {
				animal.setMoveRange(moveRange);
				animal.setPositionWorld(moveRange.getCenterX(), moveRange.getCenterY());
				animal.setVisible(true);
				Renderer.getInstance(Renderer.class).attachDrawScissor(animal);
			}
			
			Driver.getInstance(Driver.class).addListener(driverListener);
		}
	}
	
	

	@Override
	public void destroy() {
		super.destroy();
		for (RanchLand land : ranchLands) {
//			Renderer.getInstance(Renderer.class).dettachDrawScissor(land);
//			CollisionDetector.getInstance(CollisionDetector.class).dettachCollisionDetection(land);
			land.destroy();
		}
		
		for (RanchAnimal animal : ranchAnimals) {
			animal.destroy();
		}
		
		Driver.getInstance(Driver.class).removeListener(driverListener);
	}

	@Override
	public void setCollisionAABBLocal(float minX, float minY, float maxX,
			float maxY) {
		super.setCollisionAABBLocal(minX, minY, maxX, maxY);
		
		AxisAlignedBoundingBox aabb = super.getCollisionAABBLocal();
		collisionAABBLocalWithLands.minX = Math.min(0.1f, aabb.minX);
		collisionAABBLocalWithLands.minY = aabb.minY - 5 * Settings.UNIT;
		collisionAABBLocalWithLands.maxX = Math.max(aabb.minX + 5 * Settings.UNIT - 0.2f, aabb.maxX);
		collisionAABBLocalWithLands.maxY = aabb.maxY;
	}

	public AxisAlignedBoundingBox getCollisionAABBLocalWithLands()
	{
		return collisionAABBLocalWithLands;
	}
	
	@Override
	public AxisAlignedBoundingBox getAABBWorld(QuadTreeType type) {
		if(type == QuadTreeType.COLLISION)
		{
			if(buildingState == State.PosUnconfirmed)
			{
				collisionAABBWorldWithLands.minX = collisionAABBLocalWithLands.minX + mPosXWorld;
				collisionAABBWorldWithLands.minY = collisionAABBLocalWithLands.minY + mPosYWorld;
				collisionAABBWorldWithLands.maxX = collisionAABBLocalWithLands.maxX + mPosXWorld;
				collisionAABBWorldWithLands.maxY = collisionAABBLocalWithLands.maxY + mPosYWorld;
				
				return collisionAABBWorldWithLands;
			}
			else
				return mCollisionAABBWorld;
		}
		
		return super.getAABBWorld(type);
	}


	@Override
	public void setPositionWorld(float x, float y) {
		super.setPositionWorld(x, y);
		updateRanchLandsPos();
	}

	@Override
	protected WorkableViewWindow createWorkableWindow() {
		ranchWindow = UIManager.getInstance(UIManager.class).getGameUI().createRanchViewWindow(getMaxJobCnt());
		ranchWindow.setSelectBoxListener(new SelectBoxListener() {
			private static final long serialVersionUID = -2028117783227258435L;

			@Override
			public void selectBoxSelected(String selectedString) {
				setType(RanchAnimalType.findWithViewName(selectedString));
				updateWarningMsg();
			}
		});
		return ranchWindow;
	}

	private void updateRanchLandsPos() {
		int index = 0;
		float posY = mPosYWorld - Settings.UNIT;
		float posX = mPosXWorld;
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				ranchLands.get(index).setPositionWorld(posX, posY);
				posX += Settings.UNIT;
				index++;
			}
			posX = mPosXWorld;
			posY -= Settings.UNIT;
		}
	}
	
	public List<RanchLand> getRanchLands()
	{
		return ranchLands;
	}

	@Override
	protected int getMaxJobCnt() {
		return MAX_JOB_CNT;
	}

	@Override
	protected BehaviorTreeNode createBehavior(Man man) {
		return new GrazierBTN(man);
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		if(ranchAnimalType != null)
			ranchWindow.reload(ranchAnimalType);
		setState(buildingState);
	}
	
//	@Override
//	protected void reloadViewWindow() {
//		super.reloadViewWindow();
//		ranchWindow.reload(ranchAnimalType);
//	}
}

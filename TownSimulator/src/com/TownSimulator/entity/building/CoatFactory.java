package com.TownSimulator.entity.building;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.factoryworker.FactoryWorkerBTN;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.TipsBillborad;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.Color;

public class CoatFactory extends WorkableBuilding{
	private static final int 	MAX_JOB_CNT = 4;
	private static final float 	PRODUCE_INTERVAL_TIME = 20.0f;
	private static final int 	PRODUCE_AMOUNT = 1;
	private float produceAccum = 0.0f;
	
	public CoatFactory() {
		super("building_coat_factory", BuildingType.COAT_FACTORY, JobType.FACTORY_WORKER);
	}

	@Override
	protected int getMaxJobCnt() {
		return MAX_JOB_CNT;
	}

	@Override
	protected BehaviorTreeNode createBehavior(Man man) {
		return new FactoryWorkerBTN(man);
	}
	
	private void decreFurResource(int amount)
	{
		int remainAmount = amount;
		for (Building building : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllBuildings()) {
			if(building.getType() == BuildingType.WAREHOUSE)
			{
				Warehouse warehouse = (Warehouse)building;
				int stored = warehouse.getStoredResourceAmount(ResourceType.RS_FUR);
				if(stored > 0)
				{
					int decre = Math.min(remainAmount, stored);
					warehouse.addStoredResource(ResourceType.RS_FUR, -decre, false);
					float originX = warehouse.getAABBWorld(QuadTreeType.DRAW).getCenterX();
					float originY = warehouse.getAABBWorld(QuadTreeType.DRAW).maxY + Settings.UNIT * 0.6f + TipsBillborad.getTipsHeight();
					Color color = Color.RED;
					TipsBillborad.showTips(
							ResourceType.RS_FUR + " - " + amount,
							originX,
							originY, color);
					remainAmount -= decre;
					
					if(remainAmount <= 0)
						return;
				}
			}
		}
	}
	
	private void produce(float deltaTime)
	{
		int furAmount = ResourceInfoCollector.getInstance(ResourceInfoCollector.class)
							.getResourceAmount(ResourceType.RS_FUR);
		if( furAmount <= 0 )
			return;
		
		produceAccum += deltaTime;
		while(produceAccum >= PRODUCE_INTERVAL_TIME)
		{
			produceAccum -= PRODUCE_INTERVAL_TIME;
			Warehouse warehouse = EntityInfoCollector.getInstance(EntityInfoCollector.class)
									.findNearestWareHouse(mPosXWorld, mPosYWorld);
			int produceAmount = Math.min(furAmount, PRODUCE_AMOUNT * curWorkerCnt);
			decreFurResource(produceAmount);
			warehouse.addStoredResource(ResourceType.RS_COAT, produceAmount, false);
			float originX = warehouse.getAABBWorld(QuadTreeType.DRAW).getCenterX();
			float originY = warehouse.getAABBWorld(QuadTreeType.DRAW).maxY + Settings.UNIT * 0.4f;
			Color color = Color.WHITE;
			TipsBillborad.showTips(
					ResourceType.RS_COAT + " + " + produceAmount,
					originX,
					originY, color);
		}
	}

	@Override
	public void setState(State state) {
		super.setState(state);
		
		if( state == Building.State.Constructed )
		{
			Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
			{

				@Override
				public void update(float deltaTime) {
					produce(deltaTime);
				}
				
			});
		}
	}
	
	

}

package com.TownSimulator.entity.building;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.factoryworker.FactoryWorkerBTN;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.TipsBillborad;
import com.badlogic.gdx.graphics.Color;

public abstract class MoneyProducingBuilding extends WorkableBuilding{
	private static final long serialVersionUID = 5902905240346346815L;
	private DriverListener driverListener;
	private float produceTimeAccum;
	
	public MoneyProducingBuilding(String textureName, BuildingType type) {
		super(textureName, type, JobType.MP_Worker);
		
		driverListener = new DriverListenerBaseImpl()
		{

			@Override
			public void update(float deltaTime) {
				produce(deltaTime);
			}
			
		};
	}
	
	private void produce(float deltaTime)
	{
		produceTimeAccum += deltaTime;
		while(produceTimeAccum >= getProduceTimeInterval())
		{
			produceTimeAccum -= getProduceTimeInterval();
			int amount = getProduceAmountPerMan() * getCurWorkerCnt();
			if(amount <= 0)
				continue;
			
			ResourceInfoCollector.getInstance(ResourceInfoCollector.class).addMoney(amount);
			float originX = mDrawAABBWorld.getCenterX();
			float originY = mDrawAABBWorld.maxY + Settings.UNIT * 0.5f;
			TipsBillborad.showTips("$ + " + amount, originX, originY, Color.ORANGE);
		}
	}
	
	abstract protected float getProduceTimeInterval();
	
	abstract protected int getProduceAmountPerMan();

	@Override
	protected BehaviorTreeNode createBehavior(Man man) {
		return new FactoryWorkerBTN(man);
	}

	@Override
	public void setState(State state) {
		super.setState(state);
		
		if(state == Building.State.Constructed)
			Driver.getInstance(Driver.class).addListener(driverListener);
	}

	@Override
	public void destroy() {
		super.destroy();
		Driver.getInstance(Driver.class).removeListener(driverListener);
	}
	
	

}

package com.TownSimulator.entity.building;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.factoryworker.FactoryWorkerBTN;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.MPBuildingViewWindow;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.TipsBillborad;
import com.badlogic.gdx.graphics.Color;

public class MoneyProducingBuilding extends WorkableBuilding{
	private static final long serialVersionUID = 5902905240346346815L;
	private DriverListener driverListener;
	private float produceTimeAccum;
	
	public MoneyProducingBuilding(String textureName, BuildingType type) {
		super(textureName, type, JobType.MP_Worker);
		
		driverListener = new DriverListenerBaseImpl()
		{
			private static final long serialVersionUID = 6595004578287235291L;

			@Override
			public void update(float deltaTime) {
				produce(deltaTime);
			}
		};
	}
	
	@Override
	protected WorkableViewWindow createWorkableWindow() {
		MPBuildingViewWindow window = UIManager.getInstance(UIManager.class).getGameUI().createMPBuildingViewWindow(buildingType, getMaxJobCnt());
		window.setDesc(Settings.mpBuildingDataMap.get(buildingType).desc, Color.WHITE);
		return window;
	}

	private void produce(float deltaTime)
	{
		produceTimeAccum += deltaTime;
		while(produceTimeAccum >= getProduceTimeInterval())
		{
			produceTimeAccum -= getProduceTimeInterval();
			int amount = getProduceAmount();
			if(amount <= 0)
				continue;
			
			ResourceInfoCollector.getInstance(ResourceInfoCollector.class).addEnergy(amount);
			float originX = mDrawAABBWorld.getCenterX();
			float originY = mDrawAABBWorld.maxY + Settings.UNIT * 0.5f;
			TipsBillborad.showTips("$ + " + amount, originX, originY, Color.ORANGE);
		}
	}
	
	private float getProduceTimeInterval()
	{
		return Settings.mpBuildingDataMap.get(getType()).timeInterval;
	}
	
	private int getProduceAmount()
	{
		if(getMaxJobCnt() > 0)
			return (Settings.mpBuildingDataMap.get(getType()).produceAmount * getCurWorkerCnt()) / getMaxJobCnt();
		else
			return Settings.mpBuildingDataMap.get(getType()).produceAmount;
	}

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

	@Override
	protected int getMaxJobCnt() {
		return Settings.mpBuildingDataMap.get(buildingType).maxJobCnt;
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		setState(buildingState);
	}
}

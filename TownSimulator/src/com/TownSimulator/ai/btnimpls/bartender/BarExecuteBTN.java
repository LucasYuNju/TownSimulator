package com.TownSimulator.ai.btnimpls.bartender;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.building.Bar;

public class BarExecuteBTN implements ActionNode{
	private Man man;
	private Bar bar;
	private float accumulatedTime;
	private static final float TIME_PER_WINE = 10f;
	
	public BarExecuteBTN(Man man) {
		this.man = man;
		bar = (Bar) man.getInfo().workingBuilding;
	}
	
	private void doWork(float deltaTime) {
		accumulatedTime += deltaTime;
		if(accumulatedTime >= TIME_PER_WINE) {
			accumulatedTime -= TIME_PER_WINE;
			bar.transferSomeWheat();
		}
	}
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		if(bar.hasWheatToProcess()) {
			doWork(deltaTime);
		}
		return ExecuteResult.TRUE;
	}
}

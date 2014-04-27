package com.TownSimulator.mantask;

import com.TownSimulator.entity.Man;

public abstract class Task {
	protected Man mMan;
	
	public Task(Man man)
	{
		mMan = man;
	}
	
	abstract public void update(float deltaTime);
}

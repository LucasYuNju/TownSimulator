package com.TownSimulator.driver;

import java.io.Serializable;

public interface DriverListener extends Serializable{
	public void dispose();

	public void resize(int width, int height);
	
	public void pause();
	
	public void resume();
	
	public void update(float deltaTime);
}

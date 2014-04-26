package com.TownSimulator.driver;


public interface DriverListener {
	public void dispose();

	public void resize(int width, int height);
	
	public void pause();
	
	public void resume();
	
	public void update(float deltaTime);
}

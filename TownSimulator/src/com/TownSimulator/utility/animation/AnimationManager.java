package com.TownSimulator.utility.animation;

import aurelienribon.tweenengine.TweenManager;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.utility.Singleton;

public class AnimationManager extends Singleton{
	
	private TweenManager manager;
	
	public AnimationManager(){
		manager=new TweenManager();
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){

			@Override
			public void update(float deltaTime) {
				// TODO Auto-generated method stub
				manager.update(deltaTime);
			}
			
		});
	}
	
	public TweenManager getManager(){
		return manager;
	}
	
}

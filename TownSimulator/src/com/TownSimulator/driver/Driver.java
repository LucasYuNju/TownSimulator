package com.TownSimulator.driver;


import java.util.Random;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.io.InputMgr;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.SingletonPublisher;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Driver extends SingletonPublisher<DriverListener> implements ApplicationListener{
	private Driver()
	{
		
	}
	
	public void init()
	{
		Random rand = new Random();
		int initPepleCnt = 5;
		float originPosX = CameraController.getInstance(CameraController.class).getX();
		float originPoxY = CameraController.getInstance(CameraController.class).getY();
		for (int i = 0; i < initPepleCnt; i++) {
			float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 6;
			float ranxY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 6;
			Man man = new Man();
			man.setPositionWorld(originPosX + randX, originPoxY + ranxY);
			EntityInfoCollector.getInstance(EntityInfoCollector.class).addMan(man);
			
			Renderer.getInstance(Renderer.class).attachDrawScissor(man);
		}
	}
	
	@Override
	public void create() {
		Settings.refreshUnit();
		ResourceManager.getInstance(ResourceManager.class);
		InputMgr.getInstance(InputMgr.class);
		UIManager.getInstance(UIManager.class);
		CameraController.getInstance(CameraController.class);
		Renderer.getInstance(Renderer.class);
		CollisionDetector.getInstance(CollisionDetector.class);
	}

	@Override
	public void dispose() {
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).dispose();
		}
	}

	@Override
	public void render() {	
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Renderer.getInstance(Renderer.class).render();
		UIManager.getInstance(UIManager.class).render();
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).update(deltaTime);
		}
	}

	@Override
	public void resize(int width, int height) {
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).resize(width, height);
		}
	}

	@Override
	public void pause() {
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).pause();
		}
	}

	@Override
	public void resume() {
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).resume();
		}
	}

}

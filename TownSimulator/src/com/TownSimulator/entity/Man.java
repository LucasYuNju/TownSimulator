package com.TownSimulator.entity;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Man extends Entity{
	private List<Sprite> sprites =  new ArrayList<Sprite>();
	private static final float SPRITE_TIME_INTERVAL = 1 / 3f;
	private static final float MOVE_TIME_INTERVAL = 1 / 10f;
	private static final int MOVE_DISTANCE = -2;
	private float timeCurrentSpriteLasts = 0;
	private float timeStandStill = 0;
	
	public Man() {
		super(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_1"));
		sprites.add(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_1"));
		sprites.add(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_2"));
//		for(Sprite sprite : sprites) {
//			sprite.setSize(Settings.UNIT, Settings.UNIT);
//		}
		setPositionWorld(CameraController.getInstance(CameraController.class).getX() + Settings.UNIT * 3, CameraController.getInstance(CameraController.class).getY() + Settings.UNIT * 3);
		setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
		setCollisionAABBLocal(0, 0, 0, 0);
	}
	
	@Override
	public void drawSelf(SpriteBatch batch) {
		move();
		changeSprite();
		super.drawSelf(batch);
	}

	private void move() {
		timeStandStill += Gdx.graphics.getDeltaTime();
		if(timeStandStill > MOVE_TIME_INTERVAL) {
			timeStandStill = 0;
			translate(MOVE_DISTANCE, 0.0f);
			//setDrawPosition(getDrawX() + MOVE_DISTANCE, getDrawY());
		}
	}

	private void changeSprite() {
		timeCurrentSpriteLasts += Gdx.graphics.getDeltaTime();
		if(timeCurrentSpriteLasts > SPRITE_TIME_INTERVAL) {
			timeCurrentSpriteLasts = 0;
			float x = mPosXWorld;//getDrawX();
			float y = mPosYWorld;//getDrawY();
			int indexOfNextSprite = (sprites.indexOf(getSprite()) + 1) % sprites.size();
//			int indexOfNextSprite = (sprites.indexOf(getSprite()) == 0) ? 1 : 0;
			Sprite nextSprite = sprites.get(indexOfNextSprite);
			nextSprite.setPosition(x, y);
			setSprite(nextSprite);
		}
	}

}
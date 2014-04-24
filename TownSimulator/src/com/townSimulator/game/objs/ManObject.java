package com.townSimulator.game.objs;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.townSimulator.game.scene.CameraController;
import com.townSimulator.game.scene.QuadTreeType;
import com.townSimulator.utility.AxisAlignedBoundingBox;
import com.townSimulator.utility.ResourceManager;
import com.townSimulator.utility.Settings;

public class ManObject extends DrawableObject{
	private AxisAlignedBoundingBox 	mCollisionAABB;
	private List<Sprite> sprites =  new ArrayList<Sprite>();
	private static final float SPRITE_TIME_INTERVAL = 1 / 3f;
	private static final float MOVE_TIME_INTERVAL = 1 / 10f;
	private static final int MOVE_DISTANCE = -2;
	private float timeCurrentSpriteLasts = 0;
	private float timeStandStill = 0;
	
	public ManObject() {
		super(ResourceManager.createSprite("pixar_man_1"));
		sprites.add(ResourceManager.createSprite("pixar_man_1"));
		sprites.add(ResourceManager.createSprite("pixar_man_2"));
		for(Sprite sprite : sprites) {
			sprite.setSize(Settings.UNIT, Settings.UNIT);
		}
		setDrawPosition(CameraController.getInstance().getX() + Settings.UNIT * 3, CameraController.getInstance().getY() + Settings.UNIT * 3);
		mCollisionAABB = new AxisAlignedBoundingBox();
		setCollisionBounds(0, 0, 0, 0);
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
			setDrawPosition(getDrawX() + MOVE_DISTANCE, getDrawY());
		}
	}

	private void changeSprite() {
		timeCurrentSpriteLasts += Gdx.graphics.getDeltaTime();
		if(timeCurrentSpriteLasts > SPRITE_TIME_INTERVAL) {
			timeCurrentSpriteLasts = 0;
			float x = getDrawX();
			float y = getDrawY();
			int indexOfNextSprite = (sprites.indexOf(getSprite()) + 1) % sprites.size();
//			int indexOfNextSprite = (sprites.indexOf(getSprite()) == 0) ? 1 : 0;
			Sprite nextSprite = sprites.get(indexOfNextSprite);
			nextSprite.setPosition(x, y);
			setSprite(nextSprite);
		}
	}

	/*
	 * FIXME!
	 */
	public void setCollisionBounds(float minX, float minY, float maxX, float maxY) {
		mCollisionAABB.minX = minX;
		mCollisionAABB.minY = minY;
		mCollisionAABB.maxX = maxX;
		mCollisionAABB.maxY = maxY;
	}
	
	@Override
	public AxisAlignedBoundingBox getBoundingBox(QuadTreeType type) {
		if(type == QuadTreeType.DRAW)
			return mDrawAABB;
		else if(type == QuadTreeType.COLLISION)
			return mCollisionAABB;
		
		return null;
	}

}
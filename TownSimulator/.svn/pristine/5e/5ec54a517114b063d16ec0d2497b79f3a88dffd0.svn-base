package com.townSimulator.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDrawableObjects extends GameObjects implements GameDrawable{
	private Sprite mSprite;
	private float  mDepth;
	
	public GameDrawableObjects(Sprite sp, float depth)
	{
		mSprite = sp;
		mDepth = depth;
	}
	
	@Override
	public void drawSelf(SpriteBatch batch) {
		mSprite.draw(batch);
	}

	@Override
	public float getDepth() {
		return mDepth;
	}
}

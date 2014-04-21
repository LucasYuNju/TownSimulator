package com.townSimulator.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDrawableObject extends GameObjects implements GameDrawable{
	private Sprite 	mSprite;
	private float  	mDepth;
	private boolean mbVisible = true;;
	
	public GameDrawableObject(Sprite sp)
	{
		this(sp, 0.0f);
	}
	
	public GameDrawableObject(Sprite sp, float depth)
	{
		mSprite = sp;
		mDepth = depth;
	}
	
	@Override
	public void drawSelf(SpriteBatch batch) {
		if(mbVisible == false)
			return;
		
		mSprite.draw(batch);
	}

	@Override
	public float getDepth() {
		return mDepth;
	}
	
	public void setDepth(float depth)
	{
		mDepth = depth;
	}
	
	public void setDrawSize(float width, float height)
	{
		mSprite.setSize(width, height);
	}
	
	public void setDrawPosition(float x, float y)
	{
		mSprite.setPosition(x, y);
	}
	
	public float getDrawWidth()
	{
		return mSprite.getWidth();
	}
	
	public float getDrawHeight()
	{
		return mSprite.getHeight();
	}
	
	public float getDrawX()
	{
		return mSprite.getX();
	}
	
	public float getDrawY()
	{
		return mSprite.getY();
	}

	@Override
	public void setVisible(boolean v) {
		mbVisible = v;
	}
}

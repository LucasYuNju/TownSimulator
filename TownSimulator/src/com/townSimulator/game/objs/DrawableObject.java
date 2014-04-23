package com.townSimulator.game.objs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.townSimulator.game.scene.QuadTreeManageble;
import com.townSimulator.game.scene.QuadTreeType;
import com.townSimulator.utility.AxisAlignedBoundingBox;

public class DrawableObject implements Drawable, QuadTreeManageble{
	protected Sprite 					mSprite;
	protected AxisAlignedBoundingBox	mDrawAABB;
	protected float  					mDepth;
	protected boolean 					mbVisible = true;
	
	public DrawableObject(Sprite sp)
	{
		this(sp, 0.0f);
	}
	
	public DrawableObject(Sprite sp, float depth)
	{
		mSprite = sp;
		mDepth = depth;
		mDrawAABB = new AxisAlignedBoundingBox();
		resetAABB();
	}
	
	private void resetAABB()
	{
		mDrawAABB.minX = mSprite.getX();
		mDrawAABB.minY = mSprite.getY();
		mDrawAABB.maxX = mDrawAABB.minX + mSprite.getWidth();
		mDrawAABB.maxY = mDrawAABB.minY + mSprite.getHeight();
	}
	
	public Sprite getSprite()
	{
		return mSprite;
	}
	
	public void setSprite(Sprite sprite) {
		mSprite = sprite;
		resetAABB();
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
	
	@Override
	public void setDepth(float depth)
	{
		mDepth = depth;
	}
	
	@Override
	public void setDrawSize(float width, float height)
	{
		mSprite.setSize(width, height);
		resetAABB();
	}
	
	@Override
	public void setDrawPosition(float x, float y)
	{
		mSprite.setPosition(x, y);
		resetAABB();
	}
	
	@Override
	public float getDrawWidth()
	{
		return mSprite.getWidth();
	}
	
	@Override
	public float getDrawHeight()
	{
		return mSprite.getHeight();
	}
	
	@Override
	public float getDrawX()
	{
		return mSprite.getX();
	}
	
	@Override
	public float getDrawY()
	{
		return mSprite.getY();
	}

	@Override
	public void setVisible(boolean v) {
		mbVisible = v;
	}

	@Override
	public AxisAlignedBoundingBox getBoundingBox(QuadTreeType type) {
		if( type == QuadTreeType.DRAW )
			return mDrawAABB;
		
		return null;
	}
}

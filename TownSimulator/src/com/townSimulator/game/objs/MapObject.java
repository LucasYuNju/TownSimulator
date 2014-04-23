package com.townSimulator.game.objs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.townSimulator.game.scene.QuadTreeType;
import com.townSimulator.utility.AxisAlignedBoundingBox;
import com.townSimulator.utility.ResourceManager;

public class MapObject extends DrawableObject{
	private AxisAlignedBoundingBox 	mCollisionAABB;
	private Sprite 					mBoundsSp;
	
	public MapObject(Sprite sp) {
		super(sp);
		
		mCollisionAABB = new AxisAlignedBoundingBox();
		mBoundsSp = ResourceManager.createSprite("button_up");
	}
	
	public void setCollisionBounds(float minX, float minY, float maxX, float maxY)
	{
		mCollisionAABB.minX = minX;
		mCollisionAABB.minY = minY;
		mCollisionAABB.maxX = maxX;
		mCollisionAABB.maxY = maxY;
		
		mBoundsSp.setSize(maxX - minX, maxY - minY);
		mBoundsSp.setPosition(minX, minY);
	}

	@Override
	public void drawSelf(SpriteBatch batch) {
		super.drawSelf(batch);
		mBoundsSp.draw(batch);
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

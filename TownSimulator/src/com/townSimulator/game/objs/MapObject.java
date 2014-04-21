package com.townSimulator.game.objs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.townSimulator.game.render.GameDrawableObject;
import com.townSimulator.game.scene.Collidable;
import com.townSimulator.utility.AxisAlignedBoundingBox;
import com.townSimulator.utility.ResourceManager;

public class MapObject extends GameDrawableObject implements Collidable{
	private AxisAlignedBoundingBox mAABB;
	private Sprite mBoundsSp;
	
	public MapObject(Sprite sp) {
		super(sp);
		
		mAABB = new AxisAlignedBoundingBox();
		mBoundsSp = ResourceManager.createSprite("button_up");
	}

	@Override
	public AxisAlignedBoundingBox getCollisionBounds() {
		return mAABB;
	}
	
	public void setCollisionBounds(float minX, float minY, float maxX, float maxY)
	{
		mAABB.minX = minX;
		mAABB.minY = minY;
		mAABB.maxX = maxX;
		mAABB.maxY = maxY;
		
		mBoundsSp.setSize(maxX - minX, maxY - minY);
		mBoundsSp.setPosition(minX, minY);
	}

	@Override
	public void drawSelf(SpriteBatch batch) {
		super.drawSelf(batch);
		mBoundsSp.draw(batch);
	}
	
	
}

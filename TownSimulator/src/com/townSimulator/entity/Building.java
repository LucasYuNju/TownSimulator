package com.townSimulator.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Building extends Entity{
	//private Sprite boundingSp;
	
	public Building(Sprite sp) {
		super(sp);
		
		//boundingSp = ResourceManager.createSprite("button_up");
	}

	@Override
	public void drawSelf(SpriteBatch batch) {
		super.drawSelf(batch);
		//boundingSp.setBounds(mCollisionAABB.minX, mCollisionAABB.minY, mCollisionAABB.maxX - mCollisionAABB.minX, mCollisionAABB.maxY - mCollisionAABB.minY);
		//boundingSp.draw(batch);
	}
	
//	public Array<QuadTreeNode> getCollisionQuadNode()
//	{
//		return mCollisionQuadNodes;
//	}
	
}

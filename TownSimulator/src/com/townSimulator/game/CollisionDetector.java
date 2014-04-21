package com.townSimulator.game;

import com.badlogic.gdx.utils.Array;

public class CollisionDetector {
	private AxisAlignedBoundingBox	mWorldAABB;
	private CollisionQuadTreeNode	mRoot;
	
	public CollisionDetector(float worldMinX, float worldMinY, float worldMaxX, float worldMaxY)
	{
		mWorldAABB = new AxisAlignedBoundingBox(worldMinX, worldMinY, worldMaxX, worldMaxY);
		mRoot = new CollisionQuadTreeNode(mWorldAABB);
	}
	
	public boolean addCollidable(Collidable obj)
	{
		return mRoot.addCollidable(obj);
	}
	
	public boolean detectCollision(Collidable obj)
	{
		return mRoot.detectCollision(obj);
	}
	
	public boolean detectCollision(Collidable obj, Array<Collidable> collideObjs)
	{
		return mRoot.detectCollision(obj, collideObjs);
	}
	
	public boolean detectCollision(AxisAlignedBoundingBox aabb)
	{
		return mRoot.detectCollision(aabb);
	}
	
	public boolean detectCollision(AxisAlignedBoundingBox aabb, Array<Collidable> collideObjs)
	{
		return mRoot.detectCollision(aabb, collideObjs);
	}
	
	public boolean detectCollision(float x, float y)
	{
		return mRoot.detectCollision(x, y);
	}
	
	public boolean detectCollision(float x, float y, Array<Collidable> collideObjs)
	{
		return mRoot.detectCollision(x, y, collideObjs);
	}
	
}

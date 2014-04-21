package com.townSimulator.game;

import com.badlogic.gdx.utils.Array;
import com.townSimulator.utility.GameMath;

public class CollisionQuadTreeNode {
	private static final 	int 						LEAF_OBJ_CNT = 10;
	private 				AxisAlignedBoundingBox 		mAABB;
	private 				CollisionQuadTreeNode[]		mChildren;
	private					Array<Collidable>			mObjs;
	
	public CollisionQuadTreeNode(AxisAlignedBoundingBox aabb)
	{
		mAABB = aabb;
		mChildren = new CollisionQuadTreeNode[4];
		mObjs = new Array<Collidable>();
	}
	
	public boolean addCollidable( Collidable obj )
	{
		if(obj == null)
			return false;
		
		if( !GameMath.intersect(mAABB, obj.getCollisionBounds()) )
			return false;
		
		//No slice, it is a leaf
		if(mChildren[0] == null)
		{
			mObjs.add(obj);
			if(mObjs.size > LEAF_OBJ_CNT)
			{
				doQuadSlice();
			}
		}
		//Not a leaf
		else 
		{
			for (int i = 0; i < mChildren.length; i++) {
				mChildren[i].addCollidable(obj);
			}
		}
		
		return true;
	}
	
	public boolean detectCollision(Collidable obj)
	{
		return detectCollision(obj, null);
	}
	
	public boolean detectCollision(Collidable obj, Array<Collidable> collideObjs)
	{
		return detectCollision(obj.getCollisionBounds(), collideObjs);
	}
	
	public boolean detectCollision(AxisAlignedBoundingBox aabb)
	{
		return detectCollision(aabb, null);
	}
	
	public boolean detectCollision(AxisAlignedBoundingBox aabb, Array<Collidable> collideObjs)
	{
		if( !GameMath.intersect(mAABB, aabb) )
			return false;
		
		boolean bCollide = false;
		//A leaf
		if( mChildren[0] == null )
		{
			for (int i = 0; i < mObjs.size; i++) {
				if( GameMath.intersect(mObjs.get(i).getCollisionBounds(), aabb) )
				{
					if(collideObjs != null)
						collideObjs.add(mObjs.get(i));
					bCollide = true;
				}
			}
		}
		//Not a leaf
		else
		{
			for (int i = 0; i < mChildren.length; i++) {
				if( mChildren[i].detectCollision(aabb, collideObjs) )
					bCollide = true;
			}
		}
		
		return bCollide;
	}
	
	public boolean detectCollision(float x, float y)
	{
		return detectCollision(x, y, null);
	}
	
	public boolean detectCollision(float x, float y, Array<Collidable> collideObjs)
	{
		if( !GameMath.intersect(mAABB, x, y) )
			return false;
		
		boolean bCollide = false;
		//A leaf
		if( mChildren[0] == null )
		{
			for (int i = 0; i < mObjs.size; i++) {
				if( GameMath.intersect(mObjs.get(i).getCollisionBounds(), x, y) )
				{
					if(collideObjs != null)
						collideObjs.add(mObjs.get(i));
					bCollide = true;
				}
			}
		}
		//Not a leaf
		else
		{
			for (int i = 0; i < mChildren.length; i++) {
				if( mChildren[i].detectCollision(x, y, collideObjs) )
					bCollide = true;
			}
		}
		
		return bCollide;
	}
	
	private void doQuadSlice()
	{
		float centerX = (mAABB.minX + mAABB.maxX) * 0.5f;
		float centerY = (mAABB.minY + mAABB.maxY) * 0.5f;
		mChildren[0] = new CollisionQuadTreeNode( new AxisAlignedBoundingBox(mAABB.minX, centerY, 	 centerX, 	 mAABB.maxY) );
		mChildren[1] = new CollisionQuadTreeNode( new AxisAlignedBoundingBox(centerX, 	 centerY, 	 mAABB.maxX, mAABB.maxY) );
		mChildren[2] = new CollisionQuadTreeNode( new AxisAlignedBoundingBox(mAABB.minX, mAABB.minY, centerX, 	 centerY) );
		mChildren[3] = new CollisionQuadTreeNode( new AxisAlignedBoundingBox(centerX, 	 mAABB.minY, mAABB.maxX, centerY) );
		
		for (int i = 0; i < mObjs.size; i++) {
			for (int j = 0; j < mChildren.length; j++) {
				mChildren[j].addCollidable( mObjs.get(i) );
			}
		}
		
		mObjs.clear();
		mObjs = null;
	}
	
}

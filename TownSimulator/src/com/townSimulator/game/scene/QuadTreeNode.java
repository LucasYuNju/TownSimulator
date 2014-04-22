package com.townSimulator.game.scene;

import com.badlogic.gdx.utils.Array;
import com.townSimulator.utility.AxisAlignedBoundingBox;
import com.townSimulator.utility.GameMath;

public class QuadTreeNode {
	private static final 	int 						LEAF_OBJ_CNT = 10;
	private 				AxisAlignedBoundingBox 		mAABB;
	private 				QuadTreeNode[]		mChildren;
	private					Array<QuadTreeManageble>			mObjs;
	private 				QuadTreeType mType;
	
	public QuadTreeNode(QuadTreeType type, AxisAlignedBoundingBox aabb)
	{
		mType = type;
		mAABB = aabb;
		mChildren = new QuadTreeNode[4];
		mObjs = new Array<QuadTreeManageble>();
	}
	
	public boolean addIntersection( QuadTreeManageble obj )
	{
		if(obj == null)
			return false;
		
		if( !GameMath.intersect(mAABB, obj.getBoundingBox(mType)) )
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
				mChildren[i].addIntersection(obj);
			}
		}
		
		return true;
	}
	
	public boolean detectIntersection(QuadTreeManageble obj)
	{
		return detectIntersection(obj, null);
	}
	
	public boolean detectIntersection(QuadTreeManageble obj, Array<QuadTreeManageble> collideObjs)
	{
		return detectIntersection(obj.getBoundingBox(mType), collideObjs);
	}
	
	public boolean detectIntersection(AxisAlignedBoundingBox aabb)
	{
		return detectIntersection(aabb, null);
	}
	
	public boolean detectIntersection(AxisAlignedBoundingBox aabb, Array<QuadTreeManageble> collideObjs)
	{
		if( !GameMath.intersect(mAABB, aabb) )
			return false;
		
		boolean bCollide = false;
		//A leaf
		if( mChildren[0] == null )
		{
			for (int i = 0; i < mObjs.size; i++) {
				if( GameMath.intersect(mObjs.get(i).getBoundingBox(mType), aabb) )
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
				if( mChildren[i].detectIntersection(aabb, collideObjs) )
					bCollide = true;
			}
		}
		
		return bCollide;
	}
	
	public boolean detectIntersection(float x, float y)
	{
		return detectIntersection(x, y, null);
	}
	
	public boolean detectIntersection(float x, float y, Array<QuadTreeManageble> collideObjs)
	{
		if( !GameMath.intersect(mAABB, x, y) )
			return false;
		
		boolean bCollide = false;
		//A leaf
		if( mChildren[0] == null )
		{
			for (int i = 0; i < mObjs.size; i++) {
				if( GameMath.intersect(mObjs.get(i).getBoundingBox(mType), x, y) )
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
				if( mChildren[i].detectIntersection(x, y, collideObjs) )
					bCollide = true;
			}
		}
		
		return bCollide;
	}
	
	private void doQuadSlice()
	{
		float centerX = (mAABB.minX + mAABB.maxX) * 0.5f;
		float centerY = (mAABB.minY + mAABB.maxY) * 0.5f;
		mChildren[0] = new QuadTreeNode( mType, new AxisAlignedBoundingBox(mAABB.minX, centerY, 	 centerX, 	 mAABB.maxY) );
		mChildren[1] = new QuadTreeNode( mType, new AxisAlignedBoundingBox(centerX, 	 centerY, 	 mAABB.maxX, mAABB.maxY) );
		mChildren[2] = new QuadTreeNode( mType, new AxisAlignedBoundingBox(mAABB.minX, mAABB.minY, centerX, 	 centerY) );
		mChildren[3] = new QuadTreeNode( mType, new AxisAlignedBoundingBox(centerX, 	 mAABB.minY, mAABB.maxX, centerY) );
		
		for (int i = 0; i < mObjs.size; i++) {
			for (int j = 0; j < mChildren.length; j++) {
				mChildren[j].addIntersection( mObjs.get(i) );
			}
		}
		
		mObjs.clear();
		mObjs = null;
	}
	
}

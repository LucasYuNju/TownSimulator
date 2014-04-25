package com.TownSimulator.entity;

import com.TownSimulator.scene.QuadTreeManageble;
import com.TownSimulator.scene.QuadTreeNode;
import com.TownSimulator.scene.QuadTreeType;
import com.TownSimulator.scene.SceneManager;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class DrawableEntity implements Drawable, QuadTreeManageble{
	protected Sprite 					mSprite;
	protected AxisAlignedBoundingBox	mDrawAABB;
	protected Array<QuadTreeNode>		mDrawQuadNodes;
	protected float  					mDepth;
	protected boolean 					mbVisible = true;
	
	public DrawableEntity(Sprite sp)
	{
		this(sp, 0.0f);
	}
	
	public DrawableEntity(Sprite sp, float depth)
	{
		mSprite = sp;
		mDepth = depth;
		mDrawAABB = new AxisAlignedBoundingBox();
		mDrawQuadNodes = new Array<QuadTreeNode>();
		resetDrawAABB();
	}
	
	private void resetDrawAABB()
	{
		mDrawAABB.minX = mSprite.getX();
		mDrawAABB.minY = mSprite.getY();
		mDrawAABB.maxX = mDrawAABB.minX + mSprite.getWidth();
		mDrawAABB.maxY = mDrawAABB.minY + mSprite.getHeight();
		
//		for (int i = 0; i < mDrawQuadNodes.size; i++) {
//			mDrawQuadNodes.get(i).update(this);
//		}
		if(mDrawQuadNodes.size > 0)
			SceneManager.getInstance().updateDrawScissor(this);
	}
	
	public Sprite getSprite()
	{
		return mSprite;
	}
	
	public void setSprite(Sprite sprite) {
		mSprite = sprite;
		resetDrawAABB();
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
		resetDrawAABB();
	}
	
	@Override
	public void setDrawPosition(float x, float y)
	{
		mSprite.setPosition(x, y);
		resetDrawAABB();
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


	@Override
	public void addContainedQuadTreeNode(QuadTreeType type, QuadTreeNode node) {
		if(type == QuadTreeType.DRAW)
			mDrawQuadNodes.add(node);
	}

//	@Override
//	public void removeContainedQuadTreeNode(QuadTreeType type, QuadTreeNode node) {
//		if(type == QuadTreeType.DRAW)
//			mDrawQuadNodes.removeValue(node, false);
//	}

	@Override
	public void dettachQuadTree(QuadTreeType type) {
		Array<QuadTreeNode> nodes = null;
		if(type == QuadTreeType.DRAW)
			nodes = mDrawQuadNodes;
		
		if(nodes != null)
		{
			for (int i = 0; i < nodes.size; i++) {
				nodes.get(i).removeManageble(this);
			}
			nodes.clear();
		}
	}
}

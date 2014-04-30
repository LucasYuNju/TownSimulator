package com.TownSimulator.entity;

import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.render.Drawable;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.quadtree.QuadTreeManageble;
import com.TownSimulator.utility.quadtree.QuadTreeNode;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Entity implements Drawable, QuadTreeManageble{
	protected Sprite 					mSprite;
	protected float						mPosXWorld;
	protected float						mPosYWorld;
	protected AxisAlignedBoundingBox	mDrawAABBLocal;
	protected AxisAlignedBoundingBox	mDrawAABBWorld;
	protected Array<QuadTreeNode>		mDrawQuadNodes;
	protected float  					mDepth;
	protected boolean 					mbVisible = true;
	protected AxisAlignedBoundingBox 	mCollisionAABBLocal;
	protected AxisAlignedBoundingBox	mCollisionAABBWorld;
	protected Array<QuadTreeNode>		mCollisionQuadNodes;
	protected EntityListener			mListener;
	protected boolean					mUseDrawMinYAsDepth;
	
	public Entity(String textureName)
	{
		this(ResourceManager.getInstance(ResourceManager.class).createSprite(textureName));
	}
	
	public Entity(Sprite sp) 
	{
		this(sp, 0.0f, true);
	}
	
	public Entity(Sprite sp, float depth, boolean useDrawMinYAsDepth)
	{
		mDepth = depth;
		mDrawAABBLocal = new AxisAlignedBoundingBox();
		mDrawAABBWorld = new AxisAlignedBoundingBox();
		mDrawQuadNodes = new Array<QuadTreeNode>();
		mCollisionAABBLocal = new AxisAlignedBoundingBox();
		mCollisionAABBWorld = new AxisAlignedBoundingBox();
		mCollisionQuadNodes = new Array<QuadTreeNode>();
		mUseDrawMinYAsDepth = useDrawMinYAsDepth;
		
		setSprite(sp);
	}
	
	public AxisAlignedBoundingBox getDrawAABBLocal()
	{
		return mDrawAABBLocal;
	}
	
	public AxisAlignedBoundingBox getCollisionAABBLocal()
	{
		return mCollisionAABBLocal;
	}
	
	public Sprite getSprite()
	{
		return mSprite;
	}
	
	public void setTextureName(String textureName)
	{
		setSprite(ResourceManager.getInstance(ResourceManager.class).createSprite(textureName));
	}
	
	public void setSprite(Sprite sprite) {
		mSprite = sprite;
		resetSpriteBounds();
	}
	
	public void setPositionWorld(float x, float y)
	{
		mPosXWorld = x;
		mPosYWorld = y;
		
		resetSpriteBounds();
		resetDrawAABBWorld();
		resetCollisionAABBWorld();
	}
	
	public float getPositionXWorld()
	{
		return mPosXWorld;
	}
	
	public float getPositionYWorld()
	{
		return mPosYWorld;
	}
	
	private void resetSpriteBounds()
	{
		if(mSprite == null)
			return;
		
		mSprite.setBounds(	mPosXWorld + mDrawAABBLocal.minX, mPosYWorld + mDrawAABBLocal.minY,
							mDrawAABBLocal.getWidth(), mDrawAABBLocal.getHeight());
	}
	
	private void resetDrawAABBWorld()
	{
		mDrawAABBWorld.minX = mDrawAABBLocal.minX + mPosXWorld;
		mDrawAABBWorld.minY = mDrawAABBLocal.minY + mPosYWorld;
		mDrawAABBWorld.maxX = mDrawAABBLocal.maxX + mPosXWorld;
		mDrawAABBWorld.maxY = mDrawAABBLocal.maxY + mPosYWorld;
		
		if(mDrawQuadNodes.size > 0)
			Renderer.getInstance(Renderer.class).updateDrawScissor(this);
	}
	
	private void resetCollisionAABBWorld()
	{
		mCollisionAABBWorld.minX = mCollisionAABBLocal.minX + mPosXWorld;
		mCollisionAABBWorld.minY = mCollisionAABBLocal.minY + mPosYWorld;
		mCollisionAABBWorld.maxX = mCollisionAABBLocal.maxX + mPosXWorld;
		mCollisionAABBWorld.maxY = mCollisionAABBLocal.maxY + mPosYWorld;
		
		if(mCollisionQuadNodes.size > 0)
			CollisionDetector.getInstance(CollisionDetector.class).updateCollisionDetector(this);
	}
	
	@Override
	public void drawSelf(SpriteBatch batch) {
		if(mbVisible == false)
			return;
		
		mSprite.draw(batch);
	}

	@Override
	public float getDepth() {
		
		if(mUseDrawMinYAsDepth)
			return mDrawAABBWorld.minY;
		else
			return mDepth;
	}
	
	public void setUseDrawMinYAsDepth(boolean value)
	{
		mUseDrawMinYAsDepth = value;
	}
	
	public boolean isUseDrawMinYAsDepth()
	{
		return mUseDrawMinYAsDepth;
	}
	
	public void setDepth(float depth)
	{
		mDepth = depth;
	}
	
	public void setVisible(boolean v) {
		mbVisible = v;
	}
	
	public boolean isVisible()
	{
		return mbVisible;
	}
	
	public void setListener(EntityListener baseObjectListener)
	{
		mListener = baseObjectListener;
	}
	
	public boolean detectTouchDown()
	{
		if(mListener != null)
		{
			mListener.objBeTouchDown(this);
			return true;
		}
		return false;
	}
	
	public void detectTouchUp()
	{
		if(mListener != null)
			mListener.objBeTouchUp(this);
	}
	
	public void detectTouchDragged(float x, float y, float deltaX, float deltaY) {
		if(mListener != null)
			mListener.objBeTouchDragged(this, x, y, deltaX, deltaY);
	}
	
	public void setCollisionAABBLocal(float minX, float minY, float maxX, float maxY)
	{
		mCollisionAABBLocal.minX = minX;
		mCollisionAABBLocal.minY = minY;
		mCollisionAABBLocal.maxX = maxX;
		mCollisionAABBLocal.maxY = maxY;
		
		resetCollisionAABBWorld();
	}
	
	public void setDrawAABBLocal(float minX, float minY, float maxX, float maxY)
	{
		mDrawAABBLocal.minX = minX;
		mDrawAABBLocal.minY = minY;
		mDrawAABBLocal.maxX = maxX;
		mDrawAABBLocal.maxY = maxY;
		
		resetDrawAABBWorld();
	}
	
	
	public void translate(float deltaX, float deltaY)
	{
		setPositionWorld(mPosXWorld + deltaX, mPosYWorld + deltaY);//(mDrawAABBLocal.minX + deltaX, mDrawAABBLocal.minY + deltaY);
	}

	@Override
	public AxisAlignedBoundingBox getAABBWorld(QuadTreeType type) {
		if(type == QuadTreeType.DRAW)
			return mDrawAABBWorld;
		else if(type == QuadTreeType.COLLISION)
			return mCollisionAABBWorld;
		
		return null;
	}

	@Override
	public void addContainedQuadTreeNode(QuadTreeType type, QuadTreeNode node) {
		if(type == QuadTreeType.DRAW )
			mDrawQuadNodes.add(node);
		else if(type == QuadTreeType.COLLISION)
			mCollisionQuadNodes.add(node);
	}

	@Override
	public void dettachQuadTree(QuadTreeType type) {
		Array<QuadTreeNode> nodes = null;
		if(type == QuadTreeType.DRAW)
			nodes = mDrawQuadNodes;
		else if(type == QuadTreeType.COLLISION)
			nodes = mCollisionQuadNodes;
		
		if(nodes != null)
		{
			for (int i = 0; i < nodes.size; i++) {
				nodes.get(i).removeManageble(this);
			}
			nodes.clear();
		}
	}
}

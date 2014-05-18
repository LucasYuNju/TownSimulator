package com.TownSimulator.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.collision.CollisionDetectorListener;
import com.TownSimulator.render.Drawable;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.quadtree.QuadTreeManageble;
import com.TownSimulator.utility.quadtree.QuadTreeNode;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity implements Drawable, QuadTreeManageble, Serializable{
	private static final long serialVersionUID = 5545997297127601552L;
	protected transient Sprite 			mSprite;
	private String 						textureName;
	protected float						mPosXWorld;
	protected float						mPosYWorld;
	protected AxisAlignedBoundingBox	mDrawAABBLocal;
	protected AxisAlignedBoundingBox	mDrawAABBWorld;
	protected float  					mDepth;
	protected boolean 					mbVisible = true;
	protected AxisAlignedBoundingBox 	mCollisionAABBLocal;
	protected AxisAlignedBoundingBox	mCollisionAABBWorld;
	protected EntityListener			mListener;
	protected boolean					mUseDrawMinYAsDepth;
	protected transient List<QuadTreeNode>		mDrawQuadNodes;
	protected transient List<QuadTreeNode>		mCollisionQuadNodes;

	protected static Entity selectedEntity = null;
	protected boolean isSelected = false;
	public static void initStatic()
	{
		selectedEntity = null;
		
		CollisionDetector.getInstance(CollisionDetector.class).addListener(new CollisionDetectorListener() {
			
			@Override
			public void emptyTapped() {
				if(selectedEntity != null)
				{
					selectedEntity.setSelected(false);
					selectedEntity = null;
				}
			}
		});
	}
	
	public Entity(String textureName)
	{
		this.textureName = textureName;
		Sprite sp = null;
		if(textureName != null && !textureName.isEmpty()) {
			sp = ResourceManager.getInstance(ResourceManager.class).createSprite(textureName);
		}
		initEntity(sp, 0.0f, true);
	}
	
	private void initEntity(Sprite sp, float depth, boolean useDrawMinYAsDepth)
	{
		mDepth = depth;
		mDrawAABBLocal = new AxisAlignedBoundingBox();
		mDrawAABBWorld = new AxisAlignedBoundingBox();
		mDrawQuadNodes = new ArrayList<QuadTreeNode>();
		mCollisionAABBLocal = new AxisAlignedBoundingBox();
		mCollisionAABBWorld = new AxisAlignedBoundingBox();
		mCollisionQuadNodes = new ArrayList<QuadTreeNode>();
		mUseDrawMinYAsDepth = useDrawMinYAsDepth;
		
		setSprite(sp);
	}
	
	public void setSelected(boolean value)
	{
		isSelected = value;
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
		
		if(mDrawQuadNodes.size() > 0)
			Renderer.getInstance(Renderer.class).updateDrawScissor(this);
	}
	
	private void resetCollisionAABBWorld()
	{
		mCollisionAABBWorld.minX = mCollisionAABBLocal.minX + mPosXWorld;
		mCollisionAABBWorld.minY = mCollisionAABBLocal.minY + mPosYWorld;
		mCollisionAABBWorld.maxX = mCollisionAABBLocal.maxX + mPosXWorld;
		mCollisionAABBWorld.maxY = mCollisionAABBLocal.maxY + mPosYWorld;
		
		if(mCollisionQuadNodes.size() > 0)
			CollisionDetector.getInstance(CollisionDetector.class).updateCollisionDetector(this);
	}
	
	@Override
	public void drawSelf(SpriteBatch batch) {
		if(mbVisible == false || mSprite == null)
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
		return true;
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
	
	public void detectTapped()
	{
		if(mListener != null)
			mListener.objBeTapped(this);
		
		if(selectedEntity != null)
			selectedEntity.setSelected(false);
		selectedEntity = this;
		setSelected(true);
	}
	
	public void setCollisionAABBLocal(float minX, float minY, float maxX, float maxY)
	{
		mCollisionAABBLocal.minX = minX;
		mCollisionAABBLocal.minY = minY;
		mCollisionAABBLocal.maxX = maxX;
		mCollisionAABBLocal.maxY = maxY;
		
		resetSpriteBounds();
		resetCollisionAABBWorld();
	}
	
	public void setDrawAABBLocal(float minX, float minY, float maxX, float maxY)
	{
		mDrawAABBLocal.minX = minX;
		mDrawAABBLocal.minY = minY;
		mDrawAABBLocal.maxX = maxX;
		mDrawAABBLocal.maxY = maxY;
		
		resetSpriteBounds();
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
		List<QuadTreeNode> nodes = null;
		if(type == QuadTreeType.DRAW)
			nodes = mDrawQuadNodes;
		else if(type == QuadTreeType.COLLISION)
			nodes = mCollisionQuadNodes;
		
		if(nodes != null)
		{
			for (int i = 0; i < nodes.size(); i++) {
				nodes.get(i).removeManageble(this);
			}
			nodes.clear();
		}
	}
		
	protected void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		Sprite sp = ResourceManager.getInstance(ResourceManager.class).createSprite(textureName);
		Singleton.getInstance(CollisionDetector.class).attachCollisionDetection(this);
		Singleton.getInstance(Renderer.class).attachDrawScissor(this);
		setSprite(sp);
	}
}

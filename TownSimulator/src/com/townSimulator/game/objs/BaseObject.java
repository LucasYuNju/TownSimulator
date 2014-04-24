package com.townSimulator.game.objs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.townSimulator.game.scene.QuadTreeNode;
import com.townSimulator.game.scene.QuadTreeType;
import com.townSimulator.game.scene.SceneManager;
import com.townSimulator.utility.AxisAlignedBoundingBox;

public class BaseObject extends DrawableObject {
	protected AxisAlignedBoundingBox 	mCollisionAABB;
	protected Array<QuadTreeNode>				mCollisionQuadNodes;
	protected BaseObjectListener		mListener;
	
	public BaseObject(Sprite sp) {
		super(sp);
		
		mCollisionAABB = new AxisAlignedBoundingBox();
		mCollisionQuadNodes = new Array<QuadTreeNode>();
	}
	
	public void setListener(BaseObjectListener baseObjectListener)
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
	
	/**
	 * Set the collision bounds relative to the world.
	 * @param minX
	 * @param minY
	 * @param maxX
	 * @param maxY
	 */
	public void setCollisionBounds(float minX, float minY, float maxX, float maxY)
	{
		mCollisionAABB.minX = minX;
		mCollisionAABB.minY = minY;
		mCollisionAABB.maxX = maxX;
		mCollisionAABB.maxY = maxY;
		
		if(mCollisionQuadNodes.size > 0)
			SceneManager.getInstance().updateCollisionDetector(this);
	}
	
	/**
	 * Set the collision bounds relative to the draw position.
	 * @param minX
	 * @param minY
	 * @param maxX
	 * @param maxY
	 */
	public void setRelativeCollisionBounds(float minX, float minY, float maxX, float maxY)
	{
		setCollisionBounds(minX + mDrawAABB.minX, minY + mDrawAABB.minY, maxX + mDrawAABB.minX, maxY + mDrawAABB.minY);
	}
	
	/**
	 * Set the collision bounds which's
	 *  centerX = (drawX + drawWidth*0.5f)
	 *  centerY = (drawY)
	 *  xEntend = drawWidth * widthScale * 0.5f
	 *  yEntend = xEntend
	 * @param widthScale
	 */
	public void setCollisionBoundsWithWidthScale(float widthScale)
	{
		float centerX = (mDrawAABB.minX + mDrawAABB.maxX) * 0.5f;
		float centerY = mDrawAABB.minY;
		float extend = widthScale * (mDrawAABB.maxX - mDrawAABB.minX) * 0.5f;
		setCollisionBounds(centerX - extend, centerY - extend, centerX + extend, centerY + extend);
	}
	
	public void translate(float deltaX, float deltaY)
	{
		setDrawPosition(mDrawAABB.minX + deltaX, mDrawAABB.minY + deltaY);
		
		mCollisionAABB.minX += deltaX;
		mCollisionAABB.maxX += deltaX;
		mCollisionAABB.minY += deltaY;
		mCollisionAABB.maxY += deltaY;
		
//		for (int i = 0; i < mCollisionQuadNodes.size; i++) {
//			mCollisionQuadNodes.get(i).update(this);
//		}
//		dettachQuadTree(QuadTreeType.COLLISION);
//		SceneManager.getInstance().attachCollisionDetection(this);
		if(mCollisionQuadNodes.size > 0)
			SceneManager.getInstance().updateCollisionDetector(this);
	}
	
	/**
	 * Set the position of this object, it will update draw bounds position and collision bounds position.
	 * the x and y is the origin position of the draw bounds.
	 * @param x world position drawX
	 * @param y world position drawY
	 */
	public void setPositionOriginDraw(float x, float y)
	{
		float deltaX = x - mDrawAABB.minX;
		float deltaY = y - mDrawAABB.minY;
		translate(deltaX, deltaY);
	}
	
	/**
	 * Set the position of this object, it will update draw bounds position and collision bounds position.
	 * the x and y is the origin position of the collision bounds.
	 * @param x world position collisionX
	 * @param y world position collisionY
	 */
	public void setPositionOriginCollision(float x, float y)
	{
		float deltaX = x - mCollisionAABB.minX;
		float deltaY = y - mCollisionAABB.minY;
		translate(deltaX, deltaY);
	}

	@Override
	public void setDrawPosition(float x, float y) {
		super.setDrawPosition(x, y);
		setDepth(y);
	}

	@Override
	public AxisAlignedBoundingBox getBoundingBox(QuadTreeType type) {
		if(type == QuadTreeType.DRAW)
			return mDrawAABB;
		else if(type == QuadTreeType.COLLISION)
			return mCollisionAABB;
		
		return null;
	}

	@Override
	public void addContainedQuadTreeNode(QuadTreeType type, QuadTreeNode node) {
		if(type == QuadTreeType.DRAW )
			mDrawQuadNodes.add(node);
		else if(type == QuadTreeType.COLLISION)
			mCollisionQuadNodes.add(node);
	}

//	@Override
//	public void removeContainedQuadTreeNode(QuadTreeType type, QuadTreeNode node) {
//		if(type == QuadTreeType.DRAW )
//			mDrawQuadNodes.removeValue(node, false);
//		else if(type == QuadTreeType.COLLISION)
//			mCollisionQuadNodes.removeValue(node, false);
//	}

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
	

	
//	public interface BaseObjectListener
//	{
//		public void objBeTouced(BaseObject obj);
//	}

}

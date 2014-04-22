package com.townSimulator.game.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.townSimulator.utility.AxisAlignedBoundingBox;

public class GameRenderManager {
	private OrthographicCamera 		mCamera;
	private RenderBatch		   		mRenderBatch;
	private AxisAlignedBoundingBox	mScissorAABB;
	
	public GameRenderManager(OrthographicCamera camera)
	{
		mCamera = camera;
		mRenderBatch = new RenderBatch();
		mScissorAABB = new AxisAlignedBoundingBox();
	}
	
	
	public void renderBegin()
	{
		mCamera.update();
		updateScissor();
	}
	
	private void updateScissor()
	{
		mScissorAABB.minX = mCamera.position.x - mCamera.viewportWidth 	* 0.5f;
		mScissorAABB.minY = mCamera.position.y - mCamera.viewportHeight * 0.5f;
		mScissorAABB.maxX = mCamera.position.x + mCamera.viewportWidth 	* 0.5f;
		mScissorAABB.maxY = mCamera.position.y + mCamera.viewportHeight * 0.5f;
	}
	
	public AxisAlignedBoundingBox getScissor()
	{
		return mScissorAABB;
	}
	
	public void dispose()
	{
		mRenderBatch.dispose();
	}
	
	public void renderEnd()
	{
		mRenderBatch.setProjectionMatrix(mCamera.combined);
		mRenderBatch.doRender();
	}
	
	public void draw(GameDrawable draw)
	{
		mRenderBatch.addDrawable(draw);
	}
	
//	public void addDrawContainer(GameDrawableContainer drawContainer)
//	{
//		Rectangle scissorRect = new Rectangle(
//				mCamera.position.x - mCamera.viewportWidth*0.5f,
//				mCamera.position.y - mCamera.viewportHeight*0.5f,
//				mCamera.viewportWidth, mCamera.viewportHeight);
//		drawContainer.draw(mRenderBatch, scissorRect);
//	}
}

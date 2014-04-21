package com.townSimulator.game.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class GameRenderManager {
	private OrthographicCamera mCamera;
	private RenderBatch		   mRenderBatch;
	
	public GameRenderManager(OrthographicCamera camera)
	{
		mCamera = camera;
		mRenderBatch = new RenderBatch();
	}
	
	
	public void renderBegin()
	{
		mCamera.update();
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
	
	public void addDrawContainer(GameDrawableContainer drawContainer)
	{
		Rectangle scissorRect = new Rectangle(
				mCamera.position.x - mCamera.viewportWidth*0.5f,
				mCamera.position.y - mCamera.viewportHeight*0.5f,
				mCamera.viewportWidth, mCamera.viewportHeight);
		drawContainer.draw(mRenderBatch, scissorRect);
	}
}

package com.townSimulator.game.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraController {
	private final 	float 				ZOOM_SPEED = 0.1f;
	private 		OrthographicCamera	mCamera;
	private 		float 				mBaseCameraWidth;
	private 		float 				mBaseCameraHeight;
	private 		float 				mCameraScale;
	private 		float   			mZoomInitDist = 0.0f;
	private 		float   			mPrevZoomDist = 0.0f;
	private 		Vector3 			mZoomOriginW  = new Vector3();
	private static 	CameraController	instance = null;
	
	public CameraController(OrthographicCamera camera)
	{
		instance = this;
		mCamera = camera;
		mBaseCameraWidth = Gdx.graphics.getWidth();
		mBaseCameraHeight = Gdx.graphics.getHeight();
		mCameraScale = 1.0f;
	}
	
	/*
	 * 不知道应该如何初始化CameraController，所以在instance为null时抛出unchecked exception
	 */
	public synchronized static CameraController getInstance() {
		if(instance == null)
			throw new NullPointerException();
		return instance;
	}
	
	public float getX() {
		return mCamera.position.x;
	}
	
	public float getY() {
		return mCamera.position.y;
	}
	
	public boolean zoom(float initialDistance, float distance) {
		if( mZoomInitDist != initialDistance )
		{
			mZoomInitDist = initialDistance;
			mPrevZoomDist = initialDistance;
			float xS = ( Gdx.input.getX(0) + Gdx.input.getX(1) ) * 0.5f;
			float yS = ( Gdx.input.getY(0) + Gdx.input.getY(1) ) * 0.5f;
			mZoomOriginW.x = xS;
			mZoomOriginW.y = yS;
			mCamera.unproject(mZoomOriginW);
		}
		
		float scaleDelta = (mPrevZoomDist - distance ) / 32.0f * ZOOM_SPEED;
		float scale = mCameraScale + scaleDelta;
		scale = MathUtils.clamp(mCameraScale, 0.5f, 4.0f);
		if( scale != mCameraScale )
		{
			mCameraScale = scale;
			float vpWidth  = mBaseCameraWidth * mCameraScale;
			float vpHeight = mBaseCameraHeight * mCameraScale;
			Vector2 transVec = new Vector2(	mZoomOriginW.x - mCamera.position.x,
											mZoomOriginW.y - mCamera.position.y);
			transVec.scl(1.0f - vpWidth / mCamera.viewportWidth);
			mCamera.translate(transVec);
			mCamera.viewportWidth = vpWidth;
			mCamera.viewportHeight = vpHeight;
		}
		
		mPrevZoomDist = distance;
		return true;
	}
	
	public void moveRelScreen( float deltaX, float deltaY )
	{
		mCamera.translate(-deltaX * mCameraScale, deltaY * mCameraScale);
	}
}

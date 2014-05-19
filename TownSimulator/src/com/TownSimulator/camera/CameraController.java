package com.TownSimulator.camera;


import java.util.ArrayList;

import com.TownSimulator.io.InputMgr;
import com.TownSimulator.io.InputMgrListener;
import com.TownSimulator.map.Map;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.SingletonPublisher;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraController extends SingletonPublisher<CameraListener>{
	private final 	float 					ZOOM_SPEED = 0.1f;
	private 		OrthographicCamera		mCamera;
	private 		float 					mBaseCameraWidth;
	private 		float 					mBaseCameraHeight;
	private 		float 					mCameraScale;
	private 		float   				mZoomInitDist = 0.0f;
	private 		float   				mPrevZoomDist = 0.0f;
	private 		Vector3 				mZoomOriginW  = new Vector3();
	private			boolean					mbMovalble = true;
	private 		AxisAlignedBoundingBox	mCameraViewAABB;
	private 		GestureDetector			mGestureDetector;
	
	private CameraController()
	{
		mCamera = new OrthographicCamera();
		mBaseCameraWidth = Gdx.graphics.getWidth() * 2.0f;
		mBaseCameraHeight = Gdx.graphics.getHeight() * 2.0f;
		mCamera.viewportWidth = mBaseCameraWidth;
		mCamera.viewportHeight = mBaseCameraHeight;
		mCamera.position.x = Map.MAP_WIDTH  * Settings.UNIT * 0.5f;
		mCamera.position.y = Map.MAP_HEIGHT * Settings.UNIT * 0.5f;
		mCameraScale = 1.0f;
		mCameraViewAABB = new AxisAlignedBoundingBox();
		mListeners = new ArrayList<CameraListener>();
		
		mGestureDetector = new GestureDetector(new GestureListener() {
			
			@Override
			public boolean zoom(float initialDistance, float distance) {
				CameraController.this.zoom(initialDistance, distance);
				return true;
			}
			
			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public boolean tap(float x, float y, int count, int button) {
				return false;
			}
			
			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
					Vector2 pointer1, Vector2 pointer2) {
				return false;
			}
			
			@Override
			public boolean panStop(float x, float y, int pointer, int button) {
				return false;
			}
			
			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				tryMoveRelScreen(deltaX, deltaY);
				return true;
			}
			
			@Override
			public boolean longPress(float x, float y) {
				return false;
			}
			
			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				return false;
			}
		});
		
		InputMgr.getInstance(InputMgr.class).addListener(new InputMgrListener() {
			
			@Override
			public boolean touchUp(float screenX, float screenY, int pointer, int button) {
				return mGestureDetector.touchUp(screenX, screenY, pointer, button);
			}
			
			@Override
			public void touchDragged(float screenX, float screenY, float deltaX,
					float deltaY, int pointer) {
				mGestureDetector.touchDragged(screenX, screenY, pointer);
			}
			
			@Override
			public boolean touchDown(float screenX, float screenY, int pointer,
					int button) {
				return mGestureDetector.touchDown(screenX, screenY, pointer, button);
			}
		});
	}
	
	public void updateCamera()
	{
		mCamera.update();
		updateCameraViewAABB();
	}
	
	private void updateCameraViewAABB()
	{
		mCameraViewAABB.minX = mCamera.position.x - mCamera.viewportWidth 	* 0.5f;
		mCameraViewAABB.minY = mCamera.position.y - mCamera.viewportHeight * 0.5f;
		mCameraViewAABB.maxX = mCamera.position.x + mCamera.viewportWidth 	* 0.5f;
		mCameraViewAABB.maxY = mCamera.position.y + mCamera.viewportHeight * 0.5f;
	}
	
	public Matrix4 getCameraCombined()
	{
		return mCamera.combined;
	}
	
	public AxisAlignedBoundingBox getCameraViewAABB()
	{
		return mCameraViewAABB;
	}
	
	public float getX() {
		return mCamera.position.x;
	}
	
	public float getY() {
		return mCamera.position.y;
	}
	
	public void setMovable(boolean b)
	{
		mbMovalble = b;
	}
	
	public void worldToScreen(Vector3 posW)
	{
		mCamera.project(posW);
	}
	
	public void worldToScreen(Vector2 posS)
	{
		Vector3 pos3 = new Vector3(posS, 0.0f);
		worldToScreen(pos3);
		posS.x = pos3.x;
		posS.y = pos3.y;
	}
	
	public void screenToWorld(Vector3 posS)
	{
		mCamera.unproject(posS);
	}
	
	public void screenToWorld(Vector2 posS)
	{
		Vector3 pos3 = new Vector3(posS, 0.0f);
		screenToWorld(pos3);
		posS.x = pos3.x;
		posS.y = pos3.y;
	}
	
	public float screenToWorldDeltaX(float deltaX)
	{
		return deltaX * mCamera.viewportWidth / Gdx.graphics.getWidth(); 
	}
	
	public float screenToWorldDeltaY(float deltaY)
	{
		return deltaY * mCamera.viewportHeight / Gdx.graphics.getHeight();
	}
	
	public boolean zoom(float initialDistance, float distance) {
		float prevWidth = mCamera.viewportWidth;
		float prevHeight = mCamera.viewportHeight;
		
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
		
		float scaleDelta = (mPrevZoomDist - distance ) / Settings.UNIT * ZOOM_SPEED;
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
		
		for (int i = 0; i < mListeners.size(); i++) {
			mListeners.get(i).cameraZoomed(prevWidth, prevHeight, mCamera.viewportWidth, mCamera.viewportHeight);
		}
		return true;
	}
	
	public void tryMoveRelScreen( float deltaX, float deltaY )
	{
		if(mbMovalble)
			moveRelScreen(deltaX, deltaY);
	}
	
	private void moveRelScreen( float deltaX, float deltaY )
	{
		float x = MathUtils.clamp(mCamera.position.x - screenToWorldDeltaX(deltaX),
				mCamera.viewportWidth * 0.5f, Map.MAP_WIDTH * Settings.UNIT - mCamera.viewportWidth * 0.5f);
		float y = MathUtils.clamp(mCamera.position.y + screenToWorldDeltaY(deltaY),
				mCamera.viewportHeight * 0.5f, Map.MAP_HEIGHT * Settings.UNIT - mCamera.viewportHeight * 0.5f);
		float dx = x - mCamera.position.x;
		float dy = y - mCamera.position.y;
		for (int i = 0; i < mListeners.size(); i++) {
			mListeners.get(i).cameraMoved(dx, dy);
		}
		mCamera.position.x = x;
		mCamera.position.y = y;
	}
}

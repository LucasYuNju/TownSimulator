package com.townSimulator;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.townSimulator.broker.BuildBroker;
import com.townSimulator.entity.Building;
import com.townSimulator.entity.BuildingType;
import com.townSimulator.entity.EntityFactory;
import com.townSimulator.render.Renderer;
import com.townSimulator.scene.CameraController;
import com.townSimulator.scene.QuadTreeType;
import com.townSimulator.scene.SceneManager;
import com.townSimulator.ui.UIManager;
import com.townSimulator.utility.AxisAlignedBoundingBox;
import com.townSimulator.utility.ResourceManager;
import com.townSimulator.utility.Settings;

public class GameManager implements Screen, GestureListener{
	private final 	int					MAP_WIDTH  = 512;
	private final 	int					MAP_HEIGHT = 512;
	private 		OrthographicCamera 	mCamera;
	private 		CameraController 	mCameraController;
	private 		Renderer			mRenderer;
	private 		GestureDetectorEx 	mGestureDetector;
	private 		boolean 			mbGameStart = false;
	private 		UIManager			mUIMgr = null;
	private			SceneManager		mSceneManager;
	private	static	GameManager			mInstance = null;
	
	private GameManager()
	{
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();
		
		mCamera = new OrthographicCamera();
		mCameraController = new CameraController( mCamera );
		mRenderer = Renderer.getInstance();
		mRenderer.setCamera(mCamera);
		mSceneManager = new SceneManager();
		mCamera.position.x = MAP_WIDTH  * Settings.UNIT * 0.5f;
		mCamera.position.y = MAP_HEIGHT * Settings.UNIT * 0.5f;
		
		mGestureDetector = new GestureDetectorEx(this);
	}
	
	public static synchronized GameManager getInstance()
	{
		if(mInstance == null)
			mInstance = new GameManager();
		return mInstance;
	}
	
	public InputProcessor getInputProcessor()
	{
		return mGestureDetector;
	}
	
	public void setUIMgr(UIManager mgr)
	{
		mUIMgr = mgr;
	}
	
	public void startGame()
	{
		mbGameStart = true;
		mSceneManager.initMap();
	}
	
	public void startNewBuilding(BuildingType type)
	{
		if(BuildBroker.getInstance().isIdle() == false)
			return;
		
		mSceneManager.setDrawGrid(true);
		
		Building newBuildingObject = EntityFactory.createBuilding(BuildingType.WOOD_HOUSE);
		int gridX = (int) (mCamera.position.x / Settings.UNIT);
		int gridY = (int) (mCamera.position.y / Settings.UNIT);
		int gridSerchSize = 0;
		AxisAlignedBoundingBox buildingCollisionAABB = newBuildingObject.getBoundingBox(QuadTreeType.COLLISION);
		float buildingWidth = buildingCollisionAABB.maxX - buildingCollisionAABB.minX;
		float buildingHeight = buildingCollisionAABB.maxY - buildingCollisionAABB.minY;
		boolean bPosFind = false;
		float posX = 0.0f;
		float posY = 0.0f;
		AxisAlignedBoundingBox aabb = new AxisAlignedBoundingBox();
		while(!bPosFind)
		{
			for (int x = gridX - gridSerchSize; x <= gridX + gridSerchSize && !bPosFind; x++) {
				for (int y = gridY - gridSerchSize; y <= gridY + gridSerchSize && !bPosFind; y++) {
					posX = x * Settings.UNIT;
					posY = y * Settings.UNIT;
					aabb.minX = posX;
					aabb.minY = posY;
					aabb.maxX = aabb.minX + buildingWidth;
					aabb.maxY = aabb.minY + buildingHeight;
					if( !SceneManager.getInstance().getCollisionDetector().detectIntersection(aabb) )
						bPosFind = true;
				}
			}
			gridSerchSize ++;
			
		}
		newBuildingObject.setPositionOriginCollision(
				posX, posY);
		mSceneManager.addBuilding(newBuildingObject);
		
		BuildBroker.getInstance().setBuilding(newBuildingObject);
		mUIMgr.getGameUI().trackBuildProcess();
	}
	
	public boolean isGameStart()
	{
		return mbGameStart;
	}

	@Override
	public void render(float delta) {
		if( mbGameStart == false )
			return;
		
		mRenderer.renderBegin();
		mSceneManager.renderScene(mRenderer);
		mRenderer.renderEnd();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		ResourceManager.reloadResources();
	}

	@Override
	public void dispose() {
		//mSpriteBatch.dispose();
		mRenderer.dispose();
		ResourceManager.dispose();
		System.out.println("BBBBBBBBBBBBBBB");
		mInstance = null;
	}

	
	//Gesture Listener
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Vector3 pos = new Vector3(x, y, 0.0f);
		mCamera.unproject(pos);
		mSceneManager.touchDownWorldSpace(pos.x, pos.y);
		
		return true;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		//System.out.println(deltaX + "  " + deltaY);
		mCameraController.tryMoveRelScreen(deltaX, deltaY);
		Vector3 pos = new Vector3(x, y, 0.0f);
		mCamera.unproject(pos);
		mSceneManager.touchDraggedWorldSpace(	pos.x, pos.y,
												deltaX * mCamera.viewportWidth / Gdx.graphics.getWidth(),
												-deltaY * mCamera.viewportHeight / Gdx.graphics.getHeight());
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return mCameraController.zoom(initialDistance, distance);
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
	
	class GestureDetectorEx extends GestureDetector
	{
		public GestureDetectorEx(GestureListener listener) {
			super(listener);
		}

		@Override
		public boolean touchUp(float x, float y, int pointer, int button) {
			Vector3 pos = new Vector3(x, y, 0.0f);
			mCamera.unproject(pos);
			mSceneManager.touchUpWorldSpace(pos.x, pos.y);
			return super.touchUp(x, y, pointer, button);
		}
		
	}

	
}

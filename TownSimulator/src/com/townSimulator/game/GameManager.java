package com.townSimulator.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.townSimulator.game.objs.MapObject;
import com.townSimulator.game.render.Renderer;
import com.townSimulator.game.scene.CameraController;
import com.townSimulator.game.scene.QuadTreeManageble;
import com.townSimulator.game.scene.SceneManager;
import com.townSimulator.ui.UIManager;
import com.townSimulator.utility.AxisAlignedBoundingBox;
import com.townSimulator.utility.Settings;

public class GameManager implements Screen, GestureListener{
	private final 	int					MAP_WIDTH  = 512;
	private final 	int					MAP_HEIGHT = 512;
	private 		OrthographicCamera 	mCamera;
	private 		CameraController 	mCameraController;
	private 		Renderer	mRenderMgr;
	private 		GestureDetector 	mGestureDetector;
	//private 		Map 				mMap;
	private 		boolean 			mbGameStart = false;
	private 		UIManager			mUIMgr = null;
	private			SceneManager		mSceneManager;
	
	public GameManager()
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		mCamera = new OrthographicCamera(w, h);
		mCameraController = new CameraController( mCamera );
		mRenderMgr = new Renderer(mCamera);
		//mSpriteBatch = new SpriteBatch();
		
		//int seed = 100;
		//mMap = new Map(MAP_WIDTH, MAP_HEIGHT, seed);
		mSceneManager = new SceneManager();
		mCamera.position.x = MAP_WIDTH  * Settings.UNIT * 0.5f;
		mCamera.position.y = MAP_HEIGHT * Settings.UNIT * 0.5f;
		
		mGestureDetector = new GestureDetector(this);
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
	
	public boolean isGameStart()
	{
		return mbGameStart;
	}

	@Override
	public void render(float delta) {
		if( mbGameStart == false )
			return;
		
		mRenderMgr.renderBegin();
		mSceneManager.renderScene(mRenderMgr);
		mRenderMgr.renderEnd();
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
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		//mSpriteBatch.dispose();
		mRenderMgr.dispose();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Vector3 pos = new Vector3(x, y, 0.0f);
		mCamera.unproject(pos);
		Array<QuadTreeManageble> objs = new Array<QuadTreeManageble>();
		mSceneManager.getCollisionDetector().detectIntersection(new AxisAlignedBoundingBox(pos.x-0.1f, pos.y-0.1f, pos.x+0.1f, pos.y+0.1f), objs);
		
		for (int i = 0; i < objs.size; i++) {
			((MapObject) objs.get(i)).setVisible(false);
		}
		
		//System.out.println("Screen Pos: " + x + " : " + y);
		
		return false;
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
		mCameraController.moveRelScreen(deltaX, deltaY);
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
	
}

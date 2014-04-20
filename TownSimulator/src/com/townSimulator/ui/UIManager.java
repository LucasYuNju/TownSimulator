package com.townSimulator.ui;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.townSimulator.game.GameManager;
import com.townSimulator.ui.StartScreenUI.StartUIListener;

public class UIManager implements Screen, InputProcessor, StartUIListener{
	private StartScreenUI 	mStartUI;
	private GameScreenUI	mGameUI;
	private ScreenUIBase	mCurScreenUI;
	private InputProcessor 	mCurInputProcessor;
	private GameManager		mGameMgr = null;
	
	public UIManager()
	{
		mStartUI = new StartScreenUI();
		mGameUI = new GameScreenUI();
		mCurScreenUI = mStartUI;
		mCurInputProcessor = mStartUI.getInputProcessor();
		mStartUI.setListner(this);
	}
	
	public InputProcessor getInputProcessor()
	{
		return this;
	}
	
	public void setGameMgr(GameManager mgr)
	{
		mGameMgr = mgr;
	}
	
	@Override
	public void startGame() {
		if( mGameMgr != null )
		{
			mGameMgr.startGame();
			mCurScreenUI = mGameUI;
			mCurInputProcessor = mGameUI.getInputProcessor();
		}
	}

	@Override
	public void render(float delta) {
//		Label fpsLabel = (Label) stage.getRoot().findActor("fps_label");
//		fpsLabel.setText("FPS " + Gdx.graphics.getFramesPerSecond());
//		//fpsLabel.setX(Gdx.graphics.getWidth() - fpsLabel.getTextBounds().width);
//		
//		stage.act(delta);
//		stage.draw();
//		if (!mGameMgr.isGameStart()) {
//			mStartUI.update(delta);
//			mStartUI.draw();
//		}
		if( mCurScreenUI != null)
		{
			mCurScreenUI.update(delta);
			mCurScreenUI.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		//stage.setViewport(width, height, true);
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
		mStartUI.dispose();
		mGameUI.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return mCurInputProcessor.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return mCurInputProcessor.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return mCurInputProcessor.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


}

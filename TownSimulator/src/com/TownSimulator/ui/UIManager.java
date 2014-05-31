package com.TownSimulator.ui;


import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.io.InputMgr;
import com.TownSimulator.io.InputMgrListenerBaseImpl;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.screen.GameScreen;
import com.TownSimulator.ui.screen.LoadingScreenUI;
import com.TownSimulator.ui.screen.LoadingScreenUI.LoadingUIListener;
import com.TownSimulator.ui.screen.StartScreen;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.VoicePlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class UIManager extends Singleton {
	private StartScreen 	mStartUI;
	private LoadingScreenUI mLoadingUI;
	private GameScreen	mGameUI;
	private ScreenUIBase	mCurScreenUI;
	
	private UIManager()
	{
		mStartUI = new StartScreen();
		mLoadingUI = new LoadingScreenUI();
		mLoadingUI.setListener(new LoadingUIListener() {
			
			@Override
			public void loadingFinish() {
				mCurScreenUI = mGameUI;
				Driver.getInstance(Driver.class).ignoreNextUpdate();
			}
		});
		
		mGameUI = new GameScreen();
		
		mCurScreenUI = mStartUI;
		//Settings.backgroundColor = Color.BLACK.cpy();
		Settings.backgroundColor=Settings.gameNormalGroundColor.cpy();
		VoicePlayer.getInstance(VoicePlayer.class).playBgmMusic("start.mp3");
		VoicePlayer.getInstance(VoicePlayer.class).playMusicForDuringTime("rain.mp3", 60.0f);
		
		listenToDriver();
		
		InputMgr.getInstance(InputMgr.class).addListener(new InputMgrListenerBaseImpl()
		{

			@Override
			public boolean touchDown(float screenX, float screenY, int pointer,
					int button) {
				boolean bProcessed = mCurScreenUI.touchDown(screenX, screenY, pointer, button);
				if(bProcessed)
					InputMgr.getInstance(InputMgr.class).cancelTouchDown();
				return bProcessed;
			}

			@Override
			public boolean touchUp(float screenX, float screenY, int pointer,
					int button) {
				return mCurScreenUI.touchUp(screenX, screenY, pointer, button);
			}

			@Override
			public void touchDragged(float screenX, float screenY,
					float deltaX, float deltaY, int pointer) {
				mCurScreenUI.touchDragged(screenX, screenY, deltaX, deltaY, pointer);
			}
		});
	}
	
	public GameScreen getGameUI()
	{
		return mGameUI;
	}
	
	public StartScreen getStartUI()
	{
		return mStartUI;
	}

	public void startGame() 
	{
		mLoadingUI.startLoading();
		mCurScreenUI = mLoadingUI;
//		VoicePlayer.getInstance(VoicePlayer.class).playMusic("game.mp3");
	}

	public void render()
	{
		mCurScreenUI.render();
	}
	
	public void finishLoading() {
		mCurScreenUI = mGameUI;
	}

	public void listenToDriver() {
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
		{
			private static final long serialVersionUID = -3068757753326076648L;
			@Override
			public void update(float deltaTime) {
				mCurScreenUI.update(Gdx.graphics.getDeltaTime());
			}

			@Override
			public void dispose() {
				mStartUI.dispose();
				mGameUI.dispose();
				Singleton.clearInstanceMap();
			}
		});
	}
}

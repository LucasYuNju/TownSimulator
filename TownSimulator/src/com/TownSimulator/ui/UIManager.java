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
import com.TownSimulator.ui.screen.introduction.IntroductionUI;
import com.TownSimulator.ui.screen.introduction.IntroductionUI.IntroductionListener;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.VoicePlayer;
import com.badlogic.gdx.Gdx;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class UIManager extends Singleton {
	private StartScreen 	mStartUI;
	private LoadingScreenUI mLoadingUI;
	private GameScreen	mGameUI;
	private ScreenUIBase	mCurScreenUI;
	private IntroductionUI mIntroductionUI;
	
	private UIManager()
	{
		mStartUI = new StartScreen();
		

		mIntroductionUI=new IntroductionUI();
		mIntroductionUI.setListener(new IntroductionListener() {
			
			@Override
			public void introductionFinish() {
				// TODO Auto-generated method stub
				mLoadingUI.startLoading();
				mCurScreenUI = mLoadingUI;
			}
		});
		
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
		Settings.backgroundColor = Settings.gameNormalGroundColor.cpy();
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
		mCurScreenUI=mIntroductionUI;
		mIntroductionUI.startShow();
	}
	
	public void returnToStartUI()
	{
		mCurScreenUI = mStartUI;
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
				mIntroductionUI.dispose();
				mGameUI.dispose();
				Singleton.clearInstanceMap();
			}
		});
	}
}

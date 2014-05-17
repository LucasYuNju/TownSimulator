package com.TownSimulator.ui;


import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.io.InputMgr;
import com.TownSimulator.io.InputMgrListenerBaseImpl;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.screen.GameScreenUI;
import com.TownSimulator.ui.screen.LoadingScreenUI;
import com.TownSimulator.ui.screen.LoadingScreenUI.LoadingUIListener;
import com.TownSimulator.ui.screen.StartScreenUI;
import com.TownSimulator.ui.screen.StartScreenUI.StartUIListener;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.VoicePlayer;
import com.badlogic.gdx.Gdx;


public class UIManager extends Singleton implements StartUIListener{
	private StartScreenUI 	mStartUI;
	private LoadingScreenUI mLoadingUI;
	private GameScreenUI	mGameUI;
	private ScreenUIBase	mCurScreenUI;
	
	private UIManager()
	{
		mStartUI = new StartScreenUI();
		mStartUI.setListner(this);
		
		mLoadingUI = new LoadingScreenUI();
		mLoadingUI.setListener(new LoadingUIListener() {
			
			@Override
			public void loadingFinish() {
				mCurScreenUI = mGameUI;
			}
		});
		
		mGameUI = new GameScreenUI();
		
		mCurScreenUI = mStartUI;
		VoicePlayer.getInstance(VoicePlayer.class).playBgmMusic("start.mp3");
		VoicePlayer.getInstance(VoicePlayer.class).playMusicForDuringTime("rain.mp3", 60.0f);
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
		{
			@Override
			public void update(float deltaTime) {
				mCurScreenUI.update(Gdx.graphics.getDeltaTime());
			}

			@Override
			public void dispose() {
				mStartUI.dispose();
				mGameUI.dispose();
				mInstaceMap.clear();
			}
			
		});
		
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
	
	public GameScreenUI getGameUI()
	{
		return mGameUI;
	}
	
	public StartScreenUI getStartUI()
	{
		return mStartUI;
	}

	@Override
	public void startGame() 
	{
		mLoadingUI.startLoading();
		mCurScreenUI = mLoadingUI;
//		mCurScreenUI = mGameUI;
//		Map.getInstance(Map.class).init(100);
//		Renderer.getInstance(Renderer.class).setRenderScene(true);
//		Driver.getInstance(Driver.class).init();
//		World.getInstance(World.class).init();
		
		//VoicePlayer.getInstance(VoicePlayer.class).playMusic("game.mp3");


	}

	public void render()
	{
		mCurScreenUI.render();
	}

}

package com.TownSimulator.ui.screen;


import com.TownSimulator.entity.World;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.ls.LoadSave;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

public class StartScreen extends ScreenUIBase {
	private static final long serialVersionUID = -7228539445855533616L;
	private float			mButtonWidth 	= Gdx.graphics.getWidth() / 8.0f;
	private float			mButtonHeight 	= Gdx.graphics.getHeight() / 10.0f;
	private float			mVerticalPad 	= mButtonHeight * 0.2f;
	private Array<FlipButton> mButtonList;
	private FlipButton		mStartButton;
	private FlipButton		mLoadButton;
	private FlipButton		mQuitButton;
	
	public StartScreen()
	{
		mButtonList = new Array<FlipButton>();
		initComponents();
	}
	
	private void initComponents()
	{
		mStartButton = new FlipButton("button_up", "button_down", ResourceManager.stringMap.get("startUI_start"));
		mStartButton.setSize(mButtonWidth, mButtonHeight );
		mStartButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
					Singleton.getInstance(UIManager.class).startGame();
			}
		});
		mButtonList.add(mStartButton);
		
		mLoadButton = new FlipButton("button_up", "button_down", ResourceManager.stringMap.get("startUI_load"));
		mLoadButton.setSize(mButtonWidth, mButtonHeight );	
		mButtonList.add(mLoadButton);
		mLoadButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				boolean isSucessful = LoadSave.getInstance().load();
				if(isSucessful) {
					World.getInstance(World.class).init();
					Renderer.getInstance(Renderer.class).setRenderScene(true);
					World.getInstance(World.class).setGroundColor();
					Singleton.getInstance(UIManager.class).finishLoading();
//					Singleton.getInstance(UIManager.class).listenToDriver();
				}
			}
		});
		
		mQuitButton = new FlipButton("button_up", "button_down", ResourceManager.stringMap.get("startUI_quit"));
		mQuitButton.setSize(mButtonWidth, mButtonHeight );
		mQuitButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
			}
		});
		mButtonList.add(mQuitButton);
		
		updateLayout();
		for (int i = 0; i < mButtonList.size; i++) {
			mStage.addActor(mButtonList.get(i));
		}
	}
	
	private void updateLayout()
	{
		float centerX = Gdx.graphics.getWidth() * 0.5f;
		float centerY = Gdx.graphics.getHeight() * 0.5f;
		float posX = centerX - mButtonWidth * 0.5f;
		float posY = centerY + (mButtonList.size * (mButtonHeight+mVerticalPad) - mVerticalPad) * 0.5f - mButtonHeight;
		float dy = mButtonHeight + mVerticalPad;
		for (int i = 0; i < mButtonList.size; i++) {
			mButtonList.get(i).setPosition(posX, posY);
			posY -= dy;
		}
	}
}

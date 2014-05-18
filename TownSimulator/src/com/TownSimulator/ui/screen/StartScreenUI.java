package com.TownSimulator.ui.screen;


import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

public class StartScreenUI extends ScreenUIBase {
	private float			mButtonWidth 	= Gdx.graphics.getWidth() / 8.0f;
	private float			mButtonHeight 	= Gdx.graphics.getHeight() / 10.0f;
	private float			mVerticalPad 	= mButtonHeight * 0.2f;
	private Array<FlipButton> mButtonList;
	private FlipButton		mStartButton;
	private FlipButton		mOptionButton;
	private FlipButton		mScoreButton;
	private FlipButton		mQuitButton;
	
	public StartScreenUI()
	{
		mButtonList = new Array<FlipButton>();
		initComponents();
	}
	
	private void initComponents()
	{
		mStartButton = new FlipButton("button_up", "button_down", "Start");
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
		
		mOptionButton = new FlipButton("button_up", "button_down", "Option");
		mOptionButton.setSize(mButtonWidth, mButtonHeight );	
		mButtonList.add(mOptionButton);
		
		mScoreButton = new FlipButton("button_up", "button_down", "Score");
		mScoreButton.setSize(mButtonWidth, mButtonHeight );	
		mButtonList.add(mScoreButton);
		
		mQuitButton = new FlipButton("button_up", "button_down", "Quit");
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

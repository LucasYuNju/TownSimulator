package com.townSimulator.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.townSimulator.utility.ResourceManager;

public class StartScreenUI extends ScreenUIBase {
	private float			mButtonWidth 	= Gdx.graphics.getWidth() / 8.0f;
	private float			mButtonHeight 	= Gdx.graphics.getHeight() / 10.0f;
	private float			mVerticalPad 	= mButtonHeight * 0.2f;
	private Array<UIButton> mButtonList;
	private UIButton		mStartButton;
	private UIButton		mOptionButton;
	private UIButton		mScoreButton;
	private UIButton		mQuitButton;
	private StartUIListener mListner;
	
	interface StartUIListener
	{
		public void startGame();
	}
	
	public StartScreenUI()
	{
		initComponents();
	}
	
	private void initComponents()
	{
		mButtonList = new Array<UIButton>();
		
		TextureRegionDrawable imgUp = new TextureRegionDrawable(ResourceManager.findTextureRegion("button_up"));
		TextureRegionDrawable imgDown = new TextureRegionDrawable(ResourceManager.findTextureRegion("button_down"));
		
		mStartButton = new UIButton(imgUp, imgDown, "Start");
		mStartButton.setSize(mButtonWidth, mButtonHeight );
		mStartButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
//				if(mListner != null)
//				{
//					mListner.startGame();
//					return true;
//				}
//				return false;
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(mListner != null)
				{
					mListner.startGame();
				}
			}
			
		});
		mButtonList.add(mStartButton);
		
		mOptionButton = new UIButton(imgUp, imgDown, "Option");
		mOptionButton.setSize(mButtonWidth, mButtonHeight );	
		mButtonList.add(mOptionButton);
		
		mScoreButton = new UIButton(imgUp, imgDown, "Score");
		mScoreButton.setSize(mButtonWidth, mButtonHeight );	
		mButtonList.add(mScoreButton);
		
		mQuitButton = new UIButton(imgUp, imgDown, "Quit");
		mQuitButton.setSize(mButtonWidth, mButtonHeight );	
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
	
	public void setListner(StartUIListener l)
	{
		mListner = l;
	}
	
}

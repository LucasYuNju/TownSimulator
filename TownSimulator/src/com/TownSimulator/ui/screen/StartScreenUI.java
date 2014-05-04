package com.TownSimulator.ui.screen;


import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.ui.base.ScreenUIBase;
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
	private StartUIListener mListner;
	
	public interface StartUIListener
	{
		public void startGame();
	}
	
	public StartScreenUI()
	{
		initComponents();
	}
	
	private void initComponents()
	{
		mButtonList = new Array<FlipButton>();
		
		//TextureRegionDrawable imgUp = new TextureRegionDrawable(ResourceManager.getInstance(ResourceManager.class).findTextureRegion("button_up"));
		//TextureRegionDrawable imgDown = new TextureRegionDrawable(ResourceManager.getInstance(ResourceManager.class).findTextureRegion("button_down"));
		
		mStartButton = new FlipButton("button_up", "button_down", "Start");
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
		
		mOptionButton = new FlipButton("button_up", "button_down", "Option");
		mOptionButton.setSize(mButtonWidth, mButtonHeight );	
		mButtonList.add(mOptionButton);
		
		mScoreButton = new FlipButton("button_up", "button_down", "Score");
		mScoreButton.setSize(mButtonWidth, mButtonHeight );	
		mButtonList.add(mScoreButton);
		
		mQuitButton = new FlipButton("button_up", "button_down", "Quit");
		mQuitButton.setSize(mButtonWidth, mButtonHeight );	
		mButtonList.add(mQuitButton);
		
		updateLayout();
		for (int i = 0; i < mButtonList.size; i++) {
			mStage.addActor(mButtonList.get(i));
		}
	}
	
	
	
//	@Override
//	public void show() {
//		super.show();
//		initComponents();
//	}

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

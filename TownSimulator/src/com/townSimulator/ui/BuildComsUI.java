package com.townSimulator.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.townSimulator.utility.GameMath;
import com.townSimulator.utility.Settings;

public class BuildComsUI extends Group {
	public 	static 	float							BUTTON_WIDTH  			= Settings.UNIT;
	public 	static 	float							BUTTON_HEIGHT 			= Settings.UNIT;
	public 	static 	float							BUTTON_TOP_LABEL_PAD 	= Settings.UNIT * 0.2f;
	public 	static 	float							BUTTONS_H_PAD 			= BUTTON_WIDTH * 0.1f;
	private 		Array<BuildComsCategoryButton> 	mBuildButtonsList;
	private 		BuildComsCategoryButton			mInitButton;
	
	enum State
	{
		FOLD, UNFLODING, UNFLOD, FOLDING
	}
	private 		State							mCurState;
	private 		float							mActionSpeed = 2.0f;
	private 		float							mLerpParam = 0.0f;
	
	public BuildComsUI()
	{
		mBuildButtonsList = new Array<BuildComsCategoryButton>();
		mCurState = State.FOLD;
		initComponents();
	}
	
	private void initComponents()
	{
		mInitButton = new BuildComsCategoryButton("button_build", null);
		mInitButton.setVisible(true);
		addActor(mInitButton);
		
		BuildComsCategoryButton buildHouse = new BuildComsCategoryButton("button_build_house", "House");
		buildHouse.addBuild("button_build_house", "House0");
		buildHouse.addBuild("button_build_house", "House2");
		//buildHouse.updateLayout();
		buildHouse.setVisible(false);
		mBuildButtonsList.add(buildHouse);
		
		BuildComsCategoryButton buildFood = new BuildComsCategoryButton("button_build_food", "Food");
		buildFood.addBuild("button_build_food", "Food0");
		buildFood.addBuild("button_build_food", "Food2");
		//buildFood.updateLayout();
		buildFood.setVisible(false);
		mBuildButtonsList.add(buildFood);
		
		for (int i = 0; i < mBuildButtonsList.size; i++) {
			addActor(mBuildButtonsList.get(i));
		}
		
		setSize((mBuildButtonsList.size + 1) * BUTTON_WIDTH + mBuildButtonsList.size * BUTTONS_H_PAD, BUTTON_HEIGHT + BUTTON_TOP_LABEL_PAD);
		mInitButton.setPosition(getWidth() - mInitButton.getWidth(), 0.0f);
		
		mInitButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(mCurState == State.FOLD)
				{
					mCurState = State.UNFLODING;
					for (int i = 0; i < mBuildButtonsList.size; i++) {
						mBuildButtonsList.get(i).setVisible(true);
					}
				}
				else if(mCurState == State.UNFLOD)
					mCurState = State.FOLDING;
			}
		});
		
	}
	

	@Override
	public void act(float delta) {
		super.act(delta);
		if( mCurState == State.FOLDING)
		{
			mLerpParam -= delta * mActionSpeed;
			if(mLerpParam < 0.0f)
			{
				mLerpParam = 0.0f;
				mCurState = State.FOLD;
			}
			updateButtons();
		}
		else if( mCurState == State.UNFLODING)
		{
			mLerpParam += delta * mActionSpeed;
			if(mLerpParam > 1.0f)
			{
				mLerpParam = 1.0f;
				mCurState = State.UNFLOD;
			}
			updateButtons();
		}
	}
	
	private void updateButtons()
	{
		float startPosX = getWidth() - BUTTON_WIDTH;
		float finalPosX = 0.0f;
		float lerpX = GameMath.lerp(startPosX, finalPosX, mLerpParam);
		mInitButton.setPosition(lerpX, 0.0f);
		
		for (int i = 0; i < mBuildButtonsList.size; i++) {
			startPosX = getWidth() - BUTTON_WIDTH;
			finalPosX = (i + 1) * (BUTTON_WIDTH + BUTTONS_H_PAD);
			lerpX = GameMath.lerp(startPosX, finalPosX, mLerpParam);
			mBuildButtonsList.get(i).setPosition(lerpX, 0.0f);
			mBuildButtonsList.get(i).setColor(1.0f, 1.0f, 1.0f, mLerpParam);
			
			if(mLerpParam == 0.0f)
				mBuildButtonsList.get(i).setVisible(false);
		}
	}

	
}

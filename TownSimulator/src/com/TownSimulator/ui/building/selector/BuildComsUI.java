package com.TownSimulator.ui.building.selector;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

public class BuildComsUI extends Group {
	public 	static 	float							BUTTON_WIDTH  			= Settings.UNIT;
	public 	static 	float							BUTTON_HEIGHT 			= Settings.UNIT;
	public 	static 	float							BUTTON_TOP_LABEL_HEIGHT 	= Settings.UNIT * 0.2f;
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
		buildHouse.addBuild("button_build_low_cost_house", "low-cost house", BuildingType.LOW_COST_HOUSE);
		buildHouse.setVisible(false);
		mBuildButtonsList.add(buildHouse);
		
		BuildComsCategoryButton buildFood = new BuildComsCategoryButton("button_build_food", "Food");
		buildFood.addBuild("button_build_farm_house", "farm", BuildingType.FARM_HOUSE);
		buildFood.addBuild("button_build_ranch", "ranch", BuildingType.RANCH);
		buildFood.setVisible(false);
		mBuildButtonsList.add(buildFood);
		
		BuildComsCategoryButton buildInfrastruction = new BuildComsCategoryButton("button_build_infrastructure", "Infras- tructure");
		buildInfrastruction.addBuild("button_build_house", "warehouse", BuildingType.WAREHOUSE);
		buildInfrastruction.addBuild("button_build_power_station", "power station", BuildingType.POWER_STATION);
		buildInfrastruction.addBuild("button_build_coat_factory", "coat factory", BuildingType.COAT_FACTORY);
		buildInfrastruction.setVisible(false);
		mBuildButtonsList.add(buildInfrastruction);
		
		for (int i = 0; i < mBuildButtonsList.size; i++) {
			mBuildButtonsList.get(i).setColor(1.0f, 1.0f, 1.0f, 0.0f);
			addActor(mBuildButtonsList.get(i));
		}
		
		setSize((mBuildButtonsList.size + 1) * BUTTON_WIDTH + mBuildButtonsList.size * BUTTONS_H_PAD, BUTTON_HEIGHT + BUTTON_TOP_LABEL_HEIGHT);
		mInitButton.setPosition(getWidth() - mInitButton.getWidth(), 0.0f);
		
		mInitButton.addListener(new InputListener()
		{
			private float touchDownX = 0.0f;
			private float touchDownY = 0.0f;
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				touchDownX = x;
				touchDownY = y;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if( touchDownX != x || touchDownY != y)
					return;
				
				if(mCurState == State.FOLD)
				{
					mCurState = State.UNFLODING;
					float startPosX = BuildComsUI.this.getWidth() - BUTTON_WIDTH;
					for (int i = 0; i < mBuildButtonsList.size; i++) {
						mBuildButtonsList.get(i).setVisible(true);
						mBuildButtonsList.get(i).setPosition(startPosX, 0.0f);
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

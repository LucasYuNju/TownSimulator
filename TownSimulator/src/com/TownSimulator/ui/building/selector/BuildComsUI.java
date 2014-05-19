package com.TownSimulator.ui.building.selector;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.building.selector.BuildComsCategoryButton.BuildComsCategoryButtonListener;
import com.TownSimulator.ui.screen.GameScreenUI;
import com.TownSimulator.utility.GameMath;
//github.com/LuciusYu/TownSimulator.git
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildComsUI extends Group {
	public 	static 	float							BUTTON_WIDTH  			= GameScreenUI.BUTTON_WIDTH;
	public 	static 	float							BUTTON_HEIGHT 			= GameScreenUI.BUTTON_HEIGHT;
	public 	static 	float							BUTTON_TOP_MARGIN 		= GameScreenUI.BUTTON_LABEL_HEIGHT;
	public 	static 	float							BUTTONS_H_MARGIN 		= GameScreenUI.BUTTONS_H_MARGIN;
	private 		List<BuildComsCategoryButton> 					mBuildButtonsList;
	private 		BuildComsCategoryButton			mInitButton;
	private			BuildComsUIListener listener;
	
	enum State
	{
		FOLD, UNFLODING, UNFLOD, FOLDING
	}
	private 		State							mCurState;
	private 		float							mActionSpeed = 2.0f;
	private 		float							mLerpParam = 0.0f;
	
	public interface BuildComsUIListener
	{
		public void clicked();
	}
	
	public BuildComsUI()
	{
		mBuildButtonsList = new ArrayList<BuildComsCategoryButton>();
		mCurState = State.FOLD;
		initComponents();
	}
	
	private void initComponents()
	{
		BuildComsCategoryButtonListener catgogryListner = new BuildComsCategoryButtonListener() {
			
			@Override
			public void clicked() {
				listener.clicked();
			}
		};
		
		mInitButton = new BuildComsCategoryButton("button_build", null);
		mInitButton.setVisible(true);
		mInitButton.setListener(catgogryListner);
		addActor(mInitButton);
		
		BuildComsCategoryButton buildHouse = new BuildComsCategoryButton("button_build_house", "House");
		buildHouse.addBuild("button_build_low_cost_house", "low-cost house", BuildingType.LOW_COST_HOUSE);
		buildHouse.addBuild("button_build_apartment_house", "apartment", BuildingType.APARTMENT);
		buildHouse.setVisible(false);
		mBuildButtonsList.add(buildHouse);
		
		BuildComsCategoryButton buildFood = new BuildComsCategoryButton("button_build_food", "Food");
		buildFood.addBuild("button_build_farm_house", "farm", BuildingType.FARM_HOUSE);
		buildFood.addBuild("button_build_ranch", "ranch", BuildingType.RANCH);
		buildFood.setVisible(false);
		mBuildButtonsList.add(buildFood);
		
		BuildComsCategoryButton buildInfrastruction = new BuildComsCategoryButton("button_build_infrastructure", "Infras- tructure");
		buildInfrastruction.addBuild("button_build_warehouse", "warehouse", BuildingType.WAREHOUSE);
		buildInfrastruction.addBuild("button_build_felling_house", "felling", BuildingType.FELLING_HOUSE);
		buildInfrastruction.addBuild("button_build_hospital", "hospital", BuildingType.Hospital);
		buildInfrastruction.addBuild("button_build_bar", "bar", BuildingType.Bar);
		buildInfrastruction.addBuild("button_build_power_station", "power station", BuildingType.POWER_STATION);
		buildInfrastruction.addBuild("button_build_coat_factory", "coat factory", BuildingType.COAT_FACTORY);
		buildInfrastruction.setVisible(false);
		mBuildButtonsList.add(buildInfrastruction);
		
//		BuildComsCategoryButton saveButton = new SaveButton();
//		saveButton.setVisible(false);
//		mBuildButtonsList.add(saveButton);
		
		for (int i = 0; i < mBuildButtonsList.size(); i++) {
			mBuildButtonsList.get(i).setColor(1.0f, 1.0f, 1.0f, 0.0f);
			addActor(mBuildButtonsList.get(i));
			mBuildButtonsList.get(i).setListener(catgogryListner);
		}
		
		setSize((mBuildButtonsList.size() + 1) * BUTTON_WIDTH + mBuildButtonsList.size() * BUTTONS_H_MARGIN, BUTTON_HEIGHT + BUTTON_TOP_MARGIN);
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
					for (int i = 0; i < mBuildButtonsList.size(); i++) {
						mBuildButtonsList.get(i).setVisible(true);
						mBuildButtonsList.get(i).setPosition(startPosX, 0.0f);
					}
				}
				else if(mCurState == State.UNFLOD)
					mCurState = State.FOLDING;
			}
		});
		
	}
	
	public void setListener(BuildComsUIListener l)
	{
		this.listener = l;
	}
	
	public void hideBuildButtonsGroup()
	{
		BuildComsCategoryButton.hideBuildButtonsGroup();
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
		
		for (int i = 0; i < mBuildButtonsList.size(); i++) {
			startPosX = getWidth() - BUTTON_WIDTH;
			finalPosX = (i + 1) * (BUTTON_WIDTH + BUTTONS_H_MARGIN);
			lerpX = GameMath.lerp(startPosX, finalPosX, mLerpParam);
			mBuildButtonsList.get(i).setPosition(lerpX, 0.0f);
			mBuildButtonsList.get(i).setColor(1.0f, 1.0f, 1.0f, mLerpParam);
			
			if(mLerpParam == 0.0f)
				mBuildButtonsList.get(i).setVisible(false);
		}
	}

	
}

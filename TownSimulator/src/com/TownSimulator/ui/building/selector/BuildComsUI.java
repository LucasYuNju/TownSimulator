package com.TownSimulator.ui.building.selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.building.selector.BuildComsCategoryButton.BuildComsCategoryButtonListener;
import com.TownSimulator.ui.screen.GameScreen;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.ResourceManager;
//github.com/LuciusYu/TownSimulator.git
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildComsUI extends Group {
	public 	static 	float							BUTTON_WIDTH  			= GameScreen.BUTTON_WIDTH;
	public 	static 	float							BUTTON_HEIGHT 			= GameScreen.BUTTON_HEIGHT;
	public 	static 	float							BUTTON_LABEL_HEIGHT 	= GameScreen.BUTTON_LABEL_HEIGHT;
	public 	static 	float							BUTTONS_H_MARGIN 		= GameScreen.BUTTONS_H_MARGIN;
	private 		List<BuildComsCategoryButton> 					mBuildButtonsList;
	private 		BuildComsCategoryButton			mInitButton;
	private			BuildComsUIListener listener;
	public static 	HashMap<BuildingType, String> buildingIconMap = new HashMap<BuildingType, String>();
	static
	{
		buildingIconMap.put(BuildingType.LowCostHouse, "button_build_low_cost_house");
		buildingIconMap.put(BuildingType.Apartment, "button_build_apartment_house");
		
		buildingIconMap.put(BuildingType.FarmHouse, "button_build_farm_house");
		buildingIconMap.put(BuildingType.Ranch, "button_build_ranch");
		
		buildingIconMap.put(BuildingType.Warehouse, "button_build_warehouse");
		buildingIconMap.put(BuildingType.FellingHouse, "button_build_felling_house");
		buildingIconMap.put(BuildingType.Hospital, "button_build_hospital");
		buildingIconMap.put(BuildingType.Bar, "button_build_bar");
		buildingIconMap.put(BuildingType.CoatFactory, "button_build_coat_factory");
		buildingIconMap.put(BuildingType.School, "button_build_school");
		buildingIconMap.put(BuildingType.ConstrctionCompany, "button_build_construction_company");
		
		buildingIconMap.put(BuildingType.MP_Potato, "button_build_mp_potato");
		buildingIconMap.put(BuildingType.MP_MouseWheel, "button_build_mp_mouse_wheel");
		buildingIconMap.put(BuildingType.MP_Factory, "button_build_mp_factory");
		buildingIconMap.put(BuildingType.MP_Storm, "button_build_mp_storm");
		buildingIconMap.put(BuildingType.MP_Vocalno, "button_build_mp_vocalno");
		buildingIconMap.put(BuildingType.MP_BalckHole, "button_build_mp_black_hole");
	}
	
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
		
		BuildComsCategoryButton buildHouse = new BuildComsCategoryButton("button_build_house", ResourceManager.stringMap.get("buildSelect_livingHouse"));
		buildHouse.addBuild(buildingIconMap.get(BuildingType.LowCostHouse), BuildingType.LowCostHouse);
		buildHouse.addBuild(buildingIconMap.get(BuildingType.Apartment), BuildingType.Apartment);
		buildHouse.setVisible(false);
		mBuildButtonsList.add(buildHouse);
		
		BuildComsCategoryButton buildFood = new BuildComsCategoryButton("button_build_food", ResourceManager.stringMap.get("buildSelect_food"));
		buildFood.addBuild(buildingIconMap.get(BuildingType.FarmHouse), BuildingType.FarmHouse);
		buildFood.addBuild(buildingIconMap.get(BuildingType.Ranch), BuildingType.Ranch);
		buildFood.setVisible(false);
		mBuildButtonsList.add(buildFood);
		
		BuildComsCategoryButton buildInfrastruction = new BuildComsCategoryButton("button_build_infrastructure", ResourceManager.stringMap.get("buildSelect_infrastructure"));
		buildInfrastruction.addBuild(buildingIconMap.get(BuildingType.Warehouse), BuildingType.Warehouse);
		buildInfrastruction.addBuild(buildingIconMap.get(BuildingType.FellingHouse), BuildingType.FellingHouse);
		buildInfrastruction.addBuild(buildingIconMap.get(BuildingType.Hospital), BuildingType.Hospital);
		buildInfrastruction.addBuild(buildingIconMap.get(BuildingType.Bar), BuildingType.Bar);
		buildInfrastruction.addBuild(buildingIconMap.get(BuildingType.CoatFactory), BuildingType.CoatFactory);
		buildInfrastruction.addBuild(buildingIconMap.get(BuildingType.School), BuildingType.School);
		buildInfrastruction.addBuild(buildingIconMap.get(BuildingType.ConstrctionCompany), BuildingType.ConstrctionCompany);
		buildInfrastruction.setVisible(false);
		mBuildButtonsList.add(buildInfrastruction);
		
		BuildComsCategoryButton buildMp = new BuildComsCategoryButton("button_build_mp", ResourceManager.stringMap.get("buildSelect_energy"));
		buildMp.addBuild(buildingIconMap.get(BuildingType.MP_Potato), BuildingType.MP_Potato);
		buildMp.addBuild(buildingIconMap.get(BuildingType.MP_MouseWheel), BuildingType.MP_MouseWheel);
		buildMp.addBuild(buildingIconMap.get(BuildingType.MP_Factory), BuildingType.MP_Factory);
		buildMp.addBuild(buildingIconMap.get(BuildingType.MP_Storm), BuildingType.MP_Storm);
		buildMp.addBuild(buildingIconMap.get(BuildingType.MP_Vocalno), BuildingType.MP_Vocalno);
		buildMp.addBuild(buildingIconMap.get(BuildingType.MP_BalckHole), BuildingType.MP_BalckHole);
		buildMp.setVisible(false);
		mBuildButtonsList.add(buildMp);
		
//		BuildComsCategoryButton saveButton = new SaveButton();
//		saveButton.setVisible(false);
//		mBuildButtonsList.add(saveButton);
		
		for (int i = 0; i < mBuildButtonsList.size(); i++) {
			mBuildButtonsList.get(i).setColor(1.0f, 1.0f, 1.0f, 0.0f);
			addActor(mBuildButtonsList.get(i));
			mBuildButtonsList.get(i).setListener(catgogryListner);
		}
		
		setSize((mBuildButtonsList.size() + 1) * BUTTON_WIDTH + mBuildButtonsList.size() * BUTTONS_H_MARGIN, BUTTON_HEIGHT + BUTTON_LABEL_HEIGHT);
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

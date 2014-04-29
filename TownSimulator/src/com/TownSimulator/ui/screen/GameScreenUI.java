package com.TownSimulator.ui.screen;

import java.util.HashMap;
import java.util.Map;

import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.building.BuildComsUI;
import com.TownSimulator.ui.building.BuildingAdjustGroup;
import com.TownSimulator.ui.building.construction.ConstructionResourceInfo;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class GameScreenUI extends ScreenUIBase{
	private BuildComsUI		mBuildComsUI;
	private BuildingAdjustGroup	mBuildAjustUI;
	
	public GameScreenUI()
	{
		super();
		initComponents();
	}
	
	private void initComponents()
	{
		mBuildComsUI = new BuildComsUI();
		mBuildComsUI.setPosition( Gdx.graphics.getWidth() - mBuildComsUI.getWidth(), 0.0f );
		mStage.addActor(mBuildComsUI);
		
		mBuildAjustUI = new BuildingAdjustGroup();
		mBuildAjustUI.setVisible(false);
		mStage.addActor(mBuildAjustUI);
		
		//FIXME! It shouldn't be initialized here
	}
	
	public BuildingAdjustGroup getBuildAjustUI()
	{
		return mBuildAjustUI;
	}
	
	public void createTestWindow() {
//		Window window = new Window("test", new Skin());
//		mStage.addActor(window);
	}

	public ConstructionWindow createConstructionWindow() {
		Map<ResourceType, ConstructionResourceInfo> resouceMap =
				new HashMap<ResourceType, ConstructionResourceInfo>();
		ConstructionWindow constructionWindow = new ConstructionWindow(resouceMap, 5);
		constructionWindow.setVisible(false);
		mStage.addActor(constructionWindow);
		return constructionWindow;
	}
}

package com.TownSimulator.ui.screen;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.building.BuildComsUI;
import com.TownSimulator.ui.building.BuildingAdjustGroup;
import com.TownSimulator.ui.building.construction.ConstructionResourceInfo;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.badlogic.gdx.Gdx;

public class GameScreenUI extends ScreenUIBase{
	private BuildComsUI		mBuildComsUI;
	private BuildingAdjustGroup	mBuildAjustUI;
	private List<ConstructionWindow> windows = new LinkedList<ConstructionWindow>();
	
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
	}
	
	public BuildingAdjustGroup getBuildAjustUI()
	{
		return mBuildAjustUI;
	}
	
	public void createTestWindow() {
		
	}

	public ConstructionWindow createConstructionWindow(Map<ResourceType, ConstructionResourceInfo> resouceMap, 
			int numAllowedBuilder) {
		ConstructionWindow constructionWindow = new ConstructionWindow(resouceMap, numAllowedBuilder);
		constructionWindow.setVisible(false);
		mStage.addActor(constructionWindow);
		windows.add(constructionWindow);
		for(ConstructionWindow window : windows) {
			window.setVisible(false);
		}
		return constructionWindow;
	}
}

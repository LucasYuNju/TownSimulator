package com.TownSimulator.ui.screen;

import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.build.BuildComsUI;
import com.TownSimulator.ui.build.BuildingAdjustGroup;
import com.TownSimulator.ui.build.BuildingInfoWindow;
import com.badlogic.gdx.Gdx;

public class GameScreenUI extends ScreenUIBase{
	private BuildComsUI		mBuildComsUI;
	private BuildingAdjustGroup	mBuildAjustUI;
	//FIXME!
	private BuildingInfoWindow buildingInfoWindow;
	
	public GameScreenUI()
	{
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
		
		buildingInfoWindow = new BuildingInfoWindow();
		buildingInfoWindow.setVisible(false);
		mStage.addActor(buildingInfoWindow);
	}
	
	public BuildingAdjustGroup getBuildAjustUI()
	{
		return mBuildAjustUI;
	}
	
	public BuildingInfoWindow getBuildingInfoWindow() {
		return buildingInfoWindow;
	}
}

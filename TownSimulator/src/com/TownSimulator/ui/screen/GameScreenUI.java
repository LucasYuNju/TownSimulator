package com.TownSimulator.ui.screen;

import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.build.BuildAdjustUI;
import com.TownSimulator.ui.build.BuildComsUI;
import com.badlogic.gdx.Gdx;

public class GameScreenUI extends ScreenUIBase{
	private BuildComsUI		mBuildComsUI;
	private BuildAdjustUI	mBuildAjustUI;
	
	public GameScreenUI()
	{
		initComponents();
	}
	
	private void initComponents()
	{
		mBuildComsUI = new BuildComsUI();
		mBuildComsUI.setPosition( Gdx.graphics.getWidth() - mBuildComsUI.getWidth(), 0.0f );
		mStage.addActor(mBuildComsUI);
		
		mBuildAjustUI = new BuildAdjustUI();
		mBuildAjustUI.setVisible(false);
		mStage.addActor(mBuildAjustUI);
	}
	
	public BuildAdjustUI getBuildAjustUI()
	{
		return mBuildAjustUI;
	}
}

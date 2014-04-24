package com.townSimulator.ui;

import com.badlogic.gdx.Gdx;
import com.townSimulator.game.logic.BuildHelper;

public class GameScreenUI extends ScreenUIBase{
	private BuildComsUI		mBuildComsUI;
	private BuildAdjustUI	mBuildAjustUI;
	//private 
	
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
	
	public void trackBuildProcess()
	{
		BuildHelper.getInstance().setBuildAjustUI(mBuildAjustUI);
	}
	
}

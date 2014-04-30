package com.TownSimulator.ui.screen;

import java.util.LinkedList;
import java.util.List;

import javax.swing.InputMap;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.io.InputMgr;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.building.BuildComsUI;
import com.TownSimulator.ui.building.BuildingAdjustGroup;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.ui.building.view.ViewWindow;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

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
	
	public void testScrollPane() {
		Actor content = new ViewWindow();
		ScrollPane scrollPane = new ScrollPane(content);
		scrollPane.setSize(content.getWidth(), Settings.UNIT * 5);
		scrollPane.setPosition(0, 0);
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				InputMgr.getInstance(InputMgr.class).cancelTouchDown();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		mStage.addActor(scrollPane);
	}

	public ConstructionWindow createConstructionWindow(List<Resource> resouces, int numAllowedBuilder) {
		ConstructionWindow constructionWindow = new ConstructionWindow(resouces, numAllowedBuilder);
		constructionWindow.setVisible(false);
		mStage.addActor(constructionWindow);
		windows.add(constructionWindow);
		return constructionWindow;
	}
	
	public void hideAllWindow() {
		for(ConstructionWindow window : windows) {
			window.setVisible(false);
		}

	}
}

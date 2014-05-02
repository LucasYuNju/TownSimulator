package com.TownSimulator.ui.screen;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.building.BuildComsUI;
import com.TownSimulator.ui.building.BuildingAdjustGroup;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.ui.building.view.FarmViewWindow;
import com.TownSimulator.ui.building.view.ViewWindow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class GameScreenUI extends ScreenUIBase{
	private BuildComsUI		mBuildComsUI;
	private BuildingAdjustGroup	mBuildAjustUI;
	private List<Actor> windows = new LinkedList<Actor>();
	
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
	
	public ConstructionWindow createConstructionWindow(BuildingType buildingType, List<Resource> resouces, int numAllowedBuilder) {
		ConstructionWindow constructionWindow = new ConstructionWindow(buildingType, resouces, numAllowedBuilder);
		constructionWindow.setVisible(false);
		mStage.addActor(constructionWindow);
		windows.add(constructionWindow);
		return constructionWindow;
	}
	
	public ViewWindow createViewWindow(BuildingType type, String[][] data) {
		ViewWindow window = null;
		switch (type) {
		case WAREHOUSE:
			window = createCommonViewWindow(type, data);
			break;
		case LOW_COST_HOUSE:
			window = createCommonViewWindow(type, data);
			break;
		case FARM_HOUSE:
			window = createFarmViewWindow();
		default:
			break;
		}
		if(window != null)
			windows.add(window);
		return window;
	}
	
	private ViewWindow createCommonViewWindow(BuildingType buildingType, String[][] data) {
		ViewWindow content = new ViewWindow(buildingType, data);
		ScrollPane scrollPane = new ScrollPane(content);
		scrollPane.setSize(content.getWidth(), content.getHeight());
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//InputMgr.getInstance(InputMgr.class).cancelTouchDown();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		mStage.addActor(scrollPane);
		return content;
	}
	
	private ViewWindow createFarmViewWindow() {
		FarmViewWindow window = new FarmViewWindow();
		mStage.addActor(window);
		windows.add(window);
		return window;
	}
	
	public void hideAllWindow() {
		for(Actor window : windows) {
			window.setVisible(false);
		}
	}
}

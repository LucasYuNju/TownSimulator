package com.TownSimulator.ui.screen;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.StateBar;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.building.BuildComsUI;
import com.TownSimulator.ui.building.BuildingAdjustGroup;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.ui.building.view.FarmViewWindow;
import com.TownSimulator.ui.building.view.ViewWindow;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class GameScreenUI extends ScreenUIBase{
	private BuildComsUI		mBuildComsUI;
	private BuildingAdjustGroup	mBuildAjustUI;
	private List<Actor> windows = new LinkedList<Actor>();
	private StateBar stateBar;
	
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
		
		stateBar = new StateBar();
		stateBar.setVisible(true);
		mStage.addActor(stateBar);
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
	
	public ViewWindow createViewWindow(BuildingType type) {
		ViewWindow window = null;
		switch (type) {
		case WAREHOUSE:
			window = createScrollViewWindow(type, null);
			break;
		case LOW_COST_HOUSE:
			window = createScrollViewWindow(type, null);
			break;
		case FARM_HOUSE:
			window = createFarmViewWindow();
			break;
		case FELLING_HOUSE:
			window = createWorkableViewWindow(type, 4);
			break;
		default:
			break;
		}
		if(window != null)
			windows.add(window);
		return window;
	}
	
	private ViewWindow createScrollViewWindow(BuildingType buildingType, String[][] data) {
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
	
	private ViewWindow createWorkableViewWindow(BuildingType buildingType, int numAllowedWorker) {
		ViewWindow window = new WorkableViewWindow(buildingType, numAllowedWorker);
		mStage.addActor(window);
		windows.add(window);
		return window;
	}
	
	public void hideAllWindow() {
		for(Actor window : windows) {
			window.setVisible(false);
		}
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		int numPeople = EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan().size;
		int numFood = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getFoodAmount();
		stateBar.update(numPeople, numFood);
	}
}

package com.TownSimulator.ui.screen;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.MessageBoard;
import com.TownSimulator.ui.StateBar;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.building.adjust.BuildingAdjustGroup;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.ui.building.selector.BuildComsUI;
import com.TownSimulator.ui.building.view.BarViewWindow;
import com.TownSimulator.ui.building.view.FarmViewWindow;
import com.TownSimulator.ui.building.view.RanchViewWindow;
import com.TownSimulator.ui.building.view.ScrollViewWindow;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.TownSimulator.ui.building.view.WorkableWithTipsWindow;
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
	private MessageBoard messageBoard;
	
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
		
		messageBoard = new MessageBoard();
		mStage.addActor(messageBoard);

	}
	
	public BuildingAdjustGroup getBuildAjustUI()
	{
		return mBuildAjustUI;
	}
	
	public MessageBoard getMessageBoard()
	{
		return messageBoard;
	}
	
//	public ListenableViewWindow createViewWindow(BuildingType type) {
//		ListenableViewWindow window = null;
//		switch (type) {
//		case WAREHOUSE:
//			window = createScrollViewWindow(type);
//			break;
//		case LOW_COST_HOUSE:
//			window = createScrollViewWindow(type);
//			break;
//		case FARM_HOUSE:
//			window = createFarmViewWindow();
//			break;
//		case FELLING_HOUSE:
//			window = createWorkableViewWindow(type, FellingHouse.MAX_WORKER_CNT);
//			break;
//		default:
//			break;
//		}
//		if(window != null)
//			windows.add(window);
//		return window;
//	}
	
	public ConstructionWindow createConstructionWindow(BuildingType buildingType, List<Resource> resouces, int numAllowedBuilder) {
		ConstructionWindow constructionWindow = new ConstructionWindow(buildingType, resouces, numAllowedBuilder);
		constructionWindow.setVisible(false);
		mStage.addActor(constructionWindow);
		windows.add(constructionWindow);
		return constructionWindow;
	}
	
	public ScrollViewWindow createScrollViewWindow(BuildingType buildingType) {
		ScrollViewWindow window = new ScrollViewWindow(buildingType);
		//scrollPane不需要设置size，window设置size时会更新scrollPane的size
		ScrollPane scrollPane = new ScrollPane(window);
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
		return window;
	}
	
	public FarmViewWindow createFarmViewWindow(int numAllowedWorker) {
		FarmViewWindow window = new FarmViewWindow(numAllowedWorker);
		mStage.addActor(window);
		windows.add(window);
		return window;
	}
	
	public RanchViewWindow createRanchViewWindow(int numAllowedWorker) {
		RanchViewWindow window = new RanchViewWindow(numAllowedWorker);
		mStage.addActor(window);
		windows.add(window);
		return window;
	}
	
	public WorkableViewWindow createWorkableViewWindow(BuildingType buildingType, int numAllowedWorker) {
		WorkableViewWindow window = new WorkableViewWindow(buildingType, numAllowedWorker);
		mStage.addActor(window);
		windows.add(window);
		return window;
	}
	
	public WorkableWithTipsWindow createWorkableWithTipsWindow(BuildingType buildingType, int numAllowedWorker) {
		WorkableWithTipsWindow window = new WorkableWithTipsWindow(buildingType, numAllowedWorker);
		mStage.addActor(window);
		windows.add(window);
		return window;
	}
	
	public BarViewWindow createBarViewWindow(int numAllowedWorker, int maxWineStorage) {
		BarViewWindow barViewWindow = new BarViewWindow(numAllowedWorker, maxWineStorage);
		mStage.addActor(barViewWindow);
		windows.add(barViewWindow);
		return barViewWindow;
	}
	
	public void hideAllWindow() {
		for(Actor window : windows) {
			window.setVisible(false);
		}
	}
	
	@Override
	public void update(float deltaTime) {
//		super.update(deltaTime);
//		int numPeople = EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan().size;
//		int numFood = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getFoodAmount();
//		stateBar.update(numPeople, numFood);
		super.update(deltaTime);
		stateBar.update();
	}
}

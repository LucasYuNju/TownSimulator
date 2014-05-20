package com.TownSimulator.ui.screen;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.MessageBoard;
import com.TownSimulator.ui.StateBar;
import com.TownSimulator.ui.base.IconButton;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.building.adjust.BuildingAdjustGroup;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.ui.building.selector.BuildComsUI;
import com.TownSimulator.ui.building.selector.BuildComsUI.BuildComsUIListener;
import com.TownSimulator.ui.building.view.BarViewWindow;
import com.TownSimulator.ui.building.view.FarmViewWindow;
import com.TownSimulator.ui.building.view.HospitalViewWindow;
import com.TownSimulator.ui.building.view.RanchViewWindow;
import com.TownSimulator.ui.building.view.SchoolViewWindow;
import com.TownSimulator.ui.building.view.ScrollViewWindow;
import com.TownSimulator.ui.building.view.WellViewWindow;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.TownSimulator.ui.building.view.WorkableWithTipsWindow;
import com.TownSimulator.ui.speed.SpeedSettingUI;
import com.TownSimulator.ui.speed.SpeedSettingUI.SpeedSettingUIListener;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.ls.LoadSave;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class GameScreen extends ScreenUIBase{
	private static final long serialVersionUID = -5791358765820581413L;
	public 	static 	float	BUTTON_WIDTH  			= Settings.UNIT;
	public 	static 	float	BUTTON_HEIGHT 			= Settings.UNIT;
	public 	static 	float	BUTTON_LABEL_HEIGHT 		= Settings.UNIT * 0.2f;
	public 	static 	float	BUTTONS_H_MARGIN 			= BUTTON_WIDTH * 0.1f;
	public 	static 	float 	SUBBUTTONS_TO_LABRL_MARGIN = Settings.UNIT * 0.1f;
	private BuildComsUI		mBuildComsUI;
	private SpeedSettingUI	mSpeedSettingUI;
	private Button			mSaveButton;
	private BuildingAdjustGroup	mBuildAjustUI;
	private List<Actor> windows = new LinkedList<Actor>();
	private StateBar stateBar;
	private MessageBoard messageBoard;
	
	public GameScreen()
	{
		super();
		initComponents();
	}
	
	private void initComponents()
	{
		float x = Gdx.graphics.getWidth() - BUTTON_WIDTH;
		float y = 0.0f;
		
		mSaveButton = new IconButton("button_save");
		mSaveButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		mSaveButton.setPosition(x, y);
		mSaveButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				/**
				 * FIX
				 */
				System.out.println("Save");
				LoadSave.getInstance().save();
				
				mSpeedSettingUI.setShowButtons(false);
				mBuildComsUI.hideBuildButtonsGroup();
			}
			
		});
		mStage.addActor(mSaveButton);
		x -= BUTTON_WIDTH + BUTTONS_H_MARGIN;
		
		mSpeedSettingUI = new SpeedSettingUI();
		mSpeedSettingUI.setPosition(x, y);
		mSpeedSettingUI.setListener(new SpeedSettingUIListener() {
			
			@Override
			public void clicked() {
				mBuildComsUI.hideBuildButtonsGroup();
			}
		});
		mStage.addActor(mSpeedSettingUI);
		
		mBuildComsUI = new BuildComsUI();
		x -= mBuildComsUI.getWidth() + BUTTONS_H_MARGIN;
		mBuildComsUI.setPosition(x, y);
		mBuildComsUI.setListener(new BuildComsUIListener() {
			
			@Override
			public void clicked() {
				mSpeedSettingUI.setShowButtons(false);
			}
		});
		mStage.addActor(mBuildComsUI);
		
		mBuildAjustUI = new BuildingAdjustGroup();
		mBuildAjustUI.setVisible(false);
		mStage.addActor(mBuildAjustUI);
		
		stateBar = new StateBar();
		stateBar.setVisible(true);
		mStage.addActor(stateBar);
		
		MessageBoard.initStatic();
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
		windows.add(window);
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
		BarViewWindow window = new BarViewWindow(numAllowedWorker, maxWineStorage);
		mStage.addActor(window);
		windows.add(window);
		return window;
	}
	
	public HospitalViewWindow createHospitalViewWindow(int numAllowedWorker) {
		HospitalViewWindow window = new HospitalViewWindow(numAllowedWorker);
		mStage.addActor(window);
		windows.add(window);
		return window;
	}
	
	public SchoolViewWindow createSchoolViewWindow(int numAllowedWorker,int currentStudentNum){
		SchoolViewWindow schoolViewWindow=new SchoolViewWindow(numAllowedWorker,currentStudentNum);
		mStage.addActor(schoolViewWindow);
		windows.add(schoolViewWindow);
		return schoolViewWindow;
	}
	
	public WellViewWindow createWellViewWindow(){
		WellViewWindow wellViewWindow=new WellViewWindow();
		mStage.addActor(wellViewWindow);
		windows.add(wellViewWindow);
		return wellViewWindow;
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

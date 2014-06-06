package com.TownSimulator.ui.screen;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.GameOverWindow;
import com.TownSimulator.ui.MessageBoard;
import com.TownSimulator.ui.StateBoard;
import com.TownSimulator.ui.TipsWindow;
import com.TownSimulator.ui.achievement.AchievementUI;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.building.adjust.BuildingAdjustGroup;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.ui.building.selector.BuildComsUI;
import com.TownSimulator.ui.building.selector.BuildComsUI.BuildComsUIListener;
import com.TownSimulator.ui.building.view.BarViewWindow;
import com.TownSimulator.ui.building.view.FarmViewWindow;
import com.TownSimulator.ui.building.view.HospitalViewWindow;
import com.TownSimulator.ui.building.view.MPBuildingViewWindow;
import com.TownSimulator.ui.building.view.RanchViewWindow;
import com.TownSimulator.ui.building.view.SchoolViewWindow;
import com.TownSimulator.ui.building.view.ScrollViewWindow;
import com.TownSimulator.ui.building.view.WellViewWindow;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.TownSimulator.ui.options.InfoWindow;
import com.TownSimulator.ui.options.OptionsUI;
import com.TownSimulator.ui.options.OptionsUI.OptionsUIListner;
import com.TownSimulator.ui.options.ReturnConfirmWindow;
import com.TownSimulator.ui.options.ShareWindow;
import com.TownSimulator.ui.speed.SpeedSettingUI;
import com.TownSimulator.ui.speed.SpeedSettingUI.SpeedSettingUIListener;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenUIBase{
	private static final long serialVersionUID = -5791358765820581413L;
	public 	static 	float	BUTTON_WIDTH  			= Settings.UNIT;
	public 	static 	float	BUTTON_HEIGHT 			= Settings.UNIT;
	public 	static 	float	BUTTON_LABEL_HEIGHT 		= Settings.UNIT * 0.2f;
	public 	static 	float	BUTTONS_H_MARGIN 			= BUTTON_WIDTH * 0.1f;
	public 	static 	float 	SUBBUTTONS_TO_LABRL_MARGIN = Settings.UNIT * 0.1f;
	private BuildComsUI		mBuildComsUI;
	private SpeedSettingUI	mSpeedSettingUI;
	private OptionsUI		mOptionUI;
//	private Button			mSaveButton;
	private BuildingAdjustGroup	mBuildAjustUI;
	private List<Actor> buildingViewWindows = new LinkedList<Actor>();
	private StateBoard stateBoard;
	private MessageBoard messageBoard;
	private AchievementUI achievementUI;
	private GameOverWindow gameOverWindow;
	private ReturnConfirmWindow returnConfirmWindow;
	private ShareWindow	shareWindow;
	private TipsWindow tipsWindow;
	private InfoWindow infoWindow;
	
	public GameScreen()
	{
		super();
		initComponents();
	}
	
	private void initComponents()
	{
		float x = Gdx.graphics.getWidth() - BUTTON_WIDTH;
		float y = 0.0f;
		
		mOptionUI = new OptionsUI();
		mOptionUI.setPosition(x, y);
		mOptionUI.setListener(new OptionsUIListner() {
			
			@Override
			public void clicked() {
				mBuildComsUI.hideBuildButtonsGroup();
				mSpeedSettingUI.setShowButtons(false);
			}
		});
		mStage.addActor(mOptionUI);
		x -= BUTTON_WIDTH + BUTTONS_H_MARGIN;
		
		mSpeedSettingUI = new SpeedSettingUI();
		mSpeedSettingUI.setPosition(x, y);
		mSpeedSettingUI.setListener(new SpeedSettingUIListener() {
			
			@Override
			public void clicked() {
				mBuildComsUI.hideBuildButtonsGroup();
				mOptionUI.setShowButtons(false);
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
				mOptionUI.setShowButtons(false);
			}
		});
		mStage.addActor(mBuildComsUI);
		
		mBuildAjustUI = new BuildingAdjustGroup();
		mBuildAjustUI.setVisible(false);
		mStage.addActor(mBuildAjustUI);
		
		stateBoard = new StateBoard();
		stateBoard.setVisible(true);
		mStage.addActor(stateBoard);
		
		MessageBoard.initStatic();
		messageBoard = new MessageBoard();
		mStage.addActor(messageBoard);
		
		achievementUI = new AchievementUI();
		mStage.addActor(achievementUI);
		
		gameOverWindow = new GameOverWindow();
		mStage.addActor(gameOverWindow);
		
		returnConfirmWindow = new ReturnConfirmWindow();
		mStage.addActor(returnConfirmWindow);
		
		shareWindow = new ShareWindow();
		mStage.addActor(shareWindow);
		
		tipsWindow = new TipsWindow();
		mStage.addActor(tipsWindow);
		
		infoWindow = new InfoWindow();
		mStage.addActor(infoWindow);
	}
	
	public void showGameOverWindow()
	{
		gameOverWindow.updateValues();
		gameOverWindow.setVisible(true);
	}
	
	public void showInfoWindow()
	{
		infoWindow.setVisible(true);
	}
	
	public void showReturnConfirmWindow()
	{
		returnConfirmWindow.setVisible(true);
	}
	
	public void showShareWindow()
	{
		shareWindow.setVisible(true);
	}
	
	public TipsWindow getTipsWindow()
	{
		return tipsWindow;
	}
	
	public AchievementUI getAchievementUI()
	{
		return achievementUI;
	}
	
	public BuildingAdjustGroup getBuildAjustUI()
	{
		return mBuildAjustUI;
	}
	
	public MessageBoard getMessageBoard()
	{
		return messageBoard;
	}
	
	public byte[] getShareImg()
	{
		byte[] data = ScreenUtils.getFrameBufferPixels(true);
		
		return data;
	}
	
	public ConstructionWindow createConstructionWindow(BuildingType buildingType, List<Resource> resouces, int numAllowedBuilder) {
		ConstructionWindow constructionWindow = new ConstructionWindow(buildingType, resouces, numAllowedBuilder);
		constructionWindow.setVisible(false);
		mStage.addActor(constructionWindow);
		buildingViewWindows.add(constructionWindow);
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
		buildingViewWindows.add(window);
		return window;
	}
	
	public FarmViewWindow createFarmViewWindow(int numAllowedWorker) {
		FarmViewWindow window = new FarmViewWindow(numAllowedWorker);
		mStage.addActor(window);
		buildingViewWindows.add(window);
		return window;
	}
	
	public RanchViewWindow createRanchViewWindow(int numAllowedWorker) {
		RanchViewWindow window = new RanchViewWindow(numAllowedWorker);
		mStage.addActor(window);
		buildingViewWindows.add(window);
		return window;
	}
	
	public WorkableViewWindow createWorkableViewWindow(BuildingType buildingType, int numAllowedWorker) {
		WorkableViewWindow window = new WorkableViewWindow(buildingType, numAllowedWorker);
		mStage.addActor(window);
		buildingViewWindows.add(window);
		return window;
	}
	
	public MPBuildingViewWindow createMPBuildingViewWindow(BuildingType buildingType, int numAllowedWorker) {
		MPBuildingViewWindow window = new MPBuildingViewWindow(buildingType, numAllowedWorker);
		mStage.addActor(window);
		buildingViewWindows.add(window);
		return window;
	}
	
	public BarViewWindow createBarViewWindow(int numAllowedWorker, int maxWineStorage) {
		BarViewWindow window = new BarViewWindow(numAllowedWorker, maxWineStorage);
		mStage.addActor(window);
		buildingViewWindows.add(window);
		return window;
	}
	
	public HospitalViewWindow createHospitalViewWindow(int numAllowedWorker) {
		HospitalViewWindow window = new HospitalViewWindow(numAllowedWorker);
		mStage.addActor(window);
		buildingViewWindows.add(window);
		return window;
	}
	
	public SchoolViewWindow createSchoolViewWindow(int numAllowedWorker,int currentStudentNum){
		SchoolViewWindow schoolViewWindow=new SchoolViewWindow(numAllowedWorker,currentStudentNum);
		mStage.addActor(schoolViewWindow);
		buildingViewWindows.add(schoolViewWindow);
		return schoolViewWindow;
	}
	
	public WellViewWindow createWellViewWindow(){
		WellViewWindow wellViewWindow=new WellViewWindow();
		mStage.addActor(wellViewWindow);
		buildingViewWindows.add(wellViewWindow);
		return wellViewWindow;
	}
	
	public void hideAllWindow() {
		for(Actor window : buildingViewWindows) {
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
		stateBoard.update();
	}

	public void clear() {
		
	}
}

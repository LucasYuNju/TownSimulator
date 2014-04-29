package com.TownSimulator.ui.screen;

import java.util.Map;

import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.building.BuildComsUI;
import com.TownSimulator.ui.building.BuildingAdjustGroup;
import com.TownSimulator.ui.building.construction.ConstructionResourceInfo;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class GameScreenUI extends ScreenUIBase{
	private BuildComsUI		mBuildComsUI;
	private BuildingAdjustGroup	mBuildAjustUI;
	
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
//		ScrollPane scrollPane = new ScrollPane(null);
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		Label label = new Label("house", labelStyle);
		label.setSize(60, 40);
		label.setPosition(0, 0);
		label.setAlignment(Align.center);
		
		Table table = new Table();
		table.add(label).spaceLeft(10f);
		table.setSize(200, 200);
		table.setPosition(300, 300);
		
		mStage.addActor(table);
//		mStage.addActor(window);
	}

	public ConstructionWindow createConstructionWindow(Map<ResourceType, ConstructionResourceInfo> resouceMap, 
			int numAllowedBuilder) {
		ConstructionWindow constructionWindow = new ConstructionWindow(resouceMap, numAllowedBuilder);
		constructionWindow.setVisible(false);
		mStage.addActor(constructionWindow);
		return constructionWindow;
	}
}

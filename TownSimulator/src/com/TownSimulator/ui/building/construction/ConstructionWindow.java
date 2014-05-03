package com.TownSimulator.ui.building.construction;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.ui.building.ProcessBar;
import com.TownSimulator.ui.building.UndockedWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * window布局图：
 * 					  margin 		  closeButton
 *   		| header|				|
 * margin	|resIcon|resIcon|resIcon| margin
 *  	  	| label	| label	| label	|
 *  		| 		workers			|
 * 			| 		process bar		|
 * 					  margin
 */
public class ConstructionWindow extends UndockedWindow{
	//LABEL_WIDTH is also ICON_WIDTH
	private static final int NUM_PAIR = 3;
	
	//window去掉margin后的宽度和高度
	private float windowWidth;
	private float windowHeight;
	private ConstructionWindowListener listener;
	private ProcessBar processBar;

	private List<Resource> resources;
	private List<Label> resourceLabels;
	private WorkerGroup builderGroup;
	
	public ConstructionWindow(BuildingType buildingType, List<Resource> resources, int numAllowedBuilder) {
		super(buildingType);
		background = Singleton.getInstance(ResourceManager.class).findTextureRegion("background");
		this.resources = resources;
		//初始化顺序matters
		//1
		builderGroup = new WorkerGroup(this, numAllowedBuilder);
		builderGroup.setPosition(MARGIN, MARGIN * 1.4f + ProcessBar.HEIGHT);
		addActor(builderGroup);
		addPairs();
		
		//2
		float pairsWidth = LABEL_WIDTH * NUM_PAIR;
		float workersWidth = builderGroup.getGroupWidth();
		windowWidth = pairsWidth > workersWidth ? pairsWidth : workersWidth;
		windowHeight = LABEL_WIDTH * 2 + LABEL_HEIGHT * 2 + ProcessBar.HEIGHT;
		setSize(windowWidth+MARGIN * 2, windowHeight + MARGIN * 2);
		setPosition( (Gdx.graphics.getWidth() - getWidth())/2, 
				(Gdx.graphics.getHeight() - getHeight())/2 );

		//3
		processBar = new ProcessBar(windowWidth);
		processBar.setPosition(MARGIN, MARGIN);
		addActor(processBar);
		addHeader();
		addCloseButton();
	}
	
	
	private void addPairs() {
		// initialize icon button and label
		resourceLabels = new ArrayList<Label>();
		for (int i = 0; i < NUM_PAIR; i++) {
			Button btn;
			if(i == 0)
				btn = new FlipButton("button_build_food", "button_build_food", null);
			else 
				btn = new FlipButton("button_build_house", "button_build", null);
			btn.setSize(LABEL_WIDTH, LABEL_WIDTH);
			btn.setPosition(LABEL_WIDTH * i + MARGIN, LABEL_HEIGHT + LABEL_WIDTH + ProcessBar.HEIGHT + MARGIN);
			btn.addListener(new EventListener() {
				@Override
				public boolean handle(Event event) {
					return false;
				}
			});
			addActor(btn);

			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
			labelStyle.fontColor = Color.WHITE;
			Label label = new Label("", labelStyle);
			label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setPosition(LABEL_WIDTH * i + MARGIN, LABEL_WIDTH + ProcessBar.HEIGHT + MARGIN);
			label.setAlignment(Align.center);
			resourceLabels.add(label);
			addActor(label);
		}
		refreshResouceLabel();
	}
	
	public void refreshResouceLabel() {
		for(int i=0; i<resources.size(); i++) {
			StringBuilder str = new StringBuilder();
			str.append(resources.get(i).getAmount());
			str.append("/");
			str.append(resources.get(i).getNeededAmount());
			resourceLabels.get(i).setText(str.toString());
		}
	}
		
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	private void setBuilderUpperLimit(int limit) {
		//最多只能有5个建筑工同时建造一个房子
		if(limit <= builderGroup.getNumAllowedBuilder())
			builderGroup.setUpperLimit(limit);
	}
	
	public void setProcess(float process) {
		processBar.setProcess(process);
	}

	public void setListener(ConstructionWindowListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void builderLimitSelected(int selectedLimit) {
		listener.builderLimitSelected(selectedLimit);
	}
	
	private void constructionCancelled() {
		listener.constructionCancelled();
	}

	public void addBuilder() {
		builderGroup.addBuilder();
	}	
}

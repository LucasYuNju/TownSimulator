package com.TownSimulator.ui.building.construction;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.UndockedWindow;
import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private static final float PROCESS_BAR_HEIGHT = Settings.UNIT * 0.2f;
	private static final int NUM_PAIR = 3;
	
	private TextureRegion background;
	//window去掉margin后的宽度和高度，不计MARGIN
	private float windowWidth;
	private float windowHeight;
	private ConstructionWindowListener listener;
	
	//进度条
	private float process;
	private TextureRegion processBar;
	private TextureRegion blackFrame;

	private List<Resource> resources;
	private List<Label> resourceLabels;
	private ConstructionBuilderGroup builderGroup;
	
	public ConstructionWindow(BuildingType buildingType, List<Resource> resources, int numAllowedBuilder) {
		super(buildingType);
		background = Singleton.getInstance(ResourceManager.class).findTextureRegion("background");
		processBar = Singleton.getInstance(ResourceManager.class).findTextureRegion("process_bar");
		blackFrame = Singleton.getInstance(ResourceManager.class).findTextureRegion("frame_black");
		this.resources = resources;
		
		//初始化顺序matters
		//1
		builderGroup = new ConstructionBuilderGroup(this, numAllowedBuilder);
		builderGroup.setPosition(MARGIN, MARGIN * 1.4f + PROCESS_BAR_HEIGHT);
		addActor(builderGroup);
		addPairs();
		
		//2
		float pairsWidth = LABEL_WIDTH * NUM_PAIR;
		float workersWidth = builderGroup.getGroupWidth();
		windowWidth = pairsWidth > workersWidth ? pairsWidth : workersWidth;
		windowHeight = LABEL_WIDTH * 2 + LABEL_HEIGHT * 2 + PROCESS_BAR_HEIGHT;
		setSize(windowWidth + MARGIN * 2, windowHeight + MARGIN * 2);
		setPosition( (Gdx.graphics.getWidth() - getWidth())/2, 
				(Gdx.graphics.getHeight() - getHeight())/2 );

		//3
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
			btn.setPosition(LABEL_WIDTH * i + MARGIN, LABEL_HEIGHT + LABEL_WIDTH + PROCESS_BAR_HEIGHT + MARGIN);
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
			label.setPosition(LABEL_WIDTH * i + MARGIN, LABEL_WIDTH + PROCESS_BAR_HEIGHT + MARGIN);
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
		Color c = this.getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
		applyTransform(batch, computeTransform());
		drawChildren(batch, parentAlpha);
		resetTransform(batch);
		
		//draw process bar
		batch.draw(processBar, getX() + MARGIN, getY() + MARGIN, windowWidth * process, PROCESS_BAR_HEIGHT);
		batch.draw(blackFrame, getX() + MARGIN, getY() + MARGIN, windowWidth , 			PROCESS_BAR_HEIGHT);
	}
	
	private void setBuilderUpperLimit(int limit) {
		//最多只能有5个建筑工同时建造一个房子
		if(limit <= builderGroup.getNumAllowedBuilder())
			builderGroup.setUpperLimit(limit);
	}
	
	public void setProcess(float process) {
		this.process = process > 1 ? 1 : process;
	}

	public void setListener(ConstructionWindowListener listener) {
		this.listener = listener;
	}
		
	void builderLimitSelected(int selectedLimit) {
		listener.builderLimitSelected(selectedLimit);
	}
	
	private void constructionCancelled() {
		listener.constructionCancelled();
	}

	public void addBuilder() {
		builderGroup.addBuilder();
	}	
}

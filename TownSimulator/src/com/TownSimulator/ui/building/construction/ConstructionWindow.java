package com.TownSimulator.ui.building.construction;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.ui.building.view.ProcessBar;
import com.TownSimulator.ui.building.view.UndockedWindow;
import com.TownSimulator.ui.building.view.WorkerGroup;
import com.TownSimulator.ui.building.view.WorkerGroupListener;
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
		background = Singleton.getInstance(ResourceManager.class).createTextureRegion("background");
		this.resources = resources;
		//初始化顺序matters
		//1
		builderGroup = new WorkerGroup(numAllowedBuilder);
		builderGroup.setPosition(MARGIN, MARGIN + ProcessBar.HEIGHT);
		addActor(builderGroup);
		addPairs();
		
		//2
		float pairsWidth = LABEL_WIDTH * NUM_PAIR;
		windowWidth = pairsWidth > builderGroup.getWidth() ? pairsWidth : builderGroup.getWidth();
		windowHeight = ProcessBar.HEIGHT + WorkerGroup.HEIGHT + LABEL_HEIGHT + ICON_WIDTH;
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
			btn.setPosition(LABEL_WIDTH * i + MARGIN, MARGIN + ProcessBar.HEIGHT + WorkerGroup.HEIGHT + LABEL_HEIGHT);
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
			label.setPosition(LABEL_WIDTH * i + MARGIN, MARGIN + ProcessBar.HEIGHT + WorkerGroup.HEIGHT);
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
	
	public void setProcess(float process) {
		processBar.setProcess(process);
	}

	public void setConstructionListener(ConstructionWindowListener listener) {
		this.listener = listener;
	}
	
	public void setWorkerGroupListener(WorkerGroupListener workerGroupListener) {
		builderGroup.setListener(workerGroupListener);
	}
	
	//modifier should be private
	public void constructionCancelled() {
		listener.constructionCancelled();
	}

	public void addBuilder() {
		builderGroup.addWorker();
	}	
}

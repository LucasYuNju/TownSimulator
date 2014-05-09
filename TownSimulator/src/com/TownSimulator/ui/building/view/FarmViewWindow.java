package com.TownSimulator.ui.building.view;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Layout:
 * 
 * header
 * farmState|	processBar
 * curCrop	|	nextCrop
 * workers
 * 
 */
public class FarmViewWindow extends ListenableViewWindow{
	private SelectBox<String> dropDown;
	private ProcessBar processBar;
	private TextureRegion buttonBackground;
	private WorkerGroup workerGroup;
	private float width;
	private float height;
	private SelectBoxListener selectBoxListener;
	
	public FarmViewWindow() {
		super(BuildingType.FARM_HOUSE);
		buttonBackground = Singleton.getInstance(ResourceManager.class).findTextureRegion("background_button");
		width = ProcessBar.PREFERED_WIDTH + LABEL_WIDTH + MARGIN * 2;
		height = LABEL_WIDTH * 3 + WorkerGroup.HEIGHT + MARGIN * 2;
		setSize(width, height);
		addRowOne();
		addRowTwo();
		
		workerGroup = new WorkerGroup(5);
		workerGroup.setPosition(MARGIN, MARGIN);
		addActor(workerGroup);

		addCloseButton();
		addHeader();
	}
	
	private void addRowOne() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		Label label = new Label("state", labelStyle);
		label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setPosition(MARGIN, MARGIN + WorkerGroup.HEIGHT + LABEL_WIDTH * 2);
		label.setAlignment(Align.left);
		addActor(label);
		
		processBar = new ProcessBar();
		processBar.setPosition(MARGIN + LABEL_WIDTH, MARGIN + WorkerGroup.HEIGHT + LABEL_WIDTH * 2);
		addActor(processBar);
	}
	
	private void addRowTwo() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		Label label = new Label("curCrop", labelStyle);
		label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setPosition(MARGIN, MARGIN + WorkerGroup.HEIGHT + LABEL_WIDTH);
		label.setAlignment(Align.left);
		addActor(label);

		addDropDown();
	}

	private void addDropDown() {
		SelectBoxStyle style = new SelectBoxStyle();
		style.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		style.fontColor = Color.WHITE;
		style.background = new TextureRegionDrawable(buttonBackground);
		style.scrollStyle = new ScrollPaneStyle();
		style.scrollStyle.background = style.background;
		style.listStyle = new ListStyle();
		style.listStyle.font = style.font;
		style.listStyle.background = style.background;
		style.listStyle.selection = style.background;
		dropDown = new SelectBox<String>(style);
		dropDown.setItems("Wheat", "Corn", "Cabbage");
		dropDown.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		dropDown.setPosition(getWidth() - MARGIN - LABEL_WIDTH, MARGIN + WorkerGroup.HEIGHT + LABEL_WIDTH);
		dropDown.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				selectBoxListener.selectBoxSelected(dropDown.getSelected());
				return false;
			}
		});
		
		addActor(dropDown);
	}
	
	public void addWorker() {
		workerGroup.addWorker();
	}
	
	@Override
	protected void updatePosition()
	{
		Vector3 pos = new Vector3(buildingPosXWorld, buildingPosYWorld, 0.0f);
		CameraController.getInstance(CameraController.class).worldToScreen(pos);
		float windowX = pos.x - getWidth();
		float windowY = pos.y - getHeight() * 0.5f;
		setPosition(windowX, windowY);
	}
	
	@Override
	public void setWorkerGroupListener(WorkerGroupListener workerGroupListener) {
		workerGroup.setListener(workerGroupListener);
	}

	@Override
	public void setSelectBoxListener(SelectBoxListener selectBoxListener) {
		this.selectBoxListener = selectBoxListener;
	}
}
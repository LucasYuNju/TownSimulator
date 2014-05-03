package com.TownSimulator.ui.building.view;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class ViewWindow extends UndockedWindow{
	private static final int NUM_LABEL_PER_PAGE = 8;
	String[][] data;
	List<Label> labels;
	
	/**
	 * @param data 允许为null或者为空，如果这样的话，ViewWindow的UI初始化会推迟到第一次调用updateData()
	 * 
	 */
	public ViewWindow(BuildingType buildingType, String data[][]) {
		super(buildingType);
		setPosition(0, 0);
		setSize(LABEL_WIDTH * 2 + MARGIN * 2, LABEL_HEIGHT * NUM_LABEL_PER_PAGE + MARGIN * 2);
		if(data != null && data.length != 0) {
			initUI();
			updateData(data);
		}
	}
	
	/*
	 * for subclasses
	 */
	public ViewWindow(BuildingType buildingType) {
		super(buildingType);		
	}
	
	private void initUI() {
		int numLabel = data.length > NUM_LABEL_PER_PAGE ? data.length : NUM_LABEL_PER_PAGE;
		int numLabelPerRow = 2;
		if(data.length != 0)
			numLabelPerRow = data[0].length;
		setSize(LABEL_WIDTH * numLabelPerRow + MARGIN * 2, LABEL_HEIGHT * numLabel + MARGIN * 2);
		addLabels();
		addCloseButton();
		addHeader();
	}
	
	public void addLabels() {
		labels = new ArrayList<Label>();
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		for(int i=0; i<data.length; i++) {
			for(int j=0; j<data[0].length; j++) {
				Label label = new Label(data[i][j], labelStyle);
				label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
				label.setPosition(LABEL_WIDTH * j + MARGIN, getHeight() - LABEL_HEIGHT * i - LABEL_HEIGHT - MARGIN);
				addActor(label);
				labels.add(label);
			}
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(getParent() instanceof ScrollPane)
			getParent().setVisible(visible);
	}

	/**
	 * update parent actor's position.
	 * The parent actor is implicitly a scrollPane
	 */
	@Override
	protected void updatePosition()
	{
		Vector3 pos = new Vector3(buildingPosXWorld, buildingPosYWorld, 0.0f);
		CameraController.getInstance(CameraController.class).worldToScreen(pos);
		float windowX = pos.x - getWidth();
		float windowY = pos.y - getHeight() * 0.5f;
		getParent().setPosition(windowX, windowY);
	}	

	
	public void updateData(String[][] newData) {
		//data为null或者为空，UI没有初始化过
		if(newData != null && newData.length != 0) {
			if(data == null || data.length == 0 ) {
				data = newData;
				initUI();
			}
			else if(data.length != newData.length) {
				for(Label label : labels) {
					removeActor(label);
				}
				data = newData;
				initUI();
			}
		}
		data = newData;
		updateLabels();
	}
	
	private void updateLabels() {
		for(int i=0; i<data.length; i++) {
			for(int j=0; j<data[0].length; j++) {
				labels.get(i*data[0].length+j).setText(data[i][j]);
			}
		}
	}
	
	public void setWorkerGroupListener(WorkerGroupListener workerGroupListener) {
	}
	
	public void setSelectBoxListener(SelectBoxListener selectBoxListener) {
	}
}
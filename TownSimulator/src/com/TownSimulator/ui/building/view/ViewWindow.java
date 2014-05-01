package com.TownSimulator.ui.building.view;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.building.UndockedWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class ViewWindow extends UndockedWindow{
	private static final int NUM_LABEL_PER_PAGE = 8;
	String[][] data;
	
	public ViewWindow(BuildingType buildingType, String data[][]) {
		super(buildingType);
		this.data = data;
		
		if(buildingType != BuildingType.FARM_HOUSE) {
			int numLabel = data.length > NUM_LABEL_PER_PAGE ? data.length : NUM_LABEL_PER_PAGE;
			int numLabelPerRow = 2;
			if(data.length != 0) 
			numLabelPerRow = data[0].length;
			setSize(LABEL_WIDTH * numLabelPerRow + MARGIN * 2, LABEL_HEIGHT * numLabel + MARGIN * 2);
			setPosition(0, 0);
			addLabels();
			addCloseButton();
			addHeader();
		}
	}
	
	public void addLabels() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		for(int i=0; i<data.length; i++) {
			for(int j=0; j<data[0].length; j++) {
				Label label = new Label(data[i][j], labelStyle);
				label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
				label.setPosition(LABEL_WIDTH * j + MARGIN, getHeight() - LABEL_HEIGHT * i - LABEL_HEIGHT - MARGIN);
				addActor(label);
			}
		}
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
}
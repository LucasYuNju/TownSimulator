package com.TownSimulator.ui.building.view;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class BarViewWindow extends WorkableViewWindow{
	private Label wineStorageLabel;
	private int wineStorage;
	private int maxWineStorage;
	
	public BarViewWindow(int numAllowedWorker, int maxWineStorage) {
		super(BuildingType.Bar, numAllowedWorker);
		this.maxWineStorage = maxWineStorage;
		if(getWidth() < LABEL_WIDTH * 3)
			setSize(LABEL_WIDTH *3, getHeight() + LABEL_HEIGHT);
		else
			setSize(getWidth(), getHeight() + LABEL_HEIGHT);
		addLabel();
		updateLayout();
	}
	
	private void addLabel() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		Label label = new Label(getWineStorageText(), labelStyle);
		label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setPosition(MARGIN, MARGIN + WorkerGroup.HEIGHT);
		label.setAlignment(Align.left);
		addActor(label);
	}
	
	public void updateWineStorage(int wineStorage) {
		if(wineStorage >= 0 && wineStorage <= maxWineStorage) {
			this.wineStorage = wineStorage;
			updateLabl();
		}
	}
	
	private void updateLabl() {
		if(wineStorageLabel == null) {
			addLabel();
		}
		else {
			wineStorageLabel.setText(getWineStorageText());
		}
	}
	
	private String getWineStorageText() {
		return "wine " + wineStorage + "/" + maxWineStorage;
	}
}
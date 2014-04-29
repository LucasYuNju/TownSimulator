package com.TownSimulator.ui.building.view;

import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class ViewWindow extends Group{
	private static final float LABEL_WIDTH = Settings.UNIT * 2f;
	private static final float LABEL_HEIGHT = Settings.UNIT * 1f;
	private static final float MARGIN = ConstructionWindow.MARGIN;
	private static final int MAX_LABEL = 15;
	
	
	public ViewWindow() {
		super();
		setSize(LABEL_WIDTH + MARGIN * 2, LABEL_HEIGHT * MAX_LABEL + MARGIN * 2);
		setPosition(0, 0);
		init();
	}
	
	public void init() {
		for(int i=0; i< MAX_LABEL * 2/3; i++) {
			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
			labelStyle.fontColor = Color.WHITE;
			Label label = new Label(i + " :10101010101111", labelStyle);
			label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setPosition(MARGIN, LABEL_HEIGHT * i + MARGIN);
			addActor(label);
		}
	}
}

package com.TownSimulator.ui.build;

import com.TownSimulator.ui.base.UIButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class BuildingInfoWindow extends Group{
	private BuildingInfoWindowListener listener;
	private static final float LABEL_WIDTH = Settings.UNIT * 1f;
	private static final float LABEL_HEIGHT = Settings.UNIT * 0.5f;
	private int numLabels = 2;
	
	
	public BuildingInfoWindow() {
		initComponents();
	}

	private void initComponents() {
		//initialize wood button and label
		setSize(LABEL_WIDTH * numLabels, LABEL_WIDTH + LABEL_HEIGHT);
		
		Button btn = new UIButton("button_build_food", "button_build_house", null);
		btn.setSize(LABEL_WIDTH, LABEL_WIDTH);
		btn.setPosition(0, LABEL_HEIGHT);
		btn.addListener(new EventListener() {
			
			@Override
			public boolean handle(Event event) {
				return false;
			}
		});
		addActor(btn);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.5f));
		labelStyle.fontColor = Color.WHITE;
		Label label = new Label("27/30", labelStyle);
		label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setAlignment(Align.center);
		addActor(label);		
	}
	
	public void setListener(BuildingInfoWindowListener listener) {
		this.listener = listener;
	}
}

interface BuildingInfoWindowListener {
	
}
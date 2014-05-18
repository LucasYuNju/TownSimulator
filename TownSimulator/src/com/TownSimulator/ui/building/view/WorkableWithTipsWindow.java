package com.TownSimulator.ui.building.view;


import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class WorkableWithTipsWindow extends WorkableViewWindow {
	private Label tipsLabel;
	
	public WorkableWithTipsWindow(BuildingType buildingType,
			int numAllowedWorker) {
		super(buildingType, numAllowedWorker);
		setSize(getWidth(), getHeight() + LABEL_HEIGHT + MARGIN);
		init();
	}
	
	private void init()
	{
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int)(LABEL_HEIGHT * 0.7f));
		labelStyle.fontColor = Color.RED;
		tipsLabel = new Label("", labelStyle);
		tipsLabel.setSize(getWidth() - dynamiteButton.getWidth(), LABEL_HEIGHT);
		tipsLabel.setPosition(0.0f, MARGIN);
		tipsLabel.setAlignment(Align.center);
		addActor(tipsLabel);
		
		workerGroup.setPosition(MARGIN, MARGIN + tipsLabel.getHeight());
		closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
		headerLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
	}
	
	public void setTips(String tips)
	{
		tipsLabel.setText(tips);
	}
}

package com.TownSimulator.ui.building.view;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class WellViewWindow extends UndockedWindow {
	private float width;
	private float height;
	private Label textLabel;

	public WellViewWindow() {
		super(BuildingType.WELL);
		
		width=UndockedWindow.LABEL_WIDTH*2+UndockedWindow.MARGIN*2;
		height=UndockedWindow.LABEL_HEIGHT*2+UndockedWindow.MARGIN*2;
		setSize(width,height);
		
		addLabel();
		addHeader();
		addCloseButton();
		updateLayout();
	}
	
	public void addLabel(){
		LabelStyle labelStyle=new LabelStyle();
		labelStyle.font=ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor=Color.WHITE;
		textLabel=new Label(getTextLabelString(), labelStyle);
		textLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		textLabel.setPosition(MARGIN, MARGIN);
		textLabel.setAlignment(Align.left);
		addActor(textLabel);
	}

	private String getTextLabelString() {
		// TODO Auto-generated method stub
		return "This is a well.....";
	}

}

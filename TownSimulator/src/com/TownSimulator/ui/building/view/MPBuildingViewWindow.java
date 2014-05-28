package com.TownSimulator.ui.building.view;


import java.util.ArrayList;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MPBuildingViewWindow extends WorkableViewWindow {
	private ArrayList<Label> descLabels;
	private static final float DESC_LABEL_HEIGHT = TIPS_LABEL_HEIGHT;
	private BitmapFont font;
	private Color descColor = Color.WHITE;
	
	public MPBuildingViewWindow(BuildingType buildingType,
			int numAllowedWorker) {
		super(buildingType, numAllowedWorker);
//		setSize(getWidth(), getHeight() + LABEL_HEIGHT + MARGIN);
		init();
	}
	
	private void init()
	{
		descLabels = new ArrayList<Label>();
		font = ResourceManager.getInstance(ResourceManager.class).getFont((int)(DESC_LABEL_HEIGHT));
		
//		LabelStyle labelStyle = new LabelStyle();
//		labelStyle.font = font;
//		labelStyle.fontColor = Color.RED;
//		tipsLabel = new Label("", labelStyle);
//		tipsLabel.setSize(getWidth() - dynamiteButton.getWidth(), LABEL_HEIGHT);
//		tipsLabel.setPosition(0.0f, MARGIN);
//		tipsLabel.setAlignment(Align.center);
//		addActor(tipsLabel);
//		
//		workerGroup.setPosition(MARGIN, MARGIN + tipsLabel.getHeight());
//		closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
//		headerLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
	}
	
//	public void setTips(String tips)
//	{
//		setTips(tips, Color.WHITE);
//	}
	
	public void setDesc(String desc, Color color)
	{
//		font.setColor(color);
		descColor = color;
		splitDesc(desc);
		
		updateLayout();
	}
	
	private void splitDesc(String desc)
	{
		for (Label l : descLabels) {
			removeActor(l);
		}
		descLabels.clear();
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = descColor;
		float labelWidth = getWidth() - dynamiteButton.getWidth() - MARGIN * 2.0f;
		float width = 0.0f;
		StringBuilder strBuilder = new StringBuilder();
		for (char c : desc.toCharArray()) {
			if(width + font.getBounds(c + "").width > labelWidth)
			{
				Label label = new Label(strBuilder.toString(), labelStyle);
				label.setSize(labelWidth, DESC_LABEL_HEIGHT);
				addActor(label);
				descLabels.add(label);
				width = font.getBounds(c + "").width;
				strBuilder = new StringBuilder(c + "");
			}
			else
			{
				width += font.getBounds(c + "").width;
				strBuilder.append(c);
			}
		}
		
		if(strBuilder.length() > 0)
		{
			Label label = new Label(strBuilder.toString(), labelStyle);
			label.setSize(labelWidth, DESC_LABEL_HEIGHT);
			addActor(label);
			descLabels.add(label);
		}
	}

	@Override
	protected void updateLayout() {
		if(descLabels == null)
		{
			super.updateLayout();
			return;
		}
		
		setHeight(HEADER_HEIGHT + workerGroup.getHeight() + TIPS_LABEL_HEIGHT + MARGIN * 5.0f + DESC_LABEL_HEIGHT * descLabels.size());
		
		super.updateLayout();
		
		if(workerGroup != null)
			workerGroup.setPosition(MARGIN, MARGIN * 2.0f + TIPS_LABEL_HEIGHT);
		
		float y = getHeight() - HEADER_HEIGHT - MARGIN - DESC_LABEL_HEIGHT;
		for (int i = 0; i < descLabels.size(); i++) {
			Label label = descLabels.get(i);
			label.setPosition(MARGIN, y);
			
			y -= DESC_LABEL_HEIGHT;
		}
	}
	
	
}

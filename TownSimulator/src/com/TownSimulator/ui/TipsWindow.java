package com.TownSimulator.ui;

import com.TownSimulator.ui.base.BaseWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class TipsWindow extends BaseWindow{
	private static final float LABEL_HEIGHT = Settings.LABEL_HEIGHT * 0.8f;
	private Label textLabel;
	private float visibleTime = 0.0f;
	
	public TipsWindow()
	{
		initUI();
		
		setVisible(false);
	}

	private void initUI() {
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int)LABEL_HEIGHT);
		labelStyle.fontColor = Color.WHITE;
		
		textLabel = new Label("", labelStyle);
		textLabel.setPosition(Settings.MARGIN, Settings.MARGIN);
		textLabel.setHeight(LABEL_HEIGHT);
		addActor(textLabel);
	}
	
	public void showTips(String tips, float visibleTime)
	{
		if(visibleTime <= 0.0f)
			return;
		
		this.visibleTime = visibleTime;
		showTips(tips);
	}
	
	public void showTips(String tips)
	{
		setVisible(true);
		
		textLabel.setText(tips);
		textLabel.setWidth(textLabel.getStyle().font.getBounds(tips).width);
		setSize(textLabel.getWidth() + Settings.MARGIN * 2.0f, LABEL_HEIGHT + Settings.MARGIN * 2.0f);
		setPosition(Gdx.graphics.getWidth() * 0.5f - getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f - getHeight() * 0.5f);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(visibleTime > 0.0f)
		{
			visibleTime -= delta;
			
			if(visibleTime <= 0.0f)
				setVisible(false);
		}
	}
	
	
}

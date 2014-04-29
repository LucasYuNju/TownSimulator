package com.TownSimulator.ui.building.view;

import java.util.HashMap;
import java.util.Map;

import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class ViewWindow extends Group{
	private static final float LABEL_WIDTH = Settings.UNIT * 1.5f;
	private static final float LABEL_HEIGHT = Settings.UNIT * 0.5f;
	private static final float MARGIN = ConstructionWindow.MARGIN;
	private TextureRegion background;
	private Map<String, String> dataMap;
	
	public ViewWindow() {
		super();
		fakeData();
		setSize(LABEL_WIDTH * 2 + MARGIN * 2, LABEL_HEIGHT * dataMap.size() + MARGIN * 2);
		setPosition(0, 0);
		background = Singleton.getInstance(ResourceManager.class).findTextureRegion("background");
		init();
	}
	
	public void fakeData() {
		dataMap = new HashMap<String, String>();
		dataMap.put("wood", "1234");
		dataMap.put("stone", "123");
		dataMap.put("wheat", "123");
		dataMap.put("corn", "123");
		dataMap.put("meat", "123");
		dataMap.put("fur", "123");
		dataMap.put("pants", "123");
		dataMap.put("coats", "123");
		dataMap.put("laosiji", "123");
		dataMap.put("kabasiji", "123");
	}
	
	public void init() {
		int counter=0;
		for(String key : dataMap.keySet()) {
			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
			labelStyle.fontColor = Color.WHITE;

			Label keyLabel = new Label(++counter + "." + key, labelStyle);
			keyLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
			keyLabel.setPosition(MARGIN, LABEL_HEIGHT * (counter-1) + MARGIN);
//			keyLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT * counter - MARGIN);
			addActor(keyLabel);
			
			Label valueLabel = new Label(":" + dataMap.get(key), labelStyle);
			valueLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
			valueLabel.setPosition(MARGIN + LABEL_WIDTH, LABEL_HEIGHT * (counter-1) + MARGIN);
//			keyLabel.setPosition(MARGIN + LABEL_WIDTH, getHeight() - LABEL_HEIGHT * counter - MARGIN);
			addActor(valueLabel);
		}
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = this.getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
		applyTransform(batch, computeTransform());
		drawChildren(batch, parentAlpha);
		resetTransform(batch);	
	}
}

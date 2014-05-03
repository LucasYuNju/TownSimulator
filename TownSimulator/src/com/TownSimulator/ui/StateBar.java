package com.TownSimulator.ui;

import com.TownSimulator.ui.building.UndockedWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * labels:
 * 
 * food		| numFood
 * people	| numPeople
 *
 */
public class StateBar extends Group{
	private static final float LABEL_WIDTH = UndockedWindow.LABEL_WIDTH;
	private static final float LABEL_HEIGHT = UndockedWindow.LABEL_HEIGHT;
	private static final float MARGIN = UndockedWindow.MARGIN;
	
	private TextureRegion background;
	private Label numFoodLabel;
	private Label numPeopleLabel;
	
	public StateBar(int numPeople, int numFood) {
		super();
		background = Singleton.getInstance(ResourceManager.class).findTextureRegion("background");
		setSize(MARGIN * 2 + LABEL_WIDTH * 2, MARGIN * 2 + LABEL_HEIGHT * 2);
		setPosition(0, Gdx.graphics.getHeight() - getHeight());
		initLabels();
		update(numPeople, numFood);
	}
	
	private void initLabels() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;

		Label peopleLabel = new Label("People", labelStyle);
		peopleLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		peopleLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
		peopleLabel.setAlignment(Align.center);
		addActor(peopleLabel);
		
		numPeopleLabel = new Label("", labelStyle);
		numPeopleLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		numPeopleLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
		numPeopleLabel.setAlignment(Align.center);
		addActor(numPeopleLabel);

		//position
		Label foodLabel = new Label("Food", labelStyle);
		foodLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		foodLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
		foodLabel.setAlignment(Align.center);
		addActor(foodLabel);
		
		numFoodLabel = new Label("", labelStyle);
		numFoodLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		numFoodLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
		numFoodLabel.setAlignment(Align.center);
		addActor(numFoodLabel);
		
	}
	
	public void update(int numPeople, int numFood) {
		
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
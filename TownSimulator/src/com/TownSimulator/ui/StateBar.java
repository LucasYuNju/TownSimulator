package com.TownSimulator.ui;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.World;
import com.TownSimulator.ui.building.view.UndockedWindow;
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
 * season	| date
 * food		| numFood
 * people	| numPeople
 *
 */
public class StateBar extends Group{
	private static final float LABEL_WIDTH = Settings.LABEL_WIDTH * 1.2f;
	private static final float LABEL_HEIGHT = Settings.LABEL_HEIGHT;
	private static final float MARGIN = UndockedWindow.MARGIN * 0.3f;
	
	private int line = 3;
	private int column = 2;
	
	private TextureRegion background;
	private Label seasonLabel;
	private Label dateLabel;
	private Label numFoodLabel;
	private Label numPeopleLabel;
	
	public StateBar() {
		super();
		background = Singleton.getInstance(ResourceManager.class).createTextureRegion("background");
		setSize( (MARGIN + LABEL_WIDTH) * column + MARGIN, (MARGIN + LABEL_HEIGHT) * line + MARGIN);
		setPosition(0, Gdx.graphics.getHeight() - getHeight());
		initLabels();
		
		setColor(1.0f, 1.0f, 1.0f, Settings.UI_ALPHA);
	}
	
//	public StateBar() {
//		this();
//	}
	
	private void initLabels() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		
		float x = MARGIN;
		float y = MARGIN;
		
		Label peopleLabel = new Label("People", labelStyle);
		peopleLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		peopleLabel.setPosition(x, y);
		peopleLabel.setAlignment(Align.left);
		addActor(peopleLabel);
		
		x += LABEL_WIDTH + MARGIN;
		numPeopleLabel = new Label("", labelStyle);
		numPeopleLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		numPeopleLabel.setPosition(x, y);
		numPeopleLabel.setAlignment(Align.left);
		addActor(numPeopleLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label foodLabel = new Label("Food", labelStyle);
		foodLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		foodLabel.setPosition(x, y);
		foodLabel.setAlignment(Align.left);
		addActor(foodLabel);
		
		x += LABEL_WIDTH + MARGIN;
		numFoodLabel = new Label("", labelStyle);
		numFoodLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		numFoodLabel.setPosition(x, y);
		numFoodLabel.setAlignment(Align.left);
		addActor(numFoodLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		seasonLabel = new Label("", labelStyle);
		seasonLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		seasonLabel.setPosition(x, y);
		seasonLabel.setAlignment(Align.left);
		addActor(seasonLabel);
		
		x += LABEL_WIDTH + MARGIN;
		dateLabel = new Label("", labelStyle);
		dateLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		dateLabel.setPosition(x, y);
		dateLabel.setAlignment(Align.left);
		addActor(dateLabel);
		
	}
	
	public void update() {
		World world = World.getInstance(World.class);
		seasonLabel.setText(  world.getCurSeason().toString() );
		dateLabel.setText(world.getCurYear() + "/" + world.getCurMonth() + "/" + world.getCurDay());
		int numPeople = EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan().size;
		int numFood = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getFoodAmount();
		numPeopleLabel.setText(numPeople + "");
		numFoodLabel.setText(numFood + "");
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

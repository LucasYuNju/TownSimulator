package com.TownSimulator.ui;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.World;
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
 * adult	| numAdult
 * children	| numChildren
 * wood		| numWood
 * money	| numMoney
 *
 */
public class StateBar extends Group{
	private static final float LABEL_WIDTH = Settings.LABEL_WIDTH * 1.2f;
	private static final float LABEL_HEIGHT = Settings.LABEL_HEIGHT * 0.8f;
	private static final float MARGIN = Settings.MARGIN * 0.3f;
	
	private int line = 6;
	private int column = 2;
	
	private TextureRegion background;
	private Label seasonLabel;
	private Label dateLabel;
	private Label numFoodLabel;
	private Label numAdultLabel;
	private Label numChildrenLabel;
	private Label numWoodLabel;
	private Label numMoneyLabel;
	
	public StateBar() {
		super();
		background = Singleton.getInstance(ResourceManager.class).createTextureRegion("background");
		setSize( (MARGIN + LABEL_WIDTH) * column + MARGIN, (MARGIN + LABEL_HEIGHT) * line + MARGIN);
		setPosition(0, Gdx.graphics.getHeight() - getHeight());
		initLabels();
		
		setColor(1.0f, 1.0f, 1.0f, Settings.UI_ALPHA);
	}
	
	private void initLabels() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (LABEL_HEIGHT));
		labelStyle.fontColor = Color.WHITE;
		
		float x = MARGIN;
		float y = MARGIN;
		
		Label moneyLabel = new Label("$", labelStyle);
		moneyLabel.setColor(Color.ORANGE);
		moneyLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		moneyLabel.setPosition(x, y);
		moneyLabel.setAlignment(Align.left);
		addActor(moneyLabel);
		
		x += LABEL_WIDTH + MARGIN;
		numMoneyLabel = new Label("", labelStyle);
		numMoneyLabel.setColor(Color.ORANGE);
		numMoneyLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		numMoneyLabel.setPosition(x, y);
		numMoneyLabel.setAlignment(Align.left);
		addActor(numMoneyLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label woodLabel = new Label("Wood", labelStyle);
		woodLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		woodLabel.setPosition(x, y);
		woodLabel.setAlignment(Align.left);
		addActor(woodLabel);
		
		x += LABEL_WIDTH + MARGIN;
		numWoodLabel = new Label("", labelStyle);
		numWoodLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		numWoodLabel.setPosition(x, y);
		numWoodLabel.setAlignment(Align.left);
		addActor(numWoodLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label childrenLabel = new Label("Children", labelStyle);
		childrenLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		childrenLabel.setPosition(x, y);
		childrenLabel.setAlignment(Align.left);
		addActor(childrenLabel);
		
		x += LABEL_WIDTH + MARGIN;
		numChildrenLabel = new Label("", labelStyle);
		numChildrenLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		numChildrenLabel.setPosition(x, y);
		numChildrenLabel.setAlignment(Align.left);
		addActor(numChildrenLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label adultLabel = new Label("Adult", labelStyle);
		adultLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		adultLabel.setPosition(x, y);
		adultLabel.setAlignment(Align.left);
		addActor(adultLabel);
		
		x += LABEL_WIDTH + MARGIN;
		numAdultLabel = new Label("", labelStyle);
		numAdultLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		numAdultLabel.setPosition(x, y);
		numAdultLabel.setAlignment(Align.left);
		addActor(numAdultLabel);
		
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
		int numFood = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getFoodAmount();
		numFoodLabel.setText(numFood + "");
		
		int numAdult = 0;
		int numChildren = 0;
		for (Man man : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan()) {
			if(man.getInfo().isAdult())
				numAdult ++;
			else
				numChildren ++;
		}
		numAdultLabel.setText(numAdult + "");
		numChildrenLabel.setText(numChildren + "");
		
		int numWood = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getResourceAmount(ResourceType.RS_WOOD);
		numWoodLabel.setText(numWood + "");
		
		int numMoney = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getMoney();
		numMoneyLabel.setText(numMoney + "");
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

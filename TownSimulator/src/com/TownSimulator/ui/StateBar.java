package com.TownSimulator.ui;

import java.text.NumberFormat;
import java.util.Locale;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.World;
import com.TownSimulator.ui.base.FlipButton;
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
 * adult	| numAdult
 * children	| numChildren
 * food		| numFood
 * wood		| numWood
 * money	| numMoney
 *
 */
public class StateBar extends Group{
	private static final float LABEL_WIDTH_COL_0 = Settings.LABEL_WIDTH * 1.2f;
	private static final float LABEL_WIDTH_COL_1 = Settings.LABEL_WIDTH * 1.5f;
	private static final float LABEL_HEIGHT = Settings.LABEL_HEIGHT * 0.75f;
	private static final float MARGIN = Settings.MARGIN * 0.7f;
	
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
		setSize( MARGIN * (column + 1) + LABEL_WIDTH_COL_0 + LABEL_WIDTH_COL_1 + MARGIN, (MARGIN + LABEL_HEIGHT) * line + MARGIN);
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
		
		FlipButton candyIcon = new FlipButton("candy", "candy", null);
		candyIcon.setSize(LABEL_HEIGHT, LABEL_HEIGHT);
		candyIcon.setPosition(x, y);
		addActor(candyIcon);
		
		x += LABEL_WIDTH_COL_0 + MARGIN;
		numMoneyLabel = new Label("", labelStyle);
		numMoneyLabel.setColor(Color.ORANGE);
		numMoneyLabel.setSize(LABEL_WIDTH_COL_1, LABEL_HEIGHT);
		numMoneyLabel.setPosition(x, y);
		numMoneyLabel.setAlignment(Align.left);
		addActor(numMoneyLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label woodLabel = new Label("Wood", labelStyle);
		woodLabel.setSize(LABEL_WIDTH_COL_0, LABEL_HEIGHT);
		woodLabel.setPosition(x, y);
		woodLabel.setAlignment(Align.left);
		addActor(woodLabel);
		
		x += LABEL_WIDTH_COL_0 + MARGIN;
		numWoodLabel = new Label("", labelStyle);
		numWoodLabel.setSize(LABEL_WIDTH_COL_1, LABEL_HEIGHT);
		numWoodLabel.setPosition(x, y);
		numWoodLabel.setAlignment(Align.left);
		addActor(numWoodLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label childrenLabel = new Label("Children", labelStyle);
		childrenLabel.setSize(LABEL_WIDTH_COL_0, LABEL_HEIGHT);
		childrenLabel.setPosition(x, y);
		childrenLabel.setAlignment(Align.left);
		addActor(childrenLabel);
		
		x += LABEL_WIDTH_COL_0 + MARGIN;
		numChildrenLabel = new Label("", labelStyle);
		numChildrenLabel.setSize(LABEL_WIDTH_COL_1, LABEL_HEIGHT);
		numChildrenLabel.setPosition(x, y);
		numChildrenLabel.setAlignment(Align.left);
		addActor(numChildrenLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label adultLabel = new Label("Adult", labelStyle);
		adultLabel.setSize(LABEL_WIDTH_COL_0, LABEL_HEIGHT);
		adultLabel.setPosition(x, y);
		adultLabel.setAlignment(Align.left);
		addActor(adultLabel);
		
		x += LABEL_WIDTH_COL_0 + MARGIN;
		numAdultLabel = new Label("", labelStyle);
		numAdultLabel.setSize(LABEL_WIDTH_COL_1, LABEL_HEIGHT);
		numAdultLabel.setPosition(x, y);
		numAdultLabel.setAlignment(Align.left);
		addActor(numAdultLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label foodLabel = new Label("Food", labelStyle);
		foodLabel.setSize(LABEL_WIDTH_COL_0, LABEL_HEIGHT);
		foodLabel.setPosition(x, y);
		foodLabel.setAlignment(Align.left);
		addActor(foodLabel);
		
		x += LABEL_WIDTH_COL_0 + MARGIN;
		numFoodLabel = new Label("", labelStyle);
		numFoodLabel.setSize(LABEL_WIDTH_COL_1, LABEL_HEIGHT);
		numFoodLabel.setPosition(x, y);
		numFoodLabel.setAlignment(Align.left);
		addActor(numFoodLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		seasonLabel = new Label("", labelStyle);
		seasonLabel.setSize(LABEL_WIDTH_COL_0, LABEL_HEIGHT);
		seasonLabel.setPosition(x, y);
		seasonLabel.setAlignment(Align.left);
		addActor(seasonLabel);
		
		x += LABEL_WIDTH_COL_0 + MARGIN;
		dateLabel = new Label("", labelStyle);
		dateLabel.setSize(LABEL_WIDTH_COL_1, LABEL_HEIGHT);
		dateLabel.setPosition(x, y);
		dateLabel.setAlignment(Align.left);
		addActor(dateLabel);
		
	}
	
	public void update() {
		World world = World.getInstance(World.class);
		seasonLabel.setText(  world.getCurSeason().toString() );
		dateLabel.setText(world.getCurYear() + "/" + world.getCurMonth() + "/" + world.getCurDay());
		
		
		int numAdult = 0;
		int numChildren = 0;
		for (Man man : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan()) {
			if(man.getInfo().isAdult())
				numAdult ++;
			else
				numChildren ++;
		}
		numAdultLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(numAdult));
		numChildrenLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(numChildren));
		
		int numFood = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getFoodAmount();
		numFoodLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(numFood));
		
		int numWood = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getResourceAmount(ResourceType.RS_WOOD);
		numWoodLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(numWood));
		
		int numMoney = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getMoney();
		numMoneyLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(numMoney));
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

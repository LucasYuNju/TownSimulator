package com.TownSimulator.ui;

import java.text.NumberFormat;
import java.util.Locale;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.SeasonType;
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
 * wood		| numWood
 * food		| numFood
 * coat		| numCoat
 * adult	| numAdult
 * children	| numChildren
 * energy	| numEnergy
 *
 */
public class StateBoard extends Group{
	private static final float LABEL_WIDTH_COL_0 = Settings.LABEL_WIDTH * 1.2f;
	private static final float LABEL_WIDTH_COL_1 = Settings.LABEL_WIDTH * 1.5f;
	private static final float LABEL_HEIGHT = Settings.LABEL_HEIGHT * 0.75f;
	private static final float MARGIN = Settings.MARGIN * 0.7f;
	
	private int line = 7;
	private int column = 2;
	
	private TextureRegion background;
	private Label seasonLabel;
	private Label dateLabel;
	private Label numWoodLabel;
	private Label numFoodLabel;
	private Label numCoatLabel;
	private Label numAdultLabel;
	private Label numChildrenLabel;
	private Label numEnergyLabel;
	
	public StateBoard() {
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
		
		FlipButton energyIcon = new FlipButton("energy", "energy", null);
		energyIcon.setSize(LABEL_HEIGHT, LABEL_HEIGHT);
		energyIcon.setPosition(x, y);
		addActor(energyIcon);
		
		x += LABEL_WIDTH_COL_0 + MARGIN;
		numEnergyLabel = new Label("", labelStyle);
		numEnergyLabel.setColor(Color.ORANGE);
		numEnergyLabel.setSize(LABEL_WIDTH_COL_1, LABEL_HEIGHT);
		numEnergyLabel.setPosition(x, y);
		numEnergyLabel.setAlignment(Align.left);
		addActor(numEnergyLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label childrenLabel = new Label(ResourceManager.stringMap.get("stateBar_children"), labelStyle);
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
		Label adultLabel = new Label(ResourceManager.stringMap.get("stateBar_adult"), labelStyle);
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
		Label coatLabel = new Label(ResourceManager.stringMap.get("stateBar_coat"), labelStyle);
		coatLabel.setSize(LABEL_WIDTH_COL_0, LABEL_HEIGHT);
		coatLabel.setPosition(x, y);
		coatLabel.setAlignment(Align.left);
		addActor(coatLabel);
		
		x += LABEL_WIDTH_COL_0 + MARGIN;
		numCoatLabel = new Label("", labelStyle);
		numCoatLabel.setSize(LABEL_WIDTH_COL_1, LABEL_HEIGHT);
		numCoatLabel.setPosition(x, y);
		numCoatLabel.setAlignment(Align.left);
		addActor(numCoatLabel);
		
		x = MARGIN;
		y += LABEL_HEIGHT + MARGIN;
		Label foodLabel = new Label(ResourceManager.stringMap.get("stateBar_food"), labelStyle);
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
		Label woodLabel = new Label(ResourceManager.stringMap.get("stateBar_wood"), labelStyle);
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
	
	private String seasonToString(SeasonType season)
	{
		switch (season) {
		case Spring:
			return ResourceManager.stringMap.get("stateBar_spring");
		case Summer:
			return ResourceManager.stringMap.get("stateBar_summer");
		case Autumn:
			return ResourceManager.stringMap.get("stateBar_autumn");
		case Winter:
			return ResourceManager.stringMap.get("stateBar_winter");
		default:
			return null;
		}
	}
	
	public void update() {
		World world = World.getInstance(World.class);
		seasonLabel.setText(  seasonToString(world.getCurSeason()) );
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
		if(isFoodSufficient(numFood))
			numFoodLabel.setColor(Color.WHITE);
		else
			numFoodLabel.setColor(Color.RED);
		numFoodLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(numFood));
		
		int numCoat = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getResourceAmount(ResourceType.Coat);
		if(isCoatSufficient(numCoat))
			numCoatLabel.setColor(Color.WHITE);
		else
			numCoatLabel.setColor(Color.RED);
		numCoatLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(numCoat));
		
		int numWood = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getResourceAmount(ResourceType.Wood);
		numWoodLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(numWood));
		
		int numMoney = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getEnergyAmount();
		numEnergyLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(numMoney));
	}
	
	private boolean isFoodSufficient(int foodNum)
	{
		int foodNeed = (int) (ManInfo.HUNGER_DECRE_SPEED_MAX * World.SecondPerYear
								* EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan().size());
		return foodNum >= foodNeed;
	}
	
	private boolean isCoatSufficient(int coatNum)
	{
		int coatNeed = (int) (ManInfo.TEMPERATURE_DECRE_SPEED * World.SecondPerYear * 0.5f
								* EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan().size());
		return coatNum >= coatNeed;
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

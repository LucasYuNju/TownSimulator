package com.TownSimulator.ui.building.selector;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.base.IconLabelButton;
import com.TownSimulator.ui.building.adjust.BuildingAdjustBroker;
import com.TownSimulator.ui.screen.GameScreen;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildComsButton extends IconLabelButton{
	private BuildingType buildingType;
	private TextureRegion energyIcon;
	private TextureRegion woodIcon;
	//private ArrayList<String> pricesTexts;
	
	public BuildComsButton(String textureName, String labelText, BuildingType buildingType) {
		super(textureName, labelText, (int)BuildComsUI.BUTTON_TOP_MARGIN);
		setSize(BuildComsUI.BUTTON_WIDTH, BuildComsUI.BUTTON_WIDTH);
		
		this.buildingType = buildingType;
		addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.cancel();
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				
				if(BuildComsButton.this.buildingType.isMoneyProducing())
				{
					int moneyCur = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getEnergyAmount();
					int cost = Settings.mpBuildingDataMap.get(BuildComsButton.this.buildingType).cost;
					if(moneyCur < cost)
						return;
				}
				
				BuildingAdjustBroker.getInstance(BuildingAdjustBroker.class).startNewBuilding(BuildComsButton.this.buildingType);
				
				BuildComsCategoryButton.hideBuildButtonsGroup();
			}
		});
		
		if(buildingType.isMoneyProducing())
			energyIcon = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("energy");
		else
			woodIcon = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("resource_wood");
		//initPriceLabel();
	}
	
	
//	private void initPriceLabel()
//	{
//		pricesTexts = new ArrayList<String>();
//		
//		if(buildingType.isMoneyProducing())
//		{
//			
//		}
//		else
//		{
////			LabelStyle style = new LabelStyle();
////			style.font = font;
////			style.fontColor = Color.WHITE;
////			
////			priceLabel = new Label("", style);
//			float y = 0.0f;
//			for (Resource rs : Settings.normalBuildingRSMap.get(buildingType).needResource) {
////				if(ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getResourceAmount(rs.getType()) > rs.getAmount())
////					priceLabel.setColor(Color.GREEN);
////				else
////					priceLabel.setColor(Color.RED);
////				priceLabel.setText(rs.getType().toString() + " " + rs.getAmount());
////				priceLabel.setPosition(0.0f, y);
////				addActor(priceLabel);
////				y += BuildComsUI.BUTTON_TOP_MARGIN;
//				pricesTexts.add(rs.getType().toString() + " " + rs.getAmount());
//			}
//		}
//	}


	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		Color preColor = batch.getColor();
		
		float iconSize = Settings.UNIT * 0.3f;
		
		if(buildingType.isMoneyProducing())
		{
			int cost = Settings.mpBuildingDataMap.get(BuildComsButton.this.buildingType).cost;
			if (ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getEnergyAmount()
					>= cost)
				font.setColor(Color.ORANGE);
			else
				font.setColor(Color.RED);
			String str = "" + cost;
			float x = getWidth() * 0.5f - (font.getBounds(str).width + iconSize)  * 0.5f;
			Color c = getColor();
			batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
			batch.draw(energyIcon, getX() + x, getY() - GameScreen.SUBBUTTONS_TO_LABRL_MARGIN, iconSize, iconSize);
			x += iconSize;
			font.draw(batch, str, getX() + x, getY());
		}
		else
		{
			float y = 0.0f;
			for (Resource rs : Settings.normalBuildingRSMap.get(buildingType).needResource) {
				if (ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getResourceAmount(rs.getType())
						>= rs.getAmount())
					font.setColor(Color.GREEN);
				else
					font.setColor(Color.RED);
				String str = "" + rs.getAmount();
				float x = getWidth() * 0.5f - (font.getBounds(str).width + iconSize)  * 0.5f;
				Color c = getColor();
				Color woodC = GameMath.rgbaIntToColor(106, 57, 6, 255);
				woodC.a = c.a * parentAlpha;
				batch.setColor(woodC);
				batch.draw(woodIcon, getX() + x, getY() + y - GameScreen.SUBBUTTONS_TO_LABRL_MARGIN, iconSize, iconSize);
				x += iconSize;
				font.draw(batch, str, getX() + x, getY() + y);
				
				y += iconSize;
			}
		}
		batch.setColor(preColor);
	}
	
	
}

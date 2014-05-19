package com.TownSimulator.ui.building.selector;

import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.base.IconLabelButton;
import com.TownSimulator.ui.building.adjust.BuildingAdjustBroker;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildComsButton extends IconLabelButton{
	private BuildingType buildingType;
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
					int moneyCur = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getMoney();
					if(moneyCur < BuildComsButton.this.buildingType.getMoneyCost())
						return;
				}
				
				BuildingAdjustBroker.getInstance(BuildingAdjustBroker.class).startNewBuilding(BuildComsButton.this.buildingType);
			}
		});
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
		
		Color preColor = font.getColor();
		
		if(buildingType.isMoneyProducing())
		{
			if (ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getMoney()
					>= buildingType.getMoneyCost())
				font.setColor(Color.ORANGE);
			else
				font.setColor(Color.RED);
			String str = "$ " + buildingType.getMoneyCost();
			float x = getWidth() * 0.5f - font.getBounds(str).width * 0.5f;
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
				String str = rs.getType().toString() + " " + rs.getAmount();
				float x = getWidth() * 0.5f - font.getBounds(str).width * 0.5f;
				font.draw(batch, str, getX() + x, getY() + y);
				y += BuildComsUI.BUTTON_TOP_MARGIN;
			}
		}
		font.setColor(preColor);
	}
	
	
}

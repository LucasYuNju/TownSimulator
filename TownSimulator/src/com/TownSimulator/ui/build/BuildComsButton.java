package com.TownSimulator.ui.build;

import com.TownSimulator.broker.BuildBroker;
import com.TownSimulator.entity.building.BuildingType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildComsButton extends BuildingAdjustButton{
	
	public BuildComsButton(String textureName, String labelText) {
		super(textureName, labelText);
		
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
				BuildBroker.getInstance(BuildBroker.class).startNewBuilding(BuildingType.LOW_COST_HOUSE);
			}
		});
	}
}

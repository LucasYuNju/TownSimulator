package com.TownSimulator.ui.building.selector;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.base.IconLabelButton;
import com.TownSimulator.ui.building.adjust.BuildingAdjustBroker;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildComsButton extends IconLabelButton{
	private BuildingType buildingType;
	
	public BuildComsButton(String textureName, String labelText, BuildingType buildingType) {
		super(textureName, labelText, (int)BuildComsUI.BUTTON_TOP_LABEL_PAD);
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
				BuildingAdjustBroker.getInstance(BuildingAdjustBroker.class).startNewBuilding(BuildComsButton.this.buildingType);
			}
		});
	}
}

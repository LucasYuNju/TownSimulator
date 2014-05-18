package com.TownSimulator.utility.ls;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.building.selector.BuildComsCategoryButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class SaveButton extends BuildComsCategoryButton{
	
	public SaveButton() {
		super("animal_cow", "Save");
	}
	
	protected InputListener getListener() {
		return new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				LoadSave ls = new LoadSave();
				ls.save();
			}
		};
	}
	
	@Override
	public void addBuild(String textureName, String labelText, BuildingType buildingType) {
	
	}
}

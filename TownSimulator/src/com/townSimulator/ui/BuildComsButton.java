package com.townSimulator.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.townSimulator.game.GameManager;
import com.townSimulator.game.objs.BuildingType;
import com.townSimulator.game.objs.ManObject;
import com.townSimulator.game.scene.SceneManager;

public class BuildComsButton extends BuildButtonBase{
	//private String mLabelText;
	
	public BuildComsButton(String textureName, String labelText) {
		super(textureName, labelText);
		
		//mLabelText = labelText;
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
				GameManager.getInstance().startNewBuilding(BuildingType.WOOD_HOUSE);
				
				SceneManager.getInstance().addDrawableObject(new ManObject());
			}
		});
	}
}

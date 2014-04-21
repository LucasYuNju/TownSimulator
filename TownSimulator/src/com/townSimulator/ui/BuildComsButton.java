package com.townSimulator.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildComsButton extends BuildComsButtonBase{
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
			
		});
	}

}

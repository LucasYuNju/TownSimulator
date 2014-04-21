package com.townSimulator.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildComsCategoryButton extends BuildComsButtonBase{
	private BuildComsButtonsGroup mButtonsGroup;
	
	public BuildComsCategoryButton(String textureName, String labelText) {
		super(textureName, labelText);
		
		mButtonsGroup = new BuildComsButtonsGroup();
		mButtonsGroup.setVisible(false);
		addListener(new InputListener()
		{

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				mButtonsGroup.setVisible(!mButtonsGroup.isVisible());
				return true;
			}
			
		});
		addActor(mButtonsGroup);
	}	
	
	public void addBuild(String textureName, String labelText)
	{
		mButtonsGroup.addBuild(textureName, labelText);
	}
	
	public void updateLayout()
	{
		mButtonsGroup.updateLayout();
	}
}

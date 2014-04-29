package com.TownSimulator.ui.build;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildComsCategoryButton extends BuildingAdjustButton{
	private 		BuildComsButtonsGroup mButtonsGroup;
	private static 	BuildComsButtonsGroup mCurVisibleGroup;
	
	public BuildComsCategoryButton(String textureName, String labelText) {
		super(textureName, labelText);
		
		mButtonsGroup = new BuildComsButtonsGroup();
		mButtonsGroup.setVisible(false);
		addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				mButtonsGroup.updateLayout();
				if(mCurVisibleGroup != null)
					mCurVisibleGroup.setVisible(false);
				
				if(mCurVisibleGroup == mButtonsGroup)
					mCurVisibleGroup = null;
				else
				{
					mButtonsGroup.setVisible(true);
					mCurVisibleGroup = mButtonsGroup;
				}
				
			}
			
		});
		addActor(mButtonsGroup);
	}	
	
	public void addBuild(String textureName, String labelText)
	{
		mButtonsGroup.addBuild(textureName, labelText);
	}
}

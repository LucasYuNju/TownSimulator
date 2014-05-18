package com.TownSimulator.ui.building.selector;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.base.IconLabelButton;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class BuildComsCategoryButton extends IconLabelButton{
	private 		BuildComsButtonsGroup mButtonsGroup;
	private static 	BuildComsButtonsGroup mCurVisibleGroup;
	private BuildComsCategoryButtonListener listener;
	
	public interface BuildComsCategoryButtonListener
	{
		public void clicked();
	}
	
	public static void hideBuildButtonsGroup()
	{
		if(mCurVisibleGroup != null)
		{
			mCurVisibleGroup.setVisible(false);
			mCurVisibleGroup = null;
		}
	}
	
	public BuildComsCategoryButton(String textureName, String labelText) {
		super(textureName, labelText, (int)BuildComsUI.BUTTON_TOP_MARGIN);
		setSize(BuildComsUI.BUTTON_WIDTH, BuildComsUI.BUTTON_WIDTH);
		
		mButtonsGroup = new BuildComsButtonsGroup();
		mButtonsGroup.setVisible(false);
		addListener(new GdxInputListnerEx()
		{
			@Override
			public void tapped(InputEvent event, float x, float y,
					int pointer, int button) {
				listener.clicked();
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
	
	public void setListener(BuildComsCategoryButtonListener l)
	{
		this.listener = l;
	}
	
	public void addBuild(String textureName, String labelText, BuildingType buildingType)
	{
		mButtonsGroup.addBuild(textureName, labelText, buildingType);
	}
}

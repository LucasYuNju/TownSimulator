package com.TownSimulator.ui.building.adjust;

import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildingAdjustGroup extends Group{
	private static final 	float 					BUTTON_SIZE = Settings.UNIT * 0.5f;
	private 				BuildingAdjustButton 		mConfirmButton;
	private 				BuildingAdjustButton			mCancelButton;
	private 				BuildAjustUIListener 	mListener;
	
	public BuildingAdjustGroup()
	{
		initComponents();
	}
	
	private void initComponents()
	{
		setSize(BUTTON_SIZE, BUTTON_SIZE * 2.2f);
		
		mConfirmButton = new BuildingAdjustButton("button_confirm");
		mConfirmButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
		mConfirmButton.setPosition(0.0f, getHeight() - mConfirmButton.getHeight());
		mConfirmButton.addListener(new InputListener()
		{

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(mListener != null)
					mListener.confirm();
			}
			
		});
		addActor(mConfirmButton);
		
		mCancelButton = new BuildingAdjustButton("button_cancel");
		mCancelButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
		mCancelButton.setPosition(0.0f, 0.0f);
		mCancelButton.addListener(new InputListener()
		{

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(mListener != null)
					mListener.cancel();
			}
			
		});
		addActor(mCancelButton);
	}
	
	public void setListener(BuildAjustUIListener l)
	{
		mListener = l;
	}
	
	public interface BuildAjustUIListener
	{
		public void confirm();
		public void cancel();
	}
	
}

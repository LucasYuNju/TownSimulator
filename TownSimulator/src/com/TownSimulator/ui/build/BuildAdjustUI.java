package com.TownSimulator.ui.build;

import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BuildAdjustUI extends Group{
	private static final 	float 					BUTTON_SIZE = Settings.UNIT * 0.5f;
	private 				BuildButtonBase 		mConfirmButton;
	private 				BuildButtonBase			mCancelButton;
	private 				BuildAjustUIListener 	mListener;
	
	public BuildAdjustUI()
	{
		initComponents();
	}
	
	private void initComponents()
	{
		setSize(BUTTON_SIZE, BUTTON_SIZE * 2.2f);
		
		mConfirmButton = new BuildButtonBase("button_confirm", null);
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
		
		mCancelButton = new BuildButtonBase("button_cancel", null);
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

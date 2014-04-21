package com.townSimulator.ui;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.townSimulator.utility.ResourceManager;
import com.townSimulator.utility.Settings;

public class UIButton extends Button{
	private Label 		mTextLabel;
	private Drawable 	mImgUp;
	private Drawable 	mImgDown;
	private String 		mText;
	
	public UIButton(Drawable imgUp, Drawable imgDown, String text)
	{
		super(new ButtonStyle());
		mImgUp = imgUp;
		mImgDown = imgDown;
		mText = text;
		
		initComponents();
	}
	
	private void initComponents()
	{
		ButtonStyle buttonStyle = getStyle();
		buttonStyle.up = mImgUp;
		buttonStyle.down = mImgDown;
		
		if( mText != null)
			initLable();
		
	}
	
	
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		if( mTextLabel != null )
			mTextLabel.setWidth(width);
	}

	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		if( mTextLabel != null )
			mTextLabel.setHeight(height);
	}
	
	

	@Override
	public void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}

	private void initLable() 
	{
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getFont( (int)(Settings.UNIT * 0.25f) );
		labelStyle.fontColor = Color.WHITE;
		mTextLabel = new Label(mText, labelStyle);
		mTextLabel.setSize(getWidth(), getHeight());
		mTextLabel.setAlignment(Align.center);
		addActor(mTextLabel);
	}
	
	public void setText(String text)
	{
		mText = text;
	}

	
}

package com.TownSimulator.ui.base;


import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Customized button that allows to switch image when clicked
 *
 */
public class UIButton extends Button{
	private Label 			mTextLabel;
	private TextureRegion	mImgUp;
	private TextureRegion	mImgDown;
	private String 			mText;
	
	public UIButton(String imgUp, String imgDown, String text)
	{
		mImgUp = ResourceManager.getInstance(ResourceManager.class).findTextureRegion(imgUp);
		mImgDown = ResourceManager.getInstance(ResourceManager.class).findTextureRegion(imgDown);
		mText = text;
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
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont( (int)(Settings.UNIT * 0.25f) );
		labelStyle.fontColor = Color.WHITE;
		mTextLabel = new Label(mText, labelStyle);
		mTextLabel.setSize(getWidth(), getHeight());
		mTextLabel.setAlignment(Align.center);
		addActor(mTextLabel);
	}
	
	public void setText(String text)
	{
		mText = text;
		if(mTextLabel != null)
			mTextLabel.setText(text);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = this.getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
		if(isPressed())
			batch.draw(mImgDown, getX(), getY(), getWidth(), getHeight());
		else
			batch.draw(mImgUp, getX(), getY(), getWidth(), getHeight());
		
		drawChildren(batch, c.a * parentAlpha);
	}
	
	
	
}

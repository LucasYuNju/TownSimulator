package com.townSimulator.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.townSimulator.utility.ResourceManager;

class BuildComsButtonBase extends UIButton
{
	protected static 	float 					ICON_PAD 	= BuildComsUI.BUTTON_WIDTH * 0.2f;
	protected static 	TextureRegionDrawable 	mButtonUp   = new TextureRegionDrawable(ResourceManager.findTextureRegion("button_up"));
	protected static 	TextureRegionDrawable 	mButtonDown = new TextureRegionDrawable(ResourceManager.findTextureRegion("button_down"));
	protected 			TextureRegionDrawable 	mButtonImg;
	protected			Label					mTextLabel	= null;
	
	public BuildComsButtonBase(String textureName, String labelText)
	{
		super(mButtonUp, mButtonDown, null);
		setSize(BuildComsUI.BUTTON_WIDTH, BuildComsUI.BUTTON_HEIGHT);
		mButtonImg = new TextureRegionDrawable( ResourceManager.findTextureRegion(textureName) );
		
		if(labelText != null)
		{
			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getFont((int)BuildComsUI.BUTTON_TOP_LABEL_PAD);
			labelStyle.fontColor = Color.WHITE;
			mTextLabel = new Label(labelText, labelStyle);
			mTextLabel.setAlignment(Align.center);
			mTextLabel.setPosition( (getWidth() - mTextLabel.getWidth()) * 0.5f, getHeight());
			addActor(mTextLabel);
			System.out.println(labelText + "  " +mTextLabel.getWidth());
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		mButtonImg.draw(batch, 	getX() + ICON_PAD, 				getY() + ICON_PAD,
								getWidth() - ICON_PAD * 2.0f, 	getHeight() - ICON_PAD * 2.0f);
	}
	
}

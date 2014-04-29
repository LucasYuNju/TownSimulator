package com.TownSimulator.ui.base;

import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class IconLabelButton extends IconButton{
	protected	Label			mTextLabel	= null;
	protected	TextureRegion	mLabelBackgroud;
	protected	int				mFontSize = 0;
	
	public IconLabelButton(String textureName, String labelText, int fontSize) {
		super(textureName);
		mFontSize = fontSize;
		
		mLabelBackgroud = ResourceManager.getInstance(ResourceManager.class).findTextureRegion("background");
		if(labelText != null)
		{
			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont(mFontSize);
			labelStyle.fontColor = Color.WHITE;
			mTextLabel = new Label(labelText, labelStyle)
			{

				@Override
				public void draw(Batch batch, float parentAlpha) {
					Color c = getColor();
					batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
					batch.draw(mLabelBackgroud, mTextLabel.getX(), mTextLabel.getY(), mTextLabel.getWidth(), mTextLabel.getHeight());
					super.draw(batch, parentAlpha);
				}
				
				
			};
			mTextLabel.setAlignment(Align.center);
			mTextLabel.setPosition( (getWidth() - mTextLabel.getWidth()) * 0.5f, getHeight());
			addActor(mTextLabel);
		}
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		
		if(mTextLabel != null)
			mTextLabel.setPosition( (getWidth() - mTextLabel.getWidth()) * 0.5f, getHeight());
	}
	
	

}

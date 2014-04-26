package com.TownSimulator.ui.build;

import com.TownSimulator.ui.base.UIButton;
import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * 
 * Button that has background
 * better name it BackgroundedButton!!!
 * 
 */
class BuildButtonBase extends UIButton{
	protected 	TextureRegion 	mIcon;
	protected	TextureRegion	mLabelBackgroud;
	protected	Label			mTextLabel	= null;
	
	public BuildButtonBase(String textureName, String labelText)
	{
		super("button_up", "button_down", null);
		setSize(BuildComsUI.BUTTON_WIDTH, BuildComsUI.BUTTON_HEIGHT);
		mIcon 		= ResourceManager.getInstance(ResourceManager.class).findTextureRegion(textureName);
		mLabelBackgroud = ResourceManager.getInstance(ResourceManager.class).findTextureRegion("background");
		
		if(labelText != null)
		{
			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int)BuildComsUI.BUTTON_TOP_LABEL_PAD);
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
			System.out.println(labelText + "  " +mTextLabel.getWidth());
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
		float iconPad = getWidth() * 0.2f;
		batch.draw(mIcon,	getX() + iconPad, 				getY() + iconPad,
								getWidth() - iconPad * 2.0f, 	getHeight() - iconPad * 2.0f);
//		mButtonImg.draw(batch, 	getX() + iconPad, 				getY() + iconPad,
//								getWidth() - iconPad * 2.0f, 	getHeight() - iconPad * 2.0f);
	}
	
}

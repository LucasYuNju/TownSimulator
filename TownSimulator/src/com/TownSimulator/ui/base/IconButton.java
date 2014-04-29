package com.TownSimulator.ui.base;

import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IconButton extends FlipButton{
	protected 	TextureRegion 	mIcon;
	
	//protected	Label			mTextLabel	= null;
	
	public IconButton(String textureName) {
		super("button_up", "button_down", null);
		mIcon 		= ResourceManager.getInstance(ResourceManager.class).findTextureRegion(textureName);
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

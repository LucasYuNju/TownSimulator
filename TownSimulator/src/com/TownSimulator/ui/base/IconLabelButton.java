package com.TownSimulator.ui.base;

import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

public class IconLabelButton extends IconButton{
	protected	Array<Label>	labels;
	//protected	Label			mTextLabel	= null;
	protected	TextureRegion	mLabelBackgroud;
	protected	int				mFontSize = 0;
	protected	String			labelText;
	protected	BitmapFont		font;
	public static final float LABEL_BUTTON_MARGIN = 1.0f;
	
	public IconLabelButton(String textureName, String labelText, int fontSize) {
		super(textureName);
		mFontSize = fontSize;
		this.labelText = labelText;
		labels = new Array<Label>();
		font = ResourceManager.getInstance(ResourceManager.class).getFont(mFontSize);
		mLabelBackgroud = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background");
//		if(labelText != null)
//		{
//			LabelStyle labelStyle = new LabelStyle();
//			labelStyle.font = font;
//			labelStyle.fontColor = Color.WHITE;
//			mTextLabel = new Label(labelText, labelStyle)
//			{
//
//				@Override
//				public void draw(Batch batch, float parentAlpha) {
//					Color c = getColor();
//					batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
//					batch.draw(mLabelBackgroud, mTextLabel.getX(), mTextLabel.getY(), mTextLabel.getWidth(), mTextLabel.getHeight());
//					super.draw(batch, parentAlpha);
//				}
//				
//				
//			};
//			mTextLabel.setAlignment(Align.center);
//			mTextLabel.setPosition( (getWidth() - mTextLabel.getWidth()) * 0.5f, getHeight());
//			addActor(mTextLabel);
//		}
	}
	
	private void resetLabels()
	{
		if(labelText == null)
			return;
		
		for (int i = 0; i < labels.size; i++) {
			removeActor(labels.get(i));
		}
		labels.clear();
		
		float width = getWidth();
		String[] words = labelText.split(" ");
		StringBuilder strBuilder = new StringBuilder();
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = Color.WHITE;
		float widthCur = 0.0f;
		for (int i = 0; i < words.length; i++) {
			float widthNext = font.getBounds(words[i]).width;
			if(widthCur + widthNext > width)
			{
				Label label = new LabelWithBackgroud(strBuilder.toString(), labelStyle);
				label.setAlignment(Align.center);
				addActor(label);
				labels.add(label);
				strBuilder = new StringBuilder();
				strBuilder.append(words[i]);
				widthCur = widthNext;
			}
			else
			{
				if(strBuilder.length() > 0)
					strBuilder.append(" ");
				strBuilder.append(words[i]);
				widthCur += widthNext;
			}
		}
		
		if(strBuilder.length() > 0)
		{
			Label label = new LabelWithBackgroud(strBuilder.toString(), labelStyle);
			label.setAlignment(Align.center);
			addActor(label);
			labels.add(label);
		}
		
		resetLabelsPosition();
	}
	
	private void resetLabelsPosition()
	{
		for (int i = 0; i < labels.size; i++) {
			Label label = labels.get(i);
			label.setPosition( (getWidth() - label.getWidth()) * 0.5f, getHeight() + LABEL_BUTTON_MARGIN + label.getHeight() * (labels.size - i - 1));
		}
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		
		resetLabels();
//		if(mTextLabel != null)
//			mTextLabel.setPosition( (getWidth() - mTextLabel.getWidth()) * 0.5f, getHeight() + LABEL_BUTTON_MARGIN);
	}
	
	class LabelWithBackgroud extends Label
	{
		public LabelWithBackgroud(CharSequence text, LabelStyle style) {
			super(text, style);
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			Color c = getColor();
			batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
			batch.draw(mLabelBackgroud, getX(), getY(), getWidth(), getHeight());
			super.draw(batch, parentAlpha);
		}
	}

}

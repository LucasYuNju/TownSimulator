package com.TownSimulator.ui.options;

import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class InfoEntry extends Actor{
	private TextureRegion background = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background2");
	private static final float TEXT_HEIGHT = Settings.LABEL_HEIGHT * 0.6f;
	private TextureRegion icon;
	private BitmapFont font;
	private String text;
	
	public static final float HEIGHT = TEXT_HEIGHT * 2.0f + Settings.MARGIN * 3.0f;
	
	public InfoEntry(String iconName, String text)
	{
		setSize(InfoWindow.WINDOW_WIDTH - Settings.MARGIN * 2.0f, HEIGHT);
		icon = ResourceManager.getInstance(ResourceManager.class).createTextureRegion(iconName);
		this.text = text;
		font = ResourceManager.getInstance(ResourceManager.class).getFont((int)TEXT_HEIGHT);
		
		setColor(GameMath.rgbaIntToColor(0, 183,238, 255));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha * Settings.UI_ALPHA);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
		
		super.draw(batch, parentAlpha);
		
		float x = Settings.MARGIN;
		float y = Settings.MARGIN;
		float iconSize =  getHeight() - Settings.MARGIN * 2.0f;
		batch.setColor(Color.WHITE);
		batch.draw(icon, getX() + x, getY() + y, iconSize, iconSize);
		
		x += Settings.MARGIN + iconSize;
		y = getHeight() - Settings.MARGIN;
		float widthBound = getWidth() - x - Settings.MARGIN;
		StringBuilder strBuilder = new StringBuilder();
		float xCur = 0.0f;
		for (char ch : text.toCharArray()) {
			float chWidth = font.getBounds(ch + "").width;
			if(xCur + chWidth > widthBound)
			{
				font.draw(batch, strBuilder.toString(), x + getX(), y + getY());
				strBuilder = new StringBuilder(ch);
				xCur = chWidth;
				y -= TEXT_HEIGHT + Settings.MARGIN;
			}
			else
			{
				strBuilder.append(ch);
				xCur += chWidth;
			}
		}
		
		if(strBuilder.length() > 0)
			font.draw(batch, strBuilder.toString(), x + getX(), y + getY());
	}
}

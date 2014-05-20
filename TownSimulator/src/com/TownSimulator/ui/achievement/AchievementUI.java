package com.TownSimulator.ui.achievement;

import java.util.LinkedList;

import com.TownSimulator.achivement.Achievement;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AchievementUI extends Actor{
	private LinkedList<Achievement> achievesToShow;
	private static final float SHOW_TIME = 5.0f;
	private static final float SHOW_WIDTH = Settings.UNIT * 6.0f;
	private static final float SHOW_HEIGHT = Settings.UNIT * 1.3f;
	private static final float SHOW_TITLE_HEIGHT = SHOW_HEIGHT * 0.3f;
	private static final float SHOW_DESC_HEIGHT = SHOW_HEIGHT * 0.2f;
	private static final float SHOW_ALPHA = 0.7f;
	private float showTimeAccum;
	private TextureRegion background;
	private BitmapFont titleFont;
	private BitmapFont descripFont;
	
	public AchievementUI()
	{
		achievesToShow = new LinkedList<Achievement>();
		background = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("achievement_background");
		titleFont = ResourceManager.getInstance(ResourceManager.class).getFont((int)SHOW_TITLE_HEIGHT);
		descripFont = ResourceManager.getInstance(ResourceManager.class).getFont((int)SHOW_DESC_HEIGHT);
		
//		pushToShow(new FirstMPBuildingAM());
	}
	
	public void pushToShow(Achievement achieve)
	{
		achievesToShow.push(achieve);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(achievesToShow.size() > 0)
		{
			showTimeAccum += delta;
			if(showTimeAccum > SHOW_TIME)
			{
				showTimeAccum = 0.0f;
				achievesToShow.pop();
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(achievesToShow.size() > 0)
		{
			Achievement achieve = achievesToShow.peek();
			float animeTime = 0.2f;
			float animeX = Math.min( 1.0f, GameMath.lerp(0.0f, 1.0f, showTimeAccum / animeTime) );
			
			batch.setColor(1.0f, 1.0f, 1.0f, SHOW_ALPHA * animeX);
			float originX = Gdx.graphics.getWidth() * 0.5f;
			float originY = Settings.UNIT * 2.0f + SHOW_HEIGHT * 0.5f;
			float showWidth = SHOW_WIDTH * animeX;
			float showHeight = SHOW_HEIGHT * animeX;
			float x = originX - (showWidth * 0.5f);
			float y = originY - (showHeight * 0.5f);
			batch.draw(background, x, y, showWidth, showHeight);
			
			float iconSize = showHeight * 0.6f;
			x = originX - showWidth * 0.5f + showHeight * 0.5f - iconSize * 0.5f;
			y = originY - iconSize * 0.5f;
			batch.draw(achieve.getIcon(), x, y, iconSize, iconSize);
			
			if(animeX >= 1.0f)
			{
				x += Settings.MARGIN * 2.0f + iconSize;
				y = originY + showHeight * 0.5f - Settings.MARGIN * 2.0f;
				titleFont.setColor(Color.ORANGE);
				titleFont.draw(batch, achieve.getTitle(), x, y);
			
				y -= SHOW_TITLE_HEIGHT + Settings.MARGIN;
				descripFont.setColor(Color.WHITE);
				descripFont.draw(batch, achieve.getDescription(), x, y);
			}
		}
	}
	
}

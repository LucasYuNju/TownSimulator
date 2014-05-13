package com.TownSimulator.ui;

import java.util.Iterator;
import java.util.LinkedList;

import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MessageBoard extends Actor{
	private static final 	int 				lines 			= 5;
	private static final 	float 				LINE_HEIGHT 	= Settings.UNIT * 0.3f;
	private static final 	float 				MARGIN 			= Settings.MARGIN * 0.5f;
	private static final 	float 				WIDHT 			= Gdx.graphics.getWidth() * 0.4f;
	private static final 	float 				HEIGHT 			= (LINE_HEIGHT + MARGIN) * lines + MARGIN;
	private static final 	float 				BASE_ALPHA 		= Settings.UI_ALPHA;
	private static final 	float 				SHOW_TIME 		= 10.0f;
	private static final	TextureRegion 		background 		= ResourceManager.getInstance(ResourceManager.class).findTextureRegion("background");
	private static final	BitmapFont 			font 			= ResourceManager.getInstance(ResourceManager.class).getFont((int)LINE_HEIGHT);
	private 			 	float 				drawAlpha 		= 0.0f;
	private 			 	float 				showTimeAccum 	= 0.0f;
	private 				LinkedList<String> 	messages;
	
	public MessageBoard()
	{
		messages = new LinkedList<String>();
	}
	
	public void showMessage(String message)
	{
		messages.addLast(message);
		if(messages.size() > lines)
			messages.pop();
		
		drawAlpha = BASE_ALPHA;
		showTimeAccum = 0.0f;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(drawAlpha == 0.0f)
			return;
		
		drawAlpha = GameMath.lerp(BASE_ALPHA, 0.0f, showTimeAccum / SHOW_TIME);
		if( drawAlpha <= 0.0f )
		{
			drawAlpha = 0.0f;
			showTimeAccum = 0.0f;
			return;
		}
		
		showTimeAccum += delta;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(drawAlpha == 0.0f)
			return;
		
		batch.setColor(0.0f, 0.0f, 0.0f, drawAlpha);
		batch.draw(background, 0.0f, 0.0f, WIDHT, HEIGHT);
		
		font.setColor(1.0f, 1.0f, 1.0f, drawAlpha);
		float x = MARGIN;
		float y = MARGIN + font.getCapHeight();
		Iterator<String> itr = messages.descendingIterator();
		while(itr.hasNext())
		{
			String msg = itr.next();
			font.draw(batch, msg, x, y);
			y += LINE_HEIGHT + MARGIN;
		}
	}
	
}

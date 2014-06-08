package com.TownSimulator.ui;

import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class GameWinWindow extends GameOverWindow{
	private boolean bShowWinWords = true;
	private BitmapFont font;
	private Button nextButton;
	
	public GameWinWindow()
	{
		headerLabel.setText("You Win");
		font = ResourceManager.getInstance(ResourceManager.class).getFont((int)LABEL_HEIGHT);
		
		nextButton = new FlipButton("arrow_right", "arrow_right", null);
		nextButton.setSize(returnLabel.getWidth(), returnLabel.getHeight());
		nextButton.setPosition(returnLabel.getX(), returnLabel.getY());
		nextButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				bShowWinWords = false;
				nextButton.setVisible(false);
				valuesGroup.setVisible(true);
			}
			
		});
		addActor(nextButton);
		
		reset();
	}
	
	public void reset()
	{
		valuesGroup.setVisible(false);
		nextButton.setVisible(true);
		bShowWinWords = true;
		updateValues();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(bShowWinWords == false)
			return;
		
		String words = ResourceManager.stringMap.get("gameWinWindow_words");
		float x = MARGIN;
		float y = getHeight() - HEADER_HEIGHT - MARGIN * 2.0f;
		float xLeft = MARGIN;
		float xRight = getWidth() - MARGIN;
		
		StringBuilder strBuilder = new StringBuilder();
		for (char c : words.toCharArray()) {
			float cWidth = font.getBounds(c + "").width;
			if(x + cWidth > xRight)
			{
				font.draw(batch, strBuilder.toString(), getX() + xLeft, getY() + y);
				strBuilder = new StringBuilder(c);
				x = xLeft + cWidth;
				y -= LABEL_HEIGHT + MARGIN;
			}
			else
			{
				x += cWidth;
				strBuilder.append(c);
			}
		}
		
		if(strBuilder.length() > 0)
			font.draw(batch, strBuilder.toString(), getX() + xLeft, getY() + y);
	}
	
	
	
}

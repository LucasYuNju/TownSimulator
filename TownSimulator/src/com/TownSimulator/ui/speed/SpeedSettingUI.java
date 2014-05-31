package com.TownSimulator.ui.speed;

import java.util.ArrayList;

import com.TownSimulator.ui.base.IconButton;
import com.TownSimulator.ui.base.IconLabelButton;
import com.TownSimulator.ui.screen.GameScreen;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class SpeedSettingUI extends Group{
	private int speedMap[] = new int[]{1, 2, 5};
	private IconButton	initButton;	
	private ArrayList<Button> speedButtons;
	private SpeedSettingUIListener listener;
	
	public interface SpeedSettingUIListener
	{
		public void clicked();
	}
	
	public SpeedSettingUI()
	{
		speedButtons = new ArrayList<Button>();
		init();
	}
	
	public void setListener(SpeedSettingUIListener l)
	{
		this.listener = l;
	}
	
	private void init()
	{
		setSize(GameScreen.BUTTON_WIDTH, GameScreen.BUTTON_HEIGHT);
		
		initButton = new IconButton("speed_x" + speedMap[0]);
		initButton.setSize(GameScreen.BUTTON_WIDTH, GameScreen.BUTTON_HEIGHT);
		initButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				listener.clicked();
				setShowButtons( !speedButtons.get(0).isVisible() );
			}
			
		});
		initButton.setPosition(0.0f, 0.0f);
		initButton.setVisible(true);
		addActor(initButton);
		
		float buttonsWidth = speedMap.length * GameScreen.BUTTON_WIDTH + speedMap.length * GameScreen.BUTTONS_H_MARGIN;
		float x = initButton.getWidth() * 0.5f - buttonsWidth * 0.5f;
		float y = initButton.getHeight() + GameScreen.BUTTON_LABEL_HEIGHT * 2.0f + IconLabelButton.LABEL_BUTTON_MARGIN + GameScreen.SUBBUTTONS_TO_LABRL_MARGIN;
		for (int i = 0; i < speedMap.length; i++) {
			Button speedButton = new IconButton("speed_x" + speedMap[i]);
			speedButton.setSize(GameScreen.BUTTON_WIDTH, GameScreen.BUTTON_WIDTH);
			speedButton.setPosition(x, y);
			x += GameScreen.BUTTON_WIDTH + GameScreen.BUTTONS_H_MARGIN;
			speedButton.setVisible(false);
			final int speedIndex = i;
			speedButton.addListener(new InputListener()
			{
				float touchDownX = 0.0f;
				float touchDownY = 0.0f;
				int index = speedIndex;
				
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchDownX = x;
					touchDownY = y;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if(touchDownX == x && touchDownY == y)
					{
						changeSpeed(index);
						setShowButtons(false);
					}
						
				}
				
			});
			addActor(speedButton);
			speedButtons.add(speedButton);
		}
	}
	
	private void changeSpeed(int index)
	{
		Settings.gameSpeed = speedMap[index];
		initButton.setIcon("speed_x" + speedMap[index]);
	}
	
	public void setShowButtons(boolean v)
	{
		for (Button b : speedButtons) {
			b.setVisible(v);
		}
	}

//	@Override
//	public void setPosition(float x, float y) {
//		super.setPosition(x, y);
//		updateLayouts();
//	}
	
//	private void updateLayouts()
//	{
//		float originX = 
//	}
}

package com.TownSimulator.ui.options;

import java.util.ArrayList;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.driver.BufferredTask;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.base.IconButton;
import com.TownSimulator.ui.base.IconLabelButton;
import com.TownSimulator.ui.screen.GameScreen;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.ls.LoadSave;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class OptionsUI extends Group{
	private ArrayList<Button> buttons;
	
	public interface OptionsUIListner
	{
		public void clicked();
	}
	private OptionsUIListner listener;
	
	public OptionsUI()
	{
		buttons = new ArrayList<Button>();
		
		init();
		
		setShowButtons(false);
	}
	
	public void setListener(OptionsUIListner l)
	{
		this.listener = l;
	}
	
	public void setShowButtons(boolean visible)
	{
		for (Button btn : buttons) {
			btn.setVisible(visible);
		}
	}

	private void init() {
		Button optionsButton = new IconButton("button_options");
		optionsButton.setSize(GameScreen.BUTTON_WIDTH, GameScreen.BUTTON_HEIGHT);
		optionsButton.setPosition(0.0f, 0.0f);
		optionsButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				setShowButtons( !buttons.get(0).isVisible() );
				
				if(listener != null)
					listener.clicked();
			}
			
		});
		addActor(optionsButton);
		
		float x = 0.0f;
		float y = GameScreen.BUTTON_HEIGHT + GameScreen.BUTTON_LABEL_HEIGHT * 2.0f + IconLabelButton.LABEL_BUTTON_MARGIN + GameScreen.SUBBUTTONS_TO_LABRL_MARGIN;
		
		Button returnToStartButton = new IconButton("button_return");
		returnToStartButton.setSize(GameScreen.BUTTON_WIDTH, GameScreen.BUTTON_HEIGHT);
		returnToStartButton.setPosition(x, y);
		returnToStartButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				System.out.println("Reuturn");
				UIManager.getInstance(UIManager.class).getGameUI().showReturnConfirmWindow();
				setShowButtons(false);
			}
			
		});
		buttons.add(returnToStartButton);
		addActor(returnToStartButton);
		
		x -= GameScreen.BUTTON_WIDTH + GameScreen.BUTTONS_H_MARGIN;
		Button saveButton = new IconButton("button_save");
		saveButton.setSize(GameScreen.BUTTON_WIDTH, GameScreen.BUTTON_HEIGHT);
		saveButton.setPosition(x, y);
		saveButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				setShowButtons(false);
				String saveRunningTips = ResourceManager.stringMap.get("save_tips_running");
				UIManager.getInstance(UIManager.class).getGameUI().getTipsWindow().showTips(saveRunningTips);
				
				Driver.getInstance(Driver.class).pushToBufferTask(new BufferredTask() {
					
					@Override
					public void run() {
						LoadSave.getInstance().save();
						
						Driver.getInstance(Driver.class).ignoreNextUpdate();
						String saveFinishTips = ResourceManager.stringMap.get("save_tips_finish");
						float tipsVisibleTime = 5.0f;
						UIManager.getInstance(UIManager.class).getGameUI().getTipsWindow().showTips(saveFinishTips, tipsVisibleTime);
					}
				});
			}
			
		});
		buttons.add(saveButton);
		addActor(saveButton);
		
		x -= GameScreen.BUTTON_WIDTH + GameScreen.BUTTONS_H_MARGIN;
		Button shareButton = new IconButton("button_share");
		shareButton.setSize(GameScreen.BUTTON_WIDTH, GameScreen.BUTTON_HEIGHT);
		shareButton.setPosition(x, y);
		shareButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				System.out.println("Share");
				setShowButtons(false);
				UIManager.getInstance(UIManager.class).getGameUI().showShareWindow();
			}
			
		});
		buttons.add(shareButton);
		addActor(shareButton);
		
		x -= GameScreen.BUTTON_WIDTH + GameScreen.BUTTONS_H_MARGIN;
		Button homeButton = new IconButton("button_home");
		homeButton.setSize(GameScreen.BUTTON_WIDTH, GameScreen.BUTTON_HEIGHT);
		homeButton.setPosition(x, y);
		homeButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				System.out.println("Home");
				setShowButtons(false);
				
				for (Building b : EntityInfoCollector.getInstance(EntityInfoCollector.class).getBuildings(BuildingType.Warehouse)) {
					if(b.isDestroyable() == false)
					{
						float destX = b.getAABBWorld(QuadTreeType.DRAW).getCenterX();
						float destY = b.getAABBWorld(QuadTreeType.DRAW).getCenterY();
						CameraController.getInstance(CameraController.class).moveTo(destX, destY);
					}
				}
			}
			
		});
		buttons.add(homeButton);
		addActor(homeButton);
		
		x -= GameScreen.BUTTON_WIDTH + GameScreen.BUTTONS_H_MARGIN;
		Button infoButton = new IconButton("button_info");
		infoButton.setSize(GameScreen.BUTTON_WIDTH, GameScreen.BUTTON_HEIGHT);
		infoButton.setPosition(x, y);
		infoButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				System.out.println("Info");
				setShowButtons(false);
				
				UIManager.getInstance(UIManager.class).getGameUI().showInfoWindow();
			}
			
		});
		buttons.add(infoButton);
		addActor(infoButton);
	}
	
}

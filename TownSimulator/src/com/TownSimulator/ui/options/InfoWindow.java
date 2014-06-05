package com.TownSimulator.ui.options;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.base.BaseWindow;
import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.ui.building.selector.BuildComsUI;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Settings.MPData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class InfoWindow extends BaseWindow{
	public static final float WINDOW_WIDTH = Settings.UNIT * 4.0f;
	public static final float WINDOW_HEIGHT = Settings.UNIT * 6.0f;
	private ScrollPane infoScrollPane;
	
	public InfoWindow() {
		initUI();
		
		setVisible(false);
	}
	
	private void initUI()
	{
		FlipButton closeButton = new FlipButton("button_cancel", "button_cancel", null);
		closeButton.setSize(Settings.UNIT * 0.3f, Settings.UNIT * 0.3f);
		closeButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				InfoWindow.this.setVisible(false);
				infoScrollPane.setScrollPercentY(0.0f);
			}
			
		});
		addActor(closeButton);
		
		ScrollPaneStyle paneStyle = new ScrollPaneStyle();
		TextureRegionDrawable knobDraw = new TextureRegionDrawable(ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background2"));
	    paneStyle.vScrollKnob = knobDraw;
		
		infoScrollPane = new ScrollPane(new InfoDisplayPanel(), paneStyle);
		infoScrollPane.setScrollingDisabled(true, false);
		infoScrollPane.setSize(WINDOW_WIDTH - Settings.MARGIN * 2.0f, WINDOW_HEIGHT - closeButton.getHeight() - Settings.MARGIN * 2.0f);
		infoScrollPane.setScrollBarPositions(false, true);
		infoScrollPane.setColor(1.0f, 1.0f, 1.0f, Settings.UI_ALPHA);
		infoScrollPane.setupFadeScrollBars(0.7f, 3.0f);
		addActor(infoScrollPane);
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setPosition(Gdx.graphics.getWidth() * 0.5f - getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f - getHeight() * 0.5f);
		
		closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
		infoScrollPane.setPosition(Settings.MARGIN, Settings.MARGIN);
	}
	
	class InfoDisplayPanel extends Group
	{
		public InfoDisplayPanel()
		{
			initUI();
		}
		
		private void initUI()
		{
			BuildingType[] buildings = BuildingType.values();
			
			int entryCnt = buildings.length + 4;
			setSize( WINDOW_WIDTH - Settings.UNIT * 2.0f, InfoEntry.HEIGHT * entryCnt + Settings.MARGIN * (entryCnt + 1) );
			
			float x = 0.0f;
			float y = getHeight() - Settings.MARGIN - InfoEntry.HEIGHT;
			InfoEntry hungerEntry = new InfoEntry("man_state_hungry", ResourceManager.stringMap.get("manInfo_hunger"));
			hungerEntry.setPosition(x, y);
			addActor(hungerEntry);
			y -= Settings.MARGIN + InfoEntry.HEIGHT;
			
			InfoEntry coldEntry = new InfoEntry("man_state_cold", ResourceManager.stringMap.get("manInfo_cold"));
			coldEntry.setPosition(x, y);
			addActor(coldEntry);
			y -= Settings.MARGIN + InfoEntry.HEIGHT;
			
			InfoEntry healthEntry = new InfoEntry("man_state_sick", ResourceManager.stringMap.get("manInfo_health"));
			healthEntry.setPosition(x, y);
			addActor(healthEntry);
			y -= Settings.MARGIN + InfoEntry.HEIGHT;
			
			InfoEntry happinessEntry = new InfoEntry("man_state_depressed", ResourceManager.stringMap.get("manInfo_happiness"));
			happinessEntry.setPosition(x, y);
			addActor(happinessEntry);
			y -= Settings.MARGIN + InfoEntry.HEIGHT;
			
			for (BuildingType type : buildings) {
				String iconName = BuildComsUI.buildingIconMap.get(type);
				String info = null;
				if(type.isMoneyProducing())
				{
					MPData mpData = Settings.mpBuildingDataMap.get(type);
					float energySpeed =  mpData.produceAmount / mpData.timeInterval;
					info = ResourceManager.stringMap.get("buildingInfo_mp_template").replace("@", energySpeed + "");
				}
				else
					info = ResourceManager.buildingInfoMap.get(type);
				
				InfoEntry entry = new InfoEntry(iconName, info);
				entry.setPosition(x, y);
				addActor(entry);
				y -= Settings.MARGIN + InfoEntry.HEIGHT;
			}
			
		}
	}

}

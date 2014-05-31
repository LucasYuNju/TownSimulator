package com.TownSimulator.ui;

import java.util.ArrayList;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.World;
import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.share.ShareType;
import com.TownSimulator.utility.share.UMHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class GameOverWindow extends Group{
	private static final float WINDOW_WIDTH = Gdx.graphics.getWidth() * 0.4f;
	private static final float WINDOW_HEIGHT = Gdx.graphics.getHeight() * 0.4f;
	private static final float MARGIN = Settings.MARGIN * 2.0f;
	private static final float ICON_HEIGHT = WINDOW_HEIGHT * 0.2f;
	private static final float LABEL_HEIGHT = WINDOW_HEIGHT * 0.1f;
	private Label livedTimeValueLabel;
	private Label maxManValueLabel;
	private Label maxEnergyValueLabel;
	private TextureRegion background;
	private ArrayList<FlipButton> shareButtons;
	private String[] shareIconsMap = new String[]{"share_sina", "share_weixin", "share_renren", "share_qqzone"};
	private ShareType[] shareTypesMap = new ShareType[]{ShareType.Sina, ShareType.WeiXin, ShareType.RenRen, ShareType.QQZone};
	private Label returnLabel;
	
	public GameOverWindow()
	{
		background = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background");
		
		init();
	}
	
	private void init()
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setPosition(Gdx.graphics.getWidth() * 0.5f - WINDOW_WIDTH * 0.5f, Gdx.graphics.getHeight() * 0.5f - WINDOW_HEIGHT * 0.5f);
		setVisible(false);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont( (int)LABEL_HEIGHT );
		labelStyle.fontColor = Color.WHITE;
		
		float x = MARGIN;
		float y = getHeight() - MARGIN - LABEL_HEIGHT; 
		
		float labelWidth_0 = (WINDOW_WIDTH - MARGIN * 2.0f) * 0.4f;
		float labelWidth_1 = (WINDOW_WIDTH - MARGIN * 2.0f) * 0.6f;
		Label livedTimeLabel = new Label(ResourceManager.stringMap.get("gameOverWindow_livedTime"), labelStyle);
		livedTimeLabel.setSize(labelWidth_0, LABEL_HEIGHT);
		livedTimeLabel.setPosition(x, y);
		livedTimeLabel.setColor(Color.ORANGE);
		addActor(livedTimeLabel);
		
		x += labelWidth_0 + MARGIN;
		livedTimeValueLabel = new Label("", labelStyle);
		livedTimeValueLabel.setSize(labelWidth_1, LABEL_HEIGHT);
		livedTimeValueLabel.setPosition(x, y);
		addActor(livedTimeValueLabel);
		
		x = MARGIN;
		y -= MARGIN + LABEL_HEIGHT;
		Label maxManLabel = new Label(ResourceManager.stringMap.get("gameOverWindow_maxMan"), labelStyle);
		maxManLabel.setSize(labelWidth_0, LABEL_HEIGHT);
		maxManLabel.setPosition(x, y);
		maxManLabel.setColor(Color.ORANGE);
		addActor(maxManLabel);
		
		x += labelWidth_0 + MARGIN;
		maxManValueLabel = new Label("", labelStyle);
		maxManValueLabel.setSize(labelWidth_1, LABEL_HEIGHT);
		maxManValueLabel.setPosition(x, y);
		addActor(maxManValueLabel);
		
		x = MARGIN;
		y -= MARGIN + LABEL_HEIGHT;
		Label maxEnergyLabel = new Label(ResourceManager.stringMap.get("gameOverWindow_maxEnergy"), labelStyle);
		maxEnergyLabel.setSize(labelWidth_0, LABEL_HEIGHT);
		maxEnergyLabel.setPosition(x, y);
		maxEnergyLabel.setColor(Color.ORANGE);
		addActor(maxEnergyLabel);
		
		x += labelWidth_0 + MARGIN;
		maxEnergyValueLabel = new Label("", labelStyle);
		maxEnergyValueLabel.setSize(labelWidth_1, LABEL_HEIGHT);
		maxEnergyValueLabel.setPosition(x, y);
		addActor(maxEnergyValueLabel);
		
		shareButtons = new ArrayList<FlipButton>();
		float iconsHPad = (WINDOW_WIDTH - shareIconsMap.length * ICON_HEIGHT) / 5.0f;
		x = iconsHPad;
		y -= MARGIN + ICON_HEIGHT;
		for (int i = 0; i < shareIconsMap.length; i++) {
			FlipButton btn = new FlipButton(shareIconsMap[i], shareIconsMap[i], null);
			btn.setPosition(x, y);
			btn.setSize(ICON_HEIGHT, ICON_HEIGHT);
			final int index = i;
			btn.addListener(new GdxInputListnerEx()
			{
				int btnIndex = index;
				@Override
				public void tapped(InputEvent event, float x, float y,
						int pointer, int button) {
//					Gdx.graphics.setContinuousRendering(false);
					UMHelper.shareUtil.share(shareTypesMap[btnIndex]);
				}
				
			});
			addActor(btn);
			shareButtons.add(btn);
			
			x += iconsHPad + ICON_HEIGHT;
		}
		
		returnLabel = new Label(ResourceManager.stringMap.get("gameOverWindow_return"), labelStyle);
		returnLabel.setSize(labelStyle.font.getBounds(returnLabel.getText()).width, LABEL_HEIGHT);
		returnLabel.setColor(Color.ORANGE);
		returnLabel.setPosition(getWidth() - returnLabel.getWidth() - MARGIN, MARGIN);
		returnLabel.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				GameOverWindow.this.setVisible(false);
				Driver.getInstance(Driver.class).returnToStartUI();
			}
			
		});
		addActor(returnLabel);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, Settings.UI_ALPHA);
		
		batch.draw(background, getX(), getY(), WINDOW_WIDTH, WINDOW_HEIGHT);
		
		super.draw(batch, parentAlpha);
	}
	
	public void updateValues()
	{
		int livedDays = World.getInstance(World.class).getLivedDays();
		int year = livedDays / 365;
		int day = livedDays - year * 365;
		String timeStr = "";
		if(year > 0)
			timeStr += year + ResourceManager.stringMap.get("gameOverWindow_year");
		timeStr += day + ResourceManager.stringMap.get("gameOverWindow_day");
		livedTimeValueLabel.setText(timeStr);
		
		int maxManCnt = EntityInfoCollector.getInstance(EntityInfoCollector.class).getMaxManCnt();
		maxManValueLabel.setText(maxManCnt + "");
		
		int maxEnergy = ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getMaxEnergyAmount();
		maxEnergyValueLabel.setText(maxEnergy + "");
	}

}

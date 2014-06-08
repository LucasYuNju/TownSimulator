package com.TownSimulator.ui;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.World;
import com.TownSimulator.ui.options.ShareUIComs;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class GameOverWindow extends Group{
	protected static final float WINDOW_WIDTH = Gdx.graphics.getWidth() * 0.4f;
	protected static final float WINDOW_HEIGHT = Gdx.graphics.getHeight() * 0.5f;
	protected static final float MARGIN = Settings.MARGIN * 2.0f;
	public    static final float ICON_HEIGHT = Settings.UNIT * 0.8f;
	protected static final float LABEL_HEIGHT = WINDOW_HEIGHT * 0.08f;
	protected static final float HEADER_HEIGHT = LABEL_HEIGHT * 1.2f;
	protected Group valuesGroup;
	protected Label livedTimeValueLabel;
	protected Label maxManValueLabel;
	protected Label maxEnergyValueLabel;
	protected TextureRegion background;
	protected Label returnLabel;
	protected ShareUIComs shareComs;
	protected Label headerLabel;
	
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
		
		valuesGroup = new Group();
		valuesGroup.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		addActor(valuesGroup);
		
		float x = 0.0f;
		float y = getHeight() - MARGIN - HEADER_HEIGHT;
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont( (int)HEADER_HEIGHT );
		labelStyle.fontColor = Color.WHITE;
		
		headerLabel = new Label("Game Over", labelStyle);
		headerLabel.setSize(WINDOW_WIDTH, LABEL_HEIGHT);
		headerLabel.setPosition(x, y);
		headerLabel.setColor(Color.ORANGE);
		headerLabel.setAlignment(Align.center);
		addActor(headerLabel);
		
		x = MARGIN;
		y -= MARGIN + LABEL_HEIGHT;
		
		labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont( (int)LABEL_HEIGHT );
		labelStyle.fontColor = Color.WHITE;
		
		float labelWidth_0 = (WINDOW_WIDTH - MARGIN * 2.0f) * 0.4f;
		float labelWidth_1 = (WINDOW_WIDTH - MARGIN * 2.0f) * 0.6f;
		Label livedTimeLabel = new Label(ResourceManager.stringMap.get("gameOverWindow_livedTime"), labelStyle);
		livedTimeLabel.setSize(labelWidth_0, LABEL_HEIGHT);
		livedTimeLabel.setPosition(x, y);
		livedTimeLabel.setColor(Color.ORANGE);
		valuesGroup.addActor(livedTimeLabel);
		
		x += labelWidth_0 + MARGIN;
		livedTimeValueLabel = new Label("", labelStyle);
		livedTimeValueLabel.setSize(labelWidth_1, LABEL_HEIGHT);
		livedTimeValueLabel.setPosition(x, y);
		valuesGroup.addActor(livedTimeValueLabel);
		
		x = MARGIN;
		y -= MARGIN + LABEL_HEIGHT;
		Label maxManLabel = new Label(ResourceManager.stringMap.get("gameOverWindow_maxMan"), labelStyle);
		maxManLabel.setSize(labelWidth_0, LABEL_HEIGHT);
		maxManLabel.setPosition(x, y);
		maxManLabel.setColor(Color.ORANGE);
		valuesGroup.addActor(maxManLabel);
		
		x += labelWidth_0 + MARGIN;
		maxManValueLabel = new Label("", labelStyle);
		maxManValueLabel.setSize(labelWidth_1, LABEL_HEIGHT);
		maxManValueLabel.setPosition(x, y);
		valuesGroup.addActor(maxManValueLabel);
		
		x = MARGIN;
		y -= MARGIN + LABEL_HEIGHT;
		Label maxEnergyLabel = new Label(ResourceManager.stringMap.get("gameOverWindow_maxEnergy"), labelStyle);
		maxEnergyLabel.setSize(labelWidth_0, LABEL_HEIGHT);
		maxEnergyLabel.setPosition(x, y);
		maxEnergyLabel.setColor(Color.ORANGE);
		valuesGroup.addActor(maxEnergyLabel);
		
		x += labelWidth_0 + MARGIN;
		maxEnergyValueLabel = new Label("", labelStyle);
		maxEnergyValueLabel.setSize(labelWidth_1, LABEL_HEIGHT);
		maxEnergyValueLabel.setPosition(x, y);
		valuesGroup.addActor(maxEnergyValueLabel);
		
//		shareButtons = new ArrayList<FlipButton>();
//		float iconsHPad = (WINDOW_WIDTH - shareIconsMap.length * ICON_HEIGHT) / (float)(shareIconsMap.length + 1);
//		x = iconsHPad;
//		y -= MARGIN + ICON_HEIGHT;
//		for (int i = 0; i < shareIconsMap.length; i++) {
//			FlipButton btn = new FlipButton(shareIconsMap[i], shareIconsMap[i], null);
//			btn.setPosition(x, y);
//			btn.setSize(ICON_HEIGHT, ICON_HEIGHT);
//			final int index = i;
//			btn.addListener(new GdxInputListnerEx()
//			{
//				int btnIndex = index;
//				@Override
//				public void tapped(InputEvent event, float x, float y,
//						int pointer, int button) {
////					Gdx.graphics.setContinuousRendering(false);
//					UMHelper.shareUtil.share(shareTypesMap[btnIndex]);
//				}
//				
//			});
//			addActor(btn);
//			shareButtons.add(btn);
//			
//			x += iconsHPad + ICON_HEIGHT;
//		}
		shareComs = new ShareUIComs();
		shareComs.setSize(getWidth(), ICON_HEIGHT);
		shareComs.setPosition(0.0f, MARGIN * 2.0f + LABEL_HEIGHT);
		valuesGroup.addActor(shareComs);
		
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
		valuesGroup.addActor(returnLabel);
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

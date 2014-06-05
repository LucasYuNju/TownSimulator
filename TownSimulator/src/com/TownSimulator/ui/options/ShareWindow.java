package com.TownSimulator.ui.options;

import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class ShareWindow extends Group{
	private static final float WINDOW_WIDTH = Gdx.graphics.getWidth() * 0.4f;
//	private static final float WINDOW_HEIGHT = Gdx.graphics.getHeight() * 0.4f;
	private FlipButton closeButton;
	private ShareUIComs shareComs;
	private TextureRegion background = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background");
	
	public ShareWindow()
	{
		initUI();
		setVisible(false);
	}

	private void initUI() {
		closeButton = new FlipButton("button_cancel", "button_cancel", null);
		closeButton.setSize(Settings.UNIT * 0.5f, Settings.UNIT * 0.5f);
		closeButton.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				ShareWindow.this.setVisible(false);
			}
			
		});
		addActor(closeButton);
		
		shareComs = new ShareUIComs();
		shareComs.setSize(WINDOW_WIDTH, Settings.UNIT * 0.8f);
		addActor(shareComs);
		
		setSize(WINDOW_WIDTH, Settings.MARGIN * 2.0f + closeButton.getHeight() + shareComs.getHeight());
		setPosition(Gdx.graphics.getWidth() * 0.5f - getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f - getHeight() * 0.5f);
		
		closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
		shareComs.setPosition(0.0f, Settings.MARGIN);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(1.0f, 1.0f, 1.0f, parentAlpha * Settings.UI_ALPHA);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
		
		super.draw(batch, parentAlpha);
	}
	
	
	
}

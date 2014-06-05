package com.TownSimulator.ui.options;

import com.TownSimulator.driver.Driver;
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

public class ReturnConfirmWindow extends Group{
	private TextureRegion background = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background");
	
	public ReturnConfirmWindow()
	{
		initReturnWindow();
		
		setVisible(false);
	}

	private void initReturnWindow() {
		
		float buttonHeight = Settings.UNIT * 0.5f;
		LabelStyle buttonStyle = new LabelStyle();
		buttonStyle.fontColor = Color.WHITE;
		buttonStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int)(buttonHeight));
		
		Label confirm = new Label(ResourceManager.stringMap.get("returnConfirm_confirm"), buttonStyle);
		confirm.setColor(Color.ORANGE);
		confirm.setSize(buttonHeight * 2.0f, buttonHeight);
		
		confirm.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				ReturnConfirmWindow.this.setVisible(false);
				Driver.getInstance(Driver.class).returnToStartUI();
			}
			
		});
		addActor(confirm);
		
		Label cancel = new Label(ResourceManager.stringMap.get("returnConfirm_cancel"), buttonStyle);
		cancel.setColor(Color.ORANGE);
		cancel.setSize(buttonHeight * 2.0f, buttonHeight);
		
		cancel.addListener(new GdxInputListnerEx()
		{

			@Override
			public void tapped(InputEvent event, float x, float y, int pointer,
					int button) {
				ReturnConfirmWindow.this.setVisible(false);
			}
			
		});
		addActor(cancel);
		
		float labelHeight = Settings.LABEL_HEIGHT * 0.8f;
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.fontColor = Color.WHITE;
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int)(labelHeight));
		Label textLabel = new Label(ResourceManager.stringMap.get("returnConfirm_text"), labelStyle);
		textLabel.setSize(labelStyle.font.getBounds(textLabel.getText()).width, Settings.LABEL_HEIGHT);
		addActor(textLabel);
		
		float width = Math.max(confirm.getWidth() * 2.0f + Settings.MARGIN * 2.0f, textLabel.getWidth()) + Settings.MARGIN * 4.0f;
		float height = Settings.MARGIN * 3.0f + textLabel.getHeight() + confirm.getHeight();
		setSize(width, height);
		setPosition(Gdx.graphics.getWidth() * 0.5f - getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f - getHeight() * 0.5f);
		
		confirm.setPosition(getWidth() * 0.25f - confirm.getWidth() * 0.5f, Settings.MARGIN);
		cancel.setPosition(getWidth() * 0.75f - cancel.getWidth() * 0.5f, Settings.MARGIN);
		textLabel.setPosition(width * 0.5f - textLabel.getWidth() * 0.5f, confirm.getY() + confirm.getHeight() + Settings.MARGIN);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(1.0f, 1.0f, 1.0f, parentAlpha * Settings.UI_ALPHA);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
		
		super.draw(batch, parentAlpha);
	}
	
	
}

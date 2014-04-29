package com.TownSimulator.ui.base;

import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.io.InputMgrListener;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ScreenUIBase implements DriverListener, InputMgrListener{
	protected final boolean 	SHOW_FPS = true;
	protected Stage		 		mStage;
	protected Label 			mFpsLabel;
	
	public ScreenUIBase()
	{
		mStage = new Stage(new ScreenViewport());
		if( SHOW_FPS )
		{
			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont( (int)(Settings.UNIT * 0.25f) );
			labelStyle.fontColor = Color.YELLOW;
			mFpsLabel = new Label("FPS", labelStyle);
			mFpsLabel.setPosition(0, Gdx.graphics.getHeight() - mFpsLabel.getHeight());
			mStage.addActor(mFpsLabel);
		}
	}
	
	public void render()
	{
		mStage.draw();
	}
	
	@Override
	public void update(float deltaTime)
	{
		mStage.act(deltaTime);
		if(SHOW_FPS)
		{
			mFpsLabel.setText("FPS " + Gdx.graphics.getFramesPerSecond());
		}
	}

	@Override
	public void dispose()
	{
		mStage.dispose();
	}
	@Override
	public void resume()
	{
		mFpsLabel.getStyle().font = ResourceManager.getInstance(ResourceManager.class).getFont( (int)(Settings.UNIT * 0.25f) );
		System.out.println("CCCCCCC");
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public boolean touchDown(float screenX, float screenY, int pointer,
			int button) {
		return mStage.touchDown((int)screenX, (int)screenY, pointer, button);
	}

	@Override
	public boolean touchUp(float screenX, float screenY, int pointer, int button) {
		return mStage.touchUp((int)screenX, (int)screenY, pointer, button);
	}

	@Override
	public void touchDragged(float screenX, float screenY, float deltaX,
			float deltaY, int pointer) {
		mStage.touchDragged((int)screenX, (int)screenY, pointer);
	}
	
}

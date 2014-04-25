package com.TownSimulator.ui;

import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ScreenUIBase implements InputProcessor, Screen{
	protected final boolean 	SHOW_FPS = true;
	protected 		Stage 		mStage;
	protected 		Label 		mFpsLabel;
	
	public ScreenUIBase()
	{
		mStage = new Stage(new ScreenViewport());
		if( SHOW_FPS )
		{
			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getFont( (int)(Settings.UNIT * 0.25f) );
			labelStyle.fontColor = Color.YELLOW;
			mFpsLabel = new Label("FPS", labelStyle);
			mFpsLabel.setPosition(0, Gdx.graphics.getHeight() - mFpsLabel.getHeight());
			mStage.addActor(mFpsLabel);
		}
	}

	public void update(float deltaTime)
	{
		mStage.act(deltaTime);
		if(SHOW_FPS)
		{
			mFpsLabel.setText("FPS " + Gdx.graphics.getFramesPerSecond());
		}
	}
	
	public void draw()
	{
		mStage.draw();
	}
	
	public void dispose()
	{
		mStage.dispose();
	}
	
	public void resume()
	{
		mFpsLabel.getStyle().font = ResourceManager.getFont( (int)(Settings.UNIT * 0.25f) );
		System.out.println("CCCCCCC");
	}

	public InputProcessor getInputProcessor()
	{
		return this;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return mStage.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return mStage.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
//		mStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
//		if( SHOW_FPS )
//		{
//			LabelStyle labelStyle = new LabelStyle();
//			labelStyle.font = ResourceManager.getFont( (int)(Settings.UNIT * 0.25f) );
//			labelStyle.fontColor = Color.YELLOW;
//			mFpsLabel = new Label("FPS", labelStyle);
//			mFpsLabel.setPosition(0, Gdx.graphics.getHeight() - mFpsLabel.getHeight());
//			mStage.addActor(mFpsLabel);
//		}
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
}

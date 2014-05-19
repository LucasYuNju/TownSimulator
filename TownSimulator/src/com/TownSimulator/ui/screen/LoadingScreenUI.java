package com.TownSimulator.ui.screen;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.entity.World;
import com.TownSimulator.map.Map;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.particle.ParticleControl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LoadingScreenUI extends ScreenUIBase{
	private LoadingUIListener 	listener;
	//private Object				lockObj;
	//private boolean				bLoadFinish = false;
	private float				LABEL_HEIGHT = Settings.LABEL_HEIGHT;
	private float				BAR_HEIGHT = LABEL_HEIGHT * 0.8f;
	private BitmapFont			loadingTextFont = ResourceManager.getInstance(ResourceManager.class).getFont((int)LABEL_HEIGHT);
	private BitmapFont			numTextFont = ResourceManager.getInstance(ResourceManager.class).getFont((int)BAR_HEIGHT);
	private TextureRegion		background = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background");
	
	public interface LoadingUIListener
	{
		public void loadingFinish();
	}
	
	public LoadingScreenUI()
	{
		//lockObj = new Object();
	}
	
	public void setListener(LoadingUIListener l)
	{
		this.listener = l;
	}
	
	public void startLoading()
	{
//		new Thread()
//		{
//
//			@Override
//			public void run() {
//				Map.getInstance(Map.class).init(100);
//				Driver.getInstance(Driver.class).init();
//				World.getInstance(World.class).init();
//				Renderer.getInstance(Renderer.class).setRenderScene(true);
//				
//				synchronized (lockObj) {
//					bLoadFinish = true;
//				}
//			}
//			
//		}.start();
		Map.getInstance(Map.class).init(100);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
//		boolean finish = false;
//		synchronized (lockObj) {
//			finish = bLoadFinish;
//		}
//		
//		if(finish && listener != null)
//			listener.loadingFinish();
		if( !Map.getInstance(Map.class).load() )
		{
			Driver.getInstance(Driver.class).init();
			World.getInstance(World.class).init();
			ParticleControl.getInstance(ParticleControl.class).init();
			Renderer.getInstance(Renderer.class).setRenderScene(true);
			
			Settings.backgroundColor = Settings.gameNormalGroundColor.cpy();
			//if(listener != null)
			listener.loadingFinish();
		}
		
	}

	@Override
	public void render() {
		super.render();
		Batch batch = mStage.getSpriteBatch();
		batch.begin();
		
		String loadText = "Loading";
		float x = Gdx.graphics.getWidth() * 0.5f - loadingTextFont.getBounds(loadText).width * 0.5f;
		float y = Gdx.graphics.getHeight() * 0.5f + loadingTextFont.getCapHeight();
		loadingTextFont.setColor(Color.WHITE);
		loadingTextFont.draw(batch, loadText, x, y);
		
		float process = Map.getInstance(Map.class).getProcess();
		float barWidth = Gdx.graphics.getWidth() * 0.4f;
		//float barHeight = LABEL_HEIGHT * 0.6f;
		x = Gdx.graphics.getWidth() * 0.5f - barWidth * 0.5f;
		y = Gdx.graphics.getHeight() * 0.5f - BAR_HEIGHT - Settings.MARGIN;
		batch.draw(background, x, y, barWidth * process, BAR_HEIGHT);
		
		String numText = "" + (int)(process * 100.0f);
		x = Gdx.graphics.getWidth() * 0.5f - numTextFont.getBounds(numText).width * 0.5f;
		y += numTextFont.getCapHeight();
		numTextFont.setColor(Color.WHITE);
		numTextFont.draw(batch, numText, x, y);
		
		batch.end();
	}
	
	
}

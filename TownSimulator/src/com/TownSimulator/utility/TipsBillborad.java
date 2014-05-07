package com.TownSimulator.utility;

import java.util.HashMap;
import java.util.Iterator;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.render.RendererListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
/**
 *显示资源或人口增加的提示信息 
 *
 */
public class TipsBillborad {
	private static Array<TipsBillborad> tipsActiveList = new Array<TipsBillborad>();
	private static Array<TipsBillborad> tipsInActiveList = new Array<TipsBillborad>();
	//private static int tipsAllocIndex = 0;
	private static BitmapFont font = ResourceManager.getInstance(ResourceManager.class).getFont( (int)(Settings.UNIT * 0.6f) );
	//private static final float PAD = Settings.UNIT * 0.1f;
	private static SpriteBatch spriteBatch = new SpriteBatch();
	private static HashMap<String, TextureRegionAllocContainer> textureMap = new HashMap<String, TextureRegionAllocContainer>();
	//private static OrthographicCamera screenCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
	static class TextureRegionAllocContainer
	{
		private Array<TextureRegion> draws;
		private int allocIndex = 0;
		private String textureName;
		
		public TextureRegionAllocContainer(String textureName)
		{
			draws = new Array<TextureRegion>();
			this.textureName = textureName;
		}
		
		public TextureRegion alloc()
		{
			if(allocIndex >= draws.size)
				draws.add(ResourceManager.getInstance(ResourceManager.class).findTextureRegion(textureName));
			
			return draws.get(allocIndex++);
		}
		
		public void reset()
		{
			allocIndex = 0;
		}
	}
	
	static
	{
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
		{

			@Override
			public void update(float deltaTime) {
				updateTips(deltaTime);
			}		
			
		});
		
		Renderer.getInstance(Renderer.class).addListener(new RendererListener() {
			
			@Override
			public void renderEnded() {
				renderTips();
			}
			
			@Override
			public void renderBegined() {
			}
		});
		
		//screenCamera.setToOrtho(false);
	}
	
	private static void updateTips(float deltaTime)
	{
		for (TipsBillborad tip : tipsActiveList) {
			tip.update(deltaTime);
		}
	}
	
	private static void renderTips()
	{
		spriteBatch.setProjectionMatrix(CameraController.getInstance(CameraController.class).getCameraCombined());
		//spriteBatch.setProjectionMatrix(screenCamera.combined);
		spriteBatch.begin();
		for (TipsBillborad tip : tipsActiveList) {
			tip.render();
		}
		spriteBatch.end();
		
		Iterator<String> itr = textureMap.keySet().iterator();
		while(itr.hasNext())
		{
			textureMap.get(itr.next()).reset();
		}
	}
	
	private static TextureRegion allocTextureRegion(String textureName)
	{
		if( !textureMap.containsKey(textureName) )
			textureMap.put(textureName, new TextureRegionAllocContainer(textureName));
		
		return textureMap.get(textureName).alloc();
	}
	
	private static TipsBillborad allocTipsBillboard()
	{
		if (tipsInActiveList.size == 0)
			tipsInActiveList.add(new TipsBillborad());

		TipsBillborad tip = tipsInActiveList.pop();
		tipsActiveList.add(tip);

		return tip;
	}
	
	private float livingTime = 5.0f;
	private float life = 0.0f; 
	private Vector2 velocity;
	private Color color;
	private float oringinX;
	private float oringinY;
	private String text;
	//private BitmapFont font;
	
	private TipsBillborad()
	{
		velocity = new Vector2();
		color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		//font = ResourceManager.getInstance(ResourceManager.class).getFontNoManaged( (int)(Settings.UNIT * 0.5f) );
	}
	
	public void setLife(float life)
	{
		this.livingTime = life;
	}
	
	public void setVelocity(float x, float y)
	{
		velocity.x = x;
		velocity.y = y;
	}
	
	public void update(float deltaTime)
	{
		if(livingTime <= 0)
			return;
		
		life += deltaTime;
		
		if(life > livingTime)
		{
			tipsInActiveList.add(this);
			tipsActiveList.removeValue(this, false);
			return;
		}
		
		oringinX += velocity.x * deltaTime;
		oringinY += velocity.y * deltaTime;
		color.a = 1.0f - life / livingTime;
	}
	
	public void render()
	{
//		System.out.println(font.getBounds(" ").width);
		float textWidth = font.getBounds(text).width;
		//float textHeight = font.getBounds(text).height;
//		TextureRegion background = allocTextureRegion("grid");
//		spriteBatch.setColor(0.0f, 0.0f, 0.0f, color.a * 0.7f);
//		spriteBatch.draw(background,
//				oringinX - textWidth * 0.5f - PAD, oringinY - PAD,
//				textWidth + PAD * 2.0f, textHeight + PAD * 2.0f);
//		spriteBatch.setColor(Color.WHITE);
		//font.get
		font.setColor(color);
		font.draw(spriteBatch, text, oringinX - textWidth * 0.5f, oringinY + font.getCapHeight());
		font.setColor(Color.WHITE);
	}
	
	public static void showTips(String text, float originX, float originY, Color color)
	{
		//Vector3 pos = new Vector3(originX, originY, 0.0f);
		//CameraController.getInstance(CameraController.class).worldToScreen(pos);
		TipsBillborad tip = allocTipsBillboard();
		tip.life = 0.0f;
		tip.text = text;
		tip.oringinX = originX;
		tip.oringinY = originY;
		tip.velocity.x = 0.0f;
		tip.velocity.y = Settings.UNIT * 0.2f;
		tip.color.r = color.r;
		tip.color.g = color.g;
		tip.color.b = color.b;
	}
	
	public static float getTipsHeight()
	{
		return font.getCapHeight();
	}
}

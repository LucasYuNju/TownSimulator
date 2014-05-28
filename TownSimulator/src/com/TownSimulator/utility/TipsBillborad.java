package com.TownSimulator.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
/**
 *显示资源或人口增加的提示信息 
 *
 */
public class TipsBillborad {
	private static List<TipsBillborad> tipsActiveList;
	private static List<TipsBillborad> tipsInActiveList;
	private static BitmapFont font;
	private static SpriteBatch spriteBatch;
	private static HashMap<String, TextureRegionAllocContainer> textureMap = new HashMap<String, TipsBillborad.TextureRegionAllocContainer>();
	//private static OrthographicCamera screenCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
	static class TextureRegionAllocContainer
	{
		private List<TextureRegion> draws;
		private int allocIndex = 0;
		private String textureName;
		
		public TextureRegionAllocContainer(String textureName)
		{
			draws = new ArrayList<TextureRegion>();
			this.textureName = textureName;
		}
		
		public TextureRegion alloc()
		{
			if(allocIndex >= draws.size())
				draws.add(ResourceManager.getInstance(ResourceManager.class).createTextureRegion(textureName));
			
			return draws.get(allocIndex++);
		}
		
		public void reset()
		{
			allocIndex = 0;
		}
	}
	
//	{
//		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
//		{
//			
//			@Override
//			public void update(float deltaTime) {
//				updateTips(deltaTime);
//			}
//
//			@Override
//			public void dispose() {
//				spriteBatch.dispose();
//				
//			}
//		});
//		
//		Renderer.getInstance(Renderer.class).addListener(new RendererListener() {
//			
//			@Override
//			public void renderEnded() {
//				renderTips();
//			}
//			
//			@Override
//			public void renderBegined() {
//			}
//		});
//		
//		//screenCamera.setToOrtho(false);
//	}
	
	public static void initStatic()
	{
		tipsActiveList = new ArrayList<TipsBillborad>();
		tipsInActiveList = new ArrayList<TipsBillborad>();
		font = ResourceManager.getInstance(ResourceManager.class).getFont( (int)(Settings.UNIT * 0.6f) );
		spriteBatch = new SpriteBatch();
		textureMap.clear();
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
		{
			private static final long serialVersionUID = 2939432378098238642L;

			@Override
			public void update(float deltaTime) {
				updateTips(deltaTime);
			}

			@Override
			public void dispose() {
				spriteBatch.dispose();
				tipsActiveList.clear();
				tipsInActiveList.clear();
			}
		});
		
		Renderer.getInstance(Renderer.class).addListener(new RendererListener() {
			private static final long serialVersionUID = -3659126136217212295L;

			@Override
			public void renderEnded() {
				renderTips();
			}
			
			@Override
			public void renderBegined() {
			}
		});
	}
	
	private static void updateTips(float deltaTime)
	{
		List<TipsBillborad> listCpy = new ArrayList<TipsBillborad>(tipsActiveList);
		for (TipsBillborad tip : listCpy) {
			tip.update(deltaTime);
		}
	}
	
	private static void renderTips()
	{
		spriteBatch.setProjectionMatrix(CameraController.getInstance(CameraController.class).getCameraCombined());
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
	
//	private static TextureRegion allocTextureRegion(String textureName)
//	{
//		if( !textureMap.containsKey(textureName) )
//			textureMap.put(textureName, new TextureRegionAllocContainer(textureName));
//		return textureMap.get(textureName).alloc();
//	}
	
	private static TipsBillborad allocTipsBillboard()
	{
		if (tipsInActiveList.size() == 0)
			tipsInActiveList.add(new TipsBillborad());

		TipsBillborad tip = tipsInActiveList.remove(tipsInActiveList.size() - 1);
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
			tipsActiveList.remove(this);
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

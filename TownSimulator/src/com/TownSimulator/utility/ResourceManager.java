package com.TownSimulator.utility;


import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ResourceManager {
	private static HashMap<String, Texture>		mTexturesMap;
	private static HashMap<Integer, BitmapFont> mFontsMap;
	private static FreeTypeFontGenerator		mFontGenerator;
	private static AssetManager					mAssetsManager;
	
	
	public static void loadResource()
	{
		//mObjImgsTexture = new TextureAtlas(Gdx.files.internal("data/imgs.atlas"));
		mTexturesMap = new HashMap<String, Texture>();
		mAssetsManager = new AssetManager();
		mFontsMap = new HashMap<Integer, BitmapFont>();
		mFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("data/visitor1.ttf"));
	}
	
	public static void reloadResources()
	{
		while( !mAssetsManager.update() );

		System.out.println("AAAAAAAAA");
	}
	
	public static Sprite createSprite(String textureName)
	{
		if(!mTexturesMap.containsKey(textureName))
			loadTexture(textureName);
			
		return new Sprite(mTexturesMap.get(textureName));
	}
	
	private static void loadTexture(String textureName)
	{
		mAssetsManager.load("data/" + textureName + ".png", Texture.class);
		mAssetsManager.finishLoading();
		mTexturesMap.put(textureName, mAssetsManager.get("data/" + textureName + ".png", Texture.class));
//		mTexturesMap.put(textureName, new Texture(Gdx.files.internal("data/" + textureName + ".png")));
	}
	
	public static TextureRegion findTextureRegion(String textureName)
	{
		if(!mTexturesMap.containsKey(textureName))
			loadTexture(textureName);
			
		return new TextureRegion(mTexturesMap.get(textureName));
	}
	
	public static void dispose()
	{
		mAssetsManager.clear();
		mTexturesMap.clear();
		
		Iterator<Integer> it_font = mFontsMap.keySet().iterator();
		while(it_font.hasNext())
		{
			mFontsMap.get(it_font.next()).dispose();
		}
		
		mFontGenerator.dispose();
	}
	
	@SuppressWarnings("deprecation")
	public static BitmapFont getFont(int size)
	{
		if(mFontsMap.containsKey(size))
			return mFontsMap.get(size);
		else
		{
			//mAssetsManager.setLoader(type, loader);
			mFontsMap.put(size, mFontGenerator.generateFont(size));
			return mFontsMap.get(size);
		}
	}
	
}

package com.townSimulator.utility;


import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ResourceManager {
	private static HashMap<String, Texture>		mTexturesMap = new HashMap<String, Texture>();
	//private static TextureAtlas 				mObjImgsTexture;
	private static HashMap<Integer, BitmapFont> mFontsMap;
	private static FreeTypeFontGenerator		mFontGenerator;
	
	public static void loadResource()
	{
		//mObjImgsTexture = new TextureAtlas(Gdx.files.internal("data/imgs.atlas"));
		mFontsMap = new HashMap<Integer, BitmapFont>();
		mFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("data/visitor1.ttf"));
	}
	
	public static Sprite createSprite(String textureName)
	{
		if(!mTexturesMap.containsKey(textureName))
			loadTexture(textureName);
			
		return new Sprite(mTexturesMap.get(textureName));
	}
	
	private static void loadTexture(String textureName)
	{
		mTexturesMap.put(textureName, new Texture(Gdx.files.internal("data/" + textureName + ".png")));
	}
	
	public static TextureRegion findTextureRegion(String textureName)
	{
		if(!mTexturesMap.containsKey(textureName))
			loadTexture(textureName);
			
		return new TextureRegion(mTexturesMap.get(textureName));
	}
	
	public static void dispose()
	{
		//mObjImgsTexture.dispose();
		Iterator<String> it = mTexturesMap.keySet().iterator();
		while(it.hasNext())
		{
			mTexturesMap.get(it.next()).dispose();
		}
	}
	
	public static BitmapFont getFont(int size)
	{
		if(mFontsMap.containsKey(size))
			return mFontsMap.get(size);
		else
		{
			mFontsMap.put(size, mFontGenerator.generateFont(size));
			return mFontsMap.get(size);
		}
	}
	
}

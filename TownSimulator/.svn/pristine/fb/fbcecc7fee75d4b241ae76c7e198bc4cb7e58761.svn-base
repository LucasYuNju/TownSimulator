package com.townSimulator.utility;


import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ResourceManager {
	private static TextureAtlas 				mObjImgsTexture;
	private static HashMap<Integer, BitmapFont> mFontsMap;
	private static FreeTypeFontGenerator		mFontGenerator;
	
	public static void loadResource()
	{
		mObjImgsTexture = new TextureAtlas(Gdx.files.internal("data/imgs.atlas"));
		mFontsMap = new HashMap<Integer, BitmapFont>();
		mFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("data/visitor1.ttf"));
	}
	
	public static Sprite createSprite(String textureName)
	{
		return mObjImgsTexture.createSprite(textureName);
	}
	
	public static TextureRegion findTextureRegion(String textureName)
	{
		return mObjImgsTexture.findRegion(textureName);
	}
	
	public static void dispose()
	{
		mObjImgsTexture.dispose();
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

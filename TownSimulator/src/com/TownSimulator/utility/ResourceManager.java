package com.TownSimulator.utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.utility.ls.SerializedSprite;
import com.TownSimulator.utility.ls.SerializedTextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ResourceManager extends Singleton implements Serializable{
	private static final long serialVersionUID = -1444327838173945178L;
	private transient Map<String, Texture>		mTexturesMap;
	private transient Map<Integer, BitmapFont> 	mFontsMap;
	private transient Map<String, Sound>        mSoundsMap;
	private transient FreeTypeFontGenerator			mFontGenerator;
	private transient AssetManager					mAssetsManager;
	
	public ResourceManager()
	{
		initialize();
		preLoadSounds();
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
		{
			private static final long serialVersionUID = 2146745872421402154L;

			@Override
			public void dispose() {
				mAssetsManager.clear();
				mTexturesMap.clear();
				
				Iterator<Integer> it_font = mFontsMap.keySet().iterator();
				while(it_font.hasNext())
				{
					mFontsMap.get(it_font.next()).dispose();
				}
				
				Iterator<String> it_sound = mSoundsMap.keySet().iterator();
				while(it_sound.hasNext())
				{
					mSoundsMap.get(it_sound.next()).dispose();
				}
				mSoundsMap.clear();
				mFontGenerator.dispose();
			}

			@Override
			public void resume() {
				while( !mAssetsManager.update() );
			}
		});
	}
	
	private void initialize()
	{
		mTexturesMap = new HashMap<String, Texture>();
		mSoundsMap=new HashMap<String, Sound>();
		mAssetsManager = new AssetManager();
		mFontsMap = new HashMap<Integer, BitmapFont>();
		mFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("data/visitor1.ttf"));
	}
	
	public SerializedSprite createSprite(Sprite sp) {
		if(sp instanceof SerializedSprite) {
			SerializedSprite serializedSprite = (SerializedSprite) sp;
			return createSprite(serializedSprite.getTextureName());
		}
		return null;
	}
	
	public SerializedSprite createSprite(String textureName)
	{
		if( textureName == null)
			return null;
		if(!mTexturesMap.containsKey(textureName))
			loadTexture(textureName);
		
		return new SerializedSprite(mTexturesMap.get(textureName), textureName);
	}

	public SerializedTextureRegion createTextureRegion(String textureName)
	{
		if(!mTexturesMap.containsKey(textureName))
			loadTexture(textureName);
			
		return new SerializedTextureRegion(mTexturesMap.get(textureName), textureName);
	}
	
	public Texture loadTexture(String textureName)
	{
		System.out.println("Load " + textureName);
		mAssetsManager.load("data/" + textureName + ".png", Texture.class);
		mAssetsManager.finishLoading();
		mTexturesMap.put(textureName, mAssetsManager.get("data/" + textureName + ".png", Texture.class));
		return mTexturesMap.get(textureName);
	}

	private void preLoadSounds(){
		loadSound("voice/sound/cave3.wav");
		loadSound("voice/sound/game.mp3");
		//loadSound("voice/sound/rain.mp3");
		loadSound("voice/sound/loop01.wav");
	}
	
	private void loadSound(String soundName){
		mAssetsManager.load(soundName, Sound.class);
		mAssetsManager.finishLoading();		
		mSoundsMap.put(soundName, mAssetsManager.get(soundName, Sound.class));
	}
	
	public Sound getSound(String soundName){
		if(!mSoundsMap.containsKey(soundName)){
			loadSound(soundName);
		}
		return mSoundsMap.get(soundName);
	}
	
	@SuppressWarnings("deprecation")
	public BitmapFont getFont(int size)
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
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		initialize();
	}
}

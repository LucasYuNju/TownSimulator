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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class ResourceManager extends Singleton implements Serializable{
	private static final long serialVersionUID = -1444327838173945178L;
	private transient Map<String, Texture>		mTexturesMap;
	private transient Map<Integer, BitmapFont> 	mFontsMap;
	private transient Map<String, Sound>        mSoundsMap;
	private transient FreeTypeFontGenerator		mFontGenerator;
	private transient AssetManager				mAssetsManager;
	
	public static HashMap<String, String>		stringMap = new HashMap<String, String>();		
	private static String 						usedStr = null;
	static
	{
		stringMap.put("stateBar_wood", "木材");
		stringMap.put("stateBar_children", "小孩");
		stringMap.put("stateBar_adult", "成人");
		stringMap.put("stateBar_food", "食物");
		
		stringMap.put("stateBar_spring", "春天");
		stringMap.put("stateBar_summer", "夏天");
		stringMap.put("stateBar_autumn", "秋天");
		stringMap.put("stateBar_winter", "冬天");
		
		stringMap.put("buildSelect_livingHouse", "住房");
		stringMap.put("buildSelect_lowCostHouse", "廉租房");
		stringMap.put("buildSelect_apartment", "公寓");
		
		stringMap.put("buildSelect_food", "食物");
		stringMap.put("buildSelect_farm", "农场");
		stringMap.put("buildSelect_ranch", "牧场");
		
		stringMap.put("buildSelect_infrastructure", "基础设施");
		stringMap.put("buildSelect_warehouse", "仓库");
		stringMap.put("buildSelect_fellingHouse", "伐木场");
		stringMap.put("buildSelect_hospital", "医院");
		stringMap.put("buildSelect_bar", "酒吧");
		stringMap.put("buildSelect_coatFactory", "制衣厂");
		stringMap.put("buildSelect_school", "学校");
		stringMap.put("buildSelect_constructionCompany", "建筑公司");
		
		stringMap.put("buildSelect_energy", "能量");
		stringMap.put("buildSelect_potato", "土豆");
		stringMap.put("buildSelect_mouseWheel", "跑步机");
		stringMap.put("buildSelect_factory", "工厂");
		stringMap.put("buildSelect_storm", "风暴");
		stringMap.put("buildSelect_vocalno", "火山");
		stringMap.put("buildSelect_blackHole", "黑洞");
		
		stringMap.put("gameUI_save", "保存");
		
		stringMap.put("startUI_start", "开始");
		stringMap.put("startUI_load", "读取存档");
		stringMap.put("startUI_quit", "退出");
		
		stringMap.put("achivement_title_begginer", "初学者");
		stringMap.put("achivement_desc_begginer", "建造了第一个能量建筑");
		stringMap.put("achivement_title_level_0", "闪亮的灯泡");
		stringMap.put("achivement_desc_level_0", "你的能量可以点亮一颗灯泡 (1,000)");
		stringMap.put("achivement_title_level_1", "电气时代");
		stringMap.put("achivement_desc_level_1", "进入电气时代 (10,000)");
		stringMap.put("achivement_title_level_2", "地心引力");
		stringMap.put("achivement_desc_level_2", "你的能量可以让火箭发射至宇宙 (1,000,000)");
		stringMap.put("achivement_title_level_3", "宇宙爆炸");
		stringMap.put("achivement_desc_level_3", "你的能量可以引发宇宙爆炸 (100,000,000)");
		
		stringMap.put("dropDown_empty", "<空>");
	
		stringMap.put("animal_cow", "牛");
		stringMap.put("animal_sheep", "羊");
		stringMap.put("animal_caonima", "草泥马");
		
		stringMap.put("crop_wheat", "小麦");
		stringMap.put("crop_mimosa", "含羞草");
		stringMap.put("crop_rose", "玫瑰");
		
		stringMap.put("farmView_state", "状态");
		stringMap.put("farmView_curCrop", "当前作物");
		stringMap.put("farmView_sowCrop", "种植作物");
		
		stringMap.put("schoolUI_student", "学生");
		
		stringMap.put("rs_wood", "木材");
		stringMap.put("rs_wheat", "小麦");
		stringMap.put("rs_mimosa", "含羞草");
		stringMap.put("rs_rose", "玫瑰");
		stringMap.put("rs_corn", "玉米");
		stringMap.put("rs_meat", "肉");
		stringMap.put("rs_fur", "皮毛");
		stringMap.put("rs_coat", "衣服");
		
		stringMap.put("building_warning_noWorker", "没有工人");
		stringMap.put("building_warning_noAnimalSelected", "没有选择养殖牲畜");
		stringMap.put("building_warning_noCropSelected", "没有选择耕种作物");
		stringMap.put("building_warning_noFur", "没有皮毛(牧场出产)");
		
		stringMap.put("mp_desc_potato", "你的土豆疯狂生长，产生能量");
		stringMap.put("mp_desc_mouseWheel", "人力发电机");
		stringMap.put("mp_desc_factory", "能量工厂");
		stringMap.put("mp_desc_storm", "风暴赐予你能量");
		stringMap.put("mp_desc_vocalno", "火山爆发能产生巨大的能量");
		stringMap.put("mp_desc_blackHole", "黑洞能够吸收各种物质，释放巨大的能量");
		
		stringMap.put("gameOverWindow_livedTime", "存货时间");
		stringMap.put("gameOverWindow_day", "天");
		stringMap.put("gameOverWindow_year", "年");
		stringMap.put("gameOverWindow_maxMan", "最大人口");
		stringMap.put("gameOverWindow_maxEnergy", "最大能量");
		stringMap.put("gameOverWindow_return", "返回");
		
		Iterator<String> itr = stringMap.keySet().iterator();
		StringBuilder strBuilder = new StringBuilder(FreeTypeFontGenerator.DEFAULT_CHARS);
		while(itr.hasNext())
		{
			String str = stringMap.get(itr.next());
			for (char c : str.toCharArray()) {
				if( strBuilder.indexOf(c + "") >= 0 )
					continue;
				else
					strBuilder.append(c);
			}
		}
		usedStr = strBuilder.toString();
	}
	
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
		mFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("data/MSYH.TTC"));
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
	
	public Texture getTexture(String textureName) {
		if(!mTexturesMap.containsKey(textureName))
			loadTexture(textureName);
		return mTexturesMap.get(textureName);
	}
	
	private Texture loadTexture(String textureName)
	{
//		System.out.println("Load " + textureName);
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
	
	public BitmapFont getFont(int size)
	{
		if(mFontsMap.containsKey(size))
			return mFontsMap.get(size);
		else
		{
			FreeTypeFontParameter param = new FreeTypeFontParameter();
			param.characters = usedStr;
			param.size = size;
			mFontsMap.put(size, mFontGenerator.generateFont(param));
			return mFontsMap.get(size);
		}
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		initialize();
	}
}

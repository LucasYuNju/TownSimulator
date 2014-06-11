package com.TownSimulator.utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.building.BuildingType;
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
	public static HashMap<BuildingType, String> buildingInfoMap = new HashMap<BuildingType, String>();
	private static String 						usedStr = null;
	static
	{
		stringMap.put("stateBar_wood", "木材");
		stringMap.put("stateBar_children", "小孩");
		stringMap.put("stateBar_adult", "成人");
		stringMap.put("stateBar_food", "食物");
		stringMap.put("stateBar_coat", "衣服");
		
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
		stringMap.put("gameOverWindow_return", "退出");
		
		stringMap.put("gameWinWindow_words", "你居然通关了，人生还是有很多美好的东西的，有这个时间和毅力不如去干点更有意义的事吧");
		
		stringMap.put("NameMenu","姓名");
		stringMap.put("GenderMenu","性别");
		stringMap.put("AgeMenu","年龄");
		stringMap.put("HealthPointMenu","健康值");
		
		stringMap.put("returnConfirm_text", "未保存的进度将会丢失，确定要退出吗？");
		stringMap.put("returnConfirm_confirm", "确认");
		stringMap.put("returnConfirm_cancel", "取消");
		
		stringMap.put("save_tips_running", "保存中");
		stringMap.put("save_tips_finish", "保存成功");
		
		stringMap.put("buildingInfo_lowCostHouse", "廉租房供居民居住，可以增加人口");
		buildingInfoMap.put(BuildingType.LowCostHouse, stringMap.get("buildingInfo_lowCostHouse"));
		
		stringMap.put("buildingInfo_apartment", "公寓供居民居住，可以增加人口，居民的幸福度减少得更慢");
		buildingInfoMap.put(BuildingType.Apartment, stringMap.get("buildingInfo_apartment"));
		
		stringMap.put("buildingInfo_warehouse", "仓库能够储存资源，没有住房的居民会前往仓库消耗资源");
		buildingInfoMap.put(BuildingType.Warehouse, stringMap.get("buildingInfo_warehouse"));
		
		stringMap.put("buildingInfo_farmHouse", "农田可以提供食物，更多的农民可以提高产量");
		buildingInfoMap.put(BuildingType.FarmHouse, stringMap.get("buildingInfo_farmHouse"));
		
		stringMap.put("buildingInfo_fellingHouse", "伐木小屋可以砍伐一定范围内的树木获得木材");
		buildingInfoMap.put(BuildingType.FellingHouse, stringMap.get("buildingInfo_fellingHouse"));
		
		stringMap.put("buildingInfo_coatFactory", "制衣厂可以用毛皮制作衣服，衣服可以帮助居民抵御寒冷");
		buildingInfoMap.put(BuildingType.CoatFactory, stringMap.get("buildingInfo_coatFactory"));
		
		stringMap.put("buildingInfo_school", "学校可以让未成年的居民入学，居民将会获得更高的工作效率");
		buildingInfoMap.put(BuildingType.School, stringMap.get("buildingInfo_school"));
		
		stringMap.put("buildingInfo_hospital", "医院可以帮助生病的居民恢复健康");
		buildingInfoMap.put(BuildingType.Hospital, stringMap.get("buildingInfo_hospital"));
		
		stringMap.put("buildingInfo_bar", "酒吧可以帮助居民提升幸福度");
		buildingInfoMap.put(BuildingType.Bar, stringMap.get("buildingInfo_bar"));
		
		stringMap.put("buildingInfo_ranch", "牧场可以出产毛皮和少量的肉");
		buildingInfoMap.put(BuildingType.Ranch, stringMap.get("buildingInfo_ranch"));
		
		stringMap.put("buildingInfo_constructionCompany", "建筑公司可以建造新的建筑");
		buildingInfoMap.put(BuildingType.ConstrctionCompany, stringMap.get("buildingInfo_constructionCompany"));
		
		stringMap.put("buildingInfo_mp_template", "每秒产生 @ 点能量");
		
		stringMap.put("manInfo_hunger", "居民的饥饿度会随时间减少，过低居民会饿死");
		stringMap.put("manInfo_cold", "居民的保暖度会在冬天随时间减少，过低居民会冻死");
		stringMap.put("manInfo_health", "居民的偶尔会生病，健康度过低的居民会死去");
		stringMap.put("manInfo_happiness", "居民的幸福度会随时间减少，过低会影响居民的工作效率");
		
		stringMap.put("manDie_hungry", "死于饥饿");
		stringMap.put("manDie_cold", "死于寒冷");
		stringMap.put("manDie_sick", "死于疾病");
		stringMap.put("manDie_old", "老死了");
		
		stringMap.put("ShareContent", "游戏测试");
		
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
		loadSound("voice/sound/bar.mp3");
		loadSound("voice/sound/build.mp3");
		loadSound("voice/sound/bull.mp3");
		loadSound("voice/sound/click.mp3");
		loadSound("voice/sound/school.mp3");
		loadSound("voice/sound/sheep.mp3");
		loadSound("voice/sound/felling.mp3");
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

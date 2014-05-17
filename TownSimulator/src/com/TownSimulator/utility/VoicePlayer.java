package com.TownSimulator.utility;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class VoicePlayer extends Singleton{
	private static Music bgmmusic;
	private Array<MusicPlayItem> musicsList;
	
	private static String musicPath="voice/music/";
	private static String soundPath="voice/sound/";
	
	private VoicePlayer(){
		//playMusic("start.mp3");
		musicsList=new Array<MusicPlayItem>();
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){

			@Override
			public void update(float deltaTime) {
				VoicePlayer.this.update(deltaTime);
			}

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				VoicePlayer.this.dispose();
			}
			
		});		
	}
	/**
	 * 用于播放或者更换bgm音乐
	 * @param musicName
	 */
	public void playBgmMusic(String musicName){
		if(bgmmusic!=null){
			bgmmusic.dispose();
		}
		bgmmusic=Gdx.audio.newMusic(Gdx.files.internal(musicPath+musicName));
		bgmmusic.setLooping(true);
		bgmmusic.play();
	}
	
	/**
	 * 播放极短的音效，播放的素材要在sound下，且要预加载
	 * @param soundName
	 */
	public void playSound(String soundName){
		ResourceManager.getInstance(ResourceManager.class).getSound(soundPath+soundName).play();
	}
	
	/**
	 * 平时工作播放持续一段时间的音效，播放的素材要在music下，且要预加载
	 * @param musicName
	 * @param duringTime
	 */
	public void playMusicForDuringTime(String musicName,float duringTime){
		Music music=Gdx.audio.newMusic(Gdx.files.internal(musicPath+musicName));
		MusicPlayItem musicPlayItem=new MusicPlayItem(music, duringTime);
		musicsList.add(musicPlayItem);
		music.setLooping(true);
		music.play();
	}
	
	public class MusicPlayItem{
		public Music music;
		public float duringTime;
		
		public MusicPlayItem(Music music,float duringTime){
			this.music=music;
			this.duringTime=duringTime;
		}
	}
	
	private void update(float deltaTime){
		for(MusicPlayItem musicPlayItem:musicsList){
			musicPlayItem.duringTime-=deltaTime;
			System.out.println("time!!"+musicPlayItem.duringTime);
			if(musicPlayItem.duringTime<=0){
				musicPlayItem.music.stop();
				if(musicPlayItem.music!=null){
					musicPlayItem.music.dispose();					
				}
				musicsList.removeValue(musicPlayItem, false);
			}
		}
	}
	
	private void dispose(){
		if(bgmmusic!=null){
			bgmmusic.dispose();
		}
	}
	
}

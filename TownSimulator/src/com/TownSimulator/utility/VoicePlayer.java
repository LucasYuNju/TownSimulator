package com.TownSimulator.utility;

import java.util.HashMap;
import java.util.Iterator;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class VoicePlayer{
	private static Music music;
	private static ResourceManager resourceManager;
	
	private static String musicPath="voice/music/";
	private static String soundPath="voice/sound/";
	
	static
	{
		resourceManager=ResourceManager.getInstance(ResourceManager.class);
		//resourceManager.loadSound(soundPath+"cave3.wav");
		playMusic("start.mp3");
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				VoicePlayer.this.dispose();
			}
			
		});
	}
//	public VoicePlayer(){
//		resourceManager=ResourceManager.getInstance(ResourceManager.class);
//		playMusic("start.mp3");
//		
//		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){
//
//			@Override
//			public void dispose() {
//				// TODO Auto-generated method stub
//				VoicePlayer.this.dispose();
//			}
//			
//		});
//	}
	
	public static void playMusic(String musicName){
		if(music!=null){
			music.dispose();
		}
		music=Gdx.audio.newMusic(Gdx.files.internal(musicPath+musicName));
		music.setLooping(true);
		music.play();
	}
	
	public static void playSound(String soundName){
		Iterator<String> it_sound = resourceManager.getmSoundsMap().keySet().iterator();
		//System.out.println("bb"+resourceManager.getmSoundsMap().size());
		while(it_sound.hasNext())
		{
			System.out.println("1111soundIterator: "+it_sound.next());
		}
		resourceManager.getSound(soundPath+soundName).play();
//		resourceManager.getAssetManager().get(soundPath+soundName, Sound.class).play();
	}
	
	private static void playSound(String soundName,float duringTime){
		
	}
	
	public static void dispose(){
		if(music!=null){
			music.dispose();
		}
	}
	
}

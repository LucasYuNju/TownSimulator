package com.TownSimulator.utility;

import java.util.HashMap;
import java.util.Iterator;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class VoicePlayer extends Singleton{
	private static Music music;
	
	private static String musicPath="voice/music/";
	private static String soundPath="voice/sound/";
	
	public VoicePlayer(){
		playMusic("start.mp3");
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){

			@Override
			public void update(float deltaTime) {
				// TODO Auto-generated method stub
				super.update(deltaTime);
			}

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				VoicePlayer.this.dispose();
			}
			
		});		
	}
	
//	static
//	{
//		resourceManager=ResourceManager.getInstance(ResourceManager.class);
//		//resourceManager.loadSound(soundPath+"cave3.wav");
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
		ResourceManager.getInstance(ResourceManager.class).getSound(soundPath+soundName).play();
	}
	
	private static void playSound(String soundName,float duringTime){
		
	}
	
	private void update(){
		
	}
	
	public static void dispose(){
		if(music!=null){
			music.dispose();
		}
	}
	
}

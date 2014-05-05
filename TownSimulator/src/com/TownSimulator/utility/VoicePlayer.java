package com.TownSimulator.utility;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class VoicePlayer extends Singleton{
	private static Music music;
	
	private static String musicPath="voice/music/";
	private static String soundPath="voice/sound/";
	
	private VoicePlayer(){
		playMusic("start.mp3");
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){

			@Override
			public void update(float deltaTime) {
				VoicePlayer.this.update();
			}

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				VoicePlayer.this.dispose();
			}
			
		});		
	}
	
	public void playMusic(String musicName){
		if(music!=null){
			music.dispose();
		}
		music=Gdx.audio.newMusic(Gdx.files.internal(musicPath+musicName));
		music.setLooping(true);
		music.play();
	}
	

	public void playSound(String soundName){
		ResourceManager.getInstance(ResourceManager.class).getSound(soundPath+soundName).play();
	}
	
	public void playSound(String soundName,float duringTime){
		
	}
	
	private void update(){
		
	}
	
	private void dispose(){
		if(music!=null){
			music.dispose();
		}
	}
	
}

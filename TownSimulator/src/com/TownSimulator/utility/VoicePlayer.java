package com.TownSimulator.utility;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class VoicePlayer extends Singleton{
	private static Music music;
	private Array<SoundPlayItem> soundsList;
	
	private static String musicPath="voice/music/";
	private static String soundPath="voice/sound/";
	
	private VoicePlayer(){
		//playMusic("start.mp3");
		soundsList=new Array<SoundPlayItem>();
		
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
	
	public void playMusic(String musicName){
		if(music!=null){
			music.dispose();
		}
		music=Gdx.audio.newMusic(Gdx.files.internal(musicPath+musicName));
		music.setLooping(true);
		music.play();
	}
	
	public class SoundPlayItem{
		public long soundID;
		public Sound sound;
		public float duringTime;
		
		public SoundPlayItem(Sound sound,float duringTime,long id){
			this.sound=sound;
			this.duringTime=duringTime;
			this.soundID=id;
		}
	}

	public void playSound(String soundName){
		ResourceManager.getInstance(ResourceManager.class).getSound(soundPath+soundName).play();
	}
	
	public void playSound(String soundName,float duringTime){
		Sound sound=ResourceManager.getInstance(ResourceManager.class).getSound(soundPath+soundName);
		long id=sound.play();
		soundsList.add(new SoundPlayItem(sound,duringTime,id));
		sound.setLooping(id, true);
		sound.loop();
		//sound.play();
	}
	
	private void update(float deltaTime){
		for(SoundPlayItem soundPlayItem:soundsList){
			soundPlayItem.duringTime-=deltaTime;
			if(soundPlayItem.duringTime<=0){
				soundPlayItem.sound.stop(soundPlayItem.soundID);
				soundsList.removeValue(soundPlayItem, false);
			}
		}
	}
	
	private void dispose(){
		if(music!=null){
			music.dispose();
		}
	}
	
}

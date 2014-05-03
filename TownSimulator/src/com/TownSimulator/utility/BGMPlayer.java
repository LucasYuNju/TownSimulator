package com.TownSimulator.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

//音乐名字要带后缀
public class BGMPlayer{
	private MusicType musicType;
	private Music music;
	private String musicPathString="sounds/bgm/";
	
	public BGMPlayer(){
		musicType=null;
		music=null;
	}
	
	public BGMPlayer(MusicType musicType){
		this.musicType=musicType;
		playMusic(musicType);
	}
	
	public void playMusic(){
		if(this.musicType!=null){
			playMusic(this.musicType);
		}
	}
	
	public void playMusic(MusicType musicType){
		switch (musicType) {
		case StartScreen:
			playMusic("start.mp3");
			break;
		case GameScreen:
			playMusic("game.wav");
			break;
		default:
			break;
		}
	}
	
	public void playMusic(String musicName){
		music=Gdx.audio.newMusic(Gdx.files.internal(musicPathString+musicName));
		music.play();
		music.setLooping(true);
	}
	
	public void dispose(){
		if(music!=null){
			music.stop();
			music.dispose();
		}
	}

	public MusicType getMusicType() {
		return musicType;
	}

	public void setMusicType(MusicType musicType) {
		this.musicType = musicType;
	}
	
	public void changeBgm(MusicType musicType){
		dispose();
		playMusic(musicType);
	}

	
}

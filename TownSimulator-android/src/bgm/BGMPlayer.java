package bgm;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.utility.IbgmPlayer;


//音乐名字要带后缀
public class BGMPlayer implements IbgmPlayer{
	private Context context;
	private AssetManager mAssetManager;
	private SoundPool sPool;
	private MediaPlayer mPlayer;
	
	private String musicPathString="sounds/bgm/";
	private String soundPathString="sounds/";
	
	public BGMPlayer(Context context) throws IOException{
		context=context;
		mAssetManager=context.getAssets();
		initMediaPlayer();
		initSoundPool();
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				this.dispose();
			}
			
		});
	}
	
	public void initSoundPool(){
		sPool=new SoundPool(3,AudioManager.STREAM_MUSIC,0);
	}
	
	public void initMediaPlayer() throws IOException{
		mPlayer=new MediaPlayer();
		playBGM("start.mp3");
	}
	
	public void playBGM(String bgmName) throws IOException{
		mPlayer.reset();
		AssetFileDescriptor assetFileDescriptor=mAssetManager.openFd(musicPathString+bgmName);
		mPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength()); 
		mPlayer.prepare();
		mPlayer.setLooping(true);
		mPlayer.start();
	}
	
	public void dispose(){
		if(mPlayer!=null){
			mPlayer.release();
		}
		if(sPool!=null){
			sPool.release();
		}
	}
	
}


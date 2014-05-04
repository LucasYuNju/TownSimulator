package com.townSimulator;

import java.io.IOException;

import android.os.Bundle;
import bgm.BGMPlayer;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.utility.AudioManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
			BGMPlayer bgmPlayer=new BGMPlayer(this);
			AudioManager.player = bgmPlayer;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGLSurfaceView20API18 = false;
        
        Driver d = Driver.getInstance(Driver.class);
        System.out.println(d);
        initialize(Driver.getInstance(Driver.class), cfg);
    }
}
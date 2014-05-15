package com.townSimulator;

import android.os.Bundle;

import com.TownSimulator.driver.Driver;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGLSurfaceView20API18 = false;
        cfg.useAccelerometer=false;
        cfg.useCompass=false;
        
        Driver d = Driver.getInstance(Driver.class);
        System.out.println(d);
        initialize(Driver.getInstance(Driver.class), cfg);
    }
}
package com.townSimulator;

import android.os.Bundle;

import com.townSimulator.Driver;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGLSurfaceView20API18 = false;
        
        initialize(new Driver(), cfg);
    }
}
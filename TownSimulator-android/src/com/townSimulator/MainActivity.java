package com.townSimulator;

import android.content.Intent;
import android.os.Bundle;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.utility.share.UMHelper;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends AndroidApplication {
	private ShareHelper shareHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        shareHelper = new ShareHelper(this);
        UMHelper.shareUtil = shareHelper;
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGLSurfaceView20API18 = false;
        cfg.useAccelerometer=false;
        cfg.useCompass=false;
        
        Driver d = Driver.getInstance(Driver.class);
        System.out.println(d);
        initialize(Driver.getInstance(Driver.class), cfg);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	MobclickAgent.onResume(this);
    }

    public void onPause() {
    	super.onPause();
    	MobclickAgent.onPause(this);
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		shareHelper.onActivityResult(requestCode, resultCode, data);
	}
}
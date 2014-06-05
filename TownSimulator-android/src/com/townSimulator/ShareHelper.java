package com.townSimulator;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.os.Bundle;

import com.TownSimulator.utility.share.ShareType;
import com.TownSimulator.utility.share.ShareUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.db.OauthHelper;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.sso.UMWXHandler;

public class ShareHelper implements ShareUtil{
	private UMSocialService mController;
	private Activity activity;
	
	public ShareHelper(Activity activity)
	{
		this.activity = activity;
		mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
		
		mController.getConfig().supportQQPlatform(this.activity,"101108120","d8cb84c5bc7bfea83dcbdafd804a49b4","http://www.umeng.com/social");
//		mController.getConfig().setSsoHandler( new QZoneSsoHandler(this.activity, "101108120","d8cb84c5bc7bfea83dcbdafd804a49b4") );
//		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		
		String appID = "wx967daebe835fbeac";
		// 微信图文分享必须设置一个url 
		String contentUrl = "http://www.umeng.com/social";
		// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
		UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(this.activity, appID, contentUrl);
		wxHandler.setWXTitle("Hello");
		// 支持微信朋友圈
		UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(this.activity, appID, contentUrl) ;
		circleHandler.setCircleTitle("Hello");
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
	
	private SHARE_MEDIA typeToMedia(ShareType type)
	{
		switch (type) {
		case QQZone:
			return SHARE_MEDIA.QZONE;
		case RenRen:
			return SHARE_MEDIA.RENREN;
		case Sina:
			return SHARE_MEDIA.SINA;
		case WeiXin:
			return SHARE_MEDIA.WEIXIN;
		default:
			return null;
		}
	}
	
	private int[] bytesToPixels(byte[] data)
	{
		int[] result = new int[data.length / 4];
		
		for (int i = 0; i < data.length; i += 4) {
			int r = 0xFF & data[i];
			int g = 0xFF & data[i + 1];
			int b = 0xFF & data[i + 2];
			int a = 0xFF & data[i + 3];
			
			result[i/4] = Color.argb(a, r, g, b);
		}
		
		return result;
	}
	
	private void doShare(final SHARE_MEDIA media)
	{
		byte[] rawData = ScreenUtils.getFrameBufferPixels(true);
		
		Bitmap bm = Bitmap.createBitmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Bitmap.Config.ARGB_8888);
		bm.setPixels(bytesToPixels(rawData), 0, Gdx.graphics.getWidth(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		int imgWidth = 800;
		float scale = (float)imgWidth / (float) Gdx.graphics.getWidth();
		Bitmap scaledBm = Bitmap.createScaledBitmap(bm, imgWidth, (int) (Gdx.graphics.getHeight() * scale), true);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try {
			scaledBm.compress(CompressFormat.PNG, 0, bos);
			bos.flush();
			
			mController.setShareContent("Hello World!");
			mController.setShareMedia( new UMImage(this.activity, bos.toByteArray()) );
			
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mController.directShare(activity, media, new SnsPostListener() {
						
						@Override
						public void onStart() {
							System.out.println("Share Start");
						}
					
						@Override
						public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
							System.out.println("Share Finish");
						}
					});
				}
			});
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public boolean share(ShareType type) {
		final SHARE_MEDIA shareMedia = typeToMedia(type);
		
		System.out.println(OauthHelper.isAuthenticated(activity, shareMedia));
		if( !OauthHelper.isAuthenticated(activity, shareMedia) )
		{
			activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mController.doOauthVerify(activity, shareMedia, new UMAuthListener() {
						
						@Override
						public void onStart(SHARE_MEDIA arg0) {
						}
						
						@Override
						public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
							arg0.printStackTrace();
						}
						
						@Override
						public void onComplete(Bundle arg0, SHARE_MEDIA arg1) {
							
						}
						
						@Override
						public void onCancel(SHARE_MEDIA arg0) {
							
						}
					});
				}
			});
		}
		
		if( !OauthHelper.isAuthenticated(activity, shareMedia) )
			return false;
		else
			doShare(shareMedia);
		
		return false;
	}
}

package com.TownSimulator.ui.options;

import java.util.ArrayList;

import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.GdxInputListnerEx;
import com.TownSimulator.utility.share.ShareType;
import com.TownSimulator.utility.share.UMHelper;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class ShareUIComs extends Group{
	private ArrayList<FlipButton> shareButtons;
	private String[] shareIconsMap = new String[]{"share_sina", "share_weixin", "share_renren"};
	private ShareType[] shareTypesMap = new ShareType[]{ShareType.Sina, ShareType.WeiXin, ShareType.RenRen};
//	public static final float ICON_HEIGHT = Settings.UNIT;
	
	public ShareUIComs()
	{
		initUI();
	}

	private void initUI() {
		shareButtons = new ArrayList<FlipButton>();
		for (int i = 0; i < shareIconsMap.length; i++) {
			FlipButton btn = new FlipButton(shareIconsMap[i], shareIconsMap[i], null);
			final int index = i;
			btn.addListener(new GdxInputListnerEx()
			{
				int btnIndex = index;
				@Override
				public void tapped(InputEvent event, float x, float y,
						int pointer, int button) {
					UMHelper.shareUtil.share(shareTypesMap[btnIndex]);
				}
				
			});
			addActor(btn);
			shareButtons.add(btn);
		}
	}
	
	
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		updateLayouts();
	}

	private void updateLayouts()
	{
		float iconsHPad = (getWidth() - shareIconsMap.length * getHeight()) / (float)(shareIconsMap.length + 1);
		float x = iconsHPad;
		float y = 0.0f;
		for (FlipButton btn : shareButtons) {
			btn.setPosition(x, y);
			btn.setSize(getHeight(), getHeight());
			
			x += iconsHPad + getHeight();
		}
	}
	
}

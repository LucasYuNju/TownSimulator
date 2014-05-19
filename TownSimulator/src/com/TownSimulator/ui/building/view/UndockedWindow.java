package com.TownSimulator.ui.building.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.camera.CameraListener;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * 能跟随地图移动
 * <p>添加关闭按钮
 * <p>添加建筑名
 * <p>添加拆除按钮
 */
public class UndockedWindow extends Group{
	protected float buildingPosXWorld;
	protected float buildingPosYWorld;
	public static final float MARGIN = Settings.MARGIN;
	public static final float LABEL_WIDTH = Settings.LABEL_WIDTH;
	public static final float LABEL_HEIGHT = Settings.LABEL_HEIGHT;
	public static final float ICON_WIDTH = Settings.LABEL_WIDTH;
	public static final float MIN_WIDTH = LABEL_WIDTH * 2.5f;
	protected BuildingType buildingType;
	protected transient TextureRegion background;
	private String bgPath = "background";
	protected Button closeButton;
	protected Button dynamiteButton;
	protected Label headerLabel;
	private UndockedWindowListener listener;
	
	public interface UndockedWindowListener extends Serializable
	{
		public void dynamiteButtonClicked();
	}
	
	public UndockedWindow(BuildingType buildingType) {
		super();
		background = Singleton.getInstance(ResourceManager.class).createTextureRegion(bgPath);
		this.buildingType = buildingType;
		initCameraListener();
		
		setColor(1.0f, 1.0f, 1.0f, Settings.UI_ALPHA);
		
		addDynamiteButton();
	}
	
	protected void addCloseButton() {
		if(closeButton == null) {
			closeButton = new FlipButton("button_cancel", "button_cancel", null);
			closeButton.setSize(LABEL_HEIGHT, LABEL_HEIGHT);
			closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
			closeButton.addListener(new InputListener()
			{
				float touchDownX = 0.0f;
				float touchDownY = 0.0f;
				
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchDownX = x;
					touchDownY = y;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if(touchDownX == x && touchDownY == y)
						UndockedWindow.this.setVisible(false);
				}
				
			});
			addActor(closeButton);
		}
		else {
			closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
		}
	}
	
	protected void addHeader() {
		if(headerLabel == null) {
			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
			labelStyle.fontColor = Color.ORANGE;
			headerLabel = new Label(buildingType.toString(), labelStyle);
			headerLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
			headerLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
			headerLabel.setAlignment(Align.left);
			addActor(headerLabel);
		}
		else {
			headerLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
		}
	}
	
	protected void addDynamiteButton()
	{
		dynamiteButton = new FlipButton("button_dynamite", "button_dynamite", null);
		dynamiteButton.setSize(LABEL_HEIGHT * 1.5f, LABEL_HEIGHT * 1.5f);
		dynamiteButton.setPosition(getWidth() - dynamiteButton.getWidth(), 0.0f);
		dynamiteButton.addListener(new InputListener()
		{
			float touchDownX = 0.0f;
			float touchDownY = 0.0f;
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				touchDownX = x;
				touchDownY = y;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(touchDownX == x && touchDownY == y)
				{
					if(listener != null)
						listener.dynamiteButtonClicked();
				}
			}
			
		});
		addActor(dynamiteButton);
	}
	
	public void setShowDynamiteButton(boolean v)
	{
		if(v)
			dynamiteButton.setVisible(true);
		else
			dynamiteButton.setVisible(false);
	}
	
	/**
	 * 更新组件位置
	 */
	protected void updateLayout()
	{
		closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
		headerLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
		dynamiteButton.setPosition(getWidth() - dynamiteButton.getWidth(), 0.0f);
	}
	
	protected void initCameraListener()
	{
		CameraController.getInstance(CameraController.class).addListener(new CameraListener() {
			private static final long serialVersionUID = 1811039619585047103L;

			@Override
			public void cameraZoomed(float prevWidth, float prevHeight, float curWidth,
					float curHeight) {
				if(isVisible())
					updatePosition();
			}
			
			@Override
			public void cameraMoved(float deltaX, float deltaY) {
				if(isVisible())
					updatePosition();
			}
		});
	}
	
	public void setUndockedWindowListener(UndockedWindowListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible)
			updatePosition();
	}

	protected void updatePosition()
	{
		Vector3 pos = new Vector3(buildingPosXWorld, buildingPosYWorld, 0.0f);
		CameraController.getInstance(CameraController.class).worldToScreen(pos);
		float windowX = pos.x - getWidth();
		float windowY = pos.y - getHeight() * 0.5f;
		setPosition(windowX, windowY);
	}

	public void setBuildingPosWorld(float x, float y)
	{
		buildingPosXWorld = x;
		buildingPosYWorld = y;
	}
	
	/**
	 * window绘制背景色
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = this.getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
		applyTransform(batch, computeTransform());
		drawChildren(batch, parentAlpha);
		resetTransform(batch);
	}
	
	protected void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		background = Singleton.getInstance(ResourceManager.class).createTextureRegion(bgPath);
	}
}

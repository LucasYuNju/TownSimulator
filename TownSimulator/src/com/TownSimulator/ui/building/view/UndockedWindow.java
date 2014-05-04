package com.TownSimulator.ui.building.view;

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
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * 
 * 能跟随地图移动
 * 有关闭按钮
 * 有建筑名
 *
 */
public class UndockedWindow extends Group{
	protected float buildingPosXWorld;
	protected float buildingPosYWorld;
	public static final float MARGIN = Settings.MARGIN;
	public static final float LABEL_WIDTH = Settings.LABEL_WIDTH;
	public static final float LABEL_HEIGHT = Settings.LABEL_HEIGHT;
	protected BuildingType buildingType;
	protected TextureRegion background;
	private Button closeButton;
	private Label headerLabel;
	
	public UndockedWindow(BuildingType buildingType) {
		super();
		background = Singleton.getInstance(ResourceManager.class).findTextureRegion("background");
		this.buildingType = buildingType;
		initCameraListener();
	}
	
	protected void addCloseButton() {
		if(closeButton == null) {
			closeButton = new FlipButton("button_cancel", "button_cancel", null);
			closeButton.setSize(MARGIN, MARGIN);
			closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
			closeButton.addListener(new EventListener() {
				@Override
				public boolean handle(Event event) {
					UndockedWindow.this.setVisible(false);
					return false;
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
		labelStyle.fontColor = Color.WHITE;
		headerLabel = new Label(buildingType.toString(), labelStyle);
		headerLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		headerLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
		headerLabel.setAlignment(Align.center);
		addActor(headerLabel);
		}
		else {
			headerLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
		}
	}

	
	protected void initCameraListener()
	{
		CameraController.getInstance(CameraController.class).addListener(new CameraListener() {
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
}

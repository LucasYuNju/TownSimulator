package com.TownSimulator.ui.building.view;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.ui.UndockedWindow;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class ViewWindow extends UndockedWindow{
	private static final float LABEL_WIDTH = Settings.UNIT * 1.5f;
	private static final float LABEL_HEIGHT = Settings.UNIT * 0.5f;
	private static final float MARGIN = ConstructionWindow.MARGIN;
	private TextureRegion background;
	String[][] data;
	
	public ViewWindow(String data[][]) {
		super();
		this.data = data;
		background = Singleton.getInstance(ResourceManager.class).findTextureRegion("background");
		setSize(LABEL_WIDTH * 2 + MARGIN * 2, LABEL_HEIGHT * data.length + MARGIN * 2);
		setPosition(0, 0);
		addLabels();
	}
	
	public void addLabels() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		for(int i=0; i<data.length; i++) {
			for(int j=0; j<data[0].length; j++) {
				Label label = new Label(i + "." + data[i][j], labelStyle);
				label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
				label.setPosition(LABEL_WIDTH * j + MARGIN, LABEL_HEIGHT * i + MARGIN);
				addActor(label);
			}
		}
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = this.getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
		applyTransform(batch, computeTransform());
		drawChildren(batch, parentAlpha);
		resetTransform(batch);
	}
	
	@Override
	protected void updatePosition()
	{
		Vector3 pos = new Vector3(buildingPosXWorld, buildingPosYWorld, 0.0f);
		CameraController.getInstance(CameraController.class).worldToScreen(pos);
		float windowX = pos.x - getWidth();
		float windowY = pos.y - getHeight() * 0.5f;
		getParent().setPosition(windowX, windowY);
	}
}

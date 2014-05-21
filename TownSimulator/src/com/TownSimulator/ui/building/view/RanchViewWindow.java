package com.TownSimulator.ui.building.view;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.entity.RanchAnimalType;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Layout:
 * 
 * header
 * dropDown
 * workers
 * 
 */
public class RanchViewWindow extends WorkableViewWindow {
	private SelectBox<String> dropDown;
	private TextureRegion buttonBackground;
	private float width;
	private float height;
	private SelectBoxListener selectBoxListener;
	
	public RanchViewWindow(int numAllowedWorker) {
		super(BuildingType.RANCH, numAllowedWorker);
		buttonBackground = Singleton.getInstance(ResourceManager.class).createTextureRegion("background_button");
		width = ProcessBar.PREFERED_WIDTH + LABEL_WIDTH + MARGIN * 2;
		height = getHeight() + LABEL_HEIGHT + MARGIN * 2;
		setSize(width, height);
		addDropDown();
		addCloseButton();
		addHeader();
		updateLayout();
	}

	private void addDropDown() {
		SelectBoxStyle style = new SelectBoxStyle();
		style.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		style.fontColor = Color.WHITE;
		style.background = new TextureRegionDrawable(buttonBackground);
		style.scrollStyle = new ScrollPaneStyle();
		style.scrollStyle.background = style.background;
		style.listStyle = new ListStyle();
		style.listStyle.font = style.font;
		style.listStyle.background = style.background;
		style.listStyle.selection = style.background;
		dropDown = new SelectBox<String>(style);
		RanchAnimalType[] types = RanchAnimalType.values();
		String[] strs = new String[types.length + 1];
		strs[0] = "<Empty>";
		for (int i = 1; i < strs.length; i++) {
			strs[i] = types[i-1].getViewName();
		}
		dropDown.setItems(strs);
		dropDown.setSize(style.font.getBounds(strs[0]).width, LABEL_HEIGHT);
		dropDown.setPosition(MARGIN, MARGIN + WorkerGroup.HEIGHT + LABEL_HEIGHT);
		dropDown.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				selectBoxListener.selectBoxSelected(dropDown.getSelected());
				return false;
			}
		});
		
		addActor(dropDown);
	}
	
	@Override
	protected void updatePosition()
	{
		Vector3 pos = new Vector3(buildingPosXWorld, buildingPosYWorld, 0.0f);
		CameraController.getInstance(CameraController.class).worldToScreen(pos);
		float windowX = pos.x - getWidth();
		float windowY = pos.y - getHeight() * 0.5f;
		setPosition(windowX, windowY);
	}
	
	public void setSelectBoxListener(SelectBoxListener selectBoxListener) {
		this.selectBoxListener = selectBoxListener;
	}
	
	public void reload(RanchAnimalType animalType) {
		Gdx.app.log("ranch", dropDown.toString());
		Gdx.app.log("ranch", animalType.toString());
		dropDown.setSelected(animalType.getViewName());
	}
}

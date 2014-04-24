package com.townSimulator.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.townSimulator.game.GameManager;
import com.townSimulator.game.objs.BuildingType;

public class BuildComsButton extends BuildComsButtonBase{
	//private String mLabelText;
	
	public BuildComsButton(String textureName, String labelText) {
		super(textureName, labelText);
		
		//mLabelText = labelText;
		addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.cancel();
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
//				SceneManager.getInstance().setDrawGrid(true);
//				
//				Building newBuildingObject = ObjectFactory.createBuilding(BuildingType.WOOD_HOUSE);
//				int gridX = (int) (CameraController.getInstance().getX() / Settings.UNIT);
//				int gridY = (int) (CameraController.getInstance().getY() / Settings.UNIT);
//				newBuildingObject.setPositionOriginCollision(
//						gridX * Settings.UNIT, gridY * Settings.UNIT);
//				SceneManager.getInstance().addBuilding(newBuildingObject);
				GameManager.getInstance().startNewBuilding(BuildingType.WOOD_HOUSE);
			}
		});
	}
}

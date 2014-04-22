package com.townSimulator.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.townSimulator.game.objs.MapObject;
import com.townSimulator.game.objs.MapObjectType;
import com.townSimulator.game.objs.MapObjectFactory;
import com.townSimulator.game.scene.CameraController;
import com.townSimulator.game.scene.SceneManager;
import com.townSimulator.utility.Settings;

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
				SceneManager.getInstance().setDrawGrid(true);
				
				MapObject newBuildingObject = MapObjectFactory.createMapObj(MapObjectType.BUILDING);
				newBuildingObject.setDrawPosition(
						CameraController.getInstance().getX(), CameraController.getInstance().getY());
				SceneManager.getInstance().addMapObjs(newBuildingObject);
			}
		});
	}
}

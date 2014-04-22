package com.townSimulator.game.scene;

import com.badlogic.gdx.utils.Array;
import com.townSimulator.game.objs.MapObject;
import com.townSimulator.game.render.GameDrawable;
import com.townSimulator.game.render.GameRenderManager;
import com.townSimulator.utility.Settings;

public class SceneManager {
	private static final 	int					MAP_WIDTH  = 512;
	private static final 	int					MAP_HEIGHT = 512;
	private 				Map 				mMap;
	private 				QuadTree			mCollsionDetector;
	private					QuadTree			mDrawScissor;
	
	public SceneManager()
	{
		mCollsionDetector = new QuadTree(QuadTreeType.COLLISION, 0.0f, 0.0f, MAP_WIDTH * Settings.UNIT, MAP_HEIGHT * Settings.UNIT);
		mDrawScissor = new QuadTree(QuadTreeType.DRAW, 0.0f, 0.0f, MAP_WIDTH * Settings.UNIT, MAP_HEIGHT * Settings.UNIT);
	}
	
	public void initMap()
	{
		mMap = new Map(MAP_WIDTH, MAP_HEIGHT, 100, this);
	}
	
	public void addMapObjs(MapObject obj)
	{
		mCollsionDetector.addManageble(obj);
		mDrawScissor.addManageble(obj);
	}
	
	public Map getMap()
	{
		return mMap;
	}
	
	public QuadTree getCollisionDetector()
	{
		return mCollsionDetector;
	}
	
	public void renderScene(GameRenderManager renderMgr)
	{
		Array<QuadTreeManageble> renderList = new Array<QuadTreeManageble>();
		mDrawScissor.detectIntersection(renderMgr.getScissor(), renderList);
		for (int i = 0; i < renderList.size; i++) {
			renderMgr.draw((GameDrawable) renderList.get(i));
		}
	}
	
}

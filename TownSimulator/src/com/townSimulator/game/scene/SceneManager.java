package com.townSimulator.game.scene;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.townSimulator.game.objs.MapObject;
import com.townSimulator.game.render.GameDrawable;
import com.townSimulator.game.render.GameDrawableObject;
import com.townSimulator.game.render.GameRenderManager;
import com.townSimulator.utility.AxisAlignedBoundingBox;
import com.townSimulator.utility.ResourceManager;
import com.townSimulator.utility.Settings;

public class SceneManager {
	private static final 	int							MAP_WIDTH_UNIT  = 512;
	private static final 	int							MAP_HEIGHT_UNIT = 512;
	private 				Map 						mMap;
	private 				QuadTree					mCollsionDetector;
	private					QuadTree					mDrawScissor;
	private static			boolean 					mbDrawGrid = false;
	private					Array<GameDrawableObject> 	mGridIdleList;
	private					int							mMallocIndex = 0;
	
	public SceneManager()
	{
		mCollsionDetector = new QuadTree(QuadTreeType.COLLISION, 0.0f, 0.0f, MAP_WIDTH_UNIT * Settings.UNIT, MAP_HEIGHT_UNIT * Settings.UNIT);
		mDrawScissor = new QuadTree(QuadTreeType.DRAW, 0.0f, 0.0f, MAP_WIDTH_UNIT * Settings.UNIT, MAP_HEIGHT_UNIT * Settings.UNIT);
		mGridIdleList = new Array<GameDrawableObject>();
	}
	
	public void initMap()
	{
		mMap = new Map(MAP_WIDTH_UNIT, MAP_HEIGHT_UNIT, 100, this);
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
		AxisAlignedBoundingBox scissor = renderMgr.getScissor();
		mDrawScissor.detectIntersection(scissor, renderList);
		for (int i = 0; i < renderList.size; i++) {
			renderMgr.draw((GameDrawable) renderList.get(i));
		}
		
		if(mbDrawGrid)
			renderGrid(renderMgr, scissor);
	}
	
	public static void setDrawGrid(boolean bDrawGrid)
	{
		mbDrawGrid = bDrawGrid;
	}
	
	public GameDrawableObject mallocGrid()
	{
		if(mMallocIndex >= mGridIdleList.size)
		{
			Sprite gridSp = ResourceManager.createSprite("grid");
			gridSp.setSize(Settings.UNIT, Settings.UNIT);
			mGridIdleList.add( new GameDrawableObject(gridSp, -1.0f) );
		}
		
		return mGridIdleList.get(mMallocIndex++);
	}
	
	private void renderGrid(GameRenderManager renderMgr, AxisAlignedBoundingBox scissor)
	{
		int l = (int)(scissor.minX / Settings.UNIT);
		int r = (int)(scissor.maxX / Settings.UNIT);
		int b = (int)(scissor.minY / Settings.UNIT);
		int u = (int)(scissor.maxY / Settings.UNIT);
		mMallocIndex = 0;
		AxisAlignedBoundingBox gridAABB = new AxisAlignedBoundingBox();
		for (float x = l * Settings.UNIT; x <= (r+1) * Settings.UNIT; x += Settings.UNIT) {
			for (float y = b * Settings.UNIT; y <= (u+1) * Settings.UNIT; y += Settings.UNIT) {
				GameDrawableObject obj = mallocGrid();
				obj.setDrawPosition(x, y);
				gridAABB.minX = x;
				gridAABB.minY = y;
				gridAABB.maxX = x + Settings.UNIT;
				gridAABB.maxY = y + Settings.UNIT;
				obj.setDepth(y);
				if(mCollsionDetector.detectIntersection(gridAABB))
					obj.getSprite().setColor(1.0f, 0.0f, 0.0f, 0.3f);
				else
					obj.getSprite().setColor(0.0f, 0.0f, 1.0f, 0.3f);
				renderMgr.draw(obj);
			}
		}
	}
	
}

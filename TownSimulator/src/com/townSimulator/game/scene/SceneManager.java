package com.townSimulator.game.scene;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.townSimulator.game.objs.Drawable;
import com.townSimulator.game.objs.DrawableObject;
import com.townSimulator.game.objs.MapObject;
import com.townSimulator.game.render.Renderer;
import com.townSimulator.utility.AxisAlignedBoundingBox;
import com.townSimulator.utility.ResourceManager;
import com.townSimulator.utility.Settings;

/**
 * 
 * 	维持所有DrawObject的列表
 *
 */
public class SceneManager {
	private static final 	int							MAP_WIDTH_UNIT  = 512;
	private static final 	int							MAP_HEIGHT_UNIT = 512;
	private 				Map 						mMap;
	private 				QuadTree					mCollsionDetector;
	private					QuadTree					mDrawScissor;
	private static			boolean 					mbDrawGrid = false;
	private					Array<DrawableObject> 		mGridIdleList;
	private					int							mMallocIndex = 0;
	private	static			SceneManager				instance = null;
	
	public SceneManager()
	{
		instance = this;
		mCollsionDetector = new QuadTree(QuadTreeType.COLLISION, 0.0f, 0.0f, MAP_WIDTH_UNIT * Settings.UNIT, MAP_HEIGHT_UNIT * Settings.UNIT);
		mDrawScissor = new QuadTree(QuadTreeType.DRAW, 0.0f, 0.0f, MAP_WIDTH_UNIT * Settings.UNIT, MAP_HEIGHT_UNIT * Settings.UNIT);
		mGridIdleList = new Array<DrawableObject>();
	}
	
	public synchronized static SceneManager getInstance() {
		if(instance == null)
			instance = new SceneManager();
		return instance;
	}
	
	public void initMap()
	{
		mMap = new Map(MAP_WIDTH_UNIT, MAP_HEIGHT_UNIT, 100, this);
	}
	
	public void addMapObjs(DrawableObject obj)
	{
		mCollsionDetector.addManageble(obj);
		mDrawScissor.addManageble(obj);
	}
	
	public void addBuilding(){
		
	}
	
	public Map getMap()
	{
		return mMap;
	}
	
	public QuadTree getCollisionDetector()
	{
		return mCollsionDetector;
	}
	
	public void renderScene(Renderer renderMgr)
	{
		Array<QuadTreeManageble> renderList = new Array<QuadTreeManageble>();
		AxisAlignedBoundingBox scissor = renderMgr.getScissor();
		mDrawScissor.detectIntersection(scissor, renderList);
		for (int i = 0; i < renderList.size; i++) {
			renderMgr.draw((Drawable) renderList.get(i));
		}
		
		if(mbDrawGrid)
			renderGrid(renderMgr, scissor);
	}
	
	public void setDrawGrid(boolean bDrawGrid)
	{
		mbDrawGrid = bDrawGrid;
	}
	
	public DrawableObject mallocGrid()
	{
		if(mMallocIndex >= mGridIdleList.size)
		{
			Sprite gridSp = ResourceManager.createSprite("grid");
			gridSp.setSize(Settings.UNIT, Settings.UNIT);
			mGridIdleList.add( new DrawableObject(gridSp, -1.0f) );
		}
		
		return mGridIdleList.get(mMallocIndex++);
	}
	
	private void renderGrid(Renderer renderMgr, AxisAlignedBoundingBox scissor)
	{
		int l = (int)(scissor.minX / Settings.UNIT);
		int r = (int)(scissor.maxX / Settings.UNIT);
		int b = (int)(scissor.minY / Settings.UNIT);
		int u = (int)(scissor.maxY / Settings.UNIT);
		mMallocIndex = 0;
		AxisAlignedBoundingBox gridAABB = new AxisAlignedBoundingBox();
		for (float x = l * Settings.UNIT; x <= (r+1) * Settings.UNIT; x += Settings.UNIT) {
			for (float y = b * Settings.UNIT; y <= (u+1) * Settings.UNIT; y += Settings.UNIT) {
				DrawableObject obj = mallocGrid();
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

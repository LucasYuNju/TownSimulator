package com.TownSimulator.render;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.Drawable;
import com.TownSimulator.entity.Entity;
import com.TownSimulator.map.Map;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.quadtree.QuadTree;
import com.TownSimulator.utility.quadtree.QuadTreeManageble;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Renderer extends Singleton{
	private RenderBatch		   		mRenderBatch;
	private	QuadTree				mDrawScissor;
	private	Array<Entity> 			mGridIdleList;
	private boolean 				mbDrawGrid = false;
	private	int						mMallocIndex = 0;
	private boolean					mbRenderScene = false;
	
	private Renderer()
	{
		mRenderBatch = new RenderBatch();
		mDrawScissor = new QuadTree(QuadTreeType.DRAW, 0.0f, 0.0f, Map.MAP_WIDTH * Settings.UNIT, Map.MAP_HEIGHT * Settings.UNIT);
		mGridIdleList = new Array<Entity>();
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
		{

			@Override
			public void dispose() {
				mRenderBatch.dispose();
				mInstaceMap.clear();
			}
			
		});
	}
	
	public boolean attachDrawScissor(QuadTreeManageble obj)
	{
		return mDrawScissor.addManageble(obj);
	}
	
	public void dettachDrawScissor(QuadTreeManageble obj)
	{
		obj.dettachQuadTree(QuadTreeType.DRAW);
	}
	
	public void updateDrawScissor(QuadTreeManageble obj)
	{
		dettachDrawScissor(obj);
		attachDrawScissor(obj);
	}
	
	public void setRenderScene(boolean value)
	{
		mbRenderScene = value;
	}
	
	private void renderBegin()
	{
		CameraController.getInstance(CameraController.class).updateCamera();
	}
	
	private void renderEnd()
	{
		
		mRenderBatch.setProjectionMatrix(CameraController.getInstance(CameraController.class).getCameraCombined());
		mRenderBatch.doRender();
	}
	
	public void render()
	{
		renderBegin();
		if(mbRenderScene)
			renderScene();
		renderEnd();
	}
	
	private void renderScene()
	{
		Array<QuadTreeManageble> renderList = new Array<QuadTreeManageble>();
		mDrawScissor.detectIntersection(CameraController.getInstance(CameraController.class).getCameraViewAABB(), renderList);
		for (int i = 0; i < renderList.size; i++) {
			draw((Drawable) renderList.get(i));
		}
		
		if(mbDrawGrid)
			renderGrid();
	}
	
	public void setDrawGrid(boolean bDrawGrid)
	{
		mbDrawGrid = bDrawGrid;
	}
	
	public Entity mallocGrid()
	{
		if(mMallocIndex >= mGridIdleList.size)
		{
			Sprite gridSp = ResourceManager.getInstance(ResourceManager.class).createSprite("grid");
			Entity entity = new Entity(gridSp, -1.0f);
			entity.setDrawAABBLocal(0.1f, 0.1f, Settings.UNIT - 0.2f, Settings.UNIT - 0.2f);
			mGridIdleList.add( entity );
			
		}
		
		return mGridIdleList.get(mMallocIndex++);
	}
	
	private void renderGrid()
	{
		AxisAlignedBoundingBox scissor = CameraController.getInstance(CameraController.class).getCameraViewAABB();
		int l = (int)(scissor.minX / Settings.UNIT);
		int r = (int)(scissor.maxX / Settings.UNIT);
		int b = (int)(scissor.minY / Settings.UNIT);
		int u = (int)(scissor.maxY / Settings.UNIT);
		mMallocIndex = 0;
		float pad = 0.1f;
		AxisAlignedBoundingBox gridAABB;
		for (float x = l; x <= (r+1); x ++) {
			for (float y = b; y <= (u+1); y ++) {
				Entity obj = mallocGrid();
				obj.setPositionWorld(x * Settings.UNIT + pad, y * Settings.UNIT + pad);
				gridAABB = obj.getAABBWorld(QuadTreeType.DRAW);
				obj.setDepth(Float.MAX_VALUE);
				if(CollisionDetector.getInstance(CollisionDetector.class).detect(gridAABB))
					obj.getSprite().setColor(1.0f, 0.0f, 0.0f, 0.3f);
				else
					obj.getSprite().setColor(0.0f, 0.0f, 1.0f, 0.3f);
				draw(obj);
			}
		}
	}
	
	public void draw(Drawable draw)
	{
		mRenderBatch.addDrawable(draw);
	}
}

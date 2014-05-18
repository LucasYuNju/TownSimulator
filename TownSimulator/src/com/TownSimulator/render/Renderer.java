package com.TownSimulator.render;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.map.Map;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.SingletonPublisher;
import com.TownSimulator.utility.quadtree.QuadTree;
import com.TownSimulator.utility.quadtree.QuadTreeManageble;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Renderer extends SingletonPublisher<RendererListener>{
	private RenderBatch		   		mRenderBatch;
	private	QuadTree				mDrawScissor;
	private	Array<Grid> 			mGridIdleList;
//	private HashMap<String, GroundDrawContainer> mGroundDrawMap;
	private boolean 				mbDrawGrid = false;
	private	int						allocIndex = 0;
	private boolean					mbRenderScene = false;
	
	private Renderer()
	{
		mRenderBatch = new RenderBatch();
		mDrawScissor = new QuadTree(QuadTreeType.DRAW, 0.0f, 0.0f, Map.MAP_WIDTH * Settings.UNIT, Map.MAP_HEIGHT * Settings.UNIT);
		mGridIdleList = new Array<Grid>();
//		mGroundDrawMap = new HashMap<String, GroundDrawContainer>();
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
		{

			@Override
			public void dispose() {
				mRenderBatch.dispose();
				Singleton.clearInstanceMap();
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
	
	public Array<QuadTreeManageble> getVisibleEntities(AxisAlignedBoundingBox scissor)
	{
		Array<QuadTreeManageble> entities = new Array<QuadTreeManageble>();
		mDrawScissor.detectIntersection(scissor, entities);
		return entities;
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
		
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).renderBegined();
		}
	}
	
	private void renderEnd()
	{
		
		mRenderBatch.setProjectionMatrix(CameraController.getInstance(CameraController.class).getCameraCombined());
		mRenderBatch.doRender();
		
		allocIndex = 0;
//		Iterator<String> itr = mGroundDrawMap.keySet().iterator();
//		while(itr.hasNext())
//		{
//			String key = itr.next();
//			mGroundDrawMap.get(key).reset();
//		}
		
		for (int i = 0; i < mListeners.size; i++) {
			mListeners.get(i).renderEnded();
		}
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
		//renderGround();
		
		Array<QuadTreeManageble> renderList = new Array<QuadTreeManageble>();
		mDrawScissor.detectIntersection(CameraController.getInstance(CameraController.class).getCameraViewAABB(), renderList);
		for (int i = 0; i < renderList.size; i++) {
			draw((Drawable) renderList.get(i));
		}
		
		if(mbDrawGrid)
			renderGrid();
	}
	
	public void draw(Drawable draw)
	{
		mRenderBatch.addDrawable(draw);
	}
	
	public void setDrawGrid(boolean bDrawGrid)
	{
		mbDrawGrid = bDrawGrid;
	}
	
	public Grid allocGrid()
	{
		if(allocIndex >= mGridIdleList.size)
		{
			Grid grid = new Grid();
			mGridIdleList.add( grid );
		}
		
		return mGridIdleList.get(allocIndex++);
	}
	
	private void renderGrid()
	{
		AxisAlignedBoundingBox scissor = CameraController.getInstance(CameraController.class).getCameraViewAABB();
		AxisAlignedBoundingBox gridAABB = new AxisAlignedBoundingBox();
		int l = (int)(scissor.minX / Settings.UNIT);
		int r = (int)(scissor.maxX / Settings.UNIT);
		int b = (int)(scissor.minY / Settings.UNIT);
		int u = (int)(scissor.maxY / Settings.UNIT);
		for (int x = l; x <= (r+1); x ++) {
			for (int y = b; y <= (u+1); y ++) {
				Grid grid = allocGrid();
				grid.setGridPos(x, y);
				gridAABB.minX = x * Settings.UNIT + Grid.PAD;
				gridAABB.minY = y * Settings.UNIT + Grid.PAD;
				gridAABB.maxX = gridAABB.minX + Settings.UNIT - Grid.PAD * 2.0f;
				gridAABB.maxY = gridAABB.minY + Settings.UNIT - Grid.PAD * 2.0f;
				if(CollisionDetector.getInstance(CollisionDetector.class).detect(gridAABB))
					grid.setColor(1.0f, 0.0f, 0.0f, 0.3f);
				else
					grid.setColor(0.0f, 0.0f, 1.0f, 0.3f);
				draw(grid);
			}
		}
		
	}
	
//	private GroundDraw allocGroundDraw(String textureName)
//	{
//		if( !mGroundDrawMap.containsKey(textureName) )
//			mGroundDrawMap.put(textureName, new GroundDrawContainer(textureName));
//		
//		return mGroundDrawMap.get(textureName).alloc();	
//	}
	
//	private void renderGround()
//	{
//		
//		
//		AxisAlignedBoundingBox scissor = CameraController.getInstance(CameraController.class).getCameraViewAABB();
//		int l = Math.max( 0, (int)(scissor.minX / Settings.UNIT) );
//		int r = Math.min( Map.MAP_WIDTH - 1, (int)(scissor.maxX / Settings.UNIT) );
//		int b = Math.max( 0, (int)(scissor.minY / Settings.UNIT) );
//		int u = Math.min( Map.MAP_HEIGHT - 1, (int)(scissor.maxY / Settings.UNIT) );
//		for (int x = l; x <= (r); x ++) {
//			for (int y = b; y <= (u); y ++) {
//				String textureName = Map.getInstance(Map.class).getGroundMap()[x][y];
//				GroundDraw draw = allocGroundDraw(textureName);
//				draw.setGridPos(x, y);
//				draw(draw);
//			}
//		}
//		
//		
//	}
	
	class GroundDrawContainer
	{
		private Array<GroundDraw> draws;
		private int allocIndex = 0;
		private String textureName;
		
		public GroundDrawContainer(String textureName)
		{
			draws = new Array<GroundDraw>();
			this.textureName = textureName;
		}
		
		public GroundDraw alloc()
		{
			if(allocIndex >= draws.size)
				draws.add(new GroundDraw(textureName));
			
			return draws.get(allocIndex++);
		}
		
		public void reset()
		{
			allocIndex = 0;
		}
	}
	
	class GroundDraw implements Drawable
	{
		private Sprite sp;
		public GroundDraw(String textureName)
		{
			sp = ResourceManager.getInstance(ResourceManager.class).createSprite(textureName);
			sp.setSize(Settings.UNIT, Settings.UNIT);
		}
		
		public void setGridPos(int x, int y)
		{
			sp.setPosition(x * Settings.UNIT, y * Settings.UNIT);
		}
		
		@Override
		public void drawSelf(SpriteBatch batch) {
			sp.draw(batch);
		}

		@Override
		public float getDepth() {
			return Float.MAX_VALUE;
		}
		
	}
}

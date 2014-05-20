package com.TownSimulator.ui.building.adjust;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.ai.btnimpls.construct.ConstructionProject;
import com.TownSimulator.camera.CameraController;
import com.TownSimulator.camera.CameraListener;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.Entity;
import com.TownSimulator.entity.EntityFactory;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.EntityListener;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.Building.State;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FarmLand;
import com.TownSimulator.entity.building.MoneyProducingBuilding;
import com.TownSimulator.entity.building.Ranch;
import com.TownSimulator.entity.building.RanchLand;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.adjust.BuildingAdjustGroup.BuildAjustUIListener;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.Settings.MPData;
import com.TownSimulator.utility.quadtree.QuadTreeManageble;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.math.Vector3;

/**
 *控制建筑从点击建造图标到确认或取消的过程 
 *
 */
public class BuildingAdjustBroker extends Singleton implements EntityListener, CameraListener{
	private static final long serialVersionUID = -6973380969754255405L;
	private 		Building		mCurBuilding;
	private 		boolean			mbBuildingMovable = false;
	private			float			mMoveDeltaX = 0.0f;
	private			float			mMoveDeltaY = 0.0f;
	private			BuildingAdjustGroup 	mBuildUI;
	
	private BuildingAdjustBroker()
	{
		
	}
	
	public void startNewBuilding(BuildingType type)
	{
		if(isIdle() == false)
			return;
		
		Renderer.getInstance(Renderer.class).setDrawGrid(true);
		
		Building newBuildingObject = EntityFactory.createBuilding(type);
		int gridX = (int) (CameraController.getInstance(CameraController.class).getX() / Settings.UNIT);
		int gridY = (int) (CameraController.getInstance(CameraController.class).getY() / Settings.UNIT);
		int gridSerchSize = 0;
		AxisAlignedBoundingBox buildingCollisionAABB;
		if(type == BuildingType.FARM_HOUSE)
			buildingCollisionAABB = ((FarmHouse)newBuildingObject).getCollisionAABBLocalWithLands();
		else if(type == BuildingType.RANCH)
			buildingCollisionAABB = ((Ranch)newBuildingObject).getCollisionAABBLocalWithLands();
		else
			buildingCollisionAABB = newBuildingObject.getCollisionAABBLocal();
		
		boolean bPosFind = false;
		float posX = 0.0f;
		float posY = 0.0f;
		AxisAlignedBoundingBox aabb = new AxisAlignedBoundingBox();
		while(!bPosFind)
		{
			for (int x = gridX - gridSerchSize; x <= gridX + gridSerchSize && !bPosFind; x++) {
				for (int y = gridY - gridSerchSize; y <= gridY + gridSerchSize && !bPosFind; y++) {
					posX = x * Settings.UNIT;
					posY = y * Settings.UNIT;
					aabb.minX = posX + buildingCollisionAABB.minX;
					aabb.minY = posY + buildingCollisionAABB.minY;
					aabb.maxX = posX + buildingCollisionAABB.maxX;
					aabb.maxY = posY + buildingCollisionAABB.maxY;
					if( !CollisionDetector.getInstance(CollisionDetector.class).detect(aabb) )
						bPosFind = true;
				}
			}
			gridSerchSize ++;
			
		}
		newBuildingObject.setPositionWorld(posX, posY);
		Renderer.getInstance(Renderer.class).attachDrawScissor(newBuildingObject);
		if(type == BuildingType.FARM_HOUSE)
			for (FarmLand land : ((FarmHouse)newBuildingObject).getFarmLands()) {
				Renderer.getInstance(Renderer.class).attachDrawScissor(land);
			}
		else if(type == BuildingType.RANCH)
			for (RanchLand land : ((Ranch)newBuildingObject).getRanchLands()) {
				Renderer.getInstance(Renderer.class).attachDrawScissor(land);
			}
		CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(newBuildingObject);
		
		setBuilding(newBuildingObject);
		setBuildAjustUI(UIManager.getInstance(UIManager.class).getGameUI().getBuildAjustUI());
	}
	
	public boolean isIdle()
	{
		return mCurBuilding == null;
	}
	
	private void setBuilding(Building building)
	{
		mCurBuilding = building;
		
		mCurBuilding.setListener(this);
	}
	
	private void confirmBuilding()
	{
		if(mCurBuilding instanceof MoneyProducingBuilding)
		{
			mCurBuilding.setState(Building.State.Constructed);
			MPData mpData = Settings.mpBuildingDataMap.get(mCurBuilding.getType());
			int cost = mpData.cost;
			mpData.incre();
			ResourceInfoCollector.getInstance(ResourceInfoCollector.class).addMoney(-cost);
		}
		else
		{
			new ConstructionProject(mCurBuilding);

			mCurBuilding.setState(State.UnderConstruction);
			
			if(mCurBuilding.getType() == BuildingType.FARM_HOUSE)
				for (FarmLand land : ((FarmHouse)mCurBuilding).getFarmLands()) {
					CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(land);
				}
			else if(mCurBuilding.getType() == BuildingType.RANCH)
				for (RanchLand land : ((Ranch)mCurBuilding).getRanchLands()) {
					CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(land);
				}
		}
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addBuilding(mCurBuilding);
		
		Renderer.getInstance(Renderer.class).setDrawGrid(false);
		CameraController.getInstance(CameraController.class).removeListener(BuildingAdjustBroker.this);
		
		mCurBuilding.setListener(null);
		mCurBuilding = null;
		mBuildUI.setListener(null);
		mBuildUI.setVisible(false);
		mBuildUI = null;
	}
	
	private void cancelBuilding()
	{
		Renderer.getInstance(Renderer.class).setDrawGrid(false);
		Renderer.getInstance(Renderer.class).dettachDrawScissor(mCurBuilding);
		if(mCurBuilding.getType() == BuildingType.FARM_HOUSE)
			for (FarmLand land : ((FarmHouse)mCurBuilding).getFarmLands()) {
				Renderer.getInstance(Renderer.class).dettachDrawScissor(land);
			}
		if(mCurBuilding.getType() == BuildingType.RANCH)
			for (RanchLand land : ((Ranch)mCurBuilding).getRanchLands()) {
				Renderer.getInstance(Renderer.class).dettachDrawScissor(land);
			}
		CollisionDetector.getInstance(CollisionDetector.class).dettachCollisionDetection(mCurBuilding);
		CameraController.getInstance(CameraController.class).removeListener(BuildingAdjustBroker.this);

		mCurBuilding.setListener(null);
		mCurBuilding = null;
		mBuildUI.setListener(null);
		mBuildUI.setVisible(false);
		mBuildUI = null;
	}
	
	
	private void setBuildAjustUI(BuildingAdjustGroup ui)
	{
		if(ui == null)
			return;
		
		mBuildUI = ui;
		mBuildUI.setVisible(true);
		mBuildUI.setListener(new BuildAjustUIListener() {
			
			@Override
			public void confirm() {
				confirmBuilding();
			}
			
			@Override
			public void cancel() {
				cancelBuilding();
			}
		});
		updateUIPos();
		
		CameraController.getInstance(CameraController.class).addListener(this);
	}
	
	private void updateUIPos()
	{
		if(mBuildUI == null)
			return;
		
		AxisAlignedBoundingBox drawAABB = mCurBuilding.getAABBWorld(QuadTreeType.DRAW);
		Vector3 pos = new Vector3(drawAABB.maxX, drawAABB.maxY, 0.0f);
		CameraController.getInstance(CameraController.class).worldToScreen(pos);
		mBuildUI.setPosition(pos.x, pos.y - mBuildUI.getHeight());
	}

	@Override
	public void objBeTouchDown(Entity obj) {
		CameraController.getInstance(CameraController.class).setMovable(false);
		mbBuildingMovable = true;
		mMoveDeltaX = 0.0f;
		mMoveDeltaY = 0.0f;
	}

	@Override
	public void objBeTouchUp(Entity obj) {
		CameraController.getInstance(CameraController.class).setMovable(true);
		mbBuildingMovable = false;
		mMoveDeltaX = 0.0f;
		mMoveDeltaY = 0.0f;
	}

	@Override
	public void objBeTouchDragged(Entity obj, float x, float y, float deltaX, float deltaY) {
		if(mbBuildingMovable)
		{
			mMoveDeltaX += deltaX;
			mMoveDeltaY += deltaY;
			
			int gridDeltaX = (int) (mMoveDeltaX / Settings.UNIT);
			int gridDeltaY = (int) (mMoveDeltaY / Settings.UNIT);
			
			if(Math.abs(gridDeltaX) != 0 || Math.abs(gridDeltaY) != 0)
			{
				AxisAlignedBoundingBox destAABB = new AxisAlignedBoundingBox();
				AxisAlignedBoundingBox collsionAABB = mCurBuilding.getAABBWorld(QuadTreeType.COLLISION);
				float moveDeltaX = gridDeltaX * Settings.UNIT;
				float moveDeltaY = gridDeltaY * Settings.UNIT;
				destAABB.minX = collsionAABB.minX + moveDeltaX;
				destAABB.minY = collsionAABB.minY + moveDeltaY;
				destAABB.maxX = collsionAABB.maxX + moveDeltaX;
				destAABB.maxY = collsionAABB.maxY + moveDeltaY;
				
				List<QuadTreeManageble> excluded = new ArrayList<QuadTreeManageble>();
				excluded.add(mCurBuilding);
				if( !CollisionDetector.getInstance(CollisionDetector.class).detect(destAABB, null, excluded) )
				{
					mCurBuilding.translate(moveDeltaX, moveDeltaY);
					mMoveDeltaX -= gridDeltaX * Settings.UNIT; 
					mMoveDeltaY -= gridDeltaY * Settings.UNIT; 
					updateUIPos();
				}
			}
		}
	}

	@Override
	public void cameraMoved(float deltaX, float deltaY) {
		updateUIPos();
	}

	@Override
	public void cameraZoomed(float prevWidth, float prevHeight, float curWidth,
			float curHeight) {
		updateUIPos();
	}

	@Override
	public void objBeTapped(Entity obj) {
		
	}
	
}

package com.townSimulator.game.logic;

import com.badlogic.gdx.utils.Array;
import com.townSimulator.game.objs.BaseObject;
import com.townSimulator.game.objs.BaseObjectListener;
import com.townSimulator.game.objs.Building;
import com.townSimulator.game.scene.CameraController;
import com.townSimulator.game.scene.QuadTreeManageble;
import com.townSimulator.game.scene.QuadTreeType;
import com.townSimulator.game.scene.SceneManager;
import com.townSimulator.utility.AxisAlignedBoundingBox;
import com.townSimulator.utility.Settings;

public class BuildHelper implements BaseObjectListener{
	private static 	BuildHelper mInstance;
	private 		Building	mCurBuilding;
	private 		boolean		mbBuildingMovable = false;
	private			float		mMoveDeltaX = 0.0f;
	private			float		mMoveDeltaY = 0.0f;
	
	public static synchronized BuildHelper getInstance()
	{
		if(mInstance == null)
			mInstance = new BuildHelper();
		return mInstance;
	}
	
	public void setBuilding(Building building)
	{
		mCurBuilding = building;
		
		mCurBuilding.setListener(this);
	}

	@Override
	public void objBeTouchDown(BaseObject obj) {
		//System.out.println("Touch Down");
		CameraController.getInstance().setMovable(false);
		mbBuildingMovable = true;
		mMoveDeltaX = 0.0f;
		mMoveDeltaY = 0.0f;
	}

	@Override
	public void objBeTouchUp(BaseObject obj) {
		//System.out.println("Touch Up");
		CameraController.getInstance().setMovable(true);
		mbBuildingMovable = false;
		mMoveDeltaX = 0.0f;
		mMoveDeltaY = 0.0f;
		SceneManager.getInstance().setDrawGrid(false);
	}

	@Override
	public void objBeTouchDragged(BaseObject obj, float x, float y, float deltaX, float deltaY) {
		//System.out.println("Touch Dragged " + x + " " + y + " " + deltaX + " " + deltaY);
		if(mbBuildingMovable)
		{
			mMoveDeltaX += deltaX;
			mMoveDeltaY += deltaY;
			
			int gridDeltaX = (int) (mMoveDeltaX / Settings.UNIT);
			int gridDeltaY = (int) (mMoveDeltaY / Settings.UNIT);
			
			if(Math.abs(gridDeltaX) != 0 || Math.abs(gridDeltaY) != 0)
			{
				//System.out.println("Move " + mMoveDeltaX + " " + mMoveDeltaY );
				AxisAlignedBoundingBox destAABB = new AxisAlignedBoundingBox();
				AxisAlignedBoundingBox collsionAABB = mCurBuilding.getBoundingBox(QuadTreeType.COLLISION);
				float moveDeltaX = gridDeltaX * Settings.UNIT;
				float moveDeltaY = gridDeltaY * Settings.UNIT;
				destAABB.minX = collsionAABB.minX + moveDeltaX;
				destAABB.minY = collsionAABB.minY + moveDeltaY;
				destAABB.maxX = collsionAABB.maxX + moveDeltaX;
				destAABB.maxY = collsionAABB.maxY + moveDeltaY;
				
				Array<QuadTreeManageble> excluded = new Array<QuadTreeManageble>();
				excluded.add(mCurBuilding);
				if( !SceneManager.getInstance().getCollisionDetector().detectIntersection(destAABB, null, excluded) )
				{
					mCurBuilding.translate(moveDeltaX, moveDeltaY);
					mMoveDeltaX -= gridDeltaX * Settings.UNIT; 
					mMoveDeltaY -= gridDeltaY * Settings.UNIT; 
				}
			}
		}
	}
	
}

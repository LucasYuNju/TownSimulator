package com.TownSimulator.utility;

import com.TownSimulator.collision.CollisionDetector;
import com.badlogic.gdx.math.Vector2;

public class BuildingAdjustHelper {
	
	public static Vector2 searchBuildingPosition(float searchOriginX, float searchOriginY, AxisAlignedBoundingBox buildingLocalAABB)
	{
//		Building newBuildingObject = EntityFactory.createBuilding(type);
		int gridX = (int) (searchOriginX / Settings.UNIT);
		int gridY = (int) (searchOriginY / Settings.UNIT);
		int gridSerchSize = 0;
//		AxisAlignedBoundingBox buildingCollisionAABB;
//		if(type == BuildingType.FarmHouse)
//			buildingCollisionAABB = ((FarmHouse)newBuildingObject).getCollisionAABBLocalWithLands();
//		else if(type == BuildingType.Ranch)
//			buildingCollisionAABB = ((Ranch)newBuildingObject).getCollisionAABBLocalWithLands();
//		else
//			buildingCollisionAABB = newBuildingObject.getCollisionAABBLocal();
		
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
					aabb.minX = posX + buildingLocalAABB.minX;
					aabb.minY = posY + buildingLocalAABB.minY;
					aabb.maxX = posX + buildingLocalAABB.maxX;
					aabb.maxY = posY + buildingLocalAABB.maxY;
					if( !CollisionDetector.getInstance(CollisionDetector.class).detect(aabb) )
						bPosFind = true;
				}
			}
			gridSerchSize ++;
			
		}
//		newBuildingObject.destroy();
		
		Vector2 pos = new Vector2();
		pos.x = posX;
		pos.y = posY;
		return pos;
	}
}

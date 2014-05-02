package com.TownSimulator.collision;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.entity.Entity;
import com.TownSimulator.io.InputMgr;
import com.TownSimulator.io.InputMgrListenerBaseImpl;
import com.TownSimulator.map.Map;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.SingletonPublisher;
import com.TownSimulator.utility.quadtree.QuadTree;
import com.TownSimulator.utility.quadtree.QuadTreeManageble;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class CollisionDetector extends SingletonPublisher<CollisionDetectorListener>{
	private QuadTree		mCollsionDetector;
	private	Array<Entity> 	mEventsListeningObjs;
	private float touchDownXWorld;
	private float touchDownYWorld;
	
	private CollisionDetector()
	{
		mCollsionDetector = new QuadTree(QuadTreeType.COLLISION, 0.0f, 0.0f, Map.MAP_WIDTH * Settings.UNIT, Map.MAP_HEIGHT * Settings.UNIT);
		mEventsListeningObjs = new Array<Entity>();
		
		InputMgr.getInstance(InputMgr.class).addListener(new InputMgrListenerBaseImpl()
		{

			@Override
			public boolean touchDown(float screenX, float screenY, int pointer,
					int button) {
				Vector3 pos = new Vector3(screenX, screenY, 0.0f);
				CameraController.getInstance(CameraController.class).screenToWorld(pos);
				touchDownWorldSpace(pos.x, pos.y);
				return true;
			}

			@Override
			public boolean touchUp(float screenX, float screenY, int pointer,
					int button) {
				Vector3 pos = new Vector3(screenX, screenY, 0.0f);
				CameraController.getInstance(CameraController.class).screenToWorld(pos);
				touchUpWorldSpace(pos.x, pos.y);
				return true;
			}

			@Override
			public void touchDragged(float screenX, float screenY, float deltaX, float deltaY, int pointer) {
				Vector3 pos = new Vector3(screenX, screenY, 0.0f);
				CameraController.getInstance(CameraController.class).screenToWorld(pos);
				touchDraggedWorldSpace(	pos.x, pos.y,
										CameraController.getInstance(CameraController.class).screenToWorldDeltaX(deltaX),
										CameraController.getInstance(CameraController.class).screenToWorldDeltaX(-deltaY));
			}

			
			
		});
	}
	
	private void touchDownWorldSpace(float x, float y)
	{
		mEventsListeningObjs.clear();
		
		Array<QuadTreeManageble> objs = new Array<QuadTreeManageble>();
		if(mCollsionDetector.detectIntersection(x, y, objs))
		{
			for (int i = 0; i < objs.size; i++) {
				if(objs.get(i) instanceof Entity)
				{
					Entity obj = (Entity)objs.get(i);
					if(obj.detectTouchDown())
						mEventsListeningObjs.add(obj);
				}
			}
		}
		
		touchDownXWorld = x;
		touchDownYWorld = y;
	}
	
	private void touchUpWorldSpace(float x, float y)
	{
		for (int i = 0; i < mEventsListeningObjs.size; i++) {
			mEventsListeningObjs.get(i).detectTouchUp();
		}
		
		if(x == touchDownXWorld && y == touchDownYWorld)
		{
			if(mEventsListeningObjs.size == 0)
			{
				for (int i = 0; i < mListeners.size; i++) {
					mListeners.get(i).emptyTapped();
				}
			}
			else
			{
				for (int i = 0; i < mEventsListeningObjs.size; i++) {
					mEventsListeningObjs.get(i).detectTapped();
				}
			}
			
		}
			
	}
	
	private void touchDraggedWorldSpace(float x, float y, float deltaX, float deltaY)
	{
		for (int i = 0; i < mEventsListeningObjs.size; i++) {
			mEventsListeningObjs.get(i).detectTouchDragged(x, y, deltaX, deltaY);
		}
	}
	
	public boolean attachCollisionDetection(QuadTreeManageble obj)
	{
		return mCollsionDetector.addManageble(obj);
	}
	
	public void dettachCollisionDetection(QuadTreeManageble obj)
	{
		obj.dettachQuadTree(QuadTreeType.COLLISION);
	}
	
	public void updateCollisionDetector(QuadTreeManageble obj)
	{
		dettachCollisionDetection(obj);
		attachCollisionDetection(obj);
	}
	
	public boolean detect(QuadTreeManageble obj)
	{
		return mCollsionDetector.detectIntersection(obj);
	}
	
	public boolean detect(QuadTreeManageble obj, Array<QuadTreeManageble> collideObjs)
	{
		return mCollsionDetector.detectIntersection(obj, collideObjs);
	}
	
	public boolean detect(QuadTreeManageble obj, Array<QuadTreeManageble> collideObjs, Array<QuadTreeManageble> excludedObjs)
	{
		return mCollsionDetector.detectIntersection(obj, collideObjs, excludedObjs);
	}
	
	public boolean detect(AxisAlignedBoundingBox aabb)
	{
		return mCollsionDetector.detectIntersection(aabb);
	}
	
	public boolean detect(AxisAlignedBoundingBox aabb, Array<QuadTreeManageble> collideObjs)
	{
		return mCollsionDetector.detectIntersection(aabb, collideObjs);
	}
	
	public boolean detect(AxisAlignedBoundingBox aabb, Array<QuadTreeManageble> collideObjs, Array<QuadTreeManageble> excludedObjs)
	{
		return mCollsionDetector.detectIntersection(aabb, collideObjs, excludedObjs);
	}
	
	public boolean detect(float x, float y)
	{
		return mCollsionDetector.detectIntersection(x, y);
	}
	
	public boolean detect(float x, float y, Array<QuadTreeManageble> collideObjs)
	{
		return mCollsionDetector.detectIntersection(x, y, collideObjs);
	}
	
	public boolean detect(float x, float y, Array<QuadTreeManageble> collideObjs, Array<QuadTreeManageble> excludedObjs)
	{
		return mCollsionDetector.detectIntersection(x, y, collideObjs, excludedObjs);
	}
}

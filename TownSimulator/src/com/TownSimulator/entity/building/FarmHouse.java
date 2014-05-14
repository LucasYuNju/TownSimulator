package com.TownSimulator.entity.building;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.farmer.FarmerBTN;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.World;
import com.TownSimulator.entity.World.SeasonType;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.FarmViewWindow;
import com.TownSimulator.ui.building.view.SelectBoxListener;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.utils.Array;

public class FarmHouse extends WorkableBuilding implements SelectBoxListener
{
	private static final int MAX_JOB_CNT = 4;
	private CropType curCropType;
	private CropType sowCropType;
	private boolean bSowStart = false;
	private int sowedLandCnt;
	private boolean bReapStart = false; 
	private int reappedLandCnt;
	private boolean bSowed;
	private Array<FarmLand> farmLands;
	private AxisAlignedBoundingBox collisionAABBLocalWithLands;
	private AxisAlignedBoundingBox collisionAABBWorldWithLands;
	private FarmViewWindow farmWindow;
	
	public FarmHouse() {
		super("building_farm_house", BuildingType.FARM_HOUSE, JobType.FARMER);
		//undockedWindow.setSelectBoxListener(this);
		setSowCropType(CropType.Wheat);
		initFarmLands();
		
		collisionAABBLocalWithLands = new AxisAlignedBoundingBox();
		collisionAABBWorldWithLands = new AxisAlignedBoundingBox();
	}

	@Override
	protected WorkableViewWindow createWorkableWindow() {
		farmWindow = UIManager.getInstance(UIManager.class).getGameUI().createFarmViewWindow(MAX_JOB_CNT);
		farmWindow.setSelectBoxListener(this);
		return farmWindow;
	}

	

	@Override
	protected int getMaxJobCnt() {
		return MAX_JOB_CNT;
	}

	@Override
	protected BehaviorTreeNode createBehavior(Man man) {
		return new FarmerBTN(man);
	}
	
//	@Override
//	public void addWorker(Man man) {
//		super.addWorker(man);
//		man.setBehavior(new FarmerBTN(man));
//	}

	private void initFarmLands()
	{
		farmLands = new Array<FarmLand>();
		for (int i = 0; i < 9; i++) {
			FarmLand farmLand = new FarmLand();
			farmLands.add(farmLand);
		}
		
		updateFarmLandsPos();
	}
	
	private void updateFarmLandsPos()
	{
		int index = 0;
		float posY = mPosYWorld - Settings.UNIT;
		float posX = mPosXWorld;
//		for (float y = mPosYWorld - Settings.UNIT; y >= mPosYWorld - 3 * Settings.UNIT; y -= Settings.UNIT) {
//			for (float x = mPosXWorld; x <= mPosXWorld + 2 * Settings.UNIT; x += Settings.UNIT) {
//				farmLands.get(i).setPositionWorld(x, y);
//				i++;
//			}
//		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				farmLands.get(index).setPositionWorld(posX, posY);
				posX += Settings.UNIT;
				index++;
			}
			posX = mPosXWorld;
			posY -= Settings.UNIT;
		}
		
	}
	
	private void cropGrow(float deltaTime)
	{
		if(workers.size == 0)
			return;
		float efficiency = 0.0f;
		for (Man man : workers) {
			efficiency += man.getInfo().workEfficency;
		}
		efficiency /= workers.size;
		float timeSpeed = 365.0f / World.SecondPerYear;// day/second
		float fullNeedDays = GameMath.lerp(12.0f * 30.0f, 6.0f * 30.0f, workers.size / maxJobCnt);
		float speed = FarmLand.MAX_CROP_AMOUNT / (fullNeedDays / timeSpeed );
		for (FarmLand land : farmLands) {
			land.addCropAmount(speed * efficiency * deltaTime);
		}
	}
	
	private void cropDie(float deltaTime)
	{
		for (FarmLand land : farmLands) {
			land.cropDie(deltaTime);
		}
	}
	
	private void updateProcess()
	{
		float amount = 0.0f;
		for (FarmLand land : farmLands) {
			amount += land.getCurCropAmount();
		}
		float process = amount / (FarmLand.MAX_CROP_AMOUNT * farmLands.size);
		farmWindow.updateProcessBar(process);
	}
	
//	private void updateUI()
//	{
//		
//	}
	
	@Override
	public void setState(State state) {
		super.setState(state);
		if(state == State.Constructed)
		{
			Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
			{

				@Override
				public void update(float deltaTime) {
					//updateUI();
					
					if(bSowed)
					{
						if(World.getInstance(World.class).getCurSeason() != SeasonType.Winter && bReapStart == false)
							cropGrow(deltaTime);
						else
						{
							if(World.getInstance(World.class).getCurMonth() == 12 
									|| World.getInstance(World.class).getCurMonth() == 1)
								cropDie(deltaTime);
						}
						
						for (FarmLand land : farmLands) {
							land.updateView();
						}
						
						updateProcess();
					}
				}
				
			});
		}
	}

	public Array<FarmLand> getFarmLands()
	{
		return farmLands;
	}
	
	
	@Override
	public void setCollisionAABBLocal(float minX, float minY, float maxX,
			float maxY) {
		super.setCollisionAABBLocal(minX, minY, maxX, maxY);
		
		AxisAlignedBoundingBox aabb = super.getCollisionAABBLocal();
		collisionAABBLocalWithLands.minX = Math.min(0.1f, aabb.minX);
		collisionAABBLocalWithLands.minY = aabb.minY - 3 * Settings.UNIT;
		collisionAABBLocalWithLands.maxX = Math.max(aabb.minX + 3 * Settings.UNIT - 0.2f, aabb.maxX);
		collisionAABBLocalWithLands.maxY = aabb.maxY;
	}

	public AxisAlignedBoundingBox getCollisionAABBLocalWithLands()
	{
		return collisionAABBLocalWithLands;
	}
	
	@Override
	public AxisAlignedBoundingBox getAABBWorld(QuadTreeType type) {
		if(type == QuadTreeType.COLLISION)
		{
			if(buildingState == State.PosUnconfirmed)
			{
				collisionAABBWorldWithLands.minX = collisionAABBLocalWithLands.minX + mPosXWorld;
				collisionAABBWorldWithLands.minY = collisionAABBLocalWithLands.minY + mPosYWorld;
				collisionAABBWorldWithLands.maxX = collisionAABBLocalWithLands.maxX + mPosXWorld;
				collisionAABBWorldWithLands.maxY = collisionAABBLocalWithLands.maxY + mPosYWorld;
				
				return collisionAABBWorldWithLands;
			}
			else
				return mCollisionAABBWorld;
		}
		
		return super.getAABBWorld(type);
	}


	@Override
	public void setPositionWorld(float x, float y) {
		super.setPositionWorld(x, y);
		updateFarmLandsPos();
	}

	public CropType getCurCropType()
	{
		return curCropType;
	}
	
	public void setCurCropType(CropType type)
	{
		curCropType = type;
		for (FarmLand land : farmLands) {
			land.setCropType(type);
		}
	}
	
	public void setSowCropType(CropType type)
	{
		sowCropType = type;
	}
	
	public CropType getSowCropType()
	{
		return sowCropType;
	}
	
	
	public void setSowStart(boolean value)
	{
		bSowStart = value;
		
		if(value == false)
			farmWindow.setCurCropType(curCropType);
	}
	
	public boolean isSowStart()
	{
		return bSowStart;
	}
	
	public void addSowedLand()
	{
		sowedLandCnt = Math.min(sowedLandCnt + 1, farmLands.size);
	}
	
	public int getSowedLandCnt()
	{
		return sowedLandCnt;
	}
	
	public void clearSowedLandCnt(){
		sowedLandCnt=0;
	}
	
	
	
	public void setReapStart(boolean value)
	{
		bReapStart = value;
	}
	
	public boolean isReapStart()
	{
		return bReapStart;
	}
	
	public void addReappedLand()
	{
		reappedLandCnt = Math.min(reappedLandCnt + 1, farmLands.size);
	}
	
	public void clearReappedLandCnt()
	{
		reappedLandCnt = 0;
	}
	
	public int getReappedLandCnt()
	{
		return reappedLandCnt;
	}
	
	public void setSowed(boolean value)
	{
		bSowed = value;
	}
	
	public boolean isSowed()
	{
		return bSowed;
	}

	@Override
	public void selectBoxSelected(String selectedString) {
		setSowCropType(CropType.findWithViewName(selectedString));
	}

}

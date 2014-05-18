package com.TownSimulator.entity.building;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.lumerjack.LumerJackBTN;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.Tree;
import com.TownSimulator.render.Grid;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.render.RendererListener;
import com.TownSimulator.utility.Settings;

public class FellingHouse extends WorkableBuilding {
	public static final int RANGE = 5;
	public static final int MAX_JOB_CNT = 2;
	private RendererListener renderListener;
	
	public FellingHouse() {
		super("building_felling_house", BuildingType.FELLING_HOUSE, JobType.LUMERJACK);
		renderListener = new RendererListener() {
			@Override
			public void renderEnded() {
			}
			
			@Override
			public void renderBegined() {
				if(isSelected || getState() == Building.State.PosUnconfirmed)
					drawRange();
			}
		};
		Renderer.getInstance(Renderer.class).addListener(renderListener);
	}
	
	
	
	@Override
	public void destroy() {
		super.destroy();
		Renderer.getInstance(Renderer.class).removeListener(renderListener);
	}



	private void drawRange()
	{
		int originGridX = (int)(mCollisionAABBWorld.getCenterX() / Settings.UNIT);
		int originGridY = (int)(mCollisionAABBWorld.getCenterY() / Settings.UNIT);
		for (int x = originGridX - RANGE; x <= originGridX + RANGE; x++) {
			for (int y = originGridY - RANGE; y <= originGridY + RANGE; y++) {
				int dstX = Math.abs(x - originGridX);
				int dstY = Math.abs(y - originGridY);
				if(dstX * dstX + dstY * dstY > RANGE * RANGE)
					continue;
				Grid grid = Renderer.getInstance(Renderer.class).allocGrid();
				grid.setColor(1.0f, 0.0f, 0.0f, 0.4f);
				grid.setGridPos(x, y);
				Renderer.getInstance(Renderer.class).draw(grid);
			}
		}
	}
	
	public boolean isInRange(Tree tree)
	{
		int originGridX = (int)(mCollisionAABBWorld.getCenterX() / Settings.UNIT);
		int originGridY = (int)(mCollisionAABBWorld.getCenterY() / Settings.UNIT);
		int x = (int)(tree.getPositionXWorld() / Settings.UNIT);
		int y = (int)(tree.getPositionYWorld() / Settings.UNIT);
		int dstX = Math.abs(x - originGridX);
		int dstY = Math.abs(y - originGridY);
		if(dstX * dstX + dstY * dstY > RANGE * RANGE)
			return false;
		else
			return true;
	}

	@Override
	public boolean detectTouchDown() {
		super.detectTouchDown();
		return true;
	}
	
	@Override
	protected BehaviorTreeNode createBehavior(Man man) {
		return new LumerJackBTN(man);
	}

	@Override
	protected int getMaxJobCnt() {
		return MAX_JOB_CNT;
	}

	/*
	 * 增删工人都应该通知此方法
	 */
//	public void updateViewWindow() {
//		WorkableViewWindow workableViewWindow = (WorkableViewWindow) viewWindow;
//		workableViewWindow.addWorker();
//	}
	
	/*
	 * notification from viewWinodow
	 */
//	@Override
//	public void workerLimitChanged(int limit) {
//		Gdx.app.log("Felling House", "selected limit :" + limit);
//	}
}

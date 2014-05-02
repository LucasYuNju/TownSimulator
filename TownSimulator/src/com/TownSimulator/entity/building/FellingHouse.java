package com.TownSimulator.entity.building;

import com.TownSimulator.entity.JobType;
import com.TownSimulator.render.Grid;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.render.RendererListener;
import com.TownSimulator.utility.Settings;

public class FellingHouse extends WorkingBuilding{
	private static final int RANGE = 5;
	
	public FellingHouse() {
		super("building_felling_house", BuildingType.FELLING_HOUSE, JobType.LUMERJACK, 2);
		
		Renderer.getInstance(Renderer.class).addListener(new RendererListener() {
			
			@Override
			public void renderEnded() {
			}
			
			@Override
			public void renderBegined() {
				if(isSelected)
					drawRange();
			}
		});
	}
	
	private void drawRange()
	{
		int originGridX = (int)(getPositionXWorld() / Settings.UNIT);
		int originGridY = (int)(getPositionYWorld() / Settings.UNIT);
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

	@Override
	public boolean detectTouchDown() {
		super.detectTouchDown();
		return true;
	}
}

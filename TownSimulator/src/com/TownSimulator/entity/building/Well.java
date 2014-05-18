package com.TownSimulator.entity.building;

import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.UndockedWindow;

public class Well extends Building{
	private static final long serialVersionUID = -5096398900245896495L;

	public Well() {
		super("building_well",BuildingType.WELL);
	}

	@Override
	protected UndockedWindow createUndockedWindow() {
		return UIManager.getInstance(UIManager.class).getGameUI().createWellViewWindow();
	}

	@Override
	protected void updateViewWindow() {
	}
}

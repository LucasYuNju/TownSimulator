package com.TownSimulator.entity.building;

import com.TownSimulator.ui.building.view.UndockedWindow;

public class PowerStation extends Building{
	private static final long serialVersionUID = 3192695012825043343L;

	public PowerStation() {
		super("building_power_station", BuildingType.PowerStation);
	}

	@Override
	protected UndockedWindow createUndockedWindow() {
		return null;
	}

}

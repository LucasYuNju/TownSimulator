package com.TownSimulator.ui.building.view;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * 	支持WorkerGroupWindow及监听
 */
public class WorkableViewWindow extends UndockedWindow {
	protected WorkerGroup workerGroup;
	private Label tipsLabel;
	private int numAllowedWorker;
	
	public WorkableViewWindow(BuildingType buildingType, int numAllowedWorker) {
		super(buildingType);
		this.numAllowedWorker = numAllowedWorker;
		
		init();
	}
	
	private void init()
	{
		workerGroup = new WorkerGroup(numAllowedWorker);
		workerGroup.setVisible(true);
		workerGroup.setPosition(MARGIN, MARGIN);
		addActor(workerGroup);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int)(LABEL_HEIGHT * 0.7f));
		labelStyle.fontColor = Color.RED;
		tipsLabel = new Label("", labelStyle);
		tipsLabel.setSize(getWidth() - dynamiteButton.getWidth(), LABEL_HEIGHT);
		tipsLabel.setPosition(0.0f, MARGIN);
		tipsLabel.setAlignment(Align.center);
		addActor(tipsLabel);
		
		addHeader();
		addCloseButton();
		
		float width = Math.max(workerGroup.getWidth(),
				headerLabel.getStyle().font.getBounds(headerLabel.getText()).width + closeButton.getWidth() + MARGIN);
		width = Math.max(width, MIN_WIDTH);
		setSize(width + MARGIN * 2, workerGroup.getHeight() + LABEL_HEIGHT * 2 + MARGIN * 4);
		
		updateLayout();
		
//		workerGroup.setPosition(MARGIN, MARGIN + tipsLabel.getHeight());
//		closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
//		headerLabel.setPosition(MARGIN, getHeight() - LABEL_HEIGHT);
	}
	
	@Override
	protected void updateLayout() {
		super.updateLayout();
		
		if(workerGroup != null && tipsLabel != null)
			workerGroup.setPosition(MARGIN, MARGIN + tipsLabel.getHeight());
	}
	
	public void setWarningMsg(String tips)
	{
		float width = tipsLabel.getStyle().font.getBounds(tips).width + dynamiteButton.getWidth() + MARGIN * 2;
		setSize(Math.max(getWidth(), width), getHeight());
		tipsLabel.setSize(getWidth() - dynamiteButton.getWidth(), LABEL_HEIGHT);
		tipsLabel.setText(tips);
		
		updateLayout();
	}
	
	public boolean addWorker() {
		return workerGroup.addWorker();
	}
	
	public boolean addWorker(int addition) {
		return workerGroup.addWorker(addition);
	}
	
	public void setWorkerGroupListener(WorkerGroupListener workerGroupListener) {
		workerGroup.setListener(workerGroupListener);
	}
	
	public void reLoad(int openJobCnt, int numWorker) {
		workerGroup.reLoad(openJobCnt, numWorker);
	}
}

package com.TownSimulator.ui.building.view;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

//部分代码和ScrollViewWindow重复
public class HospitalViewWindow extends WorkableViewWindow {
	private static final int NUM_LABEL_PER_PAGE = 8;
	List<List<String>> data;
	List<Label> labels;
	List<Label> menuLabels;
	List<String> menuStrings;

	public HospitalViewWindow(int numAllowedWorker) {
		super(BuildingType.Hospital, numAllowedWorker);
		data = new ArrayList<List<String>>();
		initMenuString();
	}
	
	private void initUI() {
		int numLabel = data.size() > NUM_LABEL_PER_PAGE ? data.size() : NUM_LABEL_PER_PAGE;
		int numLabelPerRow = 4;
		if(!data.isEmpty())
			numLabelPerRow = data.get(0).size();
		setSize(Math.max(LABEL_WIDTH * numLabelPerRow + MARGIN * 2 + dynamiteButton.getWidth(), getWidth()), LABEL_HEIGHT * (numLabel+1) + LABEL_HEIGHT + WorkerGroup.HEIGHT + MARGIN * 4);
		getParent().setSize(getWidth(), getHeight());
		addMenuLabel();
		addLabels();
		addCloseButton();
		addHeader();
		
		updateLayout();
	}
	
	public void initMenuString(){
		menuStrings=new ArrayList<String>();
		menuStrings.add(ResourceManager.stringMap.get("NameMenu"));
		menuStrings.add(ResourceManager.stringMap.get("GenderMenu"));
		menuStrings.add(ResourceManager.stringMap.get("AgeMenu"));
		menuStrings.add(ResourceManager.stringMap.get("HealthPointMenu"));
	}
	
	public void addMenuLabel(){
		menuLabels=new ArrayList<Label>();
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (LABEL_HEIGHT* 0.8f));
		labelStyle.fontColor = new Color(254, 67, 101, 255);
		for(int i=0; i<4; i++) {
				Label label = new Label( menuStrings.get(i), labelStyle);
				label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
				label.setPosition(LABEL_WIDTH * i+ MARGIN, getHeight() - HEADER_HEIGHT -LABEL_HEIGHT- MARGIN );
				addActor(label);
				menuLabels.add(label);
		}
	}
	
	public void addLabels() {
		labels = new ArrayList<Label>();
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		for(int i=0; i<data.size(); i++) {
			for(int j=0; j<data.get(i).size(); j++) {
				Label label = new Label(data.get(i).get(j), labelStyle);
				label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
				label.setPosition(LABEL_WIDTH * j + MARGIN, getHeight() - HEADER_HEIGHT - LABEL_HEIGHT * (i+2)- MARGIN * 2);
				addActor(label);
				labels.add(label);
			}
		}
	}
		
	public void updateData(List<List<String>> newData) {
		if(newData == null || newData.isEmpty())
			return;
			
		// data为null或者为空，UI没有初始化过
		if (data.isEmpty()) {
			data = newData;
			initUI();
			updateLabels();
		} 
		//newData和data长度不同，重新添加labels
		else if (data.size() != newData.size()) {
			for(Label label: menuLabels){
				removeActor(label);
			}
			for (Label label : labels) {
				removeActor(label);
			}
			data = newData;
			initUI();
			updateLabels();
		}
		else {
			data = newData;
			updateLabels();
		}
	}
	
	private void updateLabels() {
		for(int i=0; i<data.size(); i++) {
			for(int j=0; j<data.get(0).size(); j++) {
				labels.get(i*data.get(0).size()+j).setText(data.get(i).get(j));
			}
		}
	}
}

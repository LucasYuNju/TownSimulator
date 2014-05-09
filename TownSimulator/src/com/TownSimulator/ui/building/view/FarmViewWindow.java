package com.TownSimulator.ui.building.view;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.CropType;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Layout:
 * 
 * header
 * farmState|	processBar
 * curCrop	|	value
 * nextCrop |	dropDown
 * workers
 * 
 */
public class FarmViewWindow extends WorkableViewWindow{
	private SelectBox<String> dropDown;
	private ProcessBar processBar;
	private TextureRegion buttonBackground;
	private Label curCropLabel;
	//private WorkerGroup builderGroup;
	private float width;
	private float height;
	private SelectBoxListener selectBoxListener;
	
	public FarmViewWindow(int numAllowedWorker) {
		super(BuildingType.FARM_HOUSE, numAllowedWorker);
		buttonBackground = Singleton.getInstance(ResourceManager.class).findTextureRegion("background_button");
		width = ProcessBar.PREFERED_WIDTH + LABEL_WIDTH + MARGIN * 2;
		height = LABEL_HEIGHT * 4 + WorkerGroup.HEIGHT + MARGIN * 2;
		setSize(width, height);
		addRowOne();
		addRowTwo();
		addRowTree();
//		builderGroup = new WorkerGroup(5);
//		builderGroup.setPosition(MARGIN, MARGIN);
//		addActor(builderGroup);

//=======
//		workerGroup = new WorkerGroup(5);
//		workerGroup.setPosition(MARGIN, MARGIN);
//		addActor(workerGroup);
//>>>>>>> branch 'master' of https://github.com/LuciusYu/TownSimulator.git
		addCloseButton();
		addHeader();
	}
	
	private void addRowOne() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		Label label = new Label("state", labelStyle);
		label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setPosition(MARGIN, MARGIN + WorkerGroup.HEIGHT + LABEL_HEIGHT * 2);
		label.setAlignment(Align.left);
		addActor(label);
		
		processBar = new ProcessBar();
		processBar.setPosition(MARGIN + LABEL_WIDTH, MARGIN + WorkerGroup.HEIGHT + LABEL_HEIGHT * 2);
		addActor(processBar);
	}
	
	private void addRowTwo() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		
		float x = MARGIN;
		float y = MARGIN + WorkerGroup.HEIGHT + LABEL_HEIGHT;
		Label label = new Label("curCrop", labelStyle);
		label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setPosition(x, y);
		label.setAlignment(Align.left);
		addActor(label);
		
		x += label.getWidth() + MARGIN;
		curCropLabel = new Label("<Empty>", labelStyle);
		curCropLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		curCropLabel.setPosition(x, y);
		curCropLabel.setAlignment(Align.left);
		addActor(curCropLabel);
		//addDropDown();
	}
	
	private void addRowTree()
	{
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		
		float x = MARGIN;
		float y = MARGIN + WorkerGroup.HEIGHT;
		Label label = new Label("sowCrop", labelStyle);
		label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setPosition(x, y);
		label.setAlignment(Align.left);
		addActor(label);
		
		//addDropDown();
		initDropDown();
		x += label.getWidth() + MARGIN;
		dropDown.setPosition(x, y);
		addActor(dropDown);
	}

	private void initDropDown() {
		SelectBoxStyle style = new SelectBoxStyle();
		style.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		style.fontColor = Color.WHITE;
		style.background = new TextureRegionDrawable(buttonBackground);
		style.scrollStyle = new ScrollPaneStyle();
		style.scrollStyle.background = style.background;
		style.listStyle = new ListStyle();
		style.listStyle.font = style.font;
		style.listStyle.background = style.background;
		style.listStyle.selection = style.background;
		dropDown = new SelectBox<String>(style);
		CropType[] types = CropType.values();
		String[] strs = new String[types.length + 1];
		strs[0] = "<Empty>";
		for (int i = 1; i < strs.length; i++) {
			strs[i] = types[i-1].getViewName();
		}
		dropDown.setItems(strs);
		dropDown.setSize(style.font.getBounds(strs[0]).width, LABEL_HEIGHT);
		//dropDown.setPosition(getWidth() - MARGIN - LABEL_WIDTH, MARGIN + WorkerGroup.HEIGHT + LABEL_WIDTH);
		dropDown.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				selectBoxListener.selectBoxSelected(dropDown.getSelected());
				return false;
			}
		});
		
		//addActor(dropDown);
	}
	
	public void setCurCropType(CropType type)
	{
		if(type != null)
			curCropLabel.setText(type.getViewName());
		else
			curCropLabel.setText("<Empty>");
	}
	
	public void updateProcessBar(float process)
	{
		processBar.setProcess(process);
	}
	
	public void addWorker() {
		workerGroup.addWorker();
	}
	
	@Override
	protected void updatePosition()
	{
		Vector3 pos = new Vector3(buildingPosXWorld, buildingPosYWorld, 0.0f);
		CameraController.getInstance(CameraController.class).worldToScreen(pos);
		float windowX = pos.x - getWidth();
		float windowY = pos.y - getHeight() * 0.5f;
		setPosition(windowX, windowY);
	}

	@Override
	public void setWorkerGroupListener(WorkerGroupListener workerGroupListener) {
		workerGroup.setListener(workerGroupListener);
	}


	
	public void setSelectBoxListener(SelectBoxListener selectBoxListener) {
		this.selectBoxListener = selectBoxListener;
	}
}

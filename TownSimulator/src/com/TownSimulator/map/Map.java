package com.TownSimulator.map;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.camera.CameraListener;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.Grass;
import com.TownSimulator.entity.Tree;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.quadtree.QuadTreeManageble;
import com.TownSimulator.utility.simplex.SimplexNoise;

public class Map extends Singleton implements Serializable{
	private static final long serialVersionUID = 6800548250214016876L;
	public static final int				MAP_WIDTH = 256;
	public static final int				MAP_HEIGHT = 256;
	private float[][] 					mNoiseMap;
	private float[] 					mTreeScaleMap = { 1.0f, 0.8f, 0.6f, 0.0f, 0.0f, 0.0f };
	private int							loadX;
	private int							loadY;
	private transient SimplexNoise 		noiseGenerator;
	private static final Random			rand = new Random(System.nanoTime());
	private int							initCnt;
	private float						initProcess;
	private List<Tree>					trees = new LinkedList<Tree>();
	private int 						prevSeed;
	
	private Map()
	{
		CameraController.getInstance(CameraController.class).addListener(new CameraListener() {
			
			@Override
			public void cameraZoomed(float prevWidth, float prevHeight, float curWidth,
					float curHeight) {
				updateTreeActivity();
			}
			
			@Override
			public void cameraMoved(float deltaX, float deltaY) {
				updateTreeActivity();
			}
		});
	}
	
	public List<Tree> getTrees() {
		return trees;
	}
	
	public void setTrees(List<Tree> trees) {
		this.trees = trees;
	}
	
	private void updateTreeActivity()
	{
		AxisAlignedBoundingBox scissor = CameraController.getInstance(CameraController.class).getCameraViewAABB();
		for(QuadTreeManageble e : Renderer.getInstance(Renderer.class).getVisibleEntities(scissor))
		{
			if(e instanceof Tree)
			{
				((Tree)e).setActive(true);
			}
		}
	}
	
	public float getProcess()
	{
		return initProcess;
	}
	
	private boolean doLoad()
	{
		if(loadX >= MAP_WIDTH)
			return true;
		
		float value = (float) (noiseGenerator.getNoise(loadX, loadY) + 1.0) * 0.5f; 
		mNoiseMap[loadX][loadY] = value;
		
		//Tree
		if(value > 0.6f)
		{
			float scale = mTreeScaleMap[rand.nextInt(mTreeScaleMap.length)];
			if( scale != 0.0f )
			{
				
				float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
				float randY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
				Tree tree = new Tree();
				tree.setScale(scale);
				tree.addHealth(Tree.MAX_HELTH * scale);
				tree.setPositionWorld(	Settings.UNIT * loadX + Settings.UNIT * 0.5f + randX,
										Settings.UNIT * loadY + Settings.UNIT * 0.5f + randY);
				CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(tree);
				Renderer.getInstance(Renderer.class).attachDrawScissor(tree);
				trees.add(tree);
			}
		}
		
		if(rand.nextFloat() <= 0.03)
		{
			float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT ;
			float randY = (rand.nextFloat() - 0.5f) * Settings.UNIT ;
			int grassIndex = rand.nextInt(3);
			Grass grass = new Grass("grass_" + grassIndex);
			grass.setPositionWorld(	Settings.UNIT * loadX + Settings.UNIT * 0.5f + randX,
									Settings.UNIT * loadY + Settings.UNIT * 0.5f + randY);
			Renderer.getInstance(Renderer.class).attachDrawScissor(grass);
		}
		
		loadY++;
		if(loadY >= MAP_HEIGHT)
		{
			loadY = 0;
			loadX++;
			if(loadX >= MAP_WIDTH)
				return false;
		}
		
		initCnt++;
		initProcess = (float)initCnt / (float)(MAP_WIDTH * MAP_HEIGHT);
		
		return true;
	}
	
	public boolean load()
	{
		long start = System.currentTimeMillis();
		long limit = 1000 / 30;
		while(System.currentTimeMillis() - start < limit)
		{
			if( !doLoad() )
				return false;
		}
		
		return true;
	}
	
	public void init(int seed)
	{
		prevSeed = seed;
		mNoiseMap = new float[MAP_WIDTH][MAP_HEIGHT];
		noiseGenerator = new SimplexNoise(128, 0.5, seed);
		loadX = 0;
		loadY = 0;
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		init(prevSeed);
	}
}

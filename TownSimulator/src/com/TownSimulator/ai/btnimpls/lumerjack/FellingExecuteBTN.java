package com.TownSimulator.ai.btnimpls.lumerjack;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManStateType;
import com.TownSimulator.entity.Tree;
import com.TownSimulator.utility.Animation.AnimationListener;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.particles.RandomJetBounceParticleSystem;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.math.Vector2;

public class FellingExecuteBTN extends ActionNode{
	private static final long serialVersionUID = -3010511761059974672L;
	public static int FELLING_CNT = 4;// 每砍FELLING_CNT次获取一次木材
	public float fellingInterval = 2.0f;
	public float fellingAccum = 0.0f;
	public int felllingCntAccum = 0;
	private Man man;
	private FellingInfo fellingInfo;
	private AnimationListener workAnimeListener;
	
	public FellingExecuteBTN(Man man, FellingInfo fellingInfo)
	{
		this.man = man;
		this.fellingInfo = fellingInfo;
		
		workAnimeListener = new AnimationListener() {
			private static final long serialVersionUID = 2768734776856527791L;

			@Override
			public void frameChanged(int curFrameIndex) {
				if(curFrameIndex == 1 && FellingExecuteBTN.this.fellingInfo.fellingTree != null)
				{
					FellingExecuteBTN.this.fellingInfo.fellingTree.shake();
					jetParticles();
				}
			}
		};
		man.getAnimation(ManAnimeType.WORK, true).addListener(workAnimeListener);
		man.getAnimation(ManAnimeType.WORK, false).addListener(workAnimeListener);
		
		fellingInterval = man.getAnimation(ManAnimeType.WORK, false).getLength();
	}
	
	@Override
	public void destroy() {
		man.getAnimation(ManAnimeType.WORK, true).removeListener(workAnimeListener);
		man.getAnimation(ManAnimeType.WORK, false).removeListener(workAnimeListener);
		//System.out.println("FellingExecuteBTN Destroy");
	}
	
	private void jetParticles()
	{
		AxisAlignedBoundingBox drawAABB = fellingInfo.fellingTree.getAABBWorld(QuadTreeType.DRAW);
		float posX = drawAABB.getCenterX();
		float posY = drawAABB.minY + drawAABB.getHeight() * 0.3f;
		Vector2 pos = new Vector2(posX, posY);
		float speed = Settings.UNIT;
		
		RandomJetBounceParticleSystem.createRectPartlces(pos, 0.0f, (float)Math.PI, speed, speed, GameMath.rgbaIntToColor(143, 195, 31, 255), 2.0f, drawAABB.minY, 4);
	}

	private void doFelling(float deltaTime)
	{
		fellingAccum += deltaTime;
		while(fellingAccum >= fellingInterval)
		{
			fellingAccum -= fellingInterval;
			felllingCntAccum ++;
			
			if(felllingCntAccum >= FELLING_CNT)
			{
				fellingAccum = 0.0f;
				felllingCntAccum = 0;
				
				fellingInfo.hasWood = true;
				fellingInfo.fellingTree.addHealth(-Tree.MAX_HELTH * 0.4f);
				if(fellingInfo.fellingTree.getScale() == 0.0f)
				{
					fellingInfo.fellingTree.setCutting(false);
					fellingInfo.fellingTree = null;
				}
				break;
			}
		}
	}
	
	private void felling(float deltaTime)
	{
		man.setMoveDestination(fellingInfo.fellingTree.getPositionXWorld(), fellingInfo.fellingTree.getPositionYWorld());
		man.getInfo().manStates.add( ManStateType.Working );
		
		if( !man.move(deltaTime) )
		{
			doFelling(deltaTime);
			man.getInfo().animeType = ManAnimeType.WORK;
		}
		else
			man.getInfo().animeType = ManAnimeType.MOVE;
	}
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		if(fellingInfo.hasWood)
			return ExecuteResult.FALSE;
		
		felling(deltaTime);
		return ExecuteResult.TRUE;
	}

}

package com.townSimulator.game.render;

import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;

public class RenderBatch {
	private Matrix4						mProjMatrix;
	private SpriteBatch 				mSpriteBatch;
	private Array<GameDrawable> 		mRenderList;
	private Comparator<GameDrawable> 	mSortComparator;
	
	public RenderBatch()
	{
		mProjMatrix = new Matrix4();
		mSpriteBatch = new SpriteBatch();
		mRenderList = new Array<GameDrawable>();
		mSortComparator = new Comparator<GameDrawable>() {

			@Override
			public int compare(GameDrawable d0, GameDrawable d1) {
				return d0.getDepth() > d1.getDepth() ? -1 : 1;
			}
		};
	}
	
	public void setProjectionMatrix(Matrix4 mat)
	{
		mProjMatrix = mat;
	}
	
	public void addDrawable( GameDrawable draw )
	{
		mRenderList.add(draw);
	}
	
	private void sort()
	{
		mRenderList.sort(mSortComparator);
	}
	
	public void doRender()
	{
		sort();
		mSpriteBatch.setProjectionMatrix(mProjMatrix);
		mSpriteBatch.begin();
		for (int i = 0; i < mRenderList.size; i++) {
			mRenderList.get(i).drawSelf(mSpriteBatch);
		}
		mSpriteBatch.end();
		mRenderList.clear();
	}
	
	public void dispose()
	{
		mSpriteBatch.dispose();
	}

}

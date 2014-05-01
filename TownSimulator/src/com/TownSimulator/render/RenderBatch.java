package com.TownSimulator.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.regexp.internal.recompile;

public class RenderBatch {
	private Matrix4					mProjMatrix;
	private SpriteBatch 			mSpriteBatch;
	private ArrayList<Drawable> 		mRenderList;
	private Comparator<Drawable> 	mSortComparator;
	private int drawCnt = 0;
	
	public RenderBatch()
	{
		mProjMatrix = new Matrix4();
		mSpriteBatch = new SpriteBatch();
		mSpriteBatch.maxSpritesInBatch = 100;
		mRenderList = new ArrayList<Drawable>();
		mSortComparator = new Comparator<Drawable>() {

			@Override
			public int compare(Drawable d0, Drawable d1) {
				if(d0.getDepth() == d1.getDepth())
					return 0;
				return d0.getDepth() > d1.getDepth() ? -1 : 1;
			}
		};
	}
	
	public void setProjectionMatrix(Matrix4 mat)
	{
		mProjMatrix = mat;
	}
	
	public void addDrawable( Drawable draw )
	{
//		if(drawCnt < mRenderList.size())
//			mRenderList.set(drawCnt, draw);
//		else
//			mRenderList.add(draw);
//		drawCnt++;
		mRenderList.add(draw);
	}
	
	private void sort()
	{
		//mRenderList.sort(mSortComparator);
		Collections.sort(mRenderList, mSortComparator);
	}
	
	public void doRender()
	{
		sort();
		mSpriteBatch.setProjectionMatrix(mProjMatrix);
		mSpriteBatch.begin();
		for (int i = 0; i < mRenderList.size(); i++) {
			mRenderList.get(i).drawSelf(mSpriteBatch);
		}
		mSpriteBatch.end();
		//drawCnt = 0;
		mRenderList.clear();
	}
	
	public void dispose()
	{
		mSpriteBatch.dispose();
	}

}

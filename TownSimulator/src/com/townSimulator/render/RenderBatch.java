package com.townSimulator.render;

import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.townSimulator.entity.Drawable;

public class RenderBatch {
	private Matrix4					mProjMatrix;
	private SpriteBatch 			mSpriteBatch;
	private Array<Drawable> 		mRenderList;
	private Comparator<Drawable> 	mSortComparator;
	
	public RenderBatch()
	{
		mProjMatrix = new Matrix4();
		mSpriteBatch = new SpriteBatch();
		mRenderList = new Array<Drawable>();
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

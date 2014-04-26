package com.TownSimulator.utility;

import com.badlogic.gdx.utils.Array;

public class Publisher<T> {
	protected Array<T> mListeners = new Array<T>();
	
	public void addListener(T value)
	{
		mListeners.add(value);
	}
	
	public void removeListener(T value)
	{
		mListeners.removeValue(value, false);
	}
}

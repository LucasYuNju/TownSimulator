package com.TownSimulator.utility;

import java.util.ArrayList;
import java.util.List;

public class SingletonPublisher<T> extends Singleton{
	protected List<T> mListeners = new ArrayList<T>();
	
	public void addListener(T value)
	{
		mListeners.add(value);
	}
	
	public void removeListener(T value)
	{
		mListeners.remove(value);
	}
}

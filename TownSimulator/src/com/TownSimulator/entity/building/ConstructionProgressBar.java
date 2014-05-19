package com.TownSimulator.entity.building;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.render.Drawable;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.render.RendererListener;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ConstructionProgressBar implements Drawable{
	private static TextureRegion spBackgroud = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background");
	private static TextureRegion spBar = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("process_bar");
	private static List<ConstructionProgressBar> barsAlloced = new ArrayList<ConstructionProgressBar>();
	private static List<ConstructionProgressBar> barsFree = new ArrayList<ConstructionProgressBar>();
	private Building building;
	private float progress;
	
	static
	{
		Renderer.getInstance(Renderer.class).addListener(new RendererListener() {
			private static final long serialVersionUID = -5940796622618726626L;

			@Override
			public void renderEnded() {
			}
			
			@Override
			public void renderBegined() {
				render();
			}
		});
	}
	
	private ConstructionProgressBar()
	{
		
	}
	
	private static void render()
	{
		for (int i = 0; i < barsAlloced.size(); i++) {
			Renderer.getInstance(Renderer.class).draw( barsAlloced.get(i) );
		}
	}
	
	private static ConstructionProgressBar alloc()
	{
		if(barsFree.size() <= 0)
			barsFree.add(new ConstructionProgressBar());
		ConstructionProgressBar bar = barsFree.remove(barsFree.size() - 1);
		barsAlloced.add(bar);
		return bar;
	}
	
	public void realease()
	{
		if( barsAlloced.remove(this) )
			barsFree.add(this);
		
		setProgress(0.0f);
	}
	
	public static ConstructionProgressBar create(Building building)
	{
		ConstructionProgressBar bar = alloc();
		bar.building = building;
		return bar;
	}
	
	public void setProgress(float progress)
	{
		this.progress = progress;
	}

	@Override
	public void drawSelf(SpriteBatch batch) {
		AxisAlignedBoundingBox aabb = building.getAABBWorld(QuadTreeType.DRAW);
		float width = Settings.UNIT * 2.5f;
		float height = Settings.UNIT * 0.3f;
		float x = aabb.minX - (width - aabb.getWidth()) * 0.5f;
		float y = aabb.maxY + Settings.UNIT * 0.2f;
		float pad = height * 0.1f;
		batch.draw(spBackgroud, x, y, width, height);
		batch.draw(spBar, x + pad, y + pad, (width - pad * 2.0f) * progress, height - pad * 2.0f);
	}

	@Override
	public float getDepth() {
		return -1.0f;
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		spBackgroud = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background");
		spBar = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("process_bar");
	}
}

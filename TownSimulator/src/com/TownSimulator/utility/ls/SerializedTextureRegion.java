package com.TownSimulator.utility.ls;

import java.io.Serializable;

import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SerializedTextureRegion extends TextureRegion implements Serializable{
	private static final long serialVersionUID = -6107910048288626615L;
	private String name;
	
	public SerializedTextureRegion(Texture texture, String name) {
		super(texture);
		this.name = name;
	}
	
	public String getTextureName() {
		return name;
	}
	
	private Object writeReplace() {
		return new SerializationProxy(this);
	}
	
	private static class SerializationProxy implements Serializable {
		private static final long serialVersionUID = -1909611270389194363L;
		private String textureName;
		
		SerializationProxy(SerializedTextureRegion outer) {
			textureName = outer.getTextureName();
		}
		
		private Object readResolve() {
			Texture texture = ResourceManager.getInstance(ResourceManager.class).loadTexture(textureName);
			return new SerializedTextureRegion(texture, textureName);
		}
	}
	
//	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
//		s.defaultReadObject();
//		name = (String) s.readObject();
//		setU(s.readFloat());
//		setV(s.readFloat());
//		setU2(s.readFloat());
//		setV2(s.readFloat());
//		setRegionWidth((int) s.readFloat());
//		setRegionHeight((int) s.readFloat());
//		setTexture(ResourceManager.getInstance(ResourceManager.class).loadTexture(name));
//	}
//	
//	private void writeObject(ObjectOutputStream s) throws IOException {
//		s.defaultWriteObject();
//		s.writeObject(name);
//		s.writeFloat(getU());
//		s.writeFloat(getV());
//		s.writeFloat(getU2());
//		s.writeFloat(getV2());
//		s.writeFloat(getRegionWidth());
//		s.writeFloat(getRegionHeight());
//	}
}

package com.TownSimulator.utility.ls;

import java.io.Serializable;

import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SerializedSprite extends Sprite
	implements Serializable 
{
	private static final long serialVersionUID = -5297772064416603821L;
	private String textureName;

	public SerializedSprite(Texture texture, String textureName) {
		super(texture);
		this.textureName = textureName;
	}
		
	public String getTextureName() {
		return textureName;
	}
	
	private Object writeReplace() {
		return new SerializationProxy(this);
	}
	
	public String toString() {
		return textureName + " " + getTexture();
	}
	
	private static class SerializationProxy implements Serializable {
		private static final long serialVersionUID = -1909611270389194363L;
		private String textureName;
		
		SerializationProxy(SerializedSprite outer) {
			textureName = outer.getTextureName();
		}
		
		private Object readResolve() {
			Texture texture = ResourceManager.getInstance(ResourceManager.class).getTexture(textureName);
			return new SerializedSprite(texture, textureName);
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
//
//		setX(s.readFloat());
//		setY(s.readFloat());
//		setSize(s.readFloat(), s.readFloat());
//		setOrigin(s.readFloat(), s.readFloat());
//		setRotation(s.readFloat());
//		setScale(s.readFloat(), s.readFloat());
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
//
//		s.writeFloat(getX());
//		s.writeFloat(getY());
//		s.writeFloat(getWidth());
//		s.writeFloat(getHeight());
//		s.writeFloat(getOriginX());
//		s.writeFloat(getOriginY());
//		s.writeFloat(getRotation());
//		s.writeFloat(getScaleX());
//		s.writeFloat(getScaleY());
//	}
}

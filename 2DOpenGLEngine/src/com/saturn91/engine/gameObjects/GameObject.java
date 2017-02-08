package com.saturn91.engine.gameObjects;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

public class GameObject {
	private static ArrayList<Entity> entities = new ArrayList<>();
	private static ArrayList<String> knownEntities = new ArrayList<>();
	private float rotZ;
	protected Vector2f position;
	private float scale;
	private float renderLayer;
	private static final float layerDistance = 0.01f;
	private int textureID = -999;
	
	private String thisName;
	
	public GameObject(String entityType, Vector2f position, float scale, int layer) {
		if(knownEntities.contains(entityType)){
			this.scale = scale;
			this.position = position;
			this.thisName = entityType;
			this.renderLayer = layer*layerDistance;
		}else{
			System.err.println("GameObject: unknown Entity <" + entityType + ">!");
		}
	}
	
	public GameObject(String entityType, Vector2f position, float scale, int layer, int textureID) {
		if(knownEntities.contains(entityType)){
			this.scale = scale;
			this.position = position;
			this.thisName = entityType;
			this.renderLayer = layer*layerDistance;
			this.textureID = textureID;
		}else{
			System.err.println("unknown Entity <" + entityType + ">!");
		}
	}
	
	public static boolean checkExisting(String name){
		return knownEntities.contains(name);
	}
	
	public static boolean addEntity(String name, Entity entity){
		if(!knownEntities.contains(name)){
			entities.add(entity);
			knownEntities.add(name);
			return true;
		}else{
			return false;
		}
	}
	
	public static Entity getEntityByName(String name){
		return entities.get(knownEntities.indexOf(name));
	}
	
	public Entity getEntity(){
		return entities.get(knownEntities.indexOf(thisName));
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	
	public String getName(){
		return thisName;
	}
	
	public float getRenderLayer(){
		return renderLayer;
	}
	
	public void setTextureID(int id){
		this.textureID = id;
	}
	
	public int getTextureID(){
		if(this.textureID == -999){
			return getEntityByName(thisName).getModel().getTexture().getID();
		}else{
			return textureID;
		}
	}
}

package com.saturn91.engine.gameObjects;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

public class Animation {
	private TileSet tileSet;
	private int layer = 1;
	private float size = 1;
	private int frameNum = 0;
	private int maxFramNum = 0;
	private GameObject actualObject;
	private ArrayList<Frame> frames;
	
	public Animation(TileSet tileSet) {
		frames = new ArrayList<>();
		this.tileSet = tileSet;
	}
		
	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setSize(float size) {
		this.size = size;
	}
	
	public void addFrame(int numInX, int numInY, Vector2f position, int durationMS){
		GameObject go = tileSet.getTile(numInX, numInY, position, size, layer);
		actualObject = go;
		frames.add(new Frame(go, durationMS));
		maxFramNum ++;
	}
	
	private long lastChange = 0;
	public void tick(){
		if(System.currentTimeMillis() - lastChange >= frames.get(frameNum).durationMS){
			if(frameNum + 1 < maxFramNum){
				frameNum++;
				lastChange = System.currentTimeMillis();
			}else{
				frameNum = 0;
				lastChange = System.currentTimeMillis();
			}
		}
		
		actualObject = frames.get(frameNum).go;
	}
	
	public GameObject getActualFrame(Vector2f position){
		actualObject.setPosition(position);
		return actualObject;
	}
	
	public GameObject getActualFrame(){
		return actualObject;
	}

	private class Frame{
		int durationMS;
		GameObject go;
		
		public Frame(GameObject object, int durationMS){
			this.go = object;
			this.durationMS = durationMS;
		}
	}	
}

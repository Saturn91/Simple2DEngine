package com.saturn91.engine.game.light;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Light {
	private int id;
	private Vector2f position;
	private Vector3f color;
	private float strenght = 3f;	
	private float range = 10f;
	public Light(Vector2f position, Vector3f color) {
		this.position = position;
		this.color = color;
	}
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	public Vector3f getColor() {
		return color;
	}
	public void setColor(Vector3f color) {
		this.color = color;
	}
	public float getStrenght() {
		return strenght;
	}
	public void setStrenght(float strenght) {
		this.strenght = strenght;
	}
	public float getRange() {
		return range;
	}
	public void setRange(float range) {
		this.range = range;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	
}

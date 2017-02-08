package com.saturn91.engine;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.saturn91.engine.gameObjects.GameObject;

class Camera {
	
	private static Vector3f position = new Vector3f(0,0,0);
	private static Vector2f position2D = new Vector2f(0,0);
	private float pitch;	//High of Camera
	private float yaw;		//aiming left an d right
	private static boolean boundTo = false;
	private static GameObject object;
	
	Camera() {
		
	}
	
	public void update(long delta){
		if(!boundTo){
			/*controler.tick(delta);
			position.x = controler.getPosition().x;
			position.y = controler.getPosition().y;*/
		}else{
			position.x = object.getPosition().x;
			position.y = object.getPosition().y;
		}		
	}

	static Vector2f getPosition2D() {
		position2D.x = position.x;
		position2D.y = position.y;
		return position2D;
	}
	
	static Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}
	
	public void setZoom(float zoom){
		position.z = zoom;
	}
	
	static void bindTo(GameObject tobject){
		boundTo = true;
		object = tobject;
	}
	
	static void unbind(){
		boundTo = false;
		object = null;
	}
}

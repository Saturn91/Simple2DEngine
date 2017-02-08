package com.saturn91.engine;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.saturn91.engine.gameObjects.Animation;
import com.saturn91.engine.gameObjects.GameObject;
import com.saturn91.engine.gameObjects.Light;


class Game {
	
	private static ArrayList<GameObject> gameObjects;
	private static ArrayList<Animation> animations;
	private Renderer renderer;
	private static StaticShader shader;
	private Camera camera;
	
	private static Light cameralight = new Light(new Vector2f(0,0), new Vector3f(1f, 1.0f, 1.0f));
	private static Vector3f environmentLight = new Vector3f(0.05f,0.02f,0.05f);
	
	Game() {
		init();
	}
	
	/**
	 * get trough all entities and render them
	 */
	public void render(){
		shader.setPointLights(LightEngine.getLights());
		//Prepare Renderer
		renderer.prepare();
		
		//Start Shaderprogramm
		shader.start();
		
		shader.update();
		
		shader.loadViewMatrix(camera);
		
		for(Animation a: animations){
			a.tick();
			renderer.render(a.getActualFrame(), shader);
		}
		
		for(GameObject g: gameObjects){
			renderer.render(g, shader);
		}
		
		shader.stop();
	}
	
	/**
	 * Change all states of Entities. 
	 */
	public void update(long delta){
		//Camera
		camera.update(delta);
		
	}
	
	static void addGameObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}
	
	static void deleteGameObject(GameObject gameObject){
		gameObjects.remove(gameObject);
	}
	
	static void deleteGameObjectsAll(){
		gameObjects.clear();
	}
	
	static void addAnimation(Animation animation){
		animations.add(animation);
	}
	
	static void deleteAnimation(Animation animation){
		animations.remove(animation);
	}
	
	static void deleteAnimationsAll(){
		animations.clear();
	}
	
	/**
	 * build Game
	 */
	public void init(){
		
		gameObjects = new ArrayList<>();
		animations = new ArrayList<>();
		shader = new StaticShader();
		shader.setEnviromentLight(environmentLight);
		shader.configureCameraLight(cameralight);		
		camera = new Camera();
		renderer = new Renderer(shader);
		renderer.setZoom(10);		
		LightEngine.init(25);
	}		

	static void setEnvironmentLight(Vector3f _environmentLight) {
		shader.setEnviromentLight(_environmentLight);;
	}

	static void setCameralight(Light _cameralight) {
		shader.configureCameraLight(_cameralight);
	}
}

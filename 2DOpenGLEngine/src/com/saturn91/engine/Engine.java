package com.saturn91.engine;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.saturn91.engine.game.Game;
import com.saturn91.engine.game.light.Light;
import com.saturn91.engine.game.light.LightEngine;
import com.saturn91.engine.game.renderer.Camera;
import com.saturn91.engine.gameObjects.Animation;
import com.saturn91.engine.gameObjects.GameObject;

public abstract class Engine implements Runnable{
	
	private GameMainLoop gameLoop;	
	private static int windowWidth;
	private static int windowHeight;
	private static String windowTitle;
	private static int fps = 60;
	
	public void run() {
		gameLoop = new GameMainLoop(windowWidth, windowHeight, windowTitle, fps){
			@Override
			public void update(long delta) {
				updateGame(delta);
			}

			@Override
			public void init() {
				initGame();				
			}

			@Override
			public void onClose() {
				closeThread();				
			}
		};
		startGame();
	}
	
	public Engine(int width, int height, String screenTitle) {
		windowWidth = width;
		windowHeight = height;	
		windowTitle = screenTitle;
	}

	public Engine(int width, int height, String screenTitle, int _fps) {
		windowWidth = width;
		windowHeight = height;	
		windowTitle = screenTitle;
		fps = _fps;
	}	
	
	private void startGame(){
		gameLoop.start();
		gameLoop.close();
	}
	
	public static int getWindowWidth() {
		return windowWidth;
	}

	public static int getWindowHeight() {
		return windowHeight;
	}

	public abstract void updateGame(long delta);	

	public abstract void initGame();
	
	public void addGameObject(GameObject object){
		Game.addGameObject(object);
	}
	
	public void deleteGameObject(GameObject object){
		Game.deleteGameObject(object);
	}
	
	public void deleteGameObjectsAll(){
		Game.deleteGameObjectsAll();
	}
	
	public void addAnimation(Animation ani){
		Game.addAnimation(ani);
	}
	
	public void deleteAnimation(Animation ani){
		Game.deleteAnimation(ani);
	}
	
	public void deleteAnimationsAll(){
		Game.deleteAnimationsAll();
	}
	
	public void addLight(Light light){
		LightEngine.addLight(light);
	}
	
	public void deleteLight(Light light){
		LightEngine.delLight(light.getId());
	}
	
	public void deleteLightsAll(){
		LightEngine.delLightsAll();
	}
	
	public void setCameraLight(Light light){
		Game.setCameralight(light);
	}
	
	public void setEnvironmentLight(Vector3f color){
		Game.setEnvironmentLight(color);
	}
	
	public void cameraBindTo(GameObject object){
		Camera.bindTo(object);
	}
	
	public void setCameraPosition(Vector2f position){
		Camera.getPosition2D().x = position.x;
		Camera.getPosition2D().y = position.y;
	}
	
	public abstract void closeThread();
}

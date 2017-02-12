package com.saturn91.engine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.ImageIOImageData;

import com.saturn91.engine.gameObjects.Animation;
import com.saturn91.engine.gameObjects.GameObject;
import com.saturn91.engine.gameObjects.Light;
import com.saturn91.engine.logger.Log;

public abstract class Engine implements Runnable{
	
	private GameMainLoop gameLoop;	
	private static int windowWidth;
	private static int windowHeight;
	private static String windowTitle;
	private static int fps = 60;
	private static boolean fullscreen = false;
	
	public void run() {
		if(!fullscreen){
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
		}else{
			gameLoop = new GameMainLoop(windowTitle, fps){
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
		}
		
		startGame();
		
	}
	
	public Engine(int width, int height, String screenTitle) {
		windowWidth = width;
		windowHeight = height;	
		windowTitle = screenTitle;
		fullscreen = false;
		onCreate();
	}
	
	public Engine(String screenTitle) {
		windowTitle = screenTitle;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		windowWidth = (int) screenSize.getWidth();
		windowWidth = (int) screenSize.getHeight();
		fullscreen = true;
		onCreate();
	}

	public Engine(int width, int height, String screenTitle, int _fps) {
		windowWidth = width;
		windowHeight = height;	
		windowTitle = screenTitle;
		fps = _fps;
		fullscreen = false;
		onCreate();
	}
	
	public Engine(String screenTitle, int _fps) {
		windowTitle = screenTitle;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		windowWidth = (int) screenSize.getWidth();
		windowWidth = (int) screenSize.getHeight();
		fps = _fps;
		fullscreen = true;
		onCreate();
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
	
	public Light getCameraLight(){
		return Game.getCameraLight();
	}
	
	public void setZoom(float zoom){
		Game.setZoom(zoom);
	}
	
	@Deprecated
	public void setTaskBarIcon(String pathTo16x16pxIcon, String pathTo32x32pxIcon){
		try {
			ByteBuffer[] list = new ByteBuffer[] {
		        convertImageData(pathTo16x16pxIcon),
		        convertImageData(pathTo32x32pxIcon)
		    };
			gameLoop.setIcons(list);
		} catch (Exception e) {
			e.printStackTrace();
			Log.printErrorLn("not able to load Icons", getClass().getName(), 1);
		}
	}
	
	private ByteBuffer convertImageData(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        ImageIO.write(image, "png", out);
	        return ByteBuffer.wrap(out.toByteArray()); 
		} catch (Exception e) {
			e.printStackTrace();
			Log.printErrorLn("not able to load <" + path + ">", getClass().getName() , 1);
		}	    
	    return null;
	}
	
	public abstract void onCreate();
	
	public abstract void closeThread();
}

package com.saturn91.engine;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;

import com.saturn91.engine.gameObjects.Animation;
import com.saturn91.engine.gameObjects.GameObject;
import com.saturn91.engine.gameObjects.Light;
import com.saturn91.engine.logger.Log;

abstract class GameMainLoop {
	private String className = this.getClass().getName();
	private String screenTitle;
	private int windowWidth;
	private int windowHeight;
	private int fps;
	private boolean fullscreen = false;
	
	private DisplayManager display;
	
	private Game game;
	
	GameMainLoop(int width, int height, String screenTitle, int fps){
		this.screenTitle = screenTitle;
		this.windowWidth = width;
		this.windowHeight = height;
		this.fps = fps;
		fullscreen = false;
	}
	
	GameMainLoop(String screenTitle, int fps){
		this.screenTitle = screenTitle;
		this.fps = fps;
		fullscreen = true;
	}
	
	public void start(){
		if(fullscreen){
			display = new DisplayManager(screenTitle, fps);
		}else{
			display = new DisplayManager(windowWidth, windowHeight, screenTitle, fps);
		}
		
		game = new Game();
		init();
		gameLoop();
		display.closeDisplay();		
	}
	
	private void gameLoop(){
		while(!Display.isCloseRequested()){
			update(getTick());
			game.update(delta);
			game.render();
			display.updateDisplay();
		}
	}
	
	private int tickCounter = 0;
	private long lastTick = 0;
	private long nowTime;
	private long delta;
	private long longestDelta;
	private long lastTimeTickLine = 0;
	private long getTick(){
		if(lastTimeTickLine == 0){
			lastTimeTickLine = System.currentTimeMillis();
			lastTick = System.currentTimeMillis();
		}
		nowTime = System.currentTimeMillis();
		tickCounter ++;
		delta = nowTime - lastTick;
		if(delta > longestDelta){
			longestDelta = delta;
		}
		if(nowTime - lastTimeTickLine >= 1000){
			Log.printLn("ticks: " + tickCounter + " longest delta: " + longestDelta + "ms", className, 0);
			tickCounter = 0;
			longestDelta = 0;
			lastTimeTickLine = nowTime;
		}
		lastTick = System.currentTimeMillis();
		return delta;
	}

	public abstract void update(long delta);
	
	public abstract void init();

	public void close(){
		onClose();
	}
	
	public void setIcons(ByteBuffer[] list){
		display.setIcons(list);
	}
	
	public abstract void onClose();
}

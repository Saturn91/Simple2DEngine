package com.saturn91.engine;

import org.lwjgl.opengl.Display;

import com.saturn91.engine.game.Game;
import com.saturn91.engine.game.light.Light;
import com.saturn91.engine.gameObjects.Animation;
import com.saturn91.engine.gameObjects.GameObject;
import com.saturn91.engine.logger.Log;

abstract class GameMainLoop {
	private String className = this.getClass().getName();
	private String screenTitle;
	private int windowWidth;
	private int windowHeight;
	private int fps;
	
	private DisplayManager display;
	
	private Game game;
	
	GameMainLoop(int width, int height, String screenTitle, int fps){
		this.screenTitle = screenTitle;
		this.windowWidth = width;
		this.windowHeight = height;
		this.fps = fps;
	}
	
	GameMainLoop(int width, int height, String screenTitle){
		this.screenTitle = screenTitle;
		this.windowWidth = width;
		this.windowHeight = height;
		this.fps = 60;
	}
	
	
	public void start(){
		display = new DisplayManager(windowWidth, windowHeight, screenTitle, fps);
		game = new Game();
		init();
		GameLoop();
		display.closeDisplay();		
	}
	
	public void GameLoop(){
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
		
	}
}

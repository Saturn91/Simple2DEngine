package com.saturn91.engine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.ByteBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

class DisplayManager {
	private int width;
	private int height;
	private String screentitle;
	private int fps;
	private ByteBuffer[] list;
	
	DisplayManager(int width, int height, String screentitle, int fps) {
		this.width = width;
		this.height = height;
		this.screentitle = screentitle;
		this.fps = fps;
		setupDisplay(false);
	}
	
	DisplayManager(String screentitle, int fps) {
		this.screentitle = screentitle;
		this.fps = fps;
		setupDisplay(true);
	}

	private void setupDisplay(boolean fullscreen) {
		//Constructor takes Version of OpenGL, 
		//then we set forward compatible (for newer Versions true 
		//and an other setting...
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
		
		try {
			if(fullscreen){
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				this.width = (int) screenSize.getWidth()+3;
				this.height = (int) screenSize.getHeight()+10;
				Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
				Display.create(new PixelFormat(), attribs);
				Display.setTitle(screentitle);
			}else{
				if(list!=null){
					Display.setIcon(list);
				}
				Display.setDisplayMode(new DisplayMode(width, height));
				Display.create(new PixelFormat(), attribs);
				Display.setTitle(screentitle);
			}			
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Say OpwnGL where in the Display it can render the content (vgl Canvas!)
		GL11.glViewport(0, 0, width, height);
		
	}
	
	public void updateDisplay(){
		
		Display.sync(fps);
		Display.update();
	}
	
	public void setIcons(ByteBuffer[] list){
		this.list = list;
	}
	
	public void closeDisplay(){
		Display.destroy();
	}
}

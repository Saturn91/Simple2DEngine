package com.saturn91.engine.gameObjects;

public class Constants {
	
	static float[] QuadVerticies(float width, float height){
		float output[] = {
		-0.5f*width,0.5f*height,0f,		//TopLeft	
		-0.5f*width,-0.5f*height,0f,	//DownLeft
		0.5f*width,-0.5f*height,-0f,	//DownRight
		0.5f*width,0.5f*height,-0f};	//UpRight
		return output;
	}
	
	static float[] TextureCords(){
		float output[] = {
			0,0,
			0,1,
			1,1,
			1,0};
		return output;
	}
	
	static int[] QuadIndices(){
		int[] indices = {
				0,1,3,	
				3,1,2
		};
		return indices;
	}
}

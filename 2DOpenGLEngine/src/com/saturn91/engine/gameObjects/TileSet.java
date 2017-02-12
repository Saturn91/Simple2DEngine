package com.saturn91.engine.gameObjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;

import com.saturn91.engine.logger.Log;

public class TileSet {
	private String name;
	private static ArrayList<String> defindedNames = new ArrayList<>();
	private int textureID;
	private int tileWidth;
	private int tileHeight;
	private int imgWidth;
	private int imgHeight;
	private static Loader loader = new Loader();
	
	private int normalizedSizeofOne = 32;
	
	public TileSet(String name, String filePath, int tileWidth, int tileHeight, int normilizedSize) {
		if(!defindedNames.contains(name)){
			this.name = name;
			defindedNames.add(name);
			this.tileWidth = tileWidth;
			this.tileHeight = tileHeight;
			this.normalizedSizeofOne = normilizedSize;
			textureID = loadImage(filePath);
			if(!checkValues()){
				Log.printErrorLn("Tileset: <" + filePath + "> wrong value while loading!", getClass().getName(), 1);
			}
		}else{
			Log.printErrorLn("Tileset: <" + name + "> is already a defined Tileset!", getClass().getName(), 1);
		}
	}
	
	private int loadImage(String filePath) {
		try {
			BufferedImage texturePNG = ImageIO.read(new File(filePath + ".png"));
			imgWidth = texturePNG.getWidth();
			imgHeight = texturePNG.getHeight();			
		} catch (Exception e) {
			Log.printErrorLn("Tileset: no File <" + filePath + ".png> found", getClass().getName(), 1);
		}
		return loader.loadTexture(filePath);	
	}

	private boolean checkValues(){
		
		boolean error = false;
		for(int i = 1; i< 11; i++){
			
			if(imgWidth%(2*i) == 0){
				break;
			}
			
			if(imgWidth%(2*i)<1){
				error = true;
				Log.printErrorLn("Tileset: texturewidth should be 2^n (i.e. 1024)", getClass().getName(), 1);
				break;
			}
			
		}
		
		for(int i = 1; i< 11; i++){
			if(imgHeight%(2*i) == 0){
				break;
			}
			
			if(imgHeight%(2*i)<1){
				error = true;
				Log.printErrorLn("Tileset: textureheight should be 2^n (i.e. 1024)", getClass().getName(), 1);
				break;
			}			
		}
		
		if(imgWidth%tileWidth != 0 || imgHeight%tileHeight != 0){
			Log.printErrorLn("Tileset: TextureHeight%TextureWidth should not be 0!", getClass().getName(), 1);
			error = true;
		}
		
		return !error;
	}
	
	public GameObject getTile(int numInX, int numInY, Vector2f position, float scale, int layer){
		String generatedName = name + numInX + numInY;
		float tileWidth_normalized = (float)tileWidth/ (float) normalizedSizeofOne;
		float tileHeight_normalized = (float) tileHeight/ (float) normalizedSizeofOne;
		RawModel model= loader.loadToVAO(Constants.QuadVerticies(tileWidth_normalized, tileHeight_normalized), getTextureChords(numInX, numInY), Constants.QuadIndices());
		TexturedModel staticModel = new TexturedModel(model, null);
		Entity entity = new Entity(staticModel);
		if(!GameObject.checkExisting(generatedName)){
			GameObject.addEntity(generatedName, entity);
		}
		return new GameObject(generatedName, position, scale, layer, textureID);
	}
	
	public float[] getTextureChords(float numInX, float numInY){
		
		if(tileWidth*numInX >= imgWidth){
			Log.printErrorLn("Tileset: X: <" + numInX + "> is to big!", getClass().getName(), 1);
		}
		
		if(tileHeight*numInY >= imgHeight){
			Log.printErrorLn("Tileset: Y: <" + numInY + "> is to big!", getClass().getName(), 1);
		}
		float width = ((float) tileWidth)/((float) imgWidth);
		float height = ((float) tileHeight)/((float) imgHeight);
		
		float textureChords[] = {
				numInX*width,		numInY*height,
				numInX*width,		numInY*height+height,
				numInX*width+width,	numInY*height+height,
				numInX*width+width,	numInY*height	
		};
	
		return textureChords;
	}

	public int getTextureID() {
		return textureID;
	}
}

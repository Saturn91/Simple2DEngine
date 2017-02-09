package com.saturn91.engine.gameObjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;

public class TileSet {
	private String name;
	private static ArrayList<String> defindedNames = new ArrayList<>();
	private int textureID;
	private int tileWidth;
	private int tileHeight;
	private int imgWidth;
	private int imgHeight;
	private static Loader loader = new Loader();
	
	public TileSet(String name, String filePath, int tileWidth, int tileHeight) {
		if(!defindedNames.contains(name)){
			this.name = name;
			defindedNames.add(name);
			this.tileWidth = tileWidth;
			this.tileHeight = tileHeight;
			textureID = loadImage(filePath);
			if(!checkValues()){
				System.err.println("Tileset: <" + filePath + "> wrong value while loading!");
			}
		}else{
			System.err.println("Tileset: <" + name + "> is already a defined Tileset!");
		}
	}
	
	private int loadImage(String filePath) {
		try {
			BufferedImage texturePNG = ImageIO.read(new File(filePath + ".png"));
			imgWidth = texturePNG.getWidth();
			imgHeight = texturePNG.getHeight();			
		} catch (Exception e) {
			System.err.println("Tileset: no File <" + filePath + ".png> found");
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
				System.err.println("Tileset: texturewidth should be 2^n (z.B. 1024");
				break;
			}
			
		}
		
		for(int i = 1; i< 11; i++){
			if(imgHeight%(2*i) == 0){
				break;
			}
			
			if(imgHeight%(2*i)<1){
				error = true;
				System.err.println("Tileset: textureheight should be 2^n (z.B. 1024");
				break;
			}			
		}
		
		if(imgWidth%tileWidth != 0 || imgHeight%tileHeight != 0){
			System.err.println("Tileset: TextureHeight%TextureWidth should be 0!");
			System.err.println("Tileset: TextureWidth%TextureWidth should be 0!");
			error = true;
		}
		
		return !error;
	}
	
	public GameObject getTile(int numInX, int numInY, Vector2f position, float scale, int layer){
		String generatedName = name + numInX + numInY;
		RawModel model= loader.loadToVAO(Constants.QuadVerticies(1, 1), getTextureChords(numInX, numInY), Constants.QuadIndices());
		TexturedModel staticModel = new TexturedModel(model, null);
		Entity entity = new Entity(staticModel);
		if(!GameObject.checkExisting(generatedName)){
			GameObject.addEntity(generatedName, entity);
		}
		return new GameObject(generatedName, position, scale, layer, textureID);
	}
	
	public float[] getTextureChords(float numInX, float numInY){
		
		if(tileWidth*numInX >= imgWidth){
			System.err.println("Tileset: X: <" + numInX + "> is to big!");
		}
		
		if(tileHeight*numInY >= imgHeight){
			System.err.println("Tileset: Y: <" + numInY + "> is to big!");
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

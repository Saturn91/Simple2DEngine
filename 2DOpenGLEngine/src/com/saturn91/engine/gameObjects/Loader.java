/*
 * VBO's:
 * 0 = Position
 * 1 = Texturechords
 */

package com.saturn91.engine.gameObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

class Loader {
	
	private static List<Integer> vaos = new ArrayList<>();
	private static List<Integer> vbos = new ArrayList<>();
	private static List<Integer> textures = new ArrayList<>();
	
	public RawModel loadToVAO(float[] positions, float[] textureChoords, int[] indices){
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);			//we save the Position in list 0!
		storeDataInAttributeList(1, 2, textureChoords);		//we save the textures in list 1!		
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	public int loadTexture(String filePath){
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream(filePath + ".png"));
		} catch (FileNotFoundException e) {
			System.err.println("Loader: not able to load: " + filePath + ".png");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Loader: not able to load: " + filePath + ".png");
			e.printStackTrace();
		}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	static void cleanUp(){
		for(int vao: vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo: vbos){
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int texture: textures){
			GL11.glDeleteTextures(texture);
		}
	}
	
	private int createVAO(){
		int voaID = GL30.glGenVertexArrays();
		vaos.add(voaID);
		GL30.glBindVertexArray(voaID);
		return voaID;
	}
	
	private void storeDataInAttributeList(int attributNumber, int coordinateSize, float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);	//unbind Buffer
	}
	
	private void unbindVAO(){
		GL30.glBindVertexArray(0); //unbind Vertex
	}
	
	private void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDatainIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDatainIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}

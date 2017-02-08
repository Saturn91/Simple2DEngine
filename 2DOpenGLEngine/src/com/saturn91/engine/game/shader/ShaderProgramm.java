package com.saturn91.engine.game.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.saturn91.engine.Engine;

public abstract class ShaderProgramm {
	
	protected int programmID;
	private int vertexShaderID;
	private int fragmentShaderID;
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgramm(String vertexFile, String fragmentFile){
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programmID = GL20.glCreateProgram();
		GL20.glAttachShader(programmID, vertexShaderID);
		GL20.glAttachShader(programmID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programmID);
		GL20.glValidateProgram(programmID);
		getAllUniformLocations();
		
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName){
		return GL20.glGetUniformLocation(programmID, uniformName);
	}
	
	public void setShaderVariable2f(String name, Vector2f vector){
	    GL20.glUniform2f(getUniformLocation(name), vector.x, vector.y);
	}
	
	public void setShaderVariable3f(String name, Vector3f vector){
	    GL20.glUniform3f(getUniformLocation(name), vector.x, vector.y, vector.z);
	}
	
	public void setShaderVariable4f(String name, Vector4f vector){
	    GL20.glUniform4f(getUniformLocation(name), vector.x, vector.y, vector.z, vector.w);
	}
	
	public void setShaderVariablef(String name, float value){
		GL20.glUniform1f(getUniformLocation(name), value);
	}
	
	public void start(){
		GL20.glUseProgram(programmID);
		setScreenFormat();
	}
	
	public void setScreenFormat(){
		float format = (float) Engine.getWindowWidth()/ (float) Engine.getWindowHeight();
		setShaderVariablef("screenFormat", format);
	}
	
	public void stop(){
		GL20.glUseProgram(0);
	}
	
	public void cleanUp(){
		stop();
		GL20.glDetachShader(programmID, fragmentShaderID);
		GL20.glDetachShader(programmID, vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteProgram(programmID);
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int atrribut, String variableName){
		GL20.glBindAttribLocation(programmID, atrribut, variableName);
	}
	
	protected void loadFloat(int location, float value){
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector(int location, Vector3f vector){
		GL20.glUniform3f(location, vector.x, vector.y, vector.y);
	}
	
	protected void loadBoolean(int location, boolean value){
		float toLoad = 0;
		if(value){
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	protected void loadMatrix(int location, Matrix4f matrix){
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	private static int loadShader(String file, int type){
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine())!= null){
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("ShaderProgramm: Could not find File: <" + file + ">");
			e.printStackTrace();
			System.exit(-1);
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("ShaderProgramm: Could not compile shader: <" + file + ">");
		}
		return shaderID;
	}
}

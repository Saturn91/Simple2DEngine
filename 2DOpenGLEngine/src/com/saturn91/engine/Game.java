package com.saturn91.engine;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.saturn91.engine.gameObjects.Animation;
import com.saturn91.engine.gameObjects.GameObject;
import com.saturn91.engine.gameObjects.Light;
import com.saturn91.engine.logger.Log;


class Game {

	private String pathToVertexShader;
	private String pathToFragmentShader;
	
	boolean shaderAsInputStream = false;

	private static ArrayList<GameObject> gameObjects;
	private static ArrayList<Animation> animations;
	private static Renderer renderer;
	private static StaticShader shader;
	private Camera camera;

	private static Light cameralight = new Light(new Vector2f(0,0), new Vector3f(1f, 1.0f, 1.0f));
	private static Vector3f environmentLight = new Vector3f(0.05f,0.02f,0.05f);

	Game() {
		init();
	}

	/**
	 * get trough all entities and render them
	 */
	public void render(){
		shader.setPointLights(LightEngine.getLights());
		//Prepare Renderer
		renderer.prepare();

		//Start Shaderprogramm
		shader.start();

		shader.update();

		shader.loadViewMatrix(camera);

		for(Animation a: animations){
			a.tick();
			renderer.render(a.getActualFrame(), shader);
		}

		for(GameObject g: gameObjects){
			renderer.render(g, shader);
		}

		shader.stop();
	}

	/**
	 * Change all states of Entities. 
	 */
	public void update(long delta){
		//Camera
		camera.update(delta);

	}

	static void addGameObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}

	static void deleteGameObject(GameObject gameObject){
		gameObjects.remove(gameObject);
	}

	static void deleteGameObjectsAll(){
		gameObjects.clear();
	}

	static void addAnimation(Animation animation){
		animations.add(animation);
	}

	static void deleteAnimation(Animation animation){
		animations.remove(animation);
	}

	static void deleteAnimationsAll(){
		animations.clear();
	}
	
	static Light getCameraLight(){
		return cameralight;
	}
	
	static void setZoom(float zoom){
		renderer.setZoom(zoom);
	}

	/**
	 * build Game
	 */
	public void init(){
		loadShaders();
		gameObjects = new ArrayList<>();
		animations = new ArrayList<>();
		if(shaderAsInputStream){
			shader = new StaticShader(
					getClass().getResourceAsStream("/com/saturn91/engine/vertexShader.txt"), 
					getClass().getResourceAsStream("/com/saturn91/engine/fragmentShader.txt")
			);
		}else{
			shader = new StaticShader(pathToVertexShader, pathToFragmentShader);
		}		
		shader.setEnviromentLight(environmentLight);
		shader.configureCameraLight(cameralight);		
		camera = new Camera();
		renderer = new Renderer(shader);
		renderer.setZoom(10);		
		LightEngine.init(25);
	}		

	static void setEnvironmentLight(Vector3f _environmentLight) {
		shader.setEnviromentLight(_environmentLight);
	}

	static void setCameralight(Light _cameralight) {
		cameralight = _cameralight;
		shader.configureCameraLight(_cameralight);
	}

	public void loadShaders(){
		final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

		if(jarFile.isFile()) {  // Run with JAR file
			shaderAsInputStream = true;
			Log.printLn("Load Shaders from jar", getClass().getName(), 1);
		} else { 
			shaderAsInputStream = false;
			Log.printLn("In IDE, load intern", getClass().getName(), 1);
			pathToVertexShader =  "./src/com/saturn91/engine/vertexShader.txt";
			pathToFragmentShader = "./src/com/saturn91/engine/fragmentShader.txt";
		}
	}
}

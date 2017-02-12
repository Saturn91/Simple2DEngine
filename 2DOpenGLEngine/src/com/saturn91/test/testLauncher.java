package com.saturn91.test;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.saturn91.engine.Engine;
import com.saturn91.engine.SaveSystem;
import com.saturn91.engine.gameObjects.GameObject;
import com.saturn91.engine.gameObjects.Light;
import com.saturn91.engine.gameObjects.TileSet;
import com.saturn91.engine.logger.Log;

public class testLauncher {
	
	private static TileSet tileset;

	public static void main(String[] args) {
		
		Log.setDebugModus(3);
		
		Engine engine = new Engine(1024, 720, "Test Game", 60){
			@Override
			public void updateGame(long delta) {
				update(this, delta);			
			}

			@Override
			public void initGame() {	
				tileset = new TileSet("Standard", "./res/Graphics/TestTileSet", 32, 32, 32);
				object = tileset.getTile(0, 0, new Vector2f(0.5f,0.5f), 1, 0);
				addGameObject(object);
				setEnvironmentLight(new Vector3f(0, 0, 0));
				setCameraLight(new Light(new Vector2f(0,0), new Vector3f(0, 0, 0)));
				addLight(new Light(new Vector2f(0.4f, 0.2f),  new Vector3f(0.5f, 0.5f, 0.5f)));
				//cameraBindTo(object);
				setCameraPosition(new Vector2f(1, 0));
			}

			@Override
			public void closeThread() {
				
			}

			@Override
			public void onCreate() {
							
			}		
		};
		
		new Thread(engine).start();
	}
	
	private static boolean init = true;
	
	private static GameObject object;
	private static void update(Engine engine, long delta) {
				
		object.getPosition().x += (int) delta*0.001f; 
		engine.setZoom(12);
		if(init){
			GameObject object2 = tileset.getTile(0, 1, new Vector2f(0.5f, 0.5f),  1, 1);
			engine.addGameObject(object2);
			init = false;
		}
			
	}

}

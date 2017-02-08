package com.saturn91.test;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.saturn91.engine.Engine;
import com.saturn91.engine.game.light.Light;
import com.saturn91.engine.gameObjects.GameObject;
import com.saturn91.engine.gameObjects.TileSet;
import com.saturn91.engine.logger.Log;

public class testLauncher {

	public static void main(String[] args) {
		
		Engine engine = new Engine(1024, 720, "Test Game", 60){
			
			@Override
			public void updateGame() {
								
			}

			@Override
			public void initGame() {
				TileSet tileset = new TileSet("standartTileset", "Graphics/TestTileSet", 32, 32);		
				GameObject object = tileset.getTile(0, 0, new Vector2f(0.5f,0.5f), 1, 0);
				addGameObject(object);
				setEnvironmentLight(new Vector3f(0, 0, 0));
				setCameraLight(new Light(new Vector2f(0,0), new Vector3f(0, 0, 0)));
				cameraBindTo(object);
			}		
		};	
	}
}

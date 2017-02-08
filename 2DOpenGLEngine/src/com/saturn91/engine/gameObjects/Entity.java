package com.saturn91.engine.gameObjects;

import com.saturn91.engine.game.renderer.TexturedModel;

public class Entity {
	private TexturedModel model;
	
	public Entity(TexturedModel model){
		this.model = model;
	}

	public TexturedModel getModel() {
		return model;
	}	
}

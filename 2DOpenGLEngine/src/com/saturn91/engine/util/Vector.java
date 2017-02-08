package com.saturn91.engine.util;

import org.lwjgl.util.vector.Vector2f;

public class Vector {
	public static Vector2f add(Vector2f vec2f1, Vector2f vec2f2){
		return new Vector2f(vec2f1.x + vec2f2.x, vec2f1.y + vec2f2.y);
	}
}

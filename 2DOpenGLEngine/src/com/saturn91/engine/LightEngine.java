package com.saturn91.engine;

import com.saturn91.engine.gameObjects.Light;

class LightEngine {
	private static Light t_lights[];
	private static boolean lightsStorage[];
	private static int lightCounter;
	
	static void init(int maxLights){
		t_lights = new Light[maxLights];
		lightsStorage = new boolean[maxLights];
		lightCounter = 0;
	}
	
	static int addLight(Light light){
		for(int i = 0; i < t_lights.length; i++){
			if(!lightsStorage[i]){
				lightCounter++;
				lightsStorage[i] = true;
				t_lights[i] = light;
				light.setId(i);
				return i;
			}
		}
		System.out.println("maxNum of Lights is " + t_lights.length + "!");
		return -1;
	}
	
	static int setLight(int index, Light light){
		lightCounter++;
		lightsStorage[index] = true;
		t_lights[index] = light;
		light.setId(index);
		return index;
	}
	
	static void delLight(int i){
		if(t_lights[i] != null){
			lightsStorage[i] = false;
			t_lights[i] = null;
			lightCounter --;
		}		
	}
	
	static void delLightsAll(){
		for (int i = 0; i < t_lights.length; i++) {
			t_lights[i] = null;
		}
	}
	
	
	
	static Light[] getLights(){
		Light lights[] = new Light[lightCounter];
		int thisCounter = 0;
		for(int i = 0; i < lights.length; i++){
			if(lightsStorage[i] && t_lights[i] != null){
				lights[thisCounter] = t_lights[i];
				thisCounter ++;
			}
		}
		
		return lights;
	}
	
	
}

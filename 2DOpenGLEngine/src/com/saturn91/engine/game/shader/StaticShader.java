package com.saturn91.engine.game.shader;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.saturn91.engine.game.light.Light;
import com.saturn91.engine.game.renderer.Camera;
import com.saturn91.engine.util.Maths;

public class StaticShader extends ShaderProgramm{
	
	private static final String VERTEX_FILE = "src/com/saturn91/engine/game/shader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/com/saturn91/engine/game/shader/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private Light cameraLight = new Light(new Vector2f(0,0), new Vector3f(0, 0, 0));
	private Vector3f enviromentlight = new Vector3f(0, 0, 0); 
	private Light lights[] = new Light[0];

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");			//connect position variable to 0-Attribute of VB0
		super.bindAttribute(1, "textureCoords");	//connect texturecoords to 1-Attribute of VBO
	}
	
	public void configureCameraLight(Light light){
		this.cameraLight = light;
	}
	
	public void setEnviromentLight(Vector3f lightColor){
		this.enviromentlight = lightColor;
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void update(){
		//update CameraLight
		super.setShaderVariable2f("cameraLightPosition", cameraLight.getPosition());
		super.setShaderVariable3f("cameraLightColor", cameraLight.getColor());
		super.setShaderVariablef("cameraLightStrenght", cameraLight.getStrenght());
		super.setShaderVariablef("cameraLightrange", cameraLight.getRange());
		
		//update enviromentLight
		super.setShaderVariable3f("enviromentLight", enviromentlight);
		
		//update pointLights
		if(lights.length <= 25){
			for(int i = 0; i < lights.length; i++){
				setShaderVariable3f("allLights[" + i + "].position", new Vector3f(lights[i].getPosition().x, lights[i].getPosition().y, 1));
				setShaderVariable3f("allLights[" + i + "].color", lights[i].getColor());
				setShaderVariablef("allLights[" + i + "].strenght", lights[i].getStrenght());
				setShaderVariablef("allLights[" + i + "].range", lights[i].getRange());
			}
		}else{
			System.err.println("StaticShader: at the moment only 25 lights are allowed!");
		}
	}
	
	public void setPointLights(Light lights[]){
		this.lights = lights;
	}
	
	
	

}

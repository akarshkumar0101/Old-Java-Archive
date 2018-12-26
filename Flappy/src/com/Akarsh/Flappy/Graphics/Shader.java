package com.Akarsh.Flappy.Graphics;

import com.Akarsh.Flappy.Utils.ShaderUtils;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
	
	private final int ID;
	
	public Shader(String vertex, String fragment){
		ID = ShaderUtils.load(vertex, fragment);
		
	}
	
	public void setUniform1i(String name, int value){
		
	}
	
	public void enable(){
		glUseProgram(ID);
	}
	
	public void disable(){
		glUseProgram(0);		
	}
	
	
	
}

package com.Akarsh.Flappy.Math;

import java.nio.FloatBuffer;

import com.Akarsh.Flappy.Utils.BufferUtils;

public class Matrix4f {
	
	public static final int SIZE = 4*4;
	public float elements [] = new float[SIZE];
	
	public Matrix4f(){
		
	}
	public static Matrix4f identity(){
		Matrix4f result = new Matrix4f();
		
		for(int i = 0; i < SIZE; i++){
			result.elements[i] = 0.0f;
		}
		result.elements[0*4+0] = 1.0f;
		result.elements[1*4+1] = 1.0f;
		result.elements[2*4+2] = 1.0f;
		result.elements[3*4+2] = 1.0f;
		
		return result;
	}
	
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far){
		Matrix4f result = identity();
		
		result.elements[0*4+0] = 2.0f / (right - left);
		result.elements[1*4+1] = 2.0f / (top - bottom);
		result.elements[2*4+2] = 2.0f / (near - far);
		
		result.elements[3*4+0] = (left + right) / (left - right);
		result.elements[3*4+1] = (bottom + top) / (bottom - top);
		result.elements[3*4+2] = (far + near) / (far - near);
		
		return result;
	}
	
	
	public static Matrix4f translate(Vector3f vector){
		Matrix4f result = identity();
		
		result.elements[3*4 +0] = vector.x;
		result.elements[3*4 +1] = vector.y;
		result.elements[3*4 +2] = vector.z;
		return result;
	}
	
	public static Matrix4f rotate(float angle){
		Matrix4f result = identity();
		float r = (float) Math.toRadians(angle);
		float sin = (float)Math.sin(r);
		float cos = (float)Math.cos(r);
		
		result.elements[0*4+0] = cos;
		result.elements[0*4+1] = sin;
		
		result.elements[1*4+0] = -sin;
		result.elements[1*4+1] = cos;
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f matrix){
		Matrix4f result = new Matrix4f();
		for(int y=0; y <4; y ++){
			for(int x=0; x <4; x++){
				float sum = 0.0f;
				for(int e=0; e <4; e++){
					sum += this.elements[x+e*4] * matrix.elements[e+y*4];
				}
				result.elements[x+y*4] = sum;
			}
		}
		
		return result;
		
	}
	
	public FloatBuffer toFloatBuffer(){
		return BufferUtils.createFloatBuffer(elements);
	}
}

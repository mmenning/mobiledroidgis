/*
 * Copyright 2012 Mathias Menninghaus (mathias.menninghaus (at) googlemail (dot) com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmenning.mobilegis.surface3d;

import android.util.Log;

/**
 * Simple Listener to communicate easily with SurfaceRenderer. The listener
 * should be filled with information before the SurfaceRenderer is started.
 * 
 * @author Mathias Menninghaus
 * @version 22.06.2009
 * @see SurfaceRenderer
 */
public class Listener3D {

	private static final String DT = "Listener3D";

	private float sensibility;
	private float zdistance;
	private float xwalk;
	private float ywalk;
	private float zwalk;
	private float xrot;
	private float yrot;
	private float zFar;
	private float zNear;
	
	private boolean blending;
	private boolean dithering;
	private boolean smooth_shading;
	private boolean lighting;
	private boolean diffuse_lighting;
	private boolean ambient_lighting;

	private float[] light_direction;

	public Listener3D(float zoom, float xwalk, float ywalk, float sensibility) {
		this.setXwalk(xwalk);
		this.setYwalk(ywalk);
		this.setZDistance(zoom);
		this.setSensibility(sensibility);

		this.setZwalk(0);

		this.setXrot(0);
		this.setYrot(0);
	}

	public Listener3D(float sensibility) {
		this(0, 0, 0, sensibility);
	}

	public Listener3D() {
		this(0, 0, 0, 1.0f);
	}

	public float getZDistance() {
		return zdistance;
	}

	public void setZDistance(float zdist) {
		this.zdistance = zdist;
	}

	public float getXwalk() {
		return xwalk;
	}

	public void setXwalk(float xwalk) {
		this.xwalk = xwalk;
	}

	public float getYwalk() {
		return ywalk;
	}

	public void setYwalk(float ywalk) {
		this.ywalk = ywalk;
	}

	public float getXrot() {
		return xrot;
	}

	public void setXrot(float xrot) {
		this.xrot = xrot;
	}

	public float getYrot() {
		return yrot;
	}

	public void setYrot(float yrot) {
		this.yrot = yrot;
	}

	public float getSensibility() {
		return sensibility;
	}

	public void setSensibility(float sensibility) {
		if (sensibility < 0) {
			Log.e(DT, "sensivility < 0 : is set 1");
			sensibility = 1;
		}
		this.sensibility = sensibility;
	}

	public float getZwalk() {
		return zwalk;
	}

	public void setZwalk(float zwalk) {
		this.zwalk = zwalk;
	}

	public float getZFar() {
		return zFar;
	}

	public void setZFar(float far) {
		if (far < 0) {
			Log.e(DT, "zfar < 0 : is set 1");
			zFar = 1;
		}
		zFar = far;
	}

	public float getZNear() {
		return zNear;
	}

	public void setZNear(float near) {
		if (near < 0) {
			Log.e(DT, "zNear < 0 : is set 1");
			zNear = 1;
		}
		zNear = near;
	}

	public boolean isDithering() {
		return dithering;
	}

	public void setDithering(boolean dithering) {
		this.dithering = dithering;
	}

	public boolean isSmooth_shading() {
		return smooth_shading;
	}

	public void setSmooth_shading(boolean smooth_shading) {
		this.smooth_shading = smooth_shading;
	}

	public boolean isLighting() {
		return lighting;
	}

	public void setLighting(boolean lighting) {
		this.lighting = lighting;
	}

	public boolean isDiffuse_lighting() {
		return diffuse_lighting;
	}

	public void setDiffuse_lighting(boolean diffuse_lighting) {
		this.diffuse_lighting = diffuse_lighting;
	}

	public boolean isAmbient_lighting() {
		return ambient_lighting;
	}

	public void setAmbient_lighting(boolean ambient_lighting) {
		this.ambient_lighting = ambient_lighting;
	}

	public float[] getLight_direction() {
		return light_direction;
	}

	public void setLight_direction(float[] light_direction) {
		this.light_direction = light_direction;
	}

	public boolean isBlending() {
		return blending;
	}

	public void setBlending(boolean blending) {
		this.blending = blending;
	}
	
	public void setFullGraphics(boolean on){
		blending=on;
		diffuse_lighting=on;
		ambient_lighting=on;
		lighting=on;
		dithering=on;
		smooth_shading=on;
	}
}

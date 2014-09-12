package com.fish.camera;

public class Camera {
	
	int _id;
	String _name;
	byte[] _image;
	
	public Camera() {}
	
	public Camera(int keyId, String name, byte[] image) {
		this._id = keyId;
		this._name = name;
		this._image = image;
	}
	
	public Camera(String name, byte[] image) {
		this._name = name;
		this._image = image;
	}

	public Camera(int keyId) {
		this._id = keyId;
	}

	public int getID() {
		return this._id;
	}

	public void setID(int keyId) {
		this._id = keyId;
	}
	
	public String getName() {
		return this._name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public byte[] getImage() {
		return this._image;
	}

	public void setImage(byte[] image) {
		this._image = image;
	}
}

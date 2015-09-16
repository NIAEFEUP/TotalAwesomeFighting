package com.tantch.taf.entities;

public class Projectile {
	
	private float speed;
	private int dir;
	private int x, y;
	private boolean collision;
	private float height,width;
	private Fighter fighter;
	
	
	public Projectile (Fighter fighter, int dir){
		this.fighter = fighter;
		this.dir = dir;
		speed = 200;
	}
	
	

}

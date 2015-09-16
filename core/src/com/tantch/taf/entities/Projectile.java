package com.tantch.taf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Projectile {
	
	private float speed;
	private int dir;
	private int x, y;
	private boolean collision;
	private float height,width;
	private Fighter fighter;
	private Texture bodySprite;
	
	
	public Projectile (Fighter fighter, int dir){
		this.fighter = fighter;
		this.dir = dir;
		speed = 200;
		height=5;
		width=30;
		bodySprite = new Texture(Gdx.files.internal("bow/WEAPON_arrow"));
	}


	public void draw(float delta) {
		fighter.game.batch.draw(bodySprite, x, y, width, height, 793, 231, 30,
				5, false, false);
	}
	
	public void update(float delta){
		
	}
	
	

}

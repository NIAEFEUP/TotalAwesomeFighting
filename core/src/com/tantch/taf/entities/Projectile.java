package com.tantch.taf.entities;

import java.util.concurrent.ConcurrentLinkedDeque;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tantch.taf.TAFGame;

public class Projectile {
	private TAFGame game;
	private float speed = 600;
	private String dir = "none";
	private int x, y;
	private boolean collision;
	private float height,width;
	private static Texture bodySprite =new Texture(Gdx.files.internal("img/hadouken.png"));;
	private String id;
	private Vector2 velocity;
	private int gravity = 0;
	private  ConcurrentLinkedDeque<Projectile> projectiles;


	public boolean isCollision() {
		return collision;
	}


	public void setCollision(boolean collision) {
		this.collision = collision;
	}


	public Projectile (TAFGame game,int x,int y, String id, String direction, ConcurrentLinkedDeque<Projectile> projectiles){
		this.projectiles = projectiles;
		this.game=game;
		this.id = id;
		height=14;
		width=30;
		this.x = x;
		this.y = y;
		System.out.println("here");
		this.dir = direction;
		velocity = new Vector2();
		velocity.x = speed;
	}


	public void draw(float delta) {
		update(delta);
		if(dir.equals("right"))
			game.batch.draw(bodySprite, x, y, width, height, 481, 131, 412,
					195, false, false);
		else
			game.batch.draw(bodySprite, x, y, width, height, 481, 131, 412,
					195, true, false);
		
		
	}

	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void update(float delta){

		velocity.y -= gravity * delta;
		float temp;
		if(dir.equals("left"))
			temp = -velocity.x * delta;
		else
			temp =  velocity.x * delta;
		
		x = Math.round(x + temp);
		
		if(x > 800 || x < 0 || collision)
			projectiles.remove(this);
		
		y = (int) (y + velocity.y * delta);
		
		if (velocity.x < 0)
			collision = collidesLeft();
		else if (velocity.x > 0)
			collision = collidesRight();
		
		
	}


	public Vector2 getVelocity() {
		return velocity;
	}


	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}


	public float getHeight() {
		return height;
	}


	public void setHeight(float height) {
		this.height = height;
	}


	public float getWidth() {
		return width;
	}


	public void setWidth(float width) {
		this.width = width;
	}


	public String getDir() {
		return dir;
	}


	public void setDir(String dir) {
		this.dir = dir;
	}


	private boolean collidesRight() {
		// TODO Auto-generated method stub
		return false;
	}


	private boolean collidesLeft() {
		// TODO Auto-generated method stub
		return false;
	}



}

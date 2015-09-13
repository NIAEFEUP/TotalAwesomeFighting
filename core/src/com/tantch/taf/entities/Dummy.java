package com.tantch.taf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Dummy extends Sprite{
	private Vector2 velocity = new Vector2();
	private float speed = 60 * 2, gravity = 60 * 1.8f;

	private TiledMapTileLayer collisionLayer;

	public Dummy(Sprite sprite, TiledMapTileLayer collisionLayer){
		super(sprite);
		this.collisionLayer = collisionLayer;
	}

	@Override
	public void draw(Batch batch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}

	public void update(float delta) {
		velocity.y -= gravity * delta;

		if(velocity.y > speed)
			velocity.y = speed;
		else if(velocity.y < speed)
			velocity.y = -speed;

		float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false;

		setX(getX() + velocity.x * delta);

		if(velocity.x < 0){
			//topleft
			collisionX = collisionLayer.getCell((int) (getX() / tileWidth),(int) ((getY() + getHeight())/ tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//middleleft
			if(!collisionX)
				collisionX = collisionLayer.getCell((int) (getX() / tileWidth),(int) ((getY() + (getHeight() / 2))/ tileHeight))
				.getTile().getProperties().containsKey("blocked");
			//bottomleft
			if(!collisionX)

				collisionX = collisionLayer.getCell((int) (getX() / tileWidth),(int) (getY() / tileHeight))
				.getTile().getProperties().containsKey("blocked");

		} else if( velocity.x > 0){
			//top right
			collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth),(int) ((getY() + getHeight())/ tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//middle right
			if(!collisionX)
				collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth),(int) ((getY() + (getHeight() / 2))/ tileHeight))
				.getTile().getProperties().containsKey("blocked");
			//bottom right
			if(!collisionX)
				collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth),(int) (getY() / tileHeight))
				.getTile().getProperties().containsKey("blocked");

		}

		if(collisionX){
			setX(oldX);
			velocity.x = 0;
		}
		
		setY(getY() + velocity.y * delta);

		if(velocity.y < 0){
			//bottom left
			collisionY = collisionLayer.getCell((int) (getX() / tileWidth),(int) (getY() / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//bottom middle
			if(!collisionY)
				collisionY = collisionLayer.getCell((int) ((getX() + (getWidth() / 2)) / tileWidth),(int) (getY() / tileHeight))
				.getTile().getProperties().containsKey("blocked");
			//bottom right
			if(!collisionY)
				collisionY = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth),(int) (getY() / tileHeight))
				.getTile().getProperties().containsKey("blocked");

		} else if( velocity.y > 0){
			//top left
			collisionY = collisionLayer.getCell((int) (getX() / tileWidth),(int) ((getY() + getHeight()) / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//top middle
			if(!collisionY)
				collisionY = collisionLayer.getCell((int) ((getX() + (getWidth() / 2)) / tileWidth),(int) ((getY() + getHeight()) / tileHeight))
				.getTile().getProperties().containsKey("blocked");
			//top right
			if(!collisionY)
				collisionY = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth),(int) ((getY() + getHeight()) / tileHeight))
				.getTile().getProperties().containsKey("blocked");
		}
		
		if(collisionY){
			setY(oldY);
			velocity.y = 0;
		}
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

}

package com.tantch.taf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.tantch.taf.TAFGame;

public class Fighter {

	private int JUMPPOWER = 55;
	// TODO falta por as outras partes do torso
	private String bodySpriteName;
	private String beltSpriteName;
	private String feetSpriteName;
	private String handsSpriteName;
	private String headSpriteName;
	private String legsSpriteName;
	private String torsoSpriteName;
	private Texture bodySprite;
	private Texture beltSprite;
	private Texture feetSprite;
	private Texture handsSprite;
	private Texture headSprite;
	private Texture legsSprite;
	private Texture torsoSprite;
	private TAFGame game;
	private Color taint;
	private int width = 64;
	private int height = 65;
	private int framesNumber;
	private float time;

	private int vely = 0;
	private int velx = 0;
	private int frameSkip = 0;
	private int curFrame;
	private int dir;
	private String action;
	private String standByAction;
	private boolean onGoingAction = false;
	private int x,y;
	//novo
	private Vector2 velocity = new Vector2();
	private float speed = 60 * 2, gravity = 60 * 1.8f;

	private TiledMapTileLayer collisionLayer;

	public Fighter(TAFGame game, TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
		bodySpriteName = "BODY_male.png";
		beltSpriteName = "BELT_leather.png";
		feetSpriteName = "FEET_shoes_brown.png";
		handsSpriteName = "HANDS_plate_armor_gloves.png";
		headSpriteName = "HEAD_leather_armor_hat.png";
		legsSpriteName = "LEGS_pants_greenish.png";
		torsoSpriteName = "TORSO_plate_armor_torso.png";
		taint = Color.BLACK;
		this.game = game;
		time = 0;
		curFrame = 0;
		dir = 2;
		framesNumber = 1;
		action = "idle";
		standByAction = "idle";
		setTextures();
	}

	private void setTextures() {
		String folder;
		switch (action) {
		case "left":
			folder = "walkcycle/";
			dir = 1;
			framesNumber = 9;
			frameSkip = 0;
			velx = -5;
			break;
		case "right":
			folder = "walkcycle/";
			dir = 3;
			framesNumber = 9;
			frameSkip = 0;
			velx = 5;
			break;
		case "jump":
			folder = "bow/";
			framesNumber = 1;
			frameSkip = 2;
			onGoingAction = true;
			vely = JUMPPOWER;
			break;
		case "idle":
		default:
			folder = "walkcycle/";
			frameSkip = 0;
			framesNumber = 1;
			velx = 0;
			break;
		}

		bodySprite = new Texture(Gdx.files.internal(folder + bodySpriteName));
		beltSprite = new Texture(Gdx.files.internal(folder + beltSpriteName));
		feetSprite = new Texture(Gdx.files.internal(folder + feetSpriteName));
		handsSprite = new Texture(Gdx.files.internal(folder + handsSpriteName));
		headSprite = new Texture(Gdx.files.internal(folder + headSpriteName));
		legsSprite = new Texture(Gdx.files.internal(folder + legsSpriteName));
		torsoSprite = new Texture(Gdx.files.internal(folder + torsoSpriteName));
	}

	public void start(String act) {

		if (act.equals("jump")) {
			standByAction = action;
			action = act;
			curFrame = 0;
			setTextures();
			return;
		}

		if (!action.equals("idle")) {
			standByAction = act;
			return;
		}
		curFrame = 0;
		action = act;
		setTextures();
	}

	public void stop(String act) {
		if (!action.equals(act)) {
			if (act.equals(standByAction)) {
				standByAction = "idle";
			}
			return;
		}
		action = "idle";
		start(standByAction);
		if (!standByAction.equals("idle")) {
			standByAction = "idle";
		}
	}

	public void update(float delta){
		velocity.y -= gravity * delta;

		if(velocity.y > speed)
			velocity.y = speed;
		else if(velocity.y < speed)
			velocity.y = -speed;

		float oldX = x, oldY = y, tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false;

		x = (int) (x + velocity.x * delta);

		if(velocity.x < 0){
			//topleft
			collisionX = collisionLayer.getCell((int) (x / tileWidth),(int) ((y + height)/ tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//middleleft
			if(!collisionX)
				collisionX = collisionLayer.getCell((int) (x / tileWidth),(int) ((y + (height / 2))/ tileHeight))
				.getTile().getProperties().containsKey("blocked");
			//bottomleft
			if(!collisionX)

				collisionX = collisionLayer.getCell((int) (x / tileWidth),(int) (y / tileHeight))
				.getTile().getProperties().containsKey("blocked");

		} else if( velocity.x > 0){
			//top right
			collisionX = collisionLayer.getCell((int) ((x + width) / tileWidth),(int) ((y + height)/ tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//middle right
			if(!collisionX)
				collisionX = collisionLayer.getCell((int) ((x + width) / tileWidth),(int) ((y + (height / 2))/ tileHeight))
				.getTile().getProperties().containsKey("blocked");
			//bottom right
			if(!collisionX)
				collisionX = collisionLayer.getCell((int) ((x + width) / tileWidth),(int) (y / tileHeight))
				.getTile().getProperties().containsKey("blocked");

		}

		if(collisionX){
			x = (int) oldX;
			velocity.x = 0;
		}

		y = (int) (y + velocity.y * delta);

		if(velocity.y < 0){
			//bottom left
			collisionY = collisionLayer.getCell((int) (x / tileWidth),(int) (y / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//bottom middle
			if(!collisionY)
				collisionY = collisionLayer.getCell((int) ((x + (width / 2)) / tileWidth),(int) (y / tileHeight))
				.getTile().getProperties().containsKey("blocked");
			//bottom right
			if(!collisionY)
				collisionY = collisionLayer.getCell((int) ((x + width) / tileWidth),(int) (y / tileHeight))
				.getTile().getProperties().containsKey("blocked");

		} else if( velocity.y > 0){
			//top left
			collisionY = collisionLayer.getCell((int) (x / tileWidth),(int) ((y + height) / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			//top middle
			if(!collisionY)
				collisionY = collisionLayer.getCell((int) ((x + (width / 2)) / tileWidth),(int) ((y + height) / tileHeight))
				.getTile().getProperties().containsKey("blocked");
			//top right
			if(!collisionY)
				collisionY = collisionLayer.getCell((int) ((x + width) / tileWidth),(int) ((y + height) / tileHeight))
				.getTile().getProperties().containsKey("blocked");
		}

		if(collisionY){
			y = (int) oldY;
			velocity.y = 0;
		}
	}


	public void draw(float delta) {
		/*
		time += delta;

		if (time > 1.0f / 30.0f) {
			x += velx;
			vely += GRAVITY;

			y += vely;
			// TODO HARDCODED OBSTACLE FLOOR
			if (y <= 200) {
				y = 200;
				vely = 0;
				stop("jump");
			}
			curFrame++;
			if (curFrame >= framesNumber) {
				curFrame = 0;
			}
			time = 0;

		}
		 */
		
		update(delta);
		
		int framx = (curFrame + frameSkip) * width;
		int framy = dir * height;
		game.batch.setColor(1f, 1f, 1f, 1f);
		game.batch.draw(bodySprite, x, y, width, height, framx, framy, width, height, false, false);
		game.batch.draw(beltSprite, x, y, width, height, framx, framy, width, height, false, false);
		game.batch.draw(feetSprite, x, y, width, height, framx, framy, width, height, false, false);
		game.batch.draw(handsSprite, x, y, width, height, framx, framy, width, height, false, false);
		game.batch.draw(legsSprite, x, y, width, height, framx, framy, width, height, false, false);
		game.batch.draw(headSprite, x, y, width, height, framx, framy, width, height, false, false);
		game.batch.draw(torsoSprite, x, y, width, height, framx, framy, width, height, false, false);

	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;	
		
	}

}

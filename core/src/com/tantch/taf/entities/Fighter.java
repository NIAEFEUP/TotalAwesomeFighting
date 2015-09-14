package com.tantch.taf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.tantch.taf.TAFGame;

public class Fighter implements InputProcessor{

	private int JUMPPOWER = 200;
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

	private int frameSkip = 0;
	private int curFrame;
	private int dir;
	private String action;
	private String standByAction;
	private int x, y;
	// novo
	private Vector2 velocity = new Vector2();
	private float speed = 120, gravity = 240;
	private boolean canJump;

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
			velocity.x = -speed;
			break;
		case "right":
			folder = "walkcycle/";
			dir = 3;
			framesNumber = 9;
			frameSkip = 0;
			velocity.x = speed;
			break;
		case "jump":
			folder = "bow/";
			framesNumber = 1;
			frameSkip = 2;
			velocity.y = JUMPPOWER;
			break;
		case "idle":
		default:
			folder = "walkcycle/";
			frameSkip = 0;
			framesNumber = 1;
			velocity.x = 0;
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

	public void update(float delta) {
		velocity.y -= gravity * delta;

		float oldX = x, oldY = y, tileWidth = collisionLayer.getTileWidth(),
				tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false;

		x = (int) (x + velocity.x * delta);

		if (velocity.x < 0)
			collisionX = collidesLeft();
		else if (velocity.x > 0)
			collisionX = collidesRight();

		if (collisionX) {
			x = (int) oldX;
			velocity.x = 0;
		}

		y = (int) (y + velocity.y * delta);

		if (velocity.y < 0) {
			collisionY = collidesBottom();

			canJump = collisionY;
			if(collisionY)
				stop("jump");

		} else if (velocity.y > 0) 
			collisionY = collidesTop();

		if (collisionY) {
			y = (int) oldY;
			velocity.y = 0;
		}
	}

	private boolean isCellBlocked(float x, float y){
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()) , (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
	}

	public boolean collidesRight(){
		for(float step = 0; step < height; step += (collisionLayer.getTileHeight() / 2)){
			if(isCellBlocked(x + width, y + step))
				return true;
		}
		return false;
	}

	public boolean collidesLeft(){
		for(float step = 0; step < height; step += (collisionLayer.getTileHeight() / 2)){
			if(isCellBlocked(x, y + step))
				return true;
		}
		return false;
	}

	public boolean collidesTop(){
		for(float step = 0; step < width; step += (collisionLayer.getTileWidth() / 2)){
			if(isCellBlocked(x + step, y + height))
				return true;
		}
		return false;
	}

	public boolean collidesBottom(){
		for(float step = 0; step < width; step += (collisionLayer.getTileWidth() / 2)){
			if(isCellBlocked(x + step, y))
				return true;
		}
		return false;
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

	public void draw(float delta) {

		time += delta;

		if (time > 1.0f / 30.0f) {
			curFrame++;
			if (curFrame >= framesNumber) {
				curFrame = 0;
			}
			time = 0;

		}

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

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.W:
			if(canJump){
				start("jump");
				canJump = false;
			}
			break;
		case Keys.A:
			start("left");
			break;
		case Keys.D:
			start("right");
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.W:
			break;
		case Keys.A:
			stop("left");
			break;
		case Keys.D:
			stop("right");
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}

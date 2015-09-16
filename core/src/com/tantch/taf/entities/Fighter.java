package com.tantch.taf.entities;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.tantch.taf.TAFGame;

public class Fighter implements InputProcessor {

	private int JUMPPOWER = 700;
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

	private float r, g, b;

	private int width = 64;
	private int height = 64;
	private int leftmargin = 15;
	private int topmargin = 10;
	private int realWidth = 31;
	private int realHeight = 50;
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
	private float speed = 120, gravity = 1000;
	private boolean canJump, collided;

	private TiledMapTileLayer collisionLayer;
	private HashMap<String, Fighter> fighters;
	private String name;

	// ainda mais novo
	private int leftPush;
	private int rightPush;
	private int resistancePush;
	private float attackTime;

	public Fighter(TAFGame game, TiledMapTileLayer collisionLayer, String name, HashMap<String, Fighter> fighters) {
		this.collisionLayer = collisionLayer;
		leftPush = 0;
		rightPush = 0;
		resistancePush = 10;

		bodySpriteName = "BODY_male.png";
		beltSpriteName = "BELT_leather.png";
		feetSpriteName = "FEET_shoes_brown.png";
		handsSpriteName = "HANDS_plate_armor_gloves.png";
		headSpriteName = "HEAD_leather_armor_hat.png";
		legsSpriteName = "LEGS_pants_greenish.png";
		torsoSpriteName = "TORSO_plate_armor_torso.png";
		Random rd = new Random();
		r = (rd.nextFloat() / 2f) + 0.5f;
		g = (rd.nextFloat() / 2f) + 0.5f;
		b = (rd.nextFloat() / 2f) + 0.5f;
		this.game = game;
		time = 0;
		curFrame = 0;
		dir = 2;
		framesNumber = 1;
		action = "idle";
		standByAction = "idle";
		this.fighters = fighters;
		this.name = name;
		collided = false;
		setTextures();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		case "attack":
			folder = "bow/";
			framesNumber = 1;
			frameSkip = 2;
			doAttack();
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

	private void doAttack() {
		fighters.forEach((k, v) -> {

			if (!k.equals(name)) {
				if ((((x + (realWidth / 2)+10) >= (v.getX() - (v.realWidth / 2)+3)) && x <= v.getX() && y == v.getY()) 
						|| (((x - (realWidth / 2)-10) <= (v.getX() + (v.realWidth / 2)-3)) && x >= v.getX() && y == v.getY())) {
					game.dead.add(k);
					return;
				}
			}
		});
		
	}

	public void start(String act) {

		if (act.equals("jump")) {
			
			if(velocity.y >0){
				return;
			}
			
			standByAction = action;
			action = act;
			curFrame = 0;
			setTextures();
			return;
		}

		if(act.equals("attack")){
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
		if(standByAction.equals(act)){
			standByAction = "idle";
		}

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

		float oldX = x, oldY = y;
		boolean collisionX = false, collisionY = false;
		float temp = velocity.x * delta;
		x = Math.round(x + temp);

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

		} else if (velocity.y > 0)
			collisionY = collidesTop();

		if (collisionY) {
			y = (int) oldY;
			velocity.y = 0;
		}

		// new

		if (action.equals("idle")) {
			velocity.x = velocity.x * 0.9f;
			if (velocity.x < speed / 10f) {
				velocity.x = 0;
			}
		}
		
		if(y <-200){
			game.dead.add(name);
		}

	}

	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()),
				(int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public boolean collidesRight() {
		for (float step = 0; step < realHeight; step += (collisionLayer.getTileHeight() / 2)) {
			if (isCellBlocked(x + realWidth-5, y + step))
				return true;
		}

		fighters.forEach((k, v) -> {

			if (!k.equals(name)) {
				if (((x + (realWidth / 2)-3) >= (v.getX() - (v.realWidth / 2)+3)) && x <= v.getX() && y == v.getY()) {
					v.getPushed("left", 10 ,velocity.x);
					collided = true;

					return;
				}
			}
		});
		return false;
	}

	public boolean collidesLeft() {
		for (float step = 0; step < realHeight; step += (collisionLayer.getTileHeight() / 2)) {
			if (isCellBlocked(x+5, y + step))
				return true;
		}

		fighters.forEach((k, v) -> {

			if (!k.equals(name))
				if (((x - (realWidth / 2)+3) <= (v.getX() + (v.realWidth / 2)-3)) && x >= v.getX() && y == v.getY()) {
					v.getPushed("right", 10, velocity.x);
					collided = true;
					return;
				}
		});
		
		return false;
	}

	public boolean collidesTop() {
		for (float step = 0; step < realWidth; step += (collisionLayer.getTileWidth() / 2)) {
			if (isCellBlocked(x + step, y + realHeight))
				return true;
		}
		return false;
	}

	public boolean collidesBottom() {
		for (float step = 0; step < realWidth; step += (collisionLayer.getTileWidth() / 2)) {
			if (isCellBlocked(x + step, y))
				return true;
		}//TODO poem a distancia mais pequena intersecao das 2 metades e mt grande
		fighters.forEach((k, v) -> {
			if (!k.equals(name)) {
				if (((y - (realHeight / 2)) <= (v.getY() + (v.realHeight / 2)))
						&& (y - (realHeight / 2)) > v.getY()
						&& (x + (realWidth / 2) >= v.getX() - (v.realWidth / 2))
						&& (x - (realWidth / 2) <= v.getX() + (v.realWidth / 2))) {
					v.collided = true;
					collided = true;
					game.dead.add(k);
					return;
				}
			}
		});

		if (collided) {
			collided = false;
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

		update(delta);
		
		time += delta;

		if (time > 1.0f / 30.0f) {
			curFrame++;
			if (curFrame >= framesNumber) {
				curFrame = 0;
			}
			time = 0;

		}

		int framx = (curFrame + frameSkip) * width;
		int framy = dir * height;
		game.batch.setColor(r, g, b, 1f);
		game.batch.draw(bodySprite, x, y, realWidth, realHeight, framx + leftmargin, framy + topmargin, realWidth,
				realHeight, false, false);
		game.batch.draw(beltSprite, x, y, realWidth, realHeight, framx + leftmargin, framy + topmargin, realWidth,
				realHeight, false, false);
		game.batch.draw(feetSprite, x, y, realWidth, realHeight, framx + leftmargin, framy + topmargin, realWidth,
				realHeight, false, false);
		game.batch.draw(handsSprite, x, y, realWidth, realHeight, framx + leftmargin, framy + topmargin, realWidth,
				realHeight, false, false);
		game.batch.draw(legsSprite, x, y, realWidth, realHeight, framx + leftmargin, framy + topmargin, realWidth,
				realHeight, false, false);
		game.batch.draw(headSprite, x, y, realWidth, realHeight, framx + leftmargin, framy + topmargin, realWidth,
				realHeight, false, false);
		game.batch.draw(torsoSprite, x, y, realWidth, realHeight, framx + leftmargin, framy + topmargin, realWidth,
				realHeight, false, false);
		game.batch.setColor(1f, 1f, 1f, 1f);

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
			if (canJump) {
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
		case Keys.SPACE:
			start("attack");
			break;
		}
		return true;
	}

	public void getPushed(String dir, int force, float vX) {
		switch (dir) {
		case "right":
			setVelocity(new Vector2(velocity.x + vX, getVelocity().y));
			break;
		case "left":
			setVelocity(new Vector2(vX + velocity.x, getVelocity().y));
			break;

		}
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.W:
			stop("jump");
			break;
		case Keys.A:
			stop("left");
			break;
		case Keys.D:
			stop("right");
			break;
		case Keys.SPACE:
			stop("attack");
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

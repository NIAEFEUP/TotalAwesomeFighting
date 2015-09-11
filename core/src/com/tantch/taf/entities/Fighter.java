package com.tantch.taf.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.tantch.taf.TAFGame;

public class Fighter {
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

	private int curFrame;
	private int dir;
	private String action;
	private String standByAction;

	public Fighter(TAFGame game) {
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
			break;
		case "right":
			folder = "walkcycle/";
			dir = 3;
			framesNumber = 9;
			break;
		case "up":
			folder = "walkcycle/";
			dir = 0;
			framesNumber = 9;
			break;
		case "down":
			folder = "walkcycle/";
			dir = 2;
			framesNumber = 9;
			break;
		case "idle":
		default:
			folder = "walkcycle/";
			framesNumber = 1;
			break;
		}
		System.out.println("LOG : dir : " + dir);

		bodySprite = new Texture(Gdx.files.internal(folder + bodySpriteName));
		beltSprite = new Texture(Gdx.files.internal(folder + beltSpriteName));
		feetSprite = new Texture(Gdx.files.internal(folder + feetSpriteName));
		handsSprite = new Texture(Gdx.files.internal(folder + handsSpriteName));
		headSprite = new Texture(Gdx.files.internal(folder + headSpriteName));
		legsSprite = new Texture(Gdx.files.internal(folder + legsSpriteName));
		torsoSprite = new Texture(Gdx.files.internal(folder + torsoSpriteName));
	}

	public void start(String act) {
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

	public void draw(int x, int y, float delta) {
		time += delta;

		if (time > 1.0f / 15.0f) {
			curFrame++;
			if (curFrame >= framesNumber) {
				curFrame = 0;
			}
			time = 0;

		}

		int framx = curFrame * width;
		int framy = dir * height;
		game.batch.draw(bodySprite, x, y, width * 2, height * 2, framx, framy, width, height, false, false);
		game.batch.draw(beltSprite, x, y, width * 2, height * 2, framx, framy, width, height, false, false);
		game.batch.draw(feetSprite, x, y, width * 2, height * 2, framx, framy, width, height, false, false);
		game.batch.draw(handsSprite, x, y, width * 2, height * 2, framx, framy, width, height, false, false);
		game.batch.draw(legsSprite, x, y, width * 2, height * 2, framx, framy, width, height, false, false);
		game.batch.draw(headSprite, x, y, width * 2, height * 2, framx, framy, width, height, false, false);
		game.batch.draw(torsoSprite, x, y, width * 2, height * 2, framx, framy, width, height, false, false);

	}

}

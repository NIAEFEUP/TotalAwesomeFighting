package com.tantch.taf.inputs;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.tantch.taf.screens.GameScreen;

public class GameProcessor implements InputProcessor {

	GameScreen screen;

	public GameProcessor(GameScreen gameScreen) {
		this.screen = gameScreen;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			screen.getFighter().start("left");
			return true;
		case Keys.RIGHT:
			screen.getFighter().start("right");
			return true;
		case Keys.UP:
			screen.getFighter().start("up");
			return true;
		case Keys.DOWN:
			screen.getFighter().start("down");
			return true;
		case Keys.V:
			screen.getFighter().start("espada");
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean keyUp(int keycode) {

		switch (keycode) {

		case Keys.LEFT:
			screen.getFighter().stop("left");
			return true;
		case Keys.RIGHT:
			screen.getFighter().stop("right");
			return true;
		case Keys.UP:
			screen.getFighter().stop("up");
			return true;
		case Keys.DOWN:
			screen.getFighter().stop("down");
			return true;
		case Keys.V:
			screen.getFighter().stop("espada");
			return true;

		default:
			return false;
		}

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

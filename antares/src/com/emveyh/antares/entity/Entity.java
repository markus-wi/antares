package com.emveyh.antares.entity;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.emveyh.antares.core.GlobalConfig;
import com.emveyh.antares.core.TextureManager;
import com.emveyh.antares.map.MapManager;
import com.emveyh.antares.utils.Coord;

public class Entity extends Sprite {

	private float speed;

	public Entity(TextureRegion texture, float x, float y, float speed) {
		this.setRegion(texture);
		this.setX(x);
		this.setY(y);
		this.setSize(GlobalConfig.FIXED_TILESIZE, GlobalConfig.FIXED_TILESIZE);
		this.speed = speed;
	}

	public void render(SpriteBatch batch) {
		batch.draw(TextureManager.getInstance().getSprites()[2][0], this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public void moveX(boolean negative) {
		this.moveX(negative ? speed * -1 : speed);
	}

	public void moveY(boolean negative) {
		this.moveY(negative ? speed * -1 : speed);
	}

	public void moveX(float velocity) {
		float newX = this.getX() + velocity * Gdx.graphics.getDeltaTime();
		float newY = this.getY();
		if (isValidPosition(newX, newY)) {
			this.setX(newX);
		}
	}

	public void moveY(float velocity) {
		float newX = this.getX();
		float newY = this.getY() + velocity * Gdx.graphics.getDeltaTime();
		if (isValidPosition(newX, newY)) {
			this.setY(newY);
		}
	}

	public boolean isValidPosition(float newX, float newY) {
		boolean result = false;
		if (!isCollidingWithTile(newX, newY)) {
			result = true;
		}
		return result;
	}

	public String toString() {
		return "position: [x=" + this.getX() + "] [y=" + this.getY() + "]";
	}

	public void tick() {
	}

	private boolean isCollidingWithTile(float xToCheck, float yToCheck) {
		boolean result = false;
		List<Coord> nonAccessibleTiles = MapManager.getInstance().getGameMap().getNonAccessibleTiles();
		for (Coord coord : nonAccessibleTiles) {
			if (new Rectangle(xToCheck, yToCheck, this.getWidth(), this.getHeight()).overlaps(new Rectangle(coord.getX() * GlobalConfig.FIXED_TILESIZE, coord
					.getY() * GlobalConfig.FIXED_TILESIZE, GlobalConfig.FIXED_TILESIZE, GlobalConfig.FIXED_TILESIZE))) {
				result = true;
			}
		}
		return result;
	}

}

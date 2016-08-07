package com.myrpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {
    protected Sprite sprite;
    private int pixelsPerTile;
    Rectangle boundingBox;
    int x, y;
    float worldCoordsX = 12 * 32, worldCoordsY = 0;
    boolean selected;

    public Player(String path, int p, int startX, int startY) {
        this.pixelsPerTile = p;
        this.sprite = new Sprite(new Texture(Gdx.files.internal(path)));
        sprite.setPosition(this.x, this.y);
        sprite.setSize(pixelsPerTile, pixelsPerTile);
        this.boundingBox = new Rectangle(0, 0, pixelsPerTile, pixelsPerTile);
        this.x = startX;
        this.y = startY;
    }

    public void draw(Batch batch, Vector3 cameraPosition) {
        sprite.setCenter(
                (pixelsPerTile / 2) + (pixelsPerTile * x),
                (pixelsPerTile / 2) + (pixelsPerTile * y)
        );
        sprite.draw(batch);
    }

    public void renderBoundingBox(ShapeRenderer sr) {
        if (!selected) return;
        sr.setColor(Color.CHARTREUSE);
        sr.rect(this.sprite.getX(), this.sprite.getY(), pixelsPerTile, pixelsPerTile);
    }

    public void move(int dx, int dy) {
        sprite.setX(sprite.getX() + dx);
        sprite.setY(sprite.getY() + dy);
        this.x += dx;
        this.y += dy;
        System.out.println("x: " + x + ", y: " + y);
    }
}

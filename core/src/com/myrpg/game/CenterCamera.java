package com.myrpg.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CenterCamera {

    int intX, intY;
    float screenX, screenY;
    int ratio;
    Camera camera;

    public CenterCamera(int pixelsPerTile, Camera cam, int startX, int startY) {
        this.ratio = pixelsPerTile;
        this.camera = cam;
        this.intX = startX;
        this.intY = startY;
        this.camera.translate((startX - 3) * pixelsPerTile, (startY - 5) * pixelsPerTile, 0);
        this.camera.update();
    }

    public void draw(ShapeRenderer sr) {
        // Draw a box in the center of the camera's viewport.
        sr.setColor(Color.PURPLE);
        sr.rect(camera.position.x, camera.position.y, ratio, ratio);
    }

    public void translate(int dx, int dy) {
        this.intX += dx;
        this.intY += dy;
        camera.translate(dx * ratio, dy * ratio, 0);
        this.screenX = camera.position.x;
        this.screenY = camera.position.y;
    }
}

package com.myrpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class OrthoCamController extends InputAdapter {
    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();
    TiledMap map;
    Player player;
    private float dp;
    int[][] allTiles;
    int mapWidth, mapHeight;


    public OrthoCamController(OrthographicCamera camera, Player p, TiledMap m, float pixelsPerTile,
                              int[][] tiles) {
        this.camera = camera;
        this.player = p;
        this.map = m;
        this.dp = pixelsPerTile;
        this.allTiles = tiles;
        this.mapWidth = m.getProperties().get("width", int.class);
        this.mapHeight = m.getProperties().get("height", int.class);
        int ratio = m.getProperties().get("tileheight", int.class);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        //Vector3 position = camera.unproject(clickCoordinates);
        //player.sprite.setPosition(position.x, position.y);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return super.scrolled(amount);
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        camera.unproject(curr.set(x, y, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            camera.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.position.add(delta.x, delta.y, 0);
        }
        last.set(x, y, 0);
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT
                && player.x - 1 >= 0
                && allTiles[player.x - 1][player.y] == 0) {
            camera.translate(-dp, 0);
            player.move(-1, 0);
        }
        if (keycode == Input.Keys.RIGHT
                && player.x + 1 <= mapWidth
                && allTiles[player.x + 1][player.y] == 0) {
            camera.translate(dp, 0);
            player.move(1, 0);
        }
        if (keycode == Input.Keys.UP
                && player.y + 1 <= mapHeight
                && allTiles[player.x][player.y + 1] == 0) {
            camera.translate(0, dp);
            player.move(0, 1);
        }
        if (keycode == Input.Keys.DOWN
                && player.y - 1 >= 0
                && allTiles[player.x][player.y - 1] == 0) {
            camera.translate(0, -dp);
            player.move(0, -1);
        }
        if (keycode == Input.Keys.ESCAPE)
            Gdx.app.exit();
        if (keycode == Input.Keys.NUM_1)
            map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
        if (keycode == Input.Keys.NUM_2)
            map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible());

        return false;
    }
}

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
    int pixelRatio;
    CenterCamera centerCam;


    public OrthoCamController(OrthographicCamera camera, Player p, TiledMap m, float pixelsPerTile,
                              int[][] tiles, CenterCamera centerCam) {
        this.camera = camera;
        this.player = p;
        this.map = m;
        this.dp = pixelsPerTile;
        this.allTiles = tiles;
        this.mapWidth = m.getProperties().get("width", int.class);
        this.mapHeight = m.getProperties().get("height", int.class);
        this.pixelRatio = m.getProperties().get("tileheight", int.class);
        this.centerCam = centerCam;
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }

    boolean dragging = false;
    Vector3 tp = new Vector3();
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        // Ignore if left mouse button not pressed, or if this isn't the first touch.
        if (button != Input.Buttons.LEFT || pointer > 0) {
            return false;
        }

        System.out.println("touchDown: " + screenX + "," + screenY);
        camera.unproject(tp.set(screenX, screenY, 0));
        dragging = true;

        Vector3 s =camera.unproject(new Vector3(screenX, screenY, 0));
        int x = (int) (s.x / 32);
        int y = (int) (s.y / 32);
        //player.sprite.setPosition(position.x, position.y);
        if(player.x == x && player.y == y) {
            player.selected = true;
        } else {
            player.selected = false;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(!dragging) return false;
        System.out.println("touchDragged: " + screenX + "," +screenY);

        camera.unproject(curr.set(screenX, screenY, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            camera.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.position.add(delta.x, delta.y, 0);
        }
        last.set(screenX, screenY, 0);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp: " + screenX + "," + screenY);
        last.set(-1, -1, -1);
        // Snap camera to grid.
        int nearestX = (int) (camera.position.x / pixelRatio);
        int nearestY = (int) (camera.position.y / pixelRatio);
        camera.position.set(nearestX * pixelRatio, nearestY * pixelRatio, 0);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("Scrolling: " + amount);
        return super.scrolled(amount);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            if(player.selected) {
                if (player.x - 1 < 0) return false;
                if (allTiles[player.x-1][player.y] == 1) return false;
                centerCam.translate(-1,0);
                player.move(-1,0);
            } else {
                if (centerCam.intX - 1 < 0) return false;
                centerCam.translate(-1,0);
            }
        }
        if (keycode == Input.Keys.RIGHT) {
            if(player.selected) {
                if (player.x + 1 >= mapWidth) return false;
                if (allTiles[player.x+1][player.y] == 1) return false;
                centerCam.translate(1,0);
                player.move(1,0);
            } else {
                if (centerCam.intX + 1 >= mapWidth) return false;
                centerCam.translate(1,0);
            }
        }
        if (keycode == Input.Keys.UP) {
            if(player.selected) {
                if (player.y + 1 >= mapHeight) return false;
                if (allTiles[player.x][player.y + 1] == 1) return false;
                centerCam.translate(0, 1);
                player.move(0, 1);
            } else {
                if (centerCam.intY + 1 >= mapHeight) return false;
                centerCam.translate(0, 1);
            }
        }

        if (keycode == Input.Keys.DOWN) {
            if(player.selected) {
                if (player.y - 1 < 0) return false;
                if (allTiles[player.x][player.y - 1] == 1) return false;
                centerCam.translate(0, -1);
                player.move(0, -1);
            } else {
                if (centerCam.intY - 1 < 0) return false;
                centerCam.translate(0, -1);
            }
        }

        if(keycode == Input.Keys.SPACE) {
            //player.sprite.setPosition(position.x, position.y);
            if(player.x == centerCam.intX && player.y == centerCam.intY) {
                player.selected = !player.selected;
            } else {
                player.selected = false;
            }
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

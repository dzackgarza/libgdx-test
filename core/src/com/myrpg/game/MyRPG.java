package com.myrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class MyRPG extends ApplicationAdapter {
    private TiledMap map;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;
    private OrthoCamController cameraController;
    private BitmapFont font;
    private SpriteBatch batch;
    ShapeRenderer sr;
    float w, h;

    Box2DDebugRenderer debugRenderer;
    MapLayer objectlayer;
    TiledMapTileLayer terrain2;
    int[][] allTiles;

    int numY, numX;
    int tilesVisible = 10;
    int pixelsPerTile = 32;

    Player player;
    CenterCamera centerCam;

    boolean debug = false;

    @Override
    public void create() {

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();

        int startX = 12;
        int startY = 0;
        player = new Player("pik.png", pixelsPerTile, startX, startY);

        camera = new OrthographicCamera(w, h);

        camera.setToOrtho(false, pixelsPerTile * (w / h) * tilesVisible, pixelsPerTile * tilesVisible);
        //camera.translate((player.x - 3) * 32, (player.y - 4) * 32);
        //camera.update();

        centerCam = new CenterCamera(pixelsPerTile, camera, startX, startY);

        map = new TmxMapLoader().load("maps/map01.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f);

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        sr = new ShapeRenderer();

        objectlayer = map.getLayers().get("objectlayer");
        MapLayer terrain = map.getLayers().get("terrainlayer");
        terrain2 = (TiledMapTileLayer) terrain;

        numY = terrain2.getHeight();
        numX = terrain2.getWidth();

        //[row][column]
        allTiles = new int[terrain2.getHeight()][terrain2.getWidth()];
        for (int i = 0; i < terrain2.getHeight(); i++) {
            // i = row
            for (int j = 0; j < terrain2.getWidth(); j++) {
                //j = col
                if (terrain2.getCell(i, j) != null) {
                    allTiles[i][j] = 1;
                }
            }
        }

        cameraController = new OrthoCamController(camera, player, map, pixelsPerTile, allTiles, centerCam);
        Gdx.input.setInputProcessor(cameraController);

        System.out.println("Game started.");
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();

        if (true) drawCollisionBoxes();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(.8f, .8f);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(),
                camera.position.x - (3 * pixelsPerTile), camera.position.y + (5 * pixelsPerTile));
        font.getData().setScale(.5f, .5f);

        if (debug) drawTileCoordinates();

        player.draw(batch, camera.position);
        font.getData().setScale(.4f);
        font.setColor(Color.SKY);
        font.draw(batch, "(" + (int) camera.position.x + ", " + (int) camera.position.y + ")",
                camera.position.x, camera.position.y + 38);
        batch.end();
    }

    private void drawCollisionBoxes() {
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);

        centerCam.draw(sr);

        player.renderBoundingBox(sr);

        for (int i = 0; i < numY; i++) {
            for (int j = 0; j < numX; j++) {
                if (allTiles[i][j] == 1) {
                    sr.setColor(Color.RED);
                    sr.rect(pixelsPerTile * i, pixelsPerTile * j,
                            pixelsPerTile, pixelsPerTile);
                } else {
                    sr.setColor(Color.BLUE);
                }
            }
        }
        sr.end();
    }

    private void drawTileCoordinates() {
        for (int i = 0; i < terrain2.getHeight(); i++) {
            // i = row
            for (int j = 0; j < terrain2.getWidth(); j++) {
                //j = col
                font.draw(batch, "(" + i + "," + j + ")",
                        (32 * i), (32 * j) + 8);
                font.draw(batch, "(" + (32 * i) + "," + (32 * j) + ")",
                        (32 * i), (32 * j) + 20);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

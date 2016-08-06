package com.myrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;

import static com.myrpg.game.MapBodyBuilder.buildShapes;

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
	int[][] allTiles;

	int numY, numX;
	int tilesVisible = 10;
	int pixelsPerTile = 32;

	Player player;

	@Override
	public void create () {

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		batch = new SpriteBatch();

		player = new Player("pik.png", pixelsPerTile);

		camera = new OrthographicCamera(w, h);

		camera.setToOrtho(false, pixelsPerTile * (w/h) * tilesVisible, pixelsPerTile * tilesVisible);
		camera.update();

		map = new TmxMapLoader().load("maps/map01.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f);

		font = new BitmapFont();
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font.getData().setScale(1f,1f);

		sr = new ShapeRenderer();

		objectlayer = map.getLayers().get("objectlayer");
		MapLayer terrain = map.getLayers().get("terrainlayer");
		TiledMapTileLayer terrain2 = (TiledMapTileLayer) terrain;

		numY = terrain2.getHeight();
		numX = terrain2.getWidth();

		//[row][column]
		allTiles = new int[terrain2.getHeight()][terrain2.getWidth()];
		for(int i = 0; i < terrain2.getHeight(); i++) {
			// i = row
			for (int j = 0; j < terrain2.getWidth(); j++) {
				//j = col
				if (terrain2.getCell(i,j) != null) {
					allTiles[i][j] = 1;
				}
			}
		}

		cameraController = new OrthoCamController(camera, player, map, pixelsPerTile, allTiles);
		Gdx.input.setInputProcessor(cameraController);

		System.out.println("Game started.");
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();

		sr.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);


		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.rect(camera.position.x, camera.position.y, pixelsPerTile, pixelsPerTile);
		player.renderBoundingBox(sr, camera.position.x, camera.position.y);

		for(int i = 0; i < numY; i++) {
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

		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(),
				camera.position.x - (3 * pixelsPerTile), camera.position.y + (5 * pixelsPerTile));
		player.draw(batch, camera.position);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}

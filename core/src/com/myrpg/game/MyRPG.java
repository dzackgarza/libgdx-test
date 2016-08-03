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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
	private Texture img;
	private Console console;
	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private OrthoCamController cameraController;
	private BitmapFont font;
	private SpriteBatch batch;
	Texture texture;
	Sprite sprite;
	ShapeRenderer shapes;
	World world;
	Array<Body> obstacles;
	float w, h;
	Box2DDebugRenderer debugRenderer;
	MapLayer objectlayer;

	@Override
	public void create () {

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("pik.png"));
		sprite = new Sprite(texture);
		sprite.setPosition(w/2, h/2);

		console = new GUIConsole();
		console.setCommandExecutor(new ConsoleHandler());
		console.setKeyID(Input.Keys.F12);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w/h) * 10, 10);
		camera.update();

		map = new TmxMapLoader().load("maps/map01.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);

		cameraController = new OrthoCamController(camera, sprite, map);
		Gdx.input.setInputProcessor(cameraController);

		font = new BitmapFont();

		shapes = new ShapeRenderer();

		objectlayer = map.getLayers().get("objectlayer");

		debugRenderer = new Box2DDebugRenderer();

		world = new World(new Vector2(0, -10), true);
		obstacles = buildShapes(map, 32, world);
		System.out.println("Game started.");
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();

		shapes.begin(ShapeRenderer.ShapeType.Line);
		shapes.rect(w/2, h/2, 64, 64);
		shapes.setColor(Color.RED);
		//shapes.rect(obj.getX(), obj.getY(), 64, 64);
		shapes.end();


		//batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		sprite.draw(batch);
		batch.end();

		console.draw();
		world.step(1/60f, 6, 2);
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void dispose () {
		batch.dispose();
		console.dispose();
	}
}

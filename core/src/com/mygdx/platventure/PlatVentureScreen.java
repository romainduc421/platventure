package com.mygdx.platventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.platventure.model.blocs.UIBloc;
import com.mygdx.platventure.model.utils.Constants;
import com.mygdx.platventure.sons.Sounds;
import com.mygdx.platventure.vues.MapView;
import com.mygdx.platventure.vues.StartScreen;

public class PlatVentureScreen extends Game {
	private static PlatVentureScreen gameInstance;
	private final MapView mapView = new MapView();
	private Screen startScreen;
	private SpriteBatch batch;
	private OrthographicCamera cam;

	@Override
	public void create () {
		cam = new OrthographicCamera();
		cam.setToOrtho(false,16f,16f);
		batch = new SpriteBatch();
		gameInstance = this;
		startScreen = new StartScreen(this);
		setStartScreen();
	}

	@Override
	public void render () {
		this.getScreen().render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height)
	{
		this.getScreen().resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if(this.getScreen() == mapView){
			float y = Constants.CAMERA_SIZE.getInstance() * (float) height/(float) width;
			cam.setToOrtho(false, Constants.CAMERA_SIZE.getInstance(), y);
			cam.position.set(cam.viewportWidth/2, cam.viewportHeight/2, 0);
		}
	}

	public static PlatVentureScreen getInstance()
	{
		return gameInstance;
	}


	@Override
	public void dispose () {
		mapView.dispose();
		batch.dispose();
		Sounds.dispose();
		UIBloc.dispose();
	}

	public void setPlatVentureScreen() {
		setScreen(mapView);
	}

	public void setStartScreen() {
		setScreen(startScreen);
	}

	public MapView getMapView() {
		return mapView;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCam() {
		return cam;
	}
}

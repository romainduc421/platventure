package com.mygdx.platventure.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.platventure.PlatVentureScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(880,630);
		config.setResizable(true);
		config.setTitle("PlatVenture");
		config.setForegroundFPS(60);

		new Lwjgl3Application(new PlatVentureScreen(), config);
	}
}

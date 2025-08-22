package com.nostalgi.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.nostalgi.engine.IO.DiskGameInstanceStore;
import com.nostalgi.game.ExampleGameInstance;
import com.nostalgi.game.Game;
import com.nostalgi.game.desktop.com.nostalgi.cli.CLI;
import com.nostalgi.server.HeadlessApplication;
import com.nostalgi.server.ServerConfig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		CLI cli = new CLI(arg);

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(cli.getIntArg("width", 1920), cli.getIntArg("height", 1080));
		config.setResizable(false);
		
		if(cli.getBooleanArg("fullscreen")) {
			config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		}

		ServerConfig serverConfig = new ServerConfig();

		if(cli.getBooleanArg("server")) {
			new HeadlessApplication(new Game(new ExampleGameInstance(new DiskGameInstanceStore()), true), serverConfig).run();
		} else {
			new Lwjgl3Application(new Game(new ExampleGameInstance(new DiskGameInstanceStore()), false), config);
		}
	}
}

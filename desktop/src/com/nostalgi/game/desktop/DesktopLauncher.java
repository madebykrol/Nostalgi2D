package com.nostalgi.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nostalgi.engine.IO.DiskGameInstanceStore;
import com.nostalgi.game.ExampleGameInstance;
import com.nostalgi.game.Game;
import com.nostalgi.game.desktop.com.nostalgi.cli.CLI;
import com.nostalgi.server.HeadlessApplication;
import com.nostalgi.server.ServerConfig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		CLI cli = new CLI(arg);

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = cli.getIntArg("height", 1080);
		config.width = cli.getIntArg("width", 1920);
		config.fullscreen = cli.getBooleanArg("fullscreen");
		config.resizable = false;

		ServerConfig serverConfig = new ServerConfig();

		if(cli.getBooleanArg("server")) {
			new HeadlessApplication(new Game(new ExampleGameInstance(new DiskGameInstanceStore()), true), serverConfig).run();
		} else {
			new LwjglApplication(new Game(new ExampleGameInstance(new DiskGameInstanceStore()), false), config);
		}
	}
}

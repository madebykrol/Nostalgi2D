package com.nostalgi.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nostalgi.game.ExampleTopDownRPGGame;
import com.nostalgi.game.desktop.com.nostalgi.cli.CLI;
import com.nostalig.server.HeadlessApplication;
import com.nostalig.server.ServerConfig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		CLI cli = new CLI(arg);

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = cli.getIntArg("height", 1080);
		config.width = cli.getIntArg("width", 1920);
		config.fullscreen = cli.getBooleanArg("fullscreen");

		ServerConfig serverConfig = new ServerConfig();

		if(cli.getBooleanArg("server")) {
			new HeadlessApplication(new ExampleTopDownRPGGame(true), serverConfig).run();
		} else {
			new LwjglApplication(new ExampleTopDownRPGGame(false), config);
		}
	}
}

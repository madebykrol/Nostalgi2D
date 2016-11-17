package com.nostalgi.server;

import com.badlogic.gdx.ApplicationListener;

/**
 * Created by ksdkrol on 2016-09-19.
 */
public class HeadlessApplication {

    private ApplicationListener application;
    private ServerConfig config;
    protected Thread mainLoopThread;
    protected boolean running = true;

    public HeadlessApplication(ApplicationListener application, ServerConfig config) {
        this.application = application;
        this.config = config;
    }

    public void run() {
        mainLoopThread = new Thread("Headless Application") {
            @Override
            public void run () {

                try {
                    HeadlessApplication.this.mainLoop();
                } catch (Throwable t) {

                }
            }
        };
        mainLoopThread.start();
    }

    protected void mainLoop() {

        application.create();

        while(running) {
            application.render();
        }

        application.dispose();
    }
}

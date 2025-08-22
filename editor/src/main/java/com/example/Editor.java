package com.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.nostalgi.engine.IO.DiskGameInstanceStore;
import com.nostalgi.game.ExampleGameInstance;
import com.nostalgi.game.Game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Editor {

    public static JFrame frame;
    public static Canvas canvas;

    public static void main (String[] arg) {

        frame = new JFrame();

        frame.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent arg0) {
            }
            public void windowIconified(WindowEvent arg0) {
            }
            public void windowDeiconified(WindowEvent arg0) {
            }
            public void windowDeactivated(WindowEvent arg0) {
            }
            public void windowClosing(WindowEvent arg0) {
            }
            public void windowClosed(WindowEvent arg0) {

            }
            public void windowActivated(WindowEvent arg0) {
            }
        });
        frame.setTitle("Swing + LWJGL");
        frame.setBounds(100, 100, 1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Note: LWJGL3 doesn't support embedding in AWT Canvas like LWJGL2 did
        // For a modern editor, consider using a separate window or switching to JavaFX
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Nostalgi2D Editor");
        config.setWindowedMode(800, 600);

        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JSplitPane splitPane = new JSplitPane();
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);

        JPanel canvasPanel = new JPanel();
        canvasPanel.setLayout(new BorderLayout(0, 0));
        splitPane.setRightComponent(canvasPanel);

        canvas = new Canvas() {
            private static final long serialVersionUID = -1069002023468669595L;
            public void removeNotify() {

            }
        };

        canvas.setIgnoreRepaint(true);
        canvas.setPreferredSize(new Dimension(800, 600));
        canvas.setMinimumSize(new Dimension(320, 240));
        canvas.setVisible(true);
        canvasPanel.add(canvas, BorderLayout.CENTER);

        frame.setVisible(true);
        
        // Launch LibGDX application in separate window since LWJGL3 doesn't support AWT Canvas embedding
        new Lwjgl3Application(new Game(new ExampleGameInstance(new DiskGameInstanceStore()), false), config);



    }
}

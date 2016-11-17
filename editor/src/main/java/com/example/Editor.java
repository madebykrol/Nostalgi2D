package com.example;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Mygame";
        config.width = 800;
        config.height = 600;


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
        new LwjglApplication(new Game(false), config, canvas);



    }
}

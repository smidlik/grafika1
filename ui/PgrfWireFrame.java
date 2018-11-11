package ui;

import drawables.*;
import drawables.Polygon;
import solids.Cube;
import solids.Solid;
import transforms.Camera;
import utils.Transforms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static ui.PgrfFrame.FPS;

public class PgrfWireFrame extends JFrame {

    static int FPS = 1000/30;
    static int width = 1200;
    static int height = 800;
    private BufferedImage img;
    private JPanel panel;
    private Transforms transformer;
    private List<Solid> solids;
    private Camera camera;

    public static void main(String[] args) {
        PgrfWireFrame pgrfWireFrame = new PgrfWireFrame();
        pgrfWireFrame.init(width,height);
    }

    private void init(int width, int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width,height);
        setTitle("Drátový model");

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);


        camera = new Camera();
        solids = new ArrayList<>();
        panel = new JPanel();
        add(panel);

        setLocationRelativeTo(null);
        transformer = new Transforms(img);


        solids.add(new Cube(100));


        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });

        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 100, FPS);
        draw();
    }

    private void draw() {
        img.getGraphics().fillRect(0,0,img.getWidth(),img.getHeight());

        panel.getGraphics().drawImage(img, 0,0,img.getWidth(), img.getHeight(), null); // zde ji to vykresli
        panel.paintComponents(getGraphics());
    }
    /*
     TODO: v main inicializovat PgrfWireFrame metody:
     init (šířka, výška)
     draw();
     fields:
     BufferedImage, List<Solid>, Transformer
     Camera
     */
}
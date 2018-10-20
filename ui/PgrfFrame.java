package ui;

import drawables.Drawable;
import drawables.DrawableType;
import drawables.Line;
import drawables.Point;
import drawables.Polygon;
import utils.Renderer;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PgrfFrame extends JFrame implements MouseMotionListener {

    static int FPS = 1000 / 60;
    private BufferedImage img; // pro vykreslovani
    static int width = 800;
    static int height = 600;
    private JPanel panel; // musime pridat panel, protoze to nevykreslovalo
    private Renderer renderer;
    private int coorX, coorY;
    private int lineX1,lineX2,lineY1,lineY2;
    private int clicX = 100, clicY = 100;
    private int count = 3;
    private int nConect = 0;
    private boolean nFirstClick = true;
    private boolean lineFirstClick = true;

    private List<Point> points = new ArrayList<>();

    private DrawableType type = DrawableType.LINE;


    private List<Drawable> drawables;


    public static void main(String... args) {
        PgrfFrame pgrfFrame = new PgrfFrame();
        pgrfFrame.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // Prideleni ARGB barev
        pgrfFrame.init(width, height);
    }

    // Inicializace vykresleni
    private void init(int width, int height) {

        drawables = new ArrayList<>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Počítačová grafika");

        panel = new JPanel();
        add(panel);


        panel.addMouseMotionListener(this);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (type == DrawableType.LINE) {
                    drawables.add(new Line(lineX1, lineY1, e.getX(), e.getY()));

                }



            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if (type == DrawableType.N_OBJECT) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        points.add(new Point(e.getX(), e.getY()));
                    }
                    if (e.getButton() == MouseEvent.BUTTON3 && points.size() > 2) {
                        if (!nFirstClick)drawables.remove(nConect);
                        drawables.add(new Line(points.get(0), points.get(points.size() - 1)));
                        nConect = drawables.size()-1;
                        nFirstClick = false;
                    }

                    if (points.size() > 1) {
                        for (int i = 0; i < points.size(); i++) {
                            drawables.add(new Line(points.get(i), points.get(i + 1)));
                        }
                    }
                }

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (type == DrawableType.LINE) {
                    if(lineFirstClick){
                        lineX1 = e.getX();
                        lineY1 = e.getY();
                    }
                    lineFirstClick = !lineFirstClick;

                }
                    if (type == DrawableType.POLYGON) {
                    clicX = e.getX();
                    clicY = e.getY();

                    drawables.add(new Polygon(clicX, clicY, coorX, coorY, count));
                }
            }

        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP)//sipka nahoru
                {
                    count++;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN)//sipka dolu
                {
                    if (count > 3) count--;
                }
                if (e.getKeyCode() == KeyEvent.VK_ADD)//plus na num klavesnici
                {

                }
                if (e.getKeyCode() == KeyEvent.VK_L) type = DrawableType.LINE;
                if (e.getKeyCode() == KeyEvent.VK_N) type = DrawableType.N_OBJECT;
                if (e.getKeyCode() == KeyEvent.VK_P) type = DrawableType.POLYGON;
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    drawables.clear();

                }

                super.keyReleased(e);
            }
        });

        if(type == DrawableType.LINE){

        }



        setLocationRelativeTo(null);

        renderer = new Renderer(img);

        Timer timer = new Timer(); // timer pro obnoveni toho vykresleni a ted uz to funguje
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 100, FPS);
        draw();

    }

    // vykresleni
    private void draw() {
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight()); // prideleni pozadi
        if(type == DrawableType.LINE )renderer.lineDDA(lineX1,lineY1,coorX,coorY);

        if (type == DrawableType.POLYGON) {
            renderer.polygon(clicX, clicY, coorX, coorY, count);
        }

        for (Drawable drawable : drawables) {
            drawable.draw(renderer);
        }


        panel.getGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null); // zde ji to vykresli
        panel.paintComponents(getGraphics());
    }

    @Override
    public void mouseDragged(MouseEvent e) { // v hlavicce mousemotionlisenner a dat CTRL+I a tyto 2 tridy se udelaji samy

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        coorX = e.getX(); // zjisti kde byla naposledy mys a ulozi jeji misto (pixely)
        coorY = e.getY();
    }
}
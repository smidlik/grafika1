package ui;

import drawables.Drawable;
import drawables.DrawableType;
import drawables.Line;
import drawables.Point;
import drawables.Polygon;
import utils.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PgrfFrame extends JFrame implements MouseMotionListener {



    static int FPS = 1000 / 60;
    private BufferedImage img; // pro vykreslovani
    private JPanel panel; // musime pridat panel, protoze to nevykreslovalo
    private JPanel control;
    private JButton btnLine;
    private JButton btnPolygon;
    private JButton btnNOb;
    private Renderer renderer;

    private int coorX, coorY;
    static int width = 800;
    static int height = 600;
    private int lineX1,lineX2,lineY1,lineY2;
    private int clicX = 300, clicY = 300;
    private int count = 3;
    private int nConect = 0;
    private boolean nFirstClick = true;
    private boolean lineFirstClick = false;
    private List<Point> points;
    private DrawableType type = DrawableType.LINE;
    private List<Drawable> drawables;

    public static void main(String[] args) {
        PgrfFrame pgrfFrame = new PgrfFrame();
        pgrfFrame.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // Prideleni ARGB barev
        pgrfFrame.initGraphic(width, height);
    }
    // Inicializace vykresleni
    private void initGraphic(int width,int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Počítačová grafika");


        drawables = new ArrayList<>();
        points = new ArrayList<>();
        panel = new JPanel(new BorderLayout());

/*
        btnLine = new JButton("Line");
        btnPolygon = new JButton("Polygon");
        btnNOb = new JButton("N Object");
        control = new JPanel(new BorderLayout());        control.add(btnLine,BorderLayout.WEST);

        control.add(btnPolygon,BorderLayout.CENTER);
        control.add(btnNOb,BorderLayout.EAST);
        add(control,BorderLayout.NORTH);
*/
        add(panel, BorderLayout.CENTER);





        panel.addMouseMotionListener(this);
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if(type == DrawableType.LINE){
                    lineX1 = e.getX();
                    lineY1 = e.getY();
                    lineFirstClick = true;
                }


            }


            @Override
            public void mouseReleased(MouseEvent e) {
                if(type == DrawableType.LINE){
                    lineX2 = e.getX();
                    lineY2 = e.getY();
                    lineFirstClick = false;

                }

                if (type == DrawableType.POLYGON) { ///POLYGON
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        points.add(new Point(e.getX(), e.getY()));
                    }
                    if (points.size() > 2) {
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
                if (type == DrawableType.N_OBJECT) {
                   if(nFirstClick){
                       clicX = e.getX();
                       clicY = e.getY();
                   }
                   else {
                       drawables.add(new Polygon(clicX, clicY, coorX, coorY, count));

                   }
                    nFirstClick = !nFirstClick;
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
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    type = DrawableType.LINE;

                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    type = DrawableType.N_OBJECT;

                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    type = DrawableType.POLYGON;

                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    drawables.clear();
                }

                super.keyReleased(e);
            }
        });


        Timer timer = new Timer(); // timer pro obnoveni toho vykresleni a ted uz to funguje
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 100, FPS);

        renderer = new Renderer(img);


        draw();
    }

    // vykresleni
    private void draw() {
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight()); // prideleni pozadi
      if(type == DrawableType.LINE) {
          if (lineFirstClick) {
              renderer.lineDDA(lineX1, lineY1, coorX, coorY);
          }
          else drawables.add(new Line(lineX1,lineY1,lineX2,lineY2));
      }
      if(type==DrawableType.N_OBJECT&&!nFirstClick){
          renderer.polygon(clicX,clicY,coorX,coorY,count);

      }
        if (type == DrawableType.POLYGON){
            if (points.size() > 2) {
                renderer.lineDDA(points.get(0).getX(),points.get(0).getY(), coorX,coorY);
                renderer.lineDDA(points.get(points.size()-1).getX(),points.get(points.size()-1).getY(),coorX,coorY);
            }
        }

        for (Drawable drawable : drawables) {
            drawable.draw(renderer);
        }


        panel.getGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null); // zde ji to vykresli
        panel.paintComponents(getGraphics());
    }

    @Override
    public void mouseDragged(MouseEvent e) { // v hlavicce mousemotionlisenner a dat CTRL+I a tyto 2 tridy se udelaji samy
        coorX = e.getX(); // zjisti kde byla naposledy mys a ulozi jeji misto (pixely)
        coorY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        coorX = e.getX(); // zjisti kde byla naposledy mys a ulozi jeji misto (pixely)
        coorY = e.getY();
    }
}
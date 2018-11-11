package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;

public class SeedFillFromWeb extends JPanel implements MouseListener {
    private static final int WIDTH = 720;
    private static final int HEIGHT = 480;
    private static final int BOUNDARY_RGB = Color.red.getRGB();
    private static final Color RED = Color.RED;
    private BufferedImage mImage;
    private ArrayList<Point> mVertices;
    private Boolean mFloodPoint;
    private void clearImage(){
        mVertices = new ArrayList<>();
        Graphics2D graphics2D = mImage.createGraphics();
        graphics2D.setBackground(Color.black);
        graphics2D.clearRect(0,0,WIDTH,HEIGHT);
        repaint();
        revalidate();
    }
    SeedFillFromWeb(){
        mImage = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_3BYTE_BGR);
        mVertices = new ArrayList<>();
        mFloodPoint = false;
        addMouseListener(this);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = mImage.createGraphics();
        graphics2D.setBackground(Color.black);
        g.drawImage(mImage,0,0,this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    private boolean isVertexPoint(int x, int y){
        for(Point point :mVertices)
            if(point.X == x && point.Y == y)
                return true;
        return false;
    }
    private boolean isInside(int x, int y) {
        boolean result = false;
        int i,j;
        for (i = 0, j = mVertices.size()- 1; i < mVertices.size(); j = i++) {
            if ((mVertices.get(i).Y > y) != (mVertices.get(j).Y > y) &&
                    (x < (mVertices.get(j).X - mVertices.get(i).X) * (y - mVertices.get(i).Y) /
                            (mVertices.get(j).Y-mVertices.get(i).Y) + mVertices.get(i).X)) {
                result = !result;
            }
        }
        return result;
    }
    private void drawPolygon() {
        if(mVertices.size() >= 3){
            int totalVertices = mVertices.size();
            Graphics2D graphics2D = mImage.createGraphics();
            graphics2D.setColor(RED);
            for(int i = 0 ; i < totalVertices - 1; ++i){
                Point current = mVertices.get(i);
                Point next = mVertices.get(i+1);
                graphics2D.drawLine(current.X,current.Y,next.X, next.Y);
            }
            Point first = mVertices.get(0);
            Point last = mVertices.get(totalVertices -1);
            graphics2D.drawLine(first.X,first.Y,last.X,last.Y);
            repaint();
            revalidate();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Adding point : " + e.getX()+" "+e.getY());
            mVertices.add(new Point(e.getX(),e.getY()));
        }
        else if(e.getButton() == MouseEvent.BUTTON3){
            if(!mFloodPoint) {
                System.out.println("Drawing polygon");
                drawPolygon();
                mFloodPoint = true;
            }
            else {
                System.out.println("Filling polygon");
                floodFill(e.getX(),e.getY(),Color.green);
                mFloodPoint = false;
            }
        }
        else if(e.getButton() == MouseEvent.BUTTON2){
            clearImage();
        }
    }

    private void floodFill(int x, int y, Color fillColor) {

        Stack<Point> callStack = new Stack<>();
        callStack.add(new Point(x,y));
        while(!callStack.isEmpty()) {
            Point point = callStack.pop();
            if(isInside(point.X, point.Y)) {

                if(mImage.getRGB(point.X, point.Y) != fillColor.getRGB() /*&&
                            mImage.getRGB(point.X, point.Y) != BOUNDARY_RGB*/) {
                    System.out.println("adding point " + point.toString());
                    mImage.setRGB(point.X, point.Y, fillColor.getRGB());
                    repaint();
                    revalidate();
                    callStack.add(new Point(point.X + 1, point.Y));
                    callStack.add(new Point(point.X - 1, point.Y));
                    callStack.add(new Point(point.X, point.Y + 1));
                    callStack.add(new Point(point.X, point.Y - 1));
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    class Point{
        int X, Y;
        Point(int X, int Y){
            this.X = X;
            this.Y = Y;
        }
    }
    public  static void main(String[] args) {
        SeedFillFromWeb app = new SeedFillFromWeb();
        JFrame jFrame = new JFrame();
        jFrame.setSize(720, 480);
        jFrame.setBackground(Color.BLUE);
        jFrame.add(app);
        jFrame.setVisible(true);

    }
}
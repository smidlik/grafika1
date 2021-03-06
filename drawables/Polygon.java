package drawables;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import transforms.Point2D;
import utils.Renderer;

public class Polygon implements Drawable {

    List<Point2D> points;
    private boolean done;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void addPoint(Point2D p) {
        points.add(p);
    }

    @Override
    public void draw(Renderer renderer) {
        if(!done) {
            if (points.size() > 1) {
                for (int i = 0; i < points.size(); i++) {
                    Point2D point1 = points.get(i);
                    Point2D point2 = points.get((i + 1) % points.size());
                    renderer.lineDDA(point1, point2, getColor());
                }
            }
        }
        else {
            renderer.scanLine(points, getColor(),getFillColor());
        }
    }

    @Override
    public void modifyLastPoint(int x, int y) {
        // ignored
    }

    @Override
    public int getFillColor() {
        return Color.YELLOW.getRGB();
    }

    @Override
    public int getColor() {
        return Color.BLUE.getRGB();

    }

    public void setDone(boolean done) {
        this.done = done;
    }
}

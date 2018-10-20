package drawables;

import utils.Renderer;

public class Line implements Drawable{

    int x1, x2, y1, y2;

    public Line(Point point1, Point point2){
        x1 = point1.getX();
        y1 = point1.getY();
        x2 = point2.getX();
        y2 = point2.getY();
    }

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public void draw(Renderer renderer) {
        renderer.lineDDA(x1,y1,x2,y2);
    }
}

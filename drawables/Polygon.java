package drawables;

import utils.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements Drawable {
    

    private int x1,y1,x2,y2,count;


    public Polygon(int count, Point point1, Point point2) {
        this.count = count;
        x1=point1.getX();
        y1=point1.getY();
        x2=point2.getX();
        y2=point2.getY();
    }

    public Polygon(int x1, int y1, int x2, int y2, int count) {
     this.x1 = x1;
     this.y1 = y1;
     this.x2 = x2;
     this.y2 = y2;
     this.count = count;
    }



    @Override
    public void draw(Renderer renderer) {
        renderer.polygon(x1,y1,x2,y2,count);
    }
}

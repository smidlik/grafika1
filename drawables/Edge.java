package drawables;

import transforms.*;

public class Edge {

    double x1,x2,y1,y2;
    float k,q;

    public Edge(Point2D p1, Point2D p2){
        this.x1 = p1.getX();
        this.x2 = p2.getX();
        this.y1 = p1.getY();
        this.y2 = p2.getY();
    }


    public boolean isHorizontal(){
        if (y1 == y2)return true;
        else return false;

        //TODO vrátí tru když je horizontalní // DONE
    }

    public void order(){
        //TODO seradit dle y1<y2
        if(y2<y1){
            double temp = y1;
            y1 = y2;
            y2 = temp;

        }

    }

    public void cut(){
        //TODO ořiznout poslední pixel

    }

    public void compute(){
        //TODO výpočet k a q
    }

    public  int findX(int y){
        return 0;  //TODO vrati X pro dane Y
    }

    public boolean isIntersection(){
        return false; //TODO vrátí tru když je Y>Y1 && Y<Y2
    }

    public int yMin(int yMin){
        if(y1<y2&&y1<yMin)return (int) y1;
        if(y2<y1&&y2<yMin)return (int) y2;
        return yMin;
    }
    public  int yMax(int yMax){
        if(y1>y2&&y1>yMax)return (int) y1;
        if(y2>y1&&y2>yMax)return (int) y2;
        return yMax;
    }
}

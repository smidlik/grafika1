package drawables;

public class Edge {

    int x1,x2,y1,y2;
    float k,q;

    public Edge(Point p1, Point p2){
        this.x1 = p1.getX();
        this.x2 = p2.getX();
        this.y1 = p1.getY();
        this.y2 = p2.getY();
    }

    public boolean isHorizontal(){
        return false; //TODO vrátí tru když je horizontalní
    }

    public void order(){
        //TODO seradit dle y1<y2

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
        return 0; // TODO vrátí nejmenší z yMin, Y2, Y1
    }
    public  int yMax(int yMax){
        return 0; //TODO vrátí nejvetší z yMax, y1, y2
    }
}

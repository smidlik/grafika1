package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.StrictMath.round;

public class Renderer {

    private int color;
    BufferedImage img;

    public Renderer(BufferedImage img) {
        this.img = img;
        color = Color.RED.getRGB();
    }

    private void drawPixel(int x, int y){
        if (x < 50 || x > 750) return;
        if (y < 50 || y > 550) return;
        img.setRGB(x,y,color);
    }

    public void lineTrivial(int x1, int y1, int x2, int y2){
        // obecny predpis je: y = kx + q
        int dx = x1 - x2;
        int dy = y1 - y2;
        float k = (float)dy/ (float) dx;

        if (Math.abs(dx) > Math.abs(dy)){
            //ridici osa X
            // q neni potreba, protoze je to pocatecni bod = y1
            if (x1 > x2){
                int p = x1;
                x1 = x2;
                x2 = p;
                p = y1;
                y1 = y2;
                //y2 = p;
            }

            for (int x = x1; x < x2; x++) {
                int y = y1 + (int) (k * (x - x1));
                drawPixel(x,y);
            }



        } else {
            // ridici osa Y
            float q;
            if (y1 > y2){
                int p = x1;
                x1 = x2;
                //x2 = p;
                p = y1;
                y1 = y2;
                y2 = p;
            }

            q = y1 - k*(float) x1;
            for (int y = y1; y < y2; y++) {
                int x = (int)((y-q)/k);
                drawPixel(x,y);
            }
        }
    }

    public void lineDDA(int x1, int y1, int x2, int y2){ //TODO: Do projektu udelat Bresemham a budou Bonus Body (BB) 😛
        int dx, dy;
        float x, y, G, H;

        dx = x2 - x1;
        dy = y2 - y1;
        float k = (float)dy/ (float) dx;

        // urceni ridici osy
        if (Math.abs(dx) > Math.abs(dy)){
            G = 1;
            H = k;
            if (x1 > x2){
                int p = x1;
                x1 = x2;
                x2 = p;
                p = y1;
                y1 = y2;
                y2 = p;
            }
        } else {
            G = 1/k;
            H = 1;
            //otoceni
            if (y1 > y2){
                int p = x1;
                x1 = x2;
                x2 = p;
                p = y1;
                y1 = y2;
                y2 = p;
            }
        }

        x = x1;
        y = y1;
        int max = Math.max(Math.abs(dx), Math.abs(dy));
        for (int l = 0; l <= max ; l++) {

            drawPixel(Math.round(x),Math.round(y));
            x += G;
            y += H;
        }


    }

    public void polygon(int x1, int y1, int x2, int y2, int count){
        double x0 = x2 - x1;
        double y0 = y2 - y1;
        double circleRaridus = 2 * Math.PI;
        double step = circleRaridus / (double) count;


        for (double i = 0; i < circleRaridus ; i += step) {
            //dle rotacni matice
            double x = x0 * Math.cos(step) + y0 * Math.sin(step);
            double y = y0 * Math.cos(step) - x0 * Math.sin(step);
            lineDDA((int)x0 + x1,(int)y0 + y1 , (int)x + x1, (int)y + y1 );

            // potreba zmenit x0, y0
            x0 = x;
            y0 = y;



        }
    }
}
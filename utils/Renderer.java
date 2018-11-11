package utils;

import drawables.Edge;
import transforms.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private int color;
    private BufferedImage img;

    public Renderer(BufferedImage img) {
        this.img = img;
        color = Color.RED.getRGB();
    }

    //DRAW PIXEL
    private void drawPixel(Point2D point2D, int color) {
        drawPixel((int) point2D.getX(), (int) point2D.getY(), color);
    }

    private void drawPixel(Point2D point2D) {
        drawPixel((int) point2D.getX(), (int) point2D.getY(), color);
    }

    private void drawPixel(int x, int y) {
        drawPixel(x, y, color);
    }

    private void drawPixel(int x, int y, int color) {
        if (x < 0 || x >= 800) return;
        if (y < 0 || y >= 600) return;
        img.setRGB(x, y, color);
    }

    ////////////////////////
    //////////LINE TRIVIAL///////
    public void lineTrivial(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        drawPixel(x1, y1);
        if (Math.abs(dx) > Math.abs(dy)) {
            float k = (float) dy / (float) dx;
            float q = y1 - k * x1;
            if (dx < 0) dx = -1;
            else dx = 1;
            while (x1 != x2) {
                x1 += dx;

                drawPixel(x1, Math.round(k * x1 + q));
            }
        } else if (dy != 0) {
            float m = (float) dx / (float) dy;
            float b = x1 - m * y1;
            if (dy < 0) dy = -1;
            else dy = 1;
            while (y1 != y2) {
                y1 += dy;
                drawPixel(Math.round(m * y1 + b), y1);
            }
        }
    }
    public  void lineDDA(Point2D p1,Point2D p2, int color){
        lineDDA((int)p1.getX(),(int)p1.getY(),(int)p2.getX(),(int)p2.getY(),color);
    }

    public void lineDDA(int x1, int y1, int x2, int y2, int color) {

        float k, g, h; //G = PŘÍRŮSTEK X, H = PŘÍRŮSTEK Y
        int dy = y2 - y1;
        int dx = x2 - x1;
        k = dy / (float) dx;

        //určení řídící osy
        if (Math.abs(dx) > Math.abs(dy)) {
            g = 1;
            h = k;
            if (x1 > x2) { // prohození
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        } else {
            g = 1 / k;
            h = 1;
            if (y1 > y2) { //otočení
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        }

        float x = x1;
        float y = y1;
        int max = Math.max(Math.abs(dx), Math.abs(dy));

        for (int i = 0; i <= max; i++) {
            drawPixel(Math.round(x), Math.round(y), color);
            x += g;
            y += h;
        }
    }

    public void lineBresenham(int x1, int y1, int x2, int y2, int color) {
        float x, y, k1, k2, p; //G = PŘÍRŮSTEK X, H = PŘÍRŮSTEK Y
        int dy = y2 - y1;
        int dx = x2 - x1;
        x = x1;
        y = y1;
        p = 2 * dy - dx;
        k1 = 2 * dy;
        k2 = 2 * (dy - dx);
        if (Math.abs(dy) < Math.abs(dx)) {
            if (x2 < x1) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
                y = x1;
            }
            if (y2 < y1) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
                x = y1;
            }
        }
        while (x < x2) {
            x = x + 1;
            if (p < 0) p += k1;
            else p += k2;
            y += 1;
            drawPixel((int) x, (int) y, color);
        }

    }

    public void polygon(int x1, int y1, int x2, int y2, int count) {
        double x0 = x2 - x1;
        double y0 = y2 - y1;
        double circleRadius = 2 * Math.PI;
        double step = circleRadius / (double) count;
        for (double i = 0; i < circleRadius; i += step) {
            // dle rotační matice
            double x = x0 * Math.cos(step) + y0 * Math.sin(step);
            double y = y0 * Math.cos(step) - x0 * Math.sin(step);
            lineDDA((int) x0 + x1, (int) y0 + y1,
                    (int) x + x1, (int) y + y1, color);
            // potřeba změnit x0, y0
        }
    }

    public void seedFill(int x, int y, int oldColor, int newColor) {
        if (oldColor == img.getRGB(x, y)) {
            drawPixel(x, y, newColor);

            seedFill(x - 1, y, oldColor, newColor);
            seedFill(x + 1, y, oldColor, newColor);
            seedFill(x, y + 1, oldColor, newColor);
            seedFill(x, y - 1, oldColor, newColor);
        }

    }

    public void scanLine(List<Point2D> points, int borderColor, int fillColor) {

        int yMax = 0;
        int yMin = img.getHeight();
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            //vytváření usečekf
            //volání určitých metod
            //hledání hraničních
            //přidání Edge do seznamu Edges
        }
        /*
         * TODO:
         *  1) připravit yMax a yMin -->DONE
         *  2) definice seznamu useček
         *   - seřadit dle y1<y2
         *   - vypočítat koeficienty k a q
         *   - oříznout poslední pixel
         *   3) for cykl od yMin do yMax
         *   - pro každé y hledáme prusecik s useckami
         *   - pro sudý počet prisečíku seřadit dle x
         *   4)
         *
         * */


    }
}

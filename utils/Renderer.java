package utils;

import drawables.Edge;
import drawables.Point;

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

    private void drawPixel(int x, int y) {
        drawPixel(x, y, color);
    }

    private void drawPixel(int x, int y, int color) {
        if (x < 0 || x >= 800) return;
        if (y < 0 || y >= 600) return;
        img.setRGB(x, y, color);
    }

    public void lineTrivial(int x1, int y1, int x2, int y2) {
        // y = kx + q
        int dx = x1 - x2;
        int dy = y1 - y2;

        if (Math.abs(dx) > Math.abs(dy)) {
            // řídící osa X
            // otočení
            if (x1 > x2) {
                int p = x1;
                x1 = x2;
                x2 = p;
                p = y1;
                y1 = y2;
                //y2 = p;
            }

            float k = (float) dy / (float) dx;
            for (int x = x1; x < x2; x++) {
                int y = y1 + (int) (k * (x - x1));
                drawPixel(x, y);
            }

        } else {
            // řídící osa Y

        }
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
            drawPixel(Math.round(x), Math.round(y),color);
            x += g;
            y += h;
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
    public void scanLine(List<Point> points, int borderColor, int fillColor){

        int yMax = 0;
        int yMin = img.getHeight();
        List<Edge>edges = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            //vytváření useček
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

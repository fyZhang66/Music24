package graphics;

import java.awt.*;
import java.util.Random;

public class G {
    public static Random RND = new Random();

    public static int rnd(int max) {
        return RND.nextInt(max);
    }

    public static Color rndColor() {
        return new Color(rnd(256), rnd(256), rnd(256));
    }

    // a background rectangle that is 3000x3000
    public static final VS BACKGROUND_RECT = new VS(0, 0, 3000, 3000);

    // clear the background with a color
    public static void fillBackground(Graphics g) {
        BACKGROUND_RECT.fill(g, Color.WHITE);
    }

    // -----------------------V------------------------
    // A simple 2D vector class
    // supports set, add
    public static class V {
        public int x, y;

        public V(V v){x = v.x; y = v.y;} 

        public V(int x, int y) {
            this.set(x, y);
        }

        public void set(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void add(V v) {
            x += v.x;
            y += v.y;
        }
    }

    // -----------------------VS-----------------------
    // reactangle class with location and size
    // supports fill method and hit method
    public static class VS {
        public V loc, size;

        public VS(int x, int y, int w, int h) {
            loc = new V(x, y);
            size = new V(w, h);
        }

        public void fill(Graphics g, Color c) {
            g.setColor(c);
            g.fillRect(loc.x, loc.y, size.x, size.y);
        }

        public boolean hit(int x, int y) {
            return loc.x <= x && loc.y <= y && x <= (loc.x + size.x) && y <= (loc.y + size.y);
        }

        public int xL() {
            return loc.x;
        }

        public int xH() {
            return loc.x + size.x;
        }

        public int xM() {
            return (loc.x + loc.x + size.x) / 2;
        }

        public int yL() {
            return loc.y;
        }

        public int yH() {
            return loc.y + size.y;
        }

        public int yM() {
            return (loc.y + loc.y + size.y) / 2;
        }
    }

    // -----------------------spline--------------------
    public static void spline(Graphics g, int ax, int ay, int bx, int by, int cx, int cy, int n) {
        if (n == 0) {
            g.drawLine(ax, ay, cx, cy);
            return;
        }
        int abx = (ax + bx) / 2, aby = (ay + by) / 2;
        int bcx = (bx + cx) / 2, bcy = (by + cy) / 2;
        int abcx = (abx + bcx) / 2, abcy = (aby + bcy) / 2;
        spline(g, ax, ay, abx, aby, abcx, abcy, n - 1);
        spline(g, abcx, abcy, bcx, bcy, cx, cy, n - 1);
    }

    // -----------------------LoHi---------------------
    public static class LoHi {
    }

    // -----------------------BBox---------------------
    public static class BBox {
    }

    // -----------------------PL-----------------------
    public static class PL {
        public V[] points; // we keep an array of points

        public PL(int count) {
            points = new V[count]; // allocate the array
            for (int i = 0; i < count; i++) {
                points[i] = new V(0, 0);
            } // populate it with V objects
        }

        public int size() {
            return points.length;
        }

        public void drawN(Graphics g, int n) { // used to draw an initial portion of the full array
            for (int i = 1; i < n; i++) {
                g.drawLine(points[i - 1].x, points[i - 1].y, points[i].x, points[i].y);
            }
        }
      
        public void draw(Graphics g) {
            drawN(g, points.length);
        }
    }
}

package sandbox;

import graphics.G;
import graphics.WinApp;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

public class Paint extends WinApp {
    public Paint() {
        super("Paint", 1000, 700);
    }

    public static Path thePath = new Path();
    public static Pic thePic = new Pic();

    public static int clicks = 0; // we will total the mouse clicks

    @Override
    public void mousePressed(MouseEvent me) {
        clicks++; // bump up the click counter.
        thePath = new Path(); // create a new path
        thePic.add(thePath); // add the current path to the picture
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        thePath.add(me.getPoint());
        repaint(); // If you forgot this - you add points but do not SEE them! a bug!
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 9000, 9000);
        g.setColor(Color.RED);
        FontMetrics fm = g.getFontMetrics(); // local variable fm is information about the current font.
        // ascent is the distance from the baseline to the top of the font,
        // descent is the distance from the baseline to the bottom of the font.
        int a = fm.getAscent(), d = fm.getDescent();
        int x = 400, y = 200;
        String msg = "Clicks = " + clicks;
        int w = fm.stringWidth(msg);
        g.drawString(msg, x, y);
        // a+d is the total height of the font
        g.drawRect(x, y - a, w, a + d);
        thePic.draw(g);
    }

    public static class Pic extends ArrayList<Path> {
        public void draw(Graphics g) {
            for (Path p : this) {
                p.draw(g);
            }
        }
    }

    public static class Path extends ArrayList<Point> {
        public void draw(Graphics g) {
            for (int i = 1; i < size(); i++) {
                Point p = get(i - 1), n = get(i); // the previous and the next point
                g.drawLine(p.x, p.y, n.x, n.y);
            }
        }
    }

    public static void main(String[] args) {
        PANEL = new Paint();
        WinApp.launch();
    }
}

package sandbox;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Timer;

import graphics.G;
import graphics.WinApp;

public class Squares extends WinApp implements ActionListener {
    public static Timer timer;

    public Squares() {
        super("Squares", 1000, 800);
        timer = new Timer(30, this);
        timer.setInitialDelay(5000); // I give myself 5 seconds before the timer starts
        timer.start(); // start the timer
    }

    public static G.VS theVS = new G.VS(100, 100, 200, 300);
    public static Color color = G.rndColor();
    public static Square.List squares = new Square.List();
    public static Square lastSquare;
    private boolean dragging = false;
    private static G.V mouseDelta = new G.V(0, 0);
    public static boolean showSpline = true;
    public static G.V pressedLoc = new G.V(0, 0); // we will set this when the mouse is pressed

    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }

    // Square: a reactangle class with location and size
    public static class Square extends G.VS {
        public Color color = G.rndColor();
        public G.V dv = new G.V(G.rnd(20) - 10, G.rnd(20) - 10);

        public Square(int x, int y) {
            super(x, y, 100, 100);
        }

        public void moveAndBounce() {
            loc.add(dv);
            if (xL() < 0 && dv.x < 0) {
                dv.x = -dv.x;
            }
            if (xH() > 1000 && dv.x > 0) {
                dv.x = -dv.x;
            }
            if (yL() < 0 && dv.y < 0) {
                dv.y = -dv.y;
            }
            if (yH() > 800 && dv.y > 0) {
                dv.y = -dv.y;
            }
        }

        // draw and auto move by adding velocity
        public void draw(Graphics g) {
            fill(g, color);
            // add velocity to location with limits
            moveAndBounce();
        }

        public void resize(int x, int y) {
            if (x > loc.x && y > loc.y) {
                size.set(x - loc.x, y - loc.y);
            }
        }

        public void moveTo(int x, int y) {
            loc.set(x, y);
        }

        // a list of squares
        public static class List extends ArrayList<Square> {
            public void draw(Graphics g) {
                for (Square s : this) {
                    s.draw(g);
                }
            }

            public void addNew(int x, int y) {
                add(new Square(x, y));
            }

            // hit: return the square that was hit
            public Square hit(int x, int y) {
                Square res = null;
                for (Square s : this) {
                    if (s.hit(x, y)) {
                        res = s;
                    }
                }
                return res;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        G.fillBackground(g, Color.WHITE);
        squares.draw(g);
        if (showSpline && squares.size() > 2) {
            g.setColor(Color.BLACK);
            G.V a = squares.get(0).loc, b = squares.get(1).loc, c = squares.get(2).loc; // unpack coords
            G.spline(g, a.x, a.y, b.x, b.y, c.x, c.y, 4);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        int x = me.getX(), y = me.getY();
        lastSquare = squares.hit(x, y);
        if (lastSquare == null) {
            dragging = false;
            lastSquare = new Square(x, y);
            squares.add(lastSquare);
        } else {
            dragging = true;
            lastSquare.dv.set(0, 0); // Stop the clicked square
            pressedLoc.set(x, y); // Record the mouse loc
            mouseDelta.set(lastSquare.loc.x - x, lastSquare.loc.y - y);
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (dragging) {
            lastSquare.moveTo(me.getX() + mouseDelta.x, me.getY() + mouseDelta.y);
        } else {
            lastSquare.resize(me.getX(), me.getY());
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me){
      if(dragging){
        lastSquare.dv.set(me.getX() - pressedLoc.x, me.getY() - pressedLoc.y);
      }
    }

    public static void main(String[] args) {
        PANEL = new Squares();
        WinApp.launch();
    }

}

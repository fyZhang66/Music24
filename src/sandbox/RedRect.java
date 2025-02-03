package sandbox;

import graphics.G;
import graphics.WinApp;
import java.awt.Color;
import java.awt.Graphics;

public class RedRect extends WinApp {
    public RedRect() {
        super("Red Rect", 1000, 700);
    } // Win Title, Win width, Win height

    // when first created or resized, the paintComponent method is called
    @Override
    public void paintComponent(Graphics g) { 
        Color c = G.rndColor(); // get a random color
        g.setColor(c); // use the color red..
        g.fillRect(100, 100, 100, 100); // to fill in a rectangle
    }

    public static void main(String[] args) {
        PANEL = new RedRect(); // PANEL is where the paintComponent code lives
        WinApp.launch(); // fire up the WinApp thread the the OS manages
    }
}

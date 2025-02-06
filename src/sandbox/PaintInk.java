package sandbox;
import graphics.*;
import music.UC;
import reactions.Ink;

import java.awt.*;

import java.awt.event.MouseEvent;

public class PaintInk extends WinApp {
    public static Ink.List inkList = new Ink.List();
    private int lastInkSize = 0; // 记录最后一条 Ink 线条的大小
    static {
      // code run at the initialization time
  //    inkList.add(new Ink());
    };
    public PaintInk() {
      super("PaintInk", UC.mainWindowWidth, UC.mainWindowHeight);
    }
    public void paintComponent(Graphics g) {
      G.fillBackground(g);
      inkList.show(g);
      g.setColor(Color.RED);
      Ink.BUFFER.show(g);

      // 在左上角显示最后一条 Ink 线条的大小
      g.setColor(Color.BLACK);
      g.drawString("Last Ink Size: " + lastInkSize, 10, 20);
    }
  
    public void mousePressed(MouseEvent me) {Ink.BUFFER.dn(me.getX(), me.getY()); repaint();}
    public void mouseDragged(MouseEvent me) {Ink.BUFFER.drag(me.getX(), me.getY()); repaint();}
    public void mouseReleased(MouseEvent me) {
      Ink.BUFFER.up(me.getX(), me.getY());
      inkList.add(new Ink());
      lastInkSize = Ink.BUFFER.n; // 记录最后一条 Ink 线条的大小
      repaint();
      Ink.BUFFER.clear();
    }
  
    public static void main(String[] args) {
      PANEL = new PaintInk();
      WinApp.launch();
    }

}

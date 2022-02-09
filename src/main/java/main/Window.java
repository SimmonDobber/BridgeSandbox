package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window
{
    public static final double SCALE = 1.0;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 675;
    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy bs;
    private BufferedImage image;
    private Graphics2D g;
    private Renderer renderer;
    private Camera camera;
    private int width;
    private int height;

    public Window()
    {
        this.width = WIDTH;
        this.height = HEIGHT;
        Button.setScreenW(width);
        canvas = new Canvas();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        camera = new Camera();
        renderer = new Renderer(this);
        Dimension s = new Dimension((int)(width * SCALE), (int)(height * SCALE));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);
        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        canvas.createBufferStrategy(3);
        bs = canvas.getBufferStrategy();
        g = (Graphics2D)bs.getDrawGraphics();
    }
    public void render()
    {
        g.drawImage(image, 0, 0, width, height, null);
        bs.show();
        renderer.clear();
    }
    public Graphics getG() {
        return g;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Camera getCamera() {
        return camera;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public double getScale() {
        return SCALE;
    }

}

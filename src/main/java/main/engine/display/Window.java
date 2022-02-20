package main.engine.display;

import lombok.Getter;
import main.engine.MainLoop;
import main.engine.structures.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window
{
    private static Window WINDOW = null;

    public static Window getWindow() {
        if(WINDOW == null) {
            WINDOW = new Window();
        }
        return WINDOW;
    }
    public static final double SCALE = 1.0;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 675;
    @Getter
    private Canvas canvas;
    private BufferStrategy bs;
    @Getter
    private BufferedImage image;
    @Getter
    private Graphics2D g;
    @Getter
    private Renderer renderer;
    @Getter
    private Camera camera;
    @Getter
    private final int width;
    @Getter
    private final int height;

    private Window()
    {
        this.width = WIDTH;
        this.height = HEIGHT;
        initializeObjects();
    }

    private void initializeObjects()
    {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        camera = new Camera();
        renderer = new Renderer(this);
        canvas = initializeCanvas();
        initializeFrame();
        bs = canvas.getBufferStrategy();
        g = (Graphics2D)bs.getDrawGraphics();
    }

    private Canvas initializeCanvas()
    {
        Dimension s = new Dimension((int)(width * SCALE), (int)(height * SCALE));
        Canvas canvas = new Canvas();
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);
        canvas.createBufferStrategy(1);
        return canvas;
    }

    private void initializeFrame()
    {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void render()
    {
        g.drawImage(image, 0, 0, width, height, null);
        bs.show();
        renderer.clear();
    }
}

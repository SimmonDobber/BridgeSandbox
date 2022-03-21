package main.engine.display;

import lombok.Getter;
import lombok.Setter;
import main.engine.display.renderer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window
{
    private static Window WINDOW = null;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 675;
    private BufferStrategy bs;
    @Getter private Canvas canvas;
    @Getter private BufferedImage image;
    @Getter private Graphics2D g;
    @Getter @Setter private Renderer renderer;
    @Getter private Camera camera;

    private Window() {
        initializeWindowObjects();
    }

    private void initializeWindowObjects() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        camera = new Camera();
        canvas = initializeCanvas();
        initializeFrame();
        bs = canvas.getBufferStrategy();
        g = (Graphics2D)bs.getDrawGraphics();
    }

    private Canvas initializeCanvas() {

        Canvas canvas = new Canvas();
        initializeCanvasSize(canvas);
        canvas.createBufferStrategy(1);
        return canvas;
    }

    private void initializeCanvasSize(Canvas canvas)
    {
        Dimension s = new Dimension(WIDTH, HEIGHT);
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);
    }

    private void initializeFrame() {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void render() {
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        bs.show();
    }

    public static Window getWindow()
    {
        if(WINDOW == null)
            WINDOW = new Window();
        return WINDOW;
    }
}

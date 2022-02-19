package main.engine.structures.drawable;

import lombok.Getter;
import lombok.Setter;
import main.engine.display.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Getter
public class Image implements Drawable
{
    private int x;
    private int y;
    private int w;
    private int h;
    private int fixed;
    private int[] p;

    public Image(String path, int x, int y, int w, int h, int fixed)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.fixed = fixed;
        BufferedImage image = loadImage(path);
        p = image.getRGB(0, 0, w, h, null, 0, w);
        rescale(w, h);
        image.flush();
    }

    public Image(int x, int y, int w, int h, int fixed)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.fixed = fixed;
        p = new int[w * h];
    }

    @Override
    public void render(Renderer r, int x, int y, int id)
    {
        r.drawImage(this, getAbsoluteX(x), getAbsoluteY(y), id);
    }

    private BufferedImage loadImage(String path)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(Image.class.getResourceAsStream((path))));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void rescale(int newWidth, int newHeight)
    {
        int[] r = Arrays.copyOf(p, p.length);
        p = new int[newWidth * newHeight];
        double widthScale = ((double)(newWidth) / (double)(w));
        double heightScale = ((double)(newHeight) / (double)(h));
        rescaleByPixels(r, newWidth, newHeight, widthScale, heightScale);
        w = newWidth;
        h = newHeight;
    }

    public void rotate(double angle)
    {
        angle = normalizeAngle(angle);
        while(angle >= Math.PI / 2)
        {
            int[] r = Arrays.copyOf(p, p.length);
            p = new int[w * h];
            imageTransposition(r);
            dimensionSwap();
            imageMirror();
            angle -= Math.PI / 2;
        }
    }

    private void rescaleByPixels(int[] r, int newWidth, int newHeight, double widthScale, double heightScale)
    {
        for(int j = 0; j < newHeight; j++)
        {
            for(int i = 0; i < newWidth; i++)
            {
                p[i + j * newWidth] = r[(int)((double)(i) / (widthScale)) + (int)((double)(j) / (heightScale)) * w];
            }
        }
    }

    private void imageTransposition(int[] r)
    {
        for(int i = 0; i < w; i++)
        {
            for(int j = 0; j < h; j++)
            {
                p[j + i * h] = r[i + j * w];
            }
        }
    }
    private void imageMirror()
    {
        for(int i = 0; i < w; i++)
        {
            for (int j = 0; j < h / 2; j++)
            {   
                pixelSwap(i, j);
            }
        }
    }
    private void dimensionSwap()
    {
        w = w + h;
        h = w - h;
        w = w - h;
    }
    private void pixelSwap(int i, int j)
    {
        p[i + j * w]  = p[i + j * w] + p[i + (h - j - 1) * w];
        p[i + (h - j - 1) * w] = p[i + j * w] - p[i + (h - j - 1) * w];
        p[i + j * w] = p[i + j * w] - p[i + (h - j - 1) * w];
    }
    private double normalizeAngle(double angle)
    {
        return Math.abs(angle) - 2 * Math.PI * Math.floor(Math.abs(angle) / (2 * Math.PI)) + (angle < 0 ? 2 * Math.PI : 0);
    }

    private int getAbsoluteX(int x)
    {
        return this.x + x;
    }
    private int getAbsoluteY(int y)
    {
        return this.y + y;
    }
}

package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image
{
    private int w, h;
    private int fixed;
    private int[] p;
    public Image(String path, int width, int height, int fixed)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(Image.class.getResourceAsStream((path)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        w = image.getWidth();
        h = image.getHeight();
        this.fixed = fixed;
        p = image.getRGB(0, 0, w, h, null, 0, w);
        rescale(width, height);
        image.flush();
    }
    public Image(int width, int height, int fixed)
    {
        w = width;
        h = height;
        this.fixed = fixed;
        p = new int[width * height];
    }

    public void rescale(int nWidth, int nHeight)
    {
        int[] r = new int[p.length];
        for(int i = 0; i < p.length; i++)
        {
            r[i] = p[i];
        }
        p = new int[nWidth * nHeight];
        double sw = ((double)(nWidth) / (double)(w));
        double sh = ((double)(nHeight) / (double)(h));
        for(int j = 0; j < nHeight; j++)
        {
            for(int i = 0; i < nWidth; i++)
            {
                p[i + j * nWidth] = r[(int)((double)(i) / (sw)) + (int)((double)(j) / (sh)) * w];
            }
        }
        w = nWidth;
        h = nHeight;
    }
    public void rotate(double angle)
    {
        while(angle < 0)
            angle += 2 * Math.PI;
        while(angle != 0)
        {
            int[] r = new int[p.length];
            for(int i = 0; i < p.length; i++)
            {
                r[i] = p[i];
            }
            p = new int[w * h];
            for(int i = 0; i < w; i++)
            {
                for(int j = 0; j < h; j++)
                {
                    p[j + i * h] = r[i + j * w];
                }
            }
            int x = w;
            w = h;
            h = x;
            for(int i = 0; i < w; i++)
            {
                for (int j = 0; j < h / 2; j++)
                {
                    x = p[i + j * w];
                    p[i + j * w] = p[i + (h - j - 1) * w];
                    p[i + (h - j - 1) * w] = x;
                }
            }
            angle -= Math.PI / 2;
        }
    }


    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int[] getP() {
        return p;
    }

    public int getFixed() {
        return fixed;
    }

    public void setFixed(int fixed) {
        this.fixed = fixed;
    }
}

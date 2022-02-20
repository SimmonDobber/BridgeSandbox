package main.engine.structures.drawable;

import lombok.Getter;
import lombok.Setter;
import main.engine.display.Renderer;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PipedInputStream;
import java.util.Arrays;
import java.util.Objects;

@Getter
public class Image implements Drawable
{
    private Position pos;
    private Dimensions dim;
    private final int fixed;
    private int[] p;

    public Image(String path, Position pos, Dimensions dim, int fixed)
    {
        this.pos = pos;
        this.dim = dim;
        this.fixed = fixed;
        BufferedImage image = loadImage(path);
        p = image.getRGB(0, 0, dim.getW(), dim.getH(), null, 0, dim.getW());
        rescale(dim.getW(), dim.getH());
        image.flush();
    }

    public Image(Position pos, Dimensions dim, int fixed)
    {
        this.pos = pos;
        this.dim = dim;
        this.fixed = fixed;
        p = new int[dim.getW() * dim.getH()];
    }

    @Override
    public void render(Renderer r, Position pos, int id)
    {
        r.drawImage(this, getAbsolutePos(pos), id);
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
        double widthScale = ((double)(newWidth) / (double)(dim.getW()));
        double heightScale = ((double)(newHeight) / (double)(dim.getH()));
        rescaleByPixels(r, newWidth, newHeight, widthScale, heightScale);
        dim.setW(newWidth);
        dim.setH(newHeight);
    }

    public void rotate(double angle)
    {
        angle = normalizeAngle(angle);
        while(angle >= Math.PI / 2)
        {
            int[] r = Arrays.copyOf(p, p.length);
            p = new int[dim.getW() * dim.getH()];
            imageRotation(r);
            angle -= Math.PI / 2;
        }
    }

    private void rescaleByPixels(int[] r, int newWidth, int newHeight, double widthScale, double heightScale)
    {
        for(int j = 0; j < newHeight; j++)
        {
            for(int i = 0; i < newWidth; i++)
            {
                p[i + j * newWidth] = r[(int)((double)(i) / (widthScale)) + (int)((double)(j) / (heightScale)) * dim.getW()];
            }
        }
    }

    private void imageRotation(int[] r)
    {
        for(int i = 0; i < dim.getW(); i++)
        {
            for(int j = 0; j < dim.getH(); j++)
            {
                p[(dim.getH() - j - 1) + i * dim.getH()] = r[i + j * dim.getW()];
            }
        }
    }
    private double normalizeAngle(double angle)
    {
        return Math.abs(angle) - 2 * Math.PI * Math.floor(Math.abs(angle) / (2 * Math.PI)) + (angle < 0 ? 2 * Math.PI : 0);
    }

    private Position getAbsolutePos(Position pos)
    {
        return new Position(this.pos.getX() + pos.getX(), this.pos.getY() + pos.getY());
    }
}

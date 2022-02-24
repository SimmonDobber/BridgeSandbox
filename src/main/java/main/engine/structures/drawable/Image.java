package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.renderer.Renderer;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Getter
public class Image implements Drawable
{
    protected Position pos;
    protected Dimensions dim;
    protected final int fixed;
    protected int[] p;

    public Image(Image image)
    {
        this.pos = new Position(image.getPos());
        this.dim = new Dimensions(image.getDim());
        this.fixed = image.getFixed();
        this.p = Arrays.copyOf(image.getP(), image.getP().length);
    }

    public Image(String path, Position pos, Dimensions dim, int fixed)
    {
        this.pos = pos;
        this.dim = dim;
        this.fixed = fixed;
        BufferedImage image = loadImage(path);
        p = image.getRGB(0, 0, dim.getW(), dim.getH(), null, 0, dim.getW());
        rescale(dim);
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

    public void rescale(Dimensions newDimensions)
    {
        int[] r = Arrays.copyOf(p, p.length);
        p = new int[newDimensions.getW() * newDimensions.getH()];
        double widthScale = ((double)(newDimensions.getW()) / (double)(dim.getW()));
        double heightScale = ((double)(newDimensions.getH()) / (double)(dim.getH()));
        p = rescaleByPixels(r, newDimensions, widthScale, heightScale);
        dim.setW(newDimensions.getW());
        dim.setH(newDimensions.getH());
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

    private int[] rescaleByPixels(int[] r, Dimensions newDimendions, double widthScale, double heightScale) {
        int[] pixelArray = new int[newDimendions.getW() * newDimendions.getH()];
        for(int j = 0; j < newDimendions.getH(); j++) {
            for(int i = 0; i < newDimendions.getW(); i++) {
                int rescaledId = (int)((double)(i) / (widthScale)) + (int)((double)(j) / (heightScale)) * dim.getW();
                pixelArray[i + j * newDimendions.getW()] = r[rescaledId];
            }
        }
        return pixelArray;
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

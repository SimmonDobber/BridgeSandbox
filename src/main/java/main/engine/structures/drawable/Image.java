package main.engine.structures.drawable;

import lombok.Getter;
import main.engine.display.renderer.Renderer;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;
import main.engine.structures.gameObject.Scale;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Getter
public class Image extends DrawableEntity
{
    private static double TAU = Math.PI * 2;
    protected Dimensions dim;
    protected int[] p;

    public Image(Image image) {
        super(image.getPos(), image.getFixed());
        this.dim = new Dimensions(image.getDim());
        this.p = Arrays.copyOf(image.getP(), image.getP().length);
    }

    public Image(String path, Position pos, Dimensions dim, int fixed) {
        super(pos, fixed);
        this.dim = dim;
        loadImageFromFile(path);
    }

    public Image(Position pos, Dimensions dim, int fixed) {
        super(pos, fixed);
        this.dim = dim;
        p = new int[dim.w * dim.h];
    }

    @Override
    public void render(Renderer r, Position pos, int id) {
        r.drawImage(this, getAbsolutePos(pos), id);
    }

    public void rescale(Dimensions newDimensions) {
        p = getRescaledPixelArray(newDimensions);
        dim = new Dimensions(newDimensions);
    }

    public void rotate(double angle) {
        angle = normalizeAngle(angle);
        while(angle >= Math.PI / 2) {
            p = pixelByPixelRotation();
            angle -= Math.PI / 2;
        }
    }

    private void loadImageFromFile(String path) {
        BufferedImage image = loadBufferedImageFromFile(path);
        p = image.getRGB(0, 0, dim.getW(), dim.getH(), null, 0, dim.getW());
        rescale(dim);
        image.flush();
    }

    private BufferedImage loadBufferedImageFromFile(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(Image.class.getResourceAsStream((path))));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private int[] pixelByPixelRotation() {
        int[] pixelArray = new int[dim.getSize()];
        for(int x = 0; x < dim.w; x++) {
            for(int y = 0; y < dim.h; y++) {
                rotatePixel(pixelArray, x, y);
            }
        }
        return pixelArray;
    }

    private void rotatePixel(int[] pixelArray, int x, int y) {
        int sourcePixelPositionId = new Position(x, y).getPositionId(dim.getW());
        int destinationPixelPositionId = new Position(dim.h - y - 1, x).getPositionId(dim.h);
        pixelArray[destinationPixelPositionId] = p[sourcePixelPositionId];
    }

    private int[] getRescaledPixelArray(Dimensions newDimensions) {
        Scale dimensionsScale = getDimensionsScale(newDimensions);
        return pixelByPixelRescale(newDimensions, dimensionsScale);
    }

    private int[] pixelByPixelRescale(Dimensions newDimensions, Scale dimensionsScale) {
        int[] pixelArray = new int[newDimensions.getSize()];
        for(int y = 0; y < newDimensions.h; y++) {
            for(int x = 0; x < newDimensions.w; x++) {
                rescalePixel(pixelArray, new Position(x, y), newDimensions, dimensionsScale);
            }
        }
        return pixelArray;
    }

    private void rescalePixel(int[] pixelArray, Position pixelPos, Dimensions newDimensions, Scale dimensionsScale) {
        Position sourcePosition = pixelPos.getRescaledPosition(dimensionsScale);
        pixelArray[pixelPos.getPositionId(newDimensions.w)] = p[sourcePosition.getPositionId(dim.w)];
    }

    private double normalizeAngle(double angle) {
        return Math.abs(angle) - TAU * Math.floor(Math.abs(angle) / TAU) + makeAnglePositive(angle);
    }

    private double makeAnglePositive(double angle) {
        return angle < 0 ? TAU : 0;
    }

    private Scale getDimensionsScale(Dimensions newDimensions) {
        double xScale = ((double)(dim.w) / (double)(newDimensions.w));
        double yScale = ((double)(dim.h) / (double)(newDimensions.h));
        return new Scale(xScale, yScale);
    }

    public void setP(int id, int value) {
        p[id] = value;
    }
}

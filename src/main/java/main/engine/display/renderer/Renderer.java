package main.engine.display.renderer;

import lombok.Getter;
import main.engine.ProgramContainer;
import main.engine.display.Camera;
import main.engine.display.Window;
import main.engine.display.font.Font;
import main.engine.structures.drawable.Image;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

import java.awt.image.DataBufferInt;

public class Renderer
{
    private static Renderer RENDERER = null;
    private TextRenderer textRenderer;
    private Dimensions dim;
    private Camera camera;
    private Font font;
    private int[] p;
    @Getter
    private int[] pOwner;

    private Renderer(Window window) {
        dim = new Dimensions(Window.WIDTH, Window.HEIGHT);
        font = new Font("/sansBoldplus.png", new Dimensions(2410, 40));
        camera = window.getCamera();
        textRenderer = new TextRenderer(this, font);
        initializePixelMatrices(window);
    }

    public void drawRectangle(Position rectanglePos, Dimensions rectangleDim, int color, int fixed, int owner) {
        Position startPos = getDrawRectangleStartingPosition(rectanglePos, fixed);
        Position endPos = getDrawRectangleEndPosition(rectanglePos, rectangleDim, fixed);
        for(int x = startPos.getX(); x < endPos.getX(); x++) {
            for(int y = startPos.getY(); y < endPos.getY(); y++) {
                setPixel(getRendererPositionId(new Position(x, y)), color, owner);
            }
        }
    }

    public void drawImage(Image image, Position imagePos, int owner) {
        Dimensions croppedDim = getDrawImageCroppedDimensions(image, imagePos);
        drawImagePixels(image, imagePos, croppedDim, owner);
    }

    public void drawText(Text text, Position textPos, int owner) {
        textRenderer.drawText(text, textPos, owner);
    }

    void drawSymbol(int unicodeId, Text text, Position symbolPos, int owner) {
        for(int y = 0; y < font.getSymbols()[text.getSize() / 2][unicodeId].getDim().getH(); y++) {
            for(int x = 0; x < font.getSymbols()[text.getSize() / 2][unicodeId].getDim().getW(); x++) {
                drawSymbolPixel(unicodeId, text, symbolPos, new Position(x, y), owner);
            }
        }
    }

    private void drawSymbolPixel(int unicodeId, Text text, Position symbolPos, Position pixelPosRelativeToSymbol, int owner)
    {
        if(isPixelInSymbol(unicodeId, text.getSize(), pixelPosRelativeToSymbol)) {
            Position absolutePixelPosition = getDrawSymbolAbsolutePixelPosition(text, symbolPos, pixelPosRelativeToSymbol);
            setPixel(getRendererPositionId(absolutePixelPosition), text.getColor(), owner);
        }
    }

    private int alphaCompose(int color2, int color1) {
        float alpha = ((color1 & 0xFF000000) >>> 24) * (1.0f / 255.0f);
        int red = (int)((((color1 & 0x00FF0000) >>> 16) * alpha) + (((color2 & 0x00FF0000) >>> 16) * (1.0 - alpha)));
        int green = (int)((((color1 & 0x0000FF00) >>> 8) * alpha) + (((color2 & 0x0000FF00) >>> 8) * (1.0 - alpha)));
        int blue = (int)(((color1 & 0x000000FF) * alpha) + ((color2 & 0x000000FF) * (1.0 - alpha)));
        return 255 << 24 | red << 16 | green << 8 | blue;
    }

    private void drawImagePixels(Image image, Position startingPos, Dimensions dim, int owner) {
        Position endingPos = getImageDrawEndingPos(startingPos, dim);
        for(int y = startingPos.getY(); y < endingPos.getY(); y++) {
            for(int x = startingPos.getX(); x < endingPos.getX(); x++) {
                int pixelValue = getImagePixelValue(image, startingPos, new Position(x, y));
                setPixel(getRendererPositionId(new Position(x, y)), pixelValue, owner);
            }
        }
    }

    private void initializePixelMatrices(Window window) {
        p = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
        pOwner = new int[Window.WIDTH * Window.HEIGHT];
    }

    private boolean isNotTransparent(int id, int value) {
        return (value >>> 24 == 0xFF);
    }

    private boolean isPixelOutOfScreen(int id) {
        return id < 0 || id >= dim.getSize();
    }

    private boolean isPixelInSymbol(int unicodeId, int size, Position pos) {
        Image letter = font.getSymbols()[size / 2][unicodeId];
        return letter.getP()[pos.getX() + pos.getY() * letter.getDim().getW()] == 0xFF000000;
    }

    private Position getDrawRectangleStartingPosition(Position rectanglePos, int fixed)
    {
        int startingX = Math.max(rectanglePos.getX(), 0) + camera.offsetX * (1 - fixed);
        int startingY = Math.max(rectanglePos.getY(), 0) + camera.offsetY * (1 - fixed);
        return new Position(startingX, startingY);
    }

    private Position getDrawRectangleEndPosition(Position rectanglePos, Dimensions rectangleDim, int fixed)
    {
        int endingX = Math.min(rectangleDim.getW() + rectanglePos.getX(), dim.getW()) + camera.offsetX * (1 - fixed);
        int endingY = Math.min(rectangleDim.getH() + rectanglePos.getY(), dim.getH()) + camera.offsetY * (1 - fixed);
        return new Position(endingX, endingY);
    }

    private Dimensions getDrawImageCroppedDimensions(Image image, Position pos)
    {
        int croppedWidth = Math.min(image.getDim().getW(), dim.getW() - pos.getX() - camera.offsetX * (1 - image.getFixed()));
        int croppedHeight = Math.min(image.getDim().getH(), dim.getH() - pos.getY() - camera.offsetY * (1 - image.getFixed()));
        return new Dimensions(Math.max(croppedWidth, 0), Math.max(croppedHeight, 0));
    }

    private int getImagePixelValue(Image image, Position imagePos, Position pixelPosInRelationToImage)
    {
        int absolutePixelPosX = pixelPosInRelationToImage.getX() - imagePos.getX();
        int absolutePixelPosY = pixelPosInRelationToImage.getY() - imagePos.getY();
        int absolutePixelId = absolutePixelPosX + absolutePixelPosY * image.getDim().getW();
        return image.getP()[absolutePixelId];
    }

    private Position getImageDrawEndingPos(Position startingPos, Dimensions dim)
    {
        int endingX = startingPos.getX() + dim.getW();
        int endingY = startingPos.getY() + dim.getH();
        return new Position(endingX, endingY);
    }

    private Position getDrawSymbolAbsolutePixelPosition(Text text, Position symbolPos, Position pixelPosRelativeToSymbol)
    {
        int absoluteXPos = pixelPosRelativeToSymbol.getX() + symbolPos.getX() + camera.offsetX * (1 - text.getFixed());
        int absoluteYPos = pixelPosRelativeToSymbol.getY() + symbolPos.getY() + camera.offsetY * (1 - text.getFixed());
        return new Position(absoluteXPos, absoluteYPos);
    }

    private int getRendererPositionId(Position pos) {
        return pos.getX() + pos.getY() * dim.getW();
    }

    private void setPixel(int pixelId, int value, int owner) {
        if(!isPixelOutOfScreen(pixelId)) {
            setPixelOwner(pixelId, owner);
            setPixelColor(pixelId, value);
        }
    }

    private void setPixelOwner(int pixelId, int owner) {
        pOwner[pixelId] = owner;
    }

    private void setPixelColor(int id, int value) {
        p[id] = (isNotTransparent(id, value) ? value : alphaCompose(p[id], value));
    }

    public static Renderer getRenderer() {
        if(RENDERER == null)
            RENDERER = new Renderer(Window.getWindow());
        return RENDERER;
    }
}

package main.engine.display.renderer;

import lombok.Getter;
import main.engine.display.Camera;
import main.engine.display.Window;
import main.engine.display.font.Font;
import main.engine.structures.drawable.Image;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

public class Renderer
{
    private static Renderer RENDERER = null;
    private final TextRenderManager textRenderer;
    private final Dimensions dim;
    private final Camera camera;
    private final Font font;
    private int[] p;
    @Getter
    private int[] pOwner;

    private Renderer(Window window) {
        dim = new Dimensions(Window.WIDTH, Window.HEIGHT);
        font = new Font("/sansBoldplus.png", new Dimensions(2410, 40));
        camera = window.getCamera();
        textRenderer = new TextRenderManager(this, font);
        initializePixelMatrices(window);
    }

    public static Renderer getRenderer() {
        if(RENDERER == null)
            RENDERER = new Renderer(Window.getWindow());
        return RENDERER;
    }

    public void drawRectangle(Position rectanglePos, Dimensions rectangleDim, int color, int fixed, int owner) {
        Position startPos = getDrawRectangleStartingPosition(rectanglePos, fixed);
        Position endPos = getDrawRectangleEndPosition(rectanglePos, rectangleDim, fixed);
        for(int x = startPos.x; x < endPos.x; x++) {
            for(int y = startPos.y; y < endPos.y; y++) {
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
        for(int y = 0; y < font.getSymbols()[text.getSize() / 2][unicodeId].getDim().h; y++) {
            for(int x = 0; x < font.getSymbols()[text.getSize() / 2][unicodeId].getDim().w; x++) {
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
        for(int y = startingPos.y; y < endingPos.y; y++) {
            for(int x = startingPos.x; x < endingPos.x; x++) {
                int pixelValue = getImagePixelValue(image, startingPos, new Position(x, y));
                setPixel(getRendererPositionId(new Position(x, y)), pixelValue, owner);
            }
        }
    }

    private void initializePixelMatrices(Window window) {
        p = bufferedImageToPixelArray(window);
        pOwner = new int[Window.WIDTH * Window.HEIGHT];
    }

    private int[] bufferedImageToPixelArray(Window window)
    {
        BufferedImage windowImage = window.getImage();
        WritableRaster windowRaster = windowImage.getRaster();
        DataBufferInt windowImageDataBuffer = (DataBufferInt)(windowRaster.getDataBuffer());
        return windowImageDataBuffer.getData();
    }

    private boolean isNotTransparent(int id, int value) {
        return (value >>> 24 == 0xFF);
    }

    private boolean isPixelOutOfScreen(int id) {
        return id < 0 || id >= dim.getSize();
    }

    private boolean isPixelInSymbol(int unicodeId, int size, Position pos) {
        Image letter = font.getSymbols()[size / 2][unicodeId];
        return letter.getP()[pos.getPositionId(letter.getDim().w)] == 0xFF000000;
    }

    private Position getDrawRectangleStartingPosition(Position rectanglePos, int fixed)
    {
        int startingX = Math.max(rectanglePos.x, 0) + camera.offsetX * (1 - fixed);
        int startingY = Math.max(rectanglePos.y, 0) + camera.offsetY * (1 - fixed);
        return new Position(startingX, startingY);
    }

    private Position getDrawRectangleEndPosition(Position rectanglePos, Dimensions rectangleDim, int fixed)
    {
        int endingX = Math.min(rectangleDim.w + rectanglePos.getX(), dim.w) + camera.offsetX * (1 - fixed);
        int endingY = Math.min(rectangleDim.h + rectanglePos.getY(), dim.h) + camera.offsetY * (1 - fixed);
        return new Position(endingX, endingY);
    }

    private Dimensions getDrawImageCroppedDimensions(Image image, Position pos)
    {
        int croppedWidth = Math.min(image.getDim().w, dim.w - pos.x - camera.offsetX * (1 - image.getFixed()));
        int croppedHeight = Math.min(image.getDim().h, dim.h - pos.y - camera.offsetY * (1 - image.getFixed()));
        return new Dimensions(Math.max(croppedWidth, 0), Math.max(croppedHeight, 0));
    }

    private int getImagePixelValue(Image image, Position imagePos, Position pixelPosInRelationToImage)
    {
        int absolutePixelPosX = pixelPosInRelationToImage.x - imagePos.x;
        int absolutePixelPosY = pixelPosInRelationToImage.y - imagePos.y;
        int absolutePixelId = Position.getPositionId(absolutePixelPosX, absolutePixelPosY, image.getDim().w);
        return image.getP()[absolutePixelId];
    }

    private Position getImageDrawEndingPos(Position startingPos, Dimensions dim)
    {
        int endingX = startingPos.x + dim.w;
        int endingY = startingPos.y + dim.h;
        return new Position(endingX, endingY);
    }

    private Position getDrawSymbolAbsolutePixelPosition(Text text, Position symbolPos, Position pixelPosRelativeToSymbol)
    {
        int absoluteXPos = pixelPosRelativeToSymbol.x + symbolPos.x + camera.offsetX * (1 - text.getFixed());
        int absoluteYPos = pixelPosRelativeToSymbol.y + symbolPos.y + camera.offsetY * (1 - text.getFixed());
        return new Position(absoluteXPos, absoluteYPos);
    }

    private int getRendererPositionId(Position pos) {
        return pos.getPositionId(dim.w);
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
}

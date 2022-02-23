package main.engine.display;

import lombok.Getter;
import main.engine.display.font.Font;
import main.engine.structures.drawable.Image;
import main.engine.structures.drawable.Text;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

import java.awt.image.DataBufferInt;

public class Renderer
{
    private static Renderer RENDERER = null;

    public static Renderer getRenderer()
    {
        if(RENDERER == null)
            RENDERER = new Renderer(Window.getWindow());
        return RENDERER;
    }

    public static final int DEFAULT_COLOR = 0xFF000001;
    public static final int UNICODE_OFFSET = 32;
    private final int screenW;
    private final int screenH;
    private final  Camera camera;
    private final Font font;
    private final int[] p;
    @Getter
    private final int[] pOwner;

    private Renderer(Window window)
    {
        screenW = (int)(window.getWidth() * Window.SCALE);
        screenH = (int)(window.getHeight() * Window.SCALE);
        p = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
        pOwner = new int[window.getWidth() * window.getHeight()];
        font = new Font("/sansBoldplus.png", new Dimensions(2410, 40));
        camera = window.getCamera();
    }

    private int alphaCompose(int color2, int color1)
    {
        float alpha = ((color1 & 0xFF000000) >>> 24) * (1.0f / 255.0f);
        int red = (int)((((color1 & 0x00FF0000) >>> 16) * alpha) + (((color2 & 0x00FF0000) >>> 16) * (1.0 - alpha)));
        int green = (int)((((color1 & 0x0000FF00) >>> 8) * alpha) + (((color2 & 0x0000FF00) >>> 8) * (1.0 - alpha)));
        int blue = (int)(((color1 & 0x000000FF) * alpha) + ((color2 & 0x000000FF) * (1.0 - alpha)));
        return 255 << 24 | red << 16 | green << 8 | blue;
    }

    private void drawPixel(int x,int y, int value, int owner)
    {
        int id = x + y * screenW;
        if(isPixelOutOfScreen(id))
            return;
        pOwner[id] = owner;
        p[id] = (isNotTransparent(id, value) ? value : alphaCompose(p[id], value));
    }

    private boolean isNotTransparent(int id, int value)
    {
        return (value >>> 24 == 0xFF) | (p[id] == DEFAULT_COLOR);
    }

    private boolean isPixelOutOfScreen(int id)
    {
        return id < 0 || id >= screenW * screenH;
    }

    public void drawRectangle(Position pos, Dimensions dim, int color, int fixed, int owner)
    {
        if(fixed == 0)
            drawNonFixedRectangle(pos, dim, color, owner);
        else
            drawFixedRectangle(pos, dim, color, owner);

    }

    private void drawNonFixedRectangle(Position pos, Dimensions dim, int color, int owner)
    {
        for(int i = Math.max(pos.getX(), 0) + camera.offsetX; i < Math.min(dim.getW() + pos.getX(), screenW) + camera.offsetX; i++)
        {
            for(int j = Math.max(pos.getY(), 0) + camera.offsetY; j < Math.min(dim.getH() + pos.getY(), screenH) + camera.offsetY; j++)
            {
                drawPixel(i, j, color, owner);
            }
        }
    }

    private void drawFixedRectangle(Position pos, Dimensions dim, int color, int owner)
    {
        for(int i = Math.max(pos.getX(), 0); i < Math.min(pos.getX() + dim.getW(), screenW); i++)
        {
            for(int j = Math.max(pos.getY(), 0); j < Math.min(pos.getY() + dim.getH(), screenH); j++)
            {
                drawPixel(i, j, color, owner);
            }
        }
    }

    public void drawImage(Image image, Position pos, int owner)
    {
        int newW = Math.max(0, Math.min(image.getDim().getW(), screenW - pos.getX() - camera.offsetX * (1 - image.getFixed())));
        int newH = Math.max(0, Math.min(image.getDim().getH(), screenH - pos.getY() - camera.offsetY * (1 - image.getFixed())));
        drawImagePixels(image, pos, new Dimensions(newW, newH), owner);
    }

    private void drawImagePixels(Image image, Position pos, Dimensions dim, int owner)
    {
        for(int j = pos.getY(); j < pos.getY() + dim.getH(); j++)
        {
            for(int i = pos.getX(); i < pos.getX() + dim.getW(); i++)
            {
                drawPixel(i, j, image.getP()[(i - pos.getX()) + (j - pos.getY()) * image.getDim().getW()], owner);
            }
        }
    }

    public void drawText(Text text, Position textOffset, int owner)
    {
        Position symbolOffset = new Position();
        for(int i = 0; i < text.getText().length(); i++)
        {
            int unicodeId = text.getText().codePointAt(i) - UNICODE_OFFSET;
            if(text.getText().codePointAt(i) == '\n')
            {
                int newLineYPos = symbolOffset.getY() + font.getSymbols()[text.getSize() / 2][0].getDim().getH();
                symbolOffset = new Position(0, newLineYPos);
                continue;
            }
            int absoluteXOffset = symbolOffset.getX() + textOffset.getX();
            int absoluteYOffset = symbolOffset.getY() + textOffset.getY();
            drawSymbol(unicodeId, text, new Position(absoluteXOffset, absoluteYOffset), owner);
            symbolOffset.incX(font.getSymbols()[text.getSize() / 2][unicodeId].getDim().getW());
        }
    }
    private void drawSymbol(int unicodeId, Text text, Position offset, int owner)
    {
        for(int y = 0; y < font.getSymbols()[text.getSize() / 2][unicodeId].getDim().getH(); y++)
        {
            for(int x = 0; x < font.getSymbols()[text.getSize() / 2][unicodeId].getDim().getW(); x++)
            {
                if(isPixelInSymbol(unicodeId, text.getSize(), new Position(x, y)))
                {
                    int absoluteX = x + offset.getX() + camera.offsetX * (1 - text.getFixed());
                    int absoluteY = y + offset.getY() + camera.offsetY * (1 - text.getFixed());
                    drawPixel(absoluteX, absoluteY, text.getColor(), owner);
                }
            }
        }
    }
    private boolean isPixelInSymbol(int unicodeId, int size, Position pos)
    {
        Image letter = font.getSymbols()[size / 2][unicodeId];
        return letter.getP()[pos.getX() + pos.getY() * letter.getDim().getW()] == 0xFF000000;
    }
}

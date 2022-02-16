package main.engine.display;

import lombok.Getter;

import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Renderer
{
    public static final int TRANSPARENT_COLOR = 0xFFFF00FF;
    public static final int DEFAULT_COLOR = 0xFF000000;
    public static final int UNICODE_OFFSET = 32;
    private int screenW;
    private int screenH;
    private Camera camera;
    private Font font;
    private int[] p;
    @Getter
    private int[] pOwner;

    public Renderer(Window window)
    {
        screenW = (int)(window.getWidth() * Window.SCALE);
        screenH = (int)(window.getHeight() * Window.SCALE);
        p = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
        pOwner = new int[window.getWidth() * window.getHeight()];
        font = new Font("/sansBoldplus.png");
        camera = window.getCamera();
    }

    public void clear()
    {
        Arrays.fill(p, 0);
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
        if(isPixelOutOfScreen(x, y))
            return;
        pOwner[id] = owner;
        p[id] = (isNotTransparent(id, value) ? value : alphaCompose(p[id], value));
    }

    private boolean isNotTransparent(int id, int value)
    {
        return (value >>> 24 == 0xFF) | (p[id] == DEFAULT_COLOR);
    }

    private boolean isPixelOutOfScreen(int x, int y)
    {
        return x < 0 & y < 0 & x >= screenW & y >= screenH;
    }

    public void drawRectangle(int x, int y, int w, int h, int value, int fixed)
    {
        drawRectangle(x, y, w, h, value, fixed, -1);
    }

    public void drawRectangle(int x, int y, int w, int h, int value, int fixed, int owner)
    {
        if(fixed == 0)
            drawNonFixedRectangle(x, y, w, h, value, owner);
        else
            drawFixedRectangle(x, y, w, h, value, owner);

    }

    private void drawNonFixedRectangle(int x, int y, int w, int h, int value, int owner)
    {
        for(int i = Math.max(x, 0) + camera.offX; i < Math.min(w + x, screenW) + camera.offX; i++)
        {
            for(int j = Math.max(y, 0) + camera.offY; j < Math.min(h + y, screenH) + camera.offY; j++)
            {
                drawPixel(i, j, value, owner);
            }
        }
    }

    private void drawFixedRectangle(int x, int y, int w, int h, int value, int owner)
    {
        for(int i = Math.max(x, 0); i < Math.min(w + x, screenW); i++)
        {
            for(int j = Math.max(y, 0); j < Math.min(h + y, screenH); j++)
            {
                drawPixel(i, j, value, owner);
            }
        }
    }

    public void drawImage(Image image, int x, int y, int fixed)
    {
        drawImage(image, x, y, fixed, -1);
    }

    public void drawImage(Image image, int x, int y, int fixed, int owner)
    {
        int newW = Math.max(0, Math.min(image.getW(), screenW - x - camera.offX * (1 - fixed)));
        int newH = Math.max(0, Math.min(image.getH(), screenH - y - camera.offY * (1 - fixed)));
        drawImagePixels(image, x, y, newW, newH, owner);
    }

    private void drawImagePixels(Image image, int x, int y, int newW, int newH, int owner)
    {
        for(int j = y; j < y + newH; j++)
        {
            for(int i = x; i < x + newW; i++)
            {
                drawPixel(i, j, image.getP()[(i - x) + (j - y) * image.getW()], owner);
            }
        }
    }

    public void drawText(String text, int offX, int offY, int color, int size, int fixed)
    {
        drawText(text, offX, offY, color, size, fixed, -1);
    }

    public void drawText(String text, int textOffsetX, int textOffsetY, int color, int size, int fixed, int owner)
    {
        int symbolOffsetX = 0;
        int symbolOffsetY = 0;
        for(int i = 0; i < text.length(); i++)
        {
            int unicodeId = text.codePointAt(i) - UNICODE_OFFSET;
            if(text.codePointAt(i) == '\n')
            {
                symbolOffsetX = 0;
                symbolOffsetY += font.getLetters()[size / 2][0].getH();
                continue;
            }
            drawSymbol(unicodeId, i, symbolOffsetX + textOffsetX, symbolOffsetY + textOffsetY, size, fixed, color, owner);
            symbolOffsetX += font.getLetters()[size / 2][unicodeId].getW();
        }
    }
    private void drawSymbol(int unicodeId, int i, int offsetX, int offsetY, int size, int fixed, int color, int owner)
    {
        for(int y = 0; y < font.getLetters()[size / 2][unicodeId].getH(); y++)
        {
            for(int x = 0; x < font.getLetters()[size / 2][unicodeId].getW(); x++)
            {
                if(isPixelInSymbol(unicodeId, size, x, y))
                {
                    drawPixel(x + offsetX + camera.offX * (1 - fixed), y + offsetY + + camera.offY * (1 - fixed), color, owner);
                }
            }
        }
    }
    private boolean isPixelInSymbol(int unicodeId, int size, int x, int y)
    {
        return font.getLetters()[size / 2][unicodeId].getP()[x + y * font.getLetters()[size / 2][unicodeId].getW()] == DEFAULT_COLOR;
    }

}

package main.engine.display;

import main.engine.structures.Button;

import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.HashMap;

public class Renderer
{
    private int screenW;
    private int screenH;
    private Camera camera;
    private Font font;
    private int[] p;
    private int[] pOwner;
    HashMap<Long, Integer> colors;

    public Renderer(Window window)
    {
        screenW = (int)(window.getWidth() * Window.SCALE);
        screenH = (int)(window.getHeight() * Window.SCALE);
        camera = window.getCamera();
        colors = new HashMap<>();
        font = new Font("/sansBoldplus.png", 2410, 40);
        p = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
        pOwner = new int[window.getWidth() * window.getHeight()];
        Button.setP(pOwner);
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
        if((x < 0 || x >= screenW || y < 0 || y >= screenH) || value == 0xFFFF00FF)
            return;
        pOwner[id] = owner;
        if((p[id] != 0xFF000000) && (value != 0xFF000000))
            p[id] = alphaCompose(p[id], value);
        else
            p[id] = value;
    }
    public void drawRectangle(int x, int y, int w, int h, int value, int fixed)
    {
        drawRectangle(x, y, w, h, value, fixed, -1);
    }
    public void drawRectangle(int x, int y, int w, int h, int value, int fixed, int owner)
    {
        if(fixed == 0)
        {
            for(int i = Math.max(x, 0) + camera.offX; i < Math.min(w + x, screenW) + camera.offX; i++)
            {
                for(int j = Math.max(y, 0) + camera.offY; j < Math.min(h + y, screenH) + camera.offY; j++)
                {
                    drawPixel(i, j, value, owner);
                }
            }
        }
        else
        {
            for(int i = Math.max(x, 0); i < Math.min(w + x, screenW); i++)
            {
                for(int j = Math.max(y, 0); j < Math.min(h + y, screenH); j++)
                {
                    drawPixel(i, j, value, owner);
                }
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
    public void drawText(String text, int offX, int offY, int color, int size, int fixed, int owner)
    {
        int offsetX = 0;
        int offsetY = 0;
        for(int i = 0; i < text.length(); i++)
        {
            if(text.charAt(i) == '\n')
            {
                offsetX = 0;
                offsetY += font.getLetters()[size / 2][0].getH();
                continue;
            }
            int unicode = text.codePointAt(i) - 32;
            for(int y = 0; y < font.getLetters()[size / 2][unicode].getH(); y++)
            {
                for(int x = 0; x < font.getLetters()[size / 2][unicode].getW(); x++)
                {
                    if(font.getLetters()[size / 2][unicode].getP()[x + y * font.getLetters()[size / 2][unicode].getW()] == 0xFF000000)
                    {
                        drawPixel(x + offX + offsetX + camera.offX * (1 - fixed), y + offY + offsetY + + camera.offY * (1 - fixed), color, owner);
                    }
                }
            }
            offsetX += font.getLetters()[size / 2][unicode].getW();
        }
    }
}

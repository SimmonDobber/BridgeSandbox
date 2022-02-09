package main.engine.structures;

import main.engine.display.Image;
import main.engine.display.Renderer;

public class Entity
{
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected Image img;
    public Entity(String path, int x, int y, int w, int h, int fixed)
    {
        this.img = new Image(path, w, h, fixed);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        img.rescale(w, h);
    }
    public Entity(Image image, int x, int y, int w, int h, int fixed)
    {
        this.img = image;
        image.setFixed(fixed);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        img.rescale(w, h);
    }
    public Entity(Image image, int x, int y, int fixed)
    {
        this.img = image;
        image.setFixed(fixed);
        this.x = x;
        this.y = y;
        this.w = image.getW();
        this.h = image.getH();
    }
    public Entity(int x, int y, int w, int h, int fixed)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        img = new Image(w, h, fixed);
    }
    public void render(Renderer r)
    {
        r.drawImage(img, x, y, img.getFixed());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Image getImg() {
        return img;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
        if(img != null)
            img.rescale(w, h);
    }

    public void setH(int h) {
        this.h = h;
        if(img != null)
            img.rescale(w, h);
    }

    public void setImg(Image img) {
        this.img = img;
    }

}

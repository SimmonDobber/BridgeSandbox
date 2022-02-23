package main.engine.display;

import main.engine.Input;

import java.awt.event.KeyEvent;

public class Camera
{
    public final static int DEFAULT_CAMERA_SPEED = 5;
    public int offsetX;
    public int offsetY;
    public int speed;
    public Camera()
    {
        offsetX = 0;
        offsetY = 0;
        speed = DEFAULT_CAMERA_SPEED;
    }
    public void cameraMove(Input input)
    {
        if(input.isKey(KeyEvent.VK_A))
            offsetX += speed;
        if(input.isKey(KeyEvent.VK_D))
            offsetX -= speed;
        if(input.isKey(KeyEvent.VK_W))
            offsetY += speed;
        if(input.isKey(KeyEvent.VK_S))
            offsetY -= speed;
    }
}

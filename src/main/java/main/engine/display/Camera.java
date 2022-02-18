package main.engine.display;

import main.engine.Input;

import java.awt.event.KeyEvent;

public class Camera
{
    public final static int DEFAULT_CAMERA_SPEED = 5;
    public int offX;
    public int offY;
    public int speed;
    public Camera()
    {
        offX = 0;
        offY = 0;
        speed = DEFAULT_CAMERA_SPEED;
    }
    public void cameraControl(Input input)
    {
        if(input.isKey(KeyEvent.VK_A))
        {
            offX += speed;
        }
        if(input.isKey(KeyEvent.VK_D))
        {
            offX -= speed;
        }
        if(input.isKey(KeyEvent.VK_W))
        {
            offY += speed;
        }
        if(input.isKey(KeyEvent.VK_S))
        {
            offY -= speed;
        }
    }
}

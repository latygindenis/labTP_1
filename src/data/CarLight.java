package data;

import presentation.HabitatView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CarLight extends Car {

    public void setLiveTime(int liveTime) {
        CarLight.liveTime = liveTime;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public static int liveTime = 2;

    private static Image img;

    private boolean inPosition;

    static {
        try {
            img = ImageIO.read(new File("resources/image/lightcar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CarLight(int X, int Y){
        super(X, Y);
        checkPosition();

    }
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public void paint(Graphics g){
        if(img != null){
            g.drawImage(img,this.getX(),this.getY(),img.getWidth(null),img.getHeight(null),null);
        }
    }
    private int newX;
    private int newY;

    private void checkPosition(){
        if (getX() > HabitatView.wLength/2 || getY() > HabitatView.wHeight){

        }
    }

    @Override
    public void move() {


        if (getX() > HabitatView.wLength/2) {
            setX(getX() - 1);
        }
        if(getY() > HabitatView.wHeight/2){
            setY(getY() - 1);
        }
    }
}

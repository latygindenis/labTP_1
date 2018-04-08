package data;

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

    static {
        try {
            img = ImageIO.read(new File("resources/image/lightcar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CarLight(int X, int Y){
        super(X, Y);
    }
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public void paint(Graphics g){
        if(img != null){
            g.drawImage(img,this.getX(),this.getY(),img.getWidth(null),img.getHeight(null),null);
        }
    }

    @Override
    public void move(int X, int Y) {
        setX(X);
        setY(Y);
    }
}

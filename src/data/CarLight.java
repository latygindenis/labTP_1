package data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CarLight extends Car {

    private long bornTime;

    public long getBornTime() {
        return bornTime;
    }

    public void setBornTime(long bornTime) {
        this.bornTime = bornTime;
    }

    private static Image img;

    static {
        try {
            img = ImageIO.read(new File("resources/image/lightcar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CarLight(int X, int Y, long time){
        super(X, Y);
        setBornTime(time);
        CarCollections.getInstance().bornHashMap.put(getId(), bornTime);
    }
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public void paint(Graphics g){
        if(img != null){
            g.drawImage(img,this.getX(),this.getY(),img.getWidth(null),img.getHeight(null),null);
        }
    }

    @Override
    public void move() {

    }
}

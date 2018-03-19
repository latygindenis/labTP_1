package data;

import javax.imageio.ImageIO; // загрузка изображений
import java.awt.*; // графический интерфейс
import java.io.File; // потоки, работа с файлами
import java.io.IOException;

public class CarHeavy extends Car {
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
            img = ImageIO.read(new File("resources/image/heavycar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CarHeavy(int X, int Y, long bornTime){
        super(X, Y);
        setBornTime(bornTime);
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

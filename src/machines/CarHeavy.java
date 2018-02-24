package machines;

import javax.imageio.ImageIO; // загрузка изображений
import java.awt.*; // графический интерфейс
import java.io.File; // потоки, работа с файлами
import java.io.IOException;

public class CarHeavy extends Car {

    static Image img;

    static {
        try {
            img = ImageIO.read(new File("resources/image/heavycar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    CarHeavy(){
        super();
    }
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public Car paint(Graphics g){
        if(img != null){
            g.drawImage(img,this.getX(),this.getY(),img.getWidth(null),img.getHeight(null),null);
        }
        return null;
    }

    @Override
    public void move() {

    }
}

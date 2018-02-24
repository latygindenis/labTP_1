package machines;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CarLight extends Car {

    static Image img;

    static {
        try {
            img = ImageIO.read(new File("resources/image/lightcar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    CarLight(){
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

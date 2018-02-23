package machines;

import javax.imageio.ImageIO; // загрузка изображений
import java.awt.*; // графический интерфейс
import java.io.File; // потоки, работа с файлами
import java.io.IOException;

public class MachineG extends Machine {

    static Image img;

    static {
        try {
            img = ImageIO.read(new File("C:\\Users\\denis\\IdeaProjects\\TPlabs\\resources\\image\\heavycar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MachineG(){
        super();
    }
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public Machine paint(Graphics g){
        if(img != null){
            posX=(int)(Math.random()*(650));
            posY=(int)(Math.random()*(650));
            g.drawImage(img,posX,posY,70,70,null);
        }
        return null;
    }
}

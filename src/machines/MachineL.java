package machines;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class  MachineL extends Machine {

    static Image img;

    static {
        try {
            img = ImageIO.read(new File("C:\\Users\\denis\\IdeaProjects\\TPlabs\\resources\\image\\lightcar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MachineL(){
        super();
    }
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public Machine paint(Graphics g){

        if(img != null){
            posX=(int)(Math.random()*(630));
            posY=(int)(Math.random()*(630));
            g.drawImage(img,posX,posY,70,70,null);
        }
        return null;
    }


}

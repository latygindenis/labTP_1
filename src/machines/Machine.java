package machines;

import java.awt.*; // графический интерфейс

public abstract class Machine{

    protected int posX;
    protected int posY;
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public abstract Machine paint(Graphics g);
    Machine(){
        posX = 0;
        posY = 0;
    }
}

package machines;

import java.awt.*; // графический интерфейс

public abstract class Car implements IBehaviour{

    int posX;
    int posY;
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public abstract Car paint(Graphics g);

    Car(){ //При создании объекта рандомно генерируются его координаты
        posX=(int)(Math.random()*(630));
        posY=(int)(Math.random()*(630));
    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }
}

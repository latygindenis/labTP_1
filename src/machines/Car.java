package machines;

import java.awt.*; // графический интерфейс

public abstract class Car implements IBehaviour{

    int posX;
    int posY;
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public abstract Car paint(Graphics g);

    Car(int X, int Y){ //При создании объекта рандомно генерируются его координаты
        this.posX=X;
        this.posY=Y;
    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }

    @Override
    public void setX(int X) {
        this.posX = X;
    }

    @Override
    public void setY(int Y) {
        this.posY = Y;
    }
}

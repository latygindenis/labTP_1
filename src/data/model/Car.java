package data.model;

import java.awt.*; // графический интерфейс
import java.io.Serializable;
import java.util.UUID;

public abstract class Car implements IBehaviour, Serializable {

    private int posX;
    private int posY;
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public abstract void paint(Graphics g);

    void checkId (){
        for (UUID i: CarCollections.getInstance().idTreeSet ){
            if (this.id == i){
                this.id = UUID.randomUUID();
                checkId();
            }
        }
    }
    Car(int X, int Y){ //При создании объекта рандомно генерируются его координаты
        this.posX=X;
        this.posY=Y;
        checkId();
        CarCollections.getInstance().idTreeSet.add(this.id);
    }

    public Car() {
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

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

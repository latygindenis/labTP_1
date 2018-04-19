package data.model;

import java.awt.*; // графический интерфейс
import java.util.UUID;

public class NewCar {

    private int posX;
    private int posY;
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки

    public NewCar(int posX, int posY, UUID id) {
        this.posX = posX;
        this.posY = posY;
        this.id = id;
    }

    public NewCar() {
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

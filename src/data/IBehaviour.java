package data;
// Интерфейс задает поведение объекта
public interface IBehaviour {
    int getX();
    int getY();
    void setX(int X);
    void setY(int Y);
    void move();
}

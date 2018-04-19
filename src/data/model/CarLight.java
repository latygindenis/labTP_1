package data.model;

import presentation.HabitatView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class CarLight extends Car {

    public CarLight() {
    }


    public void setLiveTime(int liveTime) {
        CarLight.liveTime = liveTime;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public static int liveTime = 2;

    private static Image img;



    static {
        try {
            img = ImageIO.read(new File("resources/image/lightcar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CarLight(int X, int Y){
        super(X, Y);
        checkPosition();

    }
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public void paint(Graphics g){
        if(img != null){
            g.drawImage(img,this.getX(),this.getY(),img.getWidth(null),img.getHeight(null),null);
        }
    }
    private boolean inPosition;
    private int newX;
    private int newY;
    private float normA;
    private float normB;
    private void checkPosition(){
        Random rnd = new Random(System.currentTimeMillis()); // Инициализируем генератор

        if (getX() > HabitatView.panelGen.getWidth()/2  -50 || getY() > HabitatView.panelGen.getHeight()/2 -50){
            inPosition = false;
            newX = 130 + rnd.nextInt(HabitatView.panelGen.getWidth()/2 - 250);
            newY = 130 + rnd.nextInt(HabitatView.panelGen.getHeight()/2 - 250);

            timeToPos = (int) Math.sqrt((newX - getX())*(newX - getX()) + (newY - getY())*(newY - getY()));

            normA = Math.round((newX - super.getX())/(Math.sqrt((newX - super.getX())*(newX - super.getX()) + (newY - super.getY())*(newY - super.getY())) ));
            normB = Math.round ((newY - super.getY())/(Math.sqrt((newX - super.getX())*(newX - super.getX()) + (newY - super.getY())*(newY - super.getY())) ));
            System.out.println("NormA: " + normA);
        }else {
            inPosition = true;
        }
    }
    private int timeToPos;
    private int curTime;

    @Override
    public void move() {
        if (!inPosition){
            curTime += 1;
            System.out.println(curTime);
            setX((int) (getX() + normA));
            setY((int) (getY() + normB));
            if (timeToPos  <= curTime){
                inPosition = true;
            }
        }
    }

    public static Image getImg() {
        return img;
    }

    public static void setImg(Image img) {
        CarLight.img = img;
    }

    public boolean isInPosition() {
        return inPosition;
    }

    public void setInPosition(boolean inPosition) {
        this.inPosition = inPosition;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public float getNormA() {
        return normA;
    }

    public void setNormA(float normA) {
        this.normA = normA;
    }

    public float getNormB() {
        return normB;
    }

    public void setNormB(float normB) {
        this.normB = normB;
    }

    public int getTimeToPos() {
        return timeToPos;
    }

    public void setTimeToPos(int timeToPos) {
        this.timeToPos = timeToPos;
    }

    public int getCurTime() {
        return curTime;
    }

    public void setCurTime(int curTime) {
        this.curTime = curTime;
    }
}

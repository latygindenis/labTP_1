package presentation;

import data.ai.HeavyAI;
import data.ai.LightAI;
import data.model.Car;
import data.model.CarCollections;
import data.model.CarHeavy;
import data.model.CarLight;
import data.model.req.CarsRequest;
import socket.SocketEmitter;

import javax.swing.*;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
public class HabitatModel { // обработка событий клавиатуры
    private Timer timer;
    private double pHeavy; //Вероятность появления CarHeavy
    private double pLight; //Вероятность появления CarLight
    public static long time = 0;
    private int timeHeavy; //Период появления CarHeavy
    private int timeLight; //Период появления CarLight
    int amountHeavy = 0;
    int amountLight = 0;
    HabitatView view;
    public HeavyAI heavyAI = new HeavyAI();
    public LightAI lightAI = new LightAI();


    public HabitatModel(double pHeavy, double pLight, int timeHeavy, int timeLight, HabitatView view) {
        this.pHeavy = pHeavy;
        this.pLight = pLight;
        this.timeHeavy = timeHeavy;
        this.timeLight = timeLight;
        this.view = view;
        heavyAI.start();
        lightAI.start();
    }

    public void update(long t) {
            synchronized (CarCollections.getInstance().arrayCarList){
                CarCollections.getInstance().cleanCollections(t); //Очистка "отживших" машин
                if (t % timeHeavy == 0) { //Каждые timeHeavy секунд
                    if (pHeavy > (float) Math.random()) { // Если прошло по вероятности
                        amountHeavy++;
                        Car rb = new CarHeavy(10 + (int) (Math.random() * (view.panelGen.getWidth() - 100)), 10 + (int) (Math.random() * (view.panelGen.getHeight() - 100)));
                        CarCollections.getInstance().arrayCarList.add(rb);
                        CarCollections.getInstance().idTreeSet.add(rb.getId());
                        CarCollections.getInstance().bornHashMap.put(rb.getId(), time);
                    }
                }
                if (t % timeLight == 0) { //Каждые timeLight секунд
                    if (pLight > (float) Math.random()) { //Если прошло по вероятности
                        amountLight++;
                        Car rb = new CarLight(10 + (int) (Math.random() * (view.panelGen.getWidth() - 100)), 10 + (int) (Math.random() * (view.panelGen.getHeight() - 100)));
                        CarCollections.getInstance().arrayCarList.add(rb);
                        CarCollections.getInstance().idTreeSet.add(rb.getId());
                        CarCollections.getInstance().bornHashMap.put(rb.getId(), time);
                    }
                }
            }


    }

    public double getpHeavy() {
        return pHeavy;
    }

    public void setpHeavy(double pHeavy) {
        this.pHeavy = pHeavy;
    }

    public double getpLight() {
        return pLight;
    }

    public void setpLight(double pLight) {
        this.pLight = pLight;
    }

    public int getTimeHeavy() {
        return timeHeavy;
    }

    public void setTimeHeavy(int timeHeavy) {
        this.timeHeavy = timeHeavy;
    }

    public int getTimeLight() {
        return timeLight;
    }

    public void setTimeLight(int timeLight) {
        this.timeLight = timeLight;
    }

    void startSimulation(boolean firstStart) {
        timer = new Timer();
        if (firstStart) {
            if (CarCollections.getInstance().arrayCarList.size() < 1) {
                amountHeavy = 0;
                amountLight = 0;
            }
            time = 0;
        }else {
            beginHeavyAI();
            beginLightAI();
        }
        timer.schedule(new TimerTask() { //Добавление задания в таймер
            public void run() {
                time++;
                update(time);
                view.startSimulation(generateStatisticString(true), generateStatisticString(false));
            }
        }, 0, 10);
    }

    String generateStatisticString(boolean withTime) {
        String statistic;
        if (withTime) {
            statistic = "Количество: " + (amountLight + amountHeavy) + "\n" +
                    "Легковые: " + amountLight + "\n" +
                    "Грузовые: " + amountHeavy + "\n" +
                    "Время: " + time;
        } else {
            statistic = "Количество: " + (amountLight + amountHeavy) + "\n" +
                    "Легковые: " + amountLight + "\n" +
                    "Грузовые: " + amountHeavy + "\n";
        }
        return statistic;
    }

    public void stopSimulation(boolean selected) {
        timer.cancel();
        timer.purge();
        pauseHeavyAI();
        pauseLightAI();
        if (selected) {
            Object[] options = {"Resume",
                    "Stop"};
            int n = JOptionPane.showOptionDialog(new JFrame(),
                    generateStatisticString(true),
                    "StopDialog",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0) {
                startSimulation(false);
            } else {
                CarCollections.getInstance().idTreeSet.clear();
                CarCollections.getInstance().bornHashMap.clear();
                CarCollections.getInstance().arrayCarList.clear();
                view.stopSimulation();
            }
        } else {
            CarCollections.getInstance().idTreeSet.clear();
            CarCollections.getInstance().bornHashMap.clear();
            CarCollections.getInstance().arrayCarList.clear();
            view.stopSimulation();
        }
    }

    void pauseLightAI (){
        if (!lightAI.paused){
            lightAI.paused = true;
        }
    }
    void beginLightAI(){
        if (lightAI.paused){
            synchronized (lightAI.obj){
                lightAI.paused = false;
                lightAI.obj.notify();
            }
        }
    }
    void pauseHeavyAI (){
        if (!heavyAI.paused){
            heavyAI.paused = true;
        }
    }
    void beginHeavyAI(){
        if (heavyAI.paused){
            synchronized (heavyAI.obj){
                heavyAI.paused = false;
                heavyAI.obj.notify();
            }
        }
    }
}
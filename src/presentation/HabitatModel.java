package presentation;

import data.Car;
import data.CarArrayList;
import data.CarHeavy;
import data.CarLight;

import java.util.Timer;
import java.util.TimerTask;

public class HabitatModel { // обработка событий клавиатуры

    private Timer timer;
    private double pHeavy; //Вероятность появления CarHeavy
    private double pLight; //Вероятность появления CarLight
    long time = 0;
    private int timeHeavy; //Период появления CarHeavy
    private int timeLight; //Период появления CarLight
    int amountHeavy = 0;
    int amountLight = 0;
    HabitatView view;

    public HabitatModel(double pHeavy, double pLight, int timeHeavy, int timeLight, HabitatView view) {
        this.pHeavy = pHeavy;
        this.pLight = pLight;
        this.timeHeavy = timeHeavy;
        this.timeLight = timeLight;
        this.view = view;
    }

    void update(long t) {
        if (t % timeHeavy == 0) { //Каждые timeHeavy секунд
            if (pHeavy > (float) Math.random()) { // Если прошло по вероятности
                amountHeavy++;
                Car rb = new CarHeavy(10 + (int) (Math.random() * 410), 10 + (int) (Math.random() * 410));
                CarArrayList.getInstance().arrayCarList.add(rb);

            }
        }
        if (t % timeLight == 0) { //Каждые timeLight секунд
            if (pLight > (float) Math.random()) { //Если прошло по вероятности
                amountLight++;
                Car rb = new CarLight(10 + (int) (Math.random() * 410), 10 + (int) (Math.random() * 410));
                CarArrayList.getInstance().arrayCarList.add(rb);

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
        if(firstStart) {
            amountHeavy = 0;
            amountLight = 0;
            time = 0;
        }
        timer.schedule(new TimerTask() { //Добавление задания в таймер
            public void run() {
                time++;
                update(time);
                view.startSimulation(generateStatisticString(true), generateStatisticString(false));
            }
        }, 0, 1000);
    }
    String generateStatisticString(boolean withTime) {
        String statistic;
        if(withTime) {
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

    public void endSimulation(boolean selected) {

    }
}
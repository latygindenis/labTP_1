package machines;

import data.Car;
import data.CarArrayList;
import data.CarHeavy;
import data.CarLight;

public class HabitatModel { // обработка событий клавиатуры

    private double pHeavy; //Вероятность появления CarHeavy
    private double pLight; //Вероятность появления CarLight
    long time = 0;
    private int timeHeavy; //Период появления CarHeavy
    private int timeLight; //Период появления CarLight
    int amountOfG = 0;
    int amountOfL = 0;

    public HabitatModel(double pHeavy, double pLight, int timeHeavy, int timeLight) {
        this.pHeavy = pHeavy;
        this.pLight = pLight;
        this.timeHeavy = timeHeavy;
        this.timeLight = timeLight;
    }

    void update(long t) {
        if (t % timeHeavy == 0) { //Каждые timeHeavy секунд
            if (pHeavy > (float) Math.random()) { // Если прошло по вероятности
                amountOfG++;
                Car rb = new CarHeavy(10 + (int) (Math.random() * 410), 10 + (int) (Math.random() * 410));
                CarArrayList.getInstance().arrayCarList.add(rb);

            }
        }
        if (t % timeLight == 0) { //Каждые timeLight секунд
            if (pLight > (float) Math.random()) { //Если прошло по вероятности
                amountOfL++;
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
}
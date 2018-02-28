package machines;

import data.Car;
import data.CarArrayList;
import data.CarHeavy;
import data.CarLight;

public class Habitat { // обработка событий клавиатуры

    static double pHeavy; //Вероятность появления CarHeavy
    static double pLight; //Вероятность появления CarLight
    static long time = 0;
    static int timeHeavy; //Период появления CarHeavy
    static int timeLight; //Период появления CarLight
    static int amountOfG = 0;
    static int amountOfL = 0;

    static {
        pHeavy = 0.5;
        pLight = 0.5;
        timeHeavy = 1;
        timeLight = 1;
    }

    static void update(long t) {
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
}
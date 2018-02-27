package machines;

public class Habitat { // обработка событий клавиатуры

    private static double pHeavy; //Вероятность появления CarHeavy
    private static double pLight; //Вероятность появления CarLight
    static long time = 0;
    private static int timeHeavy; //Период появления CarHeavy
    private static int timeLight; //Период появления CarLight
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

    public static void startSimulation() {
        amountOfG = 0;
        amountOfL = 0;
        time = 0;
    }

    public static void endSimulation() {
        CarArrayList.getInstance().arrayCarList.clear();
        amountOfG = 0;
        amountOfL = 0;
        time = 0;
    }
}
package data.ai;

import data.model.Car;
import data.model.CarCollections;
import data.model.CarLight;
public class LightAI extends BaseAI {
    public LightAI() {
        super("LightThread");
    }

    @Override
    void nextStep() {
        //System.out.println(super.threadName);
        synchronized (CarCollections.getInstance().arrayCarList) {
            for (Car car : CarCollections.getInstance().arrayCarList) {
                if (car instanceof CarLight) {
                    car.move();
                }
            }
        }
    }
}
package data.ai;

import data.model.Car;
import data.model.CarCollections;
import data.model.CarHeavy;
public class HeavyAI extends BaseAI {
    public HeavyAI() {
        super("HeavyThread");
    }

    @Override
    void nextStep() {
        // System.out.println(super.threadName);
        synchronized (CarCollections.getInstance().arrayCarList){
            for (Car car:CarCollections.getInstance().arrayCarList){
                if (car instanceof CarHeavy){
                    car.move();
                }
            }
        }
    }
}

package data;

public class HeavyAI extends BaseAI {
    public HeavyAI() {
        super("HeavyThread");
    }

    @Override
    void nextStep() {
        System.out.println(super.threadName);
        synchronized (CarCollections.getInstance().arrayCarList){
            for (Car car:CarCollections.getInstance().arrayCarList){
                if (car instanceof CarHeavy){
                    car.move(car.getX() + 1, car.getY());
                }
            }
        }
    }
}

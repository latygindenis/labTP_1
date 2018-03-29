package data;

public class HeavyAI extends BaseAI {
    public HeavyAI() {
        super("HeavyThread");
    }

    @Override
    Boolean nextStep() {
        System.out.println(super.threadName);
            for (Car car:CarCollections.getInstance().arrayCarList){
                if (car instanceof CarHeavy){
                    car.move(car.getX() + 1, car.getY());
                }
            }
            super.isGoing = false;

        return true;
    }
}

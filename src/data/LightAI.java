package data;

public class LightAI extends BaseAI{
    public LightAI() {
        super("LightThread");
    }

    @Override
    Boolean nextStep() {
        System.out.println(super.threadName);

            for (Car car:CarCollections.getInstance().arrayCarList){
                if (car instanceof CarLight){
                    car.move(car.getX() - 1, car.getY());
                }
            }
            super.isGoing = false;
        return true;
    }
}

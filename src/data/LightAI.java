package data;

public class LightAI extends BaseAI{
    public LightAI() {
        super("LightThread");
    }

    @Override
    void nextStep() {
        System.out.println(super.threadName);
        synchronized (CarCollections.getInstance().arrayCarList){
            for (Car car:CarCollections.getInstance().arrayCarList){
                if (car instanceof CarLight){
                    car.move();
                }
            }
        }
}
}

package data;

public abstract class BaseAI implements Runnable{
    Boolean isGoing = true;
    public String threadName;

    public BaseAI(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        isGoing = true;
        while (isGoing){
            synchronized (CarCollections.getInstance().arrayCarList){
                nextStep();
            }

        }
    }
    abstract Boolean nextStep();
}

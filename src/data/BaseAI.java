package data;

public abstract class BaseAI extends Thread{
    Boolean isGoing = true;
    public String threadName;
    public final Object obj = new Object();
    public BaseAI(String threadName) {
        this.threadName = threadName;
    }
    public Boolean paused = new Boolean(false);

    @Override
    public void run() {
        while (!this.isInterrupted()){
            synchronized (obj){
               if (paused){
                   try {
                       System.out.println(threadName + "paused");
                       obj.wait();

                   } catch (InterruptedException e) {
                       break;
                   }
               }
            }
            try {
                this.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nextStep();
        }
    }
    abstract void nextStep();
}

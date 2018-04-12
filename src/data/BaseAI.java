package data;

public abstract class BaseAI extends Thread{
    public String threadName;
    public final Object obj = new Object();
    public BaseAI(String threadName) {
        this.setDaemon(true);
        this.threadName = threadName;
    }
    public Boolean paused = Boolean.TRUE;
    public boolean isGoing = true;
    @Override
    public void run() {

        while (isGoing){
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

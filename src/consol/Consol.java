package consol;

public class Consol {
    ConsolView consolView;
    ConsolModel consolModel;
    ConsolController consolController;

    public Consol(int wHeight, int wWidth) {
       consolView = new ConsolView(wHeight, wWidth);
       consolModel = new ConsolModel() {
           @Override
           boolean command(String str) {
               if (str.equals("Привет")){
                   System.out.println("И тебе привет!");
                   return true;
               } else {
                   return false;
               }
           }
       };
       consolController = new ConsolController(consolView, consolModel);
    }

    public static void main(String[] args) {
        Consol consol = new Consol(300, 300);
    }
}

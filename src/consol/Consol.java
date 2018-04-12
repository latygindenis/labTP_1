package consol;

public class Consol {
    ConsolView consolView;
    ConsolModel consolModel;
    ConsolController consolController;

    public Consol(int wHeight, int wWidth) {
       consolView = new ConsolView(wHeight, wWidth);
       consolModel = new ConsolModel();
       consolController = new ConsolController(consolView, consolModel);
    }
}

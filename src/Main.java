import machines.HabitatController;
import machines.HabitatModel;
import machines.HabitatView;

public class Main {

    public static void main(String[] args) {
        HabitatView view = new HabitatView(850, 600, 100, 100);
        HabitatModel habitat = new HabitatModel(0.5, 0.5, 1,1);
        HabitatController controllers = new HabitatController(view, habitat);
    }
}

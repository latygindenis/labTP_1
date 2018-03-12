import presentation.HabitatController;
import presentation.HabitatModel;
import presentation.HabitatView;

public class Main {

    public static void main(String[] args) {
        HabitatView view = new HabitatView(850, 600, 100, 100);
        HabitatModel model = new HabitatModel(0.5, 0.5, 1,1, view);
        HabitatController controller = new HabitatController(view, model);

    }
}

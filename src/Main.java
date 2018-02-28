import machines.Controllers;
import machines.View;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        View view = new View(750, 560, 100, 100);
        Controllers controllers = new Controllers(view);
    }
}
